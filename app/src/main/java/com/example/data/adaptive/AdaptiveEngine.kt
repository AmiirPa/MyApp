package com.example.data.adaptive

import com.example.util.StreakCalculator

/**
 * نمای واحد موتور تطبیقی — یک نقطه ورود برای لایه UI (ViewModel).
 *
 * این کلاس فقط موتورهای خالص را کنار هم می‌گذارد؛ هیچ منطق تصمیم‌گیری تازه‌ای
 * ندارد. اتصال به `KonkoorViewModel` و صفحه‌ها کار والد است.
 *
 * همه ورودی‌های شواهد از بیرون می‌آیند (از `LocalKonkoorStore` و رکوردهای
 * `AdaptiveLocalStore`) تا موتور بدون Android هم قابل تست بماند.
 */
class AdaptiveEngine(private val store: AdaptiveLocalStore) {

    /** وضعیت یک مبحث از همه شواهد موجود. */
    fun topicStatus(
        topicId: String,
        lesson: LessonEvidence?,
        prerequisiteStatus: TopicStatus?,
        miniCheckCorrect: Boolean,
        miniCheckAttempted: Boolean
    ): TopicStatusResult {
        val attempts = store.examAttempts.value
            .flatMap { it.questions }
            .filter { it.topicId == topicId }
        val reviewState = store.reviewCards.value.firstOrNull { it.topicId == topicId }
        return TopicStatusEngine.resolveTopicStatus(
            topicId = topicId,
            lesson = lesson,
            attempts = attempts,
            reviewState = reviewState,
            prerequisiteStatus = prerequisiteStatus,
            miniCheckCorrect = miniCheckCorrect,
            miniCheckAttempted = miniCheckAttempted
        )
    }

    /** ثبت نتیجه یک آزمون کامل + به‌روزرسانی کارت‌های مرور مباحث آن. */
    fun recordExamResult(attempt: ExamAttempt, todayEpochDay: Long = StreakCalculator.todayEpochDay()) {
        store.recordExamAttempt(attempt)
        // هر مبحث آزمون، کارت مرورش به‌روز می‌شود: درست → جلو، غلط → جعبه ۱.
        val byTopic = attempt.questions.groupBy { it.topicId }
        for ((topicId, questions) in byTopic) {
            val first = questions.first()
            val existing = store.reviewCards.value.firstOrNull { it.topicId == topicId }
            val card = existing ?: ReviewScheduler.newCard(
                itemId = topicId,
                topicId = topicId,
                subjectId = first.subjectId,
                learnedEpochDay = todayEpochDay
            )
            val allCorrect = questions.all { it.isCorrect }
            store.saveReviewCard(ReviewScheduler.applyReview(card, allCorrect, todayEpochDay))
        }
    }

    /** ثبت یک تلاش تکی (مینی‌چک / تمرین) و به‌روزرسانی کارت مرور مبحثش. */
    fun recordQuestionAttempt(attempt: QuestionAttempt, todayEpochDay: Long = StreakCalculator.todayEpochDay()) {
        store.recordQuestionAttempt(attempt)
        val existing = store.reviewCards.value.firstOrNull { it.topicId == attempt.topicId }
        val card = existing ?: ReviewScheduler.newCard(
            itemId = attempt.topicId,
            topicId = attempt.topicId,
            subjectId = attempt.subjectId,
            learnedEpochDay = todayEpochDay
        )
        store.saveReviewCard(ReviewScheduler.applyReview(card, attempt.isCorrect, todayEpochDay))
    }

    /** صف مرور امروز (درهم‌آمیخته، اشتباه‌های پرتکرار اول). */
    fun todaysReviewQueue(maxItems: Int, todayEpochDay: Long = StreakCalculator.todayEpochDay()): List<ReviewCardState> =
        ReviewScheduler.buildReviewQueue(store.reviewCards.value, todayEpochDay, maxItems)

    /** تحلیل یک آزمون. */
    fun analyzeExam(
        attempt: ExamAttempt,
        previousAttempts: List<ExamAttempt> = emptyList(),
        prerequisiteWeakTopics: Set<String> = emptySet(),
        combinedQuestionIds: Set<String> = emptySet()
    ): ExamAnalyzer.ExamAnalysis =
        ExamAnalyzer.analyze(attempt, previousAttempts, prerequisiteWeakTopics, combinedQuestionIds)

    /** برنامه روز. */
    fun buildDailyPlan(input: AdaptivePlanner.PlanInput): DailyPlan =
        AdaptivePlanner.buildDailyPlan(input)

    /** بازسازی برنامه بعد از روزهای ازدست‌رفته. */
    fun replanAfterMissedDays(
        missedItems: List<DailyPlanItem>,
        availableMinutes: Int,
        todayEpochDay: Long = StreakCalculator.todayEpochDay()
    ): AdaptivePlanner.ReplannedDay =
        AdaptivePlanner.replanAfterMissedDays(missedItems, availableMinutes, todayEpochDay)

    /** گزارش پیشرفت. */
    fun progressReport(input: ProgressReport.ReportInput): ProgressReport.Report =
        ProgressReport.build(input)

    /** گونه توضیح بعدی برای قدمی که فهمیده نشده است. */
    fun nextExplanationVariant(alreadyTried: List<ExplanationVariant>): ExplanationVariant? =
        TutorEngine.nextVariant(alreadyTried)

    /** سؤال تشخیصی بعدی برای یک مبحث. */
    fun nextDiagnosticQuestion(
        bank: List<DiagnosticEngine.DiagnosticQuestionItem>,
        answersSoFar: List<QuestionAttempt>
    ): DiagnosticEngine.DiagnosticQuestionItem? =
        DiagnosticEngine.nextQuestion(bank, answersSoFar)

    /** نتیجه جلسه تشخیصی. */
    fun concludeDiagnostic(
        topicId: String,
        subjectId: String,
        answers: List<QuestionAttempt>,
        bank: List<DiagnosticEngine.DiagnosticQuestionItem>,
        lessonId: String
    ): DiagnosticEngine.DiagnosticResult =
        DiagnosticEngine.conclude(topicId, subjectId, answers, bank, lessonId)

    /** ثبت نتیجه تشخیصی. */
    fun recordDiagnosticSession(session: DiagnosticSession) {
        store.recordDiagnosticSession(session)
    }

    /** ذخیره برنامه روز. */
    fun saveDailyPlan(plan: DailyPlan) {
        store.saveDailyPlan(plan)
    }

    companion object {
        /** ساخت موتور از Context — فقط همین‌جا Context لازم است. */
        fun create(context: android.content.Context): AdaptiveEngine =
            AdaptiveEngine(AdaptiveLocalStore(context))
    }
}
