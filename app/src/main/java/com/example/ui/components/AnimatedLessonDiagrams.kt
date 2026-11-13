package com.example.ui.components

import com.example.data.curriculum.normalizedSubjectId
import com.example.ui.theme.resolveDynamicAccent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.data.curriculum.ZeroLesson
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

data class VisualTableRowData(
    val badge: String,
    val col1: String,
    val col2: String,
    val col3: String,
    val highlightColor: Color
)

data class VisualTreeBranchData(
    val branchTitle: String,
    val branchBadge: String,
    val branchColor: Color,
    val leaves: List<String>
)

@Composable
fun LessonInteractiveVisualCard(
    lesson: ZeroLesson,
    subjectColor: Color
) {
    LessonInteractiveVisualCard(
        lessonId = lesson.id,
        subjectId = lesson.normalizedSubjectId(),
        accentColor = subjectColor
    )
}

@Composable
fun WorkedExampleVisualDiagram(
    lesson: ZeroLesson,
    exampleIndex: Int,
    example: com.example.data.curriculum.WorkedExample,
    accentColor: Color
) {
    WorkedExampleVisualDiagram(
        lessonId = lesson.id,
        subjectId = lesson.normalizedSubjectId(),
        exampleIndex = exampleIndex,
        question = example.question,
        solution = example.stepByStepSolution,
        trapAlert = example.commonTrapAlert,
        accentColor = accentColor
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LessonInteractiveVisualCard(
    lessonId: String,
    subjectId: String,
    accentColor: Color
) {
    // 0 = Interactive Animated Diagram, 1 = Graphical Table (جدولی), 2 = Tree Diagram (درختی)
    var visualModeTab by remember(lessonId) { mutableIntStateOf(0) }
    var stepIndex by androidx.compose.runtime.saveable.rememberSaveable(lessonId) { mutableIntStateOf(0) }
    var isPlaying by remember(lessonId) { mutableStateOf(false) }
    // 0 = آرام (3400ms), 1 = عادی (2200ms), 2 = سریع (1300ms)
    var speedMode by remember(lessonId) { mutableIntStateOf(1) }
    var skipAnimationToText by remember(lessonId) { mutableStateOf(false) }

    val stepDurationMs = when (speedMode) {
        0 -> 3400L
        2 -> 1300L
        else -> 2200L
    }

    LaunchedEffect(isPlaying, speedMode, stepIndex, skipAnimationToText) {
        if (isPlaying && !skipAnimationToText && visualModeTab == 0) {
            delay(stepDurationMs)
            stepIndex = (stepIndex + 1) % 4
        }
    }

    val animatedProgress by animateFloatAsState(
        targetValue = ((stepIndex % 4) + 1) / 4f,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "stepProgressAnim"
    )
    val pulseScale = 1f
    // لهجهٔ قاب/هدر نمودار از scheme می‌آید (داینامیک کالر روشن = primary سیستم)
    val diagramAccent = resolveDynamicAccent(accentColor)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            1.dp,
            Brush.linearGradient(
                listOf(
                    diagramAccent.copy(alpha = 0.45f),
                    MaterialTheme.colorScheme.primaryContainer,
                    diagramAccent.copy(alpha = 0.25f)
                )
            )
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Top Title + Interactive Step Pill
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    diagramAccent.copy(alpha = 0.85f),
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.85f)
                                )
                            )
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = BidiFormatter.formatMixedPersianText(getVisualTitleForLesson(lessonId, subjectId)),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "نمودار علمی و انیمیشن مرحله‌به‌گام با کنترل کامل پخش و توضیح متنی همگام",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                if (visualModeTab == 0) {
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { stepIndex = (stepIndex + 1) % 4 }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.TouchApp,
                                contentDescription = "گام بعدی انیمیشن",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = BidiFormatter.formatMixedPersianText("گام ${(stepIndex % 4) + 1} از ۴"),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }

            // 3 Visual Modes Switcher: انیمیشن شکل | جدول رنگی | نمودار درختی (Responsive)
            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                val compactTabs = maxWidth < 390.dp
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val modes = listOf(
                        Triple(0, if (compactTabs) "انیمیشن و شکل" else "انیمیشن و نمودار علمی", Icons.Default.BarChart),
                        Triple(1, if (compactTabs) "جدول مقایسه‌ای" else "جدول مقایسه‌ای مبحث", Icons.Default.TableChart),
                        Triple(2, if (compactTabs) "نمودار درختی" else "نمودار درختی مفهومی", Icons.Default.AccountTree)
                    )
                    modes.forEach { (idx, label, icon) ->
                        val selected = visualModeTab == idx
                        Surface(
                            color = if (selected) accentColor else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, if (selected) accentColor else MaterialTheme.colorScheme.outlineVariant),
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { visualModeTab = idx }
                                .testTag("visual_mode_tab_$idx")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 7.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = null,
                                    tint = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            }

            when (visualModeTab) {
                0 -> {
                    // نوار کنترل واقعی انیمیشن: پخش/توقف، شروع مجدد، سرعت، و رد کردن انیمیشن (نمایش متنی)
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(
                            1.dp,
                            Brush.linearGradient(
                                listOf(
                                    diagramAccent.copy(alpha = 0.35f),
                                    MaterialTheme.colorScheme.outlineVariant
                                )
                            )
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(rememberScrollState()),
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // دکمه پخش / توقف
                                Surface(
                                    onClick = {
                                        skipAnimationToText = false
                                        isPlaying = !isPlaying
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (isPlaying) MaterialTheme.colorScheme.tertiary else accentColor,
                                    modifier = Modifier.testTag("anim_play_pause_btn")
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                            contentDescription = if (isPlaying) "توقف انیمیشن" else "پخش انیمیشن",
                                            tint = MaterialTheme.colorScheme.onPrimary,
                                            modifier = Modifier.size(15.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = if (isPlaying) "توقف" else "پخش مرحله‌ای",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onPrimary
                                        )
                                    }
                                }

                                // دکمه شروع مجدد
                                Surface(
                                    onClick = {
                                        isPlaying = false
                                        stepIndex = 0
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.surface,
                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                                    modifier = Modifier.testTag("anim_restart_btn")
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 9.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Refresh,
                                            contentDescription = "شروع مجدد از گام ۱",
                                            tint = MaterialTheme.colorScheme.onSurface,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "شروع مجدد",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }

                                // دکمه تغییر سرعت
                                val speedLabel = when (speedMode) {
                                    0 -> "سرعت: آرام (۰.۵x)"
                                    2 -> "سرعت: سریع (۱.۵x)"
                                    else -> "سرعت: عادی (۱x)"
                                }
                                Surface(
                                    onClick = { speedMode = (speedMode + 1) % 3 },
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.surface,
                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                                    modifier = Modifier.testTag("anim_speed_btn")
                                ) {
                                    Text(
                                        text = speedLabel,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Medium,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.padding(horizontal = 9.dp, vertical = 6.dp)
                                    )
                                }

                                // دکمه رد کردن انیمیشن و مشاهده تمام گام‌ها به صورت متنی
                                Surface(
                                    onClick = {
                                        isPlaying = false
                                        skipAnimationToText = !skipAnimationToText
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (skipAnimationToText) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                                    modifier = Modifier.testTag("anim_skip_text_btn")
                                ) {
                                    Text(
                                        text = if (skipAnimationToText) "بازگشت به انیمیشن" else "رد کردن انیمیشن (متن کامل)",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (skipAnimationToText) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(horizontal = 9.dp, vertical = 6.dp)
                                    )
                                }
                            }

                            // کادر توضیح متنی کاملاً همگام با مرحله فعلی انیمیشن
                            Surface(
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(1.dp, accentColor.copy(alpha = 0.35f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(10.dp),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = BidiFormatter.formatMixedPersianText(
                                            "توضیح همگام گام ${(stepIndex % 4) + 1} از ۴:"
                                        ),
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = accentColor
                                    )
                                    BiDiText(
                                        text = getSynchronizedStepExplanation(subjectId, stepIndex % 4),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }

                    if (skipAnimationToText) {
                        // حالت مطالعه متنی تمام مراحل بدون انیمیشن (برای کسانی که می‌خواهند انیمیشن را رد کنند)
                        Surface(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(20.dp),
                            border = BorderStroke(
                                1.dp,
                                Brush.linearGradient(
                                    listOf(
                                        diagramAccent.copy(alpha = 0.35f),
                                        MaterialTheme.colorScheme.outlineVariant
                                    )
                                )
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "شرح متنی کامل هر ۴ مرحله نمودار (حالت بدون انیمیشن):",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = accentColor
                                )
                                (0..3).forEach { idx ->
                                    BiDiText(
                                        text = "• گام ${BidiFormatter.toPersianDigits((idx + 1).toString())}: ${getSynchronizedStepExplanation(subjectId, idx)}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    } else {
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.22f),
                            shape = RoundedCornerShape(20.dp),
                            border = BorderStroke(
                                1.dp,
                                Brush.linearGradient(
                                    listOf(
                                        diagramAccent.copy(alpha = 0.35f),
                                        MaterialTheme.colorScheme.outlineVariant
                                    )
                                )
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                // Step Selector Pills (۱ تا ۴)
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    (0..3).forEach { idx ->
                                        val isCurrent = (stepIndex % 4) == idx
                                        Surface(
                                            color = if (isCurrent) accentColor else MaterialTheme.colorScheme.surface,
                                            shape = RoundedCornerShape(10.dp),
                                            border = BorderStroke(1.dp, if (isCurrent) accentColor else MaterialTheme.colorScheme.outlineVariant),
                                            modifier = Modifier
                                                .weight(1f)
                                                .clip(RoundedCornerShape(10.dp))
                                                .clickable {
                                                    isPlaying = false
                                                    stepIndex = idx
                                                }
                                        ) {
                                            Text(
                                                text = BidiFormatter.formatMixedPersianText("گام ${idx + 1}"),
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = if (isCurrent) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                                textAlign = TextAlign.Center,
                                                modifier = Modifier.padding(vertical = 7.dp, horizontal = 2.dp)
                                            )
                                        }
                                    }
                                }

                                // نمودار برداری علمی متناسب با درس (Canvas/SVG Vector Diagram)
                                ScientificSubjectCanvasVisualizer(
                                    subjectId = subjectId,
                                    activeStep = stepIndex % 4,
                                    animatedProgress = animatedProgress,
                                    accentColor = accentColor
                                )

                                when (subjectId) {
                                    "math" -> InteractiveMathVisualAnimator(
                                        lessonId = lessonId,
                                        accentColor = accentColor,
                                        activeStep = stepIndex % 4,
                                        pulseScale = pulseScale,
                                        onSelectStep = { stepIndex = it }
                                    )
                                    "arooz" -> InteractiveAroozVisualAnimator(
                                        lessonId = lessonId,
                                        accentColor = accentColor,
                                        activeStep = stepIndex % 4,
                                        pulseScale = pulseScale,
                                        onSelectStep = { stepIndex = it }
                                    )
                                    "fonoon" -> InteractiveFonoonVisualAnimator(
                                        accentColor = accentColor,
                                        activeStep = stepIndex % 4,
                                        pulseScale = pulseScale,
                                        onSelectStep = { stepIndex = it }
                                    )
                                    "arabic" -> InteractiveArabicVisualAnimator(
                                        accentColor = accentColor,
                                        activeStep = stepIndex % 4,
                                        pulseScale = pulseScale,
                                        onSelectStep = { stepIndex = it }
                                    )
                                    "english" -> InteractiveEnglishVisualAnimator(
                                        accentColor = accentColor,
                                        activeStep = stepIndex % 4,
                                        pulseScale = pulseScale,
                                        onSelectStep = { stepIndex = it }
                                    )
                                    "philosophy", "logic" -> InteractivePhilosophyVisualAnimator(
                                        lessonId = lessonId,
                                        subjectId = subjectId,
                                        accentColor = accentColor,
                                        activeStep = stepIndex % 4,
                                        pulseScale = pulseScale,
                                        onSelectStep = { stepIndex = it }
                                    )
                                    "social_psych_econ", "sociology", "psychology", "economics" -> InteractiveSocialPsychEconVisualAnimator(
                                        lessonId = lessonId,
                                        subjectId = subjectId,
                                        accentColor = accentColor,
                                        activeStep = stepIndex % 4,
                                        pulseScale = pulseScale,
                                        onSelectStep = { stepIndex = it }
                                    )
                                    "history_geo", "history", "geography" -> InteractiveHistoryGeoVisualAnimator(
                                        lessonId = lessonId,
                                        accentColor = accentColor,
                                        activeStep = stepIndex % 4,
                                        pulseScale = pulseScale,
                                        onSelectStep = { stepIndex = it }
                                    )
                                    "dini" -> InteractiveDiniVisualAnimator(
                                        accentColor = accentColor,
                                        activeStep = stepIndex % 4,
                                        pulseScale = pulseScale,
                                        onSelectStep = { stepIndex = it }
                                    )
                                    "farsi", "negaresh" -> InteractiveFarsiVisualAnimator(
                                        accentColor = accentColor,
                                        activeStep = stepIndex % 4,
                                        pulseScale = pulseScale,
                                        onSelectStep = { stepIndex = it }
                                    )
                                    "general_humanities", "social_identity", "media_environment" -> InteractiveGeneralHumanitiesVisualAnimator(
                                        accentColor = accentColor,
                                        activeStep = stepIndex % 4,
                                        pulseScale = pulseScale,
                                        onSelectStep = { stepIndex = it }
                                    )
                                    else -> InteractiveFarhangianVisualAnimator(
                                        accentColor = accentColor,
                                        activeStep = stepIndex % 4,
                                        onSelectStep = { stepIndex = it }
                                    )
                                }
                            }
                        }
                    }

                    BiDiText(
                        text = getVisualCaptionForLesson(lessonId, subjectId),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                1 -> {
                    SubjectGraphicalMasterTable(
                        subjectId = subjectId,
                        lessonId = lessonId,
                        accentColor = accentColor
                    )
                }

                2 -> {
                    SubjectGraphicalMasterTree(
                        subjectId = subjectId,
                        lessonId = lessonId,
                        accentColor = accentColor
                    )
                }
            }
        }
    }
}

private fun getSynchronizedStepExplanation(subjectId: String, step: Int): String {
    return when (subjectId) {
        "math" -> when (step) {
            0 -> "ابتدا داده‌های معلوم مسئله را روی محور اعداد یا جدول مشخص می‌کنیم تا بدانیم چه چیزی در دست داریم."
            1 -> "مجهول مسئله (x) را مثل یک ترازوی متعادل تنظیم می‌کنیم تا رابطه بین اعداد دیده شود."
            2 -> "عملیات ریاضی را یک‌به‌یک و بدون پرش روی دو طرف رابطه انجام می‌دهیم و تغییر روی نمودار را می‌بینیم."
            else -> "مقدار نهایی را روی محور/نمودار آزمایش می‌کنیم تا از درستی ۱۰۰٪ پاسخ مطمئن شویم."
        }
        "arabic" -> when (step) {
            0 -> "ابتدا نوع کلمه اول جمله (فعل یا اسم بودن) را از روی نشانه‌های ظاهری مشخص می‌کنیم."
            1 -> "ریشه ۳ حرفی و زمان فعل (ماضی، مضارع یا امر) و صیغه آن را تشخیص می‌دهیم."
            2 -> "انجام‌دهنده کار (فاعل با نشانه ُ) و مفعول (با نشانه َ) را با رنگ متمایز در جمله پیدا می‌کنیم."
            else -> "ترجمه دقیق و مرحله‌به‌مرحله جمله را کامل کرده و گزینه‌های نادرست را رد می‌کنیم."
        }
        "english" -> when (step) {
            0 -> "ابتدا فاعل جمله (Subject: چه کسی؟) را در ابتدای جمله انگلیسی پیدا می‌کنیم."
            1 -> "فعل کمکی یا اصلی (Verb) و زمان آن (حال، گذشته یا آینده) را بررسی می‌کنیم."
            2 -> "مفعول (Object) و قید زمان/مکان را در ادامه جمله قرار می‌دهیم."
            else -> "ساختار کامل جمله (S + V + O) را با معنی روان فارسی تطبیق می‌دهیم."
        }
        "arooz", "fonoon" -> when (step) {
            0 -> "بیت را با صدای بلند می‌خوانیم و فقط حروف ملفوظ (شنیده‌شده) را به هجاها تقسیم می‌کنیم."
            1 -> "هجاهای کوتاه (U) و بلند (—) را بر اساس کشش صدا زیر هر بخش علامت می‌زنیم."
            2 -> "نشانه‌ها را در دسته‌های ۳تایی یا ۴تایی برش می‌دهیم تا ارکان عروضی شکل بگیرند."
            else -> "نام وزن و آرایه ادبی بیت را از روی نظم تکرار شونده ارکان تشخیص می‌دهیم."
        }
        "philosophy", "logic" -> when (step) {
            0 -> "ابتدا مفهوم‌ها و مصداق‌های موجود در صورت قضیه را به زبان ساده تفکیک می‌کنیم."
            1 -> "مقدمه اول (دلیل اول) و مقدمه دوم (دلیل دوم) را در قالب جعبه‌ای کنار هم می‌چینیم."
            2 -> "رابطه حد وسط بین دو مقدمه را بررسی می‌کنیم تا ببینیم آیا نتیجه منطقی تولید می‌شود یا خیر."
            else -> "نتیجه قطعی استدلال را استخراج کرده و از نبود مغالطه اطمینان حاصل می‌کنیم."
        }
        "economics" -> when (step) {
            0 -> "داده‌های درآمد، هزینه یا قیمت را از متن مسئله جدا کرده و به زبان ساده می‌نویسیم."
            1 -> "رفتار خریدار (منحنی تقاضا با شیب نزولی) و رفتار فروشنده (منحنی عرضه با شیب صعودی) را رسم می‌کنیم."
            2 -> "محل تقاطع عرضه و تقاضا (نقطه تعادل بازار) یا فرمول سود و هزینه را محاسبه می‌کنیم."
            else -> "نتیجه عددی را با واقعیت روزمره بازار مقایسه می‌کنیم."
        }
        "history", "geography", "history_geo" -> when (step) {
            0 -> "موقعیت زمانی و مکانی موضوع را روی خط زمان یا نقشه مشخص می‌کنیم."
            1 -> "وضعیت پیش از رویداد یا عوامل طبیعی و انسانی مؤثر را بررسی می‌کنیم."
            2 -> "چگونگی رخ دادن واقعه و زنجیره علت و معلولی آن را دنبال می‌کنیم."
            else -> "نتایج، پیامدها و آثار بلندمدت رویداد یا پدیده جغرافیایی را جمع‌بندی می‌کنیم."
        }
        else -> when (step) {
            0 -> "گام اول: درک مفهوم پایه به زبان ساده و اتصال آن به تجربه روزمره."
            1 -> "گام دوم: شناخت اجزا و ویژگی‌های اصلی مطابق کتاب درسی رسمی."
            2 -> "گام سوم: بررسی مثال کاربردی و تشخیص تفاوت آن با مفاهیم مشابه."
            else -> "گام چهارم: جمع‌بندی نهایی برای پاسخ‌گویی به سؤال تشریحی و تست کنکور."
        }
    }
}

@Composable
private fun ScientificSubjectCanvasVisualizer(
    subjectId: String,
    activeStep: Int,
    animatedProgress: Float,
    accentColor: Color
) {
    val axisColor = MaterialTheme.colorScheme.outline
    val secondaryColor = MaterialTheme.colorScheme.tertiary
    val surfaceColor = MaterialTheme.colorScheme.surface

    Surface(
        color = surfaceColor,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            val diagramCaption = when (subjectId) {
                "math" -> "نمودار برداری ریاضی: محور مختصات، خط اعداد و تغییرات گام‌به‌گام تابع"
                "economics" -> "نمودار برداری اقتصاد: منحنی تقاضا (D)، منحنی عرضه (S) و نقطه تعادل بازار (E)"
                "arooz", "fonoon" -> "نوار ریتم و تقطیع عروضی: الگوی دیداری هجاهای کوتاه (U) و بلند (—)"
                "philosophy", "logic" -> "نمودار ساختار استدلال منطقی و نسبت بین مفاهیم (مقدمه ۱ + مقدمه ۲ ⬅ نتیجه)"
                "history", "geography", "history_geo" -> "خط زمان تصویری (Timeline) و مقطع علت و معلولی"
                else -> "نقشه پیشرفت مرحله‌ای مفهوم از پیش‌نیاز تا تسلط کامل"
            }
            Text(
                text = diagramCaption,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(92.dp)
            ) {
                val w = size.width
                val h = size.height
                val padX = 24.dp.toPx()
                val padY = 14.dp.toPx()

                // خط پایه افقی
                drawLine(
                    color = axisColor.copy(alpha = 0.45f),
                    start = Offset(padX, h - padY),
                    end = Offset(w - padX, h - padY),
                    strokeWidth = 2.dp.toPx(),
                    cap = StrokeCap.Round
                )

                when (subjectId) {
                    "economics" -> {
                        // منحنی تقاضا (نزولی) و منحنی عرضه (صعودی) و نقطه تعادل
                        val startX = padX
                        val endX = padX + (w - 2 * padX) * animatedProgress
                        drawLine(
                            color = accentColor,
                            start = Offset(startX, padY),
                            end = Offset(endX, padY + (h - 2 * padY) * animatedProgress),
                            strokeWidth = 3.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                        drawLine(
                            color = secondaryColor,
                            start = Offset(startX, h - padY),
                            end = Offset(endX, (h - padY) - (h - 2 * padY) * animatedProgress),
                            strokeWidth = 3.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                        if (activeStep >= 2) {
                            drawCircle(
                                color = accentColor,
                                radius = 6.dp.toPx(),
                                center = Offset(w / 2f, h / 2f)
                            )
                        }
                    }
                    "arooz", "fonoon" -> {
                        // نمایش ضرب‌آهنگ هجای کوتاه (نیم‌دایره U) و بلند (خط کشیده —)
                        val count = 8
                        val stepW = (w - 2 * padX) / count
                        for (i in 0 until count) {
                            val isActive = i <= (activeStep + 1) * 2 - 1
                            val itemColor = if (isActive) accentColor else axisColor.copy(alpha = 0.3f)
                            val cx = padX + i * stepW + stepW / 2f
                            if (i % 2 == 0) {
                                // هجای کوتاه (U)
                                drawArc(
                                    color = itemColor,
                                    startAngle = 0f,
                                    sweepAngle = 180f,
                                    useCenter = false,
                                    topLeft = Offset(cx - 10.dp.toPx(), h / 2f - 8.dp.toPx()),
                                    size = Size(20.dp.toPx(), 16.dp.toPx()),
                                    style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                                )
                            } else {
                                // هجای بلند (—)
                                drawLine(
                                    color = itemColor,
                                    start = Offset(cx - 12.dp.toPx(), h / 2f),
                                    end = Offset(cx + 12.dp.toPx(), h / 2f),
                                    strokeWidth = 3.5.dp.toPx(),
                                    cap = StrokeCap.Round
                                )
                            }
                        }
                    }
                    else -> {
                        // نمودار مرحله‌ای ۴ گره‌ای با منحنی پیوسته و نشانگر متحرک
                        val path = Path()
                        val points = (0..3).map { idx ->
                            val px = padX + (w - 2 * padX) * (idx / 3f)
                            val py = (h - padY - 8.dp.toPx()) - (h - 2 * padY - 16.dp.toPx()) * (idx / 3f)
                            Offset(px, py)
                        }
                        path.moveTo(points.first().x, points.first().y)
                        points.drop(1).forEach { pt ->
                            path.lineTo(pt.x, pt.y)
                        }
                        drawPath(
                            path = path,
                            color = axisColor.copy(alpha = 0.3f),
                            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
                        )
                        val activeX = padX + (w - 2 * padX) * animatedProgress
                        val activeY = (h - padY - 8.dp.toPx()) - (h - 2 * padY - 16.dp.toPx()) * animatedProgress
                        drawLine(
                            color = accentColor,
                            start = points.first(),
                            end = Offset(activeX, activeY),
                            strokeWidth = 3.5.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                        points.forEachIndexed { idx, pt ->
                            val reached = idx <= activeStep
                            drawCircle(
                                color = if (reached) accentColor else axisColor.copy(alpha = 0.4f),
                                radius = if (idx == activeStep) 7.dp.toPx() else 4.5.dp.toPx(),
                                center = pt
                            )
                        }
                    }
                }
            }
        }
    }
}


/**
 * 1. MATH INTERACTIVE VISUAL ANIMATOR
 * Renders crisp nested set cards, animated number-line elevator, or clean parabola with labeled roots.
 */
@Composable
private fun InteractiveMathVisualAnimator(
    lessonId: String,
    accentColor: Color,
    activeStep: Int,
    pulseScale: Float,
    onSelectStep: (Int) -> Unit = {}
) {
    if (lessonId.contains("math_00") || lessonId.contains("math_01")) {
        val setsList = listOf(
            Triple("۱. اعداد طبیعی (ℕ)", "{ ۱، ۲، ۳، ۴، ... } — شمارش با انگشتان دست از عدد ۱ به بالا", Color(0xFF2563EB)),
            Triple("۲. اعداد حسابی (𝕎)", "{ ۰، ۱، ۲، ۳، ... } — اعداد طبیعی به اضافهٔ عدد صفر (۰)", Color(0xFF0891B2)),
            Triple("۳. اعداد صحیح (ℤ)", "{ ...، −۲، −۱، ۰، +۱، +۲، ... } — آسانسور طبقات مثبت، صفر و زیرزمین منفی", Color(0xFF059669)),
            Triple("۴. اعداد گویا (ℚ) و حقیقی (ℝ)", "کسرهای a/b (مثل ۱/۲ و −۳/۴) + رادیکال‌ها (√۲ و π) روی کل محور اعداد", Color(0xFF7C3AED))
        )
        val stepDeepDives = listOf(
            "🔍 کالبدشکافی گام ۱ (اعداد طبیعی ℕ): کوچک‌ترین عضو عدد ۱ است. صفر، اعداد منفی و کسرهای غیرساده در ℕ نیستند! • تله کنکور: عدد ۱ نه اول است و نه مرکب؛ کوچک‌ترین عدد اول و تنها عدد اولِ زوج، عدد ۲ است.",
            "🔍 کالبدشکافی گام ۲ (اعداد حسابی 𝕎): وقتی عدد صفر (۰) را به سبد اعداد طبیعی اضافه کنیم، مجموعه حسابی ساخته می‌شود: 𝕎 = ℕ ∪ {۰}. • تله کنکور: تفاوت 𝕎 − ℕ دقیقاً تک‌عضویِ {۰} است!",
            "🔍 کالبدشکافی گام ۳ (اعداد صحیح ℤ): شامل سه بخش است: مثبت‌ها (+۱، +۲، ...)، طبقه همکف (۰) و زیرزمین منفی‌ها (−۱، −۲، ...). • تله کنکور: در جمع دو بدهی (−۵ و −۳) بدهی بزرگ‌تر می‌شود (−۸)، اما در ضرب دو هم‌علامت (−۵ × −۳) جواب مثبت (+۱۵) است!",
            "🔍 کالبدشکافی گام ۴ (گویا ℚ و حقیقی ℝ): هر کسری مثل ۱/۲ یا عدد اعشاری مثل ۰/۲۵ گویا (ℚ) است، اما رادیکال‌های بدون جذر کامل (مثل √۲، √۳، √۵) و عدد π گنگ (ℚ') هستند. اجتماع هر دو کل محور اعداد حقیقی (ℝ = ℚ ∪ ℚ') را می‌سازد!"
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Surface(
                color = accentColor.copy(alpha = 0.14f),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = BidiFormatter.formatMixedPersianText("رابطه زیرمجموعه بودن اعداد:"),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = accentColor
                    )
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                        Text(
                            text = "ℕ ⊂ 𝕎 ⊂ ℤ ⊂ ℚ ⊂ ℝ",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = accentColor
                        )
                    }
                }
            }

            setsList.forEachIndexed { idx, (title, desc, col) ->
                val isSelected = idx == activeStep
                val cardScale by animateFloatAsState(
                    targetValue = if (isSelected) 1.02f else 1.0f,
                    animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
                    label = "math_set_scale"
                )
                Surface(
                    color = if (isSelected) col.copy(alpha = 0.18f) else MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(if (isSelected) 2.dp else 1.dp, col.copy(alpha = if (isSelected) 0.9f else 0.3f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(cardScale)
                        .clip(RoundedCornerShape(14.dp))
                        .clickable { onSelectStep(idx) }
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = BidiFormatter.formatMixedPersianText(title),
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                color = col
                            )
                            Surface(
                                color = if (isSelected) col else col.copy(alpha = 0.12f),
                                shape = RoundedCornerShape(50)
                            ) {
                                Text(
                                    text = BidiFormatter.formatMixedPersianText(if (isSelected) "✓ گام فعال (${idx + 1})" else "انتخاب گام ${idx + 1}"),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (isSelected) Color.White else col,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }
                        }
                        BiDiText(
                            text = desc,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }

            // Dynamic Step Deep-Dive Box so clicking Step 1..4 visibly changes rich educational content
            val activeCol = setsList[activeStep].third
            Surface(
                color = activeCol.copy(alpha = 0.12f),
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.5.dp, activeCol.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                BiDiText(
                    text = stepDeepDives[activeStep],
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    } else if (lessonId.contains("math_05") || lessonId.contains("math_12")) {
        // Visual Arithmetic & Geometric Sequence Staircase (دنباله حسابی +d و هندسی ×q)
        val seqSteps = listOf(
            Triple("جمله اول (a₁)", "شروع پلکان: مثلاً a₁ = ۳", Color(0xFF2563EB)),
            Triple("جمله دوم (a₂)", "حسابی (+d): ۳ + ۴ = ۷  |  هندسی (×q): ۳ × ۲ = ۶", Color(0xFF0891B2)),
            Triple("جمله سوم (a₃)", "حسابی (+2d): ۳ + ۸ = ۱۱  |  هندسی (×q²): ۳ × ۴ = ۱۲", Color(0xFF059669)),
            Triple("فرمول جمله nام (aₙ)", "حسابی: aₙ = a₁ + (n−1)d  |  واسطه حسابی: وسطی = میانگین چپ و راست!", accentColor)
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .border(1.dp, accentColor.copy(alpha = 0.25f), RoundedCornerShape(14.dp))
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                val heights = listOf(48.dp, 68.dp, 88.dp, 108.dp)
                val labels = listOf("a₁ = ۳", "a₂ = ۷", "a₃ = ۱۱", "aₙ")
                heights.forEachIndexed { idx, h ->
                    val isSelected = idx == activeStep
                    val col = seqSteps[idx].third
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .scale(if (isSelected) pulseScale else 1f)
                            .clickable { onSelectStep(idx) },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = BidiFormatter.formatMixedPersianText(if (idx == 0) "شروع" else "+d / ×q"),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = col,
                            maxLines = 1
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(h)
                                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 6.dp, bottomEnd = 6.dp))
                                .background(
                                    Brush.verticalGradient(
                                        listOf(
                                            if (isSelected) col.copy(alpha = 0.85f) else col.copy(alpha = 0.65f),
                                            col.copy(alpha = 0.35f)
                                        )
                                    )
                                )
                                .border(
                                    width = if (isSelected) 2.dp else 1.dp,
                                    color = col,
                                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 6.dp, bottomEnd = 6.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                                Text(
                                    text = BidiFormatter.formatLtrFormula(labels[idx]),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(horizontal = 2.dp)
                                )
                            }
                        }
                    }
                }
            }

            Surface(
                color = accentColor.copy(alpha = 0.12f),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                BiDiText(
                    text = "📐 ${seqSteps[activeStep].first}: ${seqSteps[activeStep].second}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = accentColor,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    } else if (lessonId.contains("math_06") || lessonId.contains("math_07") || lessonId.contains("math_09") || lessonId.contains("math_10") || lessonId.contains("math_11")) {
        // Visual Probability & Statistics Interactive Chart (آمار، واریانس، شمارش و احتمال)
        val statSteps = listOf(
            Triple("گام ۱: میانگین (ترازوی تعادل داده‌ها)", "جمع کل داده‌ها تقسیم بر تعداد؛ اگر همه +c شوند میانگین هم +c می‌شود.", Color(0xFF2563EB)),
            Triple("گام ۲: واریانس (σ²) و انحراف معیار (σ)", "جمع و تفریق عدد ثابت در واریانس بی‌اثر است! فقط در ضرب در k، واریانس ×k² می‌شود.", Color(0xFF059669)),
            Triple("گام ۳: اصل ضرب و ترکیب C(n,r)", "انتخاب با ترتیب = جایگشت | انتخاب گروهی بدون ترتیب = ترکیب C(n,r).", Color(0xFF7C3AED)),
            Triple("گام ۴: فرمول احتمال P(A) = n(A) / n(S)", "تعداد حالت‌های مطلوب تقسیم بر کل حالت‌ها (همیشه بین ۰ و ۱).", accentColor)
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .border(1.dp, accentColor.copy(alpha = 0.25f), RoundedCornerShape(14.dp))
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val bars = listOf(
                    Triple("۱. میانگین: x̄ جدید = x̄ + c", 0.72f, Color(0xFF2563EB)),
                    Triple("۲. واریانس: σ² جدید = k² × σ²", 0.56f, Color(0xFF059669)),
                    Triple("۳. ترکیب گروهی: C(n,r)", 0.84f, Color(0xFF7C3AED)),
                    Triple("۴. احتمال: P(A) = n(A) / n(S)", 0.92f, accentColor)
                )
                bars.forEachIndexed { idx, (label, ratio, col) ->
                    val isSelected = idx == activeStep
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) col.copy(alpha = 0.12f) else Color.Transparent)
                            .clickable { onSelectStep(idx) }
                            .padding(vertical = 6.dp, horizontal = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        BiDiText(
                            text = label,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = col
                        )
                        LinearProgressIndicator(
                            progress = { ratio },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(9.dp)
                                .clip(RoundedCornerShape(5.dp)),
                            color = col,
                            trackColor = col.copy(alpha = 0.16f)
                        )
                    }
                }
            }
            Surface(
                color = accentColor.copy(alpha = 0.12f),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                BiDiText(
                    text = "${statSteps[activeStep].first}: ${statSteps[activeStep].second}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = accentColor,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    } else {
        // Clean Parabola & Coordinate Graph with External Legend Row (Zero overlapped Canvas text!)
        val stepDescriptions = listOf(
            "گام ۱: رسم محورهای مختصات (افقی X و عمودی Y) و تعیین جهت دهانه سهمی با ضریب a",
            "گام ۲: محاسبه دلتا (Δ = b² − 4ac)؛ وقتی Δ > 0 است سهمی در ۲ نقطه محور افقی را قطع می‌کند",
            "گام ۳: پیدا کردن نقاط قرمز برخورد با محور افقی (ریشه‌های معادله: x₁ و x₂)",
            "گام ۴: نقطه سبز قله سهمی یعنی رأس S(−b/2a , −Δ/4a) که بیشترین یا کمترین مقدار تابع است"
        )
        val animProgress by animateFloatAsState(
            targetValue = (activeStep + 1) / 4f,
            animationSpec = tween(500),
            label = "math_graph_anim"
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .border(1.dp, accentColor.copy(alpha = 0.25f), RoundedCornerShape(14.dp))
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Separate Top Legend Row (Never overlaps with the Canvas!)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = Color(0xFF059669).copy(alpha = 0.15f),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, Color(0xFF059669)),
                        modifier = Modifier.clickable { onSelectStep(3) }
                    ) {
                        Text(
                            text = "🟢 نقطه سبز = رأس سهمی (S)",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF059669),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Surface(
                        color = Color(0xFFE11D48).copy(alpha = 0.15f),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, Color(0xFFE11D48)),
                        modifier = Modifier.clickable { onSelectStep(2) }
                    ) {
                        Text(
                            text = BidiFormatter.formatMixedPersianText("🔴 نقاط قرمز = ریشه‌ها (x₁ , x₂)"),
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFFE11D48),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(145.dp)
                ) {
                    val axisColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.35f)
                    Canvas(modifier = Modifier.matchParentSize()) {
                        val w = size.width
                        val h = size.height
                        val originX = w * 0.5f
                        val originY = h * 0.76f

                        // Grid lines
                        drawLine(axisColor, Offset(w * 0.08f, originY), Offset(w * 0.92f, originY), strokeWidth = 3f)
                        drawLine(axisColor, Offset(originX, h * 0.08f), Offset(originX, h * 0.94f), strokeWidth = 3f)

                        // Smooth Parabola curve
                        val path = Path()
                        val span = w * 0.34f
                        val maxStep = (-30 + (60 * animProgress).toInt()).coerceAtLeast(-25)
                        var first = true
                        for (i in -30..maxStep) {
                            val norm = i / 30f
                            val px = originX + norm * span
                            val py = originY - (1f - norm * norm * 1.65f) * (h * 0.58f)
                            if (first) {
                                path.moveTo(px, py)
                                first = false
                            } else {
                                path.lineTo(px, py)
                            }
                        }
                        drawPath(path = path, color = accentColor, style = Stroke(width = 6f, cap = StrokeCap.Round))

                        // Roots x1, x2 and Vertex S
                        val rootOffset = span * 0.78f
                        if (activeStep >= 1) {
                            drawCircle(Color(0xFFE11D48), radius = 10f * pulseScale, center = Offset(originX - rootOffset, originY))
                            drawCircle(Color(0xFFE11D48), radius = 10f * pulseScale, center = Offset(originX + rootOffset, originY))
                        }
                        if (activeStep >= 2) {
                            drawCircle(Color(0xFF059669), radius = 11f * pulseScale, center = Offset(originX, originY - h * 0.58f))
                        }
                    }

                    // Coordinate Labels Overlay so points are clearly identified
                    if (activeStep >= 2) {
                        Surface(
                            color = Color(0xFF059669),
                            shape = RoundedCornerShape(50),
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(top = 4.dp)
                        ) {
                            Text(
                                text = BidiFormatter.formatMixedPersianText("رأس S = (-b/2a)"),
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }
                    if (activeStep >= 1) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .padding(horizontal = 28.dp, vertical = 2.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Surface(
                                color = Color(0xFFE11D48).copy(alpha = 0.14f),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = BidiFormatter.formatMixedPersianText("ریشه x₂"),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color(0xFFE11D48),
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 1.dp)
                                )
                            }
                            Surface(
                                color = Color(0xFFE11D48).copy(alpha = 0.14f),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = BidiFormatter.formatMixedPersianText("ریشه x₁"),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color(0xFFE11D48),
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 1.dp)
                                )
                            }
                        }
                    }
                }
            }

            Surface(
                color = accentColor.copy(alpha = 0.12f),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                BiDiText(
                    text = stepDescriptions[activeStep],
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = accentColor,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}

/**
 * 2. AROOZ (PROSODY) INTERACTIVE VISUAL ANIMATOR
 * Responsive 2-Row Syllable Clapping Blocks (4 syllables per row so labels never wrap or squish!).
 */
@Composable
private fun InteractiveAroozVisualAnimator(
    lessonId: String,
    accentColor: Color,
    activeStep: Int,
    pulseScale: Float,
    onSelectStep: (Int) -> Unit = {}
) {
    val isClappingLesson = lessonId.contains("arooz_00")
    val syllables = if (isClappingLesson) {
        listOf(
            Triple("آب", "– (بلند)", true),
            Triple("با", "– (بلند)", true),
            Triple("مَـ", "U (کوتاه)", false),
            Triple("دَر", "– (بلند)", true),
            Triple("سَـ", "U (کوتاه)", false),
            Triple("تُـ", "U (کوتاه)", false),
            Triple("بَند", "–U (کشیده)", true),
            Triple("یار", "–U (کشیده)", true)
        )
    } else {
        listOf(
            Triple("کِ (مَـ)", "U (کوتاه)", false),
            Triple("عِش (فا)", "– (بلند)", true),
            Triple("قا (عی)", "– (بلند)", true),
            Triple("سان (لُن)", "– (بلند)", true),
            Triple("نَـ (مَـ)", "U (کوتاه)", false),
            Triple("مو (فا)", "– (بلند)", true),
            Triple("دَو (عی)", "– (بلند)", true),
            Triple("وَل (لُن)", "– (بلند)", true)
        )
    }

    val stepExplanations = if (isClappingLesson) {
        listOf(
            "گام ۱ (کلاس اول دبستان): هر بار که دهانت باز و بسته شود = ۱ بخش (هجا). مثل «آب» (۱ بخش) و «بابا» (۲ بخش).",
            "گام ۲ (هجای کوتاه U): اگر بخش فقط ۲ حرف داشت و صدایش کوتاه بود (ـَ ـِ ـُ مثل نَـ ، بَـ ، تُـ)  می‌شود U (یک بشکن تند!).",
            "گام ۳ (هجای بلند –): اگر بخش صدای کشیده داشت (آ، او، ای مثل با، بو) یا ۳ حرفی بود (مثل دَر، شَب)  می‌شود خط صاف (–).",
            "گام ۴ (هجای کشیده –U): اگر بخش ۴ حرفی بود (مثل کار، دوست، بَند)  در عروض به اندازهٔ ۱ بلند + ۱ کوتاه (–U) حساب می‌شود!"
        )
    } else {
        listOf(
            "گام ۱: ابتدا ۴ هجای اول شعر را بخش‌بخش می‌خوانیم: «کِ (U) / عِش (–) / قا (–) / سان (–)»",
            "گام ۲: بارکد ۴ هجای اول درآمد: U – – – (یعنی هجای ۱ کوتاه و بقیه بلند هستند).",
            "گام ۳: طبق رمز «کوتاه در خانه ۱ = هَزَج»، وزن این شعر رکن «مَفاعیلُن (تَ تَن تَن تَن)» است!",
            "گام ۴: چون همین رکن ۴ بار در مصراع تکرار شده، نام وزن می‌شود: بحر هزج مثمن سالم!"
        )
    }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        // Split 8 syllables into 2 spacious 4-syllable rows (رکن اول و رکن دوم) so mobile screens never squish!
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .border(1.dp, accentColor.copy(alpha = 0.25f), RoundedCornerShape(14.dp))
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                syllables.chunked(4).forEachIndexed { rowIdx, rowSyllables ->
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = BidiFormatter.formatMixedPersianText(
                                if (isClappingLesson) {
                                    if (rowIdx == 0) "🎵 ردیف ۱: تمرین بخش‌های کوتاه (U) و بلند (–)" else "🎵 ردیف ۲: تمرین بخش‌های کشیده (–U)"
                                } else {
                                    if (rowIdx == 0) "🎵 رکن اول مصراع: کِ عِش قا سان = مَفاعیلُن (U – – –)" else "🎵 رکن دوم مصراع: نَـ مو دَو وَل = مَفاعیلُن (U – – –)"
                                }
                            ),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = accentColor
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.Bottom
                        ) {
                            rowSyllables.forEachIndexed { colIdx, (word, code, isLong) ->
                                val globalIdx = rowIdx * 4 + colIdx
                                val isHighlighted = (globalIdx / 2) == activeStep
                                val barHeight by animateDpAsState(
                                    targetValue = if (isLong) {
                                        if (isHighlighted) 48.dp else 40.dp
                                    } else {
                                        if (isHighlighted) 32.dp else 26.dp
                                    },
                                    animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
                                    label = "arooz_bar_h"
                                )
                                val barColor = if (isLong) accentColor else Color(0xFF0284C7)
                                val shortTag = when {
                                    code.contains("–U") -> "کشیده"
                                    isLong -> "بلند"
                                    else -> "کوتاه"
                                }

                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .scale(if (isHighlighted) pulseScale else 1f)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(barColor.copy(alpha = 0.08f))
                                        .border(
                                            width = if (isHighlighted) 1.5.dp else 1.dp,
                                            color = barColor.copy(alpha = if (isHighlighted) 0.75f else 0.25f),
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                        .clickable { onSelectStep((globalIdx / 2).coerceIn(0, 3)) }
                                        .padding(vertical = 6.dp, horizontal = 4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = shortTag,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = barColor,
                                        textAlign = TextAlign.Center,
                                        maxLines = 1
                                    )
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(barHeight)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(
                                                if (isHighlighted) barColor else barColor.copy(alpha = 0.65f)
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                                            Text(
                                                text = if (code.contains("–U")) "– U" else if (isLong) "–" else "U",
                                                color = Color.White,
                                                fontWeight = FontWeight.ExtraBold,
                                                fontSize = 14.sp
                                            )
                                        }
                                    }
                                    Text(
                                        text = word,
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        textAlign = TextAlign.Center,
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Surface(
            color = accentColor.copy(alpha = 0.12f),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            BiDiText(
                text = stepExplanations[activeStep],
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = accentColor,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

/**
 * 3. FONOON (LITERARY ARTS) INTERACTIVE VISUAL ANIMATOR
 * Shows the 4 Literary Pillars in a responsive 2x2 Grid so text never clips on mobile screens.
 */
@Composable
private fun InteractiveFonoonVisualAnimator(
    accentColor: Color,
    activeStep: Int,
    pulseScale: Float,
    onSelectStep: (Int) -> Unit = {}
) {
    data class PillarCard(
        val num: String,
        val title: String,
        val badge: String,
        val formula: String,
        val example: String,
        val color: Color
    )

    val pillars = listOf(
        PillarCard(
            "۱",
            "تشبیه (ترازوی دوکفه)",
            "هر دو طرف حاضرند!",
            "مشبه (حاضر) + مشبه‌به (حاضر) = تشبیه",
            "مثال: «دل چو آیینه است» یا اضافه تشبیهی «چراغِ عقل»",
            Color(0xFF2563EB)
        ),
        PillarCard(
            "۲",
            "استعاره (شباهت + حذف)",
            "یک طرف غایب است!",
            "شباهت داریم اما یکی از دو طرف حذف شده است",
            "مثال: «ای آفتاب خوبان» (آفتاب استعاره از معشوق) | «دستِ روزگار» (تشخیص)",
            accentColor
        ),
        PillarCard(
            "۳",
            "مجاز (غیرشباهت)",
            "رابطه جزء و کل / محل",
            "کلمه در غیر معنی اصلی به‌کار رفته اما شباهتی نیست!",
            "مثال: «سَر آن ندارد امشب» (سر = قصد و نیت) | «جهان خرم از او»",
            Color(0xFF059669)
        ),
        PillarCard(
            "۴",
            "کنایه و ایهام",
            "ضرب‌المثل و دوپهلو",
            "کنایه = جمله ضرب‌المثلی | ایهام = ۱ کلمه با ۲ معنی درست",
            "مثال کنایه: «دهان به دندان گزیدن» (پشیمانی) | «آب در غربال کردن»",
            Color(0xFFD97706)
        )
    )

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // 2x2 Responsive Grid so each pillar card has generous horizontal room
        pillars.chunked(2).forEachIndexed { rIdx, pair ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                pair.forEachIndexed { cIdx, p ->
                    val idx = rIdx * 2 + cIdx
                    val isSelected = idx == activeStep
                    Surface(
                        color = if (isSelected) p.color else p.color.copy(alpha = 0.14f),
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(if (isSelected) 2.dp else 1.dp, p.color.copy(alpha = 0.45f)),
                        modifier = Modifier
                            .weight(1f)
                            .scale(if (isSelected) pulseScale else 1f)
                            .clip(RoundedCornerShape(14.dp))
                            .clickable { onSelectStep(idx) }
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = BidiFormatter.formatMixedPersianText("${p.num}. ${p.title}"),
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = if (isSelected) Color.White else p.color,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = BidiFormatter.formatMixedPersianText(p.badge),
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isSelected) Color.White.copy(alpha = 0.92f) else MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }

        val current = pillars[activeStep]
        Surface(
            color = current.color.copy(alpha = 0.10f),
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.5.dp, current.color.copy(alpha = 0.4f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = BidiFormatter.formatMixedPersianText("🔍 کالبدشکافی ستون ${current.num}: ${current.title}"),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = current.color
                )
                Text(
                    text = BidiFormatter.formatMixedPersianText("📐 فرمول تشخیص فوری: ${current.formula}"),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = BidiFormatter.formatMixedPersianText("💡 ${current.example}"),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * 4. ARABIC INTERACTIVE VISUAL ANIMATOR
 * Comprehensive shape-based visual diagrams for Arabic 14 Conjugations (۳ اتاق کاخ صیغه‌ها + نمودار دست ۵ انگشت)
 */
@Composable
private fun InteractiveArabicVisualAnimator(
    accentColor: Color,
    activeStep: Int,
    pulseScale: Float,
    onSelectStep: (Int) -> Unit = {}
) {
    var subVisualMode by remember { mutableIntStateOf(0) }
    var selectedRoom by remember { mutableIntStateOf(0) }
    var selectedFinger by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Mode Selector Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            val modes = listOf(
                "🏛️ کاخ ۳ اتاق و ۱۴ صیغه با شکل",
                "✋ شکل هندسی ۵ انگشت دست",
                "⚡ کالبدشکافی سر، ریشه و دم"
            )
            modes.forEachIndexed { idx, label ->
                val isSelected = subVisualMode == idx
                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { subVisualMode = idx }
                        .testTag("arabic_visual_mode_$idx"),
                    shape = RoundedCornerShape(10.dp),
                    color = if (isSelected) accentColor else accentColor.copy(alpha = 0.08f),
                    border = BorderStroke(0.8.dp, if (isSelected) accentColor else accentColor.copy(alpha = 0.25f))
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) Color.White else accentColor,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        }

        when (subVisualMode) {
            0 -> {
                // MODE 0: کاخ ۳ اتاق و ۱۴ صیغه
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    // Room Tabs
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        val rooms = listOf(
                            "۱. اتاق غایب (۶ صیغه)",
                            "۲. اتاق مخاطب (۶ صیغه)",
                            "۳. اتاق متکلم (۲ صیغه)"
                        )
                        rooms.forEachIndexed { rIdx, rName ->
                            val isRoomSelected = selectedRoom == rIdx
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { selectedRoom = rIdx },
                                shape = RoundedCornerShape(8.dp),
                                color = if (isRoomSelected) accentColor.copy(alpha = 0.16f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                border = BorderStroke(if (isRoomSelected) 1.2.dp else 0.5.dp, if (isRoomSelected) accentColor else MaterialTheme.colorScheme.outlineVariant)
                            ) {
                                Text(
                                    text = rName,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = if (isRoomSelected) FontWeight.Black else FontWeight.Medium,
                                    color = if (isRoomSelected) accentColor else MaterialTheme.colorScheme.onSurface,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(vertical = 7.dp, horizontal = 2.dp),
                                    maxLines = 1
                                )
                            }
                        }
                    }

                    // Room Content Diagram
                    val roomData = when (selectedRoom) {
                        0 -> listOf(
                            Tuple6("هُوَ (او یک مرد)", "مفرد مذکر غایب", "کَتَبَ (نوشت)", "ـَ (بدون ضمیر)", "یَکْتُبُ (می‌نویسد)", "یَـ...ـُ"),
                            Tuple6("هُما (آن دو مرد)", "مثنی مذکر غایب", "کَتَبا (نوشتند)", "ـا (الف تثنیه)", "یَکْتُبانِ (می‌نویسند)", "یَـ...ـانِ"),
                            Tuple6("هُمْ (آن مردان)", "جمع مذکر غایب", "کَتَبوا (نوشتند)", "ـوا (واو جماعت)", "یَکْتُبونَ (می‌نویسند)", "یَـ...ـونَ"),
                            Tuple6("هِیَ (او یک زن)", "مفرد مؤنث غایب", "کَتَبَتْ (نوشت)", "ـَتْ (تای تانیث)", "تَکْتُبُ (می‌نویسد)", "تَـ...ـُ"),
                            Tuple6("هُما (آن دو زن)", "مثنی مؤنث غایب", "کَتَبَتا (نوشتند)", "ـَتا (الف تثنیه)", "تَکْتُبانِ (می‌نویسند)", "تَـ...ـانِ"),
                            Tuple6("هُنَّ (آن زنان)", "جمع مؤنث غایب", "کَتَبْنَ (نوشتند)", "ـْنَ (نون نسوه)", "یَکْتُبْنَ (می‌نویسند)", "یَـ...ـْنَ")
                        )
                        1 -> listOf(
                            Tuple6("أَنْتَ (تو یک مرد)", "مفرد مذکر مخاطب", "کَتَبْتَ (نوشتی)", "ـْتَ", "تَکْتُبُ (می‌نویسی)", "تَـ...ـُ"),
                            Tuple6("أَنْتُما (شما دو مرد)", "مثنی مذکر مخاطب", "کَتَبْتُما (نوشتید)", "ـْتُما", "تَکْتُبانِ (می‌نویسید)", "تَـ...ـانِ"),
                            Tuple6("أَنْتُمْ (شما مردان)", "جمع مذکر مخاطب", "کَتَبْتُمْ (نوشتید)", "ـْتُمْ", "تَکْتُبونَ (می‌نویسید)", "تَـ...ـونَ"),
                            Tuple6("أَنْتِ (تو یک زن)", "مفرد مؤنث مخاطب", "کَتَبْتِ (نوشتی)", "ـْتِ", "تَکْتُبینَ (می‌نویسی)", "تَـ...ـینَ"),
                            Tuple6("أَنْتُما (شما دو زن)", "مثنی مؤنث مخاطب", "کَتَبْتُما (نوشتید)", "ـْتُما", "تَکْتُبانِ (می‌نویسید)", "تَـ...ـانِ"),
                            Tuple6("أَنْتُنَّ (شما زنان)", "جمع مؤنث مخاطب", "کَتَبْتُنَّ (نوشتید)", "ـْتُنَّ", "تَکْتُبْنَ (می‌نویسید)", "تَـ...ـْنَ")
                        )
                        else -> listOf(
                            Tuple6("أَنَا (من)", "متکلم وَحْدَه", "کَتَبْتُ (نوشتم)", "ـْتُ (تای فاعل)", "أَکْتُبُ (می‌نویسم)", "أَ...ـُ"),
                            Tuple6("نَحْنُ (ما)", "متکلم مَعَ‌الْغَیْر", "کَتَبْنا (نوشتیم)", "ـْنا (ضمیر نا)", "نَکْتُبُ (می‌نویسیم)", "نَـ...ـُ")
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        roomData.forEach { item ->
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = MaterialTheme.colorScheme.surface,
                                border = BorderStroke(0.7.dp, accentColor.copy(alpha = 0.35f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1.1f)) {
                                        Text(
                                            text = item.t1,
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Black,
                                            color = accentColor
                                        )
                                        Text(
                                            text = item.t2,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    Column(
                                        modifier = Modifier.weight(1f),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = "ماضی: ${item.t3}",
                                            style = MaterialTheme.typography.bodySmall,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFFB45309)
                                        )
                                        Text(
                                            text = "رمز: ${item.t4}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    Column(
                                        modifier = Modifier.weight(1f),
                                        horizontalAlignment = Alignment.End
                                    ) {
                                        Text(
                                            text = "مضارع: ${item.t5}",
                                            style = MaterialTheme.typography.bodySmall,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF1D4ED8)
                                        )
                                        Text(
                                            text = "نشانه: ${item.t6}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            1 -> {
                // MODE 1: شکل هندسی ۵ انگشت دست
                val fingerNames = listOf(
                    "۱. انگشت کوچک (خنصر: غایب مذکر)",
                    "۲. انگشت حلقه (بنصر: غایب مؤنث)",
                    "۳. انگشت وسط (وسطی: مخاطب مذکر)",
                    "۴. انگشت اشاره (سبابه: مخاطب مؤنث)",
                    "۵. انگشت شست (ابهام: متکلم من/ما)"
                )
                val fingerDetails = listOf(
                    Triple("بند پایین: هُوَ (او یک مرد - ذَهَبَ)", "بند وسط: هُما (آن دو مرد - ذَهَبا)", "بند بالا: هُمْ (آن مردان - ذَهَبوا)"),
                    Triple("بند پایین: هِیَ (او یک زن - ذَهَبَتْ)", "بند وسط: هُما (آن دو زن - ذَهَبَتا)", "بند بالا: هُنَّ (آن زنان - ذَهَبْنَ)"),
                    Triple("بند پایین: أَنْتَ (تو مرد - ذَهَبْتَ)", "بند وسط: أَنْتُما (شما دو مرد - ذَهَبْتُما)", "بند بالا: أَنْتُمْ (شما مردان - ذَهَبْتُمْ)"),
                    Triple("بند پایین: أَنْتِ (تو زن - ذَهَبْتِ)", "بند وسط: أَنْتُما (شما دو زن - ذَهَبْتُما)", "بند بالا: أَنْتُنَّ (شما زنان - ذَهَبْتُنَّ)"),
                    Triple("بند پایین: أَنَا (من - ذَهَبْتُ / أَذْهَبُ)", "بند بالا: نَحْنُ (ما - ذَهَبْنا / نَذْهَبُ)", "شست دارای ۲ بند است!")
                )

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    // Finger Selector Chips
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        fingerNames.forEachIndexed { fIdx, fName ->
                            val isFSelected = selectedFinger == fIdx
                            Surface(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { selectedFinger = fIdx },
                                shape = RoundedCornerShape(8.dp),
                                color = if (isFSelected) accentColor else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                border = BorderStroke(0.6.dp, if (isFSelected) accentColor else MaterialTheme.colorScheme.outlineVariant)
                            ) {
                                Text(
                                    text = fName,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isFSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }

                    // 3-Phalanx Structure Card
                    val curDetails = fingerDetails[selectedFinger]
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = accentColor.copy(alpha = 0.08f),
                        border = BorderStroke(1.dp, accentColor.copy(alpha = 0.4f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "✋ ${fingerNames[selectedFinger]}: ۳ بند انگشت با ۳ صیغه",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Black,
                                color = accentColor
                            )

                            val phalanxes = listOf(
                                "بند ۳ (بالا: جمع)" to curDetails.third,
                                "بند ۲ (وسط: مثنی)" to curDetails.second,
                                "بند ۱ (پایین: مفرد)" to curDetails.first
                            )
                            phalanxes.forEach { (pTitle, pText) ->
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.surface,
                                    border = BorderStroke(0.6.dp, accentColor.copy(alpha = 0.3f)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 10.dp, vertical = 7.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = pTitle,
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Black,
                                            color = accentColor
                                        )
                                        Text(
                                            text = pText,
                                            style = MaterialTheme.typography.bodySmall,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            2 -> {
                // MODE 2: کالبدشکافی سر، ریشه و دم فعل
                data class ArabicSample(
                    val stepTitle: String,
                    val prefix: String,
                    val root: String,
                    val suffix: String,
                    val fullWord: String,
                    val meaning: String
                )

                val samples = listOf(
                    ArabicSample("گام ۱: فعل ماضی", "— (ندارد)", "ذَهَبْـ", "ـتُمْ (شما مردان)", "ذَهَبْتُمْ", "رفتید (ماضی مخاطب)"),
                    ArabicSample("گام ۲: فعل مضارع", "یَـ (غایب)", "ـذْهَبـ", "ـونَ (جمع مردان)", "یَذْهَبونَ", "می‌روند (مضارع غایب)"),
                    ArabicSample("گام ۳: مضارع متکلم", "نَـ (ما)", "ـکْتُبـ", "ـُ (ضمه)", "نَکْتُبُ", "می‌نویسیم (متکلم مع‌الغیر)"),
                    ArabicSample("گام ۴: فعل امر و نهی", "اِ (الف امر)", "ـعْلَمْـ", "ـوا (شما مردان)", "اِعْلَموا", "بدانید! (امر مخاطب)")
                )

                val current = samples[activeStep.coerceIn(0, 3)]

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // 1. Right Box: Prefix
                            Surface(
                                color = Color(0xFF2563EB).copy(alpha = 0.14f),
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.2.dp, Color(0xFF2563EB)),
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable { onSelectStep((activeStep + 1) % 4) }
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "۱. سرِ فعل",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color(0xFF2563EB),
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(3.dp))
                                    Text(
                                        text = current.prefix,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color(0xFF1D4ED8)
                                    )
                                }
                            }

                            // 2. Center Box: Root
                            Surface(
                                color = accentColor.copy(alpha = 0.16f),
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.5.dp, accentColor),
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable { onSelectStep((activeStep + 1) % 4) }
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "۲. ریشه ۳ حرفی",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = accentColor,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(3.dp))
                                    Text(
                                        text = current.root,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = accentColor
                                    )
                                }
                            }

                            // 3. Left Box: Suffix
                            Surface(
                                color = Color(0xFFD97706).copy(alpha = 0.14f),
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.2.dp, Color(0xFFD97706)),
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable { onSelectStep((activeStep + 1) % 4) }
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "۳. دمِ فعل (ضمیر)",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color(0xFFD97706),
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(3.dp))
                                    Text(
                                        text = current.suffix,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color(0xFFB45309)
                                    )
                                }
                            }
                        }
                    }

                    // Result
                    Surface(
                        color = accentColor.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = current.stepTitle,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = accentColor
                                )
                                Text(
                                    text = "معنی: ${current.meaning}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            Surface(
                                color = accentColor,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = current.fullWord,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private data class Tuple6<A, B, C, D, E, F>(
    val t1: A,
    val t2: B,
    val t3: C,
    val t4: D,
    val t5: E,
    val t6: F
)

/**
 * 5. ENGLISH INTERACTIVE VISUAL ANIMATOR
 * Comprehensive shape-based visual diagrams for English Grammar:
 * - Interactive Grammar Timeline (محور زمان تعاملی با خط زمان، گذشته، حال، آینده)
 * - 4-Wagon Sentence Train Engine (لوکوموتیو و واگن‌های فاعل، فعل کمکی، فعل اصلی، مفعول)
 * - 1-Second Exam Clue Locks (کلیدهای شکار تست)
 */
@Composable
private fun InteractiveEnglishVisualAnimator(
    accentColor: Color,
    activeStep: Int,
    pulseScale: Float,
    onSelectStep: (Int) -> Unit = {}
) {
    var subVisualMode by remember { mutableIntStateOf(0) }
    var selectedTenseIndex by remember { mutableIntStateOf(activeStep.coerceIn(0, 5)) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Mode Selector Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            val modes = listOf(
                "⏳ محور زمان گرامر (Timeline)",
                "🚂 ترن جمله و مجهول (Sentence Train)",
                "🎯 کلیدهای شکار گزینه در ۱ ثانیه"
            )
            modes.forEachIndexed { idx, label ->
                val isSelected = subVisualMode == idx
                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { subVisualMode = idx }
                        .testTag("english_visual_mode_$idx"),
                    shape = RoundedCornerShape(10.dp),
                    color = if (isSelected) accentColor else accentColor.copy(alpha = 0.08f),
                    border = BorderStroke(0.8.dp, if (isSelected) accentColor else accentColor.copy(alpha = 0.25f))
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) Color.White else accentColor,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        }

        when (subVisualMode) {
            0 -> {
                // MODE 0: Interactive Grammar Timeline Axis
                val tenses = listOf(
                    GrammarTenseItem(
                        name = "Past Simple (گذشته ساده)",
                        formula = "Subject + V2 (ed / irregular)",
                        example = "She studied yesterday.",
                        meaning = "کاری در گذشته در یک نقطه مشخص رخ داد و تمام شد.",
                        clues = "yesterday, ago, last week, in 1999",
                        timelinePositionFraction = 0.15f
                    ),
                    GrammarTenseItem(
                        name = "Past Continuous (گذشته استمراری)",
                        formula = "Subject + was/were + V-ing",
                        example = "While I was studying, the phone rang.",
                        meaning = "کاری در گذشته برای مدتی در جریان بود که اتفاق دیگری رخ داد.",
                        clues = "while, as, when, at 5 PM yesterday",
                        timelinePositionFraction = 0.30f
                    ),
                    GrammarTenseItem(
                        name = "Present Simple (حال ساده)",
                        formula = "Subject + V1 (he/she/it + s/es)",
                        example = "He plays football every Friday.",
                        meaning = "عادت روزمره، حقیقت علمی، یا جدول زمانی همیشگی.",
                        clues = "always, usually, often, every day",
                        timelinePositionFraction = 0.50f
                    ),
                    GrammarTenseItem(
                        name = "Present Continuous (حال استمراری)",
                        formula = "Subject + am/is/are + V-ing",
                        example = "Look! They are studying now.",
                        meaning = "کاری دقیقاً همین حالا در لحظه صحبت در حال انجام است.",
                        clues = "now, right now, look!, listen!, at present",
                        timelinePositionFraction = 0.55f
                    ),
                    GrammarTenseItem(
                        name = "Present Perfect (حال کامل / ماضی نقلی)",
                        formula = "Subject + have/has + V3 (p.p)",
                        example = "I have lived here since 2020.",
                        meaning = "کاری از گذشته شروع شده و اثر یا ادامه آن به زمان حال رسیده است.",
                        clues = "since, for, already, yet, ever, just",
                        timelinePositionFraction = 0.40f
                    ),
                    GrammarTenseItem(
                        name = "Future (آینده با will و going to)",
                        formula = "Subject + will + V1 (یا be going to)",
                        example = "I will help you tomorrow.",
                        meaning = "پیش‌بینی، تصمیم لحظه‌ای (will) یا برنامه از قبل تعیین‌شده (going to).",
                        clues = "tomorrow, next year, soon, in future",
                        timelinePositionFraction = 0.85f
                    )
                )

                val curTense = tenses[selectedTenseIndex.coerceIn(0, tenses.lastIndex)]

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    // Tense Selector
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        tenses.forEachIndexed { tIdx, item ->
                            val isTSelected = selectedTenseIndex == tIdx
                            Surface(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { selectedTenseIndex = tIdx },
                                shape = RoundedCornerShape(8.dp),
                                color = if (isTSelected) accentColor else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                border = BorderStroke(0.6.dp, if (isTSelected) accentColor else MaterialTheme.colorScheme.outlineVariant)
                            ) {
                                Text(
                                    text = item.name.substringBefore(" ("),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isTSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.padding(horizontal = 9.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }

                    // Visual Canvas Timeline
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surface,
                        border = BorderStroke(1.dp, accentColor.copy(alpha = 0.35f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = "⏳ موقعیت زمانی: ${curTense.name}",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Black,
                                color = accentColor
                            )

                            // Canvas Timeline Drawing
                            Canvas(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(44.dp)
                            ) {
                                val w = size.width
                                val y = size.height / 2f

                                // Main Horizontal Axis Line
                                drawLine(
                                    color = Color.LightGray.copy(alpha = 0.8f),
                                    start = Offset(16f, y),
                                    end = Offset(w - 16f, y),
                                    strokeWidth = 4f,
                                    cap = StrokeCap.Round
                                )

                                // Past marker
                                drawCircle(
                                    color = Color(0xFFD97706),
                                    radius = 6f,
                                    center = Offset(w * 0.18f, y)
                                )

                                // Present (NOW) central landmark
                                drawCircle(
                                    color = Color(0xFFE11D48),
                                    radius = 8f,
                                    center = Offset(w * 0.52f, y)
                                )

                                // Future marker
                                drawCircle(
                                    color = Color(0xFF059669),
                                    radius = 6f,
                                    center = Offset(w * 0.84f, y)
                                )

                                // Highlighted active node with pulse circle
                                val activeX = w * curTense.timelinePositionFraction
                                drawCircle(
                                    color = accentColor.copy(alpha = 0.35f),
                                    radius = 16f,
                                    center = Offset(activeX, y)
                                )
                                drawCircle(
                                    color = accentColor,
                                    radius = 8f,
                                    center = Offset(activeX, y)
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "⏪ Past (گذشته)",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFD97706)
                                )
                                Text(
                                    text = "🔴 Now (اکنون)",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFFE11D48)
                                )
                                Text(
                                    text = "Future (آینده) ⏩",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF059669)
                                )
                            }

                            HorizontalDivider(thickness = 0.6.dp, color = accentColor.copy(alpha = 0.25f))

                            // Formula Badge & Examples
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = accentColor.copy(alpha = 0.10f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        text = "⚡ فرمول: ${curTense.formula}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Black,
                                        color = accentColor
                                    )
                                    Text(
                                        text = "📖 مثال: ${curTense.example}",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "🎯 مفهوم: ${curTense.meaning}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = "🔑 کلمات کلیدی شکار تست: ${curTense.clues}",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF16A34A)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            1 -> {
                // MODE 1: 4-Wagon Sentence Train Engine (فاعل + کمکی + فعل اصلی + مفعول)
                val sentenceSamples = listOf(
                    TrainSentenceModel(
                        title = "جمله خبری حال کامل (Present Perfect)",
                        w1 = "Sara",
                        w1Role = "فاعل (Subject)",
                        w2 = "has",
                        w2Role = "کمکی (have/has)",
                        w3 = "written",
                        w3Role = "فعل P.P (قالب ۳)",
                        w4 = "a letter",
                        w4Role = "مفعول (Object)",
                        translation = "سارا یک نامه نوشته است."
                    ),
                    TrainSentenceModel(
                        title = "جمله مجهول زمان گذشته (Passive Voice)",
                        w1 = "The car",
                        w1Role = "مفعولِ ابتدای جمله",
                        w2 = "was",
                        w2Role = "فعل To Be (was/were)",
                        w3 = "repaired",
                        w3Role = "شکل سوم فعل (P.P)",
                        w4 = "yesterday",
                        w4Role = "زمان / قید",
                        translation = "ماشین دیروز تعمیر شد (مجهول)."
                    ),
                    TrainSentenceModel(
                        title = "جمله شرطی نوع ۲ (Unreal Condition)",
                        w1 = "If he",
                        w1Role = "شرط (If + فاعل)",
                        w2 = "studied",
                        w2Role = "گذشته ساده (V2)",
                        w3 = "would pass",
                        w3Role = "would + ساده",
                        w4 = "the exam",
                        w4Role = "مفعول و متمم",
                        translation = "اگر درس می‌خواند، در امتحان قبول می‌شد."
                    )
                )

                val trainIndex = activeStep.coerceIn(0, sentenceSamples.lastIndex)
                val curSentence = sentenceSamples[trainIndex]

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "🚂 ${curSentence.title}",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Black,
                        color = accentColor
                    )

                    // 4 Wagons in LTR
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val wagons = listOf(
                                Triple(curSentence.w1, curSentence.w1Role, Color(0xFF2563EB)),
                                Triple(curSentence.w2, curSentence.w2Role, Color(0xFFD97706)),
                                Triple(curSentence.w3, curSentence.w3Role, accentColor),
                                Triple(curSentence.w4, curSentence.w4Role, Color(0xFF059669))
                            )
                            wagons.forEachIndexed { wIdx, (wWord, wRole, wCol) ->
                                Surface(
                                    color = wCol.copy(alpha = 0.12f),
                                    shape = RoundedCornerShape(10.dp),
                                    border = BorderStroke(1.2.dp, wCol),
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(10.dp))
                                        .clickable { onSelectStep((activeStep + 1) % 3) }
                                ) {
                                    Column(
                                        modifier = Modifier.padding(vertical = 7.dp, horizontal = 3.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = wWord,
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = wCol,
                                            textAlign = TextAlign.Center,
                                            maxLines = 1
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = wRole,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            textAlign = TextAlign.Center,
                                            maxLines = 2
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Surface(
                        color = accentColor.copy(alpha = 0.08f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "💡 ترجمه روان: ${curSentence.translation}",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            2 -> {
                // MODE 2: 1-Second Exam Clue Locks
                val clueCards = listOf(
                    Triple("since / for در جمله دیدی؟", "فقط have / has + P.P بزن!", "I have studied since morning."),
                    Triple("جمله با شیء شروع شده یا by دارد؟", "فرمول مجهول: be + P.P بزن!", "The book was written by him."),
                    Triple("بعد از If گذشته ساده دیدی؟", "در جواب حتماً would + ساده بزن!", "If I knew, I would tell you."),
                    Triple("قبل از جای خالی انسان و بعدش فعل است؟", "بدون ترجمه who بزن!", "The teacher who teaches us."),
                    Triple("قبل از جای خالی شیء / حیوان است؟", "بدون ترجمه which (یا that) بزن!", "The car which was sold.")
                )

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    clueCards.forEach { (trigger, action, ex) ->
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.surface,
                            border = BorderStroke(0.7.dp, accentColor.copy(alpha = 0.35f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "👀 $trigger",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Black,
                                        color = accentColor
                                    )
                                    Text(
                                        text = "⚡ در ۱ ثانیه",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF16A34A)
                                    )
                                }
                                Text(
                                    text = "✅ پاسخ قطعی: $action",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "📖 $ex",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private data class GrammarTenseItem(
    val name: String,
    val formula: String,
    val example: String,
    val meaning: String,
    val clues: String,
    val timelinePositionFraction: Float
)

private data class TrainSentenceModel(
    val title: String,
    val w1: String,
    val w1Role: String,
    val w2: String,
    val w2Role: String,
    val w3: String,
    val w3Role: String,
    val w4: String,
    val w4Role: String,
    val translation: String
)

/**
 * 6. PHILOSOPHY & LOGIC INTERACTIVE VISUAL ANIMATOR
 * Interactive Square of Opposition (مربع تقابل منطق) with crisp Compose corner cards and diagonal lines,
 * OR Interactive Gradation of Being (مراتب تشکیک نور وجود ملاصدرا).
 */
@Composable
private fun InteractivePhilosophyVisualAnimator(
    lessonId: String,
    subjectId: String = "",
    accentColor: Color,
    activeStep: Int,
    pulseScale: Float,
    onSelectStep: (Int) -> Unit = {}
) {
    val isOntologicalLesson = subjectId == "philosophy" ||
        lessonId.contains("phil_05") || lessonId.contains("phil_06") ||
        lessonId.contains("philo_05") || lessonId.contains("philo_06")

    if (isOntologicalLesson) {
        val levels = listOf(
            Triple("☀️ سرچشمه نور: واجب‌الوجود (خداوند)", "بی‌نهایت، غنی بالذات و علتِ هستی‌بخش همه مراتب", Color(0xFFD97706)),
            Triple("۱. مرتبه شدید: عالم عقل (فرشتگان)", "موجودات مجردِ کامل؛ نزدیک‌ترین مرتبه به سرچشمه نور", Color(0xFF0891B2)),
            Triple("۲. مرتبه متوسط: عالم مثال (برزخ)", "دارای شکل و اندازه اما بدون ماده فیزیکی و زمان", Color(0xFF7C3AED)),
            Triple("۳. مرتبه ضعیف: عالم ماده (طبیعت)", "ضعیف‌ترین پرتو نور وجود همراه با حرکت، زمان و تغییر", Color(0xFF059669))
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            levels.forEachIndexed { idx, (title, desc, col) ->
                val isSelected = idx == activeStep
                Surface(
                    color = if (isSelected) col.copy(alpha = 0.18f) else MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(if (isSelected) 2.dp else 1.dp, col),
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(if (isSelected) pulseScale else 1f)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onSelectStep(idx) }
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(text = BidiFormatter.formatMixedPersianText(title), style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = col)
                        Text(text = BidiFormatter.formatMixedPersianText(desc), style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    } else {
        // Crisp 3-Row Non-Overlapping Square of Opposition (مربع تقابل منطق دهم)
        val stepRules = listOf(
            "گام ۱ (ضربدر وسط = تناقض): م.ک (هر+) با س.ج (بعضی−) و س.ک (هیچ−) با م.ج (بعضی+) دشمن خونی‌اند! یکی صادق باشد دیگری قطعاً کاذب است.",
            "گام ۲ (ضلع بالای سقف = تضاد): بین موجبه کلی (هر+) و سالبه کلی (هیچ−). هر دو با هم راست نمی‌گویند، اما اگر یکی کاذب باشد دیگری نامعلوم است!",
            "گام ۳ (ضلع پایین کف = تحت تضاد): بین موجبه جزئی (بعضی+) و سالبه جزئی (بعضی−). هر دو با هم دروغ نمی‌گویند؛ اگر یکی صادق باشد دیگری نامعلوم است.",
            "گام ۴ (ستون‌های چپ و راست = تداخل): اگر کلی (بالا) صادق باشد، جزئی زیرش هم صادق است (آب از سقف به کف می‌چکد!)."
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .border(1.dp, accentColor.copy(alpha = 0.25f), RoundedCornerShape(16.dp))
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Top Row: م.ک  <--- تضاد --->  س.ک
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Surface(
                        color = Color(0xFF2563EB),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onSelectStep(1) }
                    ) {
                        Text(
                            text = "م.ک (هر الف ب است)",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 6.dp)
                        )
                    }

                    Surface(
                        color = if (activeStep == 1) Color(0xFFD97706) else Color(0xFFD97706).copy(alpha = 0.16f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.clickable { onSelectStep(1) }
                    ) {
                        Text(
                            text = "↔ تضاد (سقف)",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (activeStep == 1) Color.White else Color(0xFFD97706),
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Surface(
                        color = Color(0xFF7C3AED),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onSelectStep(1) }
                    ) {
                        Text(
                            text = "س.ک (هیچ الف ب نیست)",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 6.dp)
                        )
                    }
                }

                // Middle Row: [تداخل]  ---  ✖ ضربدر = تناقض  ---  [تداخل]
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(74.dp)
                ) {
                    Canvas(modifier = Modifier.matchParentSize()) {
                        val left = size.width * 0.18f
                        val right = size.width * 0.82f
                        val top = 4f
                        val bottom = size.height - 4f
                        val diagWidth = if (activeStep == 0) 7f else 3.5f
                        drawLine(Color(0xFFE11D48), Offset(left, top), Offset(right, bottom), strokeWidth = diagWidth)
                        drawLine(Color(0xFFE11D48), Offset(right, top), Offset(left, bottom), strokeWidth = diagWidth)
                        val sideWidth = if (activeStep == 3) 7f else 3f
                        drawLine(accentColor, Offset(left, top), Offset(left, bottom), strokeWidth = sideWidth)
                        drawLine(accentColor, Offset(right, top), Offset(right, bottom), strokeWidth = sideWidth)
                    }

                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Surface(
                            color = if (activeStep == 3) accentColor else accentColor.copy(alpha = 0.14f),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.clickable { onSelectStep(3) }
                        ) {
                            Text(
                                text = "⬇ تداخل",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (activeStep == 3) Color.White else accentColor,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        Surface(
                            color = Color(0xFFE11D48),
                            shape = RoundedCornerShape(50),
                            shadowElevation = 2.dp,
                            modifier = Modifier
                                .scale(if (activeStep == 0) pulseScale else 1f)
                                .clickable { onSelectStep(0) }
                        ) {
                            Text(
                                text = "✖ ضربدر = تناقض",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White,
                                fontWeight = FontWeight.ExtraBold,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }

                        Surface(
                            color = if (activeStep == 3) accentColor else accentColor.copy(alpha = 0.14f),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.clickable { onSelectStep(3) }
                        ) {
                            Text(
                                text = "⬇ تداخل",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (activeStep == 3) Color.White else accentColor,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                // Bottom Row: م.ج  <--- تحت تضاد --->  س.ج
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Surface(
                        color = Color(0xFF059669),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onSelectStep(2) }
                    ) {
                        Text(
                            text = "م.ج (بعضی الف ب است)",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 6.dp)
                        )
                    }

                    Surface(
                        color = if (activeStep == 2) Color(0xFF059669) else Color(0xFF059669).copy(alpha = 0.16f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.clickable { onSelectStep(2) }
                    ) {
                        Text(
                            text = "↔ تحت تضاد",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (activeStep == 2) Color.White else Color(0xFF059669),
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Surface(
                        color = Color(0xFFD97706),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onSelectStep(2) }
                    ) {
                        Text(
                            text = "س.ج (بعضی الف ب نیست)",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            Surface(
                color = accentColor.copy(alpha = 0.12f),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                BiDiText(
                    text = stepRules[activeStep],
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = accentColor,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}

/**
 * 7. FARHANGIAN UNIVERSITY SCORE SHARE ANIMATOR
 */
@Composable
private fun InteractiveFarhangianVisualAnimator(
    accentColor: Color,
    activeStep: Int,
    onSelectStep: (Int) -> Unit = {}
) {
    val items = listOf(
        // قاعده ۱۴۰۶: نمره کل = ۶۰٪ سوابق تحصیلی (امتحانات نهایی) + ۴۰٪ کنکور سراسری.
        // مصاحبه/گزینش فرهنگیان سهمی در تراز ندارد و فقط شرط قبولی است.
        Triple("گام ۱: سوابق تحصیلی — امتحانات نهایی (۶۰٪ نمره کل)", 0.60f, Color(0xFF059669)),
        Triple("گام ۲: کنکور سراسری علوم انسانی (۴۰٪ نمره کل)", 0.40f, Color(0xFF2563EB)),
        Triple("گام ۳: آمادگی فرهنگیان (دینی، مهارت معلمی) برای مصاحبه", 0f, accentColor),
        Triple("گام ۴: مصاحبه تخصصی و گزینش (شرط قبولی، بدون سهم در تراز)", 0f, Color(0xFF7C3AED))
    )

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items.forEachIndexed { idx, (label, fraction, col) ->
            val highlighted = (activeStep % 4) == idx
            Surface(
                color = if (highlighted) col.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(if (highlighted) 2.dp else 1.dp, col.copy(alpha = 0.45f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onSelectStep(idx) }
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = BidiFormatter.formatMixedPersianText(label),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = col,
                            modifier = Modifier.weight(1f)
                        )
                        Surface(color = col, shape = RoundedCornerShape(8.dp)) {
                            Text(
                                text = BidiFormatter.formatMixedPersianText(if (fraction > 0f) "${(fraction * 100).toInt()}٪ سهم" else "شرط قبولی"),
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }
                    LinearProgressIndicator(
                        progress = { fraction },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = col,
                        trackColor = col.copy(alpha = 0.15f)
                    )
                }
            }
        }
    }
}

/**
 * 7B. ECONOMICS, PSYCHOLOGY & SOCIOLOGY INTERACTIVE VISUAL ANIMATOR
 * Includes interactive step-by-step Economics Math problem visualizer (Profit, Supply/Demand, GDP & Tax),
 * Piaget/Memory Psychology pipeline, and Sociology 3-Paradigm scanner.
 */
@Composable
private fun InteractiveSocialPsychEconVisualAnimator(
    lessonId: String,
    subjectId: String = "",
    accentColor: Color,
    pulseScale: Float,
    activeStep: Int,
    onSelectStep: (Int) -> Unit = {}
) {
    val stepIndex = activeStep % 4
    val isPsych = subjectId == "psychology" || lessonId.contains("psych")
    val isSoc = subjectId == "sociology" || lessonId.contains("soc")

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        // 4 Interactive Step Selector Chips
        val stepLabels = when {
            isPsych -> listOf("۱. رشد پیاژه", "۲. مخازن حافظه", "۳. تداخل فراموشی", "۴. استرس و حل مسئله")
            isSoc -> listOf("۱. کنش اجتماعی", "۲. لایه‌های فرهنگ", "۳. تبیینی و تفسیری", "۴. انتقادی و اسلامی")
            else -> listOf("۱. سود حسابداری/اقتصادی", "۲. عرضه، تقاضا و تعادل", "۳. تولید ملی و حذف واسطه", "۴. نرخ بیکاری و مالیات")
        }

        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            stepLabels.chunked(2).forEachIndexed { rIdx, pair ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    pair.forEachIndexed { cIdx, label ->
                        val idx = rIdx * 2 + cIdx
                        val selected = stepIndex == idx
                        Surface(
                            color = if (selected) accentColor else accentColor.copy(alpha = 0.10f),
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(if (selected) 1.5.dp else 1.dp, accentColor.copy(alpha = 0.4f)),
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { onSelectStep(idx) }
                        ) {
                            Text(
                                text = BidiFormatter.formatMixedPersianText(label),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.ExtraBold,
                                color = if (selected) Color.White else accentColor,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 6.dp)
                            )
                        }
                    }
                }
            }
        }

        // 1. Top Informational Badge
        val topRightBadge = when {
            isPsych -> "🧠 مدل تصویری روان‌شناسی یازدهم"
            isSoc -> "🏛️ مدل تصویری جامعه‌شناسی ۱ تا ۳"
            stepIndex == 0 -> "💰 ستون راست: درآمد (۱۰۰) − آشکار (۶۰) − پنهان (۲۵)"
            stepIndex == 1 -> "📈 تقاطع خط آبی (تقاضا) و سبز (عرضه) = تعادل"
            stepIndex == 2 -> "🏭 حذف کالای واسطه‌ای = فقط ارزش کالای نهایی!"
            else -> "📊 نرخ بیکاری = (بیکاران ÷ جمعیت فعال) × ۱۰۰"
        }
        Surface(
            color = accentColor.copy(alpha = 0.14f),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = BidiFormatter.formatMixedPersianText(topRightBadge),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.ExtraBold,
                color = accentColor,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }

        // 2. Clear, Fully Labeled Graphical Diagram Box
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(MaterialTheme.colorScheme.surface)
                .border(1.dp, accentColor.copy(alpha = 0.28f), RoundedCornerShape(14.dp))
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height
                    for (i in 1..3) {
                        drawLine(
                            color = accentColor.copy(alpha = 0.08f),
                            start = Offset(0f, h * i / 4f),
                            end = Offset(w, h * i / 4f),
                            strokeWidth = 1f
                        )
                    }

                    if (!isPsych && !isSoc) {
                        when (stepIndex) {
                            0 -> {
                                val barW = w * 0.16f
                                val baseY = h * 0.88f
                                val bars = listOf(
                                    Triple(w * 0.78f, h * 0.68f, Color(0xFF2563EB)),
                                    Triple(w * 0.54f, h * 0.42f, Color(0xFFEA580C)),
                                    Triple(w * 0.30f, h * 0.24f, Color(0xFFD97706)),
                                    Triple(w * 0.08f, h * 0.18f * pulseScale.coerceIn(0.95f, 1.08f), Color(0xFF059669))
                                )
                                bars.forEach { (x, barH, col) ->
                                    drawRoundRect(
                                        color = col.copy(alpha = 0.85f),
                                        topLeft = Offset(x, baseY - barH),
                                        size = Size(barW, barH),
                                        cornerRadius = CornerRadius(12f, 12f)
                                    )
                                }
                                drawLine(
                                    color = Color.Gray.copy(alpha = 0.5f),
                                    start = Offset(w * 0.04f, baseY),
                                    end = Offset(w * 0.96f, baseY),
                                    strokeWidth = 3f
                                )
                            }
                            1 -> {
                                val startX = w * 0.15f
                                val endX = w * 0.85f
                                val topY = h * 0.14f
                                val botY = h * 0.86f
                                drawLine(
                                    color = Color(0xFF2563EB),
                                    start = Offset(startX, topY),
                                    end = Offset(endX, botY),
                                    strokeWidth = 6f,
                                    cap = StrokeCap.Round
                                )
                                drawLine(
                                    color = Color(0xFF059669),
                                    start = Offset(startX, botY),
                                    end = Offset(endX, topY),
                                    strokeWidth = 6f,
                                    cap = StrokeCap.Round
                                )
                                drawCircle(
                                    color = Color(0xFFD97706).copy(alpha = 0.28f),
                                    radius = 22f * pulseScale,
                                    center = Offset(w * 0.5f, h * 0.5f)
                                )
                                drawCircle(
                                    color = Color(0xFFD97706),
                                    radius = 9f,
                                    center = Offset(w * 0.5f, h * 0.5f)
                                )
                            }
                            else -> {
                                val boxW = w * 0.25f
                                val boxH = h * 0.56f
                                val topY = h * 0.22f
                                val cols = listOf(Color(0xFF2563EB), Color(0xFF7C3AED), Color(0xFF059669))
                                val xs = listOf(w * 0.68f, w * 0.37f, w * 0.06f)
                                xs.forEachIndexed { i, x ->
                                    drawRoundRect(
                                        color = cols[i].copy(alpha = if (i == 2) 0.28f else 0.16f),
                                        topLeft = Offset(x, topY),
                                        size = Size(boxW, boxH),
                                        cornerRadius = CornerRadius(16f, 16f)
                                    )
                                    drawRoundRect(
                                        color = cols[i],
                                        topLeft = Offset(x, topY),
                                        size = Size(boxW, boxH),
                                        cornerRadius = CornerRadius(16f, 16f),
                                        style = Stroke(width = 3f)
                                    )
                                }
                            }
                        }
                    } else {
                        val nodeW = w * 0.20f
                        val nodeH = h * 0.56f
                        val topY = h * 0.22f
                        val cols = listOf(Color(0xFF2563EB), Color(0xFF7C3AED), Color(0xFFD97706), Color(0xFF059669))
                        for (i in 0..3) {
                            val rtlIdx = 3 - i
                            val x = w * (0.04f + i * 0.24f)
                            val isHighlighted = rtlIdx == stepIndex
                            drawRoundRect(
                                color = cols[rtlIdx].copy(alpha = if (isHighlighted) 0.30f else 0.12f),
                                topLeft = Offset(x, topY),
                                size = Size(nodeW, nodeH),
                                cornerRadius = CornerRadius(14f, 14f)
                            )
                            drawRoundRect(
                                color = cols[rtlIdx],
                                topLeft = Offset(x, topY),
                                size = Size(nodeW, nodeH),
                                cornerRadius = CornerRadius(14f, 14f),
                                style = Stroke(width = if (isHighlighted) 4f else 2f)
                            )
                        }
                    }
                }
            }

            // Explicit Legend & Shape Labels directly underneath the shapes so nothing is unlabeled
            val shapeLabels: List<Pair<String, Color>> = when {
                !isPsych && !isSoc && stepIndex == 0 -> listOf(
                    "درآمد کل (۱۰۰)" to Color(0xFF2563EB),
                    "هزینه آشکار (۶۰)" to Color(0xFFEA580C),
                    "هزینه پنهان (۲۵)" to Color(0xFFD97706),
                    "سود اقتصادی (۱۵)" to Color(0xFF059669)
                )
                !isPsych && !isSoc && stepIndex == 1 -> listOf(
                    "منحنی تقاضا (نزولی)" to Color(0xFF2563EB),
                    "نقطه تعادل بازار (Qs=Qd)" to Color(0xFFD97706),
                    "منحنی عرضه (صعودی)" to Color(0xFF059669)
                )
                !isPsych && !isSoc -> listOf(
                    "۱. فروش کل کالا" to Color(0xFF2563EB),
                    "۲. کسر کالاهای واسطه‌ای" to Color(0xFF7C3AED),
                    "۳. ارزش افزوده خالص" to Color(0xFF059669)
                )
                isPsych -> listOf(
                    "مرحله ۱ (آغازین)" to Color(0xFF2563EB),
                    "مرحله ۲ (میانی)" to Color(0xFF7C3AED),
                    "مرحله ۳ (پیشرفته)" to Color(0xFFD97706),
                    "مرحله ۴ (تثبیت)" to Color(0xFF059669)
                )
                else -> listOf(
                    "۱. کنش اجتماعی" to Color(0xFF2563EB),
                    "۲. پدیده و هنجار" to Color(0xFF7C3AED),
                    "۳. رویکرد تبیینی/تفسیری" to Color(0xFFD97706),
                    "۴. رویکرد انتقادی" to Color(0xFF059669)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                shapeLabels.forEach { (lbl, col) ->
                    Surface(
                        color = col.copy(alpha = 0.14f),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, col.copy(alpha = 0.45f))
                    ) {
                        Text(
                            text = BidiFormatter.formatMixedPersianText(lbl),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = col,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }

        // 3. Full-Width Golden Formula / Breakdown Card
        val bottomSummary = when {
            isPsych && stepIndex == 0 -> "پیاژه: ۱. حسی-حرکتی (پایداری شیء) ⬅ ۲. پیش‌عملیاتی (خودمحوری) ⬅ ۳. عینی (نگهداری ذهنی) ⬅ ۴. صوری"
            isPsych && stepIndex == 1 -> "حافظه حسی (ثبت محرک) ⬅ توجه ⬅ کوتاه‌مدت (۷±۲ قطعه) ⬅ مرور معنایی ⬅ بلندمدت"
            isPsych && stepIndex == 2 -> "رمز تداخل فراموشی: اگر مطلب «جدید» یادت رفت = پیش‌گستر! اگر «قدیم» یادت رفت = پس‌گستر!"
            isPsych -> "موانع حل مسئله: گیر کردن روی فرمول قبلی = آمایه ذهنی | ندیدن کاربرد جدید شیء = تثبیت کارکردی"
            isSoc && stepIndex == 0 -> "کنش = آگاهانه + ارادی + هدفدار + معنادار | کنش اجتماعی = با توجه به دیگران!"
            isSoc && stepIndex == 1 -> "لایه‌های فرهنگ: نمادها (سطحی‌ترین و محسوس‌ترین) ⬅ هنجارها ⬅ ارزش‌ها ⬅ عقاید (عمیق‌ترین)"
            isSoc && stepIndex == 2 -> "رویکرد تبیینی (کنت/دورکیم): آمار و کشف قانون علّی | رویکرد تفسیری (وبر): فهم معنای کنشگر"
            isSoc -> "رویکرد انتقادی: نفی سلطه و رهایی‌بخشی | رویکرد اسلامی: توجه توأمان به عقل، حس و وحی"
            stepIndex == 0 -> "فرمول بی‌نیاز از حفظ: سود حسابداری = ۱۰۰ − ۶۰ = ۴۰  |  سود اقتصادی = ۴۰ − ۲۵ = +۱۵ میلیون!"
            stepIndex == 1 -> "بالای نقطه تعادل = مازاد عرضه (کالا روی دست فروشنده) | پایین تعادل = مازاد تقاضا (صف و کمبود)"
            stepIndex == 2 -> "شگرد GDP در ۵ ثانیه: یا فقط قیمت «آخرین کالا (نان)» را بردار، یا «فروش هر مرحله منهای خرید قبلی»!"
            else -> "تله کنکور: دانش‌آموز، خانه‌دار و بازنشسته جزء «جمعیت غیرفعال» هستند و اصلاً در کسر بیکاری نمی‌آیند!"
        }

        Surface(
            color = accentColor.copy(alpha = 0.12f),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, accentColor.copy(alpha = 0.35f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            BiDiText(
                text = bottomSummary,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

/**
 * 7C. HISTORY & GEOGRAPHY INTERACTIVE VISUAL ANIMATOR
 * Visualizes Century conversion (+1 rule), Time-Zone / Longitude calculation (15 deg = 1 hr),
 * Iran's borders/climates, and Maritime law zones (12 & 200 nautical miles).
 */
@Composable
private fun InteractiveHistoryGeoVisualAnimator(
    lessonId: String,
    accentColor: Color,
    pulseScale: Float,
    activeStep: Int,
    onSelectStep: (Int) -> Unit = {}
) {
    val stepIndex = activeStep % 4
    val stepLabels = listOf(
        "۱. فرمول قرن (+۱)",
        "۲. ساعت و نصف‌النهار",
        "۳. سلسله‌های تاریخ ایران",
        "۴. مرز دریایی و ژئوپلیتیک"
    )

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        stepLabels.chunked(2).forEachIndexed { rIdx, pair ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                pair.forEachIndexed { cIdx, label ->
                    val idx = rIdx * 2 + cIdx
                    val selected = stepIndex == idx
                    Surface(
                        color = if (selected) accentColor else accentColor.copy(alpha = 0.10f),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(if (selected) 1.5.dp else 1.dp, accentColor.copy(alpha = 0.4f)),
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { onSelectStep(idx) }
                    ) {
                        Text(
                            text = BidiFormatter.formatMixedPersianText(label),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = if (selected) Color.White else accentColor,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 6.dp)
                        )
                    }
                }
            }
        }

        Surface(
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.dp, accentColor.copy(alpha = 0.30f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                when (stepIndex) {
                    0 -> {
                        Text(
                            text = BidiFormatter.formatMixedPersianText("⏳ ماشین تصویری تبدیل سال به قرن (در ۲ ثانیه):"),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = accentColor
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = Color(0xFF2563EB).copy(alpha = 0.14f),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = BidiFormatter.formatMixedPersianText("سال ۱۳۵۷\nحذف دو رقم آخر (۵۷)"),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF2563EB),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                            Text("⬅", fontWeight = FontWeight.ExtraBold, color = accentColor)
                            Surface(
                                color = Color(0xFF059669).copy(alpha = 0.14f),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = BidiFormatter.formatMixedPersianText("می‌ماند: ۱۳\n۱۳ + ۱ = قرن ۱۴"),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF059669),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }

                    1 -> {
                        Text(
                            text = BidiFormatter.formatMixedPersianText("🌍 فرمول اختلاف ساعت دو شهر روی نصف‌النهارها:"),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = accentColor
                        )
                        BiDiText(
                            text = "• هر ۱۵ درجه اختلاف طول جغرافیایی = ۱ ساعت اختلاف زمان!\n• شهر شرقی (راست نقشه) ساعتش جلوتر (+) و شهر غربی عقب‌تر (−) است.\n• مثال: تهران (۴۵ درجه شرقی) با گرینویچ (۰ درجه): ۴۵ ÷ ۱۵ = ۳ ساعت جلوتر!",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    2 -> {
                        Text(
                            text = BidiFormatter.formatMixedPersianText("👑 خط زمانی طلایی سلسله‌های ایران (تاریخ دهم تا دوازدهم):"),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = accentColor
                        )
                        BiDiText(
                            text = "• باستان (دهم): ماد ⬅ هخامنشی (ساتراپ/چاپار) ⬅ اشکانی (مهستان) ⬅ ساسانی\n• اسلامی (یازدهم): طاهریان ⬅ صفاریان ⬅ سامانیان ⬅ غزنویان ⬅ سلجوقیان ⬅ خوارزمشاهیان ⬅ ایلخانان ⬅ صفویه\n• معاصر (دوازدهم): افشاریه ⬅ زندیه ⬅ قاجار (مشروطه) ⬅ پهلوی ⬅ انقلاب اسلامی",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    else -> {
                        Text(
                            text = BidiFormatter.formatMixedPersianText("⚓ مناطق حقوقی دریاها و مرزهای ایران (جغرافیا ۱۰ تا ۱۲):"),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = accentColor
                        )
                        BiDiText(
                            text = "• از ساحل به اقیانوس: ۱. آب‌های داخلی ⬅ ۲. دریای سرزمینی (تا ۱۲ مایل دریایی = مرز سیاسی) ⬅ ۳. منطقه انحصاری اقتصادی و فلات قاره (تا ۲۰۰ مایل = استخراج نفت و صید) ⬅ ۴. آب‌های آزاد.\n• مرز خشکی ایران: طولانی‌ترین = عراق | کوتاه‌ترین = ارمنستان | تنها بندر اقیانوسی = چابهار!",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

/**
 * 7D. DINI (RELIGIOUS STUDIES) INTERACTIVE VISUAL ANIMATOR
 * Visualizes Tawheed branches, Afterlife progression & Embodiment of Deeds, Divine Guidance, and Verse Matching.
 */
@Composable
private fun InteractiveDiniVisualAnimator(
    accentColor: Color,
    activeStep: Int,
    pulseScale: Float,
    onSelectStep: (Int) -> Unit = {}
) {
    val stepIndex = activeStep % 4
    val stepLabels = listOf(
        "۱. درجات ۴ گانه توحید",
        "۲. مراتب برزخ و قیامت",
        "۳. هدایت تکوینی/تشریعی",
        "۴. تطبیق آیه و پیام"
    )

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        stepLabels.chunked(2).forEachIndexed { rIdx, pair ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                pair.forEachIndexed { cIdx, label ->
                    val idx = rIdx * 2 + cIdx
                    val selected = stepIndex == idx
                    Surface(
                        color = if (selected) accentColor else accentColor.copy(alpha = 0.10f),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(if (selected) 1.5.dp else 1.dp, accentColor.copy(alpha = 0.4f)),
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { onSelectStep(idx) }
                    ) {
                        Text(
                            text = BidiFormatter.formatMixedPersianText(label),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = if (selected) Color.White else accentColor,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 6.dp)
                        )
                    }
                }
            }
        }

        Surface(
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.dp, accentColor.copy(alpha = 0.30f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                when (stepIndex) {
                    0 -> {
                        Text(
                            text = BidiFormatter.formatMixedPersianText("🕌 نمودار درختی و درجات توحید (دین و زندگی دوازدهم):"),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = accentColor
                        )
                        val tawheedList = listOf(
                            Triple("توحید ذاتی", "خداوند یکتا و بی‌همتاست (قُلْ هُوَ اللَّهُ أَحَدٌ)", Color(0xFF2563EB)),
                            Triple("توحید صفاتی", "صفات خدا عین ذات اوست (بی‌نهایت در علم و قدرت)", Color(0xFF059669)),
                            Triple("توحید افعالی", "خالقیت، مالکیت، ربوبیت و ولایت فقط از آن اوست", Color(0xFFD97706)),
                            Triple("توحید عملی (عبادی)", "اخلاص در بندگی، اطاعت و توکل (شرک خفی = جلب توجه)", Color(0xFF7C3AED))
                        )
                        tawheedList.forEach { (t, d, col) ->
                            Surface(
                                color = col.copy(alpha = 0.10f),
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(0.8.dp, col.copy(alpha = 0.35f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Surface(color = col, shape = RoundedCornerShape(6.dp)) {
                                        Text(
                                            text = BidiFormatter.formatMixedPersianText(t),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                        )
                                    }
                                    BiDiText(
                                        text = d,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }
                    }

                    1 -> {
                        Text(
                            text = BidiFormatter.formatMixedPersianText("🌌 ایستگاه‌های سفر انسان از مرگ تا قیامت (دینی دهم):"),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = accentColor
                        )
                        BiDiText(
                            text = "• ۱. توفّی (دریافت کامل روح توسط فرشتگان) ⬅ ۲. عالم برزخ (حیات آگاهانه پس از مرگ) ⬅ ۳. نفخ صور اول (پایان جهان) ⬅ ۴. نفخ صور دوم (رستاخیز و تجسم اعمال)\n• تجسم اعمال: نمره و جزا از جنس خودِ کار انسان است، نه یک قرارداد بیرونی! (فَمَنْ یَعْمَلْ مِثْقالَ ذَرَّةٍ خَیْرًا یَرَهُ)",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    2 -> {
                        Text(
                            text = BidiFormatter.formatMixedPersianText("🌿 هدایت تکوینی و تشریعی (دین و زندگی یازدهم):"),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = accentColor
                        )
                        BiDiText(
                            text = "• هدایت عمومی (تکوینی): شامل همه موجودات با غرایز و قوانین طبیعی (رَبُّنَا الَّذِی أَعْطَی کُلَّ شَیْءٍ خَلْقَهُ ثُمَّ هَدَی).\n• هدایت ویژه (تشریعی): مخصوص انسان‌ها از طریق ارسال پیامبران، کتاب‌های آسمانی و عقل، همراه با اختیار و مسئولیت انتخاب.",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    else -> {
                        Text(
                            text = BidiFormatter.formatMixedPersianText("📖 کلیدواژه‌های شکار پیام آیات در کنکور و نهایی:"),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = accentColor
                        )
                        BiDiText(
                            text = "• آیه «وَ عَلَی اللَّهِ فَتَوَکَّلُوا» ⟹ پیام: توحید افعالی و اعتماد کامل به ربوبیت الهی.\n• آیه «إِنَّا هَدَیْنَاهُ السَّبِیلَ...» ⟹ پیام: وجود اختیار و آزادی انسان در پذیرش شکر یا کفران.\n• آیه «اللَّهُ یَتَوَفَّی الْأَنْفُسَ» ⟹ پیام: غیرمادی بودن روح و توفّی در هنگام مرگ.",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

/**
 * 7E. FARSI & NEGARESH INTERACTIVE VISUAL ANIMATOR
 * Visualizes Sentence Component Parsing, Noun Groups & Modifiers, Word Structures, and Exam Spelling Locks.
 */
@Composable
private fun InteractiveFarsiVisualAnimator(
    accentColor: Color,
    activeStep: Int,
    pulseScale: Float,
    onSelectStep: (Int) -> Unit = {}
) {
    val stepIndex = activeStep % 4
    val stepLabels = listOf(
        "۱. کالبدشکافی نقش‌های جمله",
        "۲. گروه اسمی و صفت/مضاف‌الیه",
        "۳. ساختار واژه (ساده/مشتق/مرکب)",
        "۴. املای هم‌آوا و ۲۰ نهایی"
    )

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        stepLabels.chunked(2).forEachIndexed { rIdx, pair ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                pair.forEachIndexed { cIdx, label ->
                    val idx = rIdx * 2 + cIdx
                    val selected = stepIndex == idx
                    Surface(
                        color = if (selected) accentColor else accentColor.copy(alpha = 0.10f),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(if (selected) 1.5.dp else 1.dp, accentColor.copy(alpha = 0.4f)),
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { onSelectStep(idx) }
                    ) {
                        Text(
                            text = BidiFormatter.formatMixedPersianText(label),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = if (selected) Color.White else accentColor,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 6.dp)
                        )
                    }
                }
            }
        }

        Surface(
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.dp, accentColor.copy(alpha = 0.30f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                when (stepIndex) {
                    0 -> {
                        Text(
                            text = BidiFormatter.formatMixedPersianText("✍️ انیمیشن کالبدشکافی نقش‌های اصلی جمله:"),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = accentColor
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val sentenceParts = listOf(
                                Triple("دانش‌آموز", "نهاد (چه کسی؟)", Color(0xFF2563EB)),
                                Triple("کتاب را", "مفعول (+ را)", Color(0xFF059669)),
                                Triple("با دقت", "متمم (با/از/در/به)", Color(0xFFD97706)),
                                Triple("خواند", "فعل اصلی", Color(0xFF7C3AED))
                            )
                            sentenceParts.forEach { (word, role, col) ->
                                Surface(
                                    color = col.copy(alpha = 0.12f),
                                    shape = RoundedCornerShape(8.dp),
                                    border = BorderStroke(1.dp, col),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(vertical = 6.dp, horizontal = 3.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = BidiFormatter.formatMixedPersianText(word),
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = col,
                                            textAlign = TextAlign.Center
                                        )
                                        Text(
                                            text = BidiFormatter.formatMixedPersianText(role),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            textAlign = TextAlign.Center,
                                            maxLines = 1
                                        )
                                    }
                                }
                            }
                        }
                    }

                    1 -> {
                        Text(
                            text = BidiFormatter.formatMixedPersianText("🔎 فرمول ۲ ثانیه‌ای صفت بیانی vs مضاف‌الیه:"),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = accentColor
                        )
                        BiDiText(
                            text = "• تکنیک «است»: بین هسته و وابسته کلمه «است» بگذار و معنی کن:\n  ۱. «کتابِ خوب» ⟹ «کتاب خوب است» (معنی داد ⟹ صفت بیانی!)\n  ۲. «کتابِ علی» ⟹ «کتاب علی است» (معنی نداد ⟹ مضاف‌الیه!)\n• تکنیک «تر»: به وابسته «تر» اضافه کن: «کتاب خوب‌تر» (صفت) vs «کتاب علی‌تر» (غلط ⟹ مضاف‌الیه).",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    2 -> {
                        Text(
                            text = BidiFormatter.formatMixedPersianText("🧱 تقسیم‌بندی ۴ گانه ساختار واژه در فارسی:"),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = accentColor
                        )
                        BiDiText(
                            text = "• ۱. ساده (۱ تکواژ): کتاب، درخت، قلم\n• ۲. مشتق (بن/اسم + وند): دانش‌مند، کار‌گر، راهنمایی\n• ۳. مرکب (۲ جزء معنادار): کتاب‌خانه، گلاب، خوش‌حال\n• ۴. مشتق-مرکب (ترکیب وند و دو جزء): دانش‌جو‌یان، خود‌خواهی",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    else -> {
                        Text(
                            text = BidiFormatter.formatMixedPersianText("📝 کلیدهای شکار املای ۲۰ امتحان نهایی:"),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = accentColor
                        )
                        BiDiText(
                            text = "• خوار (خوردن: خواربار، ناخار) vs خار (تیغ کویر / ذلیل)\n• صواب (درست و شایسته) vs ثواب (پاداش اخروی)\n• فترت (فاصله و رکود) vs فطرت (سرشت الهی انسان)\n• حیّاط (خیاط پارچه) vs حیاط (محوطه خانه)",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

/**
 * 7F. GENERAL HUMANITIES INTERACTIVE VISUAL ANIMATOR
 * Covers Health & BMI Calculator, Social Identity, Environment & Virtual Water, and Media Literacy.
 */
@Composable
private fun InteractiveGeneralHumanitiesVisualAnimator(
    accentColor: Color,
    activeStep: Int,
    pulseScale: Float,
    onSelectStep: (Int) -> Unit = {}
) {
    val stepIndex = activeStep % 4
    val stepLabels = listOf(
        "۱. سلامت و فرمول BMI",
        "۲. ارکان هویت اجتماعی",
        "۳. محیط زیست و آب مجازی",
        "۴. سواد رسانه‌ای و اقناع"
    )

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        stepLabels.chunked(2).forEachIndexed { rIdx, pair ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                pair.forEachIndexed { cIdx, label ->
                    val idx = rIdx * 2 + cIdx
                    val selected = stepIndex == idx
                    Surface(
                        color = if (selected) accentColor else accentColor.copy(alpha = 0.10f),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(if (selected) 1.5.dp else 1.dp, accentColor.copy(alpha = 0.4f)),
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { onSelectStep(idx) }
                    ) {
                        Text(
                            text = BidiFormatter.formatMixedPersianText(label),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = if (selected) Color.White else accentColor,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 6.dp)
                        )
                    }
                }
            }
        }

        Surface(
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.dp, accentColor.copy(alpha = 0.30f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                when (stepIndex) {
                    0 -> {
                        Text(
                            text = BidiFormatter.formatMixedPersianText("🩺 فرمول محاسبه BMI (سلامت و بهداشت پایه دوازدهم):"),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = accentColor
                        )
                        BiDiText(
                            text = "• فرمول: BMI = وزن (کیلوگرم) ÷ [قد (متر) × قد (متر)]\n• محدوده استاندارد:\n  - زیر ۱۸/۵ = کمبود وزن\n  - ۱۸/۵ تا ۲۴/۹ = وزن طبیعی و ایده‌آل ✅\n  - ۲۵ تا ۲۹/۹ = اضافه وزن\n  - ۳۰ به بالا = چاقی خطرناک",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    1 -> {
                        Text(
                            text = BidiFormatter.formatMixedPersianText("🏛️ لایه‌ها و ابعاد هویت اجتماعی (هویت پایه دوازدهم):"),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = accentColor
                        )
                        BiDiText(
                            text = "• هویت فردی: خصوصیات جسمانی و روانی فرد (تولد، ویژگی‌های اخلاقی).\n• هویت اجتماعی: موقعیت فرد در گروه‌ها و جامعه (مذهب، شغل، ملیت).\n• ویژگی‌های انتسابی (غیرارادی مانند جنسیت) vs اکتسابی (ارادی مانند تحصیلات).",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    2 -> {
                        Text(
                            text = BidiFormatter.formatMixedPersianText("🌱 مفهوم آب مجازی و ردپای اکولوژیک (محیط زیست یازدهم):"),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = accentColor
                        )
                        BiDiText(
                            text = "• آب مجازی: کل حجم آبی که برای تولید یک محصول (مثلاً ۱ کیلوگرم گوشت یا ۱ جفت کفش) در مراحل کاشت، داشت و فراوری مصرف می‌شود.\n• توسعه پایدار: برآوردن نیازهای امروز بدون آسیب به سهم نسل‌های آینده.",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    else -> {
                        Text(
                            text = BidiFormatter.formatMixedPersianText("📺 ۵ فن اصلی اقناع و تبلیغات (سواد رسانه‌ای دهم):"),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = accentColor
                        )
                        BiDiText(
                            text = "• ۱. شهادت و گواهی: استفاده از چهره‌های مشهور (سلبریتی‌ها).\n• ۲. همنوایی با جمع: «همه دارند می‌خرند، تو هم بخر!»\n• ۳. کلی‌گویی درخشان: استفاده از واژه‌های پرطمطراق بدون دلیل علمی.\n• ۴. ترس و طمع: ایحاد هراس از عواقب نخریدن کالا.\n• ۵. طنز و تداعی: ربط دادن کالا به احساسات شیرین.",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

/**
 * Reusable Responsive Graphical Table for each subject (especially 14 Arabic Sighas, Math Number Sets,
 * Logic Square & Philosophers, English Tenses, Prosody Meters, and Literary Styles).
 */
@Composable
fun SubjectGraphicalMasterTable(
    subjectId: String,
    lessonId: String = "",
    accentColor: Color
) {
    if (subjectId == "arabic") {
        Arabic14SighaGraphicalTable(accentColor = accentColor)
        return
    }

    val (tableTitle, headers, rows) = remember(subjectId, lessonId) {
        buildSubjectTableData(subjectId, accentColor)
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, accentColor.copy(alpha = 0.25f), RoundedCornerShape(18.dp))
            .padding(12.dp)
    ) {
        val isCompact = maxWidth < 680.dp

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text = BidiFormatter.formatMixedPersianText("📋 $tableTitle"),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.ExtraBold,
                color = accentColor
            )

            if (!isCompact) {
                // Wide screen: 4-column table header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(accentColor.copy(alpha = 0.18f))
                        .padding(vertical = 8.dp, horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    headers.forEachIndexed { idx, hText ->
                        Text(
                            text = BidiFormatter.formatMixedPersianText(hText),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = accentColor,
                            modifier = Modifier.weight(if (idx == 0) 0.9f else 1.1f)
                        )
                    }
                }
            }

            // Data Rows: Responsive Visual Cards on Compact screens, 4-Column Row on Wide screens
            rows.forEach { row ->
                if (isCompact) {
                    Surface(
                        color = row.highlightColor.copy(alpha = 0.08f),
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(1.dp, row.highlightColor.copy(alpha = 0.30f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Top Row: Badge + Col 1
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Surface(
                                    color = row.highlightColor,
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = BidiFormatter.formatMixedPersianText(row.badge),
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                    )
                                }
                                BiDiText(
                                    text = row.col1,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            // Bottom 2-Column Row: Col 2 (Example/Sign) + Col 3 (Golden Rule)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Surface(
                                    color = MaterialTheme.colorScheme.surface,
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                        .border(1.dp, row.highlightColor.copy(alpha = 0.2f), RoundedCornerShape(10.dp))
                                ) {
                                    Column(modifier = Modifier.padding(8.dp)) {
                                        Text(
                                            text = BidiFormatter.formatMixedPersianText(headers.getOrElse(2) { "مثال / نشانه" }),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = row.highlightColor,
                                            fontWeight = FontWeight.Bold
                                        )
                                        BiDiText(
                                            text = row.col2,
                                            style = MaterialTheme.typography.bodySmall,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }

                                Surface(
                                    color = row.highlightColor.copy(alpha = 0.14f),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                        .border(1.dp, row.highlightColor.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
                                ) {
                                    Column(modifier = Modifier.padding(8.dp)) {
                                        Text(
                                            text = BidiFormatter.formatMixedPersianText(headers.getOrElse(3) { "نکته طلایی" }),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = row.highlightColor,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                        BiDiText(
                                            text = row.col3,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(row.highlightColor.copy(alpha = 0.10f))
                            .border(1.dp, row.highlightColor.copy(alpha = 0.25f), RoundedCornerShape(12.dp))
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Surface(
                            color = row.highlightColor,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(0.9f)
                        ) {
                            Text(
                                text = BidiFormatter.formatMixedPersianText(row.badge),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 6.dp)
                            )
                        }
                        BiDiText(
                            text = row.col1,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1.1f)
                        )
                        BiDiText(
                            text = row.col2,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.weight(1.1f)
                        )
                        BiDiText(
                            text = row.col3,
                            style = MaterialTheme.typography.labelSmall,
                            color = row.highlightColor,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.weight(1.1f)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Special Interactive Graphical Table for the 14 Arabic Conjugations (صیغه‌های ۱۴گانه عربی)
 * with 3 Color-Coded Rooms (اتاق غایب، اتاق مخاطب، اتاق متکلم) and Root Selector.
 * Responsive layout ensures zero cramped columns on phone screens!
 */
@Composable
fun Arabic14SighaGraphicalTable(accentColor: Color) {
    var selectedRootIdx by remember { mutableIntStateOf(0) }
    val roots = listOf(
        Triple("ذَهَبَ (رفتن)", "ذَهَب", "ذهَب"),
        Triple("کَتَبَ (نوشتن)", "کَتَب", "کتُب"),
        Triple("عَلِمَ (دانستن)", "عَلِم", "علَم")
    )
    val (_, maziStem, mozareStem) = roots[selectedRootIdx]

    data class SighaRow(
        val num: String,
        val roomName: String,
        val pronoun: String,
        val mazi: String,
        val mozare: String,
        val amr: String,
        val keyHint: String,
        val roomColor: Color
    )

    val ghayebColor = Color(0xFF2563EB)     // Blue Room: غایب (او / آن‌ها)
    val mokhatabColor = Color(0xFF059669)   // Emerald Room: مخاطب (تو / شما)
    val motakallemColor = Color(0xFFD97706) // Gold Room: متکلم (من / ما)

    val sighaRows = remember(selectedRootIdx) {
        listOf(
            SighaRow("۱", "غایب مذکر (۱ نفر)", "هُوَ (او مرد)", "${maziStem}َ", "یَـ${mozareStem}ُ", "—", "ماضی بدون پسوند | مضارع: یـ ... ُ", ghayebColor),
            SighaRow("۲", "غایب مذکر (۲ نفر)", "هُما (آن دو مرد)", "${maziStem}ا", "یَـ${mozareStem}انِ", "—", "پسوند ۲ نفر: ا / انِ", ghayebColor),
            SighaRow("۳", "غایب مذکر (جمع)", "هُمْ (آن مردان)", "${maziStem}وا", "یَـ${mozareStem}ونَ", "—", "پسوند جمع مردان: وا / ونَ", ghayebColor),
            SighaRow("۴", "غایب مؤنث (۱ نفر)", "هِیَ (او زن)", "${maziStem}َتْ", "تَـ${mozareStem}ُ", "—", "ماضی: تْ ساکن | مضارع: تـ ... ُ", ghayebColor),
            SighaRow("۵", "غایب مؤنث (۲ نفر)", "هُما (آن دو زن)", "${maziStem}َتا", "تَـ${mozareStem}انِ", "—", "ماضی: تا | مضارع: تـ ... انِ", ghayebColor),
            SighaRow("۶", "غایب مؤنث (جمع)", "هُنَّ (آن زنان)", "${maziStem}ْنَ", "یَـ${mozareStem}ْنَ", "—", "نون نسوه (نَ) در آخر!", ghayebColor),

            SighaRow("۷", "مخاطب مذکر (۱ نفر)", "أَنتَ (تو مرد)", "${maziStem}ْتَ", "تَـ${mozareStem}ُ", "اِ${mozareStem}ْ", "ماضی: تَ | مضارع: تـ | امر بدون پسوند", mokhatabColor),
            SighaRow("۸", "مخاطب مذکر (۲ نفر)", "أَنتُما (شما دو نفر)", "${maziStem}ْتُما", "تَـ${mozareStem}انِ", "اِ${mozareStem}ا", "ماضی: تُما | مضارع: تـ ... انِ", mokhatabColor),
            SighaRow("۹", "مخاطب مذکر (جمع)", "أَنتُمْ (شما مردان)", "${maziStem}ْتُمْ", "تَـ${mozareStem}ونَ", "اِ${mozareStem}وا", "ماضی: تُم | مضارع: تـ ... ونَ | امر: وا", mokhatabColor),
            SighaRow("۱۰", "مخاطب مؤنث (۱ نفر)", "أَنتِ (تو زن)", "${maziStem}ْتِ", "تَـ${mozareStem}ینَ", "اِ${mozareStem}ی", "ماضی: تِ | مضارع: تـ ... ینَ | امر: ی", mokhatabColor),
            SighaRow("۱۱", "مخاطب مؤنث (۲ نفر)", "أَنتُما (شما دو زن)", "${maziStem}ْتُما", "تَـ${mozareStem}انِ", "اِ${mozareStem}ا", "مشترک با مثنای مذکر (تُما / انِ)", mokhatabColor),
            SighaRow("۱۲", "مخاطب مؤنث (جمع)", "أَنتُنَّ (شما زنان)", "${maziStem}ْتُنَّ", "تَـ${mozareStem}ْنَ", "اِ${mozareStem}ْنَ", "ماضی: تُنَّ | مضارع: تـ ... نَ", mokhatabColor),

            SighaRow("۱۳", "متکلم وحده (من)", "أَنا (من)", "${maziStem}ْتُ", "أَ${mozareStem}ُ", "—", "ماضی: تُ (من) | مضارع: أ اول کلمه!", motakallemColor),
            SighaRow("۱۴", "متکلم مع‌الغیر (ما)", "نَحنُ (ما)", "${maziStem}ْنا", "نَـ${mozareStem}ُ", "—", "ماضی: نا (ما) | مضارع: نـ اول کلمه!", motakallemColor)
        )
    }

    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, accentColor.copy(alpha = 0.28f), RoundedCornerShape(18.dp))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = BidiFormatter.formatMixedPersianText("جدول گرافیکی ۳ اتاق ۱۴ صیغه عربی (ماضی + مضارع + امر + رمز سر و دم)"),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = accentColor
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                roots.forEachIndexed { idx, (title, _, _) ->
                    val selected = idx == selectedRootIdx
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { selectedRootIdx = idx },
                        shape = RoundedCornerShape(10.dp),
                        color = if (selected) accentColor else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (selected) Color.White else MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 7.dp, horizontal = 4.dp)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf(
                    "اتاق ۱: غایب" to ghayebColor,
                    "اتاق ۲: مخاطب" to mokhatabColor,
                    "اتاق ۳: متکلم" to motakallemColor
                ).forEach { (txt, col) ->
                    Surface(
                        color = col.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = BidiFormatter.formatMixedPersianText(txt),
                            style = MaterialTheme.typography.labelSmall,
                            color = col,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 5.dp, horizontal = 4.dp)
                        )
                    }
                }
            }

            sighaRows.forEach { r ->
                Surface(
                    color = r.roomColor.copy(alpha = 0.08f),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, r.roomColor.copy(alpha = 0.28f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        // Top Row: Sigha Number + Pronoun + Room Name + Key Hint
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    color = r.roomColor,
                                    shape = CircleShape,
                                    modifier = Modifier.size(22.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = BidiFormatter.applyPreferredDigits(r.num),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "${r.pronoun} — ${BidiFormatter.formatMixedPersianText(r.roomName)}",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = r.roomColor
                                )
                            }
                        }

                        // Middle Row: Mazi | Mozare | Amr in 3 spacious pills
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Surface(
                                color = Color(0xFFD97706).copy(alpha = 0.15f),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text("ماضی", style = MaterialTheme.typography.labelSmall, color = Color(0xFFB45309))
                                    Text(
                                        text = r.mazi,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color(0xFFB45309)
                                    )
                                }
                            }

                            Surface(
                                color = Color(0xFF2563EB).copy(alpha = 0.15f),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text("مضارع", style = MaterialTheme.typography.labelSmall, color = Color(0xFF1D4ED8))
                                    Text(
                                        text = r.mozare,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color(0xFF1D4ED8)
                                    )
                                }
                            }

                            Surface(
                                color = r.roomColor.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text("امر", style = MaterialTheme.typography.labelSmall, color = r.roomColor)
                                    Text(
                                        text = r.amr,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = r.roomColor
                                    )
                                }
                            }
                        }

                        // Footer Hint
                        BiDiText(
                            text = "🔑 رمز تشخیص سریع: ${r.keyHint}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

/**
 * Reusable Graphical Tree Diagram (نمودار درختی) for every subject.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SubjectGraphicalMasterTree(
    subjectId: String,
    lessonId: String = "",
    accentColor: Color
) {
    val (rootTitle, rootSubtitle, branches) = remember(subjectId, lessonId) {
        buildSubjectTreeData(subjectId, accentColor)
    }

    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, accentColor.copy(alpha = 0.28f), RoundedCornerShape(18.dp))
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Surface(
                color = accentColor,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = BidiFormatter.formatMixedPersianText("🌳 $rootTitle"),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = BidiFormatter.formatMixedPersianText(rootSubtitle),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.9f),
                        textAlign = TextAlign.Center
                    )
                }
            }

            branches.forEachIndexed { idx, branch ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(branch.branchColor.copy(alpha = 0.08f))
                        .border(1.dp, branch.branchColor.copy(alpha = 0.30f), RoundedCornerShape(14.dp))
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            color = branch.branchColor,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = BidiFormatter.formatMixedPersianText("شاخه ${idx + 1}: ${branch.branchBadge}"),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                        Text(
                            text = BidiFormatter.formatMixedPersianText(branch.branchTitle),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = branch.branchColor
                        )
                    }

                    HorizontalDivider(color = branch.branchColor.copy(alpha = 0.2f))

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        branch.leaves.forEach { leaf ->
                            Surface(
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.border(
                                    1.dp,
                                    branch.branchColor.copy(alpha = 0.25f),
                                    RoundedCornerShape(10.dp)
                                )
                            ) {
                                BiDiText(
                                    text = "🌿 $leaf",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun buildSubjectTableData(
    subjectId: String,
    accentColor: Color
): Triple<String, List<String>, List<VisualTableRowData>> = when (subjectId) {
    "math" -> Triple(
        "جدول گرافیکی مجموعه‌های اعداد (پایه اول تا نهم) و کلیدهای ریاضی دهم تا دوازدهم",
        listOf("مجموعه / مبحث", "تعریف به زبان ساده", "اعضا و مثال", "نکته طلایی کنکور"),
        listOf(
            VisualTableRowData("طبیعی (ℕ)", "اعداد شمارش انگشتان دست از ۱ به بالا", "{۱، ۲، ۳، ۴، ...}", "صفر و منفی و کسر ندارد!", Color(0xFF2563EB)),
            VisualTableRowData("حسابی (𝕎)", "اعداد طبیعی به اضافهٔ عدد صفر (۰)", "{۰، ۱، ۲، ۳، ...}", "𝕎 = ℕ ∪ {۰} (شروع از صفر)", Color(0xFF0891B2)),
            VisualTableRowData("صحیح (ℤ)", "طبقات بالا (+)، همکف (۰) و زیرزمین (−)", "{...، −۲، −۱، ۰، +۱، +۲، ...}", "ℤ = اعداد مثبت + صفر + منفی‌ها", Color(0xFF059669)),
            VisualTableRowData("گویا (ℚ)", "هر عددی که به شکل کسر a/b نوشته شود", "۱/۲ ، −۳/۴ ، ۰/۲۵ ، ۵", "شامل تمام صحیح‌ها، کسرها و اعشاری‌ها", Color(0xFF7C3AED)),
            VisualTableRowData("گنگ / اصم (ℚ')", "رادیکال‌های بدون جذر کامل و عدد پی", "√۲ ، √۳ ، √۵ ، π", "زیر رادیکال از زندان بیرون نمی‌آید!", Color(0xFFDB2777)),
            VisualTableRowData("حقیقی (ℝ)", "اجتماع تمام اعداد گویا و گنگ روی محور", "ℝ = ℚ ∪ ℚ' (کل محور اعداد)", "بین هر دو عدد بی‌شمار عدد حقیقی هست", Color(0xFFD97706)),
            VisualTableRowData("اول و مرکب", "عدد اول فقط بر ۱ و خودش بخش‌پذیر است", "اول: {۲، ۳، ۵، ۷، ۱۱، ۱۳، ...}", "عدد ۱ نه اول است نه مرکب! تنها اولِ زوج = ۲", Color(0xFFE11D48))
        )
    )

    "philosophy" -> Triple(
        "جدول گرافیکی جامع فلسفه ۱ (یازدهم) و فلسفه ۲ (دوازدهم انسانی)",
        listOf("مبحث / فیلسوف", "کلیدواژه کتاب درسی", "تمثیل و داستان تصویری", "حکم قطعی کنکور و نهایی"),
        listOf(
            VisualTableRowData("یونان باستان (۱۱)", "طالس (آب)، هراکلیتوس (تغییر)، پارمنیدس", "رودخانه تغییر vs صخره ثابت", "افلاطون = عالم مُثُل | ارسطو = ۴ علت", Color(0xFF7C3AED)),
            VisualTableRowData("اصالت وجود (۱۲)", "وجود = نور واقعی | ماهیت = قاب شیشه‌ای", "خورشید و شیشه‌های رنگی", "ملاصدرا = اصالت و تشکیک وجود | سهروردی = ماهیت", Color(0xFFD97706)),
            VisualTableRowData("علیت (۱۲)", "سنخیت، ضرورت علّی و وابستگی وجودی معلول", "تشنگان و آب (رابطه حقیقی)", "ابراهیم = علیت الهی | هیوم = عادت ذهنی و تداعی", Color(0xFF059669)),
            VisualTableRowData("۴ مکتب اسلامی", "مشاء، اشراق، عرفان، حکمت متعالیه", "رمز مَفا / سَشَن / صَقَبَع", "صدرا = وحدت قرآن (وحی) + برهان + عرفان", accentColor)
        )
    )

    "logic" -> Triple(
        "جدول گرافیکی جامع منطق پایه دهم انسانی (تصورات و تصدیقات)",
        listOf("مبحث منطق", "کلیدواژه کتاب درسی", "تمثیل و داستان تصویری", "حکم قطعی کنکور"),
        listOf(
            VisualTableRowData("کلیات خمس و تعریف", "جنس، فصل، نوع، عرض خاص، عرض عام", "حد تام = جنس نزدیک + فصل", "رسم تام = جنس نزدیک + عرض خاص", Color(0xFF0891B2)),
            VisualTableRowData("مربع تقابل (ضربدر)", "تناقض: م.ک با س.ج | س.ک با م.ج", "دو دشمن خونی روبه‌روی هم", "یکی صادق ⟹ دیگری ۱۰۰٪ کاذب (و برعکس)", Color(0xFFE11D48)),
            VisualTableRowData("مربع تقابل (سقف/کف)", "سقف = تضاد (کلی‌ها) | کف = تحت تضاد", "سقف با هم راست نمی‌گویند", "کذب در تضاد = نامعلوم | صدق در تحت تضاد = نامعلوم", Color(0xFF2563EB)),
            VisualTableRowData("عکس مستوی و قیاس", "جابه‌جایی موضوع و محمول با حفظ کیف", "م.ک آب می‌رود و می‌شود م.ج!", "س.ج عکس مستوی ندارد | استثنایی: وضع مقدم و رفع تالی", Color(0xFF059669))
        )
    )

    "english" -> Triple(
        "جدول گرافیکی گرامر زبان انگلیسی به زبان خودمونی (با معنی فارسی تمام اصطلاحات)",
        listOf("نام گرامر به فارسی", "فرمول چشمی (با معنی)", "نشانه در سوال", "مثال با ترجمه"),
        listOf(
            VisualTableRowData("اصطلاحات پایه گرامر", "Subject = فاعل (انجام‌دهنده) | Verb = فعل (کار) | Object = مفعول", "ترتیب چپ به راست: فاعل + فعل + مفعول", "Ali (فاعل) eats (فعل) an apple (مفعول).", Color(0xFFEA580C)),
            VisualTableRowData("حال ساده و استمراری", "فعل ساده یا با s (عادت) vs am/is/are + فعل با ing (الان)", "every day (هر روز) vs now (الان)", "She studies (می‌خواند) / She is studying (دارد می‌خواند)", Color(0xFF2563EB)),
            VisualTableRowData("گذشته ساده و بعید", "فعل با ed (گذشته) vs had + شکل سوم فعل (قبل از گذشته)", "yesterday (دیروز) vs before / after", "After he had eaten (خورده بود), he slept (خوابید).", Color(0xFF0891B2)),
            VisualTableRowData("حال کامل (ماضی نقلی)", "have / has + شکل سوم فعل (فعلی که ed گرفته یا بی‌قاعده است)", "دیدن کلمات since (از زمانِ) و for (به مدتِ)", "Ali has lived here since ۲۰۲۰ (زندگی کرده است).", Color(0xFF059669)),
            VisualTableRowData("جمله مجهول (Passive)", "اول جمله شیء است + am/is/are/was/were + شکل سوم فعل", "وقتی اول جمله انسان نیست یا کلمه by (توسط) دارد", "The bridge was built (پل ساخته شد) by engineers.", Color(0xFF7C3AED)),
            VisualTableRowData("شرطی نوع ۱ و ۲ (If)", "نوع ۱: If + حال ساده ⟹ will | نوع ۲: If + گذشته ⟹ would", "نوع ۲ (خیالی): If I were you (اگر جای تو بودم)", "If I were you, I would study hard.", Color(0xFFDB2777)),
            VisualTableRowData("سوال دم‌چسبیده (Tag)", "جمله مثبت باشد ⟹ آخرش منفی؟ | جمله منفی ⟹ آخرش مثبت؟", "برعکس کردن فعل کمکی در انتهای جمله بعد از ویرگول", "Sara can swim, can't she? (مگه نه؟)", Color(0xFFD97706))
        )
    )

    "arooz" -> Triple(
        "جدول گرافیکی بارکد ۴ هجای اول تمام بحرهای عروض و قواعد قافیه",
        listOf("کد ۴ هجای اول", "نام رکن عروضی", "نام بحر و ریتم سماعی", "مثال مشهور کتاب درسی"),
        listOf(
            VisualTableRowData("U – – – (۱ کوتاه)", "مَفاعیلُن (تَ تَن تَن تَن)", "بحر هَزَج مثمن سالم", "که عشق آسان نمود اول ولی افتاد مشکل‌ها", Color(0xFFDB2777)),
            VisualTableRowData("– U – – (۲ کوتاه)", "فاعِلاتُن (تَن تَ تَن تَن)", "بحر رَمَل مثمن سالم/محذوف", "ایها الساقی ادر کاسا و ناولها", Color(0xFF7C3AED)),
            VisualTableRowData("– – U – (۳ کوتاه)", "مُستَفعِلُن (تَن تَن تَ تَن)", "بحر رَجَز مثمن سالم", "ای ساربان آهسته ران کارام جانم می‌رود", Color(0xFF2563EB)),
            VisualTableRowData("U – – (۳ هجایی)", "فَعولُن فَعولُن فَعولُن فَعَل", "بحر مُتَقارِب (وزن حماسی شاهنامه)", "بنی آدم اعضای یکدیگرند / توانا بود هر که دانا بود", Color(0xFF059669)),
            VisualTableRowData("U U – – (۱ و ۲ کوتاه)", "فَعِلاتُن فَعِلاتُن فَعِلاتُن فَعَلُن", "بحر رمل مثمن مخبون", "یاد باد آنکه نهانت نظری با ما بود", Color(0xFFD97706)),
            VisualTableRowData("– U U – (۲ و ۳ کوتاه)", "مُفتَعِلُن فاعِلاتُ مُفتَعِلُن فَع", "وزن دوری (مکث در میانه مصراع)", "مرده بدم زنده شدم گریه بدم خنده شدم", Color(0xFF0891B2)),
            VisualTableRowData("– – U U (۳ و ۴ کوتاه)", "مَفعولُ مَفاعیلُ مَفاعیلُ فَعولُن", "وزن دوری هزج اخرب مکفوف", "ای پادشه خوبان داد از غم تنهایی", accentColor)
        )
    )

    "fonoon" -> Triple(
        "جدول گرافیکی آرایه‌های ادبی و مقایسه ۳ سبک شعر فارسی (خراسانی، عراقی، هندی)",
        listOf("آرایه / سبک", "فرمول تشخیص ۲ ثانیه‌ای", "تفاوت با رقیب (ضد تله)", "مثال استاندارد کتاب درسی"),
        listOf(
            VisualTableRowData("تشبیه (۴ رکن)", "مشبه و مشبه‌به هر دو در بیت حاضرند!", "اضافه تشبیهی: چراغِ عقل، لبِ لعل", "دل چو آیینه است / درخت دوستی بنشان", Color(0xFF2563EB)),
            VisualTableRowData("استعاره (شباهت+حذف)", "یک طرف غایب است + رابطه شباهت", "اضافه استعاری: دستِ روزگار (تشخیص)", "ای آفتاب خوبان (آفتاب = استعاره از معشوق)", Color(0xFF7C3AED)),
            VisualTableRowData("مجاز (غیرشباهت)", "کلمه در غیر معنی اصلی با علاقه جزء/کل/محل", "برعکس استعاره، شباهتی در کار نیست!", "سر (قصد) آن ندارد امشب / جهان (مردم) خرم از او", Color(0xFF059669)),
            VisualTableRowData("کنایه و ایهام", "کنایه = جمله ضرب‌المثلی | ایهام = ۱ واژه ۲ معنی", "در ایهام هر دو معنی در شعر درست است", "دهان به دندان گزیدن (پشیمانی) / بگذشت (گذشت/مرد)", Color(0xFFD97706)),
            VisualTableRowData("سبک خراسانی (رُفَفَم)", "قرن ۴ و ۵: رودکی، فردوسی، فرخی، منوچهری", "قصیده، حماسه، تشبیه حسی، واژه کهن فارسی", "بوی جوی مولیان آید همی / شاهنامه فردوسی", Color(0xFF0891B2)),
            VisualTableRowData("سبک عراقی (سَمَحَع)", "قرن ۶ تا ۹: سعدی، مولوی، حافظ، عطار", "اوج غزل عاشقانه و عارفانه، استعاره و ایهام", "غزلیات سعدی و حافظ و مثنوی مولوی", Color(0xFFDB2777)),
            VisualTableRowData("سبک هندی (صَکَبَع)", "قرن ۱۰ تا ۱۲: صائب، کلیم، بیدل، عرفی", "اسلوب معادله (دو مصراع مستقل)، حس‌آمیزی", "عیب پاکان زود بر مردم هویدا می‌شود // موی اندر شیر...", accentColor)
        )
    )

    "history" -> Triple(
        "جدول گرافیکی جامع تاریخ ۱، ۲ و ۳ انسانی (دهم ۱۴۰۳، یازدهم ۱۴۰۴، دوازدهم ۱۴۰۵)",
        listOf("کتاب و پایه", "موضوع و دوره تاریخی", "مهم‌ترین کلیدواژه کنکور ۱۴۰۶", "نکته طلایی و حکم قطعی"),
        listOf(
            VisualTableRowData("تاریخ دهم (چاپ ۱۴۰۳)", "ایران باستان (ماد، هخامنشی، اشکانی، ساسانی)", "کورش، داریوش (چاپارخانه/ساتراپ)، مهرداد، انوشیروان", "ساتراپ = هخامنشی | مجلس مهستان = اشکانی | دین رسمی زرتشت = ساسانی", Color(0xFFD97706)),
            VisualTableRowData("تاریخ یازدهم (چاپ ۱۴۰۴)", "اسلام، طاهریان تا صفویه و رنسانس اروپا", "طاهریان، سلجوقیان (نظامیه)، مغول، شاه عباس صفوی", "اولین حکومت نیمه‌مستقل = طاهریان | مذهب رسمی شیعه = صفویه", Color(0xFF2563EB)),
            VisualTableRowData("تاریخ دوازدهم (چاپ ۱۴۰۵)", "قاجار، مشروطه، پهلوی، انقلاب اسلامی و جنگ‌های جهانی", "امیرکبیر (دارالفنون)، قرارداد ۱۹۰۷ و ۱۹۱۹، ملی شدن نفت", "دارالفنون = امیرکبیر | لغو تنباکو = میرزای شیرازی | مشروطه = مظفرالدین‌شاه", Color(0xFF059669))
        )
    )

    "geography" -> Triple(
        "جدول گرافیکی جامع جغرافیا ۱، ۲ و ۳ انسانی (دهم ۱۴۰۳، یازدهم ۱۴۰۴، دوازدهم ۱۴۰۵)",
        listOf("کتاب و پایه", "موضوع جغرافیایی", "مهم‌ترین کلیدواژه کنکور ۱۴۰۶", "نکته طلایی و حکم قطعی"),
        listOf(
            VisualTableRowData("جغرافیا دهم (چاپ ۱۴۰۳)", "جغرافیای ایران: آب‌وهوا، ناهمواری‌ها، جمعیت و تقسیمات", "توده‌های هوای ایران، زاگرس و البرز، هرم سنی", "پرباران‌ترین = خزری | وسیع‌ترین کوهستان = زاگرس", Color(0xFF0891B2)),
            VisualTableRowData("جغرافیا یازدهم (چاپ ۱۴۰۴)", "جغرافیای ناحیه‌ای: نواحی طبیعی، فرهنگی، سیاسی و اقتصادی", "ناحیه، مرز سیاسی، تجارت جهانی و انرژی‌های نو", "مرز طبیعی (رود/کوه) vs مرز هندسی و قراردادی", Color(0xFF7C3AED)),
            VisualTableRowData("جغرافیا دوازدهم (چاپ ۱۴۰۵)", "جغرافیای کاربردی: شهرسازی، حمل‌ونقل، مخاطرات و GIS", "سیستم اطلاعات جغرافیایی (GIS)، سنجش از دور (RS)", "داده‌های مکانی + توصیفی = GIS | ماهواره و عکس هوایی = سنجش از دور", Color(0xFFE11D48))
        )
    )

    "economics" -> Triple(
        "جدول گرافیکی جامع اقتصاد پایه دهم انسانی (مفاهیم و مسائل)",
        listOf("بخش اقتصاد", "مفهوم کلیدی کتاب درسی", "فرمول و نشانه تستی", "حکم قطعی کنکور ۱۴۰۶"),
        listOf(
            VisualTableRowData("هزینه فرصت و سود", "هزینه فرصت، سود حسابداری و سود اقتصادی", "هزینه فرصت = ارزش بهترین گزینه رهاشده", "سود اقتصادی = سود حسابداری − هزینه فرصت (پنهان)", Color(0xFF059669)),
            VisualTableRowData("بازار و تعادل", "عرضه و تقاضا، مازاد عرضه و مازاد تقاضا", "قیمت بالاتر از تعادل = مازاد عرضه", "قیمت پایین‌تر از تعادل = مازاد تقاضا (کمبود)", Color(0xFF0D9488)),
            VisualTableRowData("شاخص‌های کلان و بانک", "تورم، نقدینگی، مالیات مستقیم و غیرمستقیم، GDP", "مالیات بر درآمد/دارایی = مستقیم | ارزش افزوده = غیرمستقیم", "قرض‌الحسنه و جعاله = عقود بانکی | شاخص توسعه انسانی = HDI", Color(0xFF2563EB))
        )
    )

    "psychology" -> Triple(
        "جدول گرافیکی جامع روان‌شناسی پایه یازدهم انسانی",
        listOf("فصل روان‌شناسی", "مفهوم کلیدی کتاب درسی", "کلیدواژه و نشانه تستی", "حکم قطعی کنکور ۱۴۰۶"),
        listOf(
            VisualTableRowData("رشد (پیاژه)", "۴ مرحله رشد شناختی پیاژه", "حسی-حرکتی، پیش‌عملیاتی، عینی، انتزاعی", "پایداری شیء = حسی-حرکتی | نگهداری ذهنی = عملیاتی عینی", Color(0xFF2563EB)),
            VisualTableRowData("حافظه و فراموشی", "حسی، کوتاه‌مدت (۷±۲) و بلندمدت", "تداخل پیش‌گستر vs تداخل پس‌گستر", "فراموشی مطلب جدید = پیش‌گستر | فراموشی قدیم = پس‌گستر", Color(0xFF7C3AED)),
            VisualTableRowData("یادگیری و استرس", "شرطی‌شدن کلاسیک و کنشی، مقابله با استرس", "پاولف (محرک) | اسکینر (پاداش/تنبیه)", "مقابله مسئله‌مدار (حذف عامل) vs هیجان‌مدار (آرام‌سازی)", Color(0xFF059669))
        )
    )

    "sociology" -> Triple(
        "جدول گرافیکی جامع جامعه‌شناسی ۱، ۲ و ۳ انسانی (چاپ ۱۴۰۳-۱۴۰۵)",
        listOf("پایه و کتاب", "مفهوم کلیدی کتاب درسی", "فرمول و نشانه تستی", "حکم قطعی کنکور ۱۴۰۶"),
        listOf(
            VisualTableRowData("جامعه‌شناسی ۱ (دهم)", "کنش اجتماعی، فرهنگ، هویت فردی و اجتماعی", "کنش = آگاهانه + ارادی + هدفدار + معنادار", "کنش اجتماعی = معطوف به دیگران | پدیده‌های طبیعی کنش نیستند!", Color(0xFF7C3AED)),
            VisualTableRowData("جامعه‌شناسی ۲ (یازدهم)", "فرهنگ جهانی، غرب‌زدگی، استعمار و بیداری اسلامی", "دنیوی‌شدن (سکولاریسم)، روشنگری، استعمار کهن و نو", "بحران‌های مدرنیته = بحران معنا، هویت، محیط‌زیست و خانواده", Color(0xFFD97706)),
            VisualTableRowData("جامعه‌شناسی ۳ (دوازدهم)", "۴ پارادایم علوم اجتماعی: اثباتی، تفسیری، انتقادی، اسلامی", "اثباتی (کنت/دورکیم) | تفسیری (وبر) | انتقادی (مارکس)", "تبیین علّی = اثباتی | فهم معنا = تفسیری | رهایی‌بخشی = انتقادی", Color(0xFFE11D48))
        )
    )

    "dini" -> Triple(
        "جدول گرافیکی جامع دین و زندگی ۱، ۲ و ۳ (دهم ۱۴۰۳، یازدهم ۱۴۰۴، دوازدهم ۱۴۰۵)",
        listOf("پایه و درس", "مفهوم کلیدی کتاب درسی", "آیه و نشانه پرتکرار", "حکم قطعی نهایی و کنکور"),
        listOf(
            VisualTableRowData("دینی دهم (چاپ ۱۴۰۳)", "هدف خلقت، توفّی، برزخ، قیامت و تجسم اعمال", "اللَّهُ یَتَوَفَّی الْأَنْفُسَ / فَمَنْ یَعْمَلْ مِثْقالَ ذَرَّةٍ", "توفّی = دریافت کامل روح | دیدن خودِ عمل در قیامت = تجسم اعمال", Color(0xFF047857)),
            VisualTableRowData("دینی یازدهم (چاپ ۱۴۰۴)", "هدایت عامه و خاصه، اعجاز قرآن، امامت و ولایت فقیه", "رَبُّنَا الَّذِی أَعْطَی کُلَّ شَیْءٍ خَلْقَهُ ثُمَّ هَدَی", "هدایت تکوینی (غریزی/طبیعی) = عامه | هدایت تشریعی (پیامبران) = خاصه", Color(0xFF059669)),
            VisualTableRowData("دینی دوازدهم (چاپ ۱۴۰۵)", "توحید ذاتی، صفاتی، افعالی و عملی + شرک خفی", "قُلْ هُوَ اللَّهُ أَحَدٌ / وَ عَلَی اللَّهِ فَتَوَکَّلُوا", "تدبیر و روزی و مالکیت جهان = توحید افعالی (ریشه توکل)", Color(0xFF0891B2)),
            VisualTableRowData("دینی دوازدهم (اختیار)", "الامر بین الامرین، قضا و قدر الهی، توبه و تقوا", "إِنَّا هَدَیْنَاهُ السَّبِیلَ إِمَّا شَاکِرًا وَ إِمَّا کَفُورًا", "نه جبر و نه تفویض = اختیار انسان در طول اراده الهی", Color(0xFF7C3AED))
        )
    )

    "farsi", "negaresh" -> Triple(
        "جدول گرافیکی جامع فارسی و نگارش ۱، ۲ و ۳ (دستور، قلمروها و ۲۰ نهایی)",
        listOf("مبحث فارسی", "فرمول تشخیص ۲ ثانیه‌ای", "تفاوت با رقیب", "مثال استاندارد امتحان نهایی"),
        listOf(
            VisualTableRowData("نقش‌های اصلی جمله", "نهاد (چه کسی؟) | مفعول (+ را) | متمم (بعد از حرف اضافه) | مسند", "مسند قبل از (است، بود، شد، گشت، گردید) می‌آید", "هوا (نهاد) سرد (مسند) شد (فعل اسنادی).", Color(0xFF0284C7)),
            VisualTableRowData("گروه اسمی (هسته و وابسته)", "هسته = کلمه اصلی | وابسته پیشین (قبل) | وابسته پسین (بعد با کسره)", "بین دو کلمه «است» بگذار: معنی داد = صفت بیانی، نداد = مضاف‌الیه", "دلِ شاد (دل شاد است = صفت بیانی) vs کتابِ علی (مضاف‌الیه)", Color(0xFF2563EB)),
            VisualTableRowData("ساختمان واژه", "ساده (۱ جزء) | مشتق (بن/اسم + وند) | مرکب (۲ جزء معنادار) | مشتق-مرکب", "تکواژهای تصریفی (ها، ان، تر، م/ت/ش) مشتق نمی‌سازند!", "کتاب‌ها (ساده) | دانشمند (مشتق) | گلاب (مرکب) | خوش‌اخلاق‌ها (مرکب)", Color(0xFF059669)),
            VisualTableRowData("نگارش ۱، ۲ و ۳ (نهایی ۲۰)", "مقدمه (جملات آغازین) + بدنه (استدلال و مصداق) + نتیجه‌گیری", "حفظ املای واژه‌های هم‌آوا (خوار/خار، صواب/ثواب، فترت/فطرت)", "بازگردانی ضرب‌المثل + درک متن + انشای استدلالی = ۲۰ قطعی", Color(0xFFD97706))
        )
    )

    "general_humanities", "social_identity", "media_environment" -> Triple(
        "جدول گرافیکی کتب عمومی انسانی (سلامت ۱۲، هویت ۱۲، خانواده ۱۲، محیط زیست ۱۱، سواد رسانه‌ای ۱۰)",
        listOf("نام کتاب رسمی", "مهم‌ترین فرمول و مفهوم", "کلیدواژه امتحان نهایی", "حکم قطعی نمره ۲۰"),
        listOf(
            VisualTableRowData("سلامت و بهداشت ۱۲ (۱۴۰۵)", "نمایه توده بدنی: BMI = وزن (kg) ÷ قد² (m²)", "بیماری‌های واگیر vs غیرواگیر (دیابت، قلبی، سرطان)", "BMI بین ۱۸/۵ تا ۲۵ طبیعی | پیشگیری سطح ۱ = واکسیناسیون", Color(0xFF4F46E5)),
            VisualTableRowData("هویت اجتماعی ۱۲ (۱۴۰۵)", "هویت فردی و اجتماعی، بحران هویت، جهانی‌شدن و هویت ملی-اسلامی", "تغییرات هویتی ارادی vs غیرارادی", "عقاید و ارزش‌های اسلامی + زبان فارسی و تاریخ = ارکان هویت ملی ایران", Color(0xFF7C3AED)),
            VisualTableRowData("انسان و محیط زیست ۱۱ (۱۴۰۴)", "آب مجازی، ردپای اکولوژیک، توسعه پایدار، پسماند و تنوع زیستی", "آب پنهان مصرف‌شده در کل زنجیره تولید = آب مجازی", "انرژی خورشیدی و بادی = پاک و تجدیدپذیر | کود شیمیایی = آلودگی آب", Color(0xFF059669)),
            VisualTableRowData("سواد رسانه‌ای ۱۰ (۱۴۰۳)", "رژیم مصرف رسانه‌ای، فنون اقناع (شهادت، همنوایی، کلی‌گویی درخشان)", "استفاده از سلبریتی = شهادت | همه می‌خرند = همنوایی", "دروازه‌بانی خبر = انتخاب و برجسته‌سازی برخی اخبار توسط رسانه", Color(0xFFD97706))
        )
    )

    else -> Triple(
        "جدول گرافیکی منابع و ضرایب قبولی قطعی دانشگاه فرهنگیان ۱۴۰۶",
        listOf("بخش آزمون", "سهم و ضریب", "منبع رسمی کتاب درسی", "فرمول طلایی ۱۰۰ زدن"),
        listOf(
            VisualTableRowData("امتحانات نهایی (سوابق تحصیلی)", "۶۰٪ نمره کل (قاعده ۱۴۰۶)", "کتب رسمی پایه دوازدهم انسانی (چاپ ۱۴۰۵)", "نوشتن فرمول و کلیدواژه کتاب درسی = نمره ۲۰", Color(0xFF059669)),
            VisualTableRowData("کنکور اختصاصی انسانی", "۴۰٪ نمره کل (قاعده ۱۴۰۶)", "فارسی و فنون (۸)، ریاضی (۶)، عربی، تاریخ و جغرافیا، علوم اجتماعی، فلسفه و منطق (۵)، اقتصاد و روان‌شناسی (۲)", "تمرکز روی درس‌نامه‌های گام‌به‌گام همین برنامه", Color(0xFF2563EB)),
            VisualTableRowData("مهارت معلمی قرائتی", "ضریب ۳ دفترچه فرهنگیان", "۳ فصل اول کتاب مهارت‌های معلمی استاد قرائتی", "رمز «قـحـب»: قبل از تدریس، حین تدریس، بعد از تدریس", Color(0xFFD97706)),
            VisualTableRowData("سند تحول بنیادین", "بخش مهارت معلمی و مصاحبه", "۶ ساحت تربیتی سند تحول بنیادین آموزش و پرورش", "رمز «اعـاجـز زیـعـا» (محوری‌ترین = ساحت اعتقادی)", Color(0xFF7C3AED)),
            VisualTableRowData("دینی فرهنگیان", "ضریب ۳ دفترچه فرهنگیان", "گزیده درس‌های دین و زندگی دهم و یازدهم انسانی", "تطبیق آیات با پیام‌ها (توکل، تقوا، عدل، امامت)", Color(0xFF0891B2)),
            VisualTableRowData("هوش و استعداد معلمی", "ضریب ۲ دفترچه فرهنگیان", "استدلال منطقی، درک مطلب، حل مسئله کلاسی", "اولین قدم معلم در مواجهه با خطا: گفت‌وگوی خصوصی!", Color(0xFFDB2777))
        )
    )
}

private fun buildSubjectTreeData(
    subjectId: String,
    accentColor: Color
): Triple<String, String, List<VisualTreeBranchData>> = when (subjectId) {
    "math" -> Triple(
        "نمودار درختی جامع ریاضی و آمار (از اول ابتدایی تا دوازدهم انسانی)",
        "دسته‌بندی درختی تمام مجموعه‌های اعداد و فصول کتب درسی دهم، یازدهم و دوازدهم",
        listOf(
            VisualTreeBranchData(
                branchTitle = "مجموعه‌های اعداد (پایه اول ابتدایی تا نهم)",
                branchBadge = "پیش‌نیاز صفر مطلق",
                branchColor = Color(0xFF2563EB),
                leaves = listOf(
                    "طبیعی ℕ = {۱، ۲، ۳، ...}",
                    "حسابی 𝕎 = {۰، ۱، ۲، ...}",
                    "صحیح ℤ = {...، −۲، −۱، ۰، +۱، +۲، ...}",
                    "گویا ℚ = کسرها (a/b) و اعشار متناهی/متناوب",
                    "گنگ ℚ' = رادیکال‌های بدون جذر (√۲، √۳) و π",
                    "حقیقی ℝ = ℚ ∪ ℚ' (کل محور اعداد)",
                    "اعداد اول (۲، ۳، ۵، ۷، ۱۱) و مرکب (۴، ۶، ۸، ۹)"
                )
            ),
            VisualTreeBranchData(
                branchTitle = "ریاضی و آمار ۱ (کتاب پایه دهم انسانی)",
                branchBadge = "دهم انسانی",
                branchColor = Color(0xFF0891B2),
                leaves = listOf(
                    "معادله درجه ۱ (معلوم و مجهول)",
                    "معادله درجه ۲ (روش جمع و ضرب، جمع ضرایب صفر)",
                    "مجموعه‌ها و نمودار ون (A ∪ B و A ∩ B)",
                    "الگوهای خطی و غیرخطی"
                )
            ),
            VisualTreeBranchData(
                branchTitle = "ریاضی و آمار ۲ (کتاب پایه یازدهم انسانی)",
                branchBadge = "یازدهم انسانی",
                branchColor = Color(0xFF059669),
                leaves = listOf(
                    "گزاره‌ها و ترکیب‌های منطقی (∧ ، ∨ ، ⟹)",
                    "تابع (زوج مرتب، دامنه و برد، تابع خطی و درجه ۲)",
                    "شاخص‌های آماری (خط فقر، تورم، شاخص بهای کالا)"
                )
            ),
            VisualTreeBranchData(
                branchTitle = "ریاضی و آمار ۳ (کتاب پایه دوازدهم انسانی + نهایی)",
                branchBadge = "دوازدهم و ۲۰ نهایی",
                branchColor = Color(0xFFD97706),
                leaves = listOf(
                    "فصل ۱: شمارش، فاکتوریل (!n)، جایگشت و ترکیب",
                    "فصل ۲: احتمال و امید ریاضی (۷ نمره نهایی)",
                    "فصل ۳: دنباله حسابی (+d) و دنباله هندسی (×q)"
                )
            )
        )
    )

    "arabic" -> Triple(
        "نمودار درختی جامع عربی (از هفتم تا دوازدهم تخصصی انسانی)",
        "کالبدشکافی درختی اقسام کلمه، ۱۴ صیغه، ۸ باب، مشتقات و منصوبات",
        listOf(
            VisualTreeBranchData(
                branchTitle = "شاخه ۱: تشخیص ۱ ثانیه‌ای اسم، فعل و حرف (پایه هفتم تا نهم)",
                branchBadge = "پایه هفتم تا نهم",
                branchColor = Color(0xFF059669),
                leaves = listOf(
                    "۴ نشانه قطعی اسم: الـ | تنوین (ـً ـٍ ـٌ) | ة گرد | بعد از حرف جر",
                    "مثنی (۲ نفر): انِ / َینِ (با فتحه)",
                    "جمع مذکر سالم: ونَ / ِینَ (با کسره)",
                    "جمع مؤنث سالم: ات"
                )
            ),
            VisualTreeBranchData(
                branchTitle = "شاخه ۲: درخت ۳ اتاقه ۱۴ صیغه فعل (ماضی، مضارع، امر و نهی)",
                branchBadge = "کلید صیغه‌ها",
                branchColor = Color(0xFF2563EB),
                leaves = listOf(
                    "اتاق غایب (۱ تا ۶): ذَهَبَ، ذَهَبا، ذَهَبوا، ذَهَبَتْ، ذَهَبَتا، ذَهَبنَ",
                    "اتاق مخاطب (۷ تا ۱۲): ذَهَبتَ، ذَهَبتُما، ذَهَبتُم، ذَهَبتِ، ذَهَبتُما، ذَهَبتُنَّ",
                    "اتاق متکلم (۱۳ و ۱۴): ذَهَبتُ (من) ، ذَهَبنا (ما)",
                    "مضارع با رمز أَتَینَ در اول: یـ (غایب) | تـ (مخاطب/غایب مؤنث) | أ (من) | نـ (ما)"
                )
            ),
            VisualTreeBranchData(
                branchTitle = "شاخه ۳: قواعد دهم و یازدهم (۸ باب، مجهول، مشتقات، شرط و ناقصه)",
                branchBadge = "دهم و یازدهم انسانی",
                branchColor = Color(0xFF7C3AED),
                leaves = listOf(
                    "۸ باب ثلاثی مزید: إفعال، تفعیل، مفاعله، تفعّل، تفاعل، افتعال، انفعال، استفعال",
                    "فعل مجهول: ضمه (ـُ) روی حرف اول فعل (کُتِبَ / یُکتَبُ)",
                    "مشتقات: اسم فاعل (فاعِل / مُـ...ِ...)، اسم مفعول (مَفعول / مُـ...َ...)، مبالغه (فَعّال)",
                    "حروف مشبهة بالفعل (إنَّ... اسم منصوب) vs افعال ناقصه (کانَ... اسم مرفوع)"
                )
            ),
            VisualTreeBranchData(
                branchTitle = "شاخه ۴: قواعد دوازدهم انسانی و ۲۰ امتحان نهایی (منصوبات)",
                branchBadge = "دوازدهم و نهایی",
                branchColor = Color(0xFFD97706),
                leaves = listOf(
                    "مفعول مطلق (هم‌ریشه با فعل): تأکیدی (بدون ادامه) و نوعی (با صفت/مضاف‌الیه)",
                    "حال (قید حالت منصوب و نکره در جواب چطور؟)",
                    "منادا (یا + مفرد علم مبنی بر ضم / یا + مضاف منصوب)",
                    "مستثنی (إلّا) و اسلوب حصر (ما ... إلّا)"
                )
            )
        )
    )

    "logic" -> Triple(
        "نمودار درختی جامع منطق پایه دهم انسانی",
        "ساختار درختی کامل کتاب منطق دهم (تصورات و تصدیقات)",
        listOf(
            VisualTreeBranchData(
                branchTitle = "شاخه ۱: منطق پایه دهم — بخش تصورات (معرف / تعریف)",
                branchBadge = "منطق دهم (نیمه اول)",
                branchColor = Color(0xFF0891B2),
                leaves = listOf(
                    "اقسام دلالت لفظی: مطابقی (کل) | تضمنی (جزء) | التزامی (خارج همراه)",
                    "نسبت‌های چهارگانه: تساوی | تباین | عموم و خصوص مطلق | عموم و خصوص من‌وجه",
                    "کلیات خمس: ذاتی (نوع، جنس، فصل) | عرضی (عرض خاص، عرض عام)",
                    "تعریف: حد تام (جنس نزدیک+فصل) | حد ناقص | رسم تام (جنس نزدیک+عرض خاص) | رسم ناقص"
                )
            ),
            VisualTreeBranchData(
                branchTitle = "شاخه ۲: منطق پایه دهم — بخش تصدیقات (قضایا و قیاس)",
                branchBadge = "منطق دهم (نیمه دوم)",
                branchColor = Color(0xFFE11D48),
                leaves = listOf(
                    "قضایای حملی محصوره: موجبه کلی (هر+) | سالبه کلی (هیچ−) | موجبه جزئی (بعضی+) | سالبه جزئی (بعضی−)",
                    "مربع تقابل: تناقض (ضربدر) | تضاد (سقف) | دخول تحت تضاد (کف) | تداخل (ستون‌ها)",
                    "قیاس اقترانی (۴ شکل بر اساس جای حد وسط) + قیاس استثنایی (وضع مقدم و رفع تالی)"
                )
            )
        )
    )

    "philosophy" -> Triple(
        "نمودار درختی جامع فلسفه یازدهم و دوازدهم انسانی",
        "ساختار درختی کامل کتاب فلسفه ۱ (یازدهم) و فلسفه ۲ (دوازدهم)",
        listOf(
            VisualTreeBranchData(
                branchTitle = "شاخه ۱: فلسفه پایه یازدهم انسانی (یونان و معرفت‌شناسی)",
                branchBadge = "فلسفه یازدهم",
                branchColor = Color(0xFF7C3AED),
                leaves = listOf(
                    "پیشاسقراطیان: طالس (آب) | هراکلیتوس (تغییر و لوگوس) | پارمنیدس (ثبات هستی)",
                    "سه‌گانه آتن: سقراط (دیالوگ) | افلاطون (عالم مُثُل و غار) | ارسطو (منطق و علل اربعه)",
                    "معرفت‌شناسی: دکارت (عقل‌گرا) | لاک و هیوم (تجربه‌گرا) | کانت (حس + قالب‌های پیشینی ذهن)"
                )
            ),
            VisualTreeBranchData(
                branchTitle = "شاخه ۲: فلسفه پایه دوازدهم انسانی (هستی‌شناسی، علیت و مکاتب اسلامی)",
                branchBadge = "فلسفه دوازدهم",
                branchColor = Color(0xFFD97706),
                leaves = listOf(
                    "فصل ۱: وجود و ماهیت + اصالت وجود (ملاصدرا) + تشکیک در مراتب وجود (شدت و ضعف نور)",
                    "فصل ۲: علیت (ضرورت علی، سنخیت، وابستگی وجودی معلول به علت)",
                    "فصل ۳ و ۴: مشاء (ابن‌سینا - برهان) | اشراق (سهروردی - برهان و شهود) | حکمت متعالیه (ملاصدرا - قرآن، برهان و عرفان)"
                )
            )
        )
    )

    "english" -> Triple(
        "نمودار درختی جامع زبان انگلیسی (پایه هفتم تا دوازدهم)",
        "نقشه درختی زمان‌ها، جملات مجهول، شرطی، موصولی و Writing امتحان نهایی",
        listOf(
            VisualTreeBranchData(
                branchTitle = "شاخه ۱: پایه هفتم تا نهم (فرمول قطار ۳ واگنه جمله انگلیسی)",
                branchBadge = "پایه هفتم تا نهم",
                branchColor = Color(0xFFEA580C),
                leaves = listOf(
                    "واگن ۱: Subject (فاعل: I, You, He, She, It, We, They)",
                    "واگن ۲: Verb (فعل کمکی + فعل اصلی: am/is/are, was/were, do/does/did)",
                    "واگن ۳: Object + Place + Time (مفعول + مکان + زمان)"
                )
            ),
            VisualTreeBranchData(
                branchTitle = "شاخه ۲: زبان دهم (Vision 1 — زمان‌ها، صفات و افعال کمکی)",
                branchBadge = "پایه دهم",
                branchColor = Color(0xFF2563EB),
                leaves = listOf(
                    "آینده: will + V ساده vs be going to + V ساده",
                    "صفت برتر (-er than / more ... than) و صفت عالی (the -est / the most)",
                    "ضمایر انعکاسی: myself, yourself, himself, herself, themselves"
                )
            ),
            VisualTreeBranchData(
                branchTitle = "شاخه ۳: زبان یازدهم (Vision 2 — ماضی نقلی، شرطی نوع ۱ و مصدر/اسم مصدر)",
                branchBadge = "پایه یازدهم",
                branchColor = Color(0xFF059669),
                leaves = listOf(
                    "Present Perfect: have/has + P.P (با رمز SFJAYELR: since, for, just, yet)",
                    "شرطی نوع ۱: If + حال ساده , will + فعل ساده",
                    "رمز WDPAHPW (want, decide, plan, hope, promise) ⟹ to + Verb",
                    "رمز EMFAKSI (enjoy, mind, finish, avoid, keep) + حروف اضافه ⟹ V-ing"
                )
            ),
            VisualTreeBranchData(
                branchTitle = "شاخه ۴: زبان دوازدهم (Vision 3 — مجهول، Tag، شرطی نوع ۲ و موصولی)",
                branchBadge = "پایه دوازدهم و نهایی",
                branchColor = Color(0xFF7C3AED),
                leaves = listOf(
                    "مجهول (Passive): Object + am/is/are/was/were/have been + P.P + by",
                    "Tag Questions: جمله مثبت ⟹ تگ منفی | جمله منفی ⟹ تگ مثبت",
                    "ضمایر موصولی: who (انسان فاعل) | whom (انسان مفعول) | which (اشیاء/حیوانات)",
                    "شرطی نوع ۲: If + گذشته ساده (were) , would + فعل ساده"
                )
            )
        )
    )

    "arooz" -> Triple(
        "نمودار درختی جامع عروض و قافیه (دهم، یازدهم و دوازدهم انسانی)",
        "مسیر درختی از هجاخوانی تا تشخیص وزن در ۱۰ ثانیه و اختیارات شاعری",
        listOf(
            VisualTreeBranchData(
                branchTitle = "شاخه ۱: پایه عروض — ۳ نوع هجا و خط عروضی",
                branchBadge = "پایه دهم",
                branchColor = Color(0xFFDB2777),
                leaves = listOf(
                    "هجای کوتاه (U): ۱ صامت + ۱ مصوت کوتاه (بَ، دِ، تُ)",
                    "هجای بلند (–): صامت + مصوت بلند (با، بو، بی) یا ۳ حرفی (بَند، دِل)",
                    "حذف همزه (وصل صامت آخر به مصوت آغاز کلمه بعد: مَن اَز ⟹ مَ نَز)"
                )
            ),
            VisualTreeBranchData(
                branchTitle = "شاخه ۲: قافیه و ردیف (قواعد ۱ و ۲ قافیه)",
                branchBadge = "علوم و فنون ۱",
                branchColor = Color(0xFF7C3AED),
                leaves = listOf(
                    "ردیف: کلمه تکراری با معنی و لفظ یکسان در پایان مصراع‌ها",
                    "قاعده ۱ قافیه: ختم به مصوت بلند (آ ، او ، ای)",
                    "قاعده ۲ قافیه: ختم به مصوت + ۱ یا ۲ صامت (مثل یار/کار یا دشت/گشت)"
                )
            ),
            VisualTreeBranchData(
                branchTitle = "شاخه ۳: جدول درختی ۴ بحر اصلی و اوزان دوری",
                branchBadge = "فنون ۲ و ۳",
                branchColor = Color(0xFF2563EB),
                leaves = listOf(
                    "بحر هزج (کوتاه در ۱): U – – – (مفاعیلن)",
                    "بحر رمل (کوتاه در ۲): – U – – (فاعلاتن) و U U – – (فعلاتن)",
                    "بحر رجز (کوتاه در ۳): – – U – (مستفعلن)",
                    "بحر متقارب (حماسی): U – – (فعولن فعولن فعولن فعل)"
                )
            )
        )
    )

    "fonoon" -> Triple(
        "نمودار درختی جامع علوم و فنون ادبی (آرایه‌ها و تاریخ ادبیات)",
        "دسته‌بندی درختی آرایه‌های لفظی، بیانی، معنوی و ۳ سبک شعر فارسی",
        listOf(
            VisualTreeBranchData(
                branchTitle = "شاخه ۱: آرایه‌های لفظی (بدیع لفظی - دهم)",
                branchBadge = "فنون دهم",
                branchColor = Color(0xFF2563EB),
                leaves = listOf(
                    "سجع: متوازی (وزن و قافیه)، مطرّف (فقط قافیه)، متوازن (فقط وزن)",
                    "جناس: تام (یکسان با دو معنی) و ناقص (اختلاف در حرکت، حرف یا افزایشی)",
                    "موازنه و ترصیع: تقابل واژه‌های هم‌وزن یا هم‌سجع در دو مصراع"
                )
            ),
            VisualTreeBranchData(
                branchTitle = "شاخه ۲: ۴ ستون آرایه‌های بیانی (یازدهم)",
                branchBadge = "فنون یازدهم",
                branchColor = Color(0xFF7C3AED),
                leaves = listOf(
                    "تشبیه: هر دو طرف (مشبه و مشبه‌به) در بیت حاضرند",
                    "استعاره: یک طرف حذف شده + رابطه شباهت (شامل تشخیص/جان‌بخشی)",
                    "مجاز: کلمه در غیر معنی اصلی بدون شباهت (علاقه جزء، کل، محل، آلیت)",
                    "کنایه: عبارت با معنی دور و ضرب‌المثلی (مثل دهان به دندان گزیدن)"
                )
            ),
            VisualTreeBranchData(
                branchTitle = "شاخه ۳: آرایه‌های معنوی و سبک‌شناسی (دوازدهم)",
                branchBadge = "فنون دوازدهم",
                branchColor = Color(0xFFD97706),
                leaves = listOf(
                    "ایهام (۱ واژه با ۲ معنی درست) | پارادوکس (متناقض‌نما) | حس‌آمیزی",
                    "اسلوب معادله (دو مصراع مستقل با «همان‌طور که» — امضای سبک هندی)",
                    "۳ سبک: خراسانی (رُفَفَم - قرن ۴ و ۵) | عراقی (سَمَحَع - قرن ۶ تا ۹) | هندی (صَکَبَع - قرن ۱۰ تا ۱۲)"
                )
            )
        )
    )

    "history" -> Triple(
        "نمودار درختی جامع تاریخ ۱، ۲ و ۳ انسانی",
        "دسته‌بندی درختی تاریخ ایران و جهان (چاپ ۱۴۰۳ تا ۱۴۰۵)",
        listOf(
            VisualTreeBranchData(
                branchTitle = "شاخه ۱: تاریخ پایه دهم (ایران باستان)",
                branchBadge = "دهم انسانی",
                branchColor = Color(0xFFD97706),
                leaves = listOf(
                    "ماد (دیاکو)، هخامنشی (کورش و داریوش، ساتراپ، چاپارخانه)",
                    "اشکانی (مهستان، مهرداد) و ساسانی (اردشیر، انوشیروان، دین رسمی زرتشت)"
                )
            ),
            VisualTreeBranchData(
                branchTitle = "شاخه ۲: تاریخ پایه یازدهم (اسلام و ایران تا صفویه)",
                branchBadge = "یازدهم انسانی",
                branchColor = Color(0xFF2563EB),
                leaves = listOf(
                    "طاهریان، سامانیان، غزنویان، سلجوقیان (خواجه نظام‌الملک و نظامیه)",
                    "حمله مغول، ایلخانان، تیموریان و صفویه (شاه اسماعیل و شاه عباس)"
                )
            ),
            VisualTreeBranchData(
                branchTitle = "شاخه ۳: تاریخ پایه دوازدهم (معاصر ایران و جهان)",
                branchBadge = "دوازدهم انسانی",
                branchColor = Color(0xFF059669),
                leaves = listOf(
                    "قاجار: عهدنامه گلستان، ترکمانچای، پاریس، اصلاحات امیرکبیر، نهضت تنباکو و مشروطه",
                    "پهلوی، ملی شدن صنعت نفت، انقلاب اسلامی و جنگ‌های جهانی اول و دوم"
                )
            )
        )
    )

    "geography" -> Triple(
        "نمودار درختی جامع جغرافیا ۱، ۲ و ۳ انسانی",
        "دسته‌بندی درختی جغرافیای ایران، ناحیه‌ای و کاربردی (چاپ ۱۴۰۳ تا ۱۴۰۵)",
        listOf(
            VisualTreeBranchData(
                branchTitle = "شاخه ۱: جغرافیای پایه دهم (جغرافیای ایران)",
                branchBadge = "دهم انسانی",
                branchColor = Color(0xFF0891B2),
                leaves = listOf(
                    "مرزهای ایران: طولانی‌ترین مرز خشکی (عراق)، کوتاه‌ترین (ارمنستان)، رود کارون",
                    "تقسیمات کشوری: دهستان ⬅ بخش (بخشدار) ⬅ شهرستان (فرماندار) ⬅ استان (استاندار)"
                )
            ),
            VisualTreeBranchData(
                branchTitle = "شاخه ۲: جغرافیای پایه یازدهم (ناحیه‌ای)",
                branchBadge = "یازدهم انسانی",
                branchColor = Color(0xFF7C3AED),
                leaves = listOf(
                    "زیست‌بوم‌های جهان: استوایی (خاک لاتریتی)، بیابان مداری (پرفشار جنب حاره)، تایگا، توندرا",
                    "حقوق دریاها: دریای سرزمینی (۱۲ مایل دریایی) | فلات قاره و انحصاری اقتصادی (۲۰۰ مایل)"
                )
            ),
            VisualTreeBranchData(
                branchTitle = "شاخه ۳: جغرافیای پایه دوازدهم (کاربردی)",
                branchBadge = "دوازدهم انسانی",
                branchColor = Color(0xFFE11D48),
                leaves = listOf(
                    "مخاطرات و شهر: وارونگی دما، جزیره حرارتی، آمایش سرزمین (انسان، فضا، فعالیت)",
                    "سنجش از دور (RS) و سیستم اطلاعات جغرافیایی (GIS)"
                )
            )
        )
    )

    "economics" -> Triple(
        "نمودار درختی جامع اقتصاد پایه دهم انسانی",
        "دسته‌بندی درختی فرمول‌های مسائل و مفاهیم اقتصاد دهم",
        listOf(
            VisualTreeBranchData(
                branchTitle = "شاخه ۱: شگردهای ۵ ثانیه‌ای تمام مسائل اقتصاد دهم (چاپ ۱۴۰۳)",
                branchBadge = "اقتصاد ۱۰۰٪",
                branchColor = Color(0xFF059669),
                leaves = listOf(
                    "سود حسابداری = درآمد کل − هزینه آشکار | سود اقتصادی = سود حسابداری − هزینه پنهان (فرصت)",
                    "تعادل بازار: Qs = Qd | قیمت بالاتر از تعادل = مازاد عرضه | قیمت پایین‌تر = مازاد تقاضا",
                    "تولید ملی (GDP): حذف کالای واسطه‌ای (آرد) و فقط محاسبه ارزش کالای نهایی (نان) یا جمع ارزش افزوده‌ها",
                    "نرخ بیکاری = (بیکاران ÷ جمعیت فعال) × ۱۰۰ | مالیات مستقیم (درآمد/دارایی) vs غیرمستقیم (ارزش افزوده)"
                )
            )
        )
    )

    "psychology" -> Triple(
        "نمودار درختی جامع روان‌شناسی پایه یازدهم انسانی",
        "دسته‌بندی درختی رشد، حافظه، یادگیری و بهداشت روانی",
        listOf(
            VisualTreeBranchData(
                branchTitle = "شاخه ۱: روان‌شناسی یازدهم انسانی (چاپ ۱۴۰۴)",
                branchBadge = "روان‌شناسی ۱۱",
                branchColor = Color(0xFF2563EB),
                leaves = listOf(
                    "۴ مرحله رشد پیاژه: ۱. حسی-حرکتی (پایداری شیء) ۲. پیش‌عملیاتی ۳. عینی (نگهداری ذهنی) ۴. صوری",
                    "حافظه حسی ⬅ کوتاه‌مدت (۷±۲ قطعه با تقطیع) ⬅ بلندمدت (رویدادی، معنایی، روندی)",
                    "تداخل پیش‌گستر (فراموشی مطلب جدید) vs تداخل پس‌گستر (فراموشی مطلب قدیم)"
                )
            )
        )
    )

    "sociology" -> Triple(
        "نمودار درختی جامع جامعه‌شناسی ۱، ۲ و ۳ انسانی",
        "دسته‌بندی درختی مفاهیم و ۳ رویکرد جامعه‌شناسی",
        listOf(
            VisualTreeBranchData(
                branchTitle = "شاخه ۱: جامعه‌شناسی ۱، ۲ و ۳ (چاپ ۱۴۰۳ تا ۱۴۰۵)",
                branchBadge = "جامعه‌شناسی ۱-۳",
                branchColor = Color(0xFF7C3AED),
                leaves = listOf(
                    "جامعه‌شناسی ۱: کنش (آگاهانه، ارادی، هدفدار، معنادار) + فرهنگ (نماد، هنجار، ارزش، عقاید) + هویت",
                    "جامعه‌شناسی ۲: دنیوی‌شدن (سکولاریسم)، استعمار کهن/نو/فرانو، بیداری اسلامی",
                    "جامعه‌شناسی ۳: رویکرد تبیینی (کنت/دورکیم)، تفسیری (وبر)، انتقادی (فرانکفورت) و اسلامی"
                )
            )
        )
    )

    else -> Triple(
        "نمودار درختی قبولی قطعی دانشگاه فرهنگیان ۱۴۰۶",
        "ساختار درختی ۳ ضلع تراز کل و منابع آزمون اختصاصی تربیت معلم",
        listOf(
            VisualTreeBranchData(
                branchTitle = "ضلع ۱: سوابق تحصیلی امتحانات نهایی (۶۰٪ نمره کل)",
                branchBadge = "۶۰٪ سوابق",
                branchColor = Color(0xFF059669),
                leaves = listOf(
                    "دروس عمومی نهایی (فارسی، عربی، دینی، زبان انگلیسی)",
                    "دروس تخصصی نهایی انسانی (ریاضی، فنون، فلسفه، تاریخ، جغرافیا، جامعه‌شناسی)"
                )
            ),
            VisualTreeBranchData(
                branchTitle = "ضلع ۲: مصاحبه و گزینش فرهنگیان (شرط قبولی، بدون سهم در تراز)",
                branchBadge = "شرط قبولی",
                branchColor = Color(0xFFD97706),
                leaves = listOf(
                    "مهارت‌های معلمی استاد قرائتی (۳ فصل اول: رمز قـحـب)",
                    "سند تحول بنیادین (۶ ساحت تربیتی با رمز اعـاجـز زیـعـا)",
                    "دین و زندگی فرهنگیان + هوش و استعداد معلمی + مصاحبه کانون ارزیابی"
                )
            ),
            VisualTreeBranchData(
                branchTitle = "ضلع ۳: کنکور اختصاصی علوم انسانی (۴۰٪ نمره کل)",
                branchBadge = "۴۰٪ کنکور",
                branchColor = Color(0xFF2563EB),
                leaves = listOf(
                    "زبان و ادبیات فارسی/فنون (ضریب ۸)، ریاضی (ضریب ۶)",
                    "عربی، تاریخ و جغرافیا، علوم اجتماعی، فلسفه و منطق (ضریب ۵)؛ اقتصاد و روان‌شناسی (ضریب ۲)"
                )
            )
        )
    )
}

private fun getVisualTitleForLesson(lessonId: String, subjectId: String): String = when {
    lessonId.contains("math_00") || lessonId.contains("math_01") ->
        "انیمیشن، جدول و درخت مجموعه‌های اعداد (ℕ, 𝕎, ℤ, ℚ, ℝ)"
    subjectId == "math" ->
        "شبیه‌ساز گرافیکی سهمی، معادله، تابع و دنباله"
    lessonId.contains("arooz_00") ->
        "انیمیشن دست‌زدن و بخش‌کردن کلمات (پایه اول دبستان تا عروض)"
    subjectId == "arooz" ->
        "انیمیشن بارکد ۴ هجایی عروض + جدول کامل بحرها"
    subjectId == "fonoon" ->
        "انیمیشن ۴ ستون آرایه‌های ادبی + جدول سبک‌شناسی"
    subjectId == "arabic" ->
        "انیمیشن قفل سر و دم فعل + جدول رنگی ۳ اتاق ۱۴ صیغه"
    subjectId == "english" ->
        "انیمیشن قطار ۳ واگنه جمله انگلیسی (از چپ به راست)"
    lessonId.contains("phil_05") || lessonId.contains("phil_06") ||
        lessonId.contains("philo_05") || lessonId.contains("philo_06") ->
        "انیمیشن مراتب نور وجود و ماهیت (فلسفه دوازدهم)"
    subjectId == "philosophy" ->
        "انیمیشن مراتب وجود و فلسفه ۱ و ۲ + جدول مکاتب"
    subjectId == "logic" ->
        "انیمیشن مربع تقابل منطق دهم + جدول تعریف و قیاس"
    subjectId == "sociology" ->
        "انیمیشن لایه‌های فرهنگ و کنش + جدول ۳ رویکرد جامعه‌شناسی"
    subjectId == "psychology" ->
        "انیمیشن مراحل رشد پیاژه، حافظه و شرطی‌سازی روان‌شناسی ۱۱"
    subjectId == "economics" ->
        "انیمیشن حل چشمی مسائل اقتصاد دهم (سود، تعادل بازار، GDP)"
    subjectId == "social_psych_econ" ->
        "انیمیشن حل چشمی مسائل اقتصاد + جدول روان‌شناسی و جامعه‌شناسی"
    subjectId == "history" ->
        "انیمیشن تبدیل سال به قرن (+۱) + جدول سلسله‌ها و عهدنامه‌های تاریخ"
    subjectId == "geography" ->
        "انیمیشن اختلاف ساعت نصف‌النهارها + جدول حوضه‌های آبریز و زیست‌بوم‌ها"
    subjectId == "history_geo" ->
        "انیمیشن تبدیل قرن و ساعت جغرافیایی + خط زمانی تاریخ ایران"
    subjectId == "dini" ->
        "انیمیشن و جدول جامع دین و زندگی ۱، ۲ و ۳ (توحید، معاد، امامت)"
    subjectId == "farsi" ->
        "انیمیشن و جدول دستور زبان فارسی، واژه‌شناسی و نگارش ۲۰ نهایی"
    subjectId == "general_humanities" ->
        "جدول گرافیکی سلامت ۱۲، هویت ۱۲، محیط زیست ۱۱ و سواد رسانه‌ای ۱۰"
    else ->
        "اینفوگرافیک و جدول قبولی دانشگاه فرهنگیان ۱۴۰۶"
}

private fun getVisualCaptionForLesson(lessonId: String, subjectId: String): String = when (subjectId) {
    "math" -> "💡 راهنما: روی مرحله ۱ تا ۴ کلیک کن یا بین تب‌های «🎬 انیمیشن شکل»، «📋 جدول رنگی» و «🌳 نمودار درختی» جابه‌جا شو."
    "arooz" -> "💡 راهنما: روی مرحله ۱ تا ۴ بزن تا بخش‌بخش شدن کلمات و بارکد وزن را با انیمیشن ببینی."
    "fonoon" -> "💡 راهنما: هر ستون آرایه را انتخاب کن تا فرمول تشخیص ۲ ثانیه‌ای و مثال آن باز شود."
    "arabic" -> "💡 راهنما: در تب «📋 جدول رنگی» می‌توانی ریشه فعل (ذَهَبَ، کَتَبَ، عَلِمَ) را عوض کنی و هر ۱۴ صیغه را ببینی!"
    "english" -> "💡 راهنما: قطار انگلیسی از چپ به راست حرکت می‌کند؛ روی مراحل بزن تا تبدیل معلوم به مجهول و شرطی را ببینی."
    "philosophy" -> "💡 راهنما: روی مراحل ۱ تا ۴ کلیک کن تا مراتب وجود، علیت و مکاتب فلسفی روشن شوند."
    "logic" -> "💡 راهنما: روی مراحل ۱ تا ۴ مربع تقابل کلیک کن تا تناقض (ضربدر)، تضاد (سقف) و تداخل روشن شوند."
    "economics" -> "💡 راهنما: روی مراحل ۱ تا ۴ بزن تا فرمول‌های تصویری سود اقتصادی، تعادل بازار و تولید ملی (GDP) را ببینی."
    "psychology" -> "💡 راهنما: روی مراحل ۱ تا ۴ بزن تا مراحل رشد پیاژه، حافظه و شرطی‌سازی را ببینی."
    "sociology" -> "💡 راهنما: روی مراحل ۱ تا ۴ بزن تا لایه‌های فرهنگ، کنش اجتماعی و رویکردهای جامعه‌شناسی را ببینی."
    "history" -> "💡 راهنما: روی مراحل ۱ تا ۴ بزن تا فرمول تبدیل سال به قرن (+۱) و خط زمانی سلسله‌ها را ببینی."
    "geography" -> "💡 راهنما: روی مراحل ۱ تا ۴ بزن تا اختلاف ساعت نصف‌النهارها و نقشه مرزها و زیست‌بوم‌ها را ببینی."
    else -> "💡 راهنما (قاعده ۱۴۰۶): ۶۰٪ نمره کل از سوابق تحصیلی (امتحانات نهایی) + ۴۰٪ از کنکور سراسری؛ مصاحبه فرهنگیان فقط شرط قبولی است."
}

/**
 * 8. WORKED EXAMPLE DEDICATED VISUAL DIAGRAM (شکل گرافیکی اختصاصی برای هر مثال حل‌شده)
 * Turns every worked example into a 3-node interactive visual flowchart + subject-specific visual schematic!
 */
@Composable
fun WorkedExampleVisualDiagram(
    lessonId: String,
    subjectId: String,
    exampleIndex: Int,
    question: String,
    solution: String,
    trapAlert: String,
    accentColor: Color
) {
    var activeNode by remember(lessonId, exampleIndex) { mutableIntStateOf(0) }

    val solutionSteps = remember(solution) {
        solution
            .lines()
            .map { it.trim() }
            .filter { it.isNotEmpty() }
    }

    val firstStep = solutionSteps.firstOrNull() ?: "تشخیص کلیدواژه در صورت سوال"
    val finalStep = solutionSteps.lastOrNull() ?: "رسیدن به پاسخ قطعی"

    val subjectBadgeTitle = remember(subjectId) {
        when (subjectId) {
            "math" -> "📐 شماتیک تصویری ماشین حل مسئله ریاضی"
            "arooz" -> "🎵 شماتیک تصویری تقطیع و بارکد وزن مثال"
            "fonoon" -> "🎨 شماتیک تصویری ترازوی تشخیص آرایه و سبک"
            "arabic" -> "🔍 شماتیک تصویری رادار سر و دم کلمه عربی"
            "english" -> "🚂 شماتیک تصویری قطار گرامر و کلیدواژه انگلیسی"
            "philosophy" -> "⚖️ شماتیک تصویری استدلال فلسفه ۱ و ۲"
            "logic" -> "⚖️ شماتیک تصویری مربع تقابل و قیاس منطق دهم"
            "sociology" -> "🏛️ شماتیک تصویری شکار رویکردهای جامعه‌شناسی"
            "psychology" -> "🧠 شماتیک تصویری حافظه، رشد و شرطی‌سازی روان‌شناسی"
            "economics" -> "💰 شماتیک تصویری حل ۵ ثانیه‌ای مسئله اقتصاد"
            "social_psych_econ" -> "💰 شماتیک تصویری حل سریع اقتصاد، روان‌شناسی و جامعه‌شناسی"
            "history" -> "🏛️ شماتیک تصویری خط زمانی و عهدنامه‌های تاریخ"
            "geography" -> "🗺️ شماتیک تصویری نقشه، مقیاس و ساعت جغرافیایی"
            "history_geo" -> "🗺️ شماتیک تصویری قرن، نقشه و کلیدواژه تاریخ و جغرافیا"
            "dini" -> "🕌 شماتیک تصویری پیام آیه و توحید دین و زندگی"
            "farsi" -> "✍️ شماتیک تصویری کالبدشکافی دستور و گروه اسمی فارسی"
            "general_humanities" -> "🛡️ شماتیک تصویری سلامت، هویت، محیط زیست و رسانه"
            else -> "🎓 شماتیک تصویری حکم قطعی تربیت معلم و فرهنگیان"
        }
    }

    Surface(
        color = accentColor.copy(alpha = 0.07f),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.5.dp, accentColor.copy(alpha = 0.32f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Surface(
                        color = accentColor,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = BidiFormatter.formatMixedPersianText("🖼️ شکل مثال ${exampleIndex + 1}"),
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Text(
                        text = BidiFormatter.formatMixedPersianText(subjectBadgeTitle),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = accentColor
                    )
                }
            }

            // 3 Interactive Visual Nodes (۱. شکار سرنخ -> ۲. موتور فرمول -> ۳. شلیک به گزینه)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                val nodes = listOf(
                    Triple("۱. شکار سرنخ", "🔎 ورودی سوال", Color(0xFF2563EB)),
                    Triple("۲. قفل فرمول", "⚙️ گام‌های حل", accentColor),
                    Triple("۳. جواب قطعی", "🏆 ضد تله کنکور", Color(0xFF059669))
                )
                nodes.forEachIndexed { idx, (title, sub, col) ->
                    val selected = activeNode == idx
                    Surface(
                        color = if (selected) col else col.copy(alpha = 0.14f),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(if (selected) 2.dp else 1.dp, col.copy(alpha = 0.45f)),
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { activeNode = idx }
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(
                                text = BidiFormatter.formatMixedPersianText(title),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.ExtraBold,
                                color = if (selected) Color.White else col,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = BidiFormatter.formatMixedPersianText(sub),
                                style = MaterialTheme.typography.labelSmall,
                                color = if (selected) Color.White.copy(alpha = 0.9f) else MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            // Subject-Specific Responsive Visual Graphic inside the Worked Example (never clips text!)
            when (subjectId) {
                "arooz" -> {
                    val patternTokens = remember(solution) {
                        val rawTokens = solution.filter { it == 'U' || it == '–' || it == '-' }
                        if (rawTokens.length >= 4) {
                            rawTokens.take(8).map { if (it == 'U') "U" else "–" }
                        } else {
                            listOf("U", "–", "–", "–")
                        }
                    }
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = BidiFormatter.formatMixedPersianText("🎵 بارکد چشمی تقطیع این مثال (از راست به چپ):"),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = accentColor
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                patternTokens.forEach { sym ->
                                    val isLong = sym == "–"
                                    Surface(
                                        color = if (isLong) accentColor else Color(0xFF0284C7),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            modifier = Modifier.padding(vertical = 6.dp)
                                        ) {
                                            Text(
                                                text = sym,
                                                color = Color.White,
                                                fontWeight = FontWeight.ExtraBold,
                                                textAlign = TextAlign.Center
                                            )
                                            Text(
                                                text = if (isLong) "بلند" else "کوتاه",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = Color.White.copy(alpha = 0.9f)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                "english" -> {
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    color = Color(0xFF2563EB).copy(alpha = 0.14f),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    BiDiText(
                                        text = "۱. شکار نشانه زمان یا ساختار در جمله",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color(0xFF2563EB),
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                                Text("⬅", color = accentColor, fontWeight = FontWeight.ExtraBold)
                                Surface(
                                    color = Color(0xFF059669).copy(alpha = 0.14f),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    BiDiText(
                                        text = "۲. قفل کردن فاعل با شکل درست فعل",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color(0xFF059669),
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                else -> {
                    // Responsive Step-by-Step Visual Flow Cards (Stacked on mobile so formulas & numbers never get cut off!)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        solutionSteps.forEachIndexed { idx, stepLine ->
                            val isLast = idx == solutionSteps.lastIndex
                            val stepColor = when {
                                idx == 0 -> Color(0xFF2563EB)
                                isLast -> Color(0xFF059669)
                                else -> accentColor
                            }
                            Surface(
                                color = stepColor.copy(alpha = 0.10f),
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(1.dp, stepColor.copy(alpha = 0.30f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Surface(
                                        color = stepColor,
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = BidiFormatter.formatMixedPersianText(
                                                if (isLast && solutionSteps.size > 1) "✅ نتیجه" else "گام ${idx + 1}"
                                            ),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = Color.White,
                                            fontWeight = FontWeight.ExtraBold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                        )
                                    }
                                    BiDiText(
                                        text = stepLine.replace("->", " ⟹ "),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Active Node Detail Box
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, accentColor.copy(alpha = 0.25f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                val activeText = when (activeNode) {
                    0 -> "🔎 گام ۱ (شکار سرنخ از صورت سوال): $question\n• اول از همه دور کلیدواژهٔ اصلی خط بکش: $firstStep"
                    1 -> "⚙️ گام ۲ (تبدیل به لقمهٔ تصویری):\n${solutionSteps.joinToString("\n• ", prefix = "• ")}"
                    else -> "🏆 گام ۳ (پاسخ قطعی + سپر ضد تله): $finalStep\n⚠️ هشدار تله: $trapAlert"
                }
                BiDiText(
                    text = activeText,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}

/**
 * 9. INVENTIVE SLOW-LEARNER 100% MASTERY COACH CARD (متد اختراعی ویژه دیرآموز برای ۱۰۰ زدن بدون فراموشی)
 * Breaks every lesson into:
 * Tab 0: 🧩 لقمه ۳ ثانیه‌ای (فرمول تک‌خطی بدون فرمول‌نویسی پیچیده)
 * Tab 1: 🎬 قصر حافظه و کارتون ذهنی (تصویرسازی داستانی ضد فراموشی)
 * Tab 2: ⚡ رادار چشم‌خوانی (این کلمه را دیدی = این گزینه را بزن!)
 * Tab 3: 🎮 قفل حافظه بلندمدت (تیک زدن لقمه‌ها تا رسیدن به ۱۰۰٪ تسلط)
 */
@Composable
fun SlowLearnerSuperMethodCard(
    lesson: ZeroLesson,
    accentColor: Color
) {
    var selectedMode by remember(lesson.id) { mutableIntStateOf(0) }
    val lockedItems = remember(lesson.id) { mutableStateListOf<Int>() }

    val modes = listOf(
        "🧩 لقمه ۳ ثانیه‌ای",
        "🎬 داستان تصویری",
        "⚡ دیدن = زدن",
        "🎮 قفل ۱۰۰٪ حافظه"
    )

    val totalChunks = lesson.steps.size.coerceAtLeast(1)
    val masteryRatio = (lockedItems.size.toFloat() / totalChunks).coerceIn(0f, 1f)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("slow_learner_super_method_card"),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            accentColor.copy(alpha = 0.14f),
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
                .border(1.5.dp, accentColor.copy(alpha = 0.38f), RoundedCornerShape(22.dp))
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Top Title Banner
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Surface(
                        color = accentColor,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "🚀 متد اختراعی دیرآموز (تضمین ۱۰۰٪ کنکور)",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = BidiFormatter.formatMixedPersianText("یادگیری بدون فرمول‌های سنگین: با تصویر، داستان و شکار کلیدواژه!"),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Surface(
                    color = Color(0xFF059669).copy(alpha = 0.15f),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color(0xFF059669))
                ) {
                    Text(
                        text = BidiFormatter.formatMixedPersianText("تثبیت: ${(masteryRatio * 100).toInt()}٪"),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF059669),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }

            // 4 Interactive Mode Tabs (Sleek Horizontal Scroll Row)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                modes.forEachIndexed { idx, title ->
                    val isSelected = selectedMode == idx
                    Surface(
                        color = if (isSelected) accentColor else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(
                            width = if (isSelected) 1.dp else 0.5.dp,
                            color = if (isSelected) accentColor else MaterialTheme.colorScheme.outlineVariant
                        ),
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { selectedMode = idx }
                    ) {
                        Text(
                            text = BidiFormatter.formatMixedPersianText(title),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(vertical = 6.dp, horizontal = 10.dp)
                        )
                    }
                }
            }

            // Mode Content
            when (selectedMode) {
                0 -> {
                    // Tab 0: لقمه ۳ ثانیه‌ای (Ultra-short 1-line digest of each step)
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            BiDiText(
                                text = "💡 قانون دیرآموز: خلاصه کلیدی و ۱ خطی هر بخش را به خاطر بسپار:",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        lesson.steps.forEachIndexed { idx, step ->
                            val colors = listOf(Color(0xFF2563EB), Color(0xFF059669), Color(0xFFD97706), Color(0xFF7C3AED))
                            val stepCol = colors[idx % colors.size]
                            Surface(
                                color = stepCol.copy(alpha = 0.09f),
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, stepCol.copy(alpha = 0.35f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(10.dp),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = BidiFormatter.formatMixedPersianText("لقمه ${idx + 1}: ${step.title}"),
                                        style = MaterialTheme.typography.labelLarge,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = stepCol
                                    )
                                    BiDiText(
                                        text = if (step.memoryTrick.isNotBlank()) "🎯 خلاصه ۱ خطی: ${step.memoryTrick}" else "🎯 خلاصه ۱ خطی: ${step.simpleExplanation.substringBefore(".")}.",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }

                1 -> {
                    // Tab 1: قصر حافظه و داستان تصویری (Visual Story & Everyday Analogy)
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Surface(
                            color = Color(0xFF7C3AED).copy(alpha = 0.12f),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            BiDiText(
                                text = "🎬 تکنیک قصر حافظه (Memory Palace): چشم‌هایت را ۲ ثانیه ببند و این صحنه‌های کارتونی را در ذهنت مجسم کن تا تا روز کنکور یادت نرود:",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF7C3AED),
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                        lesson.steps.forEachIndexed { idx, step ->
                            Surface(
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, Color(0xFF7C3AED).copy(alpha = 0.3f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(10.dp),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = BidiFormatter.formatMixedPersianText("🖼️ صحنه کارتونی گام ${idx + 1}:"),
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color(0xFF7C3AED)
                                    )
                                    BiDiText(
                                        text = step.simpleExplanation,
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.Medium
                                    )
                                    if (step.memoryTrick.isNotBlank()) {
                                        Surface(
                                            color = Color(0xFFD97706).copy(alpha = 0.14f),
                                            shape = RoundedCornerShape(8.dp),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            BiDiText(
                                                text = "🔑 رمز تصویری: ${step.memoryTrick}",
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.ExtraBold,
                                                color = Color(0xFFB45309),
                                                modifier = Modifier.padding(8.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                2 -> {
                    // Tab 2: رادار دیدن = زدن (5-second Keyword -> Answer Reflex)
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Surface(
                            color = Color(0xFF059669).copy(alpha = 0.12f),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            BiDiText(
                                text = "⚡ تکنیک «دیدن = زدن» در ۵ ثانیه: در جلسه کنکور لازم نیست کل صورت سوال را تحلیل کنی؛ به محض دیدن نشانهٔ سمت راست، حکم سمت چپ را بزن!",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF059669),
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                        lesson.goldenFormulas.forEachIndexed { idx, formula ->
                            Surface(
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, Color(0xFF059669).copy(alpha = 0.35f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Surface(
                                        color = Color(0xFF059669),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text(
                                            text = BidiFormatter.formatMixedPersianText("کلید ${idx + 1}"),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = Color.White,
                                            fontWeight = FontWeight.ExtraBold,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                    BiDiText(
                                        text = formula,
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }
                    }
                }

                else -> {
                    // Tab 3: قفل ۱۰۰٪ حافظه بلندمدت (Interactive Check-off Game for Slow Learners)
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Surface(
                            color = Color(0xFFD97706).copy(alpha = 0.14f),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            BiDiText(
                                text = "🎮 بازی قفل حافظه: روی هر گامی که یاد گرفتی ضربه بزن تا سبز شود و در حافظه بلندمدتت قفل شود! هدفت رسیدن به ۱۰۰٪ است:",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFB45309),
                                modifier = Modifier.padding(10.dp)
                            )
                        }

                        LinearProgressIndicator(
                            progress = { masteryRatio },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                                .clip(RoundedCornerShape(5.dp)),
                            color = Color(0xFF059669),
                            trackColor = Color(0xFF059669).copy(alpha = 0.15f)
                        )

                        lesson.steps.forEachIndexed { idx, step ->
                            val isLocked = lockedItems.contains(idx)
                            Surface(
                                color = if (isLocked) Color(0xFF059669).copy(alpha = 0.15f) else MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(
                                    width = if (isLocked) 2.dp else 1.dp,
                                    color = if (isLocked) Color(0xFF059669) else accentColor.copy(alpha = 0.3f)
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable {
                                        if (isLocked) lockedItems.remove(idx) else lockedItems.add(idx)
                                    }
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = BidiFormatter.formatMixedPersianText("گام ${idx + 1}: ${step.title}"),
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = if (isLocked) Color(0xFF059669) else MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = BidiFormatter.formatMixedPersianText(
                                                if (isLocked) "✅ عالی! این لقمه در حافظه‌ات قفل شد." else "👆 بعد از خواندن این لقمه، اینجا بزن تا قفل شود"
                                            ),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = if (isLocked) Color(0xFF059669) else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    Surface(
                                        color = if (isLocked) Color(0xFF059669) else accentColor.copy(alpha = 0.15f),
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Text(
                                            text = if (isLocked) "قفل شد ✓" else "تثبیت کن",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isLocked) Color.White else accentColor,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
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
}

data class ChartFamilyInfo(
    val id: Int,
    val nameFa: String,
    val typeDescriptorFa: String,
    val konkoorApplication: String,
    val shortcutTip: String,
    val color: Color
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FifteenChartFamiliesGalleryCard(
    modifier: Modifier = Modifier
) {
    val chartFamilies = remember {
        listOf(
            ChartFamilyInfo(1, "نمودار ستونی", "میله‌ای مقایسه‌ای", "مقایسه تولید ناخالص داخلی (GDP) در اقتصاد دهم و توزیع فراوانی داده‌های منفصل آمار", "ارتفاع ستون = مقدار متغیر؛ سریع‌ترین راه مقایسه مقادیر گسسته در ۱۰ ثانیه!", Color(0xFF3B82F6)),
            ChartFamilyInfo(2, "نمودار خطی", "روند زمانی پیوسته", "سری‌های زمانی ریاضی و آمار ۳، تغییرات نرخ تورم و رشد جمعیت در جغرافیا", "شیب صعودی = رشد مثبت؛ شیب نزولی = افت؛ نقطه عطف = تغییر روند کلیدی!", Color(0xFF10B981)),
            ChartFamilyInfo(3, "نمودار دایره‌ای", "قطاعی نسبتی ۳۶۰ درجه", "توزیع درصد سهم بازار در اقتصاد، ترکیب نیروهای کار و سهم حافظه در روان‌شناسی", "مجموع زوایا = ۳۶۰ درجه؛ درصد هر بخش = (زاویه / ۳۶۰) × ۱۰۰٪!", Color(0xFFF59E0B)),
            ChartFamilyInfo(4, "نمودار میله‌ای افقی", "مقایسه سطحی طولی", "رتبه‌بندی هزینه‌های صریح و فرصت در اقتصاد و مقایسه متغیرها با کلمات طولانی", "مناسب برچسب‌های متنی بلند؛ طراحان کنکور برای رتبه‌بندی کشوری از آن استفاده می‌کنند.", Color(0xFF8B5CF6)),
            ChartFamilyInfo(5, "نمودار پراکندگی", "پراکنش نقطه‌ای همبستگی", "بررسی همبستگی مستقیم و معکوس بین دو متغیر در آمار دهم و روان‌شناسی", "نقاط متمایل به بالا و راست = همبستگی مثبت (مستقیم)؛ متمایل به پایین = همبستگی منفی (معکوس)!", Color(0xFFEC4899)),
            ChartFamilyInfo(6, "نمودار هیستوگرام", "توزیع فراوانی پیوسته", "دسته‌بندی داده‌های پیوسته و نمایش فراوانی گروه‌های سنی یا درصدهای امتحانات نهایی", "ستون‌های چسبیده به هم نشان‌دهنده پیوستگی متغیرها هستند؛ مساحت ستون = فراوانی دسته!", Color(0xFF06B6D4)),
            ChartFamilyInfo(7, "نمودار مساحتی", "سطحی انباشته زیر خط", "نمایش تغییرات انباشته بودجه دولت، رشد کل تولید و منابع آب در جغرافیا", "فضای رنگی زیر خط = مقدار کل انباشته‌شده در طول زمان!", Color(0xFF14B8A6)),
            ChartFamilyInfo(8, "نمودار راداری", "چندضلعی ارزیابی شایستگی", "ارزیابی شایستگی‌های ۶ ساحت سند تحول بنیادین در مصاحبه و آزمون فرهنگیان", "شعاع‌های چندضلعی = ابعاد مختلف؛ مساحت بیشتر = شایستگی همه‌جانبه معلمی!", Color(0xFF6366F1)),
            ChartFamilyInfo(9, "نمودار حبابی", "حبابی سه‌متغیره", "مقایسه هم‌زمان ۳ متغیر (سود، ریسک و حجم سرمایه‌گذاری) در مسائل ریاضی اقتصاد", "مختصات مرکز حباب = دو متغیر اول؛ اندازه و قطر حباب = متغیر سوم (حجم/سرمایه)!", Color(0xFFD97706)),
            ChartFamilyInfo(10, "نمودار جعبه‌ای", "جعبه‌ای پنج‌عددی و چارک‌ها", "نمایش چارک اول (Q1)، میانه (Q2)، چارک سوم (Q3)، دامنه میان‌چارکی و داده‌های پرت آمار", "خط وسط جعبه = میانه (Q2)؛ عرض جعبه = IQR = Q3 - Q1؛ نقاط بیرون = داده‌های پرت!", Color(0xFFEF4444)),
            ChartFamilyInfo(11, "نمودار حرارتی", "طیفی شدت و تراکم", "تراکم جمعیت زیست‌بوم‌ها در جغرافیا و شدت استرس و هیجان در روان‌شناسی", "رنگ‌های گرم (قرمز/نارنجی) = تراکم و شدت بالا؛ رنگ‌های سرد (آبی) = شدت پایین!", Color(0xFFDC2626)),
            ChartFamilyInfo(12, "نمودار قیفی", "قیف ریزش و فیلترهای گزینش", "مراحل تبادل هنجاری، جامعه‌پذیری و فیلترهای ۴ مرحله‌ای کانون ارزیابی فرهنگیان", "عرض هر مرحله در طول مسیر کاهش می‌یابد؛ نشان‌دهنده ریزش و خروجی نهایی!", Color(0xFF9333EA)),
            ChartFamilyInfo(13, "نمودار درختی", "شاخه‌ای و پیش‌نیازها", "درختواره پیش‌نیازها، صیغه‌های ۱۴ گانه عربی، بحور عروضی و ساختار جامعه‌شناسی", "تقسیم کل به شاخه‌ها و برگ‌ها؛ ارتباط منطقی مفاهیم پایه با درسنامه!", Color(0xFF059669)),
            ChartFamilyInfo(14, "نمودار گانت", "گاه‌شمار افقی زمان‌بندی", "خط زمانی عهدنامه‌های تاریخ، سبک‌های ادبی و برنامه‌ریزی هوشمند مطالعه", "محور افقی = زمان (ساعت/روز)؛ نوارهای رنگی = تقدم و تاخر فعالیت‌ها!", Color(0xFF2563EB)),
            ChartFamilyInfo(15, "نمودار آبشاری", "مرحله‌ای سود و زیان", "تحلیل گام‌به‌گام سود ناخالص، هزینه‌های صریح، مالیات، استهلاک و سود ویژه در اقتصاد", "ستون‌های شناور مثبت و منفی تاثیر هر عامل را روی سود نهایی نشان می‌دهند!", Color(0xFF0284C7))
        )
    }

    var selectedIndex by remember { mutableIntStateOf(0) }
    val activeFamily = chartFamilies[selectedIndex]

    Surface(
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.5.dp, activeFamily.color.copy(alpha = 0.5f)),
        modifier = modifier
            .fillMaxWidth()
            .testTag("card_fifteen_chart_families")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = activeFamily.color,
                        modifier = Modifier.size(38.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.BarChart,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "📊 گالری و کارگاه ۱۵ خانواده بزرگ نمودارهای کنکور",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "تحلیل دیداری و تکنیک‌های تست‌زنی انواع نمودارها در کنکور و نهایی",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = activeFamily.color.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = "${selectedIndex + 1} از ۱۵",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = activeFamily.color,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))

            // Scrollable Chip Row of 15 Chart Families
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                chartFamilies.forEachIndexed { idx, item ->
                    val isSelected = idx == selectedIndex
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedIndex = idx },
                        label = {
                            Text(
                                text = "${item.id}. ${item.nameFa}",
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 12.sp
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = item.color,
                            selectedLabelColor = Color.White,
                            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                            labelColor = MaterialTheme.colorScheme.onSurface
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )
                }
            }

            // Visual Graphic Canvas Display for Active Chart
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = activeFamily.color.copy(alpha = 0.06f),
                border = BorderStroke(1.dp, activeFamily.color.copy(alpha = 0.3f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val w = size.width
                        val h = size.height
                        val c = activeFamily.color

                        when (activeFamily.id) {
                            1 -> { // Bar Chart
                                val barW = w / 7f
                                val heights = listOf(h * 0.4f, h * 0.7f, h * 0.5f, h * 0.9f, h * 0.6f)
                                heights.forEachIndexed { i, bh ->
                                    val x = (i + 1) * barW
                                    drawRoundRect(
                                        color = c.copy(alpha = 0.75f + (i * 0.05f)),
                                        topLeft = Offset(x, h - bh),
                                        size = Size(barW * 0.7f, bh),
                                        cornerRadius = CornerRadius(8f, 8f)
                                    )
                                }
                            }
                            2 -> { // Line Chart
                                val points = listOf(
                                    Offset(w * 0.1f, h * 0.8f),
                                    Offset(w * 0.3f, h * 0.4f),
                                    Offset(w * 0.5f, h * 0.6f),
                                    Offset(w * 0.7f, h * 0.2f),
                                    Offset(w * 0.9f, h * 0.5f)
                                )
                                val path = Path().apply {
                                    moveTo(points[0].x, points[0].y)
                                    points.drop(1).forEach { lineTo(it.x, it.y) }
                                }
                                drawPath(path, c, style = Stroke(width = 6f, cap = StrokeCap.Round))
                                points.forEach { drawCircle(c, radius = 10f, center = it) }
                            }
                            3 -> { // Pie Chart
                                val radius = Math.min(w, h) * 0.4f
                                val center = Offset(w / 2f, h / 2f)
                                drawArc(c, 0f, 120f, true, topLeft = Offset(center.x - radius, center.y - radius), size = Size(radius * 2, radius * 2))
                                drawArc(c.copy(alpha = 0.6f), 120f, 100f, true, topLeft = Offset(center.x - radius, center.y - radius), size = Size(radius * 2, radius * 2))
                                drawArc(c.copy(alpha = 0.35f), 220f, 140f, true, topLeft = Offset(center.x - radius, center.y - radius), size = Size(radius * 2, radius * 2))
                            }
                            4 -> { // Horizontal Bar Chart
                                val barH = h / 6f
                                val widths = listOf(w * 0.85f, w * 0.6f, w * 0.9f, w * 0.45f)
                                widths.forEachIndexed { i, bw ->
                                    val y = (i + 0.5f) * barH * 1.2f
                                    drawRoundRect(
                                        color = c.copy(alpha = 0.8f),
                                        topLeft = Offset(w * 0.05f, y),
                                        size = Size(bw, barH * 0.7f),
                                        cornerRadius = CornerRadius(6f, 6f)
                                    )
                                }
                            }
                            5 -> { // Scatter Plot
                                val dots = listOf(
                                    Offset(w * 0.15f, h * 0.75f), Offset(w * 0.25f, h * 0.65f),
                                    Offset(w * 0.4f, h * 0.5f), Offset(w * 0.55f, h * 0.35f),
                                    Offset(w * 0.7f, h * 0.3f), Offset(w * 0.85f, h * 0.15f)
                                )
                                dots.forEach { drawCircle(c, radius = 12f, center = it) }
                                drawLine(c.copy(alpha = 0.4f), Offset(w * 0.1f, h * 0.8f), Offset(w * 0.9f, h * 0.1f), strokeWidth = 3f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f)))
                            }
                            6 -> { // Histogram
                                val barW = w / 6f
                                val heights = listOf(h * 0.3f, h * 0.65f, h * 0.9f, h * 0.55f, h * 0.2f)
                                heights.forEachIndexed { i, bh ->
                                    drawRect(
                                        color = c.copy(alpha = 0.7f),
                                        topLeft = Offset((i + 0.5f) * barW, h - bh),
                                        size = Size(barW, bh)
                                    )
                                    drawRect(
                                        color = Color.White.copy(alpha = 0.4f),
                                        topLeft = Offset((i + 0.5f) * barW, h - bh),
                                        size = Size(barW, bh),
                                        style = Stroke(width = 2f)
                                    )
                                }
                            }
                            7 -> { // Area Chart
                                val path = Path().apply {
                                    moveTo(w * 0.05f, h)
                                    lineTo(w * 0.05f, h * 0.7f)
                                    lineTo(w * 0.3f, h * 0.3f)
                                    lineTo(w * 0.6f, h * 0.5f)
                                    lineTo(w * 0.95f, h * 0.15f)
                                    lineTo(w * 0.95f, h)
                                    close()
                                }
                                drawPath(path, Brush.verticalGradient(listOf(c.copy(alpha = 0.65f), c.copy(alpha = 0.1f))))
                            }
                            8 -> { // Radar Chart
                                val center = Offset(w / 2f, h / 2f)
                                val radius = Math.min(w, h) * 0.4f
                                val points = listOf(
                                    Offset(center.x, center.y - radius * 0.9f),
                                    Offset(center.x + radius * 0.8f, center.y - radius * 0.3f),
                                    Offset(center.x + radius * 0.6f, center.y + radius * 0.7f),
                                    Offset(center.x - radius * 0.6f, center.y + radius * 0.7f),
                                    Offset(center.x - radius * 0.8f, center.y - radius * 0.3f)
                                )
                                val path = Path().apply {
                                    moveTo(points[0].x, points[0].y)
                                    points.drop(1).forEach { lineTo(it.x, it.y) }
                                    close()
                                }
                                drawPath(path, c.copy(alpha = 0.35f))
                                drawPath(path, c, style = Stroke(width = 4f))
                            }
                            9 -> { // Bubble Chart
                                val bubbles = listOf(
                                    Triple(Offset(w * 0.25f, h * 0.6f), 28f, c.copy(alpha = 0.6f)),
                                    Triple(Offset(w * 0.55f, h * 0.35f), 46f, c.copy(alpha = 0.8f)),
                                    Triple(Offset(w * 0.8f, h * 0.7f), 34f, c.copy(alpha = 0.5f))
                                )
                                bubbles.forEach { (center, r, col) ->
                                    drawCircle(col, radius = r, center = center)
                                }
                            }
                            10 -> { // Box Plot
                                val boxLeft = w * 0.3f
                                val boxRight = w * 0.7f
                                val boxTop = h * 0.3f
                                val boxBottom = h * 0.7f
                                val midY = h * 0.5f
                                drawLine(c, Offset(w * 0.1f, midY), Offset(w * 0.9f, midY), strokeWidth = 4f)
                                drawRect(c.copy(alpha = 0.3f), Offset(boxLeft, boxTop), Size(boxRight - boxLeft, boxBottom - boxTop))
                                drawRect(c, Offset(boxLeft, boxTop), Size(boxRight - boxLeft, boxBottom - boxTop), style = Stroke(4f))
                                drawLine(c, Offset(w * 0.5f, boxTop), Offset(w * 0.5f, boxBottom), strokeWidth = 6f)
                            }
                            11 -> { // Heatmap
                                val cols = 5
                                val rows = 3
                                val cellW = w / cols
                                val cellH = h / rows
                                for (r in 0 until rows) {
                                    for (col in 0 until cols) {
                                        val alpha = ((r + col) % 4 + 1) * 0.22f
                                        drawRect(
                                            c.copy(alpha = alpha),
                                            Offset(col * cellW + 4f, r * cellH + 4f),
                                            Size(cellW - 8f, cellH - 8f)
                                        )
                                    }
                                }
                            }
                            12 -> { // Funnel Chart
                                val path = Path().apply {
                                    moveTo(w * 0.1f, h * 0.1f)
                                    lineTo(w * 0.9f, h * 0.1f)
                                    lineTo(w * 0.65f, h * 0.9f)
                                    lineTo(w * 0.35f, h * 0.9f)
                                    close()
                                }
                                drawPath(path, c.copy(alpha = 0.65f))
                            }
                            13 -> { // Treemap
                                drawRect(c.copy(alpha = 0.8f), Offset(w * 0.05f, h * 0.05f), Size(w * 0.5f, h * 0.9f))
                                drawRect(c.copy(alpha = 0.5f), Offset(w * 0.57f, h * 0.05f), Size(w * 0.38f, h * 0.43f))
                                drawRect(c.copy(alpha = 0.3f), Offset(w * 0.57f, h * 0.52f), Size(w * 0.38f, h * 0.43f))
                            }
                            14 -> { // Gantt Chart
                                val barH = h / 5f
                                val intervals = listOf(
                                    Pair(w * 0.05f, w * 0.45f),
                                    Pair(w * 0.3f, w * 0.75f),
                                    Pair(w * 0.6f, w * 0.95f)
                                )
                                intervals.forEachIndexed { i, (start, end) ->
                                    val y = (i + 0.8f) * barH
                                    drawRoundRect(c.copy(alpha = 0.8f), Offset(start, y), Size(end - start, barH * 0.6f), CornerRadius(6f, 6f))
                                }
                            }
                            15 -> { // Waterfall Chart
                                val barW = w / 6f
                                drawRect(c.copy(alpha = 0.9f), Offset(barW * 0.5f, h * 0.4f), Size(barW * 0.8f, h * 0.5f))
                                drawRect(Color(0xFFEF4444).copy(alpha = 0.8f), Offset(barW * 1.8f, h * 0.4f), Size(barW * 0.8f, h * 0.2f))
                                drawRect(Color(0xFFEF4444).copy(alpha = 0.8f), Offset(barW * 3.1f, h * 0.6f), Size(barW * 0.8f, h * 0.15f))
                                drawRect(c, Offset(barW * 4.4f, h * 0.55f), Size(barW * 0.8f, h * 0.35f))
                            }
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color.Black.copy(alpha = 0.65f),
                        modifier = Modifier.align(Alignment.BottomEnd)
                    ) {
                        Text(
                            text = "${activeFamily.nameFa} • ${activeFamily.typeDescriptorFa}",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            // Application & Tip Cards
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = activeFamily.color.copy(alpha = 0.08f),
                border = BorderStroke(1.dp, activeFamily.color.copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "🎯 کاربرد در کنکور انسانی و آزمون فرهنگیان:",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = activeFamily.color
                    )
                    Text(
                        text = activeFamily.konkoorApplication,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(14.dp),
                color = Color(0xFFF59E0B).copy(alpha = 0.1f),
                border = BorderStroke(1.dp, Color(0xFFF59E0B).copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "⚡ شگرد تست‌زنی و کد رمزی:",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFFB45309)
                    )
                    Text(
                        text = activeFamily.shortcutTip,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF78350F)
                    )
                }
            }
        }
    }
}


