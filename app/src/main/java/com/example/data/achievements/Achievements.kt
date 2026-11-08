package com.example.data.achievements

import com.example.data.adaptive.DailyPlan
import com.example.data.adaptive.DiagnosticSession
import com.example.data.adaptive.ExamAttempt
import com.example.data.adaptive.ReviewCardState
import com.example.data.focus.FocusSession
import com.example.util.StreakCalculator

/**
 * §۱۰ الحاقیه — دستاوردهای سالم (Gamification بدون فریب).
 *
 * قوانین سخت‌گیرانه:
 * - هر دستاورد فقط از شواهدی که برنامه از قبل ذخیره می‌کند به دست می‌آید
 *   (درس کامل‌شده، روز مطالعه، آزمون، جلسه تشخیص، جلسه تمرکز، کارت مرور،
 *   برنامه روز). هیچ دستاوردی برای «فقط باز کردن برنامه» نیست.
 * - امتیاز، سکه، سطح و هیچ عدد ساختگی دیگری وجود ندارد.
 * - وضعیت «گرفته شده» هر بار از شواهد محاسبه می‌شود و جداگانه ذخیره
 *   نمی‌شود — پس دستاورد بدونِ شاهدش هرگز وجود ندارد. اگر شواهد پاک شوند،
 *   دستاورد هم می‌رود؛ این عیب نیست، عین صداقت است.
 * - دستاوردِ «رشته روزها» به رشته‌ی فعلی اشاره دارد (همان رشته‌ای که برنامه
 *   نشان می‌دهد)؛ اگر رشته قطع شود، دستاورد تا وقتی رشته دوباره ساخته شود
 *   برمی‌گردد. هیچ متنی کاربر را بابت عقب‌افتادگی سرزنش نمی‌کند و هیچ
 *   مقایسه‌ای با کاربر دیگر نیست.
 * - همه متن‌ها فارسی ساده و گرم‌اند؛ رقم‌ها لاتین برمی‌گردند و UI آن‌ها را
 *   به رقم فارسی تبدیل می‌کند (مثل کارت تمرکز).
 */

/** نوع دستاورد — هر نوع دقیقاً یک قاعده اندازه‌گیری دارد. */
enum class AchievementKind {
    FIRST_LESSON,
    TEN_LESSONS,
    FIRST_DIAGNOSTIC,
    FIRST_EXAM,
    FIRST_FOCUS,
    STREAK_7,
    STREAK_30,
    REVIEWS_100,
    FIRST_PLAN_DONE,
    FOCUS_300
}

/** تعریف یک دستاورد: شناسه پایدار، عنوان، توضیح، هدف عددی و واحد پیشرفت. */
data class AchievementDefinition(
    val id: String,
    val kind: AchievementKind,
    val title: String,
    val description: String,
    /** هدف عددی (مثلاً ۷ روز، ۱۰ درس، ۱۰۰ مرور). */
    val target: Int,
    /** واحد پیشرفت برای نمایش (مثلاً «روز»، «درس»، «مرور»، «دقیقه»). */
    val progressUnit: String
)

/**
 * شواهدی که دستاوردها از روی آن‌ها محاسبه می‌شوند — همه از فروشگاه‌هایی که
 * برنامه از قبل دارد. هیچ شاهدی ساخته نمی‌شود.
 */
data class AchievementEvidence(
    val completedLessonIds: Set<String> = emptySet(),
    val completedStudyDays: Set<Long> = emptySet(),
    val examAttempts: List<ExamAttempt> = emptyList(),
    val diagnosticSessions: List<DiagnosticSession> = emptyList(),
    val focusSessions: List<FocusSession> = emptyList(),
    val reviewCards: List<ReviewCardState> = emptyList(),
    val dailyPlans: List<DailyPlan> = emptyList(),
    val todayEpochDay: Long = StreakCalculator.todayEpochDay()
)

/** نتیجه یک دستاورد برای یک دسته شواهد: پیشرفت واقعی و اینکه گرفته شده یا نه. */
data class AchievementResult(
    val definition: AchievementDefinition,
    /** پیشرفت واقعی فعلی (می‌تواند از هدف هم بیشتر شود؛ همان عدد واقعی است). */
    val progress: Int,
    val earned: Boolean
) {
    /**
     * متن پیشرفت واقعی، مثلاً «۳ از ۷ روز» (با رقم‌های لاتین؛ UI به فارسی
     * تبدیل می‌کند).
     */
    fun progressText(): String = "$progress از ${definition.target} ${definition.progressUnit}"
}

/** نتیجه ارزیابی: گرفته‌شده‌ها + نزدیک‌ترین نگرفته (null یعنی همه گرفته شده‌اند). */
data class AchievementEvaluation(
    val earned: List<AchievementResult>,
    val nearestUnearned: AchievementResult?
)

/** ارزیاب خالص دستاوردها — بدون Android، بدون I/O، قابل تست واحد. */
object AchievementEvaluator {

    /** همه دستاوردها، به ترتیب نمایش (همین ترتیب در تساوی نسبت پیشرفت هم ملاک است). */
    val ALL: List<AchievementDefinition> = listOf(
        AchievementDefinition(
            id = "first-lesson",
            kind = AchievementKind.FIRST_LESSON,
            title = "اولین درس",
            description = "اولین درس رو کامل کردی؛ شروع کردن خودش نصف راهه.",
            target = 1,
            progressUnit = "درس"
        ),
        AchievementDefinition(
            id = "ten-lessons",
            kind = AchievementKind.TEN_LESSONS,
            title = "ده درس کامل",
            description = "ده درس رو تا ته رفتی و کامل کردی.",
            target = 10,
            progressUnit = "درس"
        ),
        AchievementDefinition(
            id = "first-diagnostic",
            kind = AchievementKind.FIRST_DIAGNOSTIC,
            title = "اولین تشخیص سطح",
            description = "برای یک درس، سطحت رو سنجیدی تا بدونی از کجا شروع کنی.",
            target = 1,
            progressUnit = "جلسه تشخیص"
        ),
        AchievementDefinition(
            id = "first-exam",
            kind = AchievementKind.FIRST_EXAM,
            title = "اولین آزمون",
            description = "اولین آزمونت رو ثبت کردی و نتیجه‌ش رو دیدی.",
            target = 1,
            progressUnit = "آزمون"
        ),
        AchievementDefinition(
            id = "first-focus",
            kind = AchievementKind.FIRST_FOCUS,
            title = "اولین جلسه تمرکز",
            description = "اولین جلسه تمرکزت رو با تایمر گذروندی.",
            target = 1,
            progressUnit = "جلسه تمرکز"
        ),
        AchievementDefinition(
            id = "streak-7",
            kind = AchievementKind.STREAK_7,
            title = "هفت روز پیاپی",
            description = "هفت روز پشت سر هم درس خوندی.",
            target = 7,
            progressUnit = "روز"
        ),
        AchievementDefinition(
            id = "streak-30",
            kind = AchievementKind.STREAK_30,
            title = "سی روز پیاپی",
            description = "سی روز پشت سر هم درس خوندی؛ استمرار واقعی.",
            target = 30,
            progressUnit = "روز"
        ),
        AchievementDefinition(
            id = "reviews-100",
            kind = AchievementKind.REVIEWS_100,
            title = "صد مرور",
            description = "صد بار کارت مرور رو جواب دادی.",
            target = 100,
            progressUnit = "مرور"
        ),
        AchievementDefinition(
            id = "first-plan-done",
            kind = AchievementKind.FIRST_PLAN_DONE,
            title = "اولین برنامه کامل",
            description = "برنامه یک روز رو کامل انجام دادی.",
            target = 1,
            progressUnit = "برنامه"
        ),
        AchievementDefinition(
            id = "focus-300",
            kind = AchievementKind.FOCUS_300,
            title = "پنج ساعت تمرکز",
            description = "در مجموع ۳۰۰ دقیقه تمرکز واقعی ثبت شده داری.",
            target = 300,
            progressUnit = "دقیقه"
        )
    )

    /**
     * پیشرفت واقعی یک دستاورد برای دسته شواهد داده‌شده.
     *
     * - درس‌ها: تعداد درس‌های کامل‌شده.
     * - رشته: رشته‌ی فعلی روزها (همان محاسبه صادقانه StreakCalculator).
     * - مرورها: مجموع totalReviews همه کارت‌ها.
     * - تمرکز: مجموع ثانیه‌های واقعی تمرکز، با تقسیم صحیح (هرگز گرد به بالا).
     * - برنامه کامل: برنامه‌ای که آیتم دارد و همه آیتم‌هایش درس کامل‌شده‌اند.
     * - جلسه تمرکز: فقط جلسه‌ای با ثانیه واقعی بیشتر از صفر.
     */
    fun progressOf(kind: AchievementKind, evidence: AchievementEvidence): Int = when (kind) {
        AchievementKind.FIRST_LESSON -> evidence.completedLessonIds.size
        AchievementKind.TEN_LESSONS -> evidence.completedLessonIds.size
        AchievementKind.FIRST_DIAGNOSTIC -> evidence.diagnosticSessions.size
        AchievementKind.FIRST_EXAM -> evidence.examAttempts.size
        AchievementKind.FIRST_FOCUS ->
            evidence.focusSessions.count { it.actualFocusSeconds > 0 }
        AchievementKind.STREAK_7 ->
            StreakCalculator.currentStreak(evidence.completedStudyDays, evidence.todayEpochDay)
        AchievementKind.STREAK_30 ->
            StreakCalculator.currentStreak(evidence.completedStudyDays, evidence.todayEpochDay)
        AchievementKind.REVIEWS_100 ->
            evidence.reviewCards.sumOf { it.totalReviews }
        AchievementKind.FIRST_PLAN_DONE ->
            evidence.dailyPlans.count { plan ->
                plan.items.isNotEmpty() &&
                    plan.items.all { it.lessonId in evidence.completedLessonIds }
            }
        AchievementKind.FOCUS_300 ->
            evidence.focusSessions.sumOf { it.actualFocusSeconds } / 60
    }

    /** ارزیابی همه دستاوردها برای دسته شواهد داده‌شده. */
    fun evaluate(evidence: AchievementEvidence): AchievementEvaluation {
        val results = ALL.map { definition ->
            val progress = progressOf(definition.kind, evidence)
            AchievementResult(definition, progress, progress >= definition.target)
        }
        val earned = results.filter { it.earned }
        // نزدیک‌ترین نگرفته: بیشترین نسبت پیشرفت به هدف؛ در تساوی، اولین
        // تعریف در فهرست ALL برنده است (مقایسه اکید `>` همین را تضمین می‌کند).
        var nearest: AchievementResult? = null
        var bestRatio = -1.0
        for (result in results) {
            if (result.earned) continue
            val ratio = result.progress.toDouble() / result.definition.target.toDouble()
            if (ratio > bestRatio) {
                bestRatio = ratio
                nearest = result
            }
        }
        return AchievementEvaluation(earned = earned, nearestUnearned = nearest)
    }
}
