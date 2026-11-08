package com.example.data.focus

import com.example.ui.PomodoroState
import com.example.ui.PomodoroTimer
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Tests for the PomodoroTimer session-recorder hook (§۸).
 *
 * The hook is OPTIONAL (default null): without a recorder the timer behaves
 * exactly as before (covered by PomodoroTimerTest, untouched). With a recorder:
 * a completed focus interval records 25 real minutes, a pause counts one
 * interruption, a reset records the actual partial seconds, and break seconds
 * are never recorded.
 */
class FocusTimerHookTest {

    private data class RecordedEvent(
        val plannedFocusSeconds: Int,
        val actualFocusSeconds: Int,
        val completed: Boolean,
        val interruptions: Int
    )

    private class RecordingRecorder : FocusSessionRecorder {
        val events = ArrayList<RecordedEvent>()
        override fun onFocusIntervalEnded(
            plannedFocusSeconds: Int,
            actualFocusSeconds: Int,
            completed: Boolean,
            interruptions: Int,
            startedAtEpochMillis: Long,
            endedAtEpochMillis: Long
        ) {
            events.add(RecordedEvent(plannedFocusSeconds, actualFocusSeconds, completed, interruptions))
        }
    }

    @Test
    fun completingFullFocusInterval_recordsCompletedSession() = runTest {
        val recorder = RecordingRecorder()
        val timer = PomodoroTimer(this).also { it.sessionRecorder = recorder }
        timer.toggle() // start focus
        // ۲۵ دقیقه + ۲ ثانیه: تیکِ چرخش به استراحت یک ثانیه بعد از ۲۵:۰۰ می‌افتد
        // (همان نکته advanceTimeBy در PomodoroTimerTest).
        advanceTimeBy(25 * 60 * 1000L + 2_000L)
        assertTrue(timer.state.value.isBreak)
        assertEquals(1, recorder.events.size)
        val event = recorder.events[0]
        assertEquals(1500, event.plannedFocusSeconds)
        assertEquals(1500, event.actualFocusSeconds)
        assertTrue(event.completed)
        assertEquals(0, event.interruptions)
    }

    @Test
    fun pauseDuringFocus_countsOneInterruption() = runTest {
        val recorder = RecordingRecorder()
        val timer = PomodoroTimer(this).also { it.sessionRecorder = recorder }
        timer.toggle()
        advanceTimeBy(60_000L)
        timer.toggle() // pause: one interruption
        timer.toggle() // resume
        advanceTimeBy(25 * 60 * 1000L + 5_000L)
        assertEquals(1, recorder.events.size)
        val event = recorder.events[0]
        assertEquals(1500, event.actualFocusSeconds)
        assertTrue(event.completed)
        assertEquals(1, event.interruptions)
    }

    @Test
    fun resetMidFocus_recordsPartialAbandonedSession() = runTest {
        val recorder = RecordingRecorder()
        val timer = PomodoroTimer(this).also { it.sessionRecorder = recorder }
        timer.toggle()
        advanceTimeBy(120_000L)
        val elapsed = PomodoroState.FOCUS_SECONDS - timer.state.value.secondsLeft
        assertTrue(elapsed > 0)
        timer.reset()
        assertEquals(1, recorder.events.size)
        val event = recorder.events[0]
        assertEquals(elapsed, event.actualFocusSeconds)
        assertFalse(event.completed)
        assertEquals(0, event.interruptions)
        assertEquals(PomodoroState(), timer.state.value)
    }

    @Test
    fun pauseThenReset_recordsPartialWithInterruption() = runTest {
        val recorder = RecordingRecorder()
        val timer = PomodoroTimer(this).also { it.sessionRecorder = recorder }
        timer.toggle()
        advanceTimeBy(60_000L)
        val elapsed = PomodoroState.FOCUS_SECONDS - timer.state.value.secondsLeft
        timer.toggle() // pause: one interruption
        timer.reset() // abandon: partial with that interruption
        assertEquals(1, recorder.events.size)
        val event = recorder.events[0]
        assertEquals(elapsed, event.actualFocusSeconds)
        assertFalse(event.completed)
        assertEquals(1, event.interruptions)
    }

    @Test
    fun breakSecondsNeverRecorded() = runTest {
        val recorder = RecordingRecorder()
        val timer = PomodoroTimer(this).also { it.sessionRecorder = recorder }
        timer.toggle()
        advanceTimeBy(25 * 60 * 1000L + 2_000L)
        assertEquals(1, recorder.events.size)
        // استراحت را شروع کن و کامل بگذران: چرخش استراحت→تمرکز نباید ثبت شود.
        timer.toggle() // start break
        advanceTimeBy(5 * 60 * 1000L + 2_000L)
        assertFalse(timer.state.value.isBreak)
        assertEquals(1, recorder.events.size)
        // reset روی بازه تمرکز تازه (صفر ثانیه) هم چیزی ثبت نمی‌کند.
        timer.reset()
        assertEquals(1, recorder.events.size)
    }

    @Test
    fun noRecorder_timerBehavesAsBefore() = runTest {
        val timer = PomodoroTimer(this)
        timer.toggle()
        advanceTimeBy(2_500L)
        assertEquals(PomodoroState.FOCUS_SECONDS - 2, timer.state.value.secondsLeft)
        assertTrue(timer.state.value.isRunning)
    }
}
