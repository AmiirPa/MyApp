package com.example.data.focus

import android.content.Context
import android.content.SharedPreferences
import com.example.util.StreakCalculator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONArray
import org.json.JSONObject

/**
 * ذخیره‌سازی محلی جلسه‌های تمرکز (§۸ الحاقیه) — فایل SharedPreferences جداگانه.
 *
 * همان الگوی `AdaptiveLocalStore`: JSON با `org.json` + `runCatching` + فهرست
 * خالی در صورت خرابی. **فقط داده‌های خودش را نگه می‌دارد**؛ از هیچ فروشگاه
 * دیگری نمی‌خواند و در هیچ فروشگاه دیگری نمی‌نویسد. بدون Room (کد مرده است)،
 * بدون migration؛ داده‌ها نسخه‌دارند (`schemaVersion = 1`).
 *
 * جلسه‌ها فقط محلی‌اند — همگام‌سازی ابری برای آن‌ها هنوز وصل نشده است؛
 * با حذف اپ یا پاک شدن داده‌ها، سابقه تمرکز از بین می‌رود.
 *
 * سازنده اصلی `SharedPreferences` می‌گیرد تا تست بدون `Context` واقعی ممکن
 * باشد (`Context` در اندروید واقعی کلاس انتزاعی است و نمی‌شود آن را با
 * سازنده ساخت — تست قبلی همین‌جا در CI خراب شد)؛ سازنده دوم همان مسیر
 * عادی برنامه است (MainActivity هنوز `Context` می‌دهد).
 */
class FocusSessionStore(
    private val prefs: SharedPreferences,
) : FocusSessionRecorder {

    constructor(context: Context, prefsName: String = PREFS_NAME) : this(
        context.applicationContext.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
    )

    private val _sessions = MutableStateFlow(loadSessions())
    val sessions: StateFlow<List<FocusSession>> = _sessions.asStateFlow()

    override fun onFocusIntervalEnded(
        plannedFocusSeconds: Int,
        actualFocusSeconds: Int,
        completed: Boolean,
        interruptions: Int,
        startedAtEpochMillis: Long,
        endedAtEpochMillis: Long
    ) {
        recordFocusInterval(
            actualFocusSeconds = actualFocusSeconds,
            completed = completed,
            interruptions = interruptions,
            startedAtEpochMillis = startedAtEpochMillis,
            endedAtEpochMillis = endedAtEpochMillis,
            plannedFocusSeconds = plannedFocusSeconds
        )
    }

    /**
     * ثبت یک بازه تمرکز پایان‌یافته. جلسه با صفر ثانیه ثبت نمی‌شود
     * (شروع و توقف فوری، تمرکزی نداشته است).
     */
    fun recordFocusInterval(
        actualFocusSeconds: Int,
        completed: Boolean,
        interruptions: Int,
        startedAtEpochMillis: Long,
        endedAtEpochMillis: Long,
        plannedFocusSeconds: Int = FocusSession.DEFAULT_PLANNED_FOCUS_SECONDS
    ) {
        if (actualFocusSeconds <= 0) return
        val session = FocusSession(
            id = "focus-$endedAtEpochMillis-$actualFocusSeconds",
            dateEpochDay = StreakCalculator.epochDayFromMillis(endedAtEpochMillis),
            startedAtEpochMillis = startedAtEpochMillis,
            plannedFocusSeconds = plannedFocusSeconds,
            actualFocusSeconds = actualFocusSeconds,
            interruptions = interruptions.coerceAtLeast(0),
            completed = completed,
            endedAtEpochMillis = endedAtEpochMillis
        )
        val updated = FocusSessionLogic.addSession(_sessions.value, session)
        _sessions.value = updated
        persistSessions(updated)
    }

    /** دقیقه‌های خالص تمرکز ثبت‌شده در یک روز. */
    fun netFocusMinutes(dayEpochDay: Long): Int =
        FocusSessionLogic.netFocusMinutes(_sessions.value, dayEpochDay)

    /** جلسه‌های یک روز، جدیدترین اول. */
    fun sessionsForDay(dayEpochDay: Long): List<FocusSession> =
        FocusSessionLogic.sessionsForDay(_sessions.value, dayEpochDay)

    /** تعداد وقفه‌های ثبت‌شده در یک روز. */
    fun interruptionsForDay(dayEpochDay: Long): Int =
        FocusSessionLogic.interruptionsForDay(_sessions.value, dayEpochDay)

    // ---------- JSON ----------

    private fun sessionToJson(s: FocusSession): JSONObject = JSONObject().apply {
        put("schemaVersion", SCHEMA_VERSION)
        put("id", s.id)
        put("dateEpochDay", s.dateEpochDay)
        put("startedAtEpochMillis", s.startedAtEpochMillis)
        put("plannedFocusSeconds", s.plannedFocusSeconds)
        put("actualFocusSeconds", s.actualFocusSeconds)
        put("interruptions", s.interruptions)
        put("completed", s.completed)
        s.endedAtEpochMillis?.let { put("endedAtEpochMillis", it) }
    }

    private fun sessionFromJson(obj: JSONObject): FocusSession = FocusSession(
        id = obj.optString("id"),
        dateEpochDay = obj.optLong("dateEpochDay", 0L),
        startedAtEpochMillis = obj.optLong("startedAtEpochMillis", 0L),
        plannedFocusSeconds = obj.optInt(
            "plannedFocusSeconds",
            FocusSession.DEFAULT_PLANNED_FOCUS_SECONDS
        ),
        actualFocusSeconds = obj.optInt("actualFocusSeconds", 0),
        interruptions = obj.optInt("interruptions", 0),
        completed = obj.optBoolean("completed", false),
        endedAtEpochMillis = obj.optLong("endedAtEpochMillis", 0L).takeIf { it != 0L }
    )

    private fun loadSessions(): List<FocusSession> {
        val raw = prefs.getString(KEY_SESSIONS, null) ?: return emptyList()
        return runCatching {
            val arr = JSONArray(raw)
            buildList {
                for (i in 0 until arr.length()) {
                    runCatching { add(sessionFromJson(arr.getJSONObject(i))) }
                }
            }
        }.getOrDefault(emptyList())
    }

    private fun persistSessions(list: List<FocusSession>) {
        val arr = JSONArray()
        list.forEach { arr.put(sessionToJson(it)) }
        prefs.edit().putString(KEY_SESSIONS, arr.toString()).apply()
    }

    companion object {
        const val PREFS_NAME: String = "focus_session_store"
        const val SCHEMA_VERSION: Int = 1
        private const val KEY_SESSIONS: String = "focus_sessions"
    }
}
