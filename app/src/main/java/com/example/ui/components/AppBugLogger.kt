package com.example.ui.components

import android.content.Context
import android.os.Build
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.CopyOnWriteArrayList

data class AppLogEntry(
    val timestamp: String,
    val level: String, // INFO, WARN, ERROR, CRASH
    val tag: String,
    val message: String,
    val details: String = ""
)

object AppBugLogger {
    private val logList = CopyOnWriteArrayList<AppLogEntry>()
    private const val MAX_LOGS = 200

    init {
        log("INFO", "AppBugLogger", "سیستم ثبت لاگ و باگ‌های زنده راه‌اندازی شد. سیستم آماده است.")
        
        // Catch uncaught exceptions globally
        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            val stackTrace = throwable.stackTraceToString()
            error("CRASH", "UncaughtException", "خطای بحرانی در ترد ${thread.name}: ${throwable.localizedMessage}\n$stackTrace")
            defaultHandler?.uncaughtException(thread, throwable)
        }
    }

    fun log(level: String, tag: String, message: String, details: String = "") {
        val timeStr = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault()).format(Date())
        val entry = AppLogEntry(timestamp = timeStr, level = level, tag = tag, message = message, details = details)
        logList.add(0, entry)
        if (logList.size > MAX_LOGS) {
            logList.removeAt(logList.size - 1)
        }
    }

    fun info(tag: String, message: String) = log("INFO", tag, message)
    fun warn(tag: String, message: String, details: String = "") = log("WARN", tag, message, details)
    fun error(tag: String, message: String, details: String = "") = log("ERROR", tag, message, details)

    fun getLogs(): List<AppLogEntry> = logList.toList()

    fun clearLogs() {
        logList.clear()
        info("AppBugLogger", "لیست لاگ‌ها پاکسازی شد.")
    }

    fun exportLogsToString(): String {
        val sb = StringBuilder()
        sb.append("=== صفر تا فرهنگیان - گزارش عیب‌یابی و لاگ‌ها (تولید شده در ${SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(Date())}) ===\n")
        sb.append("دستگاه: ${Build.MANUFACTURER} ${Build.MODEL} (Android ${Build.VERSION.RELEASE} - API ${Build.VERSION.SDK_INT})\n")
        sb.append("--------------------------------------------------\n\n")
        for (entry in logList) {
            sb.append("[${entry.timestamp}] [${entry.level}] [${entry.tag}]: ${entry.message}\n")
            if (entry.details.isNotBlank()) {
                sb.append(" جزئیات:\n${entry.details}\n")
            }
            sb.append("--------------------------------------------------\n")
        }
        return sb.toString()
    }

    fun saveLogsToFile(context: Context): File? {
        return try {
            val file = File(context.cacheDir, "konkoor_bug_report_${System.currentTimeMillis()}.txt")
            file.writeText(exportLogsToString(), Charsets.UTF_8)
            file
        } catch (_: Exception) {
            null
        }
    }
}
