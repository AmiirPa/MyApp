package com.example.ui.screens

import com.example.data.adaptive.EvidenceLevel
import com.example.data.adaptive.ProgressReport
import com.example.data.adaptive.TopicStatus

/**
 * برچسب‌های فارسی صفحه‌های تطبیقی (§۳ و §۹ الحاقیه).
 *
 * همه متن‌ها فارسی محاوره‌ای و ساده‌اند؛ هیچ واژه انگلیسی در متن‌ها نیست
 * و هیچ مبحثی با برچسب «ضعیف» نامیده نمی‌شود — وضعیت‌ها همان برچسب‌های
 * موتور تطبیقی‌اند. هیچ‌یک از این توابع عدد نمی‌سازد: هر سنجه‌ای که داده
 * کافی نداشته باشد `null` برمی‌گرداند تا صفحه «داده کافی نیست» نشان دهد،
 * و دلیلش را از همان `ProgressMetric` می‌گیرد.
 */
object AdaptiveUiLabels {

    /** برچسب کوتاه وضعیت یک مبحث. */
    fun topicStatusLabel(status: TopicStatus): String = when (status) {
        TopicStatus.NOT_STARTED -> "آموزش‌ندیده"
        TopicStatus.LEARNING -> "در حال یادگیری"
        TopicStatus.PREREQUISITE_WEAK -> "نیازمند پیش‌نیاز"
        TopicStatus.PARTIAL -> "یادگیری ناقص"
        TopicStatus.NEEDS_REVIEW -> "نیازمند مرور"
        TopicStatus.MASTERED -> "مسلط"
        TopicStatus.STABLE -> "تسلط پایدار"
    }

    /** توضیح یک‌خطی وضعیت یک مبحث. */
    fun topicStatusDescription(status: TopicStatus): String = when (status) {
        TopicStatus.NOT_STARTED -> "این مبحث هنوز شروع نشده است."
        TopicStatus.LEARNING -> "مطالعه این مبحث شروع شده؛ گام‌به‌گام ادامه بده."
        TopicStatus.PREREQUISITE_WEAK -> "اول پایه این مبحث را کامل کن، بعد سراغ خود مبحث بیا."
        TopicStatus.PARTIAL -> "یادگیری این مبحث هنوز کامل نشده؛ قدم‌های درس را یک بار دیگر مرور کن."
        TopicStatus.NEEDS_REVIEW -> "این مبحث را قبلاً خوانده‌ای؛ یک مرور کوتاه لازم است."
        TopicStatus.MASTERED -> "این مبحث با تکرار درست پاسخ داده شده است."
        TopicStatus.STABLE -> "این مبحث چند بار در فاصله‌های زمانی درست پاسخ داده شده است."
    }

    /** برچسب سطح شواهد (چقدر داده برای قضاوت داریم). */
    fun evidenceLevelLabel(level: EvidenceLevel): String = when (level) {
        EvidenceLevel.INSUFFICIENT -> "داده کم است"
        EvidenceLevel.LOW -> "داده کم‌وبیش کافی است"
        EvidenceLevel.SUFFICIENT -> "داده کافی است"
    }

    /** ردیف‌های گزارش پیشرفت (هر سنجه یک ردیف در صفحه گزارش است). */
    enum class AdaptiveReportRow {
        STUDY_DAYS,
        COMPLETED_LESSONS,
        MASTERY_BY_SUBJECT,
        EXAM_TREND,
        FORGETTING_RATE,
        DUE_REVIEWS_TODAY,
        REPEATED_ERROR_TOPICS,
        PLAN_ADHERENCE,
        WEAKNESS_CHANGE
    }

    /** عنوان فارسی هر سنجه گزارش پیشرفت. */
    fun metricLabel(row: AdaptiveReportRow): String = when (row) {
        AdaptiveReportRow.STUDY_DAYS -> "روزهای مطالعه"
        AdaptiveReportRow.COMPLETED_LESSONS -> "درس‌های کامل‌شده"
        AdaptiveReportRow.MASTERY_BY_SUBJECT -> "تسلط به‌تفکیک درس"
        AdaptiveReportRow.EXAM_TREND -> "روند آزمون‌ها"
        AdaptiveReportRow.FORGETTING_RATE -> "نرخ فراموشی"
        AdaptiveReportRow.DUE_REVIEWS_TODAY -> "مرورهای امروز"
        AdaptiveReportRow.REPEATED_ERROR_TOPICS -> "مبحث‌های با خطای تکراری"
        AdaptiveReportRow.PLAN_ADHERENCE -> "پایبندی به برنامه"
        AdaptiveReportRow.WEAKNESS_CHANGE -> "تغییر نقاط نیازمند تمرین"
    }

    /** توضیح کوتاه هر سنجه (اولین بار که دیده می‌شود، معنی‌اش روشن باشد). */
    fun metricDescription(row: AdaptiveReportRow): String = when (row) {
        AdaptiveReportRow.STUDY_DAYS -> "چند روز واقعاً مطالعه کرده‌ای (از روزهای ثبت‌شده)."
        AdaptiveReportRow.COMPLETED_LESSONS -> "چند درس تا انتها خوانده و کامل شده‌اند."
        AdaptiveReportRow.MASTERY_BY_SUBJECT -> "در هر درس، چند مبحث به تسلط رسیده‌اند."
        AdaptiveReportRow.EXAM_TREND -> "نمره آزمون‌هایت در حال بهتر شدن است یا نه (از دو آزمون به بعد)."
        AdaptiveReportRow.FORGETTING_RATE -> "چند درصد مرورها در اولین یادآوری ناموفق بوده‌اند."
        AdaptiveReportRow.DUE_REVIEWS_TODAY -> "چند کارت مرور برای امروز سررسید شده‌اند."
        AdaptiveReportRow.REPEATED_ERROR_TOPICS -> "کدام مبحث‌ها چند بار پشت سر هم غلط پاسخ داده شده‌اند."
        AdaptiveReportRow.PLAN_ADHERENCE -> "چند درصد برنامه امروز انجام شده است."
        AdaptiveReportRow.WEAKNESS_CHANGE -> "نسبت به گذشته، مبحث‌های نیازمند تمرین بیشتر شده‌اند یا کمتر."
    }

    /**
     * مقدار یک سنجه برای نمایش؛ `null` یعنی «داده کافی نیست».
     * دلیل نبود داده را [rowInsufficientReason] می‌دهد.
     */
    fun metricValueOrInsufficient(
        report: ProgressReport.Report,
        row: AdaptiveReportRow
    ): String? = when (row) {
        AdaptiveReportRow.STUDY_DAYS -> {
            val m = report.studyDays
            if (!m.hasEnoughData || m.value == null) null else "${m.value.size} روز"
        }
        AdaptiveReportRow.COMPLETED_LESSONS -> {
            val m = report.completedLessons
            if (!m.hasEnoughData || m.value == null) null else "${m.value} درس"
        }
        AdaptiveReportRow.MASTERY_BY_SUBJECT -> {
            val m = report.masteryBySubject
            val bySubject = m.value
            if (!m.hasEnoughData || bySubject.isNullOrEmpty()) null
            else bySubject.entries
                .sortedByDescending { (_, counts) ->
                    (counts[TopicStatus.MASTERED] ?: 0) + (counts[TopicStatus.STABLE] ?: 0)
                }
                .take(5)
                .joinToString("، ") { (subject, counts) ->
                    val mastered = (counts[TopicStatus.MASTERED] ?: 0) + (counts[TopicStatus.STABLE] ?: 0)
                    val total = counts.values.sum()
                    "$subject: $mastered مسلط از $total"
                }
        }
        AdaptiveReportRow.EXAM_TREND -> {
            val m = report.examTrend
            val percents = m.value
            if (!m.hasEnoughData || percents.isNullOrEmpty()) null
            else {
                val first = (percents.first() * 100).toInt()
                val last = (percents.last() * 100).toInt()
                when {
                    last > first -> "بهتر شده (از $first٪ به $last٪)"
                    last < first -> "افت کرده (از $first٪ به $last٪)"
                    else -> "تقریباً ثابت ($last٪)"
                }
            }
        }
        AdaptiveReportRow.FORGETTING_RATE -> {
            val m = report.forgettingRate
            if (!m.hasEnoughData || m.value == null) null else "${(m.value * 100).toInt()}٪"
        }
        AdaptiveReportRow.DUE_REVIEWS_TODAY -> {
            val m = report.dueReviewsToday
            if (!m.hasEnoughData || m.value == null) null else "${m.value} کارت"
        }
        AdaptiveReportRow.REPEATED_ERROR_TOPICS -> {
            val m = report.repeatedErrorTopics
            if (!m.hasEnoughData || m.value == null) null
            else if (m.value.isEmpty()) "فعلاً موردی نیست"
            else m.value.take(5).joinToString("، ")
        }
        AdaptiveReportRow.PLAN_ADHERENCE -> {
            val m = report.planAdherence
            if (!m.hasEnoughData || m.value == null) null else "${(m.value * 100).toInt()}٪"
        }
        AdaptiveReportRow.WEAKNESS_CHANGE -> {
            val m = report.weaknessChange
            val change = m.value
            if (!m.hasEnoughData || change == null) null
            else if (change.improvedTopics.isEmpty() && change.worsenedTopics.isEmpty()) "تغییری دیده نشده"
            else "${change.improvedTopics.size} مبحث بهتر، ${change.worsenedTopics.size} مبحث نیازمند تمرین بیشتر"
        }
    }

    /** دلیل «داده کافی نیست» برای یک سنجه (همان دلیلی که موتور داده است). */
    fun rowInsufficientReason(
        report: ProgressReport.Report,
        row: AdaptiveReportRow
    ): String? = when (row) {
        AdaptiveReportRow.STUDY_DAYS -> report.studyDays.reasonIfInsufficient
        AdaptiveReportRow.COMPLETED_LESSONS -> report.completedLessons.reasonIfInsufficient
        AdaptiveReportRow.MASTERY_BY_SUBJECT -> report.masteryBySubject.reasonIfInsufficient
        AdaptiveReportRow.EXAM_TREND -> report.examTrend.reasonIfInsufficient
        AdaptiveReportRow.FORGETTING_RATE -> report.forgettingRate.reasonIfInsufficient
        AdaptiveReportRow.DUE_REVIEWS_TODAY -> report.dueReviewsToday.reasonIfInsufficient
        AdaptiveReportRow.REPEATED_ERROR_TOPICS -> report.repeatedErrorTopics.reasonIfInsufficient
        AdaptiveReportRow.PLAN_ADHERENCE -> report.planAdherence.reasonIfInsufficient
        AdaptiveReportRow.WEAKNESS_CHANGE -> report.weaknessChange.reasonIfInsufficient
    }
}
