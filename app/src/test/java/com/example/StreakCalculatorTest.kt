package com.example

import com.example.util.StreakCalculator
import org.junit.Assert.assertEquals
import org.junit.Test

class StreakCalculatorTest {

    // "Today" is fixed in the tests so they never depend on the real clock.
    private val today = StreakCalculator.epochDay(2026, 11, 8)

    @Test
    fun emptySet_hasZeroStreak() {
        assertEquals(0, StreakCalculator.currentStreak(emptySet(), today))
    }

    @Test
    fun singleDayToday_isOne() {
        assertEquals(1, StreakCalculator.currentStreak(setOf(today), today))
    }

    @Test
    fun threeConsecutiveDaysEndingToday_isThree() {
        val days = setOf(today, today - 1, today - 2)
        assertEquals(3, StreakCalculator.currentStreak(days, today))
    }

    @Test
    fun todayMissing_butYesterdayPresent_countsFromYesterday() {
        val days = setOf(today - 1, today - 2)
        assertEquals(2, StreakCalculator.currentStreak(days, today))
    }

    @Test
    fun gap_breaksTheStreak() {
        // Studied today and 3 days ago, but not yesterday or the day before.
        val days = setOf(today, today - 3, today - 4)
        assertEquals(1, StreakCalculator.currentStreak(days, today))
    }

    @Test
    fun gapYesterday_breaksTheStreakEvenWithOlderRun() {
        // A 5-day run ending 2 days ago does not count: the streak is broken.
        val days = setOf(today - 2, today - 3, today - 4, today - 5, today - 6)
        assertEquals(0, StreakCalculator.currentStreak(days, today))
    }

    @Test
    fun futureDays_areIgnored() {
        val days = setOf(today, today - 1, today + 5)
        assertEquals(2, StreakCalculator.currentStreak(days, today))
    }

    @Test
    fun epochDay_isStableAcrossCalls() {
        assertEquals(
            StreakCalculator.epochDay(2026, 11, 8),
            StreakCalculator.epochDay(2026, 11, 8)
        )
        assertEquals(
            StreakCalculator.epochDay(2026, 11, 8) - 1,
            StreakCalculator.epochDay(2026, 11, 7)
        )
    }
}
