package com.example.data.adaptive

/**
 * مدل داده موتور تطبیقی «صفر تا فرهنگیان» (بخش‌های ۳، ۴، ۵، ۶ و ۹ الحاقیه نهایی).
 *
 * همه کلاس‌های این فایل داده خالص‌اند؛ هیچ وابستگی به Android ندارند تا
 * منطق موتور با تست واحد قابل بررسی باشد.
 */

/**
 * وضعیت هر مبحث — دقیقاً هفت حالت بریف آموزشی.
 * هر محاسبه وضعیت، یک [TopicStatusResult] با سطح شواهد برمی‌گرداند تا
 * «قطعی نیست» بودنِ وضعیتِ کم‌شواهد صریح باشد.
 */
enum class TopicStatus {
    /** آموزش‌ندیده: هیچ تلاشی ثبت نشده است. */
    NOT_STARTED,

    /** در حال یادگیری: مطالعه شروع شده، هنوز کامل نشده است. */
    LEARNING,

    /** دارای ضعف در پیش‌نیاز: پیش‌نیاز مبحث بلد نیست. */
    PREREQUISITE_WEAK,

    /** یادگیری ناقص: درس خوانده شده ولی فهم تأیید نشده است. */
    PARTIAL,

    /** نیازمند مرور: قبلاً مسلط بوده ولی یادآوری ناموفق بوده است. */
    NEEDS_REVIEW,

    /** مسلط: فهم تأییدشده با تکرار. */
    MASTERED,

    /** تسلط پایدار: تسلط در فاصله‌های زمانی تکرار شده است. */
    STABLE
}

/** میزان شواهدی که پشت یک وضعیت ایستاده است. */
enum class EvidenceLevel {
    /** کمتر از ۳ تلاش مرتبط — وضعیت قطعی نیست. */
    INSUFFICIENT,

    /** ۳ تا ۴ تلاش مرتبط. */
    LOW,

    /** ۵ تلاش مرتبط یا بیشتر. */
    SUFFICIENT
}

data class TopicStatusResult(
    val status: TopicStatus,
    val evidenceLevel: EvidenceLevel,
    /** توضیح سازنده و بدون سرزنش برای نمایش به دانش‌آموز. */
    val explanation: String
)

/**
 * علت اشتباه — ۱۲ دسته دقیق §۳ الحاقیه.
 * علتِ خوداظهاری کاربر منبع حقیقت است؛ علتِ حدس موتور همیشه جداگانه
 * و با برچسب «حدس» نگه داشته می‌شود و هرگز جای آن را نمی‌گیرد.
 */
enum class ErrorCause {
    /** ۱. ندانستن مفهوم */
    CONCEPT_UNKNOWN,

    /** ۲. ضعف پیش‌نیاز */
    PREREQUISITE_WEAK,

    /** ۳. فراموشی */
    FORGOTTEN,

    /** ۴. اشتباه محاسباتی */
    CALCULATION,

    /** ۵. بی‌دقتی */
    CARELESSNESS,

    /** ۶. درک نادرست صورت سؤال */
    MISREAD_QUESTION,

    /** ۷. ضعف در استدلال */
    REASONING_WEAK,

    /** ۸. ناتوانی در ترکیب مباحث */
    CANNOT_COMBINE,

    /** ۹. ناآشنایی با تکنیک تستی */
    TEST_TECHNIQUE,

    /** ۱۰. ضعف مدیریت زمان */
    TIME_MANAGEMENT,

    /** ۱۱. حدس تصادفی */
    RANDOM_GUESS,

    /** ۱۲. پاسخ صحیح بدون تسلط واقعی */
    CORRECT_WITHOUT_MASTERY
}

/** سطح سؤال تشخیصی. */
enum class QuestionLevel {
    /** پیش‌نیاز */
    FOUNDATION,

    /** مفهوم */
    CONCEPT,

    /** کاربرد / ترکیبی */
    APPLICATION
}

/**
 * یک تلاش برای یک سؤال — واحد پایه همه تحلیل‌ها.
 * زمان بر حسب ثانیه است؛ `answeredAtEpochDay` روز تقویمی (epoch day) است
 * تا با [com.example.util.StreakCalculator] سازگار بماند.
 */
data class QuestionAttempt(
    val questionId: String,
    val topicId: String,
    val subjectId: String,
    val level: QuestionLevel,
    val isCorrect: Boolean,
    val wasSkipped: Boolean,
    val timeSpentSeconds: Int,
    val answeredAtEpochDay: Long,
    /** علت خوداظهاری کاربر — منبع حقیقت. */
    val selfReportedCause: ErrorCause? = null,
    /** حدس موتور — همیشه جداگانه و با برچسب حدس. */
    val inferredCause: ErrorCause? = null
)

/** یک آزمون کامل با همه تلاش‌هایش؛ درصدها از همین لیست محاسبه می‌شوند. */
data class ExamAttempt(
    val id: String,
    val subjectId: String,
    val title: String,
    val startedAtEpochDay: Long,
    val totalSeconds: Int,
    val questions: List<QuestionAttempt>
)

/** نتیجه یک جلسه تشخیصی روی یک مبحث. */
data class DiagnosticSession(
    val topicId: String,
    val subjectId: String,
    val askedQuestionIds: List<String>,
    val answers: List<QuestionAttempt>,
    val concludedStatus: TopicStatus,
    val weakestLevel: QuestionLevel?,
    /** شناسه درس پیشنهادی برای شروع (بنیادین یا خود درس). */
    val recommendedLessonId: String?,
    val concludedAtEpochDay: Long
)

/** وضعیت مرور یک کارت/مبحث در جعبه لایتنر. */
data class ReviewCardState(
    val itemId: String,
    val topicId: String,
    val subjectId: String,
    /** جعبه لایتنر ۱ تا ۵. */
    val leitnerBox: Int,
    val errorCount: Int,
    val totalReviews: Int,
    val consecutiveCorrect: Int,
    val lastReviewEpochDay: Long,
    val nextReviewEpochDay: Long
)

/** نوع آیتم برنامه روز. */
enum class PlanItemKind {
    REVIEW,
    LEARN,
    TEST
}

/** یک آیتم برنامه روز — همیشه با دلیل سازنده همراه است. */
data class DailyPlanItem(
    val lessonId: String,
    val subjectId: String,
    val kind: PlanItemKind,
    val minutes: Int,
    /** دلیل: «چون مرور سررسید شده»، «چون پیش‌نیاز ضعیف است»، … */
    val reason: String
)

data class DailyPlan(
    val dateEpochDay: Long,
    val items: List<DailyPlanItem>,
    val generatedAtEpochDay: Long
)

/**
 * هر متریک گزارش با پرچم «داده کافی نیست».
 * مقدار `null` یعنی داده کافی نبوده و نباید عدد ساختگی نمایش داده شود.
 */
data class ProgressMetric<T>(
    val value: T?,
    val hasEnoughData: Boolean,
    val reasonIfInsufficient: String? = null
)

/** گونه‌های توضیح معلم خصوصی دیجیتال (§۴) — موتور فقط انتخاب می‌کند، محتوا نمی‌سازد. */
enum class ExplanationVariant {
    /** متن ساده */
    TEXT_SIMPLE,

    /** مثال روزمره */
    EVERYDAY_EXAMPLE,

    /** نمودار/شکل */
    DIAGRAM,

    /** مقایسه دو مفهوم */
    COMPARISON_TABLE,

    /** حل مسئله مرحله‌ای */
    STEP_BY_STEP_PROBLEM,

    /** پرسش و پاسخ */
    QUESTION_ANSWER,

    /** ریتم/صدا */
    RHYTHM_SOUND,

    /** پل ترکی */
    TURKISH_BRIDGE
}

/**
 * شواهد درس از ذخیره‌ساز موجود — فقط خواندنی.
 * `masteryPercent` فقط از همین منبع می‌آید؛ موتور هرگز خودش درصد تسلط نمی‌سازد.
 */
data class LessonEvidence(
    val lessonId: String,
    val subjectId: String,
    val isCompleted: Boolean,
    val masteryPercent: Int,
    val updatedAtEpochDay: Long
)
