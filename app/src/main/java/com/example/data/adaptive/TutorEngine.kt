package com.example.data.adaptive

/**
 * معلم خصوصی دیجیتال (§۴ الحاقیه) — انتخاب و توالی توضیح.
 *
 * این موتور **محتوا تولید نمی‌کند**. محتوا از `MinusZeroLearningEngine` می‌آید
 * (راهنمای ۱۰ مرحله‌ای، `extraSimpleExplanation`، `extraEverydayExample`،
 * `minusZeroPrerequisiteText`، مسیر ۹ سطحی، `visualDiagramText` قدم‌ها).
 * این موتور فقط تصمیم می‌گیرد وقتی دانش‌آموز یک قسمت را نفهمید،
 * **با کدام روش دیگر** توضیح داده شود — و هرگز همان متن قبلی را تکرار نمی‌کند.
 */
object TutorEngine {

    /** ترتیب پیش‌فرض گونه‌ها: از ساده‌ترین به چندسبکی. */
    val DEFAULT_VARIANT_ORDER = listOf(
        ExplanationVariant.TEXT_SIMPLE,
        ExplanationVariant.EVERYDAY_EXAMPLE,
        ExplanationVariant.DIAGRAM,
        ExplanationVariant.TURKISH_BRIDGE,
        ExplanationVariant.COMPARISON_TABLE,
        ExplanationVariant.STEP_BY_STEP_PROBLEM,
        ExplanationVariant.QUESTION_ANSWER,
        ExplanationVariant.RHYTHM_SOUND
    )

    /**
     * گونه توضیح بعدی برای یک قدمِ نفهمیده‌شده.
     * اولین گونه‌ای که هنوز امتحان نشده برمی‌گردد؛ اگر همه امتحان شده‌اند،
     * چرخه از اول شروع می‌شود ولی **هرگز همان گونه قبلی را پشت سر هم تکرار نمی‌کند**.
     * @param alreadyTried گونه‌های امتحان‌شده به ترتیب زمانی.
     * @return گونه بعدی، یا `null` اگر هیچ گونه‌ای در دسترس نیست.
     */
    fun nextVariant(
        alreadyTried: List<ExplanationVariant>,
        order: List<ExplanationVariant> = DEFAULT_VARIANT_ORDER
    ): ExplanationVariant? {
        if (order.isEmpty()) return null
        val lastTried = alreadyTried.lastOrNull()
        // اول: گونه‌ای که هنوز امتحان نشده و با قبلی فرق دارد.
        val untried = order.firstOrNull { it !in alreadyTried && it != lastTried }
        if (untried != null) return untried
        // همه امتحان شده‌اند: از اول بچرخ، ولی نه همان قبلی.
        return order.firstOrNull { it != lastTried } ?: order.firstOrNull()
    }

    /**
     * نگاشت ۱۵ مرحله استاندارد §۴ روی ساختار درس موجود (۴ قدم + مثال‌ها + مینی‌چک).
     * هر مرحله می‌گوید محتوایش از کدام بخش درس می‌آید — محتوا ساخته نمی‌شود، فقط ارجاع داده می‌شود.
     */
    fun mapFifteenStagesToLesson(lessonStepCount: Int, workedExampleCount: Int): List<Pair<Int, String>> {
        return listOf(
            1 to "معرفی مفهوم — عنوان و زیرعنوان درس",
            2 to "پیش‌نیازها — «پیش‌نیاز این درس:» در قدم ۱ + نقشه راه منفی صفر",
            3 to "توضیح از سطح ساده — قدم ۱ «به زبان خیلی ساده»",
            4 to "تعریف دقیق — واژه‌نامه ساده درس (اصطلاح + تعریف رسمی کتاب)",
            5 to "مثال ساده — مثال روزمره قدم ۱",
            6 to "مثال حل‌شده — مثال حل‌شده ۱ از $workedExampleCount مثال",
            7 to "تشریح گام‌به‌گام — حل مرحله‌ای مثال حل‌شده",
            8 to "نکات مهم — فرمول‌های طلایی درس",
            9 to "اشتباهات رایج — هشدار دام در مثال حل‌شده",
            10 to "ارتباط با مباحث قبلی — قدم‌های $lessonStepCount گانه و ارجاع به درس پیش‌نیاز",
            11 to "مثال سطح بالاتر — مثال حل‌شده ۲ (در صورت وجود)",
            12 to "تمرین مستقل — مینی‌چک انتهای درس",
            13 to "سؤال تشخیصی — سؤال تشخیصی موتور تطبیقی روی همین مبحث",
            14 to "جمع‌بندی — خلاصه کوتاه درس",
            15 to "مرور و تثبیت — ورود به چرخه مرور فاصله‌دار"
        )
    }

    /**
     * آیا دانش‌آموز به «روش دیگر» نیاز دارد؟ (قدم نفهمیده شده و گونه‌های ساده تمام شده‌اند.)
     * @param failedAttempts تعداد دفعات ناموفق روی همین قدم.
     * @param triedVariants گونه‌های امتحان‌شده.
     */
    fun needsDifferentApproach(failedAttempts: Int, triedVariants: List<ExplanationVariant>): Boolean {
        if (failedAttempts <= 0) return false
        // بعد از ۲ بار ناموفق با یک روش، وقت روش دیگر است.
        return failedAttempts >= 2 || triedVariants.size >= 2
    }
}
