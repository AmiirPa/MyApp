package com.example.data.adaptive

/**
 * گزارش تحلیلی پیشرفت (§۹ الحاقیه).
 *
 * همه متریک‌ها از شواهد واقعی ساخته می‌شوند و هرکدام پرچم «داده کافی نیست» دارند.
 * زمان خالص مطالعه فقط وقتی محاسبه می‌شود که تایمر جلسه ثبت شده باشد؛
 * از تعداد درس، زمان مطالعه حدس زده نمی‌شود.
 */
object ProgressReport {

    /** حداقل آزمون برای روند آزمون‌ها. */
    const val MIN_EXAMS_FOR_TREND = 2

    /** حداقل مرور برای نرخ فراموشی. */
    const val MIN_REVIEWS_FOR_FORGETTING = 3

    /** گزارش کامل پیشرفت. */
    data class Report(
        /** روزهای مطالعه ثبت‌شده (epoch day). */
        val studyDays: ProgressMetric<Set<Long>>,
        /** تعداد درس‌های تکمیل‌شده. */
        val completedLessons: ProgressMetric<Int>,
        /** تسلط هر درس: درس → وضعیت (فقط درس‌های با شواهد). */
        val masteryBySubject: ProgressMetric<Map<String, Map<TopicStatus, Int>>>,
        /** روند آزمون‌ها: درصد درست هر آزمون به ترتیب زمانی. */
        val examTrend: ProgressMetric<List<Double>>,
        /** نرخ فراموشی: مرورهای ناموفق / کل مرورها. */
        val forgettingRate: ProgressMetric<Double>,
        /** کارت‌های سررسید مرور امروز. */
        val dueReviewsToday: ProgressMetric<Int>,
        /** مباحث با اشتباه پرتکرار (errorCount ≥ ۲). */
        val repeatedErrorTopics: ProgressMetric<List<String>>,
        /** پایبندی به برنامه روز. */
        val planAdherence: ProgressMetric<Double>,
        /** تغییر نقاط ضعف: مباحثی که وضعیتشان از بازه قبل بهتر/بدتر شده است. */
        val weaknessChange: ProgressMetric<WeaknessChange>
    )

    data class WeaknessChange(
        val improvedTopics: List<String>,
        val worsenedTopics: List<String>
    )

    /** ورودی ساخت گزارش — همه از شواهد واقعی. */
    data class ReportInput(
        val completedDays: Set<Long>,
        val lessonEvidence: List<LessonEvidence>,
        /** وضعیت همه مباحث با شواهد؛ کلید = شناسه مبحث. */
        val topicStatuses: Map<String, TopicStatusResult>,
        /** آزمون‌ها به ترتیب زمانی (قدیمی به جدید). */
        val examAttempts: List<ExamAttempt>,
        val reviewCards: List<ReviewCardState>,
        val todayEpochDay: Long,
        /** برنامه امروز (اگر ساخته شده باشد) + درس‌های انجام‌شده امروز. */
        val todaysPlan: DailyPlan?,
        val completedLessonIdsToday: Set<String>,
        /** دقیقه‌های خالص مطالعه ثبت‌شده با تایمر؛ `null` یعنی تایمر وصل نیست. */
        val recordedFocusMinutesToday: Int?,
        /** وضعیت مباحث در بازه قبل (برای تغییر نقاط ضعف)؛ خالی یعنی سابقه نیست. */
        val previousTopicStatuses: Map<String, TopicStatus> = emptyMap()
    )

    fun build(input: ReportInput): Report {
        // روزهای مطالعه — از داده واقعی؛ خالی بودن یعنی «هنوز روزی ثبت نشده»، نه صفر ساختگی.
        val studyDays = ProgressMetric(
            value = input.completedDays,
            hasEnoughData = input.completedDays.isNotEmpty(),
            reasonIfInsufficient = if (input.completedDays.isEmpty()) "هنوز روزی به‌عنوان روز مطالعه ثبت نشده است." else null
        )

        val completed = input.lessonEvidence.count { it.isCompleted }
        val completedLessons = ProgressMetric(
            value = completed,
            hasEnoughData = input.lessonEvidence.isNotEmpty(),
            reasonIfInsufficient = if (input.lessonEvidence.isEmpty()) "هنوز هیچ درسی لمس نشده است." else null
        )

        val masteryBySubject = if (input.topicStatuses.isEmpty()) {
            ProgressMetric<Map<String, Map<TopicStatus, Int>>>(
                value = null,
                hasEnoughData = false,
                reasonIfInsufficient = "هنوز هیچ مبحثی شواهد کافی برای تعیین وضعیت ندارد."
            )
        } else {
            // گروه‌بندی وضعیت‌ها بر اساس درس (پیشوند شناسه مبحث تا خط تیره اول = درس نیست؛
            // پس موضوع را از روی شناسه درسِ LessonEvidence می‌گیریم و وضعیت مبحث را با آن جفت می‌کنیم).
            val bySubject = HashMap<String, MutableMap<TopicStatus, Int>>()
            for ((topicId, result) in input.topicStatuses) {
                val subject = input.lessonEvidence.firstOrNull { it.lessonId == topicId }?.subjectId
                    ?: topicId.substringBefore("-")
                val counts = bySubject.getOrPut(subject) { HashMap() }
                counts[result.status] = (counts[result.status] ?: 0) + 1
            }
            ProgressMetric<Map<String, Map<TopicStatus, Int>>>(
                value = bySubject,
                hasEnoughData = true
            )
        }

        val examTrend = if (input.examAttempts.size >= MIN_EXAMS_FOR_TREND) {
            ProgressMetric(
                value = input.examAttempts.map { attempt ->
                    val summary = ExamAnalyzer.summarize(attempt)
                    summary.correctPercent
                },
                hasEnoughData = true
            )
        } else {
            ProgressMetric<List<Double>>(
                value = null,
                hasEnoughData = false,
                reasonIfInsufficient = "برای روند آزمون‌ها حداقل $MIN_EXAMS_FOR_TREND آزمون لازم است؛ فعلاً ${input.examAttempts.size} آزمون ثبت شده است."
            )
        }

        val totalReviews = input.reviewCards.sumOf { it.totalReviews }
        val failedReviews = input.reviewCards.sumOf { it.errorCount }
        val forgettingRate = if (totalReviews >= MIN_REVIEWS_FOR_FORGETTING) {
            ProgressMetric(
                value = failedReviews.toDouble() / totalReviews.toDouble(),
                hasEnoughData = true
            )
        } else {
            ProgressMetric<Double>(
                value = null,
                hasEnoughData = false,
                reasonIfInsufficient = "برای نرخ فراموشی حداقل $MIN_REVIEWS_FOR_FORGETTING مرور لازم است؛ فعلاً $totalReviews مرور ثبت شده است."
            )
        }

        val dueToday = ReviewScheduler.dueCards(input.reviewCards, input.todayEpochDay).size
        val dueReviewsToday = ProgressMetric(
            value = dueToday,
            hasEnoughData = input.reviewCards.isNotEmpty(),
            reasonIfInsufficient = if (input.reviewCards.isEmpty()) "هنوز کارتی وارد چرخه مرور نشده است." else null
        )

        val repeated = input.reviewCards
            .filter { it.errorCount >= 2 }
            .map { it.topicId }
            .distinct()
        val repeatedErrorTopics = ProgressMetric(
            value = repeated,
            hasEnoughData = input.reviewCards.isNotEmpty(),
            reasonIfInsufficient = if (input.reviewCards.isEmpty()) "هنوز سابقه مرور وجود ندارد." else null
        )

        val adherenceValue = AdaptivePlanner.planAdherence(input.todaysPlan, input.completedLessonIdsToday)
        val planAdherence = ProgressMetric(
            value = adherenceValue,
            hasEnoughData = adherenceValue != null,
            reasonIfInsufficient = if (adherenceValue == null) "برای امروز برنامه‌ای ساخته نشده است." else null
        )

        val weaknessChange = if (input.previousTopicStatuses.isEmpty() || input.topicStatuses.isEmpty()) {
            ProgressMetric<WeaknessChange>(
                value = null,
                hasEnoughData = false,
                reasonIfInsufficient = "برای تغییر نقاط ضعف، وضعیت مباحث در دو بازه لازم است."
            )
        } else {
            val improved = ArrayList<String>()
            val worsened = ArrayList<String>()
            val rank = mapOf(
                TopicStatus.NOT_STARTED to 0,
                TopicStatus.PREREQUISITE_WEAK to 1,
                TopicStatus.LEARNING to 2,
                TopicStatus.PARTIAL to 3,
                TopicStatus.NEEDS_REVIEW to 4,
                TopicStatus.MASTERED to 5,
                TopicStatus.STABLE to 6
            )
            for ((topicId, result) in input.topicStatuses) {
                val prev = input.previousTopicStatuses[topicId] ?: continue
                val nowRank = rank[result.status] ?: 0
                val prevRank = rank[prev] ?: 0
                if (nowRank > prevRank) improved.add(topicId)
                if (nowRank < prevRank) worsened.add(topicId)
            }
            ProgressMetric(value = WeaknessChange(improved, worsened), hasEnoughData = true)
        }

        return Report(
            studyDays = studyDays,
            completedLessons = completedLessons,
            masteryBySubject = masteryBySubject,
            examTrend = examTrend,
            forgettingRate = forgettingRate,
            dueReviewsToday = dueReviewsToday,
            repeatedErrorTopics = repeatedErrorTopics,
            planAdherence = planAdherence,
            weaknessChange = weaknessChange
        )
    }
}
