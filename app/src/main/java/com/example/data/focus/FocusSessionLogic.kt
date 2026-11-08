package com.example.data.focus

/**
 * منطق خالص جلسه‌های تمرکز — بدون Android، بدون I/O، قابل تست واحد.
 *
 * همه محاسبه‌ها فقط روی `actualFocusSeconds` جلسه‌های ثبت‌شده انجام می‌شوند؛
 * هیچ‌چیز حدس زده نمی‌شود: جلسه‌ای که ثبت نشده، صفر دقیقه دارد و ثانیه‌های
 * استراحت جایی در این فهرست نیستند (تایمر آن‌ها را ثبت نمی‌کند).
 */
object FocusSessionLogic {

    /** بیشترین جلسه‌ای که در حافظه محلی نگه داشته می‌شود (قدیمی‌ترین‌ها حذف می‌شوند). */
    const val MAX_STORED_SESSIONS: Int = 2000

    /** جلسه‌های یک روز، جدیدترین اول. */
    fun sessionsForDay(
        sessions: List<FocusSession>,
        dayEpochDay: Long
    ): List<FocusSession> =
        sessions.filter { it.dateEpochDay == dayEpochDay }
            .sortedByDescending { it.endedAtEpochMillis ?: it.startedAtEpochMillis }

    /**
     * دقیقه‌های خالص تمرکز یک روز — فقط ثانیه‌های واقعی، با تقسیم صحیح.
     * تقسیم صحیح یعنی ۹۰ ثانیه = ۱ دقیقه، نه ۲؛ هرگز گرد به بالا نمی‌شود.
     */
    fun netFocusMinutes(sessions: List<FocusSession>, dayEpochDay: Long): Int =
        sessionsForDay(sessions, dayEpochDay).sumOf { it.actualFocusSeconds } / 60

    /** تعداد وقفه‌های (مکث‌های) ثبت‌شده در جلسه‌های یک روز. */
    fun interruptionsForDay(sessions: List<FocusSession>, dayEpochDay: Long): Int =
        sessionsForDay(sessions, dayEpochDay).sumOf { it.interruptions }

    /** افزودن یک جلسه به فهرست (جدیدترین اول) با سقف نگهداری. */
    fun addSession(
        sessions: List<FocusSession>,
        session: FocusSession,
        maxStored: Int = MAX_STORED_SESSIONS
    ): List<FocusSession> =
        (listOf(session) + sessions).take(maxStored)
}
