package com.example.ui.calm

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ui.AppTab
import com.example.ui.KonkoorUiState
import com.example.ui.KonkoorViewModel
import com.example.ui.components.ComprehensiveLexiconAndPoetryHubCard
import com.example.ui.components.HistoryDynastyMemoryVault
import com.example.ui.components.LibraryAndResourcesCatalogView
import com.example.ui.components.SimpleJargonDictionaryCard
import com.example.ui.screens.AddedLessonsScreen
import com.example.ui.screens.CounselingAndMentorshipScreen
import com.example.ui.screens.DailyPlanScreen
import com.example.ui.screens.DiagnosticFlowScreen
import com.example.ui.screens.InteractiveLabsScreen
import com.example.ui.screens.LearningPathScreen
import com.example.ui.screens.MasterKonkoorRoadmapScreen
import com.example.ui.screens.PracticeLeitnerAndQuizScreen
import com.example.ui.screens.ProgressDashboardScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.StudyPlannerAndFarhangianScreen

/**
 * میزبان ابزارهای قدیمی: هر ابزار زیر یک سربرگ ساده با دکمهٔ بازگشت نشان داده می‌شود.
 * صفحه‌های قدیمی دست‌نخورده‌اند؛ فقط به ناوبری تازه وصل شده‌اند.
 */
@Composable
fun ToolHost(
    route: Route.Tool,
    viewModel: KonkoorViewModel,
    uiState: KonkoorUiState,
    nav: CalmNavigator,
    currentUserId: String?,
    userDisplayName: String?,
    onSelectTab: (CalmTab) -> Unit,
    onMessage: (String) -> Unit
) {
    val openLesson: (String) -> Unit = { id -> nav.openLesson(id) }
    val navigateOldTab: (AppTab) -> Unit = { t ->
        when (t) {
            AppTab.HOME -> onSelectTab(CalmTab.TODAY)
            AppTab.LESSONS -> onSelectTab(CalmTab.SUBJECTS)
            AppTab.PRACTICE -> onSelectTab(CalmTab.PRACTICE)
            AppTab.PLANNER -> nav.push(Route.Tool(ToolId.PLANNER))
            AppTab.SETTINGS -> nav.push(Route.Tool(ToolId.ADVANCED_SETTINGS))
            AppTab.ROADMAP -> nav.push(Route.Tool(ToolId.ROADMAP))
            AppTab.LAB -> nav.push(Route.Tool(ToolId.LABS))
            AppTab.COUNSELING -> nav.push(Route.Tool(ToolId.COUNSELING))
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        BackHeader(title = toolTitle(route.tool), onBack = { nav.pop() })
        Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
            when (route.tool) {
                ToolId.PLANNER -> StudyPlannerAndFarhangianScreen(
                    uiState = uiState,
                    onAddTask = { title, subjectId, tag, duration ->
                        viewModel.addStudyTask(title, subjectId, tag, duration)
                    },
                    onToggleTask = { viewModel.toggleStudyTask(it) },
                    onDeleteTask = { viewModel.deleteStudyTask(it) },
                    onOpenLesson = openLesson,
                    onSaveLessonState = { lesson, isCompleted, isBookmarked, note, mastery ->
                        viewModel.saveLessonState(lesson, isCompleted, isBookmarked, note, mastery)
                    }
                )
                ToolId.DAILY_PLAN -> DailyPlanScreen(viewModel = viewModel, uiState = uiState, onOpenLesson = openLesson)
                ToolId.ROADMAP -> MasterKonkoorRoadmapScreen(
                    uiState = uiState,
                    onOpenLesson = openLesson,
                    onSelectSubject = { subjectId -> nav.push(Route.Subject(subjectId)) },
                    onNavigateTab = navigateOldTab,
                    onToggleStep = { stepId -> viewModel.toggleRoadmapStep(stepId) }
                )
                ToolId.PATH_100 -> LearningPathScreen(uiState = uiState, onOpenLesson = openLesson)
                ToolId.LIBRARY -> LibraryAndResourcesCatalogView(
                    allLessons = uiState.allLessons,
                    onOpenLesson = openLesson
                )
                ToolId.LABS -> InteractiveLabsScreen()
                ToolId.COUNSELING -> CounselingAndMentorshipScreen(uiState = uiState, onNavigateTab = navigateOldTab)
                ToolId.ALL_LESSONS -> AddedLessonsScreen(uiState = uiState, onOpenLesson = openLesson)
                ToolId.PROGRESS_REPORT -> ProgressDashboardScreen(viewModel = viewModel)
                ToolId.DIAGNOSTIC -> DiagnosticFlowScreen(viewModel = viewModel, uiState = uiState, onOpenLesson = openLesson)
                ToolId.EXAM_ARCHIVE -> PracticeLeitnerAndQuizScreen(
                    uiState = uiState,
                    onAnswerFlashcard = { card, knew -> viewModel.answerFlashcard(card, knew) },
                    onSubmitExamResult = { id, title, correct, total ->
                        viewModel.submitExamResult(id, title, correct, total)
                    },
                    onSubmitExamAttempt = { attempt -> viewModel.submitExamAttempt(attempt) }
                )
                ToolId.ADVANCED_SETTINGS -> SettingsScreen(
                    currentUserId = currentUserId,
                    userDisplayName = userDisplayName,
                    onShowMessage = onMessage
                )
                ToolId.LEXICON, ToolId.HISTORY_VAULT, ToolId.JARGON -> ScrollingToolColumn {
                    when (route.tool) {
                        ToolId.LEXICON -> ComprehensiveLexiconAndPoetryHubCard()
                        ToolId.HISTORY_VAULT -> HistoryDynastyMemoryVault(accentColor = MaterialTheme.colorScheme.primary)
                        else -> SimpleJargonDictionaryCard(
                            subjectId = route.arg ?: "all",
                            accentColor = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ScrollingToolColumn(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        content()
    }
}
