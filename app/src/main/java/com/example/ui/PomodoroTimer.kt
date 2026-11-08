package com.example.ui

import com.example.data.focus.FocusSessionRecorder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Pomodoro timer state. Pure data + a pure one-second transition ([tick]),
 * so the countdown rules are unit-testable without Android or a clock.
 *
 * Rules (identical to the old in-composable implementation):
 * - while running, each tick removes one second;
 * - at 0 the timer stops, flips focus<->break and reloads the next interval
 *   (25 min focus, 5 min break).
 */
data class PomodoroState(
    val secondsLeft: Int = FOCUS_SECONDS,
    val isRunning: Boolean = false,
    val isBreak: Boolean = false
) {
    companion object {
        const val FOCUS_SECONDS: Int = 25 * 60
        const val BREAK_SECONDS: Int = 5 * 60
    }

    fun tick(): PomodoroState {
        if (!isRunning) return this
        if (secondsLeft > 0) return copy(secondsLeft = secondsLeft - 1)
        val nextBreak = !isBreak
        return copy(
            isRunning = false,
            isBreak = nextBreak,
            secondsLeft = if (nextBreak) BREAK_SECONDS else FOCUS_SECONDS
        )
    }
}

/**
 * Tab-independent Pomodoro timer.
 *
 * The timer used to live in `remember { ... }` state inside
 * SmartSoldierTeacherPlannerCard, so switching tabs disposed the composition
 * and the countdown (and its running/paused state) was silently lost. This
 * holder lives outside any tab's composition: the state and the ticker survive
 * tab switches, and the card only collects [state]. The [scope] is injectable
 * so unit tests can drive the ticker with a TestScope.
 */
class PomodoroTimer(
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
) {
    private val _state = MutableStateFlow(PomodoroState())
    val state: StateFlow<PomodoroState> = _state.asStateFlow()

    private var tickerJob: Job? = null

    /**
     * قلاب اختیاری ثبت جلسه تمرکز (§۸ الحاقیه). پیش‌فرض null است — بدون اتصال،
     * تایمر دقیقاً مثل قبل کار می‌کند. وقتی وصل باشد، فقط پایان یک بازه تمرکز
     * ثبت می‌شود: بازه کامل (رسیدن به صفر) یا رها شده با reset (با همان
     * ثانیه‌های واقعی انجام‌شده). مکث در طول بازه، یک وقفه شمرده می‌شود؛
     * ثانیه‌های استراحت هرگز ثبت نمی‌شوند و تایمر هرگز ادعا نمی‌کند که می‌تواند
     * اپ‌های دیگر را قفل کند.
     */
    var sessionRecorder: FocusSessionRecorder? = null

    /** وقفه‌های بازه تمرکز جاری (هر مکث = یک وقفه، فقط وقتی تمرکز واقعی انجام شده باشد). */
    private var focusInterruptions: Int = 0

    /** شروع بازه تمرکز جاری (wall-clock)؛ null یعنی بازه‌ای باز نیست. */
    private var focusStartedAtMillis: Long? = null

    /** Start/resume, or pause if already running. */
    fun toggle() {
        if (_state.value.isRunning) {
            val before = _state.value
            _state.update { it.copy(isRunning = false) }
            tickerJob?.cancel()
            tickerJob = null
            // مکث در طول یک بازه تمرکز که تمرکز واقعی در آن انجام شده = یک وقفه.
            // مکث پیش از شروع تمرکز (همه ثانیه‌ها باقی‌اند) وقفه نیست.
            if (!before.isBreak && before.secondsLeft < PomodoroState.FOCUS_SECONDS) {
                focusInterruptions++
            }
        } else {
            val before = _state.value
            // شروع یک بازه تمرکز تازه (نه ازسرگیری بعد از مکث).
            if (!before.isBreak && focusStartedAtMillis == null) {
                focusStartedAtMillis = System.currentTimeMillis()
                focusInterruptions = 0
            }
            _state.update { it.copy(isRunning = true) }
            tickerJob?.cancel()
            tickerJob = scope.launch {
                while (_state.value.isRunning) {
                    delay(1000L)
                    // tick() is a no-op when paused, so a delay that was already
                    // in flight when the user paused cannot decrement the timer.
                    val beforeTick = _state.value
                    _state.update { it.tick() }
                    val afterTick = _state.value
                    // پایان بازه تمرکز: تیکِ صفر، تمرکز را به استراحت می‌چرخاند.
                    if (beforeTick.isRunning && !beforeTick.isBreak &&
                        beforeTick.secondsLeft == 0 && afterTick.isBreak
                    ) {
                        val endedAt = System.currentTimeMillis()
                        sessionRecorder?.onFocusIntervalEnded(
                            plannedFocusSeconds = PomodoroState.FOCUS_SECONDS,
                            actualFocusSeconds = PomodoroState.FOCUS_SECONDS,
                            completed = true,
                            interruptions = focusInterruptions,
                            startedAtEpochMillis = focusStartedAtMillis ?: endedAt,
                            endedAtEpochMillis = endedAt
                        )
                        focusStartedAtMillis = null
                        focusInterruptions = 0
                    }
                }
            }
        }
    }

    /** Stop and restore the initial 25-minute focus interval. */
    fun reset() {
        val before = _state.value
        tickerJob?.cancel()
        tickerJob = null
        // رها کردن بازه تمرکز: اگر تمرکز واقعی انجام شده، با همان ثانیه‌ها
        // به‌عنوان جلسه نیمه‌کاره ثبت می‌شود؛ استراحت یا بازه صفرثانیه‌ای ثبت نمی‌شود.
        if (!before.isBreak && focusStartedAtMillis != null) {
            val elapsed = PomodoroState.FOCUS_SECONDS - before.secondsLeft
            if (elapsed > 0) {
                val endedAt = System.currentTimeMillis()
                sessionRecorder?.onFocusIntervalEnded(
                    plannedFocusSeconds = PomodoroState.FOCUS_SECONDS,
                    actualFocusSeconds = elapsed,
                    completed = false,
                    interruptions = focusInterruptions,
                    startedAtEpochMillis = focusStartedAtMillis ?: endedAt,
                    endedAtEpochMillis = endedAt
                )
            }
        }
        focusStartedAtMillis = null
        focusInterruptions = 0
        _state.value = PomodoroState()
    }
}

/**
 * The single process-wide timer used by the study planner card. Kept outside
 * the ViewModel on purpose: the card is hosted through PracticeAndPlannerScreen
 * (owned by another collaborator), so threading ViewModel callbacks through it
 * would require edits to that file; the shared holder needs no signature
 * changes at any call site. If that screen is ever reworked, the state can move
 * into KonkoorViewModel unchanged — [PomodoroState.tick] already encodes the
 * whole transition.
 */
val sharedPomodoroTimer: PomodoroTimer by lazy { PomodoroTimer() }
