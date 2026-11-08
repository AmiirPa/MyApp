package com.example.data.adaptive

/**
 * آزمون تشخیصی تطبیقی کوتاه برای هر مبحث (§۳ الحاقیه).
 *
 * قواعد:
 * - شروع از سطح CONCEPT؛ درست → سخت‌تر (APPLICATION)، غلط → آسان‌تر (FOUNDATION).
 * - سؤال APPLICATION قبل از تأیید CONCEPT پرسیده نمی‌شود.
 * - توقف: حداکثر ۶ سؤال، یا ۳ پاسخ متوالی در یک سطح با نتیجه یکسان.
 * - خروجی: وضعیت مبحث + ضعیف‌ترین سطح + درس پیشنهادی (بنیادین یا خود درس).
 * - همه توابع خالص‌اند.
 */
object DiagnosticEngine {

    /** حداکثر سؤال در یک جلسه تشخیصی. */
    const val MAX_QUESTIONS = 6

    /** تعداد پاسخ متوالی یکسان برای توقف زودهنگام. */
    const val STOP_STREAK = 3

    /** حداقل پاسخ برای نتیجه‌گیری. */
    const val MIN_ANSWERS = 3

    /** یک سؤال تشخیصی از بانک. */
    data class DiagnosticQuestionItem(
        val id: String,
        val topicId: String,
        val subjectId: String,
        val level: QuestionLevel,
        val questionText: String,
        val options: List<String>,
        val correctIndex: Int,
        /** شناسه درس بنیادین پیش‌نیاز (برای سؤال FOUNDATION). */
        val foundationLessonId: String? = null
    )

    /** نتیجه یک جلسه تشخیصی. */
    data class DiagnosticResult(
        val topicId: String,
        val status: TopicStatus,
        val weakestLevel: QuestionLevel?,
        /** درس پیشنهادی برای شروع: بنیادین پیش‌نیاز یا خود درس. */
        val recommendedLessonId: String?,
        val evidenceLevel: EvidenceLevel,
        val askedCount: Int
    )

    /**
     * سؤال بعدی را بر اساس پاسخ‌های تا اینجا انتخاب می‌کند.
     * @return سؤال بعدی، یا `null` یعنی جلسه باید تمام شود.
     */
    fun nextQuestion(
        bank: List<DiagnosticQuestionItem>,
        answersSoFar: List<QuestionAttempt>
    ): DiagnosticQuestionItem? {
        if (answersSoFar.size >= MAX_QUESTIONS) return null
        if (shouldStop(answersSoFar)) return null

        val askedIds = answersSoFar.map { it.questionId }.toSet()
        val remaining = bank.filter { it.id !in askedIds }
        if (remaining.isEmpty()) return null

        // شروع: اولین سؤال CONCEPT.
        if (answersSoFar.isEmpty()) {
            return remaining.firstOrNull { it.level == QuestionLevel.CONCEPT }
                ?: remaining.firstOrNull()
        }

        val last = answersSoFar.last()
        val lastLevel = last.level
        return when {
            // درست در CONCEPT → سراغ APPLICATION؛ ولی فقط اگر CONCEPT تأیید شده باشد
            // (حداقل یک درست در CONCEPT) — این شرط با رسیدن به اینجا برقرار است.
            last.isCorrect && lastLevel == QuestionLevel.CONCEPT ->
                remaining.firstOrNull { it.level == QuestionLevel.APPLICATION }
                    ?: remaining.firstOrNull { it.level == QuestionLevel.CONCEPT }

            // درست در APPLICATION → یک APPLICATION دیگر، یا پایان.
            last.isCorrect && lastLevel == QuestionLevel.APPLICATION ->
                remaining.firstOrNull { it.level == QuestionLevel.APPLICATION }

            // غلط در CONCEPT یا APPLICATION → پایین‌تر: FOUNDATION (شکاف پیش‌نیاز).
            !last.isCorrect && lastLevel != QuestionLevel.FOUNDATION ->
                remaining.firstOrNull { it.level == QuestionLevel.FOUNDATION }
                    ?: remaining.firstOrNull { it.level == QuestionLevel.CONCEPT }

            // غلط در FOUNDATION → یک FOUNDATION دیگر (شکاف عمیق‌تر).
            !last.isCorrect && lastLevel == QuestionLevel.FOUNDATION ->
                remaining.firstOrNull { it.level == QuestionLevel.FOUNDATION }

            // درست در FOUNDATION بعد از غلط → برگرد به CONCEPT.
            last.isCorrect && lastLevel == QuestionLevel.FOUNDATION ->
                remaining.firstOrNull { it.level == QuestionLevel.CONCEPT }

            else -> remaining.firstOrNull()
        }
    }

    /** آیا ۳ پاسخ متوالی در یک سطح با نتیجه یکسان داریم؟ */
    fun shouldStop(answersSoFar: List<QuestionAttempt>): Boolean {
        if (answersSoFar.size < STOP_STREAK) return false
        val lastThree = answersSoFar.takeLast(STOP_STREAK)
        if (lastThree.map { it.level }.toSet().size != 1) return false
        return lastThree.all { it.isCorrect } || lastThree.all { !it.isCorrect }
    }

    /**
     * نتیجه جلسه تشخیصی را از پاسخ‌ها می‌سازد.
     * @param lessonId شناسه درس اصلی مبحث (برای توصیه «خود درس»).
     */
    fun conclude(
        topicId: String,
        subjectId: String,
        answers: List<QuestionAttempt>,
        bank: List<DiagnosticQuestionItem>,
        lessonId: String
    ): DiagnosticResult {
        val byLevel = answers.groupBy { it.level }
        val foundationWrong = byLevel[QuestionLevel.FOUNDATION]?.any { !it.isCorrect } == true
        val foundationAsked = byLevel[QuestionLevel.FOUNDATION].orEmpty()
        val conceptCorrect = byLevel[QuestionLevel.CONCEPT]?.count { it.isCorrect } ?: 0
        val conceptAsked = byLevel[QuestionLevel.CONCEPT].orEmpty().size
        val applicationCorrect = byLevel[QuestionLevel.APPLICATION]?.count { it.isCorrect } ?: 0
        val applicationAsked = byLevel[QuestionLevel.APPLICATION].orEmpty().size

        val weakestLevel: QuestionLevel? = when {
            foundationWrong -> QuestionLevel.FOUNDATION
            conceptAsked > 0 && conceptCorrect == 0 -> QuestionLevel.CONCEPT
            applicationAsked > 0 && applicationCorrect == 0 -> QuestionLevel.APPLICATION
            else -> null
        }

        // درس بنیادین پیش‌نیاز از اولین سؤال FOUNDATION غلط.
        val foundationLessonId = answers
            .firstOrNull { !it.isCorrect && it.level == QuestionLevel.FOUNDATION }
            ?.let { attempt -> bank.firstOrNull { it.id == attempt.questionId }?.foundationLessonId }

        val status = when {
            // پیش‌نیاز غلط = ضعف پیش‌نیاز (حتی اگر مفهوم درست بوده باشد).
            foundationWrong -> TopicStatus.PREREQUISITE_WEAK
            // مفهوم کاملاً غلط = یادگیری ناقص (شروع از قدم ۱ خود درس).
            conceptAsked > 0 && conceptCorrect == 0 -> TopicStatus.PARTIAL
            // کاربرد غلط ولی مفهوم درست = یادگیری ناقص در ترکیب.
            applicationAsked > 0 && applicationCorrect == 0 -> TopicStatus.PARTIAL
            // همه درست = در حال یادگیری (تشخیصی به‌تنهایی «مسلط» نمی‌سازد).
            answers.isNotEmpty() && answers.all { it.isCorrect } -> TopicStatus.LEARNING
            // پاسخ‌های مخلوط = در حال یادگیری.
            answers.isNotEmpty() -> TopicStatus.LEARNING
            else -> TopicStatus.NOT_STARTED
        }

        return DiagnosticResult(
            topicId = topicId,
            status = status,
            weakestLevel = weakestLevel,
            recommendedLessonId = foundationLessonId ?: lessonId,
            evidenceLevel = TopicStatusEngine.evidenceLevel(answers.size),
            askedCount = answers.size
        )
    }
}
