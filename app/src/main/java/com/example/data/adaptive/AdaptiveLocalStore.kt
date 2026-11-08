package com.example.data.adaptive

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONArray
import org.json.JSONObject

/**
 * ذخیره‌سازی محلی موتور تطبیقی — فایل SharedPreferences جداگانه.
 *
 * همان الگوی `LocalKonkoorStore`: JSON با `org.json` + `runCatching` + خالی در صورت خرابی.
 * **هیچ تغییری در `LocalKonkoorStore.kt` داده نمی‌شود**؛ این کلاس فقط داده‌های
 * خود موتور را نگه می‌دارد. بدون Room (کد مرده است)، بدون migration؛
 * داده‌ها نسخه‌دارند (`schemaVersion = 1`) تا تغییر بعدی سازگار بماند.
 */
class AdaptiveLocalStore(context: Context) {
    private val prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val _examAttempts = MutableStateFlow(loadExamAttempts())
    val examAttempts: StateFlow<List<ExamAttempt>> = _examAttempts.asStateFlow()

    private val _reviewCards = MutableStateFlow(loadReviewCards())
    val reviewCards: StateFlow<List<ReviewCardState>> = _reviewCards.asStateFlow()

    private val _diagnosticSessions = MutableStateFlow(loadDiagnosticSessions())
    val diagnosticSessions: StateFlow<List<DiagnosticSession>> = _diagnosticSessions.asStateFlow()

    private val _dailyPlans = MutableStateFlow(loadDailyPlans())
    val dailyPlans: StateFlow<List<DailyPlan>> = _dailyPlans.asStateFlow()

    // ---------- Exam attempts ----------

    fun recordExamAttempt(attempt: ExamAttempt) {
        val current = _examAttempts.value.toMutableList()
        current.add(0, attempt)
        _examAttempts.value = current
        persistExamAttempts(current)
    }

    fun recordQuestionAttempt(attempt: QuestionAttempt) {
        // تلاش تکی در یک «آزمون تمرینی» همان روز ذخیره می‌شود تا سابقه مبحث کامل بماند.
        val today = attempt.answeredAtEpochDay
        val current = _examAttempts.value.toMutableList()
        val index = current.indexOfFirst { it.startedAtEpochDay == today && it.title == PRACTICE_TITLE }
        if (index >= 0) {
            val existing = current[index]
            current[index] = existing.copy(questions = existing.questions + attempt)
        } else {
            current.add(
                0,
                ExamAttempt(
                    id = "practice-$today",
                    subjectId = attempt.subjectId,
                    title = PRACTICE_TITLE,
                    startedAtEpochDay = today,
                    totalSeconds = attempt.timeSpentSeconds,
                    questions = listOf(attempt)
                )
            )
        }
        _examAttempts.value = current
        persistExamAttempts(current)
    }

    // ---------- Review cards ----------

    fun saveReviewCard(card: ReviewCardState) {
        val current = _reviewCards.value.toMutableList()
        val index = current.indexOfFirst { it.itemId == card.itemId }
        if (index >= 0) current[index] = card else current.add(card)
        _reviewCards.value = current
        persistReviewCards(current)
    }

    // ---------- Diagnostic sessions ----------

    fun recordDiagnosticSession(session: DiagnosticSession) {
        val current = _diagnosticSessions.value.toMutableList()
        current.add(0, session)
        _diagnosticSessions.value = current
        persistDiagnosticSessions(current)
    }

    // ---------- Daily plans ----------

    fun saveDailyPlan(plan: DailyPlan) {
        val current = _dailyPlans.value.toMutableList()
        val index = current.indexOfFirst { it.dateEpochDay == plan.dateEpochDay }
        if (index >= 0) current[index] = plan else current.add(0, plan)
        _dailyPlans.value = current
        persistDailyPlans(current)
    }

    // ---------- JSON (de)serialisation ----------

    private fun questionAttemptToJson(a: QuestionAttempt): JSONObject = JSONObject().apply {
        put("questionId", a.questionId)
        put("topicId", a.topicId)
        put("subjectId", a.subjectId)
        put("level", a.level.name)
        put("isCorrect", a.isCorrect)
        put("wasSkipped", a.wasSkipped)
        put("timeSpentSeconds", a.timeSpentSeconds)
        put("answeredAtEpochDay", a.answeredAtEpochDay)
        a.selfReportedCause?.let { put("selfReportedCause", it.name) }
        a.inferredCause?.let { put("inferredCause", it.name) }
    }

    private fun questionAttemptFromJson(obj: JSONObject): QuestionAttempt = QuestionAttempt(
        questionId = obj.optString("questionId"),
        topicId = obj.optString("topicId"),
        subjectId = obj.optString("subjectId"),
        level = runCatching { QuestionLevel.valueOf(obj.optString("level", "CONCEPT")) }
            .getOrDefault(QuestionLevel.CONCEPT),
        isCorrect = obj.optBoolean("isCorrect", false),
        wasSkipped = obj.optBoolean("wasSkipped", false),
        timeSpentSeconds = obj.optInt("timeSpentSeconds", 0),
        answeredAtEpochDay = obj.optLong("answeredAtEpochDay", 0L),
        selfReportedCause = obj.optString("selfReportedCause").takeIf { it.isNotBlank() }
            ?.let { runCatching { ErrorCause.valueOf(it) }.getOrNull() },
        inferredCause = obj.optString("inferredCause").takeIf { it.isNotBlank() }
            ?.let { runCatching { ErrorCause.valueOf(it) }.getOrNull() }
    )

    private fun examAttemptToJson(a: ExamAttempt): JSONObject = JSONObject().apply {
        put("schemaVersion", SCHEMA_VERSION)
        put("id", a.id)
        put("subjectId", a.subjectId)
        put("title", a.title)
        put("startedAtEpochDay", a.startedAtEpochDay)
        put("totalSeconds", a.totalSeconds)
        val arr = JSONArray()
        a.questions.forEach { arr.put(questionAttemptToJson(it)) }
        put("questions", arr)
    }

    private fun examAttemptFromJson(obj: JSONObject): ExamAttempt {
        val arr = obj.optJSONArray("questions") ?: JSONArray()
        return ExamAttempt(
            id = obj.optString("id"),
            subjectId = obj.optString("subjectId"),
            title = obj.optString("title"),
            startedAtEpochDay = obj.optLong("startedAtEpochDay", 0L),
            totalSeconds = obj.optInt("totalSeconds", 0),
            questions = buildList {
                for (i in 0 until arr.length()) {
                    runCatching { add(questionAttemptFromJson(arr.getJSONObject(i))) }
                }
            }
        )
    }

    private fun loadExamAttempts(): List<ExamAttempt> {
        val raw = prefs.getString(KEY_EXAM_ATTEMPTS, null) ?: return emptyList()
        return runCatching {
            val arr = JSONArray(raw)
            buildList {
                for (i in 0 until arr.length()) {
                    runCatching { add(examAttemptFromJson(arr.getJSONObject(i))) }
                }
            }
        }.getOrDefault(emptyList())
    }

    private fun persistExamAttempts(list: List<ExamAttempt>) {
        val arr = JSONArray()
        list.forEach { arr.put(examAttemptToJson(it)) }
        prefs.edit().putString(KEY_EXAM_ATTEMPTS, arr.toString()).apply()
    }

    private fun reviewCardToJson(c: ReviewCardState): JSONObject = JSONObject().apply {
        put("schemaVersion", SCHEMA_VERSION)
        put("itemId", c.itemId)
        put("topicId", c.topicId)
        put("subjectId", c.subjectId)
        put("leitnerBox", c.leitnerBox)
        put("errorCount", c.errorCount)
        put("totalReviews", c.totalReviews)
        put("consecutiveCorrect", c.consecutiveCorrect)
        put("lastReviewEpochDay", c.lastReviewEpochDay)
        put("nextReviewEpochDay", c.nextReviewEpochDay)
    }

    private fun reviewCardFromJson(obj: JSONObject): ReviewCardState = ReviewCardState(
        itemId = obj.optString("itemId"),
        topicId = obj.optString("topicId"),
        subjectId = obj.optString("subjectId"),
        leitnerBox = obj.optInt("leitnerBox", 1),
        errorCount = obj.optInt("errorCount", 0),
        totalReviews = obj.optInt("totalReviews", 0),
        consecutiveCorrect = obj.optInt("consecutiveCorrect", 0),
        lastReviewEpochDay = obj.optLong("lastReviewEpochDay", 0L),
        nextReviewEpochDay = obj.optLong("nextReviewEpochDay", 0L)
    )

    private fun loadReviewCards(): List<ReviewCardState> {
        val raw = prefs.getString(KEY_REVIEW_CARDS, null) ?: return emptyList()
        return runCatching {
            val arr = JSONArray(raw)
            buildList {
                for (i in 0 until arr.length()) {
                    runCatching { add(reviewCardFromJson(arr.getJSONObject(i))) }
                }
            }
        }.getOrDefault(emptyList())
    }

    private fun persistReviewCards(list: List<ReviewCardState>) {
        val arr = JSONArray()
        list.forEach { arr.put(reviewCardToJson(it)) }
        prefs.edit().putString(KEY_REVIEW_CARDS, arr.toString()).apply()
    }

    private fun diagnosticSessionToJson(s: DiagnosticSession): JSONObject = JSONObject().apply {
        put("schemaVersion", SCHEMA_VERSION)
        put("topicId", s.topicId)
        put("subjectId", s.subjectId)
        val ids = JSONArray()
        s.askedQuestionIds.forEach { ids.put(it) }
        put("askedQuestionIds", ids)
        val arr = JSONArray()
        s.answers.forEach { arr.put(questionAttemptToJson(it)) }
        put("answers", arr)
        put("concludedStatus", s.concludedStatus.name)
        s.weakestLevel?.let { put("weakestLevel", it.name) }
        s.recommendedLessonId?.let { put("recommendedLessonId", it) }
        put("concludedAtEpochDay", s.concludedAtEpochDay)
    }

    private fun diagnosticSessionFromJson(obj: JSONObject): DiagnosticSession {
        val ids = obj.optJSONArray("askedQuestionIds") ?: JSONArray()
        val arr = obj.optJSONArray("answers") ?: JSONArray()
        return DiagnosticSession(
            topicId = obj.optString("topicId"),
            subjectId = obj.optString("subjectId"),
            askedQuestionIds = buildList {
                for (i in 0 until ids.length()) add(ids.optString(i))
            },
            answers = buildList {
                for (i in 0 until arr.length()) {
                    runCatching { add(questionAttemptFromJson(arr.getJSONObject(i))) }
                }
            },
            concludedStatus = runCatching {
                TopicStatus.valueOf(obj.optString("concludedStatus", "NOT_STARTED"))
            }.getOrDefault(TopicStatus.NOT_STARTED),
            weakestLevel = obj.optString("weakestLevel").takeIf { it.isNotBlank() }
                ?.let { runCatching { QuestionLevel.valueOf(it) }.getOrNull() },
            recommendedLessonId = obj.optString("recommendedLessonId").takeIf { it.isNotBlank() },
            concludedAtEpochDay = obj.optLong("concludedAtEpochDay", 0L)
        )
    }

    private fun loadDiagnosticSessions(): List<DiagnosticSession> {
        val raw = prefs.getString(KEY_DIAGNOSTICS, null) ?: return emptyList()
        return runCatching {
            val arr = JSONArray(raw)
            buildList {
                for (i in 0 until arr.length()) {
                    runCatching { add(diagnosticSessionFromJson(arr.getJSONObject(i))) }
                }
            }
        }.getOrDefault(emptyList())
    }

    private fun persistDiagnosticSessions(list: List<DiagnosticSession>) {
        val arr = JSONArray()
        list.forEach { arr.put(diagnosticSessionToJson(it)) }
        prefs.edit().putString(KEY_DIAGNOSTICS, arr.toString()).apply()
    }

    private fun dailyPlanToJson(p: DailyPlan): JSONObject = JSONObject().apply {
        put("schemaVersion", SCHEMA_VERSION)
        put("dateEpochDay", p.dateEpochDay)
        put("generatedAtEpochDay", p.generatedAtEpochDay)
        val arr = JSONArray()
        p.items.forEach { item ->
            arr.put(
                JSONObject().apply {
                    put("lessonId", item.lessonId)
                    put("subjectId", item.subjectId)
                    put("kind", item.kind.name)
                    put("minutes", item.minutes)
                    put("reason", item.reason)
                }
            )
        }
        put("items", arr)
    }

    private fun dailyPlanFromJson(obj: JSONObject): DailyPlan {
        val arr = obj.optJSONArray("items") ?: JSONArray()
        return DailyPlan(
            dateEpochDay = obj.optLong("dateEpochDay", 0L),
            items = buildList {
                for (i in 0 until arr.length()) {
                    val o = arr.getJSONObject(i)
                    runCatching {
                        add(
                            DailyPlanItem(
                                lessonId = o.optString("lessonId"),
                                subjectId = o.optString("subjectId"),
                                kind = PlanItemKind.valueOf(o.optString("kind", "LEARN")),
                                minutes = o.optInt("minutes", 0),
                                reason = o.optString("reason", "")
                            )
                        )
                    }
                }
            },
            generatedAtEpochDay = obj.optLong("generatedAtEpochDay", 0L)
        )
    }

    private fun loadDailyPlans(): List<DailyPlan> {
        val raw = prefs.getString(KEY_DAILY_PLANS, null) ?: return emptyList()
        return runCatching {
            val arr = JSONArray(raw)
            buildList {
                for (i in 0 until arr.length()) {
                    runCatching { add(dailyPlanFromJson(arr.getJSONObject(i))) }
                }
            }
        }.getOrDefault(emptyList())
    }

    private fun persistDailyPlans(list: List<DailyPlan>) {
        val arr = JSONArray()
        list.forEach { arr.put(dailyPlanToJson(it)) }
        prefs.edit().putString(KEY_DAILY_PLANS, arr.toString()).apply()
    }

    companion object {
        const val SCHEMA_VERSION = 1
        const val PRACTICE_TITLE = "تمرین روزانه"
        private const val PREFS_NAME = "adaptive_local_store"
        private const val KEY_EXAM_ATTEMPTS = "exam_attempts_json"
        private const val KEY_REVIEW_CARDS = "review_cards_json"
        private const val KEY_DIAGNOSTICS = "diagnostics_json"
        private const val KEY_DAILY_PLANS = "daily_plans_json"
    }
}
