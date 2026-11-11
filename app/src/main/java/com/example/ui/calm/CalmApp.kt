package com.example.ui.calm

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.KonkoorViewModel

/** چهار تب اصلی — نه بیشتر. */
enum class CalmTab(val label: String, val icon: ImageVector) {
    TODAY("امروز", Icons.Outlined.WbSunny),
    SUBJECTS("دروس", Icons.AutoMirrored.Outlined.MenuBook),
    PRACTICE("تمرین", Icons.Outlined.EditNote),
    ME("من", Icons.Outlined.Person)
}

/** ابزارهایی که از صفحهٔ اصلی بیرون آمده‌اند و فقط از «ابزارها» در دسترس‌اند. */
enum class ToolId {
    PLANNER, DAILY_PLAN, ROADMAP, PATH_100, LABS, LEXICON, LIBRARY, COUNSELING,
    ALL_LESSONS, HISTORY_VAULT, PROGRESS_REPORT, DIAGNOSTIC, EXAM_ARCHIVE, ADVANCED_SETTINGS, JARGON
}

sealed interface Route {
    val key: String

    data class Subject(val subjectId: String) : Route { override val key get() = "subject/$subjectId" }
    data class Chapter(val subjectId: String, val chapterKey: String) : Route { override val key get() = "chapter/$subjectId/$chapterKey" }
    data class Lesson(val lessonId: String) : Route { override val key get() = "lesson/$lessonId" }
    data object Review : Route { override val key get() = "review" }
    data class Flashcards(val subjectId: String?) : Route { override val key get() = "flash/$subjectId" }
    data class Quiz(val subjectId: String?) : Route { override val key get() = "quiz/$subjectId" }
    data class PickSubject(val forQuiz: Boolean) : Route { override val key get() = "pick/$forQuiz" }
    data object Tools : Route { override val key get() = "tools" }
    data class Tool(val tool: ToolId, val arg: String? = null) : Route { override val key get() = "tool/$tool/$arg" }
}

/** ناوبری سادهٔ پشته‌ای. */
class CalmNavigator {
    val stack = mutableStateListOf<Route>()
    fun push(route: Route) {
        if (stack.lastOrNull()?.key == route.key) return
        stack.add(route)
    }
    fun replaceTop(route: Route) {
        if (stack.isEmpty()) stack.add(route) else stack[stack.lastIndex] = route
    }
    fun pop(): Boolean {
        if (stack.isEmpty()) return false
        stack.removeAt(stack.lastIndex)
        return true
    }
    fun clear() = stack.clear()
    fun openLesson(id: String) {
        val top = stack.lastOrNull()
        if (top is Route.Lesson) replaceTop(Route.Lesson(id)) else push(Route.Lesson(id))
    }
}

private data class ScreenKey(val tab: CalmTab, val route: Route?, val depth: Int) {
    val key: String get() = route?.key ?: "tab/${tab.name}"
}

@Composable
fun CalmAppRoot(
    viewModel: KonkoorViewModel,
    currentUserId: String?,
    userDisplayName: String?
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbar = remember { SnackbarHostState() }
    val nav = remember { CalmNavigator() }
    var tabName by rememberSaveable { mutableStateOf(CalmTab.TODAY.name) }
    val tab = CalmTab.valueOf(tabName)

    // هر جای قدیمی که درسی را با openLesson باز کند، در ناوبری تازه هم باز می‌شود.
    LaunchedEffect(uiState.activeLessonId) {
        val id = uiState.activeLessonId
        if (id != null) {
            nav.openLesson(id)
            viewModel.openLesson(null)
        }
    }

    LaunchedEffect(uiState.statusMessage) {
        val msg = uiState.statusMessage
        if (!msg.isNullOrBlank()) {
            viewModel.clearStatusMessage()
            snackbar.showSnackbar(msg)
        }
    }

    BackHandler(enabled = nav.stack.isNotEmpty() || tab != CalmTab.TODAY) {
        if (!nav.pop()) tabName = CalmTab.TODAY.name
    }

    val top = nav.stack.lastOrNull()
    val immersive = top is Route.Lesson || top is Route.Quiz || top is Route.Flashcards
    val screen = ScreenKey(tab, top, nav.stack.size)

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets.systemBars,
        snackbarHost = { SnackbarHost(snackbar) },
        bottomBar = {
            AnimatedVisibility(
                visible = !immersive,
                enter = slideInVertically(tween(220)) { it } + fadeIn(tween(220)),
                exit = slideOutVertically(tween(180)) { it } + fadeOut(tween(180))
            ) {
                androidx.compose.foundation.layout.Column {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.surface,
                        tonalElevation = 0.dp,
                        modifier = Modifier.testTag("app_bottom_nav_bar")
                    ) {
                        CalmTab.entries.forEach { t ->
                            val selected = t == tab
                            NavigationBarItem(
                                selected = selected,
                                onClick = {
                                    nav.clear()
                                    tabName = t.name
                                },
                                icon = { Icon(t.icon, contentDescription = t.label) },
                                label = { Text(t.label, style = MaterialTheme.typography.labelMedium) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = MaterialTheme.colorScheme.primary,
                                    selectedTextColor = MaterialTheme.colorScheme.primary,
                                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                modifier = Modifier.testTag("nav_tab_${t.name.lowercase()}")
                            )
                        }
                    }
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding)
        ) {
            AnimatedContent(
                targetState = screen,
                contentKey = { it.key },
                transitionSpec = {
                    when {
                        // جلو رفتن در RTL: صفحهٔ تازه از سمت چپ می‌آید.
                        targetState.depth > initialState.depth ->
                            (slideInHorizontally(tween(260)) { -it / 6 } + fadeIn(tween(260))) togetherWith
                                (slideOutHorizontally(tween(220)) { it / 10 } + fadeOut(tween(180)))
                        targetState.depth < initialState.depth ->
                            (slideInHorizontally(tween(260)) { it / 6 } + fadeIn(tween(260))) togetherWith
                                (slideOutHorizontally(tween(220)) { -it / 10 } + fadeOut(tween(180)))
                        else -> fadeIn(tween(220)) togetherWith fadeOut(tween(160))
                    }
                },
                label = "calmNav"
            ) { s ->
                val route = s.route
                if (route == null) {
                    when (s.tab) {
                        CalmTab.TODAY -> TodayScreen(viewModel, uiState, nav)
                        CalmTab.SUBJECTS -> SubjectsScreen(uiState, nav)
                        CalmTab.PRACTICE -> PracticeScreen(viewModel, uiState, nav)
                        CalmTab.ME -> ProfileScreen(viewModel, uiState, nav, currentUserId)
                    }
                } else {
                    RouteContent(
                        route = route,
                        viewModel = viewModel,
                        uiState = uiState,
                        nav = nav,
                        currentUserId = currentUserId,
                        userDisplayName = userDisplayName,
                        onSelectTab = { t -> nav.clear(); tabName = t.name },
                        onMessage = { viewModel.showMessage(it) }
                    )
                }
            }
        }
    }
}

@Composable
private fun RouteContent(
    route: Route,
    viewModel: KonkoorViewModel,
    uiState: com.example.ui.KonkoorUiState,
    nav: CalmNavigator,
    currentUserId: String?,
    userDisplayName: String?,
    onSelectTab: (CalmTab) -> Unit,
    onMessage: (String) -> Unit
) {
    when (route) {
        is Route.Subject -> SubjectDetailScreen(route.subjectId, uiState, nav)
        is Route.Chapter -> ChapterScreen(route.subjectId, route.chapterKey, uiState, nav)
        is Route.Lesson -> LessonReaderScreen(route.lessonId, viewModel, uiState, nav)
        is Route.Review -> ReviewScreen(viewModel, uiState, nav)
        is Route.Flashcards -> FlashcardScreen(route.subjectId, viewModel, uiState, nav)
        is Route.Quiz -> QuizScreen(route.subjectId, viewModel, uiState, nav)
        is Route.PickSubject -> PickSubjectScreen(route.forQuiz, uiState, nav)
        is Route.Tools -> ToolsScreen(nav)
        is Route.Tool -> ToolHost(route, viewModel, uiState, nav, currentUserId, userDisplayName, onSelectTab, onMessage)
    }
}
