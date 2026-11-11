package com.example.ui.calm

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ListAlt
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.BuildCircle
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Forum
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Insights
import androidx.compose.material.icons.outlined.LibraryBooks
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Route
import androidx.compose.material.icons.outlined.Science
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Today
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ui.KonkoorUiState
import com.example.ui.KonkoorViewModel
import com.example.ui.components.SavedItemsModalSheet
import com.example.ui.theme.AppThemeMode
import com.example.ui.theme.LocalAppThemeController
import com.example.util.StreakCalculator

@Composable
fun ProfileScreen(viewModel: KonkoorViewModel, uiState: KonkoorUiState, nav: CalmNavigator, currentUserId: String?) {
    val themeController = LocalAppThemeController.current
    val prefs = LocalCalmPrefs.current
    var showSaved by remember { mutableStateOf(false) }
    if (showSaved) {
        SavedItemsModalSheet(
            uiState = uiState,
            onOpenLesson = { id -> showSaved = false; nav.openLesson(id) },
            onToggleBookmark = { viewModel.toggleBookmark(it) },
            onDismiss = { showSaved = false }
        )
    }
    val streak = remember(uiState.completedStudyDays) { StreakCalculator.currentStreak(uiState.completedStudyDays) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp)
    ) {
        ScreenTitle("من", if (currentUserId != null) "حساب متصل است" else "پیشرفتت روی همین گوشی ذخیره می‌شود")
        Spacer(Modifier.height(8.dp))
        CalmCard(modifier = Modifier.padding(horizontal = ScreenPadding)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Stat(faDigits(uiState.completedLessonsCount), "درس تمام‌شده", Modifier.weight(1f))
                Stat(faDigits(streak), "روز پشت‌سرهم", Modifier.weight(1f))
                Stat(faDigits(uiState.examResults.size), "آزمون", Modifier.weight(1f))
            }
        }

        SectionLabel("ظاهر")
        Choice(
            options = listOf("خودکار", "روشن", "تیره"),
            selected = when (themeController.themeMode) {
                AppThemeMode.LIGHT -> 1
                AppThemeMode.DARK -> 2
                else -> 0
            },
            onSelect = {
                themeController.updateThemeMode(
                    when (it) { 1 -> AppThemeMode.LIGHT; 2 -> AppThemeMode.DARK; else -> AppThemeMode.SYSTEM }
                )
            }
        )
        SectionLabel("اندازهٔ متن درس")
        val scales = listOf(0.9f, 1f, 1.15f)
        Choice(
            options = listOf("کوچک", "معمولی", "بزرگ"),
            selected = scales.indexOfFirst { kotlin.math.abs(it - prefs.readingScale) < 0.01f }.let { if (it < 0) 1 else it },
            onSelect = { prefs.updateReadingScale(scales[it]) }
        )
        SectionLabel("مسیر یادگیری را از کجا شروع کنم؟")
        Column(modifier = Modifier.padding(horizontal = ScreenPadding), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            PathStage.entries.forEach { st ->
                Pill(text = st.label, selected = prefs.startStage == st, modifier = Modifier.fillMaxWidth()) { prefs.updateStartStage(st) }
            }
        }

        SectionLabel("بیشتر")
        CalmRow(title = "نشان‌شده‌ها", subtitle = "درس‌هایی که نگه داشته‌ای", icon = Icons.Outlined.Bookmarks, trailingText = faDigits(uiState.bookmarkedLessonsCount)) { showSaved = true }
        CalmRow(title = "گزارش پیشرفت", icon = Icons.Outlined.Insights) { nav.push(Route.Tool(ToolId.PROGRESS_REPORT)) }
        CalmRow(title = "ابزارها", subtitle = "برنامه‌ریز، نقشهٔ راه، کتاب‌ها، واژه‌نامه و…", icon = Icons.Outlined.BuildCircle) { nav.push(Route.Tools) }
        CalmRow(title = "تنظیمات پیشرفته و حساب", subtitle = "ورود با گوگل، فونت، پشتیبان‌گیری", icon = Icons.Outlined.Settings) { nav.push(Route.Tool(ToolId.ADVANCED_SETTINGS)) }
    }
}

@Composable
private fun Stat(value: String, label: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onSurface)
        Text(label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
    }
}

@Composable
private fun Choice(options: List<String>, selected: Int, onSelect: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .padding(horizontal = ScreenPadding)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        options.forEachIndexed { i, o ->
            Pill(text = o, selected = i == selected, modifier = Modifier.weight(1f), flat = true) { onSelect(i) }
        }
    }
}

@Composable
private fun Pill(text: String, selected: Boolean, modifier: Modifier = Modifier, flat: Boolean = false, onClick: () -> Unit) {
    val c = MaterialTheme.colorScheme
    val bg by animateColorAsState(
        if (selected) (if (flat) c.surface else c.primaryContainer) else (if (flat) c.surfaceVariant else c.surface),
        tween(200), label = "pill"
    )
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(bg)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 12.dp),
        contentAlignment = if (flat) Alignment.Center else Alignment.CenterStart
    ) {
        Text(
            text,
            style = MaterialTheme.typography.labelLarge,
            color = if (selected) c.primary else c.onSurfaceVariant
        )
    }
}

private data class ToolEntry(val id: ToolId, val title: String, val subtitle: String, val icon: ImageVector)

private val toolEntries = listOf(
    ToolEntry(ToolId.PLANNER, "برنامه‌ریز مطالعه", "کارهای روزانه و تقویم", Icons.Outlined.CalendarMonth),
    ToolEntry(ToolId.DAILY_PLAN, "برنامهٔ هوشمند امروز", "بر اساس وقتی که داری", Icons.Outlined.Today),
    ToolEntry(ToolId.ROADMAP, "نقشهٔ راه کنکور ۱۴۰۶", "مرحله‌به‌مرحله تا کنکور", Icons.Outlined.Map),
    ToolEntry(ToolId.PATH_100, "مسیر کامل ۱ تا ۱۰۰", "همهٔ قدم‌های مسیر یکجا", Icons.Outlined.Route),
    ToolEntry(ToolId.LIBRARY, "کتاب‌های درسی و منابع", "PDF کتاب‌ها", Icons.Outlined.LibraryBooks),
    ToolEntry(ToolId.LEXICON, "واژگان و شعرهای کنکور", "معنی لغت، شعر، آرایه", Icons.AutoMirrored.Outlined.MenuBook),
    ToolEntry(ToolId.HISTORY_VAULT, "سلسله‌های تاریخ", "حفظ آسان دوره‌ها", Icons.Outlined.History),
    ToolEntry(ToolId.LABS, "آزمایشگاه یادگیری", "درخت‌ها، نمودارها، روش رتبه‌برترها", Icons.Outlined.Science),
    ToolEntry(ToolId.COUNSELING, "مشاوره و تجربه‌ها", "راهنمای مطالعه و انگیزه", Icons.Outlined.Forum),
    ToolEntry(ToolId.ALL_LESSONS, "فهرست و جستجوی همهٔ درس‌ها", "پیدا کردن هر درس", Icons.AutoMirrored.Outlined.ListAlt)
)

@Composable
fun ToolsScreen(nav: CalmNavigator) {
    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 24.dp)) {
        item { BackHeader("ابزارها", subtitle = "هر وقت لازم شد", onBack = { nav.pop() }) }
        items(toolEntries, key = { it.id.name }) { t ->
            CalmRow(title = t.title, subtitle = t.subtitle, icon = t.icon) { nav.push(Route.Tool(t.id)) }
        }
    }
}

fun toolTitle(id: ToolId): String = toolEntries.firstOrNull { it.id == id }?.title ?: when (id) {
    ToolId.PROGRESS_REPORT -> "گزارش پیشرفت"
    ToolId.DIAGNOSTIC -> "سنجش سطح"
    ToolId.EXAM_ARCHIVE -> "آزمون‌های کنکور و نهایی"
    ToolId.ADVANCED_SETTINGS -> "تنظیمات پیشرفته"
    ToolId.JARGON -> "واژه‌نامهٔ ساده"
    else -> "ابزار"
}
