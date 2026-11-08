package com.example.data.focus

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * تست‌های ذخیره‌سازی جلسه تمرکز (§۸) که واقعاً JSON می‌نویسند/می‌خوانند.
 *
 * این دو تست Robolectric هستند، نه JUnit خالص: `FocusSessionStore` با
 * `org.json` ذخیره می‌کند و در تست واحد JVM روی `android.jar`، همه متدهای
 * اندروید (از جمله `org.json`) استاب‌اند و `RuntimeException("Stub!")`
 * می‌اندازند (ران #۸۱/#۸۲: همین دو تست خراب شدند، ۱۶۰ تست دیگر قبول).
 * Robolectric اندروید واقعی (از جمله `org.json` و `SharedPreferences`
 * واقعی) می‌دهد؛ ریپو از قبل Robolectric دارد (`LocalKonkoorStoreTimestampTest`).
 * اسکریپت محلی `compile-focus-test.sh` این فایل را کامپایل نمی‌کند (کلاس‌های
 * Robolectric محلی در دسترس نیستند) — این تست‌ها فقط در CI اجرا می‌شوند.
 */
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class FocusSessionStoreTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        context.getSharedPreferences(TEST_PREFS, Context.MODE_PRIVATE)
            .edit().clear().commit()
    }

    @Test
    fun store_roundTrip_persistsAndReloads() {
        val store1 = FocusSessionStore(context, prefsName = TEST_PREFS)
        store1.recordFocusInterval(
            actualFocusSeconds = 1500, completed = true, interruptions = 1,
            startedAtEpochMillis = 1_000L, endedAtEpochMillis = 2_000L
        )
        store1.recordFocusInterval(
            actualFocusSeconds = 600, completed = false, interruptions = 0,
            startedAtEpochMillis = 3_000L, endedAtEpochMillis = 4_000L
        )
        // نمونه تازه روی همان فایل prefs: باید همان دو جلسه را بخواند.
        val store2 = FocusSessionStore(context, prefsName = TEST_PREFS)
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
    fun store_completedAndPartialBothCountInMinutes() {
        val store = FocusSessionStore(context, prefsName = TEST_PREFS)
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

    companion object {
        private const val TEST_PREFS: String = "test-focus-store"
    }
}
