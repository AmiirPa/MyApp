package com.example.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.curriculum.FlashcardAndQuizBank
import com.example.data.curriculum.FlashcardItem
import com.example.data.curriculum.QuizQuestionItem
import com.example.data.curriculum.SubjectCatalog
import com.example.data.curriculum.SubjectMeta
import com.example.data.curriculum.ZeroLesson
import com.example.data.curriculum.normalizedSubjectId
import com.example.data.firebase.CloudExamResult
import com.example.data.firebase.CloudFlashcardProgress
import com.example.data.firebase.CloudLessonProgress
import com.example.data.firebase.CloudStudyTask
import com.example.data.firebase.CloudSyncTransition
import com.example.data.firebase.KonkoorCloudRepository
import com.example.data.firebase.resolveCloudSyncTransition
import com.example.data.local.LocalKonkoorStore
import com.example.ui.components.BidiFormatter
import com.example.util.StreakCalculator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppTab {
    HOME,
    ROADMAP,
    LESSONS,
    LAB,
    COUNSELING,
    PRACTICE,
    PLANNER,
    SETTINGS
}

data class KonkoorUiState(
    val subjects: List<SubjectMeta> = SubjectCatalog.subjects,
    val lessonProgressMap: Map<String, CloudLessonProgress> = emptyMap(),
    /**
     * Epoch days (see [StreakCalculator]) on which at least one lesson was
     * completed. Union of the locally recorded study days and the days derived
     * from completed lessons' real `updatedAt` timestamps (cloud-synced data).
     * The planner's streak is computed from this set — never from a count.
     */
    val completedStudyDays: Set<Long> = emptySet(),
    val flashcardProgressMap: Map<String, CloudFlashcardProgress> = emptyMap(),
    val studyTasks: List<CloudStudyTask> = emptyList(),
    val examResults: List<CloudExamResult> = emptyList(),
    val selectedSubjectId: String = "all",
    val activeLessonId: String? = null,
    val activeTab: AppTab = AppTab.HOME,
    val statusMessage: String? = null,
    val completedRoadmapSteps: Set<String> = emptySet(),
    val allLessons: List<ZeroLesson> = emptyList(),
    val allFlashcards: List<FlashcardItem> = emptyList(),
    val allQuizQuestions: List<QuizQuestionItem> = emptyList()
) {
    val completedLessonsCount: Int
        get() = lessonProgressMap.values.count { it.isCompleted }

    val totalLessonsCount: Int
        get() = allLessons.size

    val overallProgressFraction: Float
        get() = if (totalLessonsCount > 0) completedLessonsCount.toFloat() / totalLessonsCount else 0f

    val masteredFlashcardsCount: Int
        get() = flashcardProgressMap.values.count { it.leitnerBox >= 4 }

    val bookmarkedLessons: List<ZeroLesson>
        get() = allLessons.filter { lessonProgressMap[it.id]?.isBookmarked == true }

    val bookmarkedLessonsCount: Int
        get() = bookmarkedLessons.size

    fun subjectProgressFraction(subjectId: String): Float {
        val lessonsForSub = allLessons.filter {
            it.normalizedSubjectId() == subjectId
        }
        if (lessonsForSub.isEmpty()) return 0f
        val done = lessonsForSub.count { lessonProgressMap[it.id]?.isCompleted == true }
        return done.toFloat() / lessonsForSub.size
    }
}

/**
 * The constructor arguments are only the *initial* snapshot (whatever auth state existed
 * at launch). They are NOT the live session: [onAuthChanged] must be called on every
 * auth change (MainActivity wires the Firebase AuthStateListener flow to it), and the
 * live session below is what the sync flows and the cloud writes actually read.
 * Freezing `currentUserId` / `repository` here used to mean a user who signed in after
 * launch never got cloud sync.
 */
class KonkoorViewModel(
    repository: KonkoorCloudRepository?,
    currentUserId: String?,
    private val localStore: LocalKonkoorStore? = null
) : ViewModel() {

    private data class CloudSession(val repository: KonkoorCloudRepository, val userId: String)

    private val _cloudSession = MutableStateFlow<CloudSession?>(
        if (!currentUserId.isNullOrBlank() && repository != null) {
            CloudSession(repository, currentUserId)
        } else {
            null
        }
    )
    private val cloudSession: StateFlow<CloudSession?> = _cloudSession.asStateFlow()

    /** Last user id the cloud roadmap was seeded for; cleared on sign-out so a fresh
     * sign-in retries the (server-side idempotent) seed instead of assuming it ran. */
    private var lastSeededUserId: String? = null

    /**
     * Called by MainActivity on every Firebase auth-state emission (including the first
     * one, which is a no-op when it matches the constructor snapshot). START/SWITCH
     * (re)starts cloud sync, STOP cancels the Firestore listeners (via flatMapLatest
     * below) and falls back to the local store; NONE leaves everything untouched.
     */
    fun onAuthChanged(userId: String?, repository: KonkoorCloudRepository?) {
        val previous = _cloudSession.value
        when (
            resolveCloudSyncTransition(
                previousUserId = previous?.userId,
                newUserId = userId,
                previouslyHadCloud = previous != null,
                nowHasCloud = repository != null && !userId.isNullOrBlank()
            )
        ) {
            CloudSyncTransition.NONE -> return
            CloudSyncTransition.STOP -> {
                _cloudSession.value = null
                lastSeededUserId = null
            }
            CloudSyncTransition.START, CloudSyncTransition.SWITCH -> {
                val uid = userId?.takeIf { it.isNotBlank() } ?: return
                val repo = repository ?: return
                val session = CloudSession(repo, uid)
                _cloudSession.value = session
                seedCloudRoadmapIfNeeded(session)
            }
        }
    }

    private fun seedCloudRoadmapIfNeeded(session: CloudSession) {
        if (lastSeededUserId == session.userId) return
        lastSeededUserId = session.userId
        viewModelScope.launch {
            runCatching { session.repository.seedInitialCloudRoadmapIfNeeded() }
                .onFailure { Log.w(TAG, "Seeding cloud roadmap failed", it) }
        }
    }

    /**
     * Runs a cloud write against the *current* session (read at call time, never the
     * frozen constructor values). A failure — including signing out mid-write, where
     * the repository throws from `requireUserId()` — is logged, never crashed.
     */
    private fun launchCloudWrite(block: suspend KonkoorCloudRepository.() -> Unit) {
        val session = _cloudSession.value ?: return
        viewModelScope.launch {
            runCatching { session.repository.block() }
                .onFailure { Log.w(TAG, "Cloud write failed", it) }
        }
    }

    private val _selectedSubjectId = MutableStateFlow("all")
    private val _activeLessonId = MutableStateFlow<String?>(null)
    private val _activeTab = MutableStateFlow(AppTab.HOME)
    private val _statusMessage = MutableStateFlow<String?>(null)
    private val _completedRoadmapSteps = MutableStateFlow(localStore?.getCompletedRoadmapSteps() ?: emptySet())
    private val _curriculumDataFlow = MutableStateFlow(
        Triple(emptyList<ZeroLesson>(), emptyList<FlashcardItem>(), emptyList<QuizQuestionItem>())
    )
    val statusMessage: StateFlow<String?> = _statusMessage.asStateFlow()

    init {
        viewModelScope.launch(kotlinx.coroutines.Dispatchers.Default) {
            val lessons = FlashcardAndQuizBank.allLessons
            val cards = FlashcardAndQuizBank.flashcards
            val quizzes = FlashcardAndQuizBank.quizQuestions
            _curriculumDataFlow.value = Triple(lessons, cards, quizzes)
        }
        _cloudSession.value?.let { seedCloudRoadmapIfNeeded(it) }
    }

    // Each flow below re-selects its source on every session change (flatMapLatest):
    // a new session subscribes to that user's Firestore collections, and leaving a
    // session cancels the previous subscription, so no listener for a signed-out (or
    // previous) user keeps running. While there is no session the local store is used.
    private val lessonProgressFlow: Flow<List<CloudLessonProgress>> =
        cloudSession.flatMapLatest { session ->
            if (session != null) {
                session.repository.observeLessonProgress(session.userId)
                    .catch { e ->
                        Log.w(TAG, "Error observing lesson_progress", e)
                        emit(localStore?.lessonProgress?.value ?: emptyList())
                    }
            } else {
                localStore?.lessonProgress ?: emptyFlow()
            }
        }

    private val flashcardProgressFlow: Flow<List<CloudFlashcardProgress>> =
        cloudSession.flatMapLatest { session ->
            if (session != null) {
                session.repository.observeFlashcardProgress(session.userId)
                    .catch { e ->
                        Log.w(TAG, "Error observing flashcards", e)
                        emit(localStore?.flashcardProgress?.value ?: emptyList())
                    }
            } else {
                localStore?.flashcardProgress ?: emptyFlow()
            }
        }

    private val studyTasksFlow: Flow<List<CloudStudyTask>> =
        cloudSession.flatMapLatest { session ->
            if (session != null) {
                session.repository.observeStudyTasks(session.userId)
                    .catch { e ->
                        Log.w(TAG, "Error observing study_tasks", e)
                        emit(localStore?.studyTasks?.value ?: emptyList())
                    }
            } else {
                localStore?.studyTasks ?: emptyFlow()
            }
        }

    private val examResultsFlow: Flow<List<CloudExamResult>> =
        cloudSession.flatMapLatest { session ->
            if (session != null) {
                session.repository.observeExamResults(session.userId)
                    .catch { e ->
                        Log.w(TAG, "Error observing exam_results", e)
                        emit(localStore?.examResults?.value ?: emptyList())
                    }
            } else {
                localStore?.examResults ?: emptyFlow()
            }
        }

    private val completedDaysFlow: Flow<Set<Long>> =
        localStore?.completedDays ?: flowOf(emptySet())

    private data class IntermediateState(
        val lessons: List<CloudLessonProgress>,
        val cards: List<CloudFlashcardProgress>,
        val tasks: List<CloudStudyTask>,
        val exams: List<CloudExamResult>,
        val completedDays: Set<Long>
    )

    private val intermediateFlow = combine(
        lessonProgressFlow,
        flashcardProgressFlow,
        studyTasksFlow,
        examResultsFlow,
        completedDaysFlow
    ) { lessons, cards, tasks, exams, completedDays ->
        IntermediateState(lessons, cards, tasks, exams, completedDays)
    }

    // NOTE (dead grade filter removed): KonkoorUiState used to carry a
    // selectedGradeFilter (Int) fed by _selectedGradeFilter, with
    // selectGradeFilter()/selectSubjectAndGrade() writers and onSelectGradeFilter /
    // onSelectSubjectAndGrade callbacks plumbed from MainActivity into
    // HomeDashboardScreen. No composable ever read uiState.selectedGradeFilter and
    // HomeDashboardScreen never invoked either callback, so the whole filter was
    // write-only state: selecting a grade changed nothing on screen. Removed
    // entirely (state, writers, callbacks) — behaviour-preserving.
    private data class UiSettingsState(
        val selectedSubject: String,
        val activeLesson: String?,
        val tab: AppTab,
        val msg: String?,
        val roadmapSteps: Set<String>
    )

    private data class BaseUiSettings(
        val sub: String,
        val lesson: String?,
        val tab: AppTab,
        val msg: String?
    )

    private val baseUiSettingsFlow = combine(
        _selectedSubjectId,
        _activeLessonId,
        _activeTab,
        _statusMessage
    ) { sub, lesson, tab, msg ->
        BaseUiSettings(sub, lesson, tab, msg)
    }

    private val uiSettingsFlow = combine(
        baseUiSettingsFlow,
        _completedRoadmapSteps
    ) { base, steps ->
        UiSettingsState(base.sub, base.lesson, base.tab, base.msg, steps)
    }

    val uiState: StateFlow<KonkoorUiState> = combine(
        intermediateFlow,
        uiSettingsFlow,
        _curriculumDataFlow
    ) { state, settings, curriculum ->
        val sortedTasks = state.tasks.sortedWith(
            compareBy<CloudStudyTask> { it.isCompleted }
                .thenByDescending { it.createdAt?.seconds ?: 0L }
        )
        val sortedExams = state.exams.sortedByDescending { it.createdAt?.seconds ?: 0L }
        val studyDaysFromProgress = state.lessons
            .filter { it.isCompleted }
            .mapNotNull { it.updatedAt }
            .map { StreakCalculator.epochDayFromMillis(it.seconds * 1000L) }
            .toSet()
        KonkoorUiState(
            lessonProgressMap = state.lessons.associateBy { it.lessonId },
            completedStudyDays = state.completedDays + studyDaysFromProgress,
            flashcardProgressMap = state.cards.associateBy { it.cardId },
            studyTasks = sortedTasks,
            examResults = sortedExams,
            selectedSubjectId = settings.selectedSubject,
            activeLessonId = settings.activeLesson,
            activeTab = settings.tab,
            statusMessage = settings.msg,
            completedRoadmapSteps = settings.roadmapSteps,
            allLessons = curriculum.first,
            allFlashcards = curriculum.second,
            allQuizQuestions = curriculum.third
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = KonkoorUiState()
    )

    fun toggleRoadmapStep(stepId: String) {
        val current = _completedRoadmapSteps.value
        val isNowCompleted = !current.contains(stepId)
        localStore?.setRoadmapStepCompleted(stepId, isNowCompleted)
        _completedRoadmapSteps.value = localStore?.getCompletedRoadmapSteps()
            ?: (if (isNowCompleted) current + stepId else current - stepId)
    }

    fun selectTab(tab: AppTab) {
        if (tab == AppTab.SETTINGS) {
            _activeLessonId.value = null
        }
        _activeTab.value = tab
    }

    fun selectSubject(subjectId: String, navigateToLessons: Boolean = false) {
        _selectedSubjectId.value = subjectId
        if (navigateToLessons) {
            _activeTab.value = AppTab.LESSONS
        }
    }

    fun openLesson(lessonId: String?) {
        _activeLessonId.value = lessonId
    }

    fun clearStatusMessage() {
        _statusMessage.value = null
    }

    fun showMessage(message: String) {
        _statusMessage.value = message
    }

    fun saveLessonState(
        lesson: ZeroLesson,
        isCompleted: Boolean,
        isBookmarked: Boolean,
        userNote: String,
        masteryPercent: Int
    ) {
        localStore?.saveLessonProgress(
            lessonId = lesson.id,
            subjectId = lesson.subjectId,
            isCompleted = isCompleted,
            isBookmarked = isBookmarked,
            userNote = userNote,
            masteryPercent = masteryPercent
        )
        launchCloudWrite {
            saveLessonProgress(
                lessonId = lesson.id,
                subjectId = lesson.subjectId,
                isCompleted = isCompleted,
                isBookmarked = isBookmarked,
                userNote = userNote,
                masteryPercent = masteryPercent
            )
        }
        _statusMessage.value = if (isCompleted) "درس با موفقیت تکمیل و ذخیره شد!" else "یادداشت و وضعیت درس ذخیره شد."
    }

    fun toggleBookmark(lesson: ZeroLesson) {
        val current = uiState.value.lessonProgressMap[lesson.id]
        val newBookmarked = !(current?.isBookmarked ?: false)
        val isDone = current?.isCompleted ?: false
        val note = current?.userNote ?: ""
        val mastery = current?.masteryPercent ?: 0

        localStore?.saveLessonProgress(
            lessonId = lesson.id,
            subjectId = lesson.subjectId,
            isCompleted = isDone,
            isBookmarked = newBookmarked,
            userNote = note,
            masteryPercent = mastery
        )
        launchCloudWrite {
            saveLessonProgress(
                lessonId = lesson.id,
                subjectId = lesson.subjectId,
                isCompleted = isDone,
                isBookmarked = newBookmarked,
                userNote = note,
                masteryPercent = mastery
            )
        }
        _statusMessage.value = if (newBookmarked) "درس به نشان‌شده‌ها و علاقه‌مندی‌ها افزوده شد ⭐" else "درس از نشان‌شده‌ها حذف شد."
    }

    fun answerFlashcard(card: FlashcardItem, knewIt: Boolean) {
        val current = uiState.value.flashcardProgressMap[card.id]
        val currentBox = current?.leitnerBox ?: 1
        val currentReviews = current?.reviewCount ?: 0
        val nextBox = if (knewIt) (currentBox + 1).coerceAtMost(5) else 1

        localStore?.updateFlashcardBox(
            cardId = card.id,
            subjectId = card.subjectId,
            newBox = nextBox,
            currentReviews = currentReviews
        )
        launchCloudWrite {
            updateFlashcardBox(
                cardId = card.id,
                subjectId = card.subjectId,
                newBox = nextBox,
                currentReviews = currentReviews
            )
        }
    }

    fun addStudyTask(title: String, subjectId: String, stageTag: String, durationMinutes: Int) {
        if (title.isBlank()) return
        localStore?.createStudyTask(
            title = title.trim(),
            subjectId = subjectId,
            stageTag = stageTag,
            durationMinutes = durationMinutes
        )
        launchCloudWrite {
            createStudyTask(
                title = title.trim(),
                subjectId = subjectId,
                stageTag = stageTag,
                durationMinutes = durationMinutes
            )
        }
    }

    fun toggleStudyTask(task: CloudStudyTask) {
        if (task.id.isBlank()) return
        localStore?.toggleStudyTask(task.id, task.isCompleted)
        launchCloudWrite { toggleStudyTask(task.id, task.isCompleted) }
    }

    fun deleteStudyTask(taskId: String) {
        if (taskId.isBlank()) return
        localStore?.deleteStudyTask(taskId)
        launchCloudWrite { deleteStudyTask(taskId) }
    }

    fun submitExamResult(subjectId: String, subjectTitle: String, correctCount: Int, totalCount: Int) {
        localStore?.recordExamResult(
            subjectId = subjectId,
            subjectTitle = subjectTitle,
            correctCount = correctCount,
            totalCount = totalCount
        )
        launchCloudWrite {
            recordExamResult(
                subjectId = subjectId,
                subjectTitle = subjectTitle,
                correctCount = correctCount,
                totalCount = totalCount
            )
        }
    }

    private data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)

    private companion object {
        const val TAG = "KonkoorVM"
    }
}
