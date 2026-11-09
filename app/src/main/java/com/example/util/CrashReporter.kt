package com.example.util

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.atomic.AtomicLong

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
 *
 * دیده‌بان هنگ (AnrWatchdog): همراه با نصب هندلر کرش شروع می‌شود. یک نخ
 * پس‌زمینه هر ۲ ثانیه یک شمارنده را روی نخ اصلی زیاد می‌کند؛ اگر بیش از
 * ۵ ثانیه شمارنده جلو نرفت (یعنی نخ اصلی قفل است)، استک‌تریس نخ اصلی را
 * در `anr-report.txt` (همان دو محل بالا) می‌نویسد — بدون کرش، بدون دیالوگ
 * و بدون بستن برنامه. برای هر هنگ فقط یک رکورد نوشته می‌شود.
 */
object CrashReporter {
    private const val TAG = "CrashReporter"
    private const val FILE_NAME = "crash-report.txt"
    private const val PREFS_NAME = "crash_report_store"
    private const val KEY_LAST_CRASH = "last_crash_report"

    private const val ANR_FILE_NAME = "anr-report.txt"

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
        // دیده‌بان هنگ همراه با هندلر کرش شروع می‌شود.
        AnrWatchdog.start(appContext)
    }

    /** توقف دیده‌بان هنگ (هندلر کرش سر جایش می‌ماند). */
    fun stopAnrWatchdog() {
        AnrWatchdog.stop()
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

    /**
     * دیده‌بان هنگ (ANR watchdog) — ساده و بدون وابستگی تازه.
     *
     * سازوکار: یک نخ پس‌زمینه (daemon) هر [TICK_MS] یک شمارنده را با
     * `Handler` روی نخ اصلی زیاد می‌کند. خود نخ پس‌زمینه مقدار شمارنده را
     * می‌خواند؛ اگر بیش از [THRESHOLD_MS] شمارنده جلو نرفت، یعنی نخ اصلی
     * قفل است و یک رکورد (مهر زمانی + استک‌تریس نخ اصلی) در
     * `anr-report.txt` نوشته می‌شود. نوشتن فایل فقط روی همین نخ پس‌زمینه
     * انجام می‌شود، هرگز روی نخ اصلی.
     *
     * برای هر هنگ فقط یک رکورد: پرچم `inAnr` تا وقتی نخ اصلی آزاد نشده
     * جلوی رکورد تکراری را می‌گیرد و وقتی شمارنده دوباره جلو رفت صفر
     * می‌شود. برنامه نه کرش می‌کند، نه دیالوگی نشان می‌دهد و نه بسته
     * می‌شود.
     */
    private object AnrWatchdog {
        private const val TICK_MS = 2000L
        private const val THRESHOLD_MS = 5000L

        private val mainHandler = Handler(Looper.getMainLooper())
        private val counter = AtomicLong(0)

        @Volatile
        private var running = false
        private var thread: Thread? = null

        /** شروع دیده‌بان؛ فراخوانی دوباره در حالی که در حال اجراست بی‌اثر است. */
        @Synchronized
        fun start(context: Context) {
            if (running) return
            running = true
            val appContext = context.applicationContext
            val t = Thread({
                var lastSeenCounter = counter.get()
                var lastChangeTime = SystemClock.uptimeMillis()
                var inAnr = false
                while (running) {
                    // ۱) تیک را روی نخ اصلی بفرست؛ اگر نخ اصلی قفل باشد
                    //    این Runnable در صف می‌ماند و شمارنده جلو نمی‌رود.
                    mainHandler.post { counter.incrementAndGet() }
                    try {
                        Thread.sleep(TICK_MS)
                    } catch (_: InterruptedException) {
                        break
                    }
                    if (!running) break
                    val now = SystemClock.uptimeMillis()
                    val current = counter.get()
                    if (current != lastSeenCounter) {
                        // نخ اصلی زنده است؛ پرچم هنگ را صفر کن.
                        lastSeenCounter = current
                        lastChangeTime = now
                        inAnr = false
                    } else if (now - lastChangeTime > THRESHOLD_MS && !inAnr) {
                        // نخ اصلی بیش از ۵ ثانیه قفل است؛ فقط یک رکورد.
                        inAnr = true
                        try {
                            writeAnrReport(appContext)
                        } catch (_: Exception) {
                            // دیده‌بان هرگز نباید خطا بدهد یا بمیرد.
                        }
                    }
                }
            }, "AnrWatchdog")
            t.isDaemon = true
            thread = t
            t.start()
        }

        /** توقف دیده‌بان (نخ پس‌زمینه را بیدار و تمام می‌کند). */
        @Synchronized
        fun stop() {
            running = false
            thread?.interrupt()
            thread = null
        }

        private fun buildAnrReport(): String {
            val time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(Date())
            val sb = StringBuilder()
            sb.append("Zero to Farhangian — ANR report\n")
            sb.append("time: ").append(time).append('\n')
            sb.append("note: main thread was unresponsive for more than ")
                .append(THRESHOLD_MS / 1000).append(" seconds\n")
            sb.append("main thread stack trace:\n")
            for (el in Looper.getMainLooper().thread.stackTrace) {
                sb.append("    at ").append(el.toString()).append('\n')
            }
            sb.append("--- end of ANR record ---\n")
            return sb.toString()
        }

        private fun writeAnrReport(context: Context) {
            val report = buildAnrReport()
            // ۱) حافظه داخلی — به انتهای فایل اضافه می‌شود تا هنگ‌های
            //    قبلی هم بمانند (هر هنگ فقط یک رکورد).
            try {
                context.openFileOutput(
                    ANR_FILE_NAME,
                    Context.MODE_APPEND
                ).use { it.write(report.toByteArray(Charsets.UTF_8)) }
            } catch (e: Exception) {
                Log.w(TAG, "Could not write internal ANR report", e)
            }
            // ۲) حافظه خارجی مخصوص برنامه (کاربر می‌تواند فایل را بفرستد).
            try {
                val dir = context.getExternalFilesDir(null) ?: return
                File(dir, ANR_FILE_NAME).appendText(report, Charsets.UTF_8)
            } catch (e: Exception) {
                Log.w(TAG, "Could not write external ANR report", e)
            }
        }
    }
}
