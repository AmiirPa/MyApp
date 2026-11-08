package com.example.data.focus

import android.content.SharedPreferences
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Tests for the focus module (§۸): pure session logic + the SharedPreferences
 * store round-trip.
 *
 * The store tests use an in-memory fake `SharedPreferences` implementing the
 * FULL real Android API — never `Context()`: `Context` is abstract in real
 * Android, so `Context()` compiles only against a test stub and broke CI
 * (run #79: "Cannot create an instance of an abstract class"). The fake
 * keeps the round-trip test runnable both locally and in CI.
 */
class FocusSessionTest {

    /** `SharedPreferences` درون‌حافظه‌ای؛ یک نمونه = یک فایل prefs مشترک. */
    private class InMemoryPrefs : SharedPreferences {
        val data = HashMap<String, String?>()
        override fun getAll(): Map<String, *> = data.toMap()
        override fun getString(key: String, defValue: String?): String? =
            if (data.containsKey(key)) data[key] else defValue
        override fun getStringSet(key: String, defValues: Set<String>?): Set<String>? = defValues
        override fun getInt(key: String, defValue: Int): Int = defValue
        override fun getLong(key: String, defValue: Long): Long = defValue
        override fun getFloat(key: String, defValue: Float): Float = defValue
        override fun getBoolean(key: String, defValue: Boolean): Boolean = defValue
        override fun contains(key: String): Boolean = data.containsKey(key)
        override fun edit(): SharedPreferences.Editor = Editor()
        override fun registerOnSharedPreferenceChangeListener(
            listener: SharedPreferences.OnSharedPreferenceChangeListener
        ) = Unit
        override fun unregisterOnSharedPreferenceChangeListener(
            listener: SharedPreferences.OnSharedPreferenceChangeListener
        ) = Unit

        private inner class Editor : SharedPreferences.Editor {
            override fun putString(key: String, value: String?): SharedPreferences.Editor {
                data[key] = value; return this
            }
            override fun putStringSet(key: String, values: Set<String>?): SharedPreferences.Editor = this
            override fun putInt(key: String, value: Int): SharedPreferences.Editor = this
            override fun putLong(key: String, value: Long): SharedPreferences.Editor = this
            override fun putFloat(key: String, value: Float): SharedPreferences.Editor = this
            override fun putBoolean(key: String, value: Boolean): SharedPreferences.Editor = this
            override fun remove(key: String): SharedPreferences.Editor {
                data.remove(key); return this
            }
            override fun clear(): SharedPreferences.Editor {
                data.clear(); return this
            }
            override fun commit(): Boolean = true
            override fun apply() = Unit
        }
    }

    private fun session(
        day: Long,
        actualSeconds: Int,
        interruptions: Int = 0,
        completed: Boolean = true,
        startedAt: Long = 1_000L,
        endedAt: Long = 2_000L
    ) = FocusSession(
        id = "focus-$endedAt-$actualSeconds",
        dateEpochDay = day,
        startedAtEpochMillis = startedAt,
        actualFocusSeconds = actualSeconds,
        interruptions = interruptions,
        completed = completed,
        endedAtEpochMillis = endedAt
    )

    @Test
    fun netFocusMinutes_sumsActualSecondsWithFloorDivision() {
        // ۱۵۰۰ + ۹۰ = ۱۵۹۰ ثانیه = ۲۶ دقیقه (نه ۲۷؛ هرگز گرد به بالا نمی‌شود).
        val sessions = listOf(session(day = 10, actualSeconds = 1500), session(day = 10, actualSeconds = 90))
        assertEquals(26, FocusSessionLogic.netFocusMinutes(sessions, 10))
    }

    @Test
    fun netFocusMinutes_onlyCountsGivenDay() {
        // تغییر روز: جلسه‌های روز ۱۰ در روز ۱۱ شمرده نمی‌شوند.
        val sessions = listOf(
            session(day = 10, actualSeconds = 1500),
            session(day = 11, actualSeconds = 600)
        )
        assertEquals(25, FocusSessionLogic.netFocusMinutes(sessions, 10))
        assertEquals(10, FocusSessionLogic.netFocusMinutes(sessions, 11))
    }

    @Test
    fun netFocusMinutes_emptyDayIsZero() {
        assertEquals(0, FocusSessionLogic.netFocusMinutes(emptyList(), 10))
        assertEquals(0, FocusSessionLogic.netFocusMinutes(listOf(session(day = 9, actualSeconds = 1500)), 10))
    }

    @Test
    fun interruptionsForDay_sumsInterruptionsOfDayOnly() {
        val sessions = listOf(
            session(day = 10, actualSeconds = 1500, interruptions = 2),
            session(day = 10, actualSeconds = 600, interruptions = 1),
            session(day = 11, actualSeconds = 1500, interruptions = 5)
        )
        assertEquals(3, FocusSessionLogic.interruptionsForDay(sessions, 10))
        assertEquals(5, FocusSessionLogic.interruptionsForDay(sessions, 11))
    }

    @Test
    fun sessionsForDay_newestFirst() {
        val sessions = listOf(
            session(day = 10, actualSeconds = 600, endedAt = 5_000L),
            session(day = 10, actualSeconds = 1500, endedAt = 9_000L),
            session(day = 11, actualSeconds = 1500, endedAt = 9_000L)
        )
        val day10 = FocusSessionLogic.sessionsForDay(sessions, 10)
        assertEquals(2, day10.size)
        assertEquals(9_000L, day10[0].endedAtEpochMillis)
        assertEquals(5_000L, day10[1].endedAtEpochMillis)
    }

    @Test
    fun addSession_capsAtMaxStored_dropsOldest() {
        // فهرست پایه جدیدترین اول است (مثل فروشگاه).
        val base = (5 downTo 1).map { session(day = 10, actualSeconds = 60, endedAt = it * 1_000L) }
        val added = FocusSessionLogic.addSession(base, session(day = 10, actualSeconds = 60, endedAt = 9_000L), maxStored = 3)
        assertEquals(3, added.size)
        assertEquals(9_000L, added[0].endedAtEpochMillis)
        assertEquals(5_000L, added[1].endedAtEpochMillis)
        assertEquals(4_000L, added[2].endedAtEpochMillis)
    }

    // ---------- Store ----------

    @Test
    fun store_roundTrip_persistsAndReloads() {
        val prefs = InMemoryPrefs()
        val store1 = FocusSessionStore(prefs)
        store1.recordFocusInterval(
            actualFocusSeconds = 1500, completed = true, interruptions = 1,
            startedAtEpochMillis = 1_000L, endedAtEpochMillis = 2_000L
        )
        store1.recordFocusInterval(
            actualFocusSeconds = 600, completed = false, interruptions = 0,
            startedAtEpochMillis = 3_000L, endedAtEpochMillis = 4_000L
        )
        // نمونه تازه روی همان فایل prefs: باید همان دو جلسه را بخواند.
        val store2 = FocusSessionStore(prefs)
        val loaded = store2.sessions.value
        assertEquals(2, loaded.size)
        assertEquals(600, loaded[0].actualFocusSeconds)
        assertFalse(loaded[0].completed)
        assertEquals(0, loaded[0].interruptions)
        assertEquals(1500, loaded[1].actualFocusSeconds)
        assertTrue(loaded[1].completed)
        assertEquals(1, loaded[1].interruptions)
        assertEquals(1_000L, loaded[1].startedAtEpochMillis)
        assertEquals(2_000L, loaded[1].endedAtEpochMillis)
    }

    @Test
    fun store_ignoresZeroSecondInterval() {
        val store = FocusSessionStore(InMemoryPrefs())
        store.recordFocusInterval(
            actualFocusSeconds = 0, completed = false, interruptions = 0,
            startedAtEpochMillis = 1_000L, endedAtEpochMillis = 2_000L
        )
        assertTrue(store.sessions.value.isEmpty())
    }

    @Test
    fun store_completedAndPartialBothCountInMinutes() {
        val store = FocusSessionStore(InMemoryPrefs())
        // endedAt روی روز ۰ می‌افتد (epochDayFromMillis(2000) == 0).
        store.recordFocusInterval(
            actualFocusSeconds = 1500, completed = true, interruptions = 0,
            startedAtEpochMillis = 1_000L, endedAtEpochMillis = 2_000L
        )
        store.recordFocusInterval(
            actualFocusSeconds = 90, completed = false, interruptions = 2,
            startedAtEpochMillis = 3_000L, endedAtEpochMillis = 4_000L
        )
        assertEquals(26, store.netFocusMinutes(0))
        assertEquals(2, store.sessionsForDay(0).size)
        assertEquals(2, store.interruptionsForDay(0))
    }
}
