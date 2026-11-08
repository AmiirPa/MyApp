package com.example

import com.example.ui.PomodoroState
import com.example.ui.PomodoroTimer
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Tests for the tab-independent Pomodoro timer (PomodoroTimer.kt): the pure
 * one-second transition and the ticker that used to live in remember-state
 * inside SmartSoldierTeacherPlannerCard (and was lost on every tab switch).
 */
class PomodoroTimerTest {

    @Test
    fun tick_decrementsByOneSecondWhileRunning() {
        val state = PomodoroState(secondsLeft = 100, isRunning = true, isBreak = false)
        val next = state.tick()
        assertEquals(99, next.secondsLeft)
        assertTrue(next.isRunning)
        assertFalse(next.isBreak)
    }

    @Test
    fun tick_isNoopWhenPaused() {
        val state = PomodoroState(secondsLeft = 100, isRunning = false, isBreak = false)
        assertEquals(state, state.tick())
    }

    @Test
    fun tick_atZeroFocus_flipsToBreakAndStops() {
        val state = PomodoroState(secondsLeft = 0, isRunning = true, isBreak = false)
        val next = state.tick()
        assertTrue(next.isBreak)
        assertFalse(next.isRunning)
        assertEquals(PomodoroState.BREAK_SECONDS, next.secondsLeft)
        assertEquals(5 * 60, next.secondsLeft)
    }

    @Test
    fun tick_atZeroBreak_flipsBackToFocusAndStops() {
        val state = PomodoroState(secondsLeft = 0, isRunning = true, isBreak = true)
        val next = state.tick()
        assertFalse(next.isBreak)
        assertFalse(next.isRunning)
        assertEquals(PomodoroState.FOCUS_SECONDS, next.secondsLeft)
        assertEquals(25 * 60, next.secondsLeft)
    }

    @Test
    fun ticker_countsDownAndSurvivesPause_withoutLosingTime() = runTest {
        val timer = PomodoroTimer(this)
        assertEquals(PomodoroState(), timer.state.value)

        timer.toggle() // start
        assertTrue(timer.state.value.isRunning)
        advanceTimeBy(2_500L) // ticks at 1000ms and 2000ms
        assertEquals(PomodoroState.FOCUS_SECONDS - 2, timer.state.value.secondsLeft)

        timer.toggle() // pause
        assertFalse(timer.state.value.isRunning)
        advanceTimeBy(10_000L) // an in-flight delay must not decrement while paused
        assertEquals(PomodoroState.FOCUS_SECONDS - 2, timer.state.value.secondsLeft)

        timer.reset()
        assertEquals(PomodoroState(), timer.state.value)
    }

    @Test
    fun ticker_stopsAtZeroAndLoadsBreakInterval() = runTest {
        val timer = PomodoroTimer(this)
        timer.toggle()
        // Fast-forward the whole 25-minute focus interval plus the one extra
        // second in which the 0-second tick flips to the break interval.
        advanceTimeBy(25 * 60 * 1000L + 1_000L)
        val state = timer.state.value
        assertTrue(state.isBreak)
        assertFalse(state.isRunning)
        assertEquals(PomodoroState.BREAK_SECONDS, state.secondsLeft)
    }
}
