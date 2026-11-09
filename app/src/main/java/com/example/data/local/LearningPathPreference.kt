package com.example.data.local

import android.content.Context

/**
 * ذخیرهٔ انتخاب بخش صفحهٔ مسیر یادگیری (دبستان / متوسطه اول / متوسطه دوم)
 * و وضعیت رد کردن راهنمای شروع — در SharedPreferences جداگانه، با همان
 * الگوی FocusSessionStore (applicationContext + MODE_PRIVATE).
 *
 * فقط داده‌های خودش را نگه می‌دارد؛ از هیچ فروشگاه دیگری نمی‌خواند و
 * در هیچ فروشگاه دیگری نمی‌نویسد.
 */
object LearningPathPreference {
    const val SECTION_ELEMENTARY = "ELEMENTARY"
    const val SECTION_MIDDLE = "MIDDLE"
    const val SECTION_HIGH = "HIGH"

    private const val PREFS_NAME = "learning_path_prefs"
    private const val KEY_SECTION = "selected_section"
    private const val KEY_START_GUIDE_DISMISSED = "start_guide_dismissed"

    private fun prefs(context: Context) =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    /** نام بخش ذخیره‌شده؛ اگر نبود یا نامعتبر بود SECTION_HIGH. */
    fun loadSectionName(context: Context): String {
        val name = prefs(context).getString(KEY_SECTION, null)
        return when (name) {
            SECTION_ELEMENTARY, SECTION_MIDDLE, SECTION_HIGH -> name
            else -> SECTION_HIGH
        }
    }

    fun saveSectionName(context: Context, name: String) {
        prefs(context).edit().putString(KEY_SECTION, name).apply()
    }

    /** راهنمای شروع رد شده است؟ پیش‌فرض false. */
    fun isStartGuideDismissed(context: Context): Boolean =
        prefs(context).getBoolean(KEY_START_GUIDE_DISMISSED, false)

    fun setStartGuideDismissed(context: Context, dismissed: Boolean) {
        prefs(context).edit().putBoolean(KEY_START_GUIDE_DISMISSED, dismissed).apply()
    }
}
