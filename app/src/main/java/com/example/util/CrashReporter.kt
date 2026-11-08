package com.example.util

import android.content.Context
import android.util.Log
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * گزارش‌گیر خرابی برنامه (CrashReporter) — برای پیدا کردن علت «برنامه مدام
 * متوقف می‌شود» روی گوشی واقعی.
 *
 * یک هندلر سراسری برای خطاهای گرفته‌نشده نصب می‌کند؛ وقتی برنامه به‌خاطر
 * یک خطا می‌میرد، متن کامل خطا (نوع، پیام و ردیابی پشته) را در یک فایل
 * می‌نویسد و بعد رفتار عادی سیستم را ادامه می‌دهد (یعنی همان پیام «برنامه
 * متوقف شد» نشان داده می‌شود). خود هندلر هرگز خطا نمی‌دهد.
 *
 * فایل گزارش: `<حافظه خارجی مخصوص برنامه>/crash-report.txt`
 * (در فایل‌منیجر: Android/data/com.aistudio.konkoorensani.fhkzqp/files/crash-report.txt)
 * یک نسخه هم در حافظه داخلی برنامه (`files/crash-report.txt`) نوشته می‌شود.
 *
 * این گزارش فقط برای عیب‌یابی است؛ هیچ اطلاعاتی به اینترنت فرستاده نمی‌شود.
 */
object CrashReporter {
    private const val TAG = "CrashReporter"
    private const val FILE_NAME = "crash-report.txt"
    private const val PREFS_NAME = "crash_report_store"
    private const val KEY_LAST_CRASH = "last_crash_report"

    private var installed = false

    /** نصب هندلر؛ فقط یک بار (فراخوانی دوباره بی‌اثر است). */
    fun install(context: Context) {
        if (installed) return
        installed = true
        val appContext = context.applicationContext
        val previous = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            try {
                val report = buildReport(thread, throwable)
                writeReport(appContext, report)
            } catch (_: Exception) {
                // هندلر گزارش خودش هرگز نباید خطا بدهد.
            }
            // رفتار عادی سیستم (نمایش پیام توقف و کشتن پردازه) حفظ می‌شود.
            previous?.uncaughtException(thread, throwable)
        }
    }

    /** آخرین گزارش خرابی ذخیره‌شده (اگر برنامه بعد از خرابی دوباره باز شده باشد). */
    fun lastCrashReport(context: Context): String? {
        return try {
            context.applicationContext
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getString(KEY_LAST_CRASH, null)
        } catch (_: Exception) {
            null
        }
    }

    /** پاک کردن گزارش ذخیره‌شده (بعد از اینکه خوانده شد). */
    fun clearLastCrashReport(context: Context) {
        try {
            context.applicationContext
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit().remove(KEY_LAST_CRASH).apply()
        } catch (_: Exception) {
            // بی‌صدا رد شو.
        }
    }

    private fun buildReport(thread: Thread, throwable: Throwable): String {
        val time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(Date())
        val sb = StringBuilder()
        sb.append("Zero to Farhangian — crash report\n")
        sb.append("time: ").append(time).append('\n')
        sb.append("thread: ").append(thread.name).append('\n')
        sb.append("exception: ").append(throwable.javaClass.name).append('\n')
        sb.append("message: ").append(throwable.message).append('\n')
        var cause: Throwable? = throwable
        var depth = 0
        while (cause != null && depth < 10) {
            sb.append("--- cause level ").append(depth).append(" ---\n")
            for (el in cause.stackTrace) {
                sb.append("    at ").append(el.toString()).append('\n')
            }
            cause = cause.cause
            depth++
        }
        return sb.toString()
    }

    private fun writeReport(context: Context, report: String) {
        // ۱) حافظه داخلی (همیشه در دسترس است).
        try {
            context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use {
                it.write(report.toByteArray(Charsets.UTF_8))
            }
        } catch (e: Exception) {
            Log.w(TAG, "Could not write internal crash report", e)
        }
        // ۲) SharedPreferences (برای نمایش در تنظیمات در اجرای بعدی).
        try {
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit().putString(KEY_LAST_CRASH, report).apply()
        } catch (e: Exception) {
            Log.w(TAG, "Could not save crash report to prefs", e)
        }
        // ۳) حافظه خارجی مخصوص برنامه (کاربر می‌تواند فایل را بفرستد).
        try {
            val dir = context.getExternalFilesDir(null) ?: return
            File(dir, FILE_NAME).writeText(report, Charsets.UTF_8)
        } catch (e: Exception) {
            Log.w(TAG, "Could not write external crash report", e)
        }
    }
}
