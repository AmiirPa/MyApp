package com.example.data.focus

/**
 * §۸ الحاقیه — جلسه تمرکز واقعی (پومودورو).
 *
 * صداقت (سخت‌گیرانه): فقط ثانیه‌های واقعی تمرکز شمرده می‌شوند.
 * - یک بازه ۲۵ دقیقه‌ای کامل = ۲۵ دقیقه (`completed = true`).
 * - جلسه نیمه‌کاره = همان ثانیه‌هایی که واقعاً تمرکز شده (`completed = false`)؛
 *   ثانیه‌های باقی‌مانده هرگز شمرده نمی‌شوند.
 * - ثانیه‌های استراحت هرگز ثبت نمی‌شوند؛ ثانیه‌های مکث هرگز شمرده نمی‌شوند.
 * - دقیقه‌ها با تقسیم صحیح محاسبه می‌شوند (هرگز گرد به بالا نمی‌شود) و زمان
 *   باز بودن اپ، تمرکز شمرده نمی‌شود.
 */
data class FocusSession(
    val id: String,
    /** روز تقویمی جلسه (epoch day، مثل StreakCalculator). */
    val dateEpochDay: Long,
    val startedAtEpochMillis: Long,
    /** طول برنامه‌ریزی‌شده بازه تمرکز (پیش‌فرض ۲۵ دقیقه = ۱۵۰۰ ثانیه). */
    val plannedFocusSeconds: Int = DEFAULT_PLANNED_FOCUS_SECONDS,
    /** ثانیه‌های واقعی تمرکز در این جلسه — تنها چیزی که در دقیقه‌ها شمرده می‌شود. */
    val actualFocusSeconds: Int,
    /** تعداد مکث‌ها (توقف تایمر) در طول این بازه تمرکز. */
    val interruptions: Int,
    /** true یعنی بازه به صفر رسید؛ false یعنی نیمه‌کاره رها شد (reset). */
    val completed: Boolean,
    /** پایان جلسه؛ در جلسه‌های ذخیره‌شده همیشه مقدار دارد. */
    val endedAtEpochMillis: Long? = null
) {
    companion object {
        const val DEFAULT_PLANNED_FOCUS_SECONDS: Int = 25 * 60
    }
}

/**
 * قلاب ثبت جلسه تمرکز برای تایمر پومودورو (§۸).
 *
 * تایمر فقط در پایان یک بازه تمرکز صدا می‌زند:
 * - بازه کامل شد → `completed = true` با `actualFocusSeconds = plannedFocusSeconds`؛
 * - بازه با reset رها شد → `completed = false` با ثانیه‌های واقعی انجام‌شده؛
 * - مکث در طول بازه، در `interruptions` شمرده می‌شود.
 * استراحت هرگز این قلاب را صدا نمی‌زند. پیش‌فرض تایمر null است — بدون اتصال،
 * تایمر دقیقاً مثل قبل کار می‌کند.
 */
fun interface FocusSessionRecorder {
    fun onFocusIntervalEnded(
        plannedFocusSeconds: Int,
        actualFocusSeconds: Int,
        completed: Boolean,
        interruptions: Int,
        startedAtEpochMillis: Long,
        endedAtEpochMillis: Long
    )
}
