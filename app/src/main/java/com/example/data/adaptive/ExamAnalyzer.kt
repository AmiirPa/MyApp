package com.example.data.adaptive

/**
 * تحلیل آزمون و تست‌زنی (§۶ الحاقیه).
 *
 * همه توابع خالص‌اند و از یک [ExamAttempt] (رکورد هر سؤال با زمانش) محاسبه می‌شوند.
 * هیچ عددی از روی حدس ساخته نمی‌شود: هر متریکی که شواهد کافی ندارد `null`
 * برمی‌گردد و UI باید «داده کافی نیست» نشان بدهد.
 */
object ExamAnalyzer {

    /** حداقل سؤال برای گزارش عملکرد یک مبحث. */
    const val MIN_QUESTIONS_PER_TOPIC = 2

    /** حداقل آزمون برای محاسبه تغییر نسبت به قبل. */
    const val MIN_ATTEMPTS_FOR_DELTA = 2

    /** سؤال درست زیر این زمان (ثانیه) مشکوک به حدس است. */
    const val SUSPICIOUS_FAST_SECONDS = 5

    /** ضریب میانه زمان برای «دشوار» و «خیلی سریع». */
    const val SLOW_MULTIPLIER = 1.5
    const val FAST_FRACTION = 0.25

    /** خلاصه یک آزمون. */
    data class ExamSummary(
        val correctPercent: Double,
        val wrongPercent: Double,
        val skippedPercent: Double,
        val correctCount: Int,
        val wrongCount: Int,
        val skippedCount: Int,
        val totalCount: Int,
        val totalSeconds: Int,
        /** میانگین زمان هر سؤال (ثانیه)؛ بدون سؤال `null` است. */
        val averageSecondsPerQuestion: Double?,
        /** میانه زمان سؤال‌های پاسخ‌داده‌شده؛ مبنای «دشوار/مشکوک». */
        val medianSecondsPerQuestion: Double?,
        /** عملکرد هر درس: درس → درصد درست (فقط مباحث با حداقل ۲ سؤال). */
        val perSubjectPercent: Map<String, Double>,
        /** عملکرد هر مبحث: مبحث → درصد درست (فقط مباحث با حداقل ۲ سؤال). */
        val perTopicPercent: Map<String, Double>,
        /** ضعیف‌ترین مباحث: حداقل ۲ تلاش و زیر ۵۰٪. */
        val weakestTopics: List<String>,
        /** سؤال‌های دشوار: غلط + زمان بیشتر از ۱٫۵ برابر میانه. */
        val difficultQuestions: List<QuestionAttempt>,
        /** سؤال‌های مشکوک: درست + خیلی سریع (احتمال حدس). */
        val suspiciousQuestions: List<QuestionAttempt>
    )

    /** تحلیل یک پاسخ غلط — برای نمایش «چرا اشتباه شد و چه تمرینی لازم است». */
    data class WrongAnswerAnalysis(
        val attempt: QuestionAttempt,
        /** علت خوداظهاری کاربر؛ اگر نیست، حدس موتور (با برچسب حدس). */
        val cause: ErrorCause?,
        /** آیا این علت حدس موتور است (نه خوداظهاری)؟ */
        val causeIsInferred: Boolean,
        /** تمرین پیشنهادی: شناسه درس (پیش‌نیاز / خود درس / تمرین ترکیبی). */
        val recommendedLessonId: String?,
        /** توضیح سازنده برای دانش‌آموز. */
        val guidance: String
    )

    /** تحلیل کامل یک آزمون + مقایسه با آزمون قبلی (اگر هست). */
    data class ExamAnalysis(
        val summary: ExamSummary,
        val wrongAnswers: List<WrongAnswerAnalysis>,
        /** تغییر درصد درست نسبت به آزمون قبلی همان درس؛ بدون سابقه `null` است. */
        val deltaPercentVsPrevious: Double?
    )

    /** میانه یک لیست عددی؛ لیست خالی → `null`. */
    fun median(values: List<Int>): Double? {
        if (values.isEmpty()) return null
        val sorted = values.sorted()
        val mid = sorted.size / 2
        return if (sorted.size % 2 == 1) sorted[mid].toDouble()
        else (sorted[mid - 1] + sorted[mid]).toDouble() / 2.0
    }

    /** خلاصه یک آزمون. */
    fun summarize(attempt: ExamAttempt): ExamSummary {
        val questions = attempt.questions
        val total = questions.size
        val correct = questions.count { it.isCorrect && !it.wasSkipped }
        val skipped = questions.count { it.wasSkipped }
        val wrong = total - correct - skipped
        val answeredTimes = questions.filterNot { it.wasSkipped }.map { it.timeSpentSeconds }
        val medianTime = median(answeredTimes)

        fun rateFor(group: List<QuestionAttempt>): Double? {
            if (group.size < MIN_QUESTIONS_PER_TOPIC) return null
            val answered = group.filterNot { it.wasSkipped }
            if (answered.isEmpty()) return null
            return answered.count { it.isCorrect }.toDouble() / answered.size.toDouble() * 100.0
        }

        val perSubject = questions.groupBy { it.subjectId }
            .mapNotNull { (subject, qs) -> rateFor(qs)?.let { subject to it } }
            .toMap()
        val perTopic = questions.groupBy { it.topicId }
            .mapNotNull { (topic, qs) -> rateFor(qs)?.let { topic to it } }
            .toMap()
        val weakest = perTopic.filter { it.value < 50.0 }.keys.toList()

        val difficult = if (medianTime != null) {
            questions.filter { !it.isCorrect && !it.wasSkipped && it.timeSpentSeconds > medianTime * SLOW_MULTIPLIER }
        } else emptyList()
        val suspicious = if (medianTime != null) {
            questions.filter {
                it.isCorrect && !it.wasSkipped &&
                    (it.timeSpentSeconds <= SUSPICIOUS_FAST_SECONDS ||
                        it.timeSpentSeconds < medianTime * FAST_FRACTION)
            }
        } else {
            questions.filter { it.isCorrect && !it.wasSkipped && it.timeSpentSeconds <= SUSPICIOUS_FAST_SECONDS }
        }

        return ExamSummary(
            correctPercent = if (total > 0) correct.toDouble() / total * 100.0 else 0.0,
            wrongPercent = if (total > 0) wrong.toDouble() / total * 100.0 else 0.0,
            skippedPercent = if (total > 0) skipped.toDouble() / total * 100.0 else 0.0,
            correctCount = correct,
            wrongCount = wrong,
            skippedCount = skipped,
            totalCount = total,
            totalSeconds = attempt.totalSeconds,
            averageSecondsPerQuestion = if (total > 0) attempt.totalSeconds.toDouble() / total else null,
            medianSecondsPerQuestion = medianTime,
            perSubjectPercent = perSubject,
            perTopicPercent = perTopic,
            weakestTopics = weakest,
            difficultQuestions = difficult,
            suspiciousQuestions = suspicious
        )
    }

    /**
     * حدس علت اشتباه — فقط حدس است، هرگز قطعی نیست.
     * @param prerequisiteWeak آیا پیش‌نیاز مبحث ضعیف است (از موتور وضعیت).
     * @param isCombinedQuestion آیا سؤال ترکیبی دو مبحث است.
     * @return علت حدس‌زده‌شده، یا `null` اگر حدسی نمی‌توان زد.
     */
    fun inferErrorCause(
        attempt: QuestionAttempt,
        medianSeconds: Double?,
        prerequisiteWeak: Boolean,
        isCombinedQuestion: Boolean
    ): ErrorCause? {
        // نزده = وقت نرسیده است.
        if (attempt.wasSkipped) return ErrorCause.TIME_MANAGEMENT
        // درست ولی خیلی سریع = پاسخ صحیح بدون تسلط واقعی.
        if (attempt.isCorrect) {
            return if (attempt.timeSpentSeconds <= SUSPICIOUS_FAST_SECONDS) {
                ErrorCause.CORRECT_WITHOUT_MASTERY
            } else null
        }
        if (isCombinedQuestion) return ErrorCause.CANNOT_COMBINE
        if (medianSeconds != null) {
            if (attempt.timeSpentSeconds < medianSeconds * FAST_FRACTION) return ErrorCause.CARELESSNESS
            if (attempt.timeSpentSeconds > medianSeconds * SLOW_MULTIPLIER) {
                return if (prerequisiteWeak) ErrorCause.PREREQUISITE_WEAK else ErrorCause.REASONING_WEAK
            }
        }
        return null
    }

    /** تحلیل همه پاسخ‌های غلط یک آزمون. */
    fun analyzeWrongAnswers(
        attempt: ExamAttempt,
        summary: ExamSummary,
        prerequisiteWeakTopics: Set<String>,
        combinedQuestionIds: Set<String> = emptySet()
    ): List<WrongAnswerAnalysis> {
        return attempt.questions.filter { !it.isCorrect && !it.wasSkipped }.map { q ->
            val selfCause = q.selfReportedCause
            val inferred = selfCause
                ?: inferErrorCause(
                    q,
                    summary.medianSecondsPerQuestion,
                    prerequisiteWeakTopics.contains(q.topicId),
                    combinedQuestionIds.contains(q.questionId)
                )
            val recommended = when (inferred) {
                ErrorCause.PREREQUISITE_WEAK -> "prerequisite:${q.topicId}"
                ErrorCause.CANNOT_COMBINE -> "combined:${q.topicId}"
                ErrorCause.CALCULATION, ErrorCause.CARELESSNESS -> "practice:${q.topicId}"
                else -> q.topicId
            }
            val guidance = when (inferred) {
                ErrorCause.PREREQUISITE_WEAK ->
                    "این سؤال به پیش‌نیاز برمی‌گردد؛ اول درس پایه آن را مرور کن، بعد دوباره به این مبحث برگرد."
                ErrorCause.CANNOT_COMBINE ->
                    "سؤال ترکیبی بود؛ هر مبحث را جدا مرور کن، بعد تمرین ترکیبی حل کن."
                ErrorCause.CARELESSNESS ->
                    "پاسخ سریع داده شده؛ دفعه بعد اول صورت سؤال را کامل بخوان، بعد گزینه‌ها را ببین."
                ErrorCause.TIME_MANAGEMENT ->
                    "وقت برای این سؤال نرسید؛ در تمرین بعدی زمان هر سؤال را جداگانه مدیریت کن."
                ErrorCause.REASONING_WEAK ->
                    "استدلال سؤال جا افتاده؛ راه‌حل مرحله‌به‌مرحله درس را یک بار دیگر با مثال حل‌شده بخوان."
                null ->
                    "علت این اشتباه مشخص نیست؛ درس را یک بار دیگر بخوان و اگر باز هم اشتباه شد، علت را خودت ثبت کن."
                else ->
                    "این مبحث را با تمرین بیشتر تثبیت کن؛ مینی‌چک انتهای درس را دوباره حل کن."
            }
            WrongAnswerAnalysis(
                attempt = q,
                cause = inferred,
                causeIsInferred = selfCause == null && inferred != null,
                recommendedLessonId = recommended,
                guidance = guidance
            )
        }
    }

    /**
     * تحلیل کامل آزمون + مقایسه با آزمون قبلی همان درس.
     * @param previousAttempts آزمون‌های قبلی همان درس، به ترتیب زمانی (قدیمی به جدید).
     */
    fun analyze(
        attempt: ExamAttempt,
        previousAttempts: List<ExamAttempt> = emptyList(),
        prerequisiteWeakTopics: Set<String> = emptySet(),
        combinedQuestionIds: Set<String> = emptySet()
    ): ExamAnalysis {
        val summary = summarize(attempt)
        val wrong = analyzeWrongAnswers(attempt, summary, prerequisiteWeakTopics, combinedQuestionIds)
        val delta = if (previousAttempts.isNotEmpty()) {
            // تغییر نسبت به آزمون قبلی: حداقل ۲ آزمون لازم است (فعلی + یک قبلی).
            val previous = previousAttempts.last()
            val prevSummary = summarize(previous)
            summary.correctPercent - prevSummary.correctPercent
        } else null
        return ExamAnalysis(summary, wrong, delta)
    }
}
