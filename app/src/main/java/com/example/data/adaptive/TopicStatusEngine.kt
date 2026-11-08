package com.example.data.adaptive

/**
 * محاسبه وضعیت هر مبحث از شواهد واقعی (§۳ الحاقیه).
 *
 * قوانین (از سند طراحی):
 * - ارتقا فقط با تکرار شواهد است؛ یک جواب درست وضعیت را بالا نمی‌برد.
 * - تنزل با یک جواب غلط انجام نمی‌شود؛ حداقل ۲ تلاش غلط متوالی یا یک مرور ناموفق لازم است.
 * - جواب درستِ «شانسی» (خیلی سریع + بدون سابقه) در ارتقا شمرده نمی‌شود.
 * - همه توابع خالص‌اند؛ هیچ وابستگی به Android یا زمان واقعی ندارند.
 */
object TopicStatusEngine {

    /** حداقل تلاش برای ارتقا به MASTERED. */
    const val MIN_ATTEMPTS_FOR_MASTERY = 2

    /** حداقل تلاش برای محاسبه عملکرد درصدی یک مبحث. */
    const val MIN_ATTEMPTS_FOR_RATE = 2

    /** آستانه تسلط موضوعی در آزمون (۸۰٪). */
    const val MASTERY_RATE_THRESHOLD = 0.80

    /** آستانه یادگیری ناقص (زیر ۵۰٪). */
    const val PARTIAL_RATE_THRESHOLD = 0.50

    /** جواب درست زیر این زمان (ثانیه) با سابقه صفر، «شانسی» شمرده می‌شود. */
    const val LUCKY_CORRECT_MAX_SECONDS = 5

    /** تعداد تلاش متوالی غلط برای تنزل. */
    const val CONSECUTIVE_WRONG_FOR_DEMOTION = 2

    /** تعداد مرور فاصله‌دارِ درست برای تسلط پایدار. */
    const val SPACED_REVIEWS_FOR_STABLE = 2

    /**
     * تشخیص جواب «شانسی»: درست، خیلی سریع، و بدون هیچ سابقه قبلی روی مبحث.
     * چنین جوابی در ارتقا شمرده نمی‌شود و علت CORRECT_WITHOUT_MASTERY می‌خورد.
     */
    fun isLuckyCorrect(attempt: QuestionAttempt, priorAttemptsOnTopic: Int): Boolean {
        return attempt.isCorrect &&
            !attempt.wasSkipped &&
            attempt.timeSpentSeconds <= LUCKY_CORRECT_MAX_SECONDS &&
            priorAttemptsOnTopic == 0
    }

    /** سطح شواهد از روی تعداد تلاش‌های مرتبط. */
    fun evidenceLevel(attemptCount: Int): EvidenceLevel = when {
        attemptCount < 3 -> EvidenceLevel.INSUFFICIENT
        attemptCount <= 4 -> EvidenceLevel.LOW
        else -> EvidenceLevel.SUFFICIENT
    }

    /**
     * وضعیت یک مبحث را از همه شواهد موجود محاسبه می‌کند.
     *
     * @param topicId شناسه مبحث (شناسه درس).
     * @param lesson شواهد درس از ذخیره‌ساز موجود؛ `null` یعنی درس هنوز لمس نشده است.
     * @param attempts همه تلاش‌های ثبت‌شده روی این مبحث (تشخیصی، مینی‌چک، آزمون)، به ترتیب زمانی.
     * @param reviewState وضعیت مرور مبحث؛ `null` یعنی هنوز وارد چرخه مرور نشده است.
     * @param prerequisiteStatus وضعیت پیش‌نیاز مستقیم مبحث؛ `null` یعنی پیش‌نیاز ندارد یا بررسی نشده است.
     * @param miniCheckCorrect مینی‌چک انتهای درس درست پاسخ داده شده است (اگر اصلاً تلاش شده باشد).
     * @param miniCheckAttempted مینی‌چک انتهای درس اصلاً تلاش شده است.
     */
    fun resolveTopicStatus(
        topicId: String,
        lesson: LessonEvidence?,
        attempts: List<QuestionAttempt>,
        reviewState: ReviewCardState?,
        prerequisiteStatus: TopicStatus?,
        miniCheckCorrect: Boolean,
        miniCheckAttempted: Boolean
    ): TopicStatusResult {
        val meaningfulAttempts = attempts.filterNot { it.wasSkipped }
        val correctAttempts = meaningfulAttempts.filter { it.isCorrect }
        // جواب‌های شانسی از شمارش ارتقا حذف می‌شوند.
        val countedCorrect = correctAttempts.filterIndexed { index, attempt ->
            !isLuckyCorrect(attempt, index)
        }
        val level = evidenceLevel(meaningfulAttempts.size)
        val topicRate = topicSuccessRate(meaningfulAttempts)
        val recentWrongStreak = meaningfulAttempts
            .takeLastWhile { !it.isCorrect }
            .size

        // شواهد تسلط: یا عملکرد ≥۸۰٪ با حداقل ۲ درستِ شمرده‌شده، یا حداقل ۳ درستِ
        // شمرده‌شده بدون ۲ غلط متوالی (یک لغزش تکی، تسلط را از بین نمی‌برد).
        val masteryEvidence = (
            topicRate != null && topicRate >= MASTERY_RATE_THRESHOLD &&
                countedCorrect.size >= MIN_ATTEMPTS_FOR_MASTERY
            ) || (
            countedCorrect.size >= 3 &&
                recentWrongStreak < CONSECUTIVE_WRONG_FOR_DEMOTION
            )

        // ۱. ضعف پیش‌نیاز بر همه‌چیز مقدم است — ولی فقط وقتی شواهدش هست.
        val prerequisiteWrongStreak = prerequisiteAttemptsWrong(attempts)
        if (prerequisiteStatus == TopicStatus.PREREQUISITE_WEAK ||
            prerequisiteStatus == TopicStatus.NOT_STARTED && prerequisiteWrongStreak
        ) {
            return TopicStatusResult(
                TopicStatus.PREREQUISITE_WEAK,
                level,
                "پیش‌نیاز این مبحث هنوز کامل نشده؛ اول آن را مرور کن، بعد سراغ این مبحث بیا."
            )
        }

        // ۲. تسلط پایدار: مسلط + یادآوری درست در مرورهای فاصله‌دار.
        val spacedCorrect = reviewState?.consecutiveCorrect ?: 0
        if (lesson?.isCompleted == true && miniCheckAttempted && miniCheckCorrect &&
            masteryEvidence &&
            spacedCorrect >= SPACED_REVIEWS_FOR_STABLE
        ) {
            return TopicStatusResult(
                TopicStatus.STABLE,
                level,
                "تسلط پایدار: این مبحث را چند بار در فاصله‌های زمانی درست پاسخ داده‌ای."
            )
        }

        // ۳. نیازمند مرور: شواهد تسلط هست ولی آخرین یادآوری فاصله‌دار ناموفق بوده است.
        // (قبل از MASTERED بررسی می‌شود تا یک مرور ناموفق، تسلط را پنهان نکند.)
        if (masteryEvidence && reviewState != null && reviewState.totalReviews > 0 &&
            reviewState.consecutiveCorrect == 0 && reviewState.errorCount > 0 &&
            lesson?.isCompleted == true
        ) {
            return TopicStatusResult(
                TopicStatus.NEEDS_REVIEW,
                level,
                "این مبحث را قبلاً خوانده‌ای؛ یادآوری آخر موفق نبود، یک مرور کوتاه لازم است."
            )
        }

        // ۴. مسلط: درس کامل + مینی‌چک درست + شواهد تسلط (تکرار درست).
        if (lesson?.isCompleted == true && miniCheckAttempted && miniCheckCorrect &&
            masteryEvidence
        ) {
            return TopicStatusResult(
                TopicStatus.MASTERED,
                level,
                "مسلط: درس کامل شده و سؤال‌ها با تکرار درست پاسخ داده شده‌اند."
            )
        }

        // ۵. یادگیری ناقص: درس کامل ولی مینی‌چک غلط، یا عملکرد زیر ۵۰٪ با حداقل ۲ تلاش.
        if ((lesson?.isCompleted == true && miniCheckAttempted && !miniCheckCorrect) ||
            (topicRate != null && topicRate < PARTIAL_RATE_THRESHOLD &&
                meaningfulAttempts.size >= MIN_ATTEMPTS_FOR_RATE) ||
            recentWrongStreak >= CONSECUTIVE_WRONG_FOR_DEMOTION
        ) {
            return TopicStatusResult(
                TopicStatus.PARTIAL,
                level,
                "یادگیری این مبحث هنوز کامل نشده؛ قدم‌های درس را یک بار دیگر با مثال حل‌شده مرور کن."
            )
        }

        // ۶. در حال یادگیری: درس شروع شده یا تلاش‌هایی هست ولی هنوز کامل نشده است.
        if (lesson != null && (lesson.isCompleted || lesson.masteryPercent > 0) ||
            meaningfulAttempts.isNotEmpty()
        ) {
            return TopicStatusResult(
                TopicStatus.LEARNING,
                level,
                "مطالعه این مبحث شروع شده؛ گام‌به‌گام ادامه بده."
            )
        }

        // ۷. هیچ شواهدی نیست.
        return TopicStatusResult(
            TopicStatus.NOT_STARTED,
            EvidenceLevel.INSUFFICIENT,
            "این مبحث هنوز شروع نشده است؛ از قدم اول و با کمک دستیار «منفی صفر» شروع کن."
        )
    }

    /** نرخ موفقیت مبحث؛ بدون تلاش معنادار `null` است. */
    private fun topicSuccessRate(attempts: List<QuestionAttempt>): Double? {
        if (attempts.isEmpty()) return null
        return attempts.count { it.isCorrect }.toDouble() / attempts.size.toDouble()
    }

    /** آیا در تلاش‌های پیش‌نیاز، غلطی هست؟ (برای تشخیص ضعف پیش‌نیاز بدون وضعیت جداگانه.) */
    private fun prerequisiteAttemptsWrong(attempts: List<QuestionAttempt>): Boolean =
        attempts.any { it.level == QuestionLevel.FOUNDATION && !it.isCorrect }
}