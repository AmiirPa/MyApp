package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.BrightnessAuto
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.firebase.KonkoorCloudRepository
import com.example.data.firebase.authStateFlow
import com.example.data.local.LocalKonkoorStore
import com.example.ui.AppTab
import com.example.ui.KonkoorViewModel
import com.example.ui.sharedPomodoroTimer
import com.example.ui.components.BidiFormatter
import com.example.ui.components.SavedItemsModalSheet
import com.example.data.adaptive.AdaptiveEngine
import com.example.data.focus.FocusSessionStore
import com.example.util.CrashReporter
import com.example.ui.screens.CounselingAndMentorshipScreen
import com.example.ui.screens.DailyPlanScreen
import com.example.ui.screens.DiagnosticFlowScreen
import com.example.ui.screens.HomeDashboardScreen
import com.example.ui.screens.InteractiveLabsScreen
import com.example.ui.screens.MasterKonkoorRoadmapScreen
import com.example.ui.screens.PracticeLeitnerAndQuizScreen
import com.example.ui.screens.ProgressDashboardScreen
import com.example.ui.screens.ReviewQueueScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.StudyPlannerAndFarhangianScreen
import com.example.ui.screens.ZeroLessonsScreen
import com.example.ui.theme.AppThemeController
import com.example.ui.theme.AppThemeMode
import com.example.ui.theme.LocalAppThemeController
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.StudyBackground
import com.example.ui.theme.StudyBorder
import com.example.ui.theme.StudyMuted
import com.example.ui.theme.StudyPrimary
import com.example.ui.theme.StudySurface
import com.example.ui.theme.StudyText
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // گزارش‌گیر خرابی: اگر برنامه به‌خاطر خطایی بمیرد، متن کامل خطا در
        // فایل crash-report.txt ذخیره می‌شود تا علت واقعی پیدا شود.
        CrashReporter.install(applicationContext)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val themeController = remember { AppThemeController(context) }
            val authFlow = remember {
                try { Firebase.auth.authStateFlow() } catch (_: Exception) { null }
            }
            val currentUser = if (authFlow != null) {
                val s by authFlow.collectAsStateWithLifecycle(
                    initialValue = try { Firebase.auth.currentUser } catch (_: Exception) { null }
                )
                s
            } else null

            // The ViewModel is intentionally created once: its cloud session is NOT
            // frozen from this first snapshot. Every later auth emission below calls
            // onAuthChanged, which (re)starts or cleanly stops cloud sync. Recreating
            // the ViewModel per user would wipe UI state (tab, open lesson) instead.
            val viewModel = remember {
                // فروشگاه محلی: اگر ساخته نشد، ViewModel بدون آن کار می‌کند
                // (برنامه خراب نمی‌شود؛ فقط پیشرفت محلی ذخیره نمی‌شود).
                val store = try {
                    LocalKonkoorStore(context.applicationContext)
                } catch (_: Exception) { null }
                val user = try { Firebase.auth.currentUser } catch (_: Exception) { null }
                val repo = if (user != null) {
                    try { KonkoorCloudRepository(context.applicationContext) } catch (_: Exception) { null }
                } else null
                // موتور تطبیقی: اگر ساخته نشد، ViewModel با engine=null کار می‌کند
                // و صفحه‌های تطبیقی «داده کافی نیست» نشان می‌دهند (اپ خراب نمی‌شود).
                val adaptiveEngine = try {
                    AdaptiveEngine.create(context.applicationContext)
                } catch (_: Exception) { null }
                // جلسه‌های تمرکز (§۸): اگر ساخته نشد، تایمر بدون ثبت کار می‌کند.
                val focusStore = try {
                    FocusSessionStore(context.applicationContext)
                } catch (_: Exception) { null }
                // قلاب ثبت پومودورو: فقط ثانیه‌های واقعی تمرکز ثبت می‌شوند؛
                // استراحت و مکث هرگز ثبت نمی‌شوند.
                if (focusStore != null) {
                    sharedPomodoroTimer.sessionRecorder = focusStore
                }
                KonkoorViewModel(repo, user?.uid, store, adaptiveEngine, focusStore)
            }

            // authFlow is the Firebase AuthStateListener (see authStateFlow() in
            // GoogleAuthHelper.kt). It used to feed only the header UI; it now also
            // drives the ViewModel's cloud session, so signing in after launch starts
            // sync and signing out stops the Firestore listeners and falls back to
            // the local store. The first emission matches the snapshot above and is
            // a no-op inside onAuthChanged.
            val appContext = context.applicationContext
            LaunchedEffect(currentUser?.uid) {
                val user = currentUser
                val repo = if (user != null) {
                    try { KonkoorCloudRepository(appContext) } catch (_: Exception) { null }
                } else null
                viewModel.onAuthChanged(user?.uid, repo)
            }

            val systemDark = isSystemInDarkTheme()
            val isDark = themeController.resolveIsDarkTheme(systemDark)
            androidx.compose.runtime.SideEffect {
                val window = (context as? android.app.Activity)?.window
                if (window != null) {
                    androidx.core.view.WindowCompat.getInsetsController(window, window.decorView)?.let { controller ->
                        controller.isAppearanceLightStatusBars = !isDark
                        controller.isAppearanceLightNavigationBars = !isDark
                    }
                }
            }

            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                MyApplicationTheme(themeController = themeController) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        AppScaffold(
                            viewModel = viewModel,
                            currentUserId = currentUser?.uid,
                            userDisplayName = currentUser?.displayName ?: currentUser?.email
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppScaffold(
    viewModel: KonkoorViewModel,
    currentUserId: String?,
    userDisplayName: String?
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val themeController = LocalAppThemeController.current
    val systemDark = isSystemInDarkTheme()
    val isDark = themeController.resolveIsDarkTheme(systemDark)

    val isSubLevelOpen = uiState.activeLessonId != null ||
        (uiState.activeTab == AppTab.LESSONS && uiState.selectedSubjectId != "all") ||
        uiState.activeTab == AppTab.SETTINGS

    var lastNonSettingsTab by remember { mutableStateOf(AppTab.HOME) }
    LaunchedEffect(uiState.activeTab) {
        if (uiState.activeTab != AppTab.SETTINGS) {
            lastNonSettingsTab = uiState.activeTab
        }
    }

    BackHandler(enabled = isSubLevelOpen || uiState.activeTab != AppTab.HOME) {
        when {
            uiState.activeLessonId != null -> viewModel.openLesson(null)
            uiState.activeTab == AppTab.SETTINGS -> {
                viewModel.selectTab(lastNonSettingsTab)
            }
            uiState.activeTab == AppTab.LESSONS && uiState.selectedSubjectId != "all" -> {
                viewModel.selectSubject("all", navigateToLessons = false)
            }
            else -> {
                viewModel.selectSubject("all", navigateToLessons = false)
                viewModel.selectTab(AppTab.HOME)
            }
        }
    }

    LaunchedEffect(uiState.statusMessage) {
        val msg = uiState.statusMessage
        if (!msg.isNullOrBlank()) {
            snackbarHostState.showSnackbar(msg)
            viewModel.clearStatusMessage()
        }
    }

    // 5 Primary Bottom Bar Destinations: HOME | LESSONS | PLANNER | EXAMS | MORE (Settings is a separate top-level destination)
    val navTabs = remember(themeController.appLanguage) {
        listOf(
            Triple(AppTab.HOME, Icons.Default.Home, themeController.tr("خانه", "Home")),
            Triple(AppTab.LESSONS, Icons.Default.MenuBook, themeController.tr("درس‌ها", "Lessons")),
            Triple(AppTab.PLANNER, Icons.Default.CalendarMonth, themeController.tr("برنامه", "Planner")),
            Triple(AppTab.PRACTICE, Icons.Default.Quiz, themeController.tr("آزمون‌ها", "Exams")),
            Triple(AppTab.COUNSELING, Icons.Default.Explore, themeController.tr("بیشتر", "More"))
        )
    }

    val barSurfaceColor = MaterialTheme.colorScheme.surface
    val barBorderColor = MaterialTheme.colorScheme.outlineVariant
    val primaryColor = MaterialTheme.colorScheme.primary
    val onPrimaryColor = MaterialTheme.colorScheme.onPrimary
    val textColor = MaterialTheme.colorScheme.onSurface
    val mutedColor = MaterialTheme.colorScheme.onSurfaceVariant

    val currentSectionTitle = when (uiState.activeTab) {
        AppTab.HOME -> themeController.tr("صفر تا فرهنگیان", "Zero to Farhangian")
        AppTab.LESSONS -> themeController.tr("درس‌ها و منابع آموزشی", "Subjects & Lessons")
        AppTab.PLANNER -> themeController.tr("دفتر برنامه‌ریزی تحصیلی", "Smart Study Planner")
        AppTab.PRACTICE -> themeController.tr("آزمون‌ها و بانک سوالات", "Exams & Flashcards")
        AppTab.SETTINGS -> themeController.tr("تنظیمات برنامه", "App Settings")
        AppTab.COUNSELING, AppTab.LAB, AppTab.ROADMAP -> themeController.tr("امکانات و خدمات آموزشی", "Educational Tools & Library")
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isWideScreen = maxWidth >= 600.dp

        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                if (uiState.activeLessonId == null) {
                    var showSavedItemsSheet by remember { mutableStateOf(false) }

                    if (showSavedItemsSheet) {
                        SavedItemsModalSheet(
                            uiState = uiState,
                            onOpenLesson = { lessonId ->
                                showSavedItemsSheet = false
                                viewModel.openLesson(lessonId)
                            },
                            onToggleBookmark = { lesson ->
                                viewModel.toggleBookmark(lesson)
                            },
                            onDismiss = { showSavedItemsSheet = false }
                        )
                    }

                    Column(modifier = Modifier.fillMaxWidth()) {
                        TopAppBar(
                            title = {
                                Text(
                                    text = BidiFormatter.applyPreferredDigits(currentSectionTitle),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = textColor,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            navigationIcon = {
                                if (isSubLevelOpen) {
                                    IconButton(
                                        onClick = {
                                            // NOTE: the top bar (and therefore this button) is only
                                            // composed when activeLessonId == null, so there is no
                                            // "close lesson" branch here — that case is handled by
                                            // LessonDetailView's own BackHandler and the system
                                            // BackHandler in AppScaffold. The old
                                            // `activeLessonId != null -> openLesson(null)` branch
                                            // could never fire from this button and was removed.
                                            when {
                                                uiState.activeTab == AppTab.SETTINGS -> viewModel.selectTab(lastNonSettingsTab)
                                                else -> viewModel.selectSubject("all", navigateToLessons = false)
                                            }
                                        },
                                        modifier = Modifier.testTag("top_bar_back_button")
                                    ) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = themeController.tr("بازگشت", "Back"),
                                            tint = textColor
                                        )
                                    }
                                }
                            },
                            actions = {
                                val bookmarkedCount = uiState.bookmarkedLessonsCount
                                IconButton(
                                    onClick = { showSavedItemsSheet = true },
                                    modifier = Modifier.testTag("action_saved_items")
                                ) {
                                    BadgedBox(
                                        badge = {
                                            if (bookmarkedCount > 0) {
                                                Badge(
                                                    containerColor = primaryColor,
                                                    contentColor = onPrimaryColor
                                                ) {
                                                    Text(
                                                        text = BidiFormatter.applyPreferredDigits(bookmarkedCount.toString()),
                                                        fontSize = 10.sp,
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                }
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = if (bookmarkedCount > 0) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                            contentDescription = themeController.tr("نشان‌شده‌ها", "Saved Items"),
                                            tint = if (bookmarkedCount > 0) primaryColor else mutedColor,
                                            modifier = Modifier.size(21.dp)
                                        )
                                    }
                                }

                                val themeIcon = when (themeController.themeMode) {
                                    AppThemeMode.SYSTEM -> Icons.Default.BrightnessAuto
                                    AppThemeMode.LIGHT -> Icons.Default.LightMode
                                    AppThemeMode.DARK -> Icons.Default.DarkMode
                                }

                                IconButton(
                                    onClick = {
                                        themeController.cycleThemeMode()
                                    },
                                    modifier = Modifier.testTag("action_toggle_theme")
                                ) {
                                    Icon(
                                        imageVector = themeIcon,
                                        contentDescription = themeController.tr("تغییر تم", "Toggle Theme"),
                                        tint = mutedColor,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }

                                IconButton(
                                    onClick = {
                                        viewModel.openLesson(null)
                                        if (uiState.activeTab == AppTab.SETTINGS) {
                                            viewModel.selectTab(lastNonSettingsTab)
                                        } else {
                                            viewModel.selectTab(AppTab.SETTINGS)
                                        }
                                    },
                                    modifier = Modifier.testTag("nav_tab_settings")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Settings,
                                        contentDescription = themeController.tr("تنظیمات", "Settings"),
                                        tint = if (uiState.activeTab == AppTab.SETTINGS) primaryColor else mutedColor,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = barSurfaceColor,
                                titleContentColor = textColor
                            )
                        )
                        HorizontalDivider(thickness = 1.dp, color = barBorderColor)
                    }
                }
            },
            bottomBar = {
                if (!isWideScreen && uiState.activeLessonId == null) {
                    NavigationBar(
                        containerColor = barSurfaceColor,
                        contentColor = textColor,
                        tonalElevation = 3.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("app_bottom_nav_bar")
                    ) {
                        navTabs.forEach { (tab, icon, label) ->
                            val isMoreGroup = tab == AppTab.COUNSELING &&
                                uiState.activeTab in setOf(AppTab.COUNSELING, AppTab.LAB, AppTab.ROADMAP)
                            val isSelected = uiState.activeTab == tab || isMoreGroup

                            NavigationBarItem(
                                selected = isSelected,
                                onClick = {
                                    viewModel.openLesson(null)
                                    if (tab == AppTab.LESSONS && uiState.activeTab == AppTab.LESSONS) {
                                        viewModel.selectSubject("all", navigateToLessons = false)
                                    }
                                    viewModel.selectTab(tab)
                                },
                                icon = {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = label,
                                        modifier = Modifier.size(24.dp)
                                    )
                                },
                                label = {
                                    Text(
                                        text = label,
                                        fontSize = 11.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                },
                                alwaysShowLabel = true,
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                    selectedTextColor = MaterialTheme.colorScheme.primary,
                                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                                    unselectedIconColor = mutedColor,
                                    unselectedTextColor = mutedColor
                                ),
                                modifier = Modifier.testTag("nav_tab_${tab.name.lowercase()}")
                            )
                        }
                    }
                }
            }
        ) { paddingValues ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (isWideScreen && uiState.activeLessonId == null) {
                    NavigationRail(
                        containerColor = barSurfaceColor,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        navTabs.forEach { (tab, icon, label) ->
                            val isMoreGroup = tab == AppTab.COUNSELING &&
                                uiState.activeTab in setOf(AppTab.COUNSELING, AppTab.LAB, AppTab.ROADMAP)
                            val isSelected = uiState.activeTab == tab || isMoreGroup

                            NavigationRailItem(
                                selected = isSelected,
                                onClick = {
                                    viewModel.openLesson(null)
                                    viewModel.selectTab(tab)
                                },
                                icon = {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = label
                                    )
                                },
                                label = {
                                    Text(
                                        text = label,
                                        fontSize = 11.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                },
                                modifier = Modifier.testTag("nav_tab_${tab.name.lowercase()}")
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .then(
                                if (isWideScreen) Modifier.widthIn(max = 920.dp).fillMaxWidth()
                                else Modifier.fillMaxWidth()
                            )
                    ) {
                        AnimatedContent(
                            targetState = uiState.activeTab,
                            transitionSpec = {
                                fadeIn(animationSpec = tween(180)) togetherWith
                                    fadeOut(animationSpec = tween(140))
                            },
                            label = "TabTransition"
                        ) { targetTab ->
                            when (targetTab) {
                                AppTab.HOME -> HomeDashboardScreen(
                                    uiState = uiState,
                                    studentDisplayName = userDisplayName,
                                    onSelectSubjectForLessons = { subjectId ->
                                        viewModel.selectSubject(subjectId, navigateToLessons = true)
                                    },
                                    onOpenLesson = { viewModel.openLesson(it) },
                                    onNavigateTab = { viewModel.selectTab(it) },
                                    onSaveLessonState = { lesson, isCompleted, isBookmarked, note, mastery ->
                                        viewModel.saveLessonState(lesson, isCompleted, isBookmarked, note, mastery)
                                    },
                                    onMiniCheckAnswered = { lesson, correct ->
                                        viewModel.recordMiniCheckAttempt(lesson, correct)
                                    }
                                )

                                AppTab.LESSONS -> ZeroLessonsScreen(
                                    uiState = uiState,
                                    onSelectSubject = { viewModel.selectSubject(it, navigateToLessons = false) },
                                    onOpenLesson = { viewModel.openLesson(it) },
                                    onSaveLessonState = { lesson, isCompleted, isBookmarked, note, mastery ->
                                        viewModel.saveLessonState(lesson, isCompleted, isBookmarked, note, mastery)
                                    },
                                    onMiniCheckAnswered = { lesson, correct ->
                                        viewModel.recordMiniCheckAttempt(lesson, correct)
                                    }
                                )

                                AppTab.PLANNER -> StudyPlannerAndFarhangianScreen(
                                    uiState = uiState,
                                    onAddTask = { title, subjectId, tag, duration ->
                                        viewModel.addStudyTask(title, subjectId, tag, duration)
                                    },
                                    onToggleTask = { viewModel.toggleStudyTask(it) },
                                    onDeleteTask = { viewModel.deleteStudyTask(it) },
                                    onOpenLesson = { lessonId ->
                                        viewModel.openLesson(lessonId)
                                    },
                                    onSaveLessonState = { lesson, isCompleted, isBookmarked, note, mastery ->
                                        viewModel.saveLessonState(lesson, isCompleted, isBookmarked, note, mastery)
                                    }
                                )

                                AppTab.PRACTICE -> PracticeLeitnerAndQuizScreen(
                                    uiState = uiState,
                                    onAnswerFlashcard = { card, knew -> viewModel.answerFlashcard(card, knew) },
                                    onSubmitExamResult = { id, title, correct, total ->
                                        viewModel.submitExamResult(id, title, correct, total)
                                    },
                                    onSubmitExamAttempt = { attempt ->
                                        viewModel.submitExamAttempt(attempt)
                                    }
                                )

                                // SETTINGS is an independent top-level destination, NEVER merged into MoreSectionScreen
                                AppTab.SETTINGS -> SettingsScreen(
                                    currentUserId = currentUserId,
                                    userDisplayName = userDisplayName,
                                    onShowMessage = { msg ->
                                        scope.launch { snackbarHostState.showSnackbar(msg) }
                                    }
                                )

                                AppTab.COUNSELING, AppTab.LAB, AppTab.ROADMAP -> {
                                    val initialSection = when (targetTab) {
                                        AppTab.COUNSELING -> 0
                                        AppTab.LAB -> 1
                                        AppTab.ROADMAP -> 2
                                        else -> 0
                                    }
                                    MoreSectionScreen(
                                        initialSubSection = initialSection,
                                        uiState = uiState,
                                        viewModel = viewModel
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MoreSectionScreen(
    initialSubSection: Int,
    uiState: com.example.ui.KonkoorUiState,
    viewModel: KonkoorViewModel
) {
    var activeSection by remember(initialSubSection) { mutableIntStateOf(initialSubSection) }
    val themeController = LocalAppThemeController.current
    val surfaceColor = MaterialTheme.colorScheme.surface
    val borderColor = MaterialTheme.colorScheme.outlineVariant
    val primaryColor = MaterialTheme.colorScheme.primary
    val onPrimaryColor = MaterialTheme.colorScheme.onPrimary
    val mutedColor = MaterialTheme.colorScheme.onSurfaceVariant

    // Strictly educational & learning tools (Settings is separated into its own standalone destination)
    val items = remember(themeController.appLanguage) {
        listOf(
            Triple(0, themeController.tr("مشاوره و تجربیات", "Counseling & Guides"), "sub_section_counseling"),
            Triple(1, themeController.tr("آزمایشگاه یادگیری", "Learning Lab"), "nav_tab_lab"),
            Triple(2, themeController.tr("نقشه راه ۱۴۰۶", "1406 Roadmap"), "nav_tab_roadmap"),
            Triple(3, themeController.tr("کتابخانه و منابع", "Library & PDFs"), "sub_section_library"),
            // صفحه‌های تطبیقی (§۳ تا §۹ الحاقیه) — متن‌ها فقط فارسی‌اند.
            Triple(4, "تشخیص سطح", "adaptive_diagnostic"),
            Triple(5, "مرور امروز", "adaptive_review_queue"),
            Triple(6, "برنامه امروز", "adaptive_daily_plan"),
            Triple(7, "گزارش پیشرفت", "adaptive_progress_report")
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("more_educational_section_root")
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            shape = RoundedCornerShape(14.dp),
            color = surfaceColor,
            border = BorderStroke(1.dp, borderColor)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEach { (idx, label, tag) ->
                    val isSelected = activeSection == idx
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { activeSection = idx }
                            .testTag(tag),
                        color = if (isSelected) primaryColor else Color.Transparent,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = BidiFormatter.applyPreferredDigits(label),
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) onPrimaryColor else mutedColor,
                            maxLines = 1,
                            softWrap = false,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 9.dp)
                        )
                    }
                }
            }
        }

        AnimatedContent(
            targetState = activeSection,
            transitionSpec = {
                fadeIn(animationSpec = tween(180)) togetherWith
                    fadeOut(animationSpec = tween(140))
            },
            modifier = Modifier.weight(1f).fillMaxWidth(),
            label = "MoreSubSectionAnim"
        ) { sectionIdx ->
            when (sectionIdx) {
                0 -> CounselingAndMentorshipScreen(
                    uiState = uiState,
                    onNavigateTab = { viewModel.selectTab(it) }
                )
                1 -> InteractiveLabsScreen()
                2 -> MasterKonkoorRoadmapScreen(
                    uiState = uiState,
                    onOpenLesson = { lessonId ->
                        viewModel.selectTab(AppTab.HOME)
                        viewModel.openLesson(lessonId)
                    },
                    onSelectSubject = { subjectId ->
                        viewModel.selectSubject(subjectId, navigateToLessons = true)
                    },
                    onNavigateTab = { tab -> viewModel.selectTab(tab) },
                    onToggleStep = { stepId -> viewModel.toggleRoadmapStep(stepId) }
                )
                3 -> com.example.ui.components.LibraryAndResourcesCatalogView(
                    allLessons = uiState.allLessons,
                    onOpenLesson = { lessonId ->
                        viewModel.selectTab(AppTab.LESSONS)
                        viewModel.openLesson(lessonId)
                    }
                )
                4 -> DiagnosticFlowScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    onOpenLesson = { lessonId ->
                        viewModel.selectTab(AppTab.LESSONS)
                        viewModel.openLesson(lessonId)
                    }
                )
                5 -> ReviewQueueScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    onOpenLesson = { lessonId ->
                        viewModel.selectTab(AppTab.LESSONS)
                        viewModel.openLesson(lessonId)
                    }
                )
                6 -> DailyPlanScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    onOpenLesson = { lessonId ->
                        viewModel.selectTab(AppTab.LESSONS)
                        viewModel.openLesson(lessonId)
                    }
                )
                7 -> ProgressDashboardScreen(viewModel = viewModel)
                else -> com.example.ui.components.LibraryAndResourcesCatalogView(
                    allLessons = uiState.allLessons,
                    onOpenLesson = { lessonId ->
                        viewModel.selectTab(AppTab.LESSONS)
                        viewModel.openLesson(lessonId)
                    }
                )
            }
        }
    }
}

