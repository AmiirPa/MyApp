package com.example.util

import java.util.Calendar

/**
 * Real study-streak calculator.
 *
 * A streak is the number of consecutive calendar days (in the device's local
 * timezone) on which the user completed at least one lesson, ending today —
 * or ending yesterday when nothing has been completed yet today.
 *
 * This replaces the old fabricated formula
 * `(totalCompletedLessons / 3 + 1).coerceIn(1, 120)`, which invented a streak
 * from a bare lesson count and never looked at a single date.
 *
 * Pure functions only (no Android, no I/O) so the logic is unit-testable.
 * Uses [java.util.Calendar] (not java.time) because minSdk is 24.
 */
object StreakCalculator {

    private const val MILLIS_PER_DAY = 24L * 60L * 60L * 1000L

    /** Epoch day number (days since 1970-01-01) for a local calendar date. */
    fun epochDay(year: Int, month: Int, day: Int): Long {
        val cal = Calendar.getInstance()
        cal.set(year, month - 1, day, 12, 0, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return epochDayFromMillis(cal.timeInMillis)
    }

    /** Epoch day number for a wall-clock instant, in the device's local timezone. */
    fun epochDayFromMillis(millis: Long): Long {
        val offset = java.util.TimeZone.getDefault().getOffset(millis).toLong()
        return Math.floorDiv(millis + offset, MILLIS_PER_DAY)
    }

    /** Today's epoch day number, in the device's local timezone. */
    fun todayEpochDay(): Long = epochDayFromMillis(System.currentTimeMillis())

    /**
     * Current consecutive-day streak.
     *
     * @param completedDays epoch days on which at least one lesson was completed.
     * @param todayEpochDay today's epoch day (defaults to [todayEpochDay]).
     * @return 0 when there is no recorded study day at all, otherwise the length
     * of the unbroken run ending today (or yesterday, if today is not in the set yet).
     * Days in the future are ignored.
     */
    fun currentStreak(completedDays: Set<Long>, todayEpochDay: Long = todayEpochDay()): Int {
        if (completedDays.isEmpty()) return 0
        var cursor = if (todayEpochDay in completedDays) todayEpochDay else todayEpochDay - 1
        var streak = 0
        while (cursor in completedDays) {
            streak++
            cursor--
        }
        return streak
    }
}
