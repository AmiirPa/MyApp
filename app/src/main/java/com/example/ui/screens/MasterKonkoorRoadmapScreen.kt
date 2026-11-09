package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AltRoute
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AppTab
import com.example.ui.KonkoorUiState
import com.example.ui.components.BidiFormatter
import com.example.ui.theme.SubjectArabicColor
import com.example.ui.theme.SubjectAroozColor
import com.example.ui.theme.SubjectEnglishColor
import com.example.ui.theme.SubjectFarhangianColor
import com.example.ui.theme.SubjectFonoonColor
import com.example.ui.theme.SubjectMathColor
import com.example.ui.theme.SubjectPhilosophyColor
import com.example.ui.theme.resolveDynamicAccent

// ==========================================
// MODELS FOR THE COMPREHENSIVE ROADMAP
// ==========================================

data class RoadmapStepItem(
    val id: String,
    val phaseNumber: Int,
    val phaseTitle: String,
    val phaseBadgeColor: Color,
    val stepIndex: Int,
    val stepTitle: String,
    val subjectId: String,
    val subjectName: String,
    val whyFirst: String,
    val whatToMaster: String,
    val targetLessonId: String? = null,
    val hasLab: Boolean = false,
    val durationRecommendation: String,
    val trapAlert: String
)

data class DiagnosticTroubleItem(
    val id: String,
    val subjectId: String,
    val subjectTitle: String,
    val symptomProblem: String,
    val rootCause: String,
    val missingPrerequisite: String,
    val quickFiveMinuteCure: String,
    val targetLessonId: String? = null,
    val targetLabTab: Boolean = false,
    val badgeColor: Color
)

@OptIn(ExperimentalLayoutApi::class, androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun MasterKonkoorRoadmapScreen(
    uiState: KonkoorUiState,
    onOpenLesson: (String) -> Unit,
    onSelectSubject: (String) -> Unit,
    onNavigateTab: (AppTab) -> Unit,
    onToggleStep: (String) -> Unit
) {
    var selectedMainTab by remember { mutableIntStateOf(0) }
    var selectedStudentLevel by remember { mutableIntStateOf(0) } // 0: همه، 1: منفی صفر، 2: متوسط، 3: فرهنگیان، 4: رتبه برتر
    var diagnosticSearchQuery by remember { mutableStateOf("") }
    var diagnosticSubjectFilter by remember { mutableStateOf("all") }

    val allSteps = remember { buildChronologicalRoadmapSteps() }
    val allDiagnostics = remember { buildAllDiagnosticTroubles() }

    val completedCount = uiState.completedRoadmapSteps.size
    val totalSteps = allSteps.size
    val progressPct = if (totalSteps > 0) (completedCount.toFloat() / totalSteps * 100).toInt() else 0
    val headerAccent = resolveDynamicAccent(MaterialTheme.colorScheme.primary)

    val filteredSteps = remember(selectedStudentLevel, allSteps) {
        when (selectedStudentLevel) {
            1 -> allSteps.filter { it.phaseNumber == 0 } // زیر صفر
            2 -> allSteps.filter { it.phaseNumber == 1 } // دهم و یازدهم
            3 -> allSteps.filter { it.phaseNumber == 2 || it.subjectId == "farhangian" } // فرهنگیان
            4 -> allSteps.filter { it.phaseNumber == 3 } // رتبه برتری
            else -> allSteps
        }
    }

    val filteredDiagnostics = remember(diagnosticSearchQuery, diagnosticSubjectFilter, allDiagnostics) {
        val q = diagnosticSearchQuery.trim()
        allDiagnostics.filter { item ->
            val matchSubject = diagnosticSubjectFilter == "all" || item.subjectId == diagnosticSubjectFilter
            val matchQuery = q.isEmpty() ||
                item.symptomProblem.contains(q, ignoreCase = true) ||
                item.missingPrerequisite.contains(q, ignoreCase = true) ||
                item.rootCause.contains(q, ignoreCase = true) ||
                item.subjectTitle.contains(q, ignoreCase = true)
            matchSubject && matchQuery
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("screen_master_roadmap"),
        contentPadding = PaddingValues(start = 14.dp, top = 14.dp, end = 14.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // منوی فهرست‌وار عمودی بخش‌های نقشه راه (مرتب و بازشونده بدون شلوغی بالای صفحه)
        item {
            var isRoadmapMenuExpanded by remember { mutableStateOf(false) }
            val roadmapNavItems = listOf(
                Triple(0, "🎯 ۱. مسیر مرحله‌بندی شده کنکور ۱۴۰۶ (۵ درس)", "ادبیات، عربی، معارف، روان‌شناسی و جامعه‌شناسی (۸۰ ایستگاه)"),
                Triple(1, "🌳 ۲. درختواره پیش‌نیازها و روابط دروس", "ارتباط درختی دروس دهم، یازدهم و دوازدهم"),
                Triple(2, "🧭 ۳. نقشه ترتیبی و گام‌به‌گام کل دروس", "اول چی بخونیم؟ بعدش چی؟ از زیر صفر تا رتبه‌برتری"),
                Triple(3, "🔍 ۴. رادار عیب‌یاب و درمان ضعف‌ها", "اگر تستی را متوجه نمی‌شوی، کجای پیش‌نیاز لنگ می‌زند؟"),
                Triple(4, "🏆 ۵. فرمول رتبه‌های برتر کنکور", "عادات و استراتژی‌های تست‌زنی تک‌رقمی‌ها و دورقمی‌ها")
            )
            val activeNavItem = roadmapNavItems.firstOrNull { it.first == selectedMainTab } ?: roadmapNavItems[0]

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(
                            listOf(
                                headerAccent.copy(alpha = 0.16f),
                                MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.28f)
                            )
                        ),
                        RoundedCornerShape(16.dp)
                    ),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.30f))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { isRoadmapMenuExpanded = !isRoadmapMenuExpanded }
                            .padding(vertical = 4.dp, horizontal = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "📋 بخش فعال نقشه راه:",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = activeNavItem.second,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = activeNavItem.third,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (isRoadmapMenuExpanded) "بستن فهرست" else "سایر بخش‌ها",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = if (isRoadmapMenuExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }

                    AnimatedVisibility(
                        visible = isRoadmapMenuExpanded,
                        enter = expandVertically() + fadeIn(),
                        exit = shrinkVertically() + fadeOut()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            roadmapNavItems.forEach { (idx, title, desc) ->
                                val isSelected = selectedMainTab == idx
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(10.dp))
                                        .clickable {
                                            selectedMainTab = idx
                                            isRoadmapMenuExpanded = false
                                        },
                                    color = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f)
                                    else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
                                    border = BorderStroke(
                                        if (isSelected) 1.2.dp else 0.5.dp,
                                        if (isSelected) MaterialTheme.colorScheme.primary
                                        else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
                                    ),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 9.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = title,
                                                style = MaterialTheme.typography.titleSmall,
                                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                                                color = if (isSelected) MaterialTheme.colorScheme.primary
                                                else MaterialTheme.colorScheme.onSurface
                                            )
                                            Text(
                                                text = desc,
                                                style = MaterialTheme.typography.bodySmall,
                                                fontSize = 11.sp,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                        Icon(
                                            imageVector = if (isSelected) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                                            contentDescription = null,
                                            tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // TAB CONTENT
        when (selectedMainTab) {
            0 -> {
                // TAB 0: STAGED LEARNING PATH FOR 1406 (مسیر مرحله‌بندی شده ۵ درس کنکور ۱۴۰۶)
                item {
                    com.example.ui.components.StagedLearningPathView(
                        onOpenLesson = { lessonId -> onOpenLesson(lessonId) }
                    )
                }
            }

            1 -> {
                // TAB 1: INTERACTIVE TREE NAVIGATION (نمای درختی پیش‌نیازها با لینک مستقیم)
                item {
                    InteractiveRoadmapTreeNavigationCard(
                        completedRoadmapSteps = uiState.completedRoadmapSteps,
                        onOpenLesson = { lessonId -> onOpenLesson(lessonId) },
                        onSelectSubject = { subId -> onSelectSubject(subId) },
                        onNavigateLab = { onNavigateTab(AppTab.LAB) },
                        onNavigatePractice = { onNavigateTab(AppTab.PRACTICE) }
                    )
                }
            }

            2 -> {
                // TAB 1: CHRONOLOGICAL ROADMAP (اول چی بخونیم؟ بعدش چی؟)
                item {
                    RoadmapGuidanceIntroCard()
                }

                items(filteredSteps, key = { it.id }) { step ->
                    val isChecked = uiState.completedRoadmapSteps.contains(step.id)
                    RoadmapStepCard(
                        step = step,
                        isCompleted = isChecked,
                        onToggle = { onToggleStep(step.id) },
                        onOpenLesson = { lessonId -> onOpenLesson(lessonId) },
                        onSelectSubject = { subId -> onSelectSubject(subId) },
                        onNavigateLab = { onNavigateTab(AppTab.LAB) }
                    )
                }
            }

            3 -> {
                // TAB 3: DIAGNOSTIC FALLBACK RADAR («اگر بلد نیستی برگرد این پیش‌نیاز رو بخون»)
                item {
                    DiagnosticSearchAndFilterCard(
                        searchQuery = diagnosticSearchQuery,
                        onQueryChange = { diagnosticSearchQuery = it },
                        selectedSubject = diagnosticSubjectFilter,
                        onSubjectSelect = { diagnosticSubjectFilter = it }
                    )
                }

                if (filteredDiagnostics.isEmpty()) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(36.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "موردی با این جستجو پیدا نشد!",
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "می‌توانید فیلتر درس‌ها را روی «همه درس‌ها» بگذارید یا کلمه دیگری جستجو کنید.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                } else {
                    items(filteredDiagnostics, key = { it.id }) { trouble ->
                        DiagnosticTroubleCard(
                            trouble = trouble,
                            onOpenLesson = { lessonId -> onOpenLesson(lessonId) },
                            onSelectSubject = { subId -> onSelectSubject(subId) },
                            onNavigateLab = { onNavigateTab(AppTab.LAB) },
                            onNavigatePractice = { onNavigateTab(AppTab.PRACTICE) }
                        )
                    }
                }
            }

            4 -> {
                // TAB 4: TOP RANKER SECRETS & SCIENTIFIC STUDY FORMULA
                item {
                    TopRankerTransformationFormulaCard(
                        onNavigatePlanner = { onNavigateTab(AppTab.PLANNER) },
                        onNavigatePractice = { onNavigateTab(AppTab.PRACTICE) }
                    )
                }
            }
        }
    }
}

// ==========================================
// COMPONENT 1: HERO PROGRESS CARD
// ==========================================
@Composable
private fun RoadmapHeroProgressCard(
    completedSteps: Int,
    totalSteps: Int,
    progressPct: Int
) {
    val heroAccent = resolveDynamicAccent(MaterialTheme.colorScheme.primary)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.linearGradient(
                    listOf(
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.85f),
                        MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.70f)
                    )
                ),
                RoundedCornerShape(24.dp)
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                Icons.Default.Explore,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                    Column {
                        Text(
                            text = "نقشه راه رتبه‌ساز کنکور و فرهنگیان",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "مسیر زنجیره‌ای از زیرِ منفی صفر تا تراز بالای ۹۵۰۰",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = heroAccent
                ) {
                    Text(
                        text = "${BidiFormatter.toPersianDigits(progressPct.toString())}٪ طی شده",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Black,
                        maxLines = 1,
                        softWrap = false,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }

            LinearProgressIndicator(
                progress = { (progressPct / 100f).coerceIn(0f, 1f) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(9.dp)
                    .clip(RoundedCornerShape(6.dp)),
                color = heroAccent,
                trackColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "گام‌های فتح‌شده: ${BidiFormatter.toPersianDigits(completedSteps.toString())} از ${BidiFormatter.toPersianDigits(totalSteps.toString())} ایستگاه",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = if (progressPct >= 80) "🏆 در آستانه رتبه تک‌رقمی!" else if (progressPct >= 40) "🚀 موتور روشن شد، ادامه بده!" else "🌱 قدم اول رو محکم بردار",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

// ==========================================
// COMPONENT 2: STUDENT LEVEL SELECTOR
// ==========================================
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun StudentLevelDetectorCard(
    selectedLevel: Int,
    onSelectLevel: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.AltRoute,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "الان در چه سطحی هستی؟ (انتخاب کن تا مسیرت روشن بشه):",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                FilterChip(
                    selected = selectedLevel == 0,
                    onClick = { onSelectLevel(0) },
                    label = { Text("🌟 کل مسیر (۲۰ گام)") }
                )
                FilterChip(
                    selected = selectedLevel == 1,
                    onClick = { onSelectLevel(1) },
                    label = { Text("🔴 زیرِ زیرِ صفر (همه چی یادم رفته)") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.6f),
                        selectedLabelColor = MaterialTheme.colorScheme.onErrorContainer
                    )
                )
                FilterChip(
                    selected = selectedLevel == 2,
                    onClick = { onSelectLevel(2) },
                    label = { Text("🟡 متوسط (قواعد رو قاطی می‌کنم)") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.6f),
                        selectedLabelColor = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                )
                FilterChip(
                    selected = selectedLevel == 3,
                    onClick = { onSelectLevel(3) },
                    label = { Text("🟢 هدفم فقط فرهنگیانه") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
                FilterChip(
                    selected = selectedLevel == 4,
                    onClick = { onSelectLevel(4) },
                    label = { Text("🟣 دنبال رتبه زیر ۱۰۰ هستم") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f),
                        selectedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                )
            }

            AnimatedVisibility(
                visible = selectedLevel != 0,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val adviceText = when (selectedLevel) {
                        1 -> "🎯 نقشه راه به فاز ۰ (پیش‌نیازهای شالوده بتنی) محدود شد: از عربی و زبان صفر شروع کن، تا وقتی صیغه‌ها و ساختار جمله را نفهمیدی سراغ تست نرو!"
                        2 -> "🎯 نقشه راه به فاز ۱ (ستون‌های دهم و یازدهم) محدود شد: تمرکزت را بگذار روی ۸ باب عربی، ۲۴ آرایه ادبی فنون و مسائل چشمی ۵ ثانیه‌ای اقتصاد!"
                        3 -> "🎯 نقشه راه به فاز فرهنگیان محدود شد: پکیج طلایی آزمون اختصاصی تربیت معلم، هوش تحلیلی و مهارت‌های معلمی استاد قرائتی هایلایت شد!"
                        4 -> "🎯 نقشه راه به فاز ۳ (جعبه‌ابزار سرعت و رتبه تک‌رقمی) محدود شد: تکنیک ضربدر-منها، زمان‌های نقصانی و شبیه‌سازی کنکورهای اخیر را اجرا کن!"
                        else -> ""
                    }
                    Text(
                        text = adviceText,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(10.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// ==========================================
// COMPONENT 3: ROADMAP INTRO CARD
// ==========================================
@Composable
private fun RoadmapGuidanceIntroCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Lightbulb,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "قانون طلایی زنجیره: هرگز گام بعدی را نخوان تا وقتی گام قبلی را تیک نزده‌ای! هر گام پیش‌نیاز قطعی گام بعدی است. با لمس دکمه «برو به این درسنامه» مستقیماً وارد مبحث می‌شوی.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

// ==========================================
// COMPONENT 4: ROADMAP STEP CARD
// ==========================================
@Composable
private fun RoadmapStepCard(
    step: RoadmapStepItem,
    isCompleted: Boolean,
    onToggle: () -> Unit,
    onOpenLesson: (String) -> Unit,
    onSelectSubject: (String) -> Unit,
    onNavigateLab: () -> Unit
) {
    val stepAccent = resolveDynamicAccent(MaterialTheme.colorScheme.primary)
    val stepBrush = Brush.verticalGradient(
        listOf(
            MaterialTheme.colorScheme.surface,
            if (isCompleted) MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            else MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
        )
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(stepBrush, RoundedCornerShape(20.dp))
            .testTag("step_card_${step.id}"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isCompleted) 0.dp else 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Header Row: Checkbox + Phase Badge + Step Index
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onToggle() }
                ) {
                    Icon(
                        imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                        contentDescription = if (isCompleted) "انجام شد" else "انجام نشده",
                        tint = if (isCompleted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = step.phaseBadgeColor.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = "گام ${BidiFormatter.toPersianDigits(step.stepIndex.toString())} • ${step.phaseTitle}",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = step.phaseBadgeColor,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = step.durationRecommendation,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            // Step Title
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(
                            Brush.linearGradient(
                                listOf(stepAccent.copy(alpha = 0.85f), MaterialTheme.colorScheme.secondary.copy(alpha = 0.85f))
                            ),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.MenuBook,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Text(
                    text = step.stepTitle,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = if (isCompleted) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f) else MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
            }

            // Why First? (چرا اول باید اینو بخونی؟)
            Surface(
                color = MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = 0.6f),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "❓ چرا اول باید اینو بخونی که بعدش بری سراغ بعدی؟",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = step.whyFirst,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // What to Master?
            Text(
                text = "📌 چه چیزهایی رو باید مسلط بشی:\n${step.whatToMaster}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Trap Alert
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.45f), RoundedCornerShape(8.dp))
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.WarningAmber,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "تله اشتباه داوطلبان: ${step.trapAlert}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (step.targetLessonId != null) {
                    Button(
                        onClick = { onOpenLesson(step.targetLessonId) },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .background(
                                Brush.linearGradient(
                                    listOf(stepAccent.copy(alpha = 0.90f), MaterialTheme.colorScheme.secondary.copy(alpha = 0.85f))
                                ),
                                RoundedCornerShape(16.dp)
                            ),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                    ) {
                        Icon(Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("ورود به این درسنامه", style = MaterialTheme.typography.labelMedium, maxLines = 1, softWrap = false)
                    }
                } else {
                    Button(
                        onClick = { onSelectSubject(step.subjectId) },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .background(
                                Brush.linearGradient(
                                    listOf(stepAccent.copy(alpha = 0.90f), MaterialTheme.colorScheme.secondary.copy(alpha = 0.85f))
                                ),
                                RoundedCornerShape(16.dp)
                            ),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                    ) {
                        Icon(Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("مشاهده درس‌های ${step.subjectName}", style = MaterialTheme.typography.labelMedium, maxLines = 1, softWrap = false)
                    }
                }

                if (step.hasLab) {
                    OutlinedButton(
                        onClick = { onNavigateLab() },
                        modifier = Modifier.height(48.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Science, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("کارگاه تصویری", style = MaterialTheme.typography.labelSmall)
                    }
                }

                OutlinedButton(
                    onClick = { onToggle() },
                    modifier = Modifier.height(48.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(if (isCompleted) "لغو تیک" else "تیک تکمیل", style = MaterialTheme.typography.labelSmall, maxLines = 1, softWrap = false)
                }
            }
        }
    }
}

// ==========================================
// COMPONENT 5: DIAGNOSTIC SEARCH & FILTER
// ==========================================
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DiagnosticSearchAndFilterCard(
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    selectedSubject: String,
    onSubjectSelect: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "🔍 رادار عیب‌یاب فوری: «در چی گیر کردی؟»",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "اگر تستی را غلط می‌زنی یا درسی را نمی‌فهمی، مشکل از هوش تو نیست! فقط ۱ پیش‌نیاز را جا انداخته‌ای. مشکلت را پیدا کن تا مستقیماً به پیش‌نیاز هدایت شوی:",
                style = MaterialTheme.typography.bodySmall
            )

            OutlinedTextField(
                value = searchQuery,
                onValueChange = onQueryChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("مثلاً: ترجمه عربی، باب‌های مزید، who و whom، عروض، کسر، منطق...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = RoundedCornerShape(14.dp),
                singleLine = true
            )

            // Subject Filter Chips
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                val chips = listOf(
                    "all" to "همه درس‌ها",
                    "arabic" to "عربی",
                    "english" to "زبان انگلیسی",
                    "fonoon" to "علوم و فنون / عروض",
                    "math" to "ریاضی انسانی",
                    "philosophy" to "فلسفه و منطق",
                    "economics" to "اقتصاد",
                    "sociology" to "جامعه‌شناسی",
                    "history" to "تاریخ و جغرافیا",
                    "farhangian" to "فرهنگیان"
                )
                chips.forEach { (subId, label) ->
                    FilterChip(
                        selected = selectedSubject == subId,
                        onClick = { onSubjectSelect(subId) },
                        label = { Text(label, style = MaterialTheme.typography.labelSmall) }
                    )
                }
            }
        }
    }
}

// ==========================================
// COMPONENT 6: DIAGNOSTIC TROUBLE CARD
// ==========================================
@Composable
private fun DiagnosticTroubleCard(
    trouble: DiagnosticTroubleItem,
    onOpenLesson: (String) -> Unit,
    onSelectSubject: (String) -> Unit,
    onNavigateLab: () -> Unit,
    onNavigatePractice: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.surface,
                        trouble.badgeColor.copy(alpha = 0.12f)
                    )
                ),
                RoundedCornerShape(20.dp)
            )
            .testTag("diagnostic_card_${trouble.id}"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Subject badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = trouble.badgeColor.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = trouble.subjectTitle,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = trouble.badgeColor,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                Text(
                    text = "عیب‌یابی پیش‌نیاز",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Symptom / Problem
            Text(
                text = trouble.symptomProblem,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.error
            )

            // Root Cause
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "🔎 ریشه مشکل کجاست؟",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = trouble.rootCause,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Missing Prerequisite (پیش‌نیاز واجب)
            Surface(
                color = trouble.badgeColor.copy(alpha = 0.10f),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "🚨 پیش‌نیاز واجبی که جا انداختی و باید بخونی:",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = trouble.badgeColor
                    )
                    Text(
                        text = trouble.missingPrerequisite,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // Quick 5-min cure
            Text(
                text = "💡 درمان ۵ دقیقه‌ای: ${trouble.quickFiveMinuteCure}",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )

            HorizontalDivider()

            // Direct Links to Lesson / Lab
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (trouble.targetLessonId != null) {
                    Button(
                        onClick = { onOpenLesson(trouble.targetLessonId) },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = trouble.badgeColor.copy(alpha = 0.85f))
                    ) {
                        Icon(Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("رفتن به درسنامه این پیش‌نیاز", style = MaterialTheme.typography.labelSmall, maxLines = 1, softWrap = false)
                    }
                } else {
                    Button(
                        onClick = { onSelectSubject(trouble.subjectId) },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = trouble.badgeColor.copy(alpha = 0.85f))
                    ) {
                        Icon(Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("درس‌های ${trouble.subjectTitle}", style = MaterialTheme.typography.labelSmall, maxLines = 1, softWrap = false)
                    }
                }

                if (trouble.targetLabTab) {
                    OutlinedButton(
                        onClick = { onNavigateLab() },
                        modifier = Modifier.height(48.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Science, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("کارگاه تعاملی", style = MaterialTheme.typography.labelSmall, maxLines = 1, softWrap = false)
                    }
                }

                OutlinedButton(
                    onClick = { onNavigatePractice() },
                    modifier = Modifier.height(48.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Quiz, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("تست", style = MaterialTheme.typography.labelSmall, maxLines = 1, softWrap = false)
                }
            }
        }
    }
}

// ==========================================
// COMPONENT 7: TOP RANKER TRANSFORMATION FORMULA
// ==========================================
@Composable
private fun TopRankerTransformationFormulaCard(
    onNavigatePlanner: () -> Unit,
    onNavigatePractice: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = "🏆 چگونه از صفر مطلق به رتبه تک‌رقمی کنکور برسیم؟",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "هیچ‌کس با رتبه ۱ به دنیا نیامده است! تفاوت رتبه‌های زیر ۱۰۰ با دیگران فقط در ۴ اصل علمی زیر است که اگر مو به مو پیاده کنی، جهش ترازت حتمی است:",
                style = MaterialTheme.typography.bodySmall
            )

            // Secret 1
            Surface(
                color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.25f),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "۱. منحنی فراموشی ابینگهاوس (قانون مرور ۲۴ ساعته):",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    Text(
                        text = "اگر درسی را خواندی و تا ۲۴ ساعت بعد مرور نکردی، مغزت ۸۰٪ آن را دور می‌ریزد! اما اگر بعد از ۲۴ ساعت، فقط ۳ دقیقه فلش‌کارت‌های لایتنر همان درس را مرور کنی، تا ۱۰ روز در حافظه بلندمدت قفل می‌شود.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // Secret 2
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "۲. فرمول طلایی شاغلین و سربازمعلم‌ها (مدیریت زمان ۱۶:۰۰ تا ۲۳:۰۰):",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "اگر صبح تا ظهر مدرسه یا سر کاری، اصلاً نگران نباش! از ساعت ۱۶:۰۰ تا ۲۲:۳۰ دقیقاً ۵ ساعت وقت مفید داری (۳ پارت مطالعاتی ۹۰ دقیقه‌ای). پنجشنبه و جمعه‌ها هم روزی ۸ تا ۱۰ ساعت می‌خوانی. با همین روتین رتبه ۲ کنکور انسانی سربازمعلم شد!",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // Secret 3
            Surface(
                color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.25f),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "۳. دفترچه تحلیل آزمون (قتلگاه بی‌دقتی‌ها):",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = "آزمون آزمایشی بدون تحلیل مثل تیراندازی در تاریکی است! هر تستی که غلط زدی یا نزدی، به یکی از این ۳ علت است: الف) پیش‌نیاز را بلد نبودم ⬅️ سریع درسنامه را بخوان؛ ب) بی‌دقتی کردم (مثلاً فعل منفی بود) ⬅️ دور فعل خط بکش؛ ج) وقت کم آوردم ⬅️ تکنیک ضربدر-منها بزن.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // Secret 4
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "۴. استراتژی زمان‌های نقصانی و ضربدر-منها در دفترچه:",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "در کنکور سوالات ۳ نوع هستند: ۱) آب خوردن (در ۲۰ ثانیه بزن)؛ ۲) وقت‌گیر ولی بلد (ضربدر بزن و در دور دوم برگرد)؛ ۳) سخت و ناشناخته (منها بزن و هرگز بهش نگاه نکن!). با این روش ۲۰ دقیقه آخر دفترچه را ذخیره می‌کنی.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = { onNavigatePlanner() },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("تنظیم برنامه‌ریز هوشمند", maxLines = 1, softWrap = false)
                }
                OutlinedButton(
                    onClick = { onNavigatePractice() },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("جعبه لایتنر ابینگهاوس", maxLines = 1, softWrap = false)
                }
            }
        }
    }
}

// ==========================================
// DATA BUILDER: CHRONOLOGICAL ROADMAP (20 STEPS)
// ==========================================
private fun buildChronologicalRoadmapSteps(): List<RoadmapStepItem> = listOf(
    // PHASE 0: شالوده بتنی و پیش‌نیازهای زیرِ صفر
    RoadmapStepItem(
        id = "step_01_arabic_zero",
        phaseNumber = 0,
        phaseTitle = "فاز ۰: شالوده بتنی زیرِ صفر",
        phaseBadgeColor = Color(0xFFEF4444),
        stepIndex = 1,
        stepTitle = "الفبای عربی، ۶ علامت ریز، فاعل و مفعول، ضمایر متصل و منفصل",
        subjectId = "arabic",
        subjectName = "عربی جامع انسانی",
        whyFirst = "تا زمانی که نتوانی فرق اسم و فعل و ضمیر (هُو، هِیَ، ـهُ، ـها) را تشخیص دهی، خواندن کل قواعد دهم تا دوازدهم وقت تلف کردن است!",
        whatToMaster = "• ۶ علامت (ـَ ـِ ـُ ـْ ـً ـٍ ـٌ) و صدای هر کدام\n• تشخیص اسم با «الـ»، «ة» و تنوین\n• فاعل (علامت ضمه ـُ) و مفعول (علامت فتحه ـَ)\n• ضمایر جدا و چسبیده در کل کتاب‌ها",
        targetLessonId = "arabic_0000_absolute_zero_7th_to_12th_khodemoni",
        hasLab = true,
        durationRecommendation = "زمان پیشنهادی: ۴ روز (روزی ۱ ساعت)",
        trapAlert = "حفظ کردن صیغه‌ها بدون دانستن ضمایر باعث می‌شود سر جلسه ماضی و مضارع را قاطی کنی!"
    ),
    RoadmapStepItem(
        id = "step_02_english_zero",
        phaseNumber = 0,
        phaseTitle = "فاز ۰: شالوده بتنی زیرِ صفر",
        phaseBadgeColor = Color(0xFFEF4444),
        stepIndex = 2,
        stepTitle = "قطار ۳ واگنه انگلیسی (Subject-Verb-Object) + رادار زمان‌ها بدون ترجمه",
        subjectId = "english",
        subjectName = "زبان انگلیسی جامع",
        whyFirst = "گرامر کنکور ترجمه نمی‌خواهد! اگر بدانی فاعل اول جمله است و فعل کجاست، با نگاه کردن به قید زمان آخر جمله تست را در ۲ ثانیه می‌زنی.",
        whatToMaster = "• جایگاه Subject (فاعل) و Verb (فعل) و Object (مفعول)\n• ۴ زمان اصلی: حال ساده، استمراری، گذشته و آینده\n• افعال کمکی مدال (can, will, should) و فعل ساده بعد از آن‌ها",
        targetLessonId = "english_00_seventh_grade_train",
        hasLab = true,
        durationRecommendation = "زمان پیشنهادی: ۳ روز (روزی ۴۵ دقیقه)",
        trapAlert = "تلاش برای معنی کردن کل جمله انگلیسی وقتت را می‌گیرد؛ فقط به کلمه قبل و بعد از جای خالی نگاه کن!"
    ),
    RoadmapStepItem(
        id = "step_03_fonoon_arooz_zero",
        phaseNumber = 0,
        phaseTitle = "فاز ۰: شالوده بتنی زیرِ صفر",
        phaseBadgeColor = Color(0xFFEF4444),
        stepIndex = 3,
        stepTitle = "عروض و قافیه از کلاس اول دبستان: هجابندی صامت و مصوت بدون خواندن آواز",
        subjectId = "arooz",
        subjectName = "عروض و قافیه",
        whyFirst = "عروض ۴۰٪ نمره علوم و فنون کنکور است. اگر هجای کوتاه (U) و بلند (—) را یاد نگیری، در تمام سال‌های دهم تا دوازدهم در تست وزن گیر می‌کنی.",
        whatToMaster = "• بخش کردن کلمات مثل کلاس اول دبستان\n• تشخیص مصوت کوتاه (ـَـِـُ) و بلند (آ، او، ای)\n• علامت‌گذاری هجاها (U و —) بدون نیاز به گوش موسیقی",
        targetLessonId = "arooz_00_zero_syllable_chopper_khodemoni",
        hasLab = true,
        durationRecommendation = "زمان پیشنهادی: ۴ روز (روزی ۱ ساعت)",
        trapAlert = "سعی نکن با آواز خواندن وزن را پیدا کنی؛ فقط حروف را بشمار و علامت هجا بگذار!"
    ),
    RoadmapStepItem(
        id = "step_04_math_zero",
        phaseNumber = 0,
        phaseTitle = "فاز ۰: شالوده بتنی زیرِ صفر",
        phaseBadgeColor = Color(0xFFEF4444),
        stepIndex = 4,
        stepTitle = "ریاضی از اول دبستان: جدول ضرب، کسرها، پرانتزها و علامت منفی",
        subjectId = "math",
        subjectName = "ریاضی و آمار انسانی",
        whyFirst = "ریاضی انسانی بسیار ساده است؛ داوطلبان فقط به خاطر اشتباه در علامت منفی (- در - می‌شود +) و جمع کسرها صفر می‌زنند!",
        whatToMaster = "• ضرب علامت‌ها و اولویت پرانتز و توان\n• جمع و تفریق کسرها با مخرج مشترک سریع\n• حل معادله درجه ۱ (معلوم‌ها یک طرف، مجهول‌ها طرف دیگر)",
        targetLessonId = "math_00_table_division_zero_to_hero",
        hasLab = false,
        durationRecommendation = "زمان پیشنهادی: ۴ روز (روزی ۱ ساعت)",
        trapAlert = "فرار از ریاضی بزرگ‌ترین اشتباه است؛ با زدن فقط ۴ تست ریاضی ترازت ۲۵۰۰ تا بالا می‌پرد!"
    ),
    RoadmapStepItem(
        id = "step_05_logic_zero",
        phaseNumber = 0,
        phaseTitle = "فاز ۰: شالوده بتنی زیرِ صفر",
        phaseBadgeColor = Color(0xFFEF4444),
        stepIndex = 5,
        stepTitle = "منطق دهم از صفر: تصور در برابر تصدیق، تعریف لغوی و مفهومی",
        subjectId = "logic",
        subjectName = "منطق جامع دهم",
        whyFirst = "منطق پایه و الفبای فلسفه است. اگر تصور و تصدیق و شروط تعریف را یاد نگیری، فلسفه یازدهم و دوازدهم برایت غیرقابل فهم می‌شود.",
        whatToMaster = "• تفاوت تصور (یک کلمه یا ترکیب بدون حکم) و تصدیق (جمله کامل با فعل است/نیست)\n• ۴ شرط تعریف درست: جامع بودن، مانع بودن، واضح‌تر بودن و دوری نبودن",
        targetLessonId = "phil_00_logic_zero_gift_box",
        hasLab = false,
        durationRecommendation = "زمان پیشنهادی: ۳ روز (روزی ۱ ساعت)",
        trapAlert = "حفظ کردن تعاریف منطقی بدون مثال‌های روزمره باعث فراموشی در هفته بعد می‌شود."
    ),
    RoadmapStepItem(
        id = "step_06_history_zero",
        phaseNumber = 0,
        phaseTitle = "فاز ۰: شالوده بتنی زیرِ صفر",
        phaseBadgeColor = Color(0xFFEF4444),
        stepIndex = 6,
        stepTitle = "خط زمان زنجیره‌ای تاریخ ایران: از هخامنشی تا پایان قاجار",
        subjectId = "history",
        subjectName = "تاریخ جامع انسانی",
        whyFirst = "تاریخ حفظ کردن سال‌ها نیست! اگر توالی سلسله‌ها (ماد -> هخامنشی -> سلوکی -> اشکانی -> ساسانی -> طاهریان تا قاجار) را بدانی، سوالات توالی رو هوا حل می‌شوند.",
        whatToMaster = "• ترتیب زمانی ظهور و سقوط سلسله‌های ایران باستان و بعد از اسلام\n• مهم‌ترین پادشاه و پایتخت هر دوره با رمزهای داستان‌وار",
        targetLessonId = "history_00_timeline_mastery",
        hasLab = false,
        durationRecommendation = "زمان پیشنهادی: ۳ روز (روزی ۱ ساعت)",
        trapAlert = "حفظ کردن تک‌تک تاریخ‌ها به جای دیدن نقشه زنجیره‌ای و خط زمان."
    ),

    // PHASE 1: ستون‌های ترازساز دهم و یازدهم (۶۰٪ تست‌های کنکور)
    RoadmapStepItem(
        id = "step_07_arabic_8_baabs",
        phaseNumber = 1,
        phaseTitle = "فاز ۱: ستون‌های ترازساز دهم و یازدهم",
        phaseBadgeColor = Color(0xFFF59E0B),
        stepIndex = 7,
        stepTitle = "۸ باب ثلاثی مزید عربی با قالب‌های روزمره فارسی + تشخیص ماضی/مضارع/مصدر",
        subjectId = "arabic",
        subjectName = "عربی دهم و یازدهم",
        whyFirst = "۸ باب در تمام تست‌های ترجمه، تعریب و قواعد دهم تا دوازدهم تکرار می‌شود؛ بدون آن نیمی از سوالات دفترچه قفل است.",
        whatToMaster = "• ۸ قالب فارسی: اخراج، تعلیم، مسابقه، تشکر، تصادف، احترام، انفجار، استخدام\n• تشخیص فرق چشمی ماضی (تَعَلَّمَ با فتحه) و مصدر (تَعَلُّم با ضمه)\n• اسم فاعل (مُفْعِل) و اسم مفعول (مُفْعَل)",
        targetLessonId = null,
        hasLab = true,
        durationRecommendation = "زمان پیشنهادی: ۵ روز (روزی ۱ ساعت)",
        trapAlert = "حفظ کردن اسامی وزن‌ها به جای استفاده از ۸ کلمه آشنای فارسی روزمره."
    ),
    RoadmapStepItem(
        id = "step_08_fonoon_arayeha",
        phaseNumber = 1,
        phaseTitle = "فاز ۱: ستون‌های ترازساز دهم و یازدهم",
        phaseBadgeColor = Color(0xFFF59E0B),
        stepIndex = 8,
        stepTitle = "۲۴ آرایه ادبی (تشبیه، استعاره، مجاز، کنایه، ایهام) و سبک خراسانی و عراقی",
        subjectId = "fonoon",
        subjectName = "علوم و فنون دهم و یازدهم",
        whyFirst = "آرایه‌ها و سبک‌شناسی نیمی از ۳۰ سوال علوم و فنون کنکور است و بالاترین ضریب (ضریب ۳) را دارد.",
        whatToMaster = "• ۴ رکن تشبیه و تفاوت آن با استعاره (استعاره = تشبیه بی‌مادر!)\n• مجاز با علاقه کلیه و جزئیه و کنایه با معنی دور و نزدیک\n• ویژگی‌های فکری، زبانی و ادبی سبک خراسانی و عراقی",
        targetLessonId = null,
        hasLab = false,
        durationRecommendation = "زمان پیشنهادی: ۶ روز (روزی ۹۰ دقیقه)",
        trapAlert = "حفظ کردن ابیات کتاب به جای یاد گرفتن شیوه پیدا کردن آرایه در بیت جدید کنکور."
    ),
    RoadmapStepItem(
        id = "step_09_sociology_1_2",
        phaseNumber = 1,
        phaseTitle = "فاز ۱: ستون‌های ترازساز دهم و یازدهم",
        phaseBadgeColor = Color(0xFFF59E0B),
        stepIndex = 9,
        stepTitle = "جامعه‌شناسی ۱ و ۲: ۳ عینک تبیینی، تفسیری و انتقادی + کنش و جهان‌های اجتماعی",
        subjectId = "sociology",
        subjectName = "جامعه‌شناسی جامع",
        whyFirst = "جامعه‌شناسی ضریب ۲ کنکور است و با یاد گرفتن ۳ رویکرد اصلی، ۱۰۰٪ تست‌ها را بدون حفظ کردن طوطی‌وار حل می‌کنی.",
        whatToMaster = "• رویکرد پوزیتیویستی (تبیینی)، پدیدارشناسی (تفسیری) و مکتب فرانکفورت (انتقادی)\n• لایه‌های عمیق و سطحی فرهنگ و جهان غرب و جهان اسلام",
        targetLessonId = null,
        hasLab = false,
        durationRecommendation = "زمان پیشنهادی: ۵ روز (روزی ۱ ساعت)",
        trapAlert = "خواندن کلمات سنگین بدون تصویرسازی با مصادیق روزمره جامعه ایران."
    ),
    RoadmapStepItem(
        id = "step_10_psychology_11",
        phaseNumber = 1,
        phaseTitle = "فاز ۱: ستون‌های ترازساز دهم و یازدهم",
        phaseBadgeColor = Color(0xFFF59E0B),
        stepIndex = 10,
        stepTitle = "روان‌شناسی یازدهم: رشد پیاژه، حافظه، فراموشی، انگیزش و استرس",
        subjectId = "psychology",
        subjectName = "روان‌شناسی یازدهم",
        whyFirst = "روان‌شناسی فقط ۱ کتاب دارد اما ۲۰ سوال کنکور (ضریب ۲) را شامل می‌شود؛ زودبازده‌ترین درس کنکور انسانی است!",
        whatToMaster = "• ۴ مرحله رشد شناختی پیاژه (حسی‌حرکتی، پیش‌عملیاتی، عینی، صوری)\n• حافظه حسی، کوتاه‌مدت و بلندمدت و اثر موقعیت زنجیره‌ای\n• روش‌های حل مسئله: الگوریتم در برابر بینش و اکتشافی",
        targetLessonId = null,
        hasLab = false,
        durationRecommendation = "زمان پیشنهادی: ۵ روز (روزی ۹۰ دقیقه)",
        trapAlert = "قاطی کردن نظریه اریکسون با پیاژه در سوالات ترکیبی رشد."
    ),
    RoadmapStepItem(
        id = "step_11_economics_10",
        phaseNumber = 1,
        phaseTitle = "فاز ۱: ستون‌های ترازساز دهم و یازدهم",
        phaseBadgeColor = Color(0xFFF59E0B),
        stepIndex = 11,
        stepTitle = "اقتصاد دهم: حل ۵ ثانیه‌ای مسائل مالیات تصاعدی، نقطه سربه‌سر، سود و استهلاک",
        subjectId = "economics",
        subjectName = "اقتصاد دهم",
        whyFirst = "۵ تست از ۱۵ تست اقتصاد، مسئله ریاضی است. اگر با شگردهای بدون فرمول یاد بگیری، ۵ تست را در کمتر از ۳ دقیقه صددرصد می‌زنی.",
        whatToMaster = "• روش پله‌ای مالیات تصاعدی بر درآمد\n• نقطه سربه‌سر (هزینه ثابت ÷ سود هر واحد)\n• درآمد ملی، تولید ناخالص داخلی (GDP) و ارزش افزوده",
        targetLessonId = null,
        hasLab = false,
        durationRecommendation = "زمان پیشنهادی: ۴ روز (روزی ۱ ساعت)",
        trapAlert = "حل کردن مسائل با روش تشریحی کتاب درسی که در کنکور ۵ دقیقه وقت می‌برد!"
    ),
    RoadmapStepItem(
        id = "step_12_philosophy_11",
        phaseNumber = 1,
        phaseTitle = "فاز ۱: ستون‌های ترازساز دهم و یازدهم",
        phaseBadgeColor = Color(0xFFF59E0B),
        stepIndex = 12,
        stepTitle = "فلسفه یازدهم: حیرت، چیستی معرفت، سقراط، افلاطون و ارسطو",
        subjectId = "philosophy",
        subjectName = "فلسفه ۱ یازدهم",
        whyFirst = "فلسفه یازدهم پیش‌نیاز قطعی فلسفه دوازدهم (ملاصدرا و ابن‌سینا) است؛ بدون شناخت نظریه مثل افلاطون و ماده و صورت ارسطو، دوازدهم مبهم خواهد بود.",
        whatToMaster = "• روش مامایی سقراط در دیالوگ‌ها\n• عالم مثل افلاطون در برابر جهان تجربی ارسطو\n• شک دکارت و عقل‌گرایی در برابر تجربه‌گرایی لاک و هیوم",
        targetLessonId = null,
        hasLab = false,
        durationRecommendation = "زمان پیشنهادی: ۵ روز (روزی ۹۰ دقیقه)",
        trapAlert = "حفظ کردن اسامی فیلسوفان بدون درک سوال محوری هر کدام (آیا واقعیت وجود دارد؟ چگونه می‌شناسیم؟)."
    ),

    // PHASE 2: سقف طلایی — دوازدهم و کنکور اختصاصی فرهنگیان
    RoadmapStepItem(
        id = "step_13_twelfth_nahaee",
        phaseNumber = 2,
        phaseTitle = "فاز ۲: دوازدهم و آزمون فرهنگیان",
        phaseBadgeColor = Color(0xFF10B981),
        stepIndex = 13,
        stepTitle = "دروس دوازدهم برای معدل ۲۰ نهایی (عربی ۳، فنون ۳، فلسفه ۲، سلامت و هویت)",
        subjectId = "fonoon",
        subjectName = "دروس نهایی دوازدهم",
        whyFirst = "سوابق تحصیلی ۶۰٪ تراز کنکور سراسری را تعیین می‌کند؛ نمره ۲۰ امتحان نهایی دوازدهم تو را صدها پله از رقبا جلو می‌اندازد.",
        whatToMaster = "• مفعول مطلق، حال و تمییز در عربی ۳\n• اختیارات شاعری و بحور عروضی دوازدهم در فنون ۳\n• وجود و ماهیت و علیت در فلسفه ۲\n• سوالات نهایی سال‌های ۱۴۰۲ و ۱۴۰۳ با پاسخنامه مصححان",
        targetLessonId = null,
        hasLab = false,
        durationRecommendation = "زمان پیشنهادی: ۱۰ روز (روزی ۲ ساعت)",
        trapAlert = "تست‌زنی صرف و فراموش کردن حل سوالات نهایی خرداد و شهریور."
    ),
    RoadmapStepItem(
        id = "step_14_farhangian_aptitude",
        phaseNumber = 2,
        phaseTitle = "فاز ۲: دوازدهم و آزمون فرهنگیان",
        phaseBadgeColor = Color(0xFF10B981),
        stepIndex = 14,
        stepTitle = "بسته هوش و استعداد معلمی: درک متن، استدلال تحلیلی، تجسم فضایی و روابط منطقی",
        subjectId = "farhangian",
        subjectName = "آزمون پذیرش دانشگاه فرهنگیان",
        whyFirst = "دفترچه ویژه فرهنگیان ۵۰٪ تراز قبولی تربیت معلم است و ضریب فوق‌العاده بالایی دارد؛ سوالات آن مهارتی هستند نه حفظی.",
        whatToMaster = "• تکنیک اسکن سریع پاراگراف برای پاسخ به سوالات درک مطلب\n• جدول استدلال تحلیلی و چیدمان منطقی داده‌ها\n• تقارن، دوران اشکال هندسی و الگوهای عددی",
        targetLessonId = null,
        hasLab = false,
        durationRecommendation = "زمان پیشنهادی: ۶ روز (روزی ۹۰ دقیقه)",
        trapAlert = "استرس گرفتن از سوالات طولانی هوش؛ این سوالات فقط ظاهر شلوغی دارند و با جدول داده‌ها در ۳۰ ثانیه حل می‌شوند."
    ),
    RoadmapStepItem(
        id = "step_15_farhangian_tarbiat",
        phaseNumber = 2,
        phaseTitle = "فاز ۲: دوازدهم و آزمون فرهنگیان",
        phaseBadgeColor = Color(0xFF10B981),
        stepIndex = 15,
        stepTitle = "تعلیم و تربیت اسلامی + کتاب مهارت معلمی استاد قرائتی",
        subjectId = "farhangian",
        subjectName = "مهارت معلمی فرهنگیان",
        whyFirst = "بخش دوم دفترچه اختصاصی فرهنگیان است و سوالات آن مستقیماً از کتاب استاد قرائتی و سند تحول بنیادین مطرح می‌شود.",
        whatToMaster = "• ویژگی‌های معلم تراز انقلاب اسلامی و روش‌های کلاسداری\n• داستان‌ها و تمثیل‌های قرآنی استاد قرائتی در تربیت کودک و نوجوان\n• شبیه‌سازی سناریوهای مصاحبه حضوری و کانون ارزیابی",
        targetLessonId = null,
        hasLab = false,
        durationRecommendation = "زمان پیشنهادی: ۴ روز (روزی ۱ ساعت)",
        trapAlert = "نادیده گرفتن این درس به بهانه ساده بودن؛ تراز این درس تعیین‌کننده دعوت به مصاحبه است."
    ),
    RoadmapStepItem(
        id = "step_16_dini_comprehensive",
        phaseNumber = 2,
        phaseTitle = "فاز ۲: دوازدهم و آزمون فرهنگیان",
        phaseBadgeColor = Color(0xFF10B981),
        stepIndex = 16,
        stepTitle = "دین و زندگی جامع: کدگذاری پیام آیات، روابط علت و معلولی و شأن نزول",
        subjectId = "dini",
        subjectName = "دین و زندگی دهم تا دوازدهم",
        whyFirst = "دینی بالاترین ضریب عمومی در امتحانات نهایی را دارد و در مصاحبه و گزینش دانشگاه فرهنگیان پای ثابت سوالات است.",
        whatToMaster = "• کدگذاری کلمات کلیدی آیات با تیترهای کتاب درسی\n• توحید ذاتی، افعالی، صفاتی و عبادی در دینی دوازدهم\n• علل انحراف جامعه پس از پیامبر و ولایت فقیه در حکومت اسلامی",
        targetLessonId = null,
        hasLab = false,
        durationRecommendation = "زمان پیشنهادی: ۵ روز (روزی ۱ ساعت)",
        trapAlert = "حفظ کردن متن عربی آیه بدون دانستن ارتباط عبارت کلیدی آیه با مفهوم درس."
    ),

    // PHASE 3: جعبه‌ابزار رتبه برتری و سرعت (تثبیت تراز +۹۰۰۰)
    RoadmapStepItem(
        id = "step_17_cross_minus_technique",
        phaseNumber = 3,
        phaseTitle = "فاز ۳: جعبه‌ابزار رتبه برتری",
        phaseBadgeColor = Color(0xFF8B5CF6),
        stepIndex = 17,
        stepTitle = "استراتژی زمان‌های نقصانی و تکنیک ضربدر-منها در دفترچه ۱ و ۲",
        subjectId = "study_methods",
        subjectName = "مهندسی تست‌زنی کنکور",
        whyFirst = "حتی اگر ۱۰۰٪ کتاب‌ها را بلد باشی، بدون مدیریت زمان دفترچه را سفید می‌گذاری! این تکنیک ۱۵ تا ۲۰ دقیقه زمان ذخیره ایجاد می‌کند.",
        whatToMaster = "• دور اول: فقط حل تست‌های فوری و بدون شک (زیر ۳۰ ثانیه)\n• تست‌های وقت‌گیر: علامت ضربدر (برگشت در دور دوم)\n• تست‌های سخت یا مبهم: علامت منها (رد شدن قطعی بدون عذاب وجدان)",
        targetLessonId = null,
        hasLab = false,
        durationRecommendation = "زمان پیشنهادی: ۳ روز تمرین روی تست‌های زمان‌دار",
        trapAlert = "گیر دادن به یک تست سخت ریاضی یا منطق و از دست دادن ۵ تست آسان جامعه‌شناسی و اقتصاد!"
    ),
    RoadmapStepItem(
        id = "step_18_exam_analysis_journal",
        phaseNumber = 3,
        phaseTitle = "فاز ۳: جعبه‌ابزار رتبه برتری",
        phaseBadgeColor = Color(0xFF8B5CF6),
        stepIndex = 18,
        stepTitle = "تحلیل آزمون به سبک رتبه ۱: ساخت دفترچه یادداشت اشتباهات و تله‌ها",
        subjectId = "study_methods",
        subjectName = "تحلیل آزمون رتبه‌ساز",
        whyFirst = "هر سوال غلط در آزمون آزمایشی گنج توست! اگر همان روز ریشه غلطت را در نیاوری، در کنکور دقیقاً همان تله را تکرار می‌کنی.",
        whatToMaster = "• جدول ۳ ستونه: ۱) سوال چه بود؟ ۲) من چی فکر کردم؟ ۳) تله طراح کجا بود؟\n• مرور دفترچه اشتباهات در شب قبل از هر آزمون آزمایشی",
        targetLessonId = null,
        hasLab = false,
        durationRecommendation = "زمان پیشنهادی: عصر روز هر آزمون آزمایشی",
        trapAlert = "نگاه کردن به درصد و تراز و نبستن پرونده سوالات غلط و نزده."
    ),
    RoadmapStepItem(
        id = "step_19_quick_tree_review",
        phaseNumber = 3,
        phaseTitle = "فاز ۳: جعبه‌ابزار رتبه برتری",
        phaseBadgeColor = Color(0xFF8B5CF6),
        stepIndex = 19,
        stepTitle = "تورق سریع با درختواره‌های مفهومی و انیمیشن‌های قواعد در ۲۴ ساعت پایانی",
        subjectId = "study_methods",
        subjectName = "تورق سریع رتبه‌ساز",
        whyFirst = "در روزهای پایانی نباید کتاب قطور خواند؛ مرور یک درختواره درختی در ۵ دقیقه تمام نکات یک فصل را در مغز بازیابی می‌کند.",
        whatToMaster = "• مرور چشمی ۲۴ درختواره مفهومی تاریخ، فنون، فلسفه، عربی و جامعه‌شناسی\n• مرور جدول ۱۴ صیغه و فرمول‌های سریع ریاضی و اقتصاد",
        targetLessonId = null,
        hasLab = true,
        durationRecommendation = "زمان پیشنهادی: هفته آخر منتهی به کنکور",
        trapAlert = "خواندن مبحث جدید در روزهای پایانی؛ فقط مرور و تثبیت آموخته‌های قبلی!"
    ),
    RoadmapStepItem(
        id = "step_20_full_simulation_exam",
        phaseNumber = 3,
        phaseTitle = "فاز ۳: جعبه‌ابزار رتبه برتری",
        phaseBadgeColor = Color(0xFF8B5CF6),
        stepIndex = 20,
        stepTitle = "شبیه‌سازی کامل کنکورهای سراسری ۱۴۰۲ تا ۱۴۰۴ با کارنامه درصدی و شبیه‌ساز تراز",
        subjectId = "study_methods",
        subjectName = "شبیه‌سازی کنکور سراسری",
        whyFirst = "آخرین گام برای از بین بردن استرس جلسه کنکور؛ پوشیدن لباس کنکور، نشستن روی صندلی غیرراحت و زدن آزمون راس ساعت ۸:۰۰ صبح.",
        whatToMaster = "• پاسخگویی به دفترچه ۱ و دفترچه ۲ در زمان استاندارد سازمان سنجش\n• پر کردن پاسخبرگ تستی با مداد نرم مشکی بدون اشتباه در جابه‌جایی گزینه‌ها\n• محاسبه درصد با احتساب نمره منفی (۳ غلط = ۱ درست می‌سوزد)",
        targetLessonId = null,
        hasLab = false,
        durationRecommendation = "زمان پیشنهادی: هر ۳ روز یک کنکور جامع در ماه پایانی",
        trapAlert = "حل تست‌های کنکور به صورت پراکنده؛ حتماً باید کل دفترچه را یکجا و زمان‌دار بزنی."
    )
)

// ==========================================
// DATA BUILDER: 16 DIAGNOSTIC TROUBLES & CURES
// ==========================================
private fun buildAllDiagnosticTroubles(): List<DiagnosticTroubleItem> = listOf(
    DiagnosticTroubleItem(
        id = "diag_arabic_translation",
        subjectId = "arabic",
        subjectTitle = "عربی جامع",
        symptomProblem = "۱. در تست‌های ترجمه و تعریب عربی گزینه‌ها را قاطی می‌کنی و نمی‌دانی کدام درسته؟",
        rootCause = "سعی می‌کنی کلمات متن را دانه به دانه معنی کنی؛ در کنکور طراح روی «فعل (زمان و صیغه)»، «ضمیر» و «معرفه و نکره» دام می‌گذارد!",
        missingPrerequisite = "درس زیرِ صفر عربی: شناخت ۶ علامت ریز، فاعل و مفعول، و جدول ضمایر متصل و منفصل",
        quickFiveMinuteCure = "در هر ۴ گزینه اول زیر «فعل» خط بکش: ماضی (رفت)، مضارع (می‌رود)، منفی (نمی‌رود) یا مستقبل (خواهد رفت) است؟ ۳ گزینه در ۳ ثانیه خط می‌خورند!",
        targetLessonId = "arabic_0000_absolute_zero_7th_to_12th_khodemoni",
        targetLabTab = true,
        badgeColor = SubjectArabicColor
    ),
    DiagnosticTroubleItem(
        id = "diag_arabic_8_baabs",
        subjectId = "arabic",
        subjectTitle = "عربی دهم و یازدهم",
        symptomProblem = "۲. در باب‌های ثلاثی مزید (إفعال، تفعیل، مفاعلة...) قفل می‌کنی و اسم‌های عربی یادت می‌رود؟",
        rootCause = "باب‌ها را طوطی‌وار حفظ می‌کنی؛ در حالی که این باب‌ها دقیقاً همان کلمات روزمره فارسی هستند که هر روز می‌گویی!",
        missingPrerequisite = "کارگاه ۸ کلمه روزمره فارسی (اخراج، تعلیم، مسابقه...) + آجرهای رنگی ریشه و حروف زائد",
        quickFiveMinuteCure = "۸ کلمه فارسی را حفظ کن: اخراج (إفعال)، تعلیم (تفعیل)، مسابقه (مفاعلة)، تشکر (تفعّل)، تصادف (تفاعل)، احترام (افتعال)، انفجار (انفعال)، استخدام (استفعال). فتحه = فعل، ضمه = مصدر!",
        targetLessonId = null,
        targetLabTab = true,
        badgeColor = SubjectArabicColor
    ),
    DiagnosticTroubleItem(
        id = "diag_english_relative_pronouns",
        subjectId = "english",
        subjectTitle = "زبان انگلیسی دوازدهم",
        symptomProblem = "۳. در ضمایر موصولی (who / whom / which / whose) گیج می‌شوی و نمی‌دانی کدام را بگذاری؟",
        rootCause = "سعی می‌کنی کل جمله بلند انگلیسی را معنی کنی! این تست‌ها اصلاً نیازی به ترجمه ندارند.",
        missingPrerequisite = "کارگاه جعبه دوطرفه چپ و راست جای خالی در زبان انگلیسی",
        quickFiveMinuteCure = "فقط به کلمه چپ و راست جای خالی نگاه کن: چپ غیرانسان بود ⬅️ which | چپ انسان و راست فعل بود ⬅️ who | راست فاعل (I, you, Ali) بود ⬅️ whom | راست اسم بدون the بود ⬅️ whose!",
        targetLessonId = null,
        targetLabTab = true,
        badgeColor = SubjectEnglishColor
    ),
    DiagnosticTroubleItem(
        id = "diag_english_tenses",
        subjectId = "english",
        subjectTitle = "زبان انگلیسی پایه تا دوازدهم",
        symptomProblem = "۴. زمان‌های فعل انگلیسی (now, yesterday, since, tomorrow) را قاطی می‌کنی؟",
        rootCause = "به قید زمان در انتهای جمله دقت نمی‌کنی و روی معنی تک‌تک لغات متوقف می‌شوی.",
        missingPrerequisite = "درس ۰ زبان انگلیسی: قطار ۳ واگنه و رادار قیدهای زمان",
        quickFiveMinuteCure = "کلمه آخر جمله را بخوان: yesterday یا ago ⬅️ فعل گذشته (V-ed) | now یا at the moment ⬅️ am/is/are + V-ing | since یا for ⬅️ have/has + p.p | tomorrow ⬅️ will + فعل ساده!",
        targetLessonId = "english_00_seventh_grade_train",
        targetLabTab = true,
        badgeColor = SubjectEnglishColor
    ),
    DiagnosticTroubleItem(
        id = "diag_fonoon_arooz_chopping",
        subjectId = "arooz",
        subjectTitle = "عروض و قافیه",
        symptomProblem = "۵. در عروض تقطیع هجایی بلد نیستی و گوشت وزن شعر را حس نمی‌کند؟",
        rootCause = "فکر می‌کنی عروض یعنی آواز خواندن؛ در حالی که عروض فقط بخش کردن کلمات مثل کلاس اول دبستان است!",
        missingPrerequisite = "درس تقطیع هجایی از کلاس اول دبستان: صامت و مصوت کوتاه و بلند",
        quickFiveMinuteCure = "شعر را بخوان و برای هر هجا علامت بگذار: اگر مصوت کوتاه (ـَـِـُ) داشت ⬅️ علامت کوتاه (U)؛ اگر مصوت بلند (آ، او، ای) یا حرف بی‌صدا داشت ⬅️ علامت بلند (—). تمام!",
        targetLessonId = "arooz_00_zero_syllable_chopper_khodemoni",
        targetLabTab = true,
        badgeColor = SubjectAroozColor
    ),
    DiagnosticTroubleItem(
        id = "diag_fonoon_araye_tashbih_esteare",
        subjectId = "fonoon",
        subjectTitle = "علوم و فنون ادبی",
        symptomProblem = "۶. بین «تشبیه» و «استعاره» گیر می‌کنی و نمی‌توانی در بیت تشخیص بدهی؟",
        rootCause = "نمی‌دانی که استعاره در واقع همان تشبیه است که مشبه یا مشبه‌به آن حذف شده است!",
        missingPrerequisite = "درس ۲۴ آرایه ادبی علوم و فنون: فرمول ۴ رکن تشبیه و تشبیه بلیغ",
        quickFiveMinuteCure = "از خودت بپرس: آیا هر دو طرف (مثلاً یار و ماه) در بیت هستند؟ اگر هر دو هستند ⬅️ تشبیه؛ اگر فقط «ماه» آمده و منظورش یار است ⬅️ استعاره!",
        targetLessonId = null,
        targetLabTab = false,
        badgeColor = SubjectFonoonColor
    ),
    DiagnosticTroubleItem(
        id = "diag_math_minus_signs",
        subjectId = "math",
        subjectTitle = "ریاضی و آمار انسانی",
        symptomProblem = "۷. با دیدن x و فرمول دستپاچه می‌شوی و در علامت‌های منفی و مثبت غلط محاسباتی می‌آوری؟",
        rootCause = "از دوره راهنمایی مفهوم ضرب علامت‌ها و پرانتز را تجسم نکرده‌ای.",
        missingPrerequisite = "درس ۰ و ۱ ریاضی انسانی: جدول ضرب مکمل ۱۰، تقسیم پیتزایی و جمع کسرها",
        quickFiveMinuteCure = "قاعده دوستانه: دوستِ دوستم = دوستم (+ در + = +) | دشمنِ دشمنم = دوستم (- در - = +) | دشمنِ دوستم = دشمنم (- در + = -). پرانتز همیشه اولویت اول است!",
        targetLessonId = "math_00_table_division_zero_to_hero",
        targetLabTab = false,
        badgeColor = SubjectMathColor
    ),
    DiagnosticTroubleItem(
        id = "diag_economics_math_problems",
        subjectId = "economics",
        subjectTitle = "اقتصاد دهم",
        symptomProblem = "۸. در مسائل مالیات تصاعدی، نقطه سربه‌سر و استهلاک فرمول‌ها یادت می‌رود؟",
        rootCause = "مسائل را با روش‌های طولانی جبری حل می‌کنی؛ مسائل کنکور برای حل ۳۰ ثانیه‌ای طراحی شده‌اند!",
        missingPrerequisite = "درس اقتصاد دهم: شگردهای ۵ ثانیه‌ای بدون فرمول مسائل کنکور",
        quickFiveMinuteCure = "در مالیات تصاعدی جدول پله‌ای بکش و فقط مازاد هر پله را در درصد ضرب کن؛ در نقطه سربه‌سر، هزینه ثابت را تقسیم بر (قیمت فروش - هزینه متغیر) کن!",
        targetLessonId = null,
        targetLabTab = false,
        badgeColor = Color(0xFF059669)
    ),
    DiagnosticTroubleItem(
        id = "diag_logic_square_of_opposition",
        subjectId = "logic",
        subjectTitle = "منطق دهم",
        symptomProblem = "۹. در مربع تقابل منطق (تناقض، تضاد، تداخل و عکس مستوی) گزینه‌ها را قاطی می‌کنی؟",
        rootCause = "مربع تقابل را به صورت تصویری روی کاغذ نکشیده‌ای و حفظی فکر می‌کنی.",
        missingPrerequisite = "درس ۵ و ۶ منطق دهم: جعبه کادو و مربع تقابل رنگی",
        quickFiveMinuteCure = "یک مربع بکش: گوشه بالا چپ = هر الف ب است (موجبه کلی) | قطر روبرو = بعضی الف ب نیست (سالبه جزئی). قطرها همیشه با هم «متناقض» هستند (یکی راست، آن یکی حتماً دروغ)!",
        targetLessonId = "phil_00_logic_zero_gift_box",
        targetLabTab = false,
        badgeColor = Color(0xFF6366F1)
    ),
    DiagnosticTroubleItem(
        id = "diag_philosophy_12_mollasadra",
        subjectId = "philosophy",
        subjectTitle = "فلسفه ۲ دوازدهم",
        symptomProblem = "۱۰. در فلسفه دوازدهم اصطلاحات ملاصدرا، وجود و ماهیت و علیت را متوجه نمی‌شوی؟",
        rootCause = "فلسفه یازدهم (بحث سوفسطائیان، سقراط و ارسطو) را نفهمیده‌ای و وارد مباحث انتزاعی شده‌ای.",
        missingPrerequisite = "درس‌های پایه فلسفه یازدهم: تفاوت عقل و حس و چیستی حقیقت",
        quickFiveMinuteCure = "وجود یعنی «هستی و بودن» (نور خورشید)؛ ماهیت یعنی «قالب و چیستی شیء» (شیشه رنگی که نور از آن رد می‌شود). به همین سادگی!",
        targetLessonId = null,
        targetLabTab = false,
        badgeColor = SubjectPhilosophyColor
    ),
    DiagnosticTroubleItem(
        id = "diag_sociology_three_glasses",
        subjectId = "sociology",
        subjectTitle = "جامعه‌شناسی جامع",
        symptomProblem = "۱۱. در جامعه‌شناسی رویکردهای تبیینی، تفسیری و انتقادی را در تست‌ها قاطی می‌کنی؟",
        rootCause = "عینک فیلسوفان جامعه‌شناس را در ذهن تجسم نکرده‌ای.",
        missingPrerequisite = "درس جامعه‌شناسی دهم و یازدهم: داستان خودمونی ۳ عینک جامعه‌شناسان",
        quickFiveMinuteCure = "عینک تبیینی = انسان را مثل سنگ و آزمایشگاه می‌بیند (طبیعت‌گرا) | عینک تفسیری = دنبال معنی و نیت انسان در کنش است | عینک انتقادی = دنبال رهایی از ظلم و نابرابری سرمایه‌داری است!",
        targetLessonId = null,
        targetLabTab = false,
        badgeColor = Color(0xFF0D9488)
    ),
    DiagnosticTroubleItem(
        id = "diag_history_dynasties_order",
        subjectId = "history",
        subjectTitle = "تاریخ جامع انسانی",
        symptomProblem = "۱۲. توالی پادشاهان، سلسله‌ها و عهدنامه‌ها را در تاریخ فراموش می‌کنی؟",
        rootCause = "مطالب را جدا از هم حفظ می‌کنی به جای اینکه زنجیره خط زمان را به عنوان یک قصه دنبال کنی.",
        missingPrerequisite = "درس تاریخ جامع: خط زمان مصور پادشاهان و سلسله‌های ایران",
        quickFiveMinuteCure = "رمز ۵ سلسله باستان: «مهشس» (ماد ⬅️ هخامنشی ⬅️ سلوکی ⬅️ اشکانی ⬅️ ساسانی). با همین ۱ رمز ۳ تست توالی کنکور را در ۳ ثانیه می‌زنی!",
        targetLessonId = "history_00_timeline_mastery",
        targetLabTab = false,
        badgeColor = Color(0xFFD97706)
    ),
    DiagnosticTroubleItem(
        id = "diag_dini_ayah_link",
        subjectId = "dini",
        subjectTitle = "دین و زندگی جامع",
        symptomProblem = "۱۳. در دین و زندگی آیه را می‌خوانی ولی نمی‌دانی پیامش به کدام تیتر درس وصل است؟",
        rootCause = "کلمات کلیدی درون آیه را با تیترهای کتاب کدگذاری نکرده‌ای.",
        missingPrerequisite = "درس دین و زندگی جامع: دیکشنری کلمات کلیدی آیات و تیترهای کتاب",
        quickFiveMinuteCure = "در هر آیه ۱ کلمه شاه‌کلید است؛ مثلاً کلمه «یُسَبِّحُ» ⬅️ توحید در خالقیت و تسبیح همگانی؛ کلمه «ذلِکَ لِمَن لَم یَکُن...» ⬅️ ارسال رسل و اتمام حجت!",
        targetLessonId = null,
        targetLabTab = false,
        badgeColor = Color(0xFFF59E0B)
    ),
    DiagnosticTroubleItem(
        id = "diag_farhangian_aptitude_test",
        subjectId = "farhangian",
        subjectTitle = "آزمون پذیرش فرهنگیان",
        symptomProblem = "۱۴. برای آزمون فرهنگیان نمی‌دانی چطور هوش و استعداد معلمی را بخوانم؟",
        rootCause = "فکر می‌کنی مثل کنکور عادی است و باید کتاب حفظ کنی؛ در حالی که این درس مهارت تحلیلی می‌خواهد.",
        missingPrerequisite = "پکیج طلایی آزمون پذیرش دانشگاه فرهنگیان: هوش تحلیلی و مهارت معلمی",
        quickFiveMinuteCure = "تست‌های هوش کلامی و درک مطلب را با تکنیک علامت‌گذاری واژگان کلیدی بخوان و کتاب مهارت‌های معلمی استاد قرائتی را مثل داستان مرور کن!",
        targetLessonId = null,
        targetLabTab = false,
        badgeColor = SubjectFarhangianColor
    ),
    DiagnosticTroubleItem(
        id = "diag_psychology_piaget_stages",
        subjectId = "psychology",
        subjectTitle = "روان‌شناسی یازدهم",
        symptomProblem = "۱۵. در مراحل رشد شناختی پیاژه (حسی‌حرکتی، عینی، صوری) ویژگی‌های کودکان را قاطی می‌کنی؟",
        rootCause = "سنین و کارهای هر دوره را به یک کودک واقعی در خانواده‌ات وصل نکرده‌ای.",
        missingPrerequisite = "درس روان‌شناسی یازدهم: مراحل رشد پیاژه با مثال‌های روزمره",
        quickFiveMinuteCure = "حسی‌حرکتی (۰ تا ۲ سال: پایداری شیء) | پیش‌عملیاتی (۲ تا ۷ سال: خودمیان‌بینی) | عینی (۷ تا ۱۱ سال: نگهداری ذهنی حجم و وزن) | صوری (۱۲ سال به بالا: فرضیه‌سازی و انتزاعی)!",
        targetLessonId = null,
        targetLabTab = false,
        badgeColor = Color(0xFF0891B2)
    ),
    DiagnosticTroubleItem(
        id = "diag_study_exam_anxiety",
        subjectId = "study_methods",
        subjectTitle = "مهندسی کنکور",
        symptomProblem = "۱۶. سر جلسه آزمون دستپاچه می‌شوی و وقت کم می‌آوری و سوالات آسان را ندیده رها می‌کنی؟",
        rootCause = "روی سوالات سخت کلنجار می‌روی و احساس می‌کنی باید به ترتیب شماره دفترچه پاسخ بدهی!",
        missingPrerequisite = "تکنیک ضربدر-منها و استراتژی زمان‌های نقصانی در دفترچه",
        quickFiveMinuteCure = "قانون ۲ دقیقه‌ای: اگر بعد از خواندن سوال راه حل به ذهنت نرسید، فوراً ضربدر بزن و رد شو! هیچ سوالی بیش از ۱ نمره ندارد؛ سوال آسان و سخت هم‌نمره هستند!",
        targetLessonId = null,
        targetLabTab = false,
        badgeColor = Color(0xFF8B5CF6)
    )
)

// ==========================================
// INTERACTIVE ROADMAP TREE NAVIGATION CARD
// ==========================================

data class RoadmapPrerequisiteNode(
    val id: String,
    val title: String,
    val levelBadge: String,
    val subjectId: String,
    val subjectTitle: String,
    val badgeColor: Color,
    val conceptSummary: String,
    val targetLessonId: String? = null,
    val hasLab: Boolean = true,
    val children: List<RoadmapPrerequisiteNode> = emptyList()
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InteractiveRoadmapTreeNavigationCard(
    completedRoadmapSteps: Set<String>,
    onOpenLesson: (String) -> Unit,
    onSelectSubject: (String) -> Unit,
    onNavigateLab: () -> Unit,
    onNavigatePractice: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedSubjectFilter by remember { mutableStateOf("all") }

    val treeRoots = remember { buildRoadmapPrerequisiteTree() }

    val filteredRoots = remember(searchQuery, selectedSubjectFilter, treeRoots) {
        val q = searchQuery.trim()
        treeRoots.filter { root ->
            val matchSubject = selectedSubjectFilter == "all" || root.subjectId == selectedSubjectFilter
            val matchQuery = q.isEmpty() ||
                root.title.contains(q, ignoreCase = true) ||
                root.conceptSummary.contains(q, ignoreCase = true) ||
                root.children.any { child ->
                    child.title.contains(q, ignoreCase = true) ||
                    child.conceptSummary.contains(q, ignoreCase = true) ||
                    child.children.any { gc -> gc.title.contains(q, ignoreCase = true) }
                }
            matchSubject && matchQuery
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = CircleShape,
                    modifier = Modifier.size(38.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.AltRoute,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "🌳 نمای درختی تعاملی پیش‌نیازها (Tree Navigation)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "اگر مبحث پایه‌ای را یاد نگرفته‌ای، مستقیماً روی لینک پرش همان شاخه بزن تا وارد درسنامه یا کارگاه شوی!",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Search input
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("جستجو در درخت پیش‌نیازها (مثلاً: صیغه، ۷، who، عروض، کسر...)") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = RoundedCornerShape(14.dp),
                singleLine = true
            )

            // Subject Filter Chips
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                val filters = listOf(
                    "all" to "همه شاخه‌ها",
                    "arabic" to "عربی",
                    "english" to "زبان انگلیسی",
                    "arooz" to "علوم و فنون / عروض",
                    "math" to "ریاضی انسانی",
                    "philosophy" to "منطق و فلسفه",
                    "history" to "تاریخ و جغرافیا",
                    "sociology" to "جامعه و روان",
                    "farhangian" to "فرهنگیان"
                )
                filters.forEach { (subId, label) ->
                    FilterChip(
                        selected = selectedSubjectFilter == subId,
                        onClick = { selectedSubjectFilter = subId },
                        label = { Text(label, style = MaterialTheme.typography.labelSmall) }
                    )
                }
            }

            HorizontalDivider()

            // Tree Nodes Rendering
            if (filteredRoots.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "موردی یافت نشد! عبارت دیگری جستجو کنید یا فیلتر را روی «همه شاخه‌ها» بگذارید.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    filteredRoots.forEach { rootNode ->
                        RoadmapTreeNodeBranchItem(
                            node = rootNode,
                            depth = 0,
                            completedSteps = completedRoadmapSteps,
                            onOpenLesson = onOpenLesson,
                            onSelectSubject = onSelectSubject,
                            onNavigateLab = onNavigateLab,
                            onNavigatePractice = onNavigatePractice
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RoadmapTreeNodeBranchItem(
    node: RoadmapPrerequisiteNode,
    depth: Int,
    completedSteps: Set<String>,
    onOpenLesson: (String) -> Unit,
    onSelectSubject: (String) -> Unit,
    onNavigateLab: () -> Unit,
    onNavigatePractice: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(true) }
    val isMastered = node.targetLessonId != null && completedSteps.contains(node.id)

    val indentDp = (depth * 14).dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = indentDp)
    ) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = if (isMastered) MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f) else MaterialTheme.colorScheme.surfaceContainerLow,
            border = BorderStroke(1.dp, node.badgeColor.copy(alpha = if (depth == 0) 0.5f else 0.25f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Node Header Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = node.badgeColor,
                            modifier = Modifier.size(10.dp)
                        ) {}
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = node.title,
                            style = if (depth == 0) MaterialTheme.typography.titleSmall else MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = if (isMastered) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f) else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isMastered) Color(0xFF10B981).copy(alpha = 0.15f) else Color(0xFFEF4444).copy(alpha = 0.12f)
                        ) {
                            Text(
                                text = if (isMastered) "✅ فتح‌شده" else "⚠️ پیش‌نیاز کلیدی",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (isMastered) Color(0xFF047857) else Color(0xFFB91C1C),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        if (node.children.isNotEmpty()) {
                            IconButton(
                                onClick = { isExpanded = !isExpanded },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(
                                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                    contentDescription = null,
                                    tint = node.badgeColor
                                )
                            }
                        }
                    }
                }

                // Summary
                Text(
                    text = node.conceptSummary,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Direct Hyperlink Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (node.targetLessonId != null) {
                        Button(
                            onClick = { onOpenLesson(node.targetLessonId) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = node.badgeColor)
                        ) {
                            Icon(Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("🔗 پرش به درسنامه", style = MaterialTheme.typography.labelSmall)
                        }
                    } else {
                        Button(
                            onClick = { onSelectSubject(node.subjectId) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = node.badgeColor)
                        ) {
                            Icon(Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("درس‌های ${node.subjectTitle}", style = MaterialTheme.typography.labelSmall)
                        }
                    }

                    if (node.hasLab) {
                        OutlinedButton(
                            onClick = { onNavigateLab() },
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(Icons.Default.Science, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(2.dp))
                            Text("کارگاه", style = MaterialTheme.typography.labelSmall)
                        }
                    }

                    OutlinedButton(
                        onClick = { onNavigatePractice() },
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Icon(Icons.Default.Quiz, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(2.dp))
                        Text("تست", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }

        // Child Nodes
        if (isExpanded && node.children.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                node.children.forEach { childNode ->
                    RoadmapTreeNodeBranchItem(
                        node = childNode,
                        depth = depth + 1,
                        completedSteps = completedSteps,
                        onOpenLesson = onOpenLesson,
                        onSelectSubject = onSelectSubject,
                        onNavigateLab = onNavigateLab,
                        onNavigatePractice = onNavigatePractice
                    )
                }
            }
        }
    }
}

private fun buildRoadmapPrerequisiteTree(): List<RoadmapPrerequisiteNode> = listOf(
    // 1. ARABIC PREREQUISITE TREE
    RoadmapPrerequisiteNode(
        id = "tree_arabic_root",
        title = "🌴 ۱. شاخه عربی جامع (از زیرِ منفی صفر تا کنکور دوازدهم)",
        levelBadge = "پیش‌نیاز ریشه‌ای",
        subjectId = "arabic",
        subjectTitle = "عربی",
        badgeColor = SubjectArabicColor,
        conceptSummary = "اگر از کلاس هفتم تا دوازدهم قواعد و ترجمه عربی را قاطی می‌کنی، تمام شاخه‌های زیر به صورت پیش‌نیاز ریشه‌ای متصل شده‌اند:",
        targetLessonId = "arabic_0000_absolute_zero_7th_to_12th_khodemoni",
        hasLab = true,
        children = listOf(
            RoadmapPrerequisiteNode(
                id = "tree_arabic_sighas",
                title = "۱۴ صیغه عربی روی ۵ انگشت دست (غایب، مخاطب، متکلم)",
                levelBadge = "کلاس هفتم تا دهم",
                subjectId = "arabic",
                subjectTitle = "عربی",
                badgeColor = SubjectArabicColor,
                conceptSummary = "سر و دم افعال ماضی و مضارع با کد ۱ ثانیه‌ای انگشتان دست (ـوا، ـتَ، ـتِ، یـ...ونَ، تـ...ینَ)",
                targetLessonId = "arabic_0000_absolute_zero_7th_to_12th_khodemoni",
                hasLab = true
            ),
            RoadmapPrerequisiteNode(
                id = "tree_arabic_8_baabs",
                title = "۸ باب ثلاثی مزید با ۸ کلمه روزمره فارسی (اخراج، تعلیم، مسابقه...)",
                levelBadge = "پایه دهم",
                subjectId = "arabic",
                subjectTitle = "عربی",
                badgeColor = SubjectArabicColor,
                conceptSummary = "آجرهای سبز ریشه + آجرهای قرمز اضافه + اسکنر چشمی فرق ماضی (فتحه) و مصدر (ضمه)",
                targetLessonId = null,
                hasLab = true,
                children = listOf(
                    RoadmapPrerequisiteNode(
                        id = "tree_arabic_mansoobat",
                        title = "قواعد منصوبات (حال، تمییز و مفعول مطلق)",
                        levelBadge = "پایه دوازدهم",
                        subjectId = "arabic",
                        subjectTitle = "عربی",
                        badgeColor = SubjectArabicColor,
                        conceptSummary = "قید حالت (مشتق منصوب)، رفع ابهام عدد/نسبت و مصدر هم‌ریشه فعل",
                        targetLessonId = null,
                        hasLab = true
                    ),
                    RoadmapPrerequisiteNode(
                        id = "tree_arabic_istisna",
                        title = "استثناء (إلّا)، منادا و لای نفی جنس",
                        levelBadge = "پایه دوازدهم",
                        subjectId = "arabic",
                        subjectTitle = "عربی",
                        badgeColor = SubjectArabicColor,
                        conceptSummary = "مستثنی، کلمه بعد از یا (منادا) و لای نفی جنس بدون ال و تنوین",
                        targetLessonId = null,
                        hasLab = true
                    )
                )
            )
        )
    ),

    // 2. ENGLISH PREREQUISITE TREE
    RoadmapPrerequisiteNode(
        id = "tree_english_root",
        title = "🌴 ۲. شاخه زبان انگلیسی جامع (Vision 1, 2, 3)",
        levelBadge = "پیش‌نیاز ریشه‌ای",
        subjectId = "english",
        subjectTitle = "زبان انگلیسی",
        badgeColor = SubjectEnglishColor,
        conceptSummary = "قطار ۳ واگنه Subject + Verb + Object و رادار زمان‌ها بدون نیاز به ترجمه جملات",
        targetLessonId = "english_00_seventh_grade_train",
        hasLab = true,
        children = listOf(
            RoadmapPrerequisiteNode(
                id = "tree_english_relatives",
                title = "ضمایر موصولی who / whom / which / whose با فرمول جعبه دوطرفه",
                levelBadge = "زبان دوازدهم",
                subjectId = "english",
                subjectTitle = "زبان انگلیسی",
                badgeColor = SubjectEnglishColor,
                conceptSummary = "غیرانسان ⬅ which | انسان + فعل ⬅ who | انسان + فاعل ⬅ whom | مالکیت ⬅ whose",
                targetLessonId = null,
                hasLab = true
            ),
            RoadmapPrerequisiteNode(
                id = "tree_english_passive_tag",
                title = "مجهول (Be + P.P) و دنباله سؤالی (Tag Questions)",
                levelBadge = "زبان دوازدهم",
                subjectId = "english",
                subjectTitle = "زبان انگلیسی",
                badgeColor = SubjectEnglishColor,
                conceptSummary = "شیء بی‌جان + to be + قسمت سوم فعل | قانون آینه برعکس برای Tag Questions",
                targetLessonId = null,
                hasLab = true
            )
        )
    ),

    // 3. FONOON & AROOZ PREREQUISITE TREE
    RoadmapPrerequisiteNode(
        id = "tree_arooz_root",
        title = "🌴 ۳. شاخه علوم و فنون ادبی و عروض (آرایه‌ها، وزن و سبک‌شناسی)",
        levelBadge = "پیش‌نیاز ریشه‌ای",
        subjectId = "arooz",
        subjectTitle = "عروض و فنون",
        badgeColor = SubjectAroozColor,
        conceptSummary = "تقطیع هجایی از کلاس اول دبستان (U و —) بدون نیاز به خواندن آواز",
        targetLessonId = "arooz_00_zero_syllable_chopper_khodemoni",
        hasLab = true,
        children = listOf(
            RoadmapPrerequisiteNode(
                id = "tree_fonoon_arayeha",
                title = "۲۴ آرایه ادبی: تشبیه بلیغ، استعاره، مجاز، کنایه، ایهام و پارادوکس",
                levelBadge = "پایه یازدهم و دوازدهم",
                subjectId = "fonoon",
                subjectTitle = "علوم و فنون",
                badgeColor = SubjectFonoonColor,
                conceptSummary = "فرمول برعکس کردن + «مثلِ» برای تشخیص تشبیه از استعاره • کنایه و اسلوب معادله",
                targetLessonId = null,
                hasLab = false
            )
        )
    ),

    // 4. MATH PREREQUISITE TREE
    RoadmapPrerequisiteNode(
        id = "tree_math_root",
        title = "🌴 ۴. شاخه ریاضی و آمار انسانی (از اول دبستان تا کنکور دوازدهم)",
        levelBadge = "پیش‌نیاز ریشه‌ای",
        subjectId = "math",
        subjectTitle = "ریاضی انسانی",
        badgeColor = SubjectMathColor,
        conceptSummary = "جدول ضرب مکمل ۱۰، تقسیم پیتزایی، ضرب علامت‌های منفی و اولویت پرانتزها",
        targetLessonId = "math_00_table_division_zero_to_hero",
        hasLab = false,
        children = listOf(
            RoadmapPrerequisiteNode(
                id = "tree_math_eq_func",
                title = "معادله درجه ۲ (شگرد a+b+c=0) و دامنه توابع رادیکالی و کسری",
                levelBadge = "پایه دهم و یازدهم",
                subjectId = "math",
                subjectTitle = "ریاضی انسانی",
                badgeColor = SubjectMathColor,
                conceptSummary = "ریشه‌های ۱ و c/a • مخرج ≠ ۰ • زیر رادیکال ≥ ۰",
                targetLessonId = null,
                hasLab = false
            ),
            RoadmapPrerequisiteNode(
                id = "tree_math_logic_stats",
                title = "منطق ریاضی (p ⟹ q)، دنباله حسابی/هندسی و واریانس آمار",
                levelBadge = "پایه یازدهم و دوازدهم",
                subjectId = "math",
                subjectTitle = "ریاضی انسانی",
                badgeColor = SubjectMathColor,
                conceptSummary = "شرطی T ⟹ F • جاگذاری n=1,2 در دنباله‌ها • بی‌اثری جمع در واریانس",
                targetLessonId = null,
                hasLab = false
            )
        )
    ),

    // 5. PHILOSOPHY & LOGIC PREREQUISITE TREE
    RoadmapPrerequisiteNode(
        id = "tree_logic_root",
        title = "🌴 ۵. شاخه منطق دهم و فلسفه یازدهم و دوازدهم",
        levelBadge = "پیش‌نیاز ریشه‌ای",
        subjectId = "philosophy",
        subjectTitle = "فلسفه و منطق",
        badgeColor = SubjectPhilosophyColor,
        conceptSummary = "تصور و تصدیق، کلیات خمس، ۴ شرط تعریف درست و مربع تقابل",
        targetLessonId = "phil_00_logic_zero_gift_box",
        hasLab = false,
        children = listOf(
            RoadmapPrerequisiteNode(
                id = "tree_phil_11_12",
                title = "فلسفه ۱۱ (سقراط، افلاطون، ارسطو) و فلسفه ۱۲ (ابن‌سینا، سهروردی، ملاصدرا)",
                levelBadge = "پایه یازدهم و دوازدهم",
                subjectId = "philosophy",
                subjectTitle = "فلسفه",
                badgeColor = SubjectPhilosophyColor,
                conceptSummary = "عالم مثل افلاطون • حکمت مشاء، اشراق و حکمت متعالیه ملاصدرا (اصالت وجود)",
                targetLessonId = null,
                hasLab = false
            )
        )
    ),

    // 6. HISTORY & GEOGRAPHY PREREQUISITE TREE
    RoadmapPrerequisiteNode(
        id = "tree_history_root",
        title = "🌴 ۶. شاخه تاریخ ۱، ۲، ۳ و جغرافیای انسانی",
        levelBadge = "پیش‌نیاز ریشه‌ای",
        subjectId = "history",
        subjectTitle = "تاریخ و جغرافیا",
        badgeColor = Color(0xFFD97706),
        conceptSummary = "خط زمان مصور سلسله‌های ایران باستان (رمز «مهشس») و تاریخ اسلام و معاصر",
        targetLessonId = "history_00_timeline_mastery",
        hasLab = false
    ),

    // 7. SOCIOLOGY & PSYCHOLOGY PREREQUISITE TREE
    RoadmapPrerequisiteNode(
        id = "tree_sociology_root",
        title = "🌴 ۷. شاخه جامعه‌شناسی ۱، ۲، ۳ و روان‌شناسی یازدهم",
        levelBadge = "پیش‌نیاز ریشه‌ای",
        subjectId = "sociology",
        subjectTitle = "جامعه و روان",
        badgeColor = Color(0xFF0D9488),
        conceptSummary = "۳ عینک جامعه‌شناسی (تبیینی، تفسیری، انتقادی) + مراحل رشد پیاژه در روان‌شناسی",
        targetLessonId = null,
        hasLab = false
    ),

    // 8. FARHANGIAN PREREQUISITE TREE
    RoadmapPrerequisiteNode(
        id = "tree_farhangian_root",
        title = "🌴 ۸. شاخه آزمون اختصاصی پذیرش دانشگاه فرهنگیان",
        levelBadge = "دفترچه فرهنگیان",
        subjectId = "farhangian",
        subjectTitle = "فرهنگیان",
        badgeColor = SubjectFarhangianColor,
        conceptSummary = "مهارت‌های معلمی استاد قرائتی، هوش و استعداد تحلیلی و سناریوهای کانون ارزیابی",
        targetLessonId = null,
        hasLab = false
    )
)

