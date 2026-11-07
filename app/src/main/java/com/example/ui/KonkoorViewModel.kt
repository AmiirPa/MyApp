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
import com.example.data.firebase.KonkoorCloudRepository
import com.example.data.local.LocalKonkoorStore
import com.example.ui.components.BidiFormatter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
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
    val flashcardProgressMap: Map<String, CloudFlashcardProgress> = emptyMap(),
    val studyTasks: List<CloudStudyTask> = emptyList(),
    val examResults: List<CloudExamResult> = emptyList(),
    val selectedSubjectId: String = "all",
    val activeLessonId: String? = null,
    val selectedGradeFilter: Int = 0,
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

class KonkoorViewModel(
    private val repository: KonkoorCloudRepository?,
    private val currentUserId: String?,
    private val localStore: LocalKonkoorStore? = null
) : ViewModel() {

    private val isCloudAuthenticated: Boolean
        get() = !currentUserId.isNullOrBlank() && repository != null

    private val _selectedSubjectId = MutableStateFlow("all")
    private val _activeLessonId = MutableStateFlow<String?>(null)
    private val _selectedGradeFilter = MutableStateFlow(0)
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
        if (isCloudAuthenticated) {
            viewModelScope.launch {
                repository?.seedInitialCloudRoadmapIfNeeded()
            }
        }
    }

    private val lessonProgressFlow: Flow<List<CloudLessonProgress>> =
        if (isCloudAuthenticated && repository != null && currentUserId != null) {
            repository.observeLessonProgress(currentUserId)
                .catch { e ->
                    Log.w(TAG, "Error observing lesson_progress", e)
                    emit(localStore?.lessonProgress?.value ?: emptyList())
                }
        } else {
            localStore?.lessonProgress ?: emptyFlow()
        }

    private val flashcardProgressFlow: Flow<List<CloudFlashcardProgress>> =
        if (isCloudAuthenticated && repository != null && currentUserId != null) {
            repository.observeFlashcardProgress(currentUserId)
                .catch { e ->
                    Log.w(TAG, "Error observing flashcards", e)
                    emit(localStore?.flashcardProgress?.value ?: emptyList())
                }
        } else {
            localStore?.flashcardProgress ?: emptyFlow()
        }

    private val studyTasksFlow: Flow<List<CloudStudyTask>> =
        if (isCloudAuthenticated && repository != null && currentUserId != null) {
            repository.observeStudyTasks(currentUserId)
                .catch { e ->
                    Log.w(TAG, "Error observing study_tasks", e)
                    emit(localStore?.studyTasks?.value ?: emptyList())
                }
        } else {
            localStore?.studyTasks ?: emptyFlow()
        }

    private val examResultsFlow: Flow<List<CloudExamResult>> =
        if (isCloudAuthenticated && repository != null && currentUserId != null) {
            repository.observeExamResults(currentUserId)
                .catch { e ->
                    Log.w(TAG, "Error observing exam_results", e)
                    emit(localStore?.examResults?.value ?: emptyList())
                }
        } else {
            localStore?.examResults ?: emptyFlow()
        }

    private data class IntermediateState(
        val lessons: List<CloudLessonProgress>,
        val cards: List<CloudFlashcardProgress>,
        val tasks: List<CloudStudyTask>,
        val exams: List<CloudExamResult>
    )

    private val intermediateFlow = combine(
        lessonProgressFlow,
        flashcardProgressFlow,
        studyTasksFlow,
        examResultsFlow
    ) { lessons, cards, tasks, exams ->
        IntermediateState(lessons, cards, tasks, exams)
    }

    private data class UiSettingsState(
        val selectedSubject: String,
        val activeLesson: String?,
        val gradeFilter: Int,
        val tab: AppTab,
        val msg: String?,
        val roadmapSteps: Set<String>
    )

    private data class BaseUiSettings(
        val sub: String,
        val lesson: String?,
        val grade: Int,
        val tab: AppTab,
        val msg: String?
    )

    private val baseUiSettingsFlow = combine(
        _selectedSubjectId,
        _activeLessonId,
        _selectedGradeFilter,
        _activeTab,
        _statusMessage
    ) { sub, lesson, grade, tab, msg ->
        BaseUiSettings(sub, lesson, grade, tab, msg)
    }

    private val uiSettingsFlow = combine(
        baseUiSettingsFlow,
        _completedRoadmapSteps
    ) { base, steps ->
        UiSettingsState(base.sub, base.lesson, base.grade, base.tab, base.msg, steps)
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
        KonkoorUiState(
            lessonProgressMap = state.lessons.associateBy { it.lessonId },
            flashcardProgressMap = state.cards.associateBy { it.cardId },
            studyTasks = sortedTasks,
            examResults = sortedExams,
            selectedSubjectId = settings.selectedSubject,
            activeLessonId = settings.activeLesson,
            selectedGradeFilter = settings.gradeFilter,
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

    fun selectGradeFilter(grade: Int) {
        _selectedGradeFilter.value = grade
        _selectedSubjectId.value = "all"
    }

    fun selectSubject(subjectId: String, navigateToLessons: Boolean = false) {
        _selectedSubjectId.value = subjectId
        if (navigateToLessons) {
            _activeTab.value = AppTab.LESSONS
        }
    }

    fun selectSubjectAndGrade(subjectId: String, grade: Int) {
        _selectedSubjectId.value = subjectId
        _selectedGradeFilter.value = grade
        _activeTab.value = AppTab.HOME
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
        if (isCloudAuthenticated && repository != null) {
            viewModelScope.launch {
                repository.saveLessonProgress(
                    lessonId = lesson.id,
                    subjectId = lesson.subjectId,
                    isCompleted = isCompleted,
                    isBookmarked = isBookmarked,
                    userNote = userNote,
                    masteryPercent = masteryPercent
                )
            }
        }
        _statusMessage.value = if (isCompleted) "درس با موفقیت تکمیل و ذخیره شد!" else "یادداشت و وضعیت درس ذخیره شد."
    }

    fun toggleBookmark(lesson: ZeroLesson) {
        val current = uiState.value.lessonProgressMap[lesson.id]
        val newBookmarked = !(current?.isBookmarked ?: false)
        val isDone = current?.isCompleted ?: false
        val note = current?.userNote ?: ""
        val mastery = current?.masteryPercent ?: (if (isDone) 100 else 50)

        localStore?.saveLessonProgress(
            lessonId = lesson.id,
            subjectId = lesson.subjectId,
            isCompleted = isDone,
            isBookmarked = newBookmarked,
            userNote = note,
            masteryPercent = mastery
        )
        if (isCloudAuthenticated && repository != null) {
            viewModelScope.launch {
                repository.saveLessonProgress(
                    lessonId = lesson.id,
                    subjectId = lesson.subjectId,
                    isCompleted = isDone,
                    isBookmarked = newBookmarked,
                    userNote = note,
                    masteryPercent = mastery
                )
            }
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
        if (isCloudAuthenticated && repository != null) {
            viewModelScope.launch {
                repository.updateFlashcardBox(
                    cardId = card.id,
                    subjectId = card.subjectId,
                    newBox = nextBox,
                    currentReviews = currentReviews
                )
            }
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
        if (isCloudAuthenticated && repository != null) {
            viewModelScope.launch {
                repository.createStudyTask(
                    title = title.trim(),
                    subjectId = subjectId,
                    stageTag = stageTag,
                    durationMinutes = durationMinutes
                )
            }
        }
    }

    fun toggleStudyTask(task: CloudStudyTask) {
        if (task.id.isBlank()) return
        localStore?.toggleStudyTask(task.id, task.isCompleted)
        if (isCloudAuthenticated && repository != null) {
            viewModelScope.launch {
                repository.toggleStudyTask(task.id, task.isCompleted)
            }
        }
    }

    fun deleteStudyTask(taskId: String) {
        if (taskId.isBlank()) return
        localStore?.deleteStudyTask(taskId)
        if (isCloudAuthenticated && repository != null) {
            viewModelScope.launch {
                repository.deleteStudyTask(taskId)
            }
        }
    }

    fun submitExamResult(subjectId: String, subjectTitle: String, correctCount: Int, totalCount: Int) {
        localStore?.recordExamResult(
            subjectId = subjectId,
            subjectTitle = subjectTitle,
            correctCount = correctCount,
            totalCount = totalCount
        )
        if (isCloudAuthenticated && repository != null) {
            viewModelScope.launch {
                repository.recordExamResult(
                    subjectId = subjectId,
                    subjectTitle = subjectTitle,
                    correctCount = correctCount,
                    totalCount = totalCount
                )
            }
        }
    }

    private data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)

    private companion object {
        const val TAG = "KonkoorVM"
    }
}
