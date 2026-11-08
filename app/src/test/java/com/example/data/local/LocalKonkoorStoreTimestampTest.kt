package com.example.data.local

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.firebase.Timestamp
import org.json.JSONArray
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Round-trip tests for the timestamp fix in LocalKonkoorStore (problem 4):
 * the real timestamp is recorded at save time, persisted as epoch millis, and
 * preserved on load — never replaced with "now" — and a record that carries
 * no timestamp loads as null ("not enough data"), not as a fabricated time.
 *
 * Persistence is millis-precision: a saved Timestamp keeps its millis value
 * exactly across a store reload (simulated app restart), which is what these
 * tests assert. The fixed-past-timestamp tests prove load does not fabricate
 * "now": a 2020 value stored on disk must still read back as 2020.
 *
 * NOTE: the SharedPreferences name and JSON keys below mirror the store's
 * private constants (PREFS_NAME / KEY_FLASHCARDS / KEY_TASKS / KEY_EXAMS);
 * if those ever change, update the literals here too.
 */
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class LocalKonkoorStoreTimestampTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit().clear().commit()
    }

    private fun prefs() =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private fun millis(ts: Timestamp?): Long? =
        ts?.let { it.seconds * 1000L + it.nanoseconds / 1_000_000 }

    private fun putRawJson(key: String, json: String) {
        prefs().edit().putString(key, json).apply()
    }

    // --- flashcards ---

    @Test
    fun flashcardUpdatedAt_roundTrip_preservesSavedMillis() {
        val first = LocalKonkoorStore(context)
        first.updateFlashcardBox("card-1", "math", 2, 0)
        val savedMillis =
            millis(first.flashcardProgress.value.first { it.cardId == "card-1" }.updatedAt)

        // Simulate an app restart: a new store instance reloads from disk.
        val reloaded = LocalKonkoorStore(context)
        val reloadedMillis =
            millis(reloaded.flashcardProgress.value.first { it.cardId == "card-1" }.updatedAt)
        assertEquals(savedMillis, reloadedMillis)
    }

    @Test
    fun flashcardPersist_writesRealUpdatedAtMillis() {
        val store = LocalKonkoorStore(context)
        store.updateFlashcardBox("card-1", "math", 2, 0)
        val savedMillis =
            millis(store.flashcardProgress.value.first { it.cardId == "card-1" }.updatedAt)
        val raw = prefs().getString(KEY_FLASHCARDS, null)!!
        val storedMillis = JSONArray(raw).getJSONObject(0).getLong("updatedAtMillis")
        assertEquals(savedMillis, storedMillis)
    }

    @Test
    fun flashcard_fixedPastTimestamp_survivesReloadUnchanged() {
        val pastMillis = 1577836800000L // 2020-01-01T00:00:00Z
        putRawJson(
            KEY_FLASHCARDS,
            JSONArray().put(
                JSONObject().apply {
                    put("cardId", "card-old")
                    put("subjectId", "math")
                    put("leitnerBox", 3)
                    put("reviewCount", 5)
                    put("updatedAtMillis", pastMillis)
                }
            ).toString()
        )
        val store = LocalKonkoorStore(context)
        val loaded = store.flashcardProgress.value.first { it.cardId == "card-old" }
        assertEquals(pastMillis, millis(loaded.updatedAt))
    }

    @Test
    fun flashcard_legacyRecordWithoutTimestamp_loadsNull() {
        putRawJson(
            KEY_FLASHCARDS,
            JSONArray().put(
                JSONObject().apply {
                    put("cardId", "card-legacy")
                    put("subjectId", "math")
                    put("leitnerBox", 1)
                    put("reviewCount", 0)
                    // No updatedAtMillis on purpose: pre-fix records carry none.
                }
            ).toString()
        )
        val store = LocalKonkoorStore(context)
        val loaded = store.flashcardProgress.value.first { it.cardId == "card-legacy" }
        assertNull(loaded.updatedAt)
    }

    // --- study tasks ---

    @Test
    fun taskCreatedAt_roundTrip_preservesSavedMillis() {
        val first = LocalKonkoorStore(context)
        first.createStudyTask("تمرین تست", "math", "پایه", 30)
        val created = first.studyTasks.value.first { it.title == "تمرین تست" }
        val savedMillis = millis(created.createdAt)

        val reloaded = LocalKonkoorStore(context)
        val reloadedMillis =
            millis(reloaded.studyTasks.value.first { it.id == created.id }.createdAt)
        assertEquals(savedMillis, reloadedMillis)
    }

    @Test
    fun task_fixedPastTimestamp_survivesReloadUnchanged() {
        val pastMillis = 1577836800000L // 2020-01-01T00:00:00Z
        putRawJson(
            KEY_TASKS,
            JSONArray().put(
                JSONObject().apply {
                    put("id", "task-old")
                    put("title", "وظیفه قدیمی")
                    put("subjectId", "math")
                    put("stageTag", "پایه")
                    put("durationMinutes", 30)
                    put("isCompleted", false)
                    put("createdAtMillis", pastMillis)
                }
            ).toString()
        )
        val store = LocalKonkoorStore(context)
        val loaded = store.studyTasks.value.first { it.id == "task-old" }
        assertEquals(pastMillis, millis(loaded.createdAt))
    }

    @Test
    fun task_legacyRecordWithoutTimestamp_loadsNull() {
        putRawJson(
            KEY_TASKS,
            JSONArray().put(
                JSONObject().apply {
                    put("id", "task-legacy")
                    put("title", "وظیفه قدیمی")
                    put("subjectId", "math")
                    put("stageTag", "پایه")
                    put("durationMinutes", 30)
                    put("isCompleted", false)
                    // No createdAtMillis on purpose.
                }
            ).toString()
        )
        val store = LocalKonkoorStore(context)
        val loaded = store.studyTasks.value.first { it.id == "task-legacy" }
        assertNull(loaded.createdAt)
    }

    // --- exam results ---

    @Test
    fun examCreatedAt_roundTrip_preservesSavedMillis() {
        val first = LocalKonkoorStore(context)
        first.recordExamResult("konkoor_1405", "کنکور ۱۴۰۵", 12, 20)
        val recorded = first.examResults.value.first()
        val savedMillis = millis(recorded.createdAt)

        val reloaded = LocalKonkoorStore(context)
        val reloadedMillis =
            millis(reloaded.examResults.value.first { it.id == recorded.id }.createdAt)
        assertEquals(savedMillis, reloadedMillis)
    }

    @Test
    fun exam_fixedPastTimestamp_survivesReloadUnchanged() {
        val pastMillis = 1577836800000L // 2020-01-01T00:00:00Z
        putRawJson(
            KEY_EXAMS,
            JSONArray().put(
                JSONObject().apply {
                    put("id", "exam-old")
                    put("subjectId", "konkoor_1405")
                    put("subjectTitle", "کنکور ۱۴۰۵")
                    put("correctCount", 12)
                    put("totalCount", 20)
                    put("scorePercent", 60)
                    put("createdAtMillis", pastMillis)
                }
            ).toString()
        )
        val store = LocalKonkoorStore(context)
        val loaded = store.examResults.value.first { it.id == "exam-old" }
        assertEquals(pastMillis, millis(loaded.createdAt))
    }

    @Test
    fun exam_legacyRecordWithoutTimestamp_loadsNull() {
        putRawJson(
            KEY_EXAMS,
            JSONArray().put(
                JSONObject().apply {
                    put("id", "exam-legacy")
                    put("subjectId", "konkoor_1405")
                    put("subjectTitle", "کنکور ۱۴۰۵")
                    put("correctCount", 12)
                    put("totalCount", 20)
                    put("scorePercent", 60)
                    // No createdAtMillis on purpose.
                }
            ).toString()
        )
        val store = LocalKonkoorStore(context)
        val loaded = store.examResults.value.first { it.id == "exam-legacy" }
        assertNull(loaded.createdAt)
    }

    // --- lessons (legacy now() fallback removed: old records without
    // updatedAtMillis must load null, not a fabricated "now") ---

    @Test
    fun lesson_legacyRecordWithoutTimestamp_loadsNull() {
        putRawJson(
            KEY_LESSONS,
            JSONArray().put(
                JSONObject().apply {
                    put("lessonId", "lesson-legacy")
                    put("subjectId", "math")
                    put("isCompleted", true)
                    put("isBookmarked", false)
                    put("userNote", "")
                    put("masteryPercent", 0)
                    // No updatedAtMillis on purpose: pre-fix records carry none.
                }
            ).toString()
        )
        val store = LocalKonkoorStore(context)
        val loaded = store.lessonProgress.value.first { it.lessonId == "lesson-legacy" }
        assertNull(loaded.updatedAt)
    }

    companion object {
        // Mirrors LocalKonkoorStore's private constants.
        private const val PREFS_NAME = "konkoor_local_offline_store"
        private const val KEY_FLASHCARDS = "flashcards_json"
        private const val KEY_TASKS = "tasks_json"
        private const val KEY_EXAMS = "exams_json"
        private const val KEY_LESSONS = "lessons_json"
    }
}
