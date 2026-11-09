package com.example.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.curriculum.ZeroLesson
import com.example.data.curriculum.normalizedSubjectId
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

/**
 * مدل شاخه و برگ در بوم درختی تعاملی با قابلیت بزرگ‌نمایی و جابه‌جایی (Zoom/Pan Tree Canvas)
 */
data class ZoomPanTreeBranch(
    val id: String,
    val title: String,
    val badge: String,
    val color: Color,
    val detailRule: String,
    val memoryCode: String,
    val leaves: List<Pair<String, String>> // Pair(leafTitle, leafSub)
)

data class ZoomPanTreeModel(
    val id: String,
    val tabLabel: String,
    val rootTitle: String,
    val rootSubtitle: String,
    val rootColor: Color,
    val branches: List<ZoomPanTreeBranch>
)

/**
 * مدل انیمیشن مرحله‌به‌مرحله قواعد عربی و گرامر انگلیسی (Step-by-Step Compose Animated Blocks)
 */
data class AnimatedGrammarToken(
    val text: String,
    val roleLabel: String,
    val color: Color,
    val isHighlighted: Boolean = false
)

data class AnimatedGrammarStepFrame(
    val stepNumber: Int,
    val stepTitle: String,
    val formulaBadge: String,
    val tokens: List<AnimatedGrammarToken>,
    val explanation: String,
    val memoryTrick: String
)

data class StepByStepGrammarScenario(
    val id: String,
    val categoryBadge: String,
    val title: String,
    val accentColor: Color,
    val frames: List<AnimatedGrammarStepFrame>
)

/**
 * کامپوننت جامع:
 * ۱. بوم نمودارهای درختی با قابلیت تعامل (Zoom / Pan + انتخاب گره‌ها) برای تاریخ (دهم، یازدهم، دوازدهم) و عربی
 * ۲. پلیر انیمیشن‌های مرحله‌به‌مرحله (Compose Step-by-Step Transitions) برای گرامر زبان انگلیسی و قواعد عربی
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun VisualMemoryTreeAndTimelineCard(
    lesson: ZeroLesson? = null,
    subjectIdFilter: String? = lesson?.normalizedSubjectId(),
    accentColor: Color = Color(0xFF2563EB),
    modifier: Modifier = Modifier
) {
    val normalizedSub = subjectIdFilter?.trim()?.lowercase()
    val isLockedToSingleSubject = !normalizedSub.isNullOrBlank() && normalizedSub != "all"

    val defaultTab = remember(lesson?.id, normalizedSub) {
        if (isLockedToSingleSubject) 0
        else when (normalizedSub) {
            "history", "geography" -> 0
            "arabic", "dini" -> 1
            "math", "fonoon", "arooz", "philosophy", "logic", "sociology", "psychology", "economics" -> 2
            "english" -> 3
            else -> 1
        }
    }
    var selectedMainTab by remember(lesson?.id, normalizedSub) { mutableIntStateOf(defaultTab) }
    var isExpanded by remember { mutableStateOf(true) }

    val infiniteTransition = rememberInfiniteTransition(label = "visual_tree_pulse")
    val pulseFraction by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_fraction"
    )

    val subjectSpecificTrees = remember(normalizedSub) {
        when (normalizedSub) {
            "history", "geography", "history_geo" -> buildHistoryZoomPanTrees()
            "arabic" -> buildArabicZoomPanTrees()
            "english" -> buildAllSubjectsZoomPanTrees().filter { it.id == "english_visual_tree" }
            "math" -> buildAllSubjectsZoomPanTrees().filter { it.id == "math_humanities_tree" }
            "fonoon", "arooz", "farsi", "negaresh" -> buildAllSubjectsZoomPanTrees().filter { it.id == "fonoon_arooz_tree" }
            else -> buildAllSubjectsZoomPanTrees().filter { it.id == "humanities_core_tree" }
        }.ifEmpty { buildAllSubjectsZoomPanTrees().filter { it.id == "humanities_core_tree" } }
    }

    val subjectSpecificScenarios = remember(normalizedSub) {
        val all = buildStepByStepGrammarScenarios()
        when (normalizedSub) {
            "arabic" -> all.filter { it.id.startsWith("arabic_") }
            "english" -> all.filter { it.id.startsWith("eng") }
            "math" -> all.filter { it.id.startsWith("math_") }
            "fonoon", "arooz" -> all.filter { it.id.startsWith("fonoon_") }
            else -> emptyList()
        }
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .testTag("visual_memory_tree_card"),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            border = BorderStroke(1.2.dp, accentColor.copy(alpha = 0.45f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                accentColor.copy(alpha = 0.09f),
                                MaterialTheme.colorScheme.surface
                            )
                        )
                    )
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { isExpanded = !isExpanded }
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .background(
                                    Brush.linearGradient(
                                        listOf(
                                            accentColor,
                                            MaterialTheme.colorScheme.secondaryContainer
                                        )
                                    ),
                                    RoundedCornerShape(14.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountTree,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (isLockedToSingleSubject) {
                                    "🌳 نمودار درختی (Zoom/Pan) و انیمیشن‌های اختصاصی این درس"
                                } else {
                                    "🌳 نمودار درختی تعاملی (Zoom/Pan) + انیمیشن مرحله‌به‌مرحله قواعد"
                                },
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Black,
                                color = accentColor
                            )
                            Text(
                                text = if (isLockedToSingleSubject) {
                                    "فقط مباحث مربوط به همین درس (بدون پراکندگی و تداخل با دروس دیگر)"
                                } else {
                                    "بوم بزرگ‌نمایی شاخه‌های دروس + پلیر انیمیشنی گرامر زبان انگلیسی و عربی"
                                },
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = accentColor
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    accentColor.copy(alpha = 0.4f),
                                    MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f)
                                )
                            ),
                            RoundedCornerShape(2.dp)
                        )
                )

                AnimatedVisibility(
                    visible = isExpanded,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        if (isLockedToSingleSubject) {
                            if (subjectSpecificScenarios.isNotEmpty()) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .horizontalScroll(rememberScrollState()),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    val singleSubjectTabs = listOf(
                                        "🌳 ۱. نمودار درختی این درس (Zoom/Pan)" to accentColor,
                                        "🎬 ۲. انیمیشن مرحله‌به‌مرحله این درس" to Color(0xFF2563EB)
                                    )
                                    singleSubjectTabs.forEachIndexed { idx, (label, tabColor) ->
                                        val selected = selectedMainTab == idx
                                        FilterChip(
                                            selected = selected,
                                            onClick = { selectedMainTab = idx },
                                            label = {
                                                Text(
                                                    text = label,
                                                    style = MaterialTheme.typography.labelMedium,
                                                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                                                )
                                            },
                                            colors = FilterChipDefaults.filterChipColors(
                                                selectedContainerColor = tabColor.copy(alpha = 0.18f),
                                                selectedLabelColor = tabColor
                                            ),
                                            modifier = Modifier.testTag("visual_tree_main_tab_$idx")
                                        )
                                    }
                                }

                                when (selectedMainTab) {
                                    0 -> InteractiveZoomPanTreeExplorer(
                                        trees = subjectSpecificTrees,
                                        pulseFraction = pulseFraction
                                    )
                                    else -> StepByStepComposeGrammarAnimator(
                                        scenarios = subjectSpecificScenarios,
                                        pulseFraction = pulseFraction
                                    )
                                }
                            } else {
                                InteractiveZoomPanTreeExplorer(
                                    trees = subjectSpecificTrees,
                                    pulseFraction = pulseFraction
                                )
                            }
                        } else {
                            // Global Lab Mode: 4 Main Interactive Modes
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(rememberScrollState()),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                val tabs = listOf(
                                    "🏛️ درختواره تاریخ (Zoom/Pan)" to Color(0xFFD97706),
                                    "🕌 درختواره عربی و ۱۴ صیغه (Zoom/Pan)" to Color(0xFF059669),
                                    "📐 درختواره ریاضی، فنون، زبان و انسانی (Zoom/Pan)" to Color(0xFF7C3AED),
                                    "🎬 انیمیشن صیغه‌ها، زبان، ریاضی و فنون" to Color(0xFF2563EB)
                                )
                                tabs.forEachIndexed { idx, (label, tabColor) ->
                                    val selected = selectedMainTab == idx
                                    FilterChip(
                                        selected = selected,
                                        onClick = { selectedMainTab = idx },
                                        label = {
                                            Text(
                                                text = label,
                                                style = MaterialTheme.typography.labelMedium,
                                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                                            )
                                        },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = tabColor.copy(alpha = 0.18f),
                                            selectedLabelColor = tabColor
                                        ),
                                        modifier = Modifier.testTag("visual_tree_main_tab_$idx")
                                    )
                                }
                            }

                            when (selectedMainTab) {
                                0 -> InteractiveZoomPanTreeExplorer(
                                    trees = remember { buildHistoryZoomPanTrees() },
                                    pulseFraction = pulseFraction
                                )
                                1 -> InteractiveZoomPanTreeExplorer(
                                    trees = remember { buildArabicZoomPanTrees() },
                                    pulseFraction = pulseFraction
                                )
                                2 -> InteractiveZoomPanTreeExplorer(
                                    trees = remember { buildAllSubjectsZoomPanTrees() },
                                    pulseFraction = pulseFraction
                                )
                                3 -> StepByStepComposeGrammarAnimator(
                                    pulseFraction = pulseFraction
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * بوم تعاملی نمودارهای درختی با قابلیت Zoom (بزرگ‌نمایی/کوچک‌نمایی)، Pan (جابه‌جایی ۲ بعدی با لمس یا دکمه)
 * و باز کردن جزئیات و کد رمزی هر شاخه و برگ
 */
@Composable
private fun InteractiveZoomPanTreeExplorer(
    trees: List<ZoomPanTreeModel>,
    pulseFraction: Float
) {
    var selectedTreeIdx by remember { mutableIntStateOf(0) }
    val activeTree = trees[selectedTreeIdx.coerceIn(0, trees.lastIndex)]
    var selectedBranchIdx by remember(activeTree.id) { mutableIntStateOf(0) }

    // Zoom & Pan interactive states
    var targetZoom by remember(activeTree.id) { mutableFloatStateOf(1.0f) }
    var panOffsetX by remember(activeTree.id) { mutableFloatStateOf(0f) }
    var panOffsetY by remember(activeTree.id) { mutableFloatStateOf(0f) }

    val animatedZoom by animateFloatAsState(
        targetValue = targetZoom,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "tree_zoom"
    )

    val activeBranch = activeTree.branches[selectedBranchIdx.coerceIn(0, activeTree.branches.lastIndex)]

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // Tree selector chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            trees.forEachIndexed { idx, tree ->
                val isSelected = selectedTreeIdx == idx
                Surface(
                    onClick = {
                        selectedTreeIdx = idx
                        selectedBranchIdx = 0
                    },
                    shape = RoundedCornerShape(10.dp),
                    color = if (isSelected) tree.rootColor else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
                    modifier = Modifier.testTag("select_tree_${tree.id}")
                ) {
                    Text(
                        text = tree.tabLabel,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // Interactive Zoom/Pan Toolbar
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
            border = BorderStroke(0.7.dp, activeTree.rootColor.copy(alpha = 0.35f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = BidiFormatter.formatMixedPersianText(
                        "🔍 بوم درختی تعاملی (${(targetZoom * 100).roundToInt()}٪) — با دو انگشت یا دکمه‌ها زوم و جابه‌جا کن"
                    ),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = activeTree.rootColor,
                    modifier = Modifier.weight(1f)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        onClick = { targetZoom = (targetZoom + 0.25f).coerceIn(0.65f, 2.25f) },
                        shape = RoundedCornerShape(8.dp),
                        color = activeTree.rootColor.copy(alpha = 0.15f),
                        modifier = Modifier.testTag("tree_zoom_in_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ZoomIn,
                            contentDescription = "بزرگ‌نمایی نمودار",
                            tint = activeTree.rootColor,
                            modifier = Modifier.padding(5.dp).size(18.dp)
                        )
                    }
                    Surface(
                        onClick = { targetZoom = (targetZoom - 0.25f).coerceIn(0.65f, 2.25f) },
                        shape = RoundedCornerShape(8.dp),
                        color = activeTree.rootColor.copy(alpha = 0.15f),
                        modifier = Modifier.testTag("tree_zoom_out_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ZoomOut,
                            contentDescription = "کوچک‌نمایی نمودار",
                            tint = activeTree.rootColor,
                            modifier = Modifier.padding(5.dp).size(18.dp)
                        )
                    }
                    Surface(
                        onClick = {
                            targetZoom = 1.0f
                            panOffsetX = 0f
                            panOffsetY = 0f
                        },
                        shape = RoundedCornerShape(8.dp),
                        color = activeTree.rootColor.copy(alpha = 0.15f),
                        modifier = Modifier.testTag("tree_zoom_reset_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.CenterFocusStrong,
                            contentDescription = "بازنشانی دید",
                            tint = activeTree.rootColor,
                            modifier = Modifier.padding(5.dp).size(18.dp)
                        )
                    }
                }
            }
        }

        // 2D Zoom & Pan Interactive Tree Viewport
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(310.dp)
                .clip(RoundedCornerShape(16.dp))
                .clipToBounds()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            activeTree.rootColor.copy(alpha = 0.10f),
                            MaterialTheme.colorScheme.surfaceContainerLow
                        )
                    )
                )
                .border(
                    width = 1.dp,
                    color = activeTree.rootColor.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(16.dp)
                )
                .pointerInput(activeTree.id) {
                    detectTransformGestures { _, pan, zoomChange, _ ->
                        val newZoom = (targetZoom * zoomChange).coerceIn(0.65f, 2.25f)
                        targetZoom = newZoom
                        panOffsetX = (panOffsetX + pan.x).coerceIn(-360f, 360f)
                        panOffsetY = (panOffsetY + pan.y).coerceIn(-220f, 220f)
                    }
                }
                .testTag("zoom_pan_tree_canvas_box"),
            contentAlignment = Alignment.TopCenter
        ) {
            // Transformed Hierarchical Tree Layer
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        scaleX = animatedZoom
                        scaleY = animatedZoom
                        translationX = panOffsetX
                        translationY = panOffsetY
                    }
                    .padding(10.dp)
            ) {
                // Background Animated Bezier Tree Branches Canvas
                val branchesCount = activeTree.branches.size
                val branchColors = remember(activeTree.id) { activeTree.branches.map { it.color } }

                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height

                    // Subtle grid dots for spatial pan/zoom reference
                    val gridStep = 40f
                    var gx = 20f
                    while (gx < w) {
                        var gy = 20f
                        while (gy < h) {
                            drawCircle(
                                color = activeTree.rootColor.copy(alpha = 0.08f),
                                radius = 1.5f,
                                center = Offset(gx, gy)
                            )
                            gy += gridStep
                        }
                        gx += gridStep
                    }

                    val rootCenter = Offset(w * 0.5f, h * 0.14f)
                    val branchY = h * 0.46f
                    val leafY = h * 0.82f

                    for (i in 0 until branchesCount) {
                        // RTL horizontal position
                        val fraction = (i + 0.5f) / branchesCount.toFloat()
                        val bx = w * (1f - fraction)
                        val bCenter = Offset(bx, branchY)
                        val bColor = branchColors[i]
                        val isChosen = i == selectedBranchIdx

                        // Cubic Bezier from Root to Branch
                        val rootToBranch = Path().apply {
                            moveTo(rootCenter.x, rootCenter.y + 18f)
                            cubicTo(
                                rootCenter.x, (rootCenter.y + branchY) * 0.5f,
                                bCenter.x, (rootCenter.y + branchY) * 0.5f,
                                bCenter.x, bCenter.y - 18f
                            )
                        }
                        drawPath(
                            path = rootToBranch,
                            color = bColor.copy(alpha = if (isChosen) 0.85f else 0.35f),
                            style = Stroke(
                                width = if (isChosen) 5f else 2.5f,
                                pathEffect = if (isChosen) {
                                    PathEffect.dashPathEffect(floatArrayOf(16f, 8f), phase = -pulseFraction * 24f)
                                } else null
                            )
                        )

                        // Branch to its Leaf cluster (clean straight connection)
                        val branchToLeaf = Path().apply {
                            moveTo(bCenter.x, bCenter.y + 18f)
                            lineTo(bCenter.x, leafY - 16f)
                        }
                        drawPath(
                            path = branchToLeaf,
                            color = bColor.copy(alpha = if (isChosen) 0.85f else 0.35f),
                            style = Stroke(
                                width = if (isChosen) 3.5f else 2f,
                                pathEffect = if (isChosen) {
                                    PathEffect.dashPathEffect(floatArrayOf(12f, 6f), phase = -pulseFraction * 18f)
                                } else null
                            )
                        )
                    }
                }

                // Foreground Interactive Tree Nodes (Level 1: Root, Level 2: Branches, Level 3: Leaves)
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Level 1: Root Node
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = activeTree.rootColor,
                        shadowElevation = 4.dp
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "🌳 ${activeTree.rootTitle}",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                            Text(
                                text = activeTree.rootSubtitle,
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }
                    }

                    // Level 2: Interactive Branch Nodes
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        activeTree.branches.forEachIndexed { idx, branch ->
                            val isSelected = idx == selectedBranchIdx
                            Surface(
                                onClick = { selectedBranchIdx = idx },
                                shape = RoundedCornerShape(10.dp),
                                color = if (isSelected) branch.color else MaterialTheme.colorScheme.surface,
                                border = BorderStroke(
                                    width = if (isSelected) 2.dp else 1.dp,
                                    color = branch.color
                                ),
                                shadowElevation = if (isSelected) 4.dp else 1.dp,
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("tree_branch_node_$idx")
                            ) {
                                Column(
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 6.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = BidiFormatter.formatMixedPersianText(branch.badge),
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color.White.copy(alpha = 0.9f) else branch.color,
                                        textAlign = TextAlign.Center,
                                        maxLines = 1
                                    )
                                    Text(
                                        text = BidiFormatter.formatMixedPersianText(branch.title),
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Black,
                                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                                        textAlign = TextAlign.Center,
                                        maxLines = 2
                                    )
                                }
                            }
                        }
                    }

                    // Level 3: Leaves of all branches (highlighting the selected branch's leaves)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        activeTree.branches.forEachIndexed { idx, branch ->
                            val isBranchSelected = idx == selectedBranchIdx
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { selectedBranchIdx = idx },
                                verticalArrangement = Arrangement.spacedBy(3.dp)
                            ) {
                                branch.leaves.take(2).forEach { (leafTitle, leafSub) ->
                                    Surface(
                                        shape = RoundedCornerShape(7.dp),
                                        color = if (isBranchSelected) {
                                            branch.color.copy(alpha = 0.16f)
                                        } else {
                                            MaterialTheme.colorScheme.surface.copy(alpha = 0.75f)
                                        },
                                        border = BorderStroke(
                                            width = if (isBranchSelected) 1.dp else 0.5.dp,
                                            color = branch.color.copy(alpha = if (isBranchSelected) 0.65f else 0.25f)
                                        ),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(horizontal = 5.dp, vertical = 4.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = BidiFormatter.formatMixedPersianText(leafTitle),
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = if (isBranchSelected) branch.color else MaterialTheme.colorScheme.onSurface,
                                                textAlign = TextAlign.Center,
                                                maxLines = 1
                                            )
                                            Text(
                                                text = BidiFormatter.formatMixedPersianText(leafSub),
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
                    }
                }
            }
        }

        // Selected Branch Detailed Inspector Card
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = activeBranch.color.copy(alpha = 0.08f),
            border = BorderStroke(1.dp, activeBranch.color.copy(alpha = 0.45f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    activeBranch.color.copy(alpha = 0.35f),
                                    MaterialTheme.colorScheme.tertiary.copy(alpha = 0.35f)
                                )
                            ),
                            RoundedCornerShape(3.dp)
                        )
                )
                BiDiText(
                    text = "🔎 کالبدشکافی شاخه انتخاب‌شده: «${activeBranch.title}» (${activeBranch.badge})",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Black,
                    color = activeBranch.color
                )
                BiDiText(
                    text = activeBranch.detailRule,
                    style = MaterialTheme.typography.bodySmall
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    activeBranch.leaves.forEach { (lTitle, lSub) ->
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surface,
                            border = BorderStroke(0.7.dp, activeBranch.color.copy(alpha = 0.35f)),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(
                                modifier = Modifier.padding(6.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                BiDiText(
                                    text = lTitle,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = activeBranch.color,
                                    textAlign = TextAlign.Center
                                )
                                BiDiText(
                                    text = lSub,
                                    style = MaterialTheme.typography.labelSmall,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(0.8.dp, activeBranch.color.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    BiDiText(
                        text = activeBranch.memoryCode,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = activeBranch.color,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

/**
 * پلیر انیمیشن‌های مرحله‌به‌مرحله Compose (معادل انیمیشن‌های تعاملی CSS/Compose Keyframes)
 * برای آموزش بصری گرامر زبان انگلیسی و قواعد عربی با جابه‌جایی بلوک‌های کلمات و تغییر رنگ خودکار
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun StepByStepComposeGrammarAnimator(
    scenarios: List<StepByStepGrammarScenario> = remember { buildStepByStepGrammarScenarios() },
    pulseFraction: Float
) {
    var selectedScenarioIdx by remember(scenarios) { mutableIntStateOf(0) }
    val activeScenario = scenarios[selectedScenarioIdx.coerceIn(0, scenarios.lastIndex)]

    var currentFrameIdx by remember(activeScenario.id) { mutableIntStateOf(0) }
    var isAutoPlaying by remember(activeScenario.id) { mutableStateOf(false) }

    LaunchedEffect(isAutoPlaying, currentFrameIdx, activeScenario.id) {
        if (isAutoPlaying) {
            delay(3200L)
            currentFrameIdx = (currentFrameIdx + 1) % activeScenario.frames.size
        }
    }

    val safeFrameIdx = currentFrameIdx.coerceIn(0, activeScenario.frames.lastIndex)
    val activeFrame = activeScenario.frames[safeFrameIdx]

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // Scenario Picker Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            scenarios.forEachIndexed { idx, sc ->
                val isSelected = selectedScenarioIdx == idx
                Surface(
                    onClick = {
                        selectedScenarioIdx = idx
                        currentFrameIdx = 0
                        isAutoPlaying = false
                    },
                    shape = RoundedCornerShape(10.dp),
                    color = if (isSelected) sc.accentColor else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
                    modifier = Modifier.testTag("grammar_anim_scenario_${sc.id}")
                ) {
                    Text(
                        text = "${sc.categoryBadge}: ${sc.title}",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // Animated Stage Container
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = activeScenario.accentColor.copy(alpha = 0.07f),
            border = BorderStroke(1.2.dp, activeScenario.accentColor.copy(alpha = 0.45f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Playback Controls Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "🎬 ${activeScenario.title}",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Black,
                            color = activeScenario.accentColor
                        )
                        Text(
                            text = BidiFormatter.formatMixedPersianText(
                                "گام ${safeFrameIdx + 1} از ${activeScenario.frames.size}: ${activeFrame.stepTitle}"
                            ),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                isAutoPlaying = false
                                currentFrameIdx = if (safeFrameIdx > 0) safeFrameIdx - 1 else activeScenario.frames.lastIndex
                            },
                            modifier = Modifier.size(32.dp).testTag("grammar_anim_prev_btn")
                        ) {
                            Icon(Icons.Default.SkipPrevious, contentDescription = "گام قبلی", tint = activeScenario.accentColor)
                        }

                        Surface(
                            onClick = { isAutoPlaying = !isAutoPlaying },
                            shape = RoundedCornerShape(20.dp),
                            color = activeScenario.accentColor,
                            modifier = Modifier.testTag("grammar_anim_play_pause_btn")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = if (isAutoPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    contentDescription = if (isAutoPlaying) "توقف" else "پخش خودکار",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (isAutoPlaying) "توقف" else "پخش انیمیشن",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }

                        IconButton(
                            onClick = {
                                isAutoPlaying = false
                                currentFrameIdx = (safeFrameIdx + 1) % activeScenario.frames.size
                            },
                            modifier = Modifier.size(32.dp).testTag("grammar_anim_next_btn")
                        ) {
                            Icon(Icons.Default.SkipNext, contentDescription = "گام بعدی", tint = activeScenario.accentColor)
                        }
                    }
                }

                // Step Progress Pills
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    activeScenario.frames.forEachIndexed { idx, frame ->
                        val isCurrent = idx == safeFrameIdx
                        val pillColor by animateColorAsState(
                            targetValue = if (isCurrent) activeScenario.accentColor else activeScenario.accentColor.copy(alpha = 0.22f),
                            animationSpec = tween(350),
                            label = "step_pill_color"
                        )
                        Surface(
                            onClick = {
                                isAutoPlaying = false
                                currentFrameIdx = idx
                            },
                            shape = RoundedCornerShape(8.dp),
                            color = pillColor,
                            modifier = Modifier
                                .weight(1f)
                                .height(26.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = BidiFormatter.formatMixedPersianText("گام ${frame.stepNumber}"),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isCurrent) Color.White else activeScenario.accentColor
                                )
                            }
                        }
                    }
                }

                // Animated Word Blocks Stage (CSS-like Keyframe Block Transitions in Compose)
                AnimatedContent(
                    targetState = activeFrame,
                    transitionSpec = {
                        (slideInHorizontally(animationSpec = tween(420)) { width -> width / 3 } +
                            fadeIn(animationSpec = tween(420))) togetherWith
                            (slideOutHorizontally(animationSpec = tween(320)) { width -> -width / 3 } +
                                fadeOut(animationSpec = tween(320)))
                    },
                    label = "grammar_frame_transition"
                ) { frame ->
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        // Formula Badge Banner
                        Surface(
                            color = activeScenario.accentColor.copy(alpha = 0.14f),
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(0.8.dp, activeScenario.accentColor.copy(alpha = 0.4f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            BiDiText(
                                text = "📐 فرمول این گام: ${frame.formulaBadge}",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Black,
                                color = activeScenario.accentColor,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }

                        // Animated Token Blocks
                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = MaterialTheme.colorScheme.surface,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = activeScenario.accentColor.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            frame.tokens.forEach { token ->
                                val bounceOffset = if (token.isHighlighted) (-3f * pulseFraction).roundToInt() else 0
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = if (token.isHighlighted) token.color else token.color.copy(alpha = 0.14f),
                                    border = BorderStroke(
                                        width = if (token.isHighlighted) 2.dp else 1.dp,
                                        color = token.color
                                    ),
                                    shadowElevation = if (token.isHighlighted) 4.dp else 0.dp,
                                    modifier = Modifier.offset { IntOffset(0, bounceOffset) }
                                ) {
                                    Column(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        BiDiText(
                                            text = token.text,
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Black,
                                            color = if (token.isHighlighted) Color.White else token.color
                                        )
                                        BiDiText(
                                            text = token.roleLabel,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = if (token.isHighlighted) Color.White.copy(alpha = 0.92f) else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }

                        // Step Explanation & Trick
                        Surface(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(0.7.dp, MaterialTheme.colorScheme.outlineVariant),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                BiDiText(
                                    text = "💡 توضیح مرحله ${frame.stepNumber}: ${frame.explanation}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                                BiDiText(
                                    text = "🔑 رمز کنکوری: ${frame.memoryTrick}",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold,
                                    color = activeScenario.accentColor
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun buildHistoryZoomPanTrees(): List<ZoomPanTreeModel> = listOf(
    ZoomPanTreeModel(
        id = "hist_ancient_tree",
        tabLabel = "۱. درختواره ایران باستان (دهم)",
        rootTitle = "سلسله‌های ایران باستان (تاریخ دهم)",
        rootSubtitle = "از ۷۰۸ ق.م (مادها) تا ۶۵۱ م (سقوط ساسانیان)",
        rootColor = Color(0xFFD97706),
        branches = listOf(
            ZoomPanTreeBranch(
                id = "medes",
                title = "مادها",
                badge = "۷۰۸ تا ۵۵۰ ق.م",
                color = Color(0xFFD97706),
                detailRule = "بنیان‌گذار: دیاکو • پایتخت: هگمتانه (همدان) • هووخشتره با اتحاد بابل در ۶۱۲ ق.م امپراتوری آشور (نینوا) را نابود کرد.",
                memoryCode = "🔑 کد رمزی ماد: «دیاکو در همدان + هووخشتره فاتح نینوا (۶۱۲ ق.م)»",
                leaves = listOf(
                    "دیاکو و هگمتانه" to "نخستین شاه و پایتخت",
                    "هووخشتره (۶۱۲ ق.م)" to "ارتش منظم و فتح آشور"
                )
            ),
            ZoomPanTreeBranch(
                id = "achaemenids",
                title = "هخامنشیان",
                badge = "۵۵۰ تا ۳۳۰ ق.م",
                color = Color(0xFF0284C7),
                detailRule = "کوروش بزرگ (منشور حقوق بشر و فتح بابل ۵۳۹ ق.م) • داریوش اول (شهربانی/ساتراپی، بازرسان چشم و گوش شاه، سکه دریک و راه شاهی).",
                memoryCode = "🔑 کد رمزی هخامنشی: «کوروش ۵۳۹ بابل • داریوش = دریک + ساتراپ + راه شاهی»",
                leaves = listOf(
                    "کوروش (۵۳۹ ق.م)" to "فتح بابل و منشور",
                    "داریوش اول" to "ساتراپ، دریک و راه شاهی"
                )
            ),
            ZoomPanTreeBranch(
                id = "parthians",
                title = "اشکانیان",
                badge = "۲۴۷ ق.م تا ۲۲۴ م",
                color = Color(0xFF7C3AED),
                detailRule = "بنیان‌گذار: ارشک • طولانی‌ترین سلسله ایران (۴۷۱ سال) • حکومت غیرمتمرکز ملوک‌الطوایفی • مجلس مهستان • پیروزی سورنا بر کراسوس در حران (۵۳ ق.م).",
                memoryCode = "🔑 کد رمزی اشکانی: «مهستان + ملوک‌الطوایفی + سورنا در حران (۵۳ ق.م)»",
                leaves = listOf(
                    "مجلس مهستان" to "مشورت خردمندان و مغان",
                    "نبرد حران (۵۳ ق.م)" to "سورنا و شکست کراسوس"
                )
            ),
            ZoomPanTreeBranch(
                id = "sasanians",
                title = "ساسانیان",
                badge = "۲۲۴ تا ۶۵۱ م",
                color = Color(0xFFDC2626),
                detailRule = "بنیان‌گذار: اردشیر بابکان • پایتخت: تیسفون • دین رسمی زرتشت (کرتیر) • اسارت والریان توسط شاپور اول • اصلاحات مالیاتی انوشیروان و جندی‌شاپور.",
                memoryCode = "🔑 کد رمزی ساسانی: «اردشیر تیسفون + شاپور و والریان + انوشیروان و جندی‌شاپور»",
                leaves = listOf(
                    "اردشیر و شاپور اول" to "تیسفون و اسارت والریان",
                    "خسرو انوشیروان" to "اصلاحات و جندی‌شاپور"
                )
            )
        )
    ),
    ZoomPanTreeModel(
        id = "hist_islamic_tree",
        tabLabel = "۲. درختواره ایران اسلامی (یازدهم)",
        rootTitle = "حکومت‌های ایران اسلامی (تاریخ یازدهم)",
        rootSubtitle = "از طاهریان (۲۰۵ ق) تا صفویه (۹۰۷ ق)",
        rootColor = Color(0xFF059669),
        branches = listOf(
            ZoomPanTreeBranch(
                id = "tahir_saffar_saman",
                title = "طاهریان، صفاریان، سامانیان",
                badge = "قرن ۳ و ۴ ق",
                color = Color(0xFF059669),
                detailRule = "طاهریان (نیشابور، نخستین حکومت نیمه‌مستقل) • صفاریان (یعقوب لیث در سیستان، احیای زبان فارسی) • سامانیان (بخارا، وزیران دانشمند و رودکی/ابن‌سینا).",
                memoryCode = "🔑 کد رمزی ترتیب: «طاص» = طاهری (نیشابور) ➔ صفاری (زرنج) ➔ سامانی (بخارا)",
                leaves = listOf(
                    "طاهریان و صفاریان" to "طاهر و یعقوب لیث",
                    "سامانیان (بخارا)" to "عصر طلایی فرهنگ و دیوان"
                )
            ),
            ZoomPanTreeBranch(
                id = "buyid_ghazna_seljuk",
                title = "آل‌بویه، غزنویان، سلجوقیان",
                badge = "قرن ۴ تا ۶ ق",
                color = Color(0xFF2563EB),
                detailRule = "آل‌بویه (حکومت شیعی، فتح بغداد ۳۳۴ ق) • غزنویان (محمود غزنوی و لشکرکشی به هند) • سلجوقیان (طغرل، خواجه نظام‌الملک، مدارس نظامیه و ملازگرد ۴۶۳ ق).",
                memoryCode = "🔑 کد رمزی: «آل‌بویه ۳۳۴ بغداد • سلجوقی ۴۶۳ ملازگرد + خواجه نظام‌الملک»",
                leaves = listOf(
                    "آل‌بویه (۳۳۴ ق)" to "فتح بغداد و خلافت",
                    "سلجوقیان (۴۶۳ ق)" to "ملازگرد و مدارس نظامیه"
                )
            ),
            ZoomPanTreeBranch(
                id = "khwarazm_ilkhan_safavid",
                title = "خوارزمشاهیان، ایلخانان، صفویه",
                badge = "قرن ۷ تا ۱۰ ق",
                color = Color(0xFF9333EA),
                detailRule = "حادثه اترار (۶۱۶ ق) و حمله مغول • ایلخانان (رصدخانه مراغه با خواجه نصیر و اصلاحات غازان‌خان با رشیدالدین فضل‌الله) • شاه اسماعیل صفوی (۹۰۷ ق تبریز).",
                memoryCode = "🔑 کد رمزی: «اترار ۶۱۶ ق ➔ مراغه خواجه نصیر ➔ غازان‌خان ➔ ۹۰۷ ق صفویه»",
                leaves = listOf(
                    "ایلخانان (مراغه)" to "خواجه نصیر و غازان‌خان",
                    "صفویه (۹۰۷ ق)" to "شاه اسماعیل و مذهب رسمی"
                )
            )
        )
    ),
    ZoomPanTreeModel(
        id = "hist_modern_tree",
        tabLabel = "۳. درختواره معاصر و عهدنامه‌ها (دوازدهم)",
        rootTitle = "تاریخ معاصر ایران (قاجار، پهلوی و انقلاب)",
        rootSubtitle = "عهدنامه‌ها، مشروطه (۱۲۸۵)، نفت (۱۳۲۹) و انقلاب (۱۳۵۷)",
        rootColor = Color(0xFFE11D48),
        branches = listOf(
            ZoomPanTreeBranch(
                id = "qajar_treaties",
                title = "قاجار و مشروطه",
                badge = "۱۸۱۳ تا ۱۲۸۵ ش",
                color = Color(0xFFE11D48),
                detailRule = "گلستان (۱۸۱۳ م / ۱۱۹۲ ش: قفقاز و حق کشتیرانی خزر) • ترکمانچای (۱۸۲۸ م / ۱۲۰۶ ش: ایروان و نخجوان + کاپیتولاسیون) • پاریس (۱۸۵۷ م: هرات) • مشروطه (۱۴ مرداد ۱۲۸۵).",
                memoryCode = "🔑 کد رمزی: «گلستان = خزر • ترکمانچای = ارس و کاپیتولاسیون • پاریس = هرات»",
                leaves = listOf(
                    "گلستان و ترکمانچای" to "۱۸۱۳ و ۱۸۲۸ میلادی",
                    "دارالفنون و مشروطه" to "۱۲۳۰ ش و ۱۴ مرداد ۱۲۸۵"
                )
            ),
            ZoomPanTreeBranch(
                id = "pahlavi_first",
                title = "کودتا و پهلوی اول",
                badge = "۱۲۹۹ تا ۱۳۲۰ ش",
                color = Color(0xFFD97706),
                detailRule = "کودتای ۳ اسفند ۱۲۹۹ (سیدضیاء و رضاخان) • انقراض قاجار (آبان ۱۳۰۴) • دانشگاه تهران (۱۳۱۳) • واقعه مسجد گوهرشاد (تیر ۱۳۱۴) • اشغال ایران (۳ شهریور ۱۳۲۰).",
                memoryCode = "🔑 کد رمزی: «۱۲۹۹ کودتا • ۱۳۰۴ پهلوی • ۱۳۱۴ گوهرشاد • ۱۳۲۰ اشغال متفقین»",
                leaves = listOf(
                    "کودتای ۱۲۹۹ و ۱۳۰۴" to "سیدضیاء و انقراض قاجار",
                    "گوهرشاد و شهریور ۲۰" to "تیر ۱۳۱۴ و اشغال متفقین"
                )
            ),
            ZoomPanTreeBranch(
                id = "oil_to_revolution",
                title = "نفت ملی تا انقلاب",
                badge = "۱۳۲۹ تا ۱۳۵۷ ش",
                color = Color(0xFF0D9488),
                detailRule = "ملی شدن صنعت نفت (۲۹ اسفند ۱۳۲۹ — مصدق و کاشانی) • قیام ۳۰ تیر ۱۳۳۱ • کودتای ۲۸ مرداد ۱۳۳۲ (آژاکس) • قیام ۱۵ خرداد ۱۳۴۲ • پیروزی انقلاب (۲۲ بهمن ۱۳۵۷).",
                memoryCode = "🔑 کد رمزی: «۲۹ اسفند ۲۹ نفت • ۳۰ تیر ۳۱ • ۲۸ مرداد ۳۲ کودتا • ۱۵ خرداد ۴۲»",
                leaves = listOf(
                    "ملی شدن نفت (۱۳۲۹)" to "۲۹ اسفند ۲۹ و کودتای ۳۲",
                    "۱۵ خرداد تا ۲۲ بهمن" to "نهضت امام و انقلاب ۱۳۵۷"
                )
            )
        )
    )
)

private fun buildArabicZoomPanTrees(): List<ZoomPanTreeModel> = listOf(
    ZoomPanTreeModel(
        id = "arabic_14_sigha_hand_tree",
        tabLabel = "۰. درختواره ۱۴ صیغه با ۵ انگشت دست (از زیر صفر)",
        rootTitle = "شجره‌نامه تصویری ۱۴ صیغه عربی (ماضی، مضارع، امر و نهی)",
        rootSubtitle = "تشخیص ۱ ثانیه‌ای صیغه فقط از روی حرف اول و آخر فعل (بدون نیاز به معنی)",
        rootColor = Color(0xFF047857),
        branches = listOf(
            ZoomPanTreeBranch(
                id = "sigha_ghayeb",
                title = "۱. غایب (او / آن‌ها)",
                badge = "انگشت شست و اشاره",
                color = Color(0xFF0284C7),
                detailRule = "غایب یعنی کسی که حضور ندارد (او / آن‌ها):\n• ماضی: ذَهَبَ (او مرد رفت)، ذَهَبَتْ (او زن رفت)، ذَهَبا/ذَهَبَتا (آن دو رفتند)، ذَهَبوا (آن مردان رفتند)، ذَهَبْنَ (آن زنان رفتند).\n• مضارع: یَذْهَبُ (می‌رود مرد)، تَذْهَبُ (می‌رود زن)، یَذْهَبونَ (می‌روند مردان)، یَذْهَبْنَ (می‌روند زنان).",
                memoryCode = "🔑 کد ۱ ثانیه‌ای غایب: «ـوا و یـ...ونَ = آن‌ها (مردان) • ـَتْ = او زن در گذشته • یـ اول فعل = غایب!»",
                leaves = listOf(
                    "ذَهَبَ / ذَهَبَتْ / ذَهَبوا" to "گذشته: او رفت / آن‌ها رفتند",
                    "یَذْهَبُ / یَذْهَبونَ" to "مضارع: او می‌رود / آن‌ها می‌روند"
                )
            ),
            ZoomPanTreeBranch(
                id = "sigha_mokhatab",
                title = "۲. مخاطب (تو / شما)",
                badge = "انگشت وسط و حلقه",
                color = Color(0xFFD97706),
                detailRule = "مخاطب یعنی کسی که روبه‌روی توست (تو / شما):\n• ماضی با «ت» در آخر: ذَهَبْتَ (تو مرد رفتی)، ذَهَبْتِ (تو زن رفتی)، ذَهَبْتُما (شما دو نفر)، ذَهَبْتُمْ (شما مردان رفتید)، ذَهَبْتُنَّ (شما زنان).\n• مضارع با «تـ» در اول: تَذْهَبُ (می‌روی مرد)، تَذْهَبینَ (می‌روی زن)، تَذْهَبونَ (می‌روید شما).",
                memoryCode = "🔑 کد ۱ ثانیه‌ای مخاطب: «ـتَ = تو مرد • ـتِ / تـ...ینَ = تو زن • ـتُم / تـ...ونَ = شما!»",
                leaves = listOf(
                    "ذَهَبْتَ / ذَهَبْتِ / ذَهَبْتُم" to "گذشته: تو رفتی / شما رفتید",
                    "تَذْهَبُ / تَذْهَبینَ / تَذْهَبونَ" to "مضارع: تو می‌روی / شما می‌روید"
                )
            ),
            ZoomPanTreeBranch(
                id = "sigha_motakallem_amr",
                title = "۳. متکلم (من/ما) + امر و نهی",
                badge = "انگشت کوچک",
                color = Color(0xFFE11D48),
                detailRule = "متکلم یعنی گوینده (من و ما):\n• ماضی: ذَهَبْتُ (من رفتم)، ذَهَبْنا (ما رفتیم).\n• مضارع: أَذْهَبُ (من می‌روم)، نَذْهَبُ (ما می‌رویم).\n• امر و نهی: إِذْهَبْ / إِذْهَبوا (برو / بروید!) • لا تَذْهَبْ / لا تَذْهَبوا (نرو / نروید!).",
                memoryCode = "🔑 کد ۱ ثانیه‌ای متکلم: «ـتُ = من رفتم • أَ = من می‌روم • ـنا = ما رفتیم • نَـ = ما می‌رویم!»",
                leaves = listOf(
                    "ذَهَبْتُ / أَذْهَبُ (من)" to "ـتُ (گذشته من) • أَ (مضارع من)",
                    "ذَهَبْنا / نَذْهَبُ (ما)" to "ـنا (گذشته ما) • نَـ (مضارع ما)"
                )
            )
        )
    ),
    ZoomPanTreeModel(
        id = "arabic_erab_tree",
        tabLabel = "۱. درختواره جامع اعراب و نقش‌ها",
        rootTitle = "شجره‌نامه نقش‌ها و اعراب در جمله عربی",
        rootSubtitle = "تشخیص ۳ ثانیه‌ای مرفوع (ـُ)، منصوب (ـَ) و مجرور (ـِ)",
        rootColor = Color(0xFF059669),
        branches = listOf(
            ZoomPanTreeBranch(
                id = "marfooat",
                title = "مرفوعات (ـُ / ونَ)",
                badge = "ضمه و تنوین رفع",
                color = Color(0xFF0284C7),
                detailRule = "هر اسمی که کار را انجام دهد (فاعل)، جانشین فاعل در فعل مجهول (نایب فاعل)، دو رکن جمله اسمیه (مبتدا و خبر)، اسمِ کانَ و خبرِ إِنَّ مرفوع است.",
                memoryCode = "🔑 کد رمزی مرفوعات: «فاعل + نایب فاعل + مبتدا و خبر + اسم کان + خبر إنّ = همیشه مرفوع!»",
                leaves = listOf(
                    "فاعل و نایب فاعل" to "بعد از فعل معلوم/مجهول",
                    "مبتدا، خبر و اسم کان" to "ارکان جمله اسمیه"
                )
            ),
            ZoomPanTreeBranch(
                id = "mansoobat",
                title = "منصوبات (ـَ / ینَ)",
                badge = "فتحه و تنوین نصب",
                color = Color(0xFFE11D48),
                detailRule = "مفعول‌به (چه چیزی را؟)، حال (چگونه؟ مشتق نکره)، تمییز (رفع ابهام عدد/نسبت)، مفعول مطلق (مصدر هم‌ریشه فعل)، مستثنی بعد از إلّا، اسمِ إِنَّ و خبرِ کانَ.",
                memoryCode = "🔑 کد رمزی منصوبات: «مفعول + حال + تمییز + اسم إنّ + خبر کان + اسم لای نفی جنس = منصوب!»",
                leaves = listOf(
                    "مفعول‌به و مفعول مطلق" to "پاسخ چه را و مصدر هم‌ریشه",
                    "حال، تمییز و اسم إنّ" to "قید حالت، رفع ابهام و نواسخ"
                )
            ),
            ZoomPanTreeBranch(
                id = "majroorat",
                title = "مجرورات (ـِ / ینَ)",
                badge = "کسره و تنوین جر",
                color = Color(0xFF059669),
                detailRule = "فقط در ۲ حالت اسم مجرور می‌شود: ۱. بعد از حروف جر (مِن، إلی، عَن، عَلی، فی، بِـ، کَـ، لِـ) • ۲. مضاف‌الیه (اسم دوم در ترکیب اضافی، بعد از اسم بدون ال و تنوین).",
                memoryCode = "🔑 کد رمزی مجرورات: «حرف جر + مضاف‌الیه = فقط همین ۲ مورد کسره (ـِ) می‌گیرند!»",
                leaves = listOf(
                    "مجرور به حرف جر" to "بعد از فی، مِن، عَلی، بِـ",
                    "مضاف‌الیه" to "اسم بعد از کلمه بدون ال و تنوین"
                )
            )
        )
    ),
    ZoomPanTreeModel(
        id = "arabic_abwab_tree",
        tabLabel = "۲. درختواره ۸ باب ثلاثی مزید و مشتقات",
        rootTitle = "ماشین تشخیص ۸ باب مزید و مشتقات عربی",
        rootSubtitle = "فرمول چشمی ماضی، مضارع، مصدر و اسم فاعل/مفعول",
        rootColor = Color(0xFF7C3AED),
        branches = listOf(
            ZoomPanTreeBranch(
                id = "abwab_hamza_shadda",
                title = "إفعال، تفعیل، مفاعلة",
                badge = "مضارع با «یُـ»",
                color = Color(0xFF2563EB),
                detailRule = "هر ۳ باب در مضارع با «یُـ» شروع می‌شوند! إفعال (أَفْعَلَ/یُفْعِلُ/إِفْعال) • تفعیل (فَعَّلَ/یُفَعِّلُ/تَفْعیل) • مفاعلة (فاعَلَ/یُفاعِلُ/مُفاعَلَة).",
                memoryCode = "🔑 کد رمزی: «دیدن یُـ در اول مضارع = یکی از ۳ باب إفعال، تفعیل یا مفاعلة (یا مجهول)!»",
                leaves = listOf(
                    "إِفْعال و تَفْعیل" to "أَکْرَمَ/إِکْرام • عَلَّمَ/تَعْلیم",
                    "مُفاعَلَة (جِهاد)" to "جاهَدَ / یُجاهِدُ / مُجاهَدَة"
                )
            ),
            ZoomPanTreeBranch(
                id = "abwab_ta_starting",
                title = "تفعّل و تفاعل",
                badge = "مصدر با ضمه قبل از آخر",
                color = Color(0xFFD97706),
                detailRule = "تفعّل (تَفَعَّلَ/یَتَفَعَّلُ/تَفَعُّل) و تفاعل (تَفاعَلَ/یَتَفاعَلُ/تَفاعُل): تنها باب‌هایی هستند که مصدرشان با «تَـ» شروع شده و حرف یکی مانده به آخرش ضمه (ـُ) دارد!",
                memoryCode = "🔑 تله کنکور: «تَعَلُّم و تَعاوُن فعل مضارع نیستند؛ مصدر باب تفعّل و تفاعل هستند!»",
                leaves = listOf(
                    "باب تَفَعُّل" to "تَعَلَّمَ / یَتَعَلَّمُ / تَعَلُّم",
                    "باب تَفاعُل" to "تَعاوَنَ / یَتَعاوَنُ / تَعاوُن"
                )
            ),
            ZoomPanTreeBranch(
                id = "abwab_hamza_wasl",
                title = "افتعال، انفعال، استفعال",
                badge = "همزه وصل «اِ»",
                color = Color(0xFF059669),
                detailRule = "افتعال (اِفْتَعَلَ: ت بعد از حرف اول ریشه مثل اِکْتَسَبَ) • انفعال (اِنْفَعَلَ: ن قبل از ریشه، معنی مجهول مثل اِنْکَسَرَ) • استفعال (اِسْتَفْعَلَ: پیشوند استـ مثل اِسْتَغْفَرَ).",
                memoryCode = "🔑 کد رمزی: «اِنْـ = انفعال (مجهول) • اِسْتـ = استفعال (طلب) • ت وسط = افتعال»",
                leaves = listOf(
                    "اِفْتِعال و اِنْفِعال" to "اِکْتِساب • اِنْکِسار (شکسته شد)",
                    "اِسْتِفْعال" to "اِسْتِغْفار و اِسْتِخْراج"
                )
            )
        )
    )
)

private fun buildAllSubjectsZoomPanTrees(): List<ZoomPanTreeModel> = listOf(
    ZoomPanTreeModel(
        id = "english_visual_tree",
        tabLabel = "۱. درختواره زبان انگلیسی (از زیر صفر تا نهایی)",
        rootTitle = "شجره‌نامه تصویری زبان انگلیسی (جمله‌سازی، زمان‌ها و گرامر)",
        rootSubtitle = "فرمول قطار ۳ واگنه S+V+O، ۸ زمان، مجهول، شرطی و Tag Question",
        rootColor = Color(0xFF0284C7),
        branches = listOf(
            ZoomPanTreeBranch(
                id = "eng_train_tenses",
                title = "قطار جمله‌سازی و زمان‌ها",
                badge = "Subject + Verb + Object",
                color = Color(0xFF0284C7),
                detailRule = "در انگلیسی برعکس فارسی، «فعل» بلافاصله بعد از «فاعل» می‌آید: Subject (فاعل) + Verb (فعل) + Object (مفعول) + Place (مکان) + Time (زمان).\n• حال ساده: V / V+s (every day)\n• گذشته ساده: V-ed / V2 (yesterday, last, ago)\n• ماضی نقلی: have/has + P.P. (since, for, already, yet)\n• ماضی بعید: had + P.P. (قبل از گذشته ساده با before/after/because).",
                memoryCode = "🔑 کد رمزی زمان‌ها: «yesterday = گذشته ساده • since/for = have/has + P.P. • before/after با گذشته = had + P.P.!»",
                leaves = listOf(
                    "قطار ۳ واگنه S + V + O" to "Ali (فاعل) + reads (فعل) + a book (مفعول)",
                    "ماضی نقلی و ماضی بعید" to "have/has + P.P. • had + P.P."
                )
            ),
            ZoomPanTreeBranch(
                id = "eng_passive_tag",
                title = "مجهول و Tag Question",
                badge = "Be + P.P. و آینه",
                color = Color(0xFFE11D48),
                detailRule = "• جملات مجهول (Passive): وقتی شیء بی‌جان اول جمله بیاید یا کلمه «by» بعد از جای خالی باشد ⬅ فرمول قطعی: am/is/are/was/were/been + P.P. (قسمت سوم فعل).\n• دنباله سؤالی (Tag Question): قانون آینه! جمله مثبت ⬅ تگ منفی؛ جمله منفی (یا دارای never, hardly, seldom, little, few) ⬅ تگ مثبت!",
                memoryTrickOrCode = "🔑 کد رمزی: «دیدن by = گزینه دارای to be + P.P. • دیدن never/hardly = تگ مثبت!»"
            ),
            ZoomPanTreeBranch(
                id = "eng_conditionals_relatives",
                title = "شرطی ۱ و ۲ + ضمایر موصولی",
                badge = "If و who/whom/which",
                color = Color(0xFF059669),
                detailRule = "• شرطی نوع ۱: If + حال ساده ⬅ will/can + فعل ساده.\n• شرطی نوع ۲ (خیالی): If + گذشته ساده (یا were برای همه اشخاص!) ⬅ would/could + فعل ساده.\n• ضمایر موصولی: انسان + فعل = who • انسان + فاعل (ضمیر/اسم) = whom • شیء یا حیوان = which.",
                memoryCode = "🔑 کد رمزی: «انسان + فعل = who • انسان + فاعل = whom • If I were you = I would...»",
                leaves = listOf(
                    "شرطی نوع ۱ و ۲" to "If + Present ➔ will • If + Past ➔ would",
                    "ضمایر موصولی" to "who (فاعل) • whom (مفعول) • which (اشیاء)"
                )
            )
        )
    ),
    ZoomPanTreeModel(
        id = "math_humanities_tree",
        tabLabel = "۲. درختواره ضدقفل ریاضی و آمار انسانی",
        rootTitle = "شجره‌نامه تیپ‌شناسی ریاضی و آمار کنکور انسانی",
        rootSubtitle = "شگردهای ۵ ثانیه‌ای بدون فرمول برای معادله، تابع، منطق، دنباله و آمار",
        rootColor = Color(0xFF1D4ED8),
        branches = listOf(
            ZoomPanTreeBranch(
                id = "math_eq_func",
                title = "معادله درجه ۲ و تابع",
                badge = "پایه دهم و یازدهم",
                color = Color(0xFF1D4ED8),
                detailRule = "• معادله ax² + bx + c = 0: اگر جمع ضرایب صفر شد (a+b+c=0) ریشه‌ها ۱ و c/a هستند! اگر a−b+c=0 شد ریشه‌ها −۱ و −c/a هستند! جمع ریشه‌ها S = −b/a و ضرب ریشه‌ها P = c/a.\n• تابع: در زوج‌مرتب‌ها مؤلفه اول تکراری با مؤلفه دوم متفاوت نباشد! دامنه رادیکالی: زیر رادیکال ≥ ۰؛ کسری: مخرج ≠ ۰.",
                memoryCode = "🔑 کد رمزی: «جمع ضرایب صفر = یکی ۱ و یکی آخر به اول (c/a)!»",
                leaves = listOf(
                    "شگرد معادله درجه ۲" to "a+b+c=0 ➔ x=1, c/a • S=-b/a, P=c/a",
                    "دامنه و زوج‌مرتب تابع" to "مخرج ≠ ۰ و زیر رادیکال ≥ ۰"
                )
            ),
            ZoomPanTreeBranch(
                id = "math_logic_seq",
                title = "منطق ریاضی و دنباله‌ها",
                badge = "یازدهم و دوازدهم",
                color = Color(0xFF7C3AED),
                detailRule = "• منطق ریاضی: گزاره شرطی (p ⟹ q) فقط وقتی نادرست (F) است که p درست (T) و q نادرست (F) باشد!\n• دنباله حسابی: هر جمله با جمع عددی ثابت (d) ساخته می‌شود (2b = a + c).\n• دنباله هندسی: هر جمله با ضرب در عددی ثابت (q) ساخته می‌شود (b² = a × c).",
                memoryCode = "🔑 کد رمزی: «در دنباله‌ها به جای n عدد ۱ و ۲ بگذار و گزینه‌ها را خط بزن!»",
                leaves = listOf(
                    "منطق ریاضی (p ⟹ q)" to "فقط T ⟹ F نادرست است!",
                    "دنباله حسابی و هندسی" to "حسابی: 2b=a+c • هندسی: b²=a×c"
                )
            ),
            ZoomPanTreeBranch(
                id = "math_stats_prob",
                title = "آمار، واریانس و احتمال",
                badge = "دهم و دوازدهم",
                color = Color(0xFF059669),
                detailRule = "• تغییر داده‌ها (y = kx + b): میانگین و میانه در k ضرب و با b جمع می‌شوند؛ اما انحراف معیار فقط در |k| و واریانس در k² ضرب می‌شود (جمع b هیچ اثری ندارد!).\n• شمارش و احتمال: کلمه «وَ» یعنی ضرب (×) و «یا» یعنی جمع (+). احتمالِ حداقل یکی = ۱ منهای احتمالِ هیچ‌کدام.",
                memoryCode = "🔑 کد رمزی آمار: «جمع و تفریق عدد ثابت، واریانس و انحراف معیار را تکان نمی‌دهد!»",
                leaves = listOf(
                    "واریانس و انحراف معیار" to "جمع بی‌اثر است • ضرب در k² و |k|",
                    "شمارش و احتمال" to "وَ = ضرب (×) • یا = جمع (+)"
                )
            )
        )
    ),
    ZoomPanTreeModel(
        id = "fonoon_arooz_tree",
        tabLabel = "۳. درختواره علوم و فنون ادبی و عروض",
        rootTitle = "شجره‌نامه جامع علوم و فنون ۱، ۲ و ۳ (آرایه، عروض و سبک)",
        rootSubtitle = "تشخیص چشمی آرایه‌ها، تقطیع ۴ هجای اول و کلیدواژه سبک‌ها",
        rootColor = Color(0xFF9333EA),
        branches = listOf(
            ZoomPanTreeBranch(
                id = "fonoon_bayan",
                title = "تشبیه، استعاره، مجاز، کنایه",
                badge = "آرایه‌های بیان (یازدهم)",
                color = Color(0xFF9333EA),
                detailRule = "• تفکیک تشبیه بلیغ و استعاره مکنیه در ترکیب اضافی (Aِ B): جای دو کلمه را برعکس کن و بینشان «مثلِ» بگذار (B مثلِ A است): اگر معنی داد (دریای عشق ⬅ عشق مثل دریاست) = تشبیه بلیغ؛ اگر معنی نداد (دست روزگار ⬅ روزگار مثل دست است!) = استعاره مکنیه.\n• کنایه: جمله یا ترکیبی که معنی ظاهری‌اش مقصود نیست (پشت پا زدن، دهان دوختن).",
                memoryCode = "🔑 کد رمزی: «برعکس کن + مثلِ بگذار: معنی داد = تشبیه بلیغ • معنی نداد = استعاره مکنیه!»",
                leaves = listOf(
                    "تشبیه بلیغ 🆚 استعاره" to "تست برعکس کردن + «مثلِ»",
                    "مجاز و کنایه" to "کلمه در غیر معنی اصلی • جمله کنایی"
                )
            ),
            ZoomPanTreeBranch(
                id = "fonoon_badie",
                title = "اسلوب معادله، ایهام، پارادوکس",
                badge = "آرایه‌های دوازدهم",
                color = Color(0xFFE11D48),
                detailRule = "• اسلوب معادله: دو مصراع مستقل که بینشان کلمه «همان‌طور که / آری» قرار می‌گیرد و جای دو مصراع قابل تعویض است.\n• متناقض‌نما (پارادوکس): دو واژه متضاد در یک ترکیب به یک چیز نسبت داده شوند (دولتِ فقر، گنجِ غم).\n• ایهام: ۱ واژه با ۲ معنی که هر دو به بیت می‌آید.",
                memoryCode = "🔑 کد رمزی: «قرار گرفتن همان‌طور که بین دو مصراع = ۱۰۰٪ اسلوب معادله!»",
                leaves = listOf(
                    "اسلوب معادله و تمثیل" to "همان‌طور که بین دو مصراع مستقل",
                    "ایهام و متناقض‌نما" to "۲ معنی هم‌زمان • ترکیب دو ضد"
                )
            ),
            ZoomPanTreeBranch(
                id = "fonoon_arooz_styles",
                title = "عروض ۴ هجایی و ۴ سبک ادبی",
                badge = "عروض و تاریخ ادبیات",
                color = Color(0xFFD97706),
                detailRule = "• عروض: فقط ۴ هجای اول بیت را تقطیع کن! U – – – (مفاعیلن) • – U – – (فاعلاتن) • – – U – (مستفعلن) • U – – (فعولن). اگر وسط مصراع مکث و تقارن داشت = وزن دوری!\n• سبک‌ها: قرن ۴و۵ خراسانی (حماسه و طبیعت) • ۶تا۹ عراقی (عشق و عرفان) • ۱۰تا۱۲ هندی (اسلوب معادله و تک‌بیت) • ۱۳ بازگشت • ۱۴ مشروطه.",
                memoryCode = "🔑 کد رمزی عروض: «مکث وسط مصراع = وزن دوری • U – – = فعولن (شاهنامه و بوستان)!»",
                leaves = listOf(
                    "کد ۴ هجای اول عروض" to "مفاعیلن (U---) • فاعلاتن (-U--)",
                    "۴ سبک شعر فارسی" to "خراسانی • عراقی • هندی • بازگشت و مشروطه"
                )
            )
        )
    ),
    ZoomPanTreeModel(
        id = "humanities_core_tree",
        tabLabel = "۴. درختواره فلسفه، منطق، جامعه‌شناسی و اقتصاد",
        rootTitle = "شجره‌نامه دروس تحلیلی انسانی (فلسفه، منطق، جامعه‌شناسی، روان‌شناسی، اقتصاد)",
        rootSubtitle = "کلیدواژه‌های ۱۰۰٪ تضمینی برای شکار تست‌های مفهومی در ۱۰ ثانیه",
        rootColor = Color(0xFF0D9488),
        branches = listOf(
            ZoomPanTreeBranch(
                id = "phil_logic_branch",
                title = "منطق ۱۰ و فلسفه ۱۱ و ۱۲",
                badge = "کلیات خمس تا ملاصدرا",
                color = Color(0xFF6D28D9),
                detailRule = "• منطق ۱۰: کلیات خمس (نوع=انسان، جنس=حیوان، فصل=ناطق، عرض خاص=ضاحک، عرض عام=ماشی) • قیاس استثنایی (فقط وضع مقدم و رفع تالی معتبرند).\n• فلسفه ۱۲: مشاء (ابن‌سینا: عقل استدلالی) • اشراق (سهروردی: عقل + شهود) • حکمت متعالیه (ملاصدرا: اصالت وجود، تشکیک وجود، حرکت جوهری).",
                memoryCode = "🔑 کد رمزی: «وضع مقدم و رفع تالی = صحیح • ملاصدرا = اصالت وجود و حرکت جوهری!»",
                leaves = listOf(
                    "منطق دهم (کلیات و قیاس)" to "فصل = ذاتی جداکننده • وضع مقدم/رفع تالی",
                    "فلسفه یازدهم و دوازدهم" to "مشاء • اشراق • حکمت متعالیه ملاصدرا"
                )
            ),
            ZoomPanTreeBranch(
                id = "soc_3_approaches",
                title = "۳ رویکرد جامعه‌شناسی",
                badge = "تبیینی، تفسیری، انتقادی",
                color = Color(0xFF0D9488),
                detailRule = "• تبیینی (پوزیتیویستی - کنت و دورکیم): شبیه‌سازی جامعه با طبیعت، کشف قوانین تجربی و کمی، نادیده گرفتن معنا.\n• تفسیری (وبر): فهم معنا و نیت کنشگران، نسبی‌گرایی فرهنگی.\n• انتقادی (مارکس): نقد سلطه و سرمایه‌داری و رهایی محرومان.",
                memoryCode = "🔑 کد رمزی جامعه‌شناسی: «قانون طبیعی = تبیینی • فهم معنا = تفسیری • نقد سلطه = انتقادی!»",
                leaves = listOf(
                    "تبیینی (دورکیم) و تفسیری (وبر)" to "کشف قانون حسی 🆚 فهم معنای کنش",
                    "انتقادی و جهان اسلام" to "نقد سلطه • سنت‌های الهی و بیداری اسلامی"
                )
            ),
            ZoomPanTreeBranch(
                id = "psych_econ_branch",
                title = "روان‌شناسی ۱۱ و اقتصاد ۱۰",
                badge = "شرطی‌سازی و مسائل اقتصاد",
                color = Color(0xFFD97706),
                detailRule = "• روان‌شناسی: شرطی‌سازی کلاسیک پاولف (بازتاب غیرارادی) در برابر کنشی اسکینر (تقویت/تنبیه رفتار ارادی) • تداخل پیش‌گستر (قدیم مانع جدید) و پس‌گستر (جدید مانع قدیم).\n• اقتصاد: سود حسابداری = درآمد − هزینه آشکار • سود اقتصادی = سود حسابداری − هزینه فرصت (پنهان).",
                memoryCode = "🔑 کد رمزی: «پاولف = غیرارادی • اسکینر = تقویت/تنبیه • سود اقتصادی = کسر هر دو هزینه!»",
                leaves = listOf(
                    "روان‌شناسی (یادگیری و حافظه)" to "کلاسیک/کنشی • پیش‌گستر/پس‌گستر",
                    "اقتصاد (سود، عرضه و بانکداری)" to "سود اقتصادی • مضاربه (تجارت) و جعاله"
                )
            )
        )
    )
)

private fun ZoomPanTreeBranch(
    id: String,
    title: String,
    badge: String,
    color: Color,
    detailRule: String,
    memoryTrickOrCode: String
) = ZoomPanTreeBranch(
    id = id,
    title = title,
    badge = badge,
    color = color,
    detailRule = detailRule,
    memoryCode = memoryTrickOrCode,
    leaves = listOf(
        "مجهول (Passive)" to "Be + P.P. (تبدیل مفعول به اول جمله)",
        "Tag Question" to "مثبت ➔ منفی • منفی ➔ مثبت"
    )
)

private fun buildStepByStepGrammarScenarios(): List<StepByStepGrammarScenario> = listOf(
    StepByStepGrammarScenario(
        id = "arabic_8_baabs_mold_anim",
        categoryBadge = "🧱 عربی از زیرِ صفر",
        title = "انیمیشن قالب کیک‌پزی ۸ باب ثلاثی مزید (با ۸ کلمه روزمره فارسی!)",
        accentColor = Color(0xFF047857),
        frames = listOf(
            AnimatedGrammarStepFrame(
                stepNumber = 1,
                stepTitle = "گام ۱: فرق «ثلاثی مجرد» و «ثلاثی مزید» چیست؟",
                formulaBadge = "مجرد = ۳ حرف لُخت (سبز) | مزید = ۳ حرف اصلی (سبز) + حروف اضافه (قرمز)",
                tokens = listOf(
                    AnimatedGrammarToken("خَرَجَ (خ+ر+ج)", "ثلاثی مجرد (بیرون رفت)", Color(0xFF059669), true),
                    AnimatedGrammarToken("+ أَ (اضافه) ➔ أَخْرَجَ", "باب إفعال (اخراج کرد!)", Color(0xFFDC2626), true),
                    AnimatedGrammarToken("+ اِسْتَـ (اضافه) ➔ اِسْتَخْرَجَ", "باب استفعال (استخراج کرد!)", Color(0xFF7C3AED), true)
                ),
                explanation = "«ثلاثی» یعنی ۳ حرفی! وقتی ۳ حرف اصلی (خ-ر-ج) تنها باشند به آن «ثلاثی مجرد» می‌گویند؛ وقتی آن‌ها را در یکی از ۸ قالب آماده بریزیم و حرف اضافه (قرمز) به آن بچسبانیم، به آن «ثلاثی مزید (باب)» می‌گویند!",
                memoryTrick = "کد ۱ ثانیه‌ای: «مجرد = ۳ حرف تنها • مزید = ۳ حرف اصلی + ۱ تا ۳ حرف اضافه!»"
            ),
            AnimatedGrammarStepFrame(
                stepNumber = 2,
                stepTitle = "گام ۲: ۳ باب اول که مضارعشان با «یُـ» (ضمه) شروع می‌شود!",
                formulaBadge = "إفعال (اِخراج) • تفعیل (تَعلیم) • مفاعلة (مُسابِقه) ⬅ مضارع با «یُـ»!",
                tokens = listOf(
                    AnimatedGrammarToken("أَخْرَجَ / یُخْرِجُ / إِخْراج", "۱. باب إفعال (رمز: اخراج)", Color(0xFF047857), true),
                    AnimatedGrammarToken("عَلَّمَ / یُعَلِّمُ / تَعْلیم", "۲. باب تفعیل (رمز: تعلیم - تشدید وسط)", Color(0xFF2563EB), true),
                    AnimatedGrammarToken("شاهَدَ / یُشاهِدُ / مُشاهَدَة", "۳. باب مفاعلة (رمز: مشاهده - الف دوم)", Color(0xFFD97706), true)
                ),
                explanation = "در هر ۳ بابِ إفعال، تفعیل و مفاعلة، فعل مضارع با «یُـ» (ضمه) شروع می‌شود! اگر تشدید وسط داشت = تفعیل؛ اگر الف بعد از حرف اول داشت = مفاعلة؛ اگر هیچ‌کدام را نداشت = إفعال!",
                memoryTrick = "کد رمزی: «دیدن یُـ در اول مضارع معلوم = شک نکن یکی از ۳ باب إفعال، تفعیل یا مفاعلة است!»"
            ),
            AnimatedGrammarStepFrame(
                stepNumber = 3,
                stepTitle = "گام ۳: ۲ باب خواهر که اولشان «تَـ» دارد (تَفَعُّل و تَفاعُل)",
                formulaBadge = "تَفَعُّل (تَشَکُّر: ت + تشدید) • تَفاعُل (تَصادُف: ت + الف)",
                tokens = listOf(
                    AnimatedGrammarToken("تَعَلَّمَ / یَتَعَلَّمُ / تَعَلُّم", "۴. باب تفعّل (ت + تشدید)", Color(0xFF7C3AED), true),
                    AnimatedGrammarToken("تَکاتَبَ / یَتَکاتَبُ / تَکاتُب", "۵. باب تفاعل (ت + الف)", Color(0xFFE11D48), true)
                ),
                explanation = "هر دو با «تَـ» شروع می‌شوند! اگر وسطش تشدید داشت باب تفعّل (مثل تشکّر) است؛ اگر بعد از حرف اول الف داشت باب تفاعل (مثل تصادف) است. مصدر هر دو با ضمه (تَعَلُّم / تَکاتُب) ساخته می‌شود!",
                memoryTrick = "تله کنکور: «تَعَلَّمَ (با فتحه) = فعل ماضی • تَعَلُّم (با ضمه) = مصدر!»"
            ),
            AnimatedGrammarStepFrame(
                stepNumber = 4,
                stepTitle = "گام ۴: ۳ باب با همزه وصل در اول (افتعال، انفعال، استفعال)",
                formulaBadge = "افتعال (اِحتِرام) • انفعال (اِنفِجار) • استفعال (اِستِخدام)",
                tokens = listOf(
                    AnimatedGrammarToken("اِحْتَرَمَ / یَحْتَرِمُ / اِحْتِرام", "۶. باب افتعال (ت بعد از حرف اول ریشه)", Color(0xFF0284C7), true),
                    AnimatedGrammarToken("اِنْکَسَرَ / یَنْکَسِرُ / اِنْکِسار", "۷. باب انفعال (اِنـ / یَنـ = شد!)", Color(0xFF059669), true),
                    AnimatedGrammarToken("اِسْتَخْرَجَ / یَسْتَخْرِجُ / اِسْتِخْراج", "۸. باب استفعال (اِسْتَـ / یَسْتَـ)", Color(0xFFD97706), true)
                ),
                explanation = "در باب افتعال یک «ت» بعد از حرف اول ریشه قایم شده (اِحْتَرَمَ)؛ در باب انفعال اولش «اِنـ» دارد و معنی «شدن» می‌دهد (اِنْکَسَرَ = شکسته شد)؛ در باب استفعال اولش «اِسْتَـ» دارد!",
                memoryTrick = "کد رمزی: «اِنـ = انفعال (شدن) • اِسْتَـ = استفعال • ت بعد از حرف اول = افتعال!»"
            )
        )
    ),
    StepByStepGrammarScenario(
        id = "english_relative_pronouns_box_anim",
        categoryBadge = "🇬🇧 زبان یازدهم و دوازدهم",
        title = "انیمیشن جعبه‌های چپ و راست در ضمایر موصولی (who / whom / which / whose)",
        accentColor = Color(0xFF0284C7),
        frames = listOf(
            AnimatedGrammarStepFrame(
                stepNumber = 1,
                stepTitle = "فرمول ۱: وقتی کلمهٔ سمت چپِ جای خالی «شیء یا حیوان» است!",
                formulaBadge = "[شیء / حیوان: book, car, cat] + 👉 which + [هر چیزی]",
                tokens = listOf(
                    AnimatedGrammarToken("The book (کتاب = شیء)", "۱. نگاه به چپِ جای خالی", Color(0xFF059669), true),
                    AnimatedGrammarToken("👉 which", "پاسخ ۱۰۰٪ قطعی!", Color(0xFFE11D48), true),
                    AnimatedGrammarToken("is on the desk", "ادامه جمله", Color(0xFF64748B), false)
                ),
                explanation = "اصلاً لازم نیست جمله را ترجمه کنی! به کلمه قبل از جای خالی نگاه کن: اگر انسان نبود (شیء، مکان یا حیوان بود مثل book, car, cat, dictionary)، در ۱ ثانیه گزینه «which» را بزن!",
                memoryTrick = "کد ۱ ثانیه‌ای: «غیرانسان قبل از جای خالی = which!»"
            ),
            AnimatedGrammarStepFrame(
                stepNumber = 2,
                stepTitle = "فرمول ۲: وقتی سمت چپ «انسان» و سمت راست «فعل» است!",
                formulaBadge = "[انسان: man, woman, doctor] + 👉 who + [فعل: is, was, works, helped]",
                tokens = listOf(
                    AnimatedGrammarToken("The woman (انسان)", "۱. سمت چپ = آدم", Color(0xFF059669), true),
                    AnimatedGrammarToken("👉 who", "پاسخ قطعی فاعلی!", Color(0xFF0284C7), true),
                    AnimatedGrammarToken("helped us (فعل)", "۲. سمت راست = فعل!", Color(0xFF7C3AED), true)
                ),
                explanation = "اگر قبل از جای خالی انسان بود (The woman, The boy, The teacher) و بلافاصله بعد از جای خالی فعل آمد (is, was, can, works, helped)، گزینه «who» ۱۰۰٪ درست است!",
                memoryTrick = "کد رمزی: «انسان + who + فعل!»"
            ),
            AnimatedGrammarStepFrame(
                stepNumber = 3,
                stepTitle = "فرمول ۳: وقتی سمت چپ «انسان» و سمت راست «فاعل (I/you/we/they...)» است!",
                formulaBadge = "[انسان (یا حرف اضافه to/with)] + 👉 whom + [فاعل: I, you, we, they, he, she]",
                tokens = listOf(
                    AnimatedGrammarToken("The doctor (انسان)", "۱. سمت چپ = آدم", Color(0xFF059669), true),
                    AnimatedGrammarToken("👉 whom", "پاسخ قطعی مفعولی!", Color(0xFFE11D48), true),
                    AnimatedGrammarToken("we met (ضمیر فاعلی we)", "۲. سمت راست = فاعل!", Color(0xFFD97706), true)
                ),
                explanation = "اگر قبل از جای خالی انسان بود و بعد از جای خالی یک ضمیر فاعلی (I, you, he, she, we, they) یا اسم آمد، یا قبل از جای خالی حرف اضافه (to/with/for) بود، گزینه «whom» درست است!",
                memoryTrick = "کد رمزی: «انسان + whom + ضمیر فاعلی (I/you/we/they) یا بعد از to/with!»"
            ),
            AnimatedGrammarStepFrame(
                stepNumber = 4,
                stepTitle = "فرمول ۴: وقتی بعد از جای خالی «اسمِ بدون a/the (مالکیت)» می‌آید!",
                formulaBadge = "[انسان / شیء] + 👉 whose (که ...اش) + [اسم ملکی: car, father, name]",
                tokens = listOf(
                    AnimatedGrammarToken("The boy (پسر)", "۱. سمت چپ = صاحب", Color(0xFF059669), false),
                    AnimatedGrammarToken("👉 whose", "پاسخ قطعی ملکی!", Color(0xFF7C3AED), true),
                    AnimatedGrammarToken("car was stolen (ماشینش)", "۲. سمت راست = اسم بدون a/the!", Color(0xFFE11D48), true)
                ),
                explanation = "اگر بعد از جای خالی یک اسم بدون a/an/the آمد که متعلق به کلمه قبل است (مثلاً The boy whose car... = پسری که ماشینش...)، گزینه «whose» را بزن!",
                memoryTrick = "کد رمزی: «انسان + whose + اسمِ لُخت (بدون a/an/the)!»"
            )
        )
    ),
    StepByStepGrammarScenario(
        id = "arabic_14_sigha_hand_anim",
        categoryBadge = "🖐️ عربی از زیرِ صفر",
        title = "انیمیشن ۱۴ صیغه عربی با کد ۵ انگشت دست و برش رنگی اول و آخر فعل",
        accentColor = Color(0xFF047857),
        frames = listOf(
            AnimatedGrammarStepFrame(
                stepNumber = 1,
                stepTitle = "گام ۱: راز طلایی تشخیص ماضی و مضارع در ۱ ثانیه!",
                formulaBadge = "ماضی = علامت فقط در آخرِ فعل | مضارع = (أ / نـ / یـ / تـ) در اولِ فعل",
                tokens = listOf(
                    AnimatedGrammarToken("ذَهَبْـ + ـتُ", "ماضی (نشانه در آخر = رفتم)", Color(0xFF047857), true),
                    AnimatedGrammarToken("أَ + ـذْهَبُ", "مضارع (نشانه در اول = می‌روم)", Color(0xFF2563EB), true),
                    AnimatedGrammarToken("سَـ + یَذْهَبُ", "مستقبل (سـ = خواهد رفت)", Color(0xFFD97706), false)
                ),
                explanation = "اگر اولِ فعل یکی از ۴ حرف «أ، نـ، یـ، تـ» را نداشت، ۱۰۰٪ فعل ماضی (گذشته) است و فقط باید به آخرش نگاه کنی! اگر اولش أ/نـ/یـ/تـ داشت، فعل مضارع (حال/آینده) است!",
                memoryTrick = "کد ۱ ثانیه‌ای: «ماضی = نگاه به آخر فعل • مضارع = نگاه به اول و آخر فعل!»"
            ),
            AnimatedGrammarStepFrame(
                stepNumber = 2,
                stepTitle = "گام ۲: انگشت شست و اشاره — غایب (او مرد، او زن، آن‌ها)",
                formulaBadge = "غایب = کسی که اینجا نیست (او / آن‌ها)",
                tokens = listOf(
                    AnimatedGrammarToken("ذَهَبَ / یَذْهَبُ", "او ۱ مرد (رفت / می‌رود)", Color(0xFF0284C7), true),
                    AnimatedGrammarToken("ذَهَبَتْ / تَذْهَبُ", "او ۱ زن (ـَتْ ساکن در ماضی)", Color(0xFFE11D48), true),
                    AnimatedGrammarToken("ذَهَبوا / یَذْهَبونَ", "آن‌ها مردان (ـوا / یـ...ونَ)", Color(0xFF059669), true)
                ),
                explanation = "در غایب: بدون پسوند (ذَهَبَ) یعنی «او مرد رفت»؛ «ـَتْ» ساکن (ذَهَبَتْ) یعنی «او زن رفت»؛ «ـوا» در ماضی و «یـ...ونَ» در مضارع یعنی «آن‌ها رفتند / می‌روند».",
                memoryTrick = "کد رمزی: «ـوا و یـ...ونَ = آن‌ها • ـَتْ (با سکون) = او زن!»"
            ),
            AnimatedGrammarStepFrame(
                stepNumber = 3,
                stepTitle = "گام ۳: انگشت وسط و حلقه — مخاطب (تو مرد، تو زن، شما)",
                formulaBadge = "مخاطب = کسی که روبه‌روی توست (همیشه «ت» دارد!)",
                tokens = listOf(
                    AnimatedGrammarToken("ذَهَبْتَ / تَذْهَبُ", "تو ۱ مرد (ـتَ / تـ)", Color(0xFFD97706), true),
                    AnimatedGrammarToken("ذَهَبْتِ / تَذْهَبینَ", "تو ۱ زن (ـتِ / تـ...ینَ)", Color(0xFFE11D48), true),
                    AnimatedGrammarToken("ذَهَبْتُمْ / تَذْهَبونَ", "شما مردان (ـتُم / تـ...ونَ)", Color(0xFF7C3AED), true)
                ),
                explanation = "تمام صیغه‌های مخاطب (تو و شما) چه در ماضی و چه در مضارع حرف «ت» دارند! در ماضی «ـتَ/ـتِ/ـتُم» در آخر فعل می‌آید و در مضارع «تـ» در اول فعل می‌آید.",
                memoryTrick = "کد رمزی: «تَ = تو مرد • تِ / ینَ = تو زن • تُم / تـ...ونَ = شما!»"
            ),
            AnimatedGrammarStepFrame(
                stepNumber = 4,
                stepTitle = "گام ۴: انگشت کوچک — متکلم (من و ما) + امر و نهی",
                formulaBadge = "من = (ـتُ / أَ) | ما = (ـنا / نَـ) | امر = حذف تـ و ساکن کردن آخر",
                tokens = listOf(
                    AnimatedGrammarToken("ذَهَبْتُ / أَذْهَبُ", "من (رفتم / می‌روم)", Color(0xFF047857), true),
                    AnimatedGrammarToken("ذَهَبْنا / نَذْهَبُ", "ما (رفتیم / می‌رویم)", Color(0xFF2563EB), true),
                    AnimatedGrammarToken("إِذْهَبْ / لا تَذْهَبْ", "امر (برو!) و نهی (نرو!)", Color(0xFFE11D48), true)
                ),
                explanation = "برای «من»: در ماضی «ـتُ» (ذَهَبْتُ = رفتم) و در مضارع «أَ» (أَذْهَبُ = می‌روم). برای «ما»: در ماضی «ـنا» (ذَهَبْنا = رفتیم) و در مضارع «نَـ» (نَذْهَبُ = می‌رویم)!",
                memoryTrick = "کد طلایی کنکور: «تُ با ضمه = من رفتم • أَ اول مضارع = من می‌روم • نا و نـ = ما!»"
            )
        )
    ),
    StepByStepGrammarScenario(
        id = "english_visual_train_anim",
        categoryBadge = "🚂 زبان انگلیسی از زیر صفر",
        title = "انیمیشن قطار ۳ واگنه جمله‌سازی و تشخیص تصویری ۸ زمان انگلیسی",
        accentColor = Color(0xFF0284C7),
        frames = listOf(
            AnimatedGrammarStepFrame(
                stepNumber = 1,
                stepTitle = "گام ۱: قطار ۳ واگنه انگلیسی (برعکس فارسی!)",
                formulaBadge = "واگن ۱: Subject (فاعل) ➔ واگن ۲: Verb (فعل) ➔ واگن ۳: Object (مفعول)",
                tokens = listOf(
                    AnimatedGrammarToken("🚂 Ali (فاعل)", "واگن ۱: چه‌کسی؟", Color(0xFF0284C7), true),
                    AnimatedGrammarToken("🚃 studies (فعل)", "واگن ۲: چه‌کار می‌کند؟", Color(0xFFE11D48), true),
                    AnimatedGrammarToken("🚋 Arabic (مفعول)", "واگن ۳: چه‌چیزی را؟", Color(0xFF059669), true)
                ),
                explanation = "در فارسی می‌گوییم «علی (فاعل) عربی را (مفعول) می‌خواند (فعل آخر جمله)»، اما در انگلیسی فعل همیشه واگن دوم است و بلافاصله بعد از فاعل می‌نشیند!",
                memoryTrick = "فرمول قطار انگلیسی: «اول فاعل، بلافاصله بعدش فعل، بعدش مفعول، و قید زمان در آخر!»"
            ),
            AnimatedGrammarStepFrame(
                stepNumber = 2,
                stepTitle = "گام ۲: تشخیص زمان حال ساده و حال استمراری با کلیدواژه",
                formulaBadge = "every day ➔ V / V+s (حال ساده) | now / Look! ➔ am/is/are + ing (استمراری)",
                tokens = listOf(
                    AnimatedGrammarToken("She plays (every day)", "حال ساده: سوم‌شخص مفرد + s", Color(0xFF0284C7), true),
                    AnimatedGrammarToken("She is playing (now)", "حال استمراری: am/is/are + ing", Color(0xFF059669), true)
                ),
                explanation = "اگر آخر جمله every day, usually, always دیدی زمان حال ساده است (برای he/she/it فعل s می‌گیرد). اگر now, at the moment, Look! دیدی زمان حال استمراری (am/is/are + ing) است.",
                memoryTrick = "کد رمزی: «he/she/it در حال ساده عاشق حرف s است! • now = am/is/are + ing»"
            ),
            AnimatedGrammarStepFrame(
                stepNumber = 3,
                stepTitle = "گام ۳: تشخیص گذشته ساده و گذشته استمراری",
                formulaBadge = "yesterday / last / ago ➔ V-ed / V2 | when / while ➔ was/were + ing",
                tokens = listOf(
                    AnimatedGrammarToken("visited / went", "گذشته ساده (با yesterday)", Color(0xFFD97706), true),
                    AnimatedGrammarToken("was / were studying", "گذشته استمراری (در حین کار دیگر)", Color(0xFF7C3AED), true)
                ),
                explanation = "با دیدن yesterday, last week, 2 days ago گزینه گذشته ساده (-ed یا شکل دوم فعل) را بزن. اگر دو کار در گذشته با when/while قطع شده باشند، کار طولانی‌تر was/were + ing می‌گیرد.",
                memoryTrick = "کد رمزی: «while + was/were + ing • yesterday/last/ago = گذشته ساده!»"
            ),
            AnimatedGrammarStepFrame(
                stepNumber = 4,
                stepTitle = "گام ۴: ماضی نقلی (have/has + P.P.) در برابر ماضی بعید (had + P.P.)",
                formulaBadge = "since / for ➔ have/has + P.P. (Vision 2) | before / after + Past ➔ had + P.P. (Vision 3)",
                tokens = listOf(
                    AnimatedGrammarToken("have / has lived (since 2020)", "ماضی نقلی: از گذشته تا الان", Color(0xFF059669), true),
                    AnimatedGrammarToken("had eaten (before he arrived)", "ماضی بعید: گذشتهٔ دورتر!", Color(0xFFE11D48), true)
                ),
                explanation = "اگر در جمله since یا for دیدی، گزینه have/has + P.P. درست است. اگر در جمله before، after یا because همراه با یک فعل گذشته ساده دیدی، گزینه «had + P.P.» (ماضی بعید) ۱۰۰٪ درست است!",
                memoryTrick = "کد طلایی امتحان نهایی: «since/for = have/has + P.P. • before/after با گذشته = had + P.P.!»"
            )
        )
    ),
    StepByStepGrammarScenario(
        id = "math_unlocker_anim",
        categoryBadge = "📐 ریاضی انسانی بدون فرمول",
        title = "انیمیشن ضدقفل دفترچه ریاضی انسانی (معادله، منطق، دنباله و آمار)",
        accentColor = Color(0xFF1D4ED8),
        frames = listOf(
            AnimatedGrammarStepFrame(
                stepNumber = 1,
                stepTitle = "شگرد ۱: حل معادله درجه ۲ در ۳ ثانیه (قانون جمع ضرایب)",
                formulaBadge = "ax² + bx + c = 0 ➔ اگر a + b + c = 0 شد: ریشه‌ها ۱ و c/a هستند!",
                tokens = listOf(
                    AnimatedGrammarToken("2x² - 9x + 7 = 0", "جمع ضرایب: 2 - 9 + 7 = 0 ✅", Color(0xFF1D4ED8), true),
                    AnimatedGrammarToken("x = 1", "ریشه اول همیشه ۱", Color(0xFF059669), true),
                    AnimatedGrammarToken("x = 7/2", "ریشه دوم: آخر تقسیم بر اول (c/a)", Color(0xFFE11D48), true)
                ),
                explanation = "بدون دلتا و فرمول! فقط اعداد پشت x² و x و عدد ثابت را جمع کن: ۲ منهای ۹ به اضافه ۷ می‌شود صفر! پس یک جواب ۱ و جواب دیگر ۷/۲ است!",
                memoryTrick = "کد رمزی: «جمع ضرایب شد صفر؟ یکی ۱، یکی آخر به اول (c/a)!»"
            ),
            AnimatedGrammarStepFrame(
                stepNumber = 2,
                stepTitle = "شگرد ۲: منطق ریاضی یازدهم (قانون قول و قرار در p ⟹ q)",
                formulaBadge = "گزاره شرطی (p ⟹ q) فقط در حالت (T ⟹ F) نادرست است!",
                tokens = listOf(
                    AnimatedGrammarToken("T ⟹ F  ≡  F ❌", "تنها حالت نادرست (قول دادی و عمل نکردی!)", Color(0xFFE11D48), true),
                    AnimatedGrammarToken("F ⟹ T  ≡  T ✅", "مقدم نادرست = همیشه درست!", Color(0xFF059669), true),
                    AnimatedGrammarToken("F ⟹ F  ≡  T ✅", "مقدم نادرست = همیشه درست!", Color(0xFF0284C7), true)
                ),
                explanation = "در تست‌های منطق ریاضی، گزاره شرطی p ⟹ q در همه حالات درست (T) است به جز ۱ حالت: وقتی سمت چپ درست (T) و سمت راست نادرست (F) باشد!",
                memoryTrick = "کد رمزی: «در شرطی فقط T ⟹ F غلط است؛ بقیه همه درست‌اند!»"
            ),
            AnimatedGrammarStepFrame(
                stepNumber = 3,
                stepTitle = "شگرد ۳: آمار دهم (اثر جمع و ضرب روی میانگین و واریانس)",
                formulaBadge = "داده‌های جدید y = kx + b ➔ واریانس فقط در k² ضرب می‌شود (جمع b بی‌اثر است!)",
                tokens = listOf(
                    AnimatedGrammarToken("میانگین جدید = k x̄ + b", "هم ضرب و هم جمع اثر دارد", Color(0xFF0284C7), false),
                    AnimatedGrammarToken("انحراف معیار جدید = |k| σ", "جمع b هیچ اثری ندارد!", Color(0xFFD97706), true),
                    AnimatedGrammarToken("واریانس جدید = k² σ²", "فقط در مجذور k ضرب می‌شود!", Color(0xFFE11D48), true)
                ),
                explanation = "اگر به همه داده‌ها ۱۰ واحد اضافه کنیم، واریانس و انحراف معیار اصلاً تغییر نمی‌کنند! فقط وقتی داده‌ها در k ضرب شوند، انحراف معیار در |k| و واریانس در k² ضرب می‌شود.",
                memoryTrick = "کد رمزی: «جمع و تفریق زورش به واریانس و انحراف معیار نمی‌رسد!»"
            )
        )
    ),
    StepByStepGrammarScenario(
        id = "fonoon_unlocker_anim",
        categoryBadge = "🎭 علوم و فنون و عروض",
        title = "انیمیشن ضدقفل علوم و فنون (تشبیه/استعاره، اسلوب معادله و عروض)",
        accentColor = Color(0xFF9333EA),
        frames = listOf(
            AnimatedGrammarStepFrame(
                stepNumber = 1,
                stepTitle = "شگرد ۱: تفکیک ۱ ثانیه‌ای تشبیه بلیغ و استعاره مکنیه",
                formulaBadge = "ترکیب اضافی (Aِ B) را برعکس کن و «مثلِ» بگذار: (B مثلِ A است)",
                tokens = listOf(
                    AnimatedGrammarToken("دریایِ عشق ➔ عشق مثلِ دریاست ✅", "معنی داد = تشبیه بلیغ!", Color(0xFF059669), true),
                    AnimatedGrammarToken("دستِ روزگار ➔ روزگار مثلِ دست است ❌", "معنی نداد = استعاره مکنیه!", Color(0xFFE11D48), true)
                ),
                explanation = "هر وقت بین تشبیه بلیغ و استعاره مکنیه شک کردی، جای دو کلمه را عوض کن و «مثلِ» بگذار؛ اگر جمله منطقی بود تشبیه است و اگر بی‌معنی شد استعاره مکنیه (تشخیص) است!",
                memoryTrick = "کد رمزی: «برعکس + مثلِ: معنی داد = تشبیه • معنی نداد = استعاره مکنیه!»"
            ),
            AnimatedGrammarStepFrame(
                stepNumber = 2,
                stepTitle = "شگرد ۲: تشخیص اسلوب معادله در ۳ ثانیه (سبک هندی / دوازدهم)",
                formulaBadge = "مصراع اول (پیام عقلی) + [همان‌طور که / آری] + مصراع دوم (مثال حسی از طبیعت)",
                tokens = listOf(
                    AnimatedGrammarToken("عیب پاکان زود بر مردم هویدا می‌شود", "مصراع ۱: حکم اخلاقی", Color(0xFF2563EB), false),
                    AnimatedGrammarToken("«همان‌طور که / آری»", "پل ارتباطی معادله!", Color(0xFFE11D48), true),
                    AnimatedGrammarToken("موی در شیر خالص زود پیدا می‌شود", "مصراع ۲: مصداق حسی", Color(0xFF059669), true)
                ),
                explanation = "اگر دو مصراع از نظر دستوری کاملاً مستقل باشند، بتوان جای دو مصراع را عوض کرد و بینشان «همان‌طور که» گذاشت، آرایه ۱۰۰٪ اسلوب معادله است!",
                memoryTrick = "کد رمزی: «دو مصراع مستقل + همان‌طور که = اسلوب معادله (مخصوص صائب تبریزی)!»"
            ),
            AnimatedGrammarStepFrame(
                stepNumber = 3,
                stepTitle = "شگرد ۳: تقطیع فقط ۴ هجای اول در عروض کنکور!",
                formulaBadge = "هرگز کل بیت را تقطیع نکن! ۴ هجای اول وزن را لو می‌دهد:",
                tokens = listOf(
                    AnimatedGrammarToken("U – – –", "مفاعیلن (بحر هزج)", Color(0xFF0284C7), true),
                    AnimatedGrammarToken("– U – –", "فاعلاتن (بحر رمل)", Color(0xFF059669), true),
                    AnimatedGrammarToken("– – U –", "مستفعلن (بحر رجز)", Color(0xFFD97706), true),
                    AnimatedGrammarToken("U – –", "فعولن (متقارب / شاهنامه)", Color(0xFFE11D48), true)
                ),
                explanation = "با تقطیع فقط ۴ هجای ابتدایی مصراع اول و بررسی وجود یا عدم وجود مکث در وسط مصراع (وزن دوری)، در کمتر از ۱۰ ثانیه گزینه صحیح عروض پیدا می‌شود.",
                memoryTrick = "کد رمزی: «مکث وسط مصراع = وزن دوری • شروع با U – – = فعولن!»"
            )
        )
    ),
    StepByStepGrammarScenario(
        id = "eng_passive",
        categoryBadge = "🌍 زبان دوازدهم",
        title = "تبدیل مرحله‌به‌مرحله معلوم به مجهول (Active ➔ Passive)",
        accentColor = Color(0xFF2563EB),
        frames = listOf(
            AnimatedGrammarStepFrame(
                stepNumber = 1,
                stepTitle = "کالبدشکافی جمله معلوم (Active)",
                formulaBadge = "Subject (فاعل) + Verb (فعل) + Object (مفعول)",
                tokens = listOf(
                    AnimatedGrammarToken("The mechanic", "۱. فاعل (Subject)", Color(0xFF64748B), false),
                    AnimatedGrammarToken("repaired", "۲. فعل گذشته ساده", Color(0xFF2563EB), true),
                    AnimatedGrammarToken("the car", "۳. مفعول (Object)", Color(0xFF059669), true)
                ),
                explanation = "ابتدا ۳ بخش اصلی جمله معلوم را پیدا می‌کنیم: فاعل (The mechanic)، فعل در زمان گذشته ساده (repaired) و مفعول (the car).",
                memoryTrick = "مفعول همیشه دقیقاً بعد از فعل اصلی قرار دارد!"
            ),
            AnimatedGrammarStepFrame(
                stepNumber = 2,
                stepTitle = "پرواز مفعول به ابتدای جمله!",
                formulaBadge = "Object (مفعول در صدر) + [ ___ ] + by Subject",
                tokens = listOf(
                    AnimatedGrammarToken("The car", "مفعول در جایگاه فاعل!", Color(0xFF059669), true),
                    AnimatedGrammarToken("[ ? Be + P.P. ? ]", "جای خالی فعل مجهول", Color(0xFFD97706), true),
                    AnimatedGrammarToken("by the mechanic", "فاعل در انتها با by", Color(0xFF64748B), false)
                ),
                explanation = "مفعول (The car) با انیمیشن به اول جمله می‌آید و فاعل قبلی با حرف اضافه «by» به آخر جمله منتقل می‌شود.",
                memoryTrick = "در کنکور اگر بعد از جای خالی «by + شخص» دیدی، ۱۰۰٪ گزینه مجهول است!"
            ),
            AnimatedGrammarStepFrame(
                stepNumber = 3,
                stepTitle = "تزریق موتور مجهول (Be + P.P.)",
                formulaBadge = "Past Passive = was / were + Past Participle (P.P.)",
                tokens = listOf(
                    AnimatedGrammarToken("The car", "مفرد (Singular)", Color(0xFF059669), false),
                    AnimatedGrammarToken("was", "فعل Be گذشته مفرد", Color(0xFFE11D48), true),
                    AnimatedGrammarToken("repaired", "قسمت سوم فعل (P.P.)", Color(0xFF2563EB), true),
                    AnimatedGrammarToken("by the mechanic.", "قید عامل", Color(0xFF64748B), false)
                ),
                explanation = "چون زمان جمله گذشته ساده و «The car» مفرد است، از «was» به علاوه قسمت سوم فعل (repaired) استفاده می‌کنیم.",
                memoryTrick = "مجهول بدون یکی از اعضای خانواده Be (am/is/are/was/were/been/be) محال است!"
            ),
            AnimatedGrammarStepFrame(
                stepNumber = 4,
                stepTitle = "فرمول ۵ زمان مجهول در کنکور و نهایی",
                formulaBadge = "All Tenses Passive = To Be (هم‌زمان با فعل) + P.P.",
                tokens = listOf(
                    AnimatedGrammarToken("am/is/are + P.P.", "حال ساده مجهول", Color(0xFF0284C7), false),
                    AnimatedGrammarToken("was/were + P.P.", "گذشته ساده مجهول", Color(0xFF2563EB), true),
                    AnimatedGrammarToken("have/has been + P.P.", "ماضی نقلی مجهول", Color(0xFF059669), true),
                    AnimatedGrammarToken("will/can be + P.P.", "آینده و مدال مجهول", Color(0xFF7C3AED), false)
                ),
                explanation = "با همین الگو تمام ۵ زمان کتاب دوازدهم در ۳ ثانیه به مجهول تبدیل می‌شوند.",
                memoryTrick = "کد رمزی: «Yesterday ➔ was/were + P.P. | Since/For ➔ has/have been + P.P.»"
            )
        )
    ),
    StepByStepGrammarScenario(
        id = "eng_conditionals_tags",
        categoryBadge = "🌍 زبان دوازدهم",
        title = "انیمیشن جملات شرطی (Type 1 & 2) و Tag Questions",
        accentColor = Color(0xFF7C3AED),
        frames = listOf(
            AnimatedGrammarStepFrame(
                stepNumber = 1,
                stepTitle = "شرطی نوع اول (ممکن در آینده — Type 1)",
                formulaBadge = "If + Simple Present (حال ساده) , will / can + Simple Verb",
                tokens = listOf(
                    AnimatedGrammarToken("If", "حرف شرط", Color(0xFF7C3AED), false),
                    AnimatedGrammarToken("you study hard,", "حال ساده (V / V+s)", Color(0xFF059669), true),
                    AnimatedGrammarToken("you will pass", "will + فعل ساده", Color(0xFF2563EB), true),
                    AnimatedGrammarToken("the exam.", "ادامه جمله", Color(0xFF64748B), false)
                ),
                explanation = "در شرطی نوع ۱، سمت If همیشه حال ساده است و سمت جواب شرط «will / can / may + فعل ساده» می‌آید.",
                memoryTrick = "هرگز در جملهٔ دارای If از will استفاده نکن! (If you will study = غلط!)"
            ),
            AnimatedGrammarStepFrame(
                stepNumber = 2,
                stepTitle = "شرطی نوع دوم (خیالی در حال — Type 2)",
                formulaBadge = "If + Simple Past (گذشته ساده / were) , would / could + Simple Verb",
                tokens = listOf(
                    AnimatedGrammarToken("If I were you,", "were برای همه ضمایر!", Color(0xFFE11D48), true),
                    AnimatedGrammarToken("I would buy", "would + فعل ساده", Color(0xFF7C3AED), true),
                    AnimatedGrammarToken("that book.", "مفعول", Color(0xFF64748B), false)
                ),
                explanation = "وقتی یک پله به گذشته می‌رویم (تخیل در زمان حال)، فعل شرط گذشته ساده (یا were) و جواب شرط «would / could + فعل ساده» می‌شود.",
                memoryTrick = "در شرطی نوع ۲ حتی برای I / he / she / it هم به جای was از «were» استفاده کن!"
            ),
            AnimatedGrammarStepFrame(
                stepNumber = 3,
                stepTitle = "قانون آینه برعکس در Tag Questions",
                formulaBadge = "Positive Sentence (+) , Negative Tag (-)? | Negative (-) , Positive (+)?",
                tokens = listOf(
                    AnimatedGrammarToken("Sina plays tennis,", "جمله مثبت (گذشته/حال)", Color(0xFF059669), false),
                    AnimatedGrammarToken("doesn't", "منفیِ فعل کمکی!", Color(0xFFE11D48), true),
                    AnimatedGrammarToken("he?", "ضمیر فاعلی هم‌جنس", Color(0xFF2563EB), true)
                ),
                explanation = "جمله اصلی مثبت است (plays = حال ساده با s)، پس در آینهٔ انتهای جمله به «doesn't» تبدیل شده و Sina به ضمیر «he» تبدیل می‌شود.",
                memoryTrick = "کلمات never, hardly, seldom, rarely, little جمله را منفی می‌کنند ⬅ دنباله سؤالی باید مثبت باشد!"
            ),
            AnimatedGrammarStepFrame(
                stepNumber = 4,
                stepTitle = "استثناهای طلایی Tag Questions در امتحان نهایی",
                formulaBadge = "I am ➔ aren't I? | Let's ➔ shall we? | Imperative ➔ will you?",
                tokens = listOf(
                    AnimatedGrammarToken("I am late ➔ aren't I?", "استثنای اول", Color(0xFFE11D48), true),
                    AnimatedGrammarToken("Let's go ➔ shall we?", "استثنای دوم", Color(0xFF059669), true),
                    AnimatedGrammarToken("Open the door ➔ will you?", "استثنای سوم (امری)", Color(0xFF2563EB), true)
                ),
                explanation = "این ۳ استثنا پرتکرارترین سؤالات امتحان نهایی زبان دوازدهم و کنکور فرهنگیان هستند.",
                memoryTrick = "کد رمزی: «I am = aren't I • Let's = shall we • امری = will you»"
            )
        )
    ),
    StepByStepGrammarScenario(
        id = "arabic_nawasikh_anim",
        categoryBadge = "🕌 عربی دهم تا دوازدهم",
        title = "انیمیشن ورود «إِنَّ»، «کانَ» و «لای نفی جنس» به جمله اسمیه",
        accentColor = Color(0xFF059669),
        frames = listOf(
            AnimatedGrammarStepFrame(
                stepNumber = 1,
                stepTitle = "جمله اسمیه پایه (مبتدا و خبر مرفوع)",
                formulaBadge = "المبتدأ (مرفوع 🔵 ـُ) + الخبر (مرفوع 🔵 ـٌ)",
                tokens = listOf(
                    AnimatedGrammarToken("الْمُعَلِّمُ", "مبتدا (مرفوع با ضمه ـُ)", Color(0xFF0284C7), true),
                    AnimatedGrammarToken("مُخْلِصٌ", "خبر (مرفوع با تنوین ـٌ)", Color(0xFF0284C7), true)
                ),
                explanation = "در حالت عادی، هم مبتدا و هم خبر هر دو مرفوع (دارای ضمه ـُ / ـٌ یا ونَ) هستند.",
                memoryTrick = "مبتدا و خبر در حالت پایه دو برادر مرفوع (آبی 🔵) هستند!"
            ),
            AnimatedGrammarStepFrame(
                stepNumber = 2,
                stepTitle = "ورود «إِنَّ» (حروف مشبهة بالفعل)",
                formulaBadge = "إِنَّ / أَنَّ / لَعَلَّ / لَیْتَ / لکِنَّ + اسم (منصوب 🔴 ـَ) + خبر (مرفوع 🔵 ـٌ)",
                tokens = listOf(
                    AnimatedGrammarToken("إِنَّ", "حرف مشبهة بالفعل", Color(0xFFE11D48), true),
                    AnimatedGrammarToken("الْمُعَلِّمَ", "اسم إنّ (منصوب با فتحه ـَ)", Color(0xFFE11D48), true),
                    AnimatedGrammarToken("مُخْلِصٌ", "خبر إنّ (مرفوع با ـٌ)", Color(0xFF0284C7), false)
                ),
                explanation = "بلافاصله پس از ورود «إِنَّ»، کلمه اول (مبتدا) تبدیل به «اسم إنّ» شده و فتحه (ـَ) می‌گیرد، اما خبر مرفوع باقی می‌ماند!",
                memoryTrick = "کد رمزی: «إِنَّ اولی را فتحه (ـَ) می‌دهد، دومی را ضمه (ـُ)!»"
            ),
            AnimatedGrammarStepFrame(
                stepNumber = 3,
                stepTitle = "ورود «کانَ» (افعال ناقصه — برعکس إنّ!)",
                formulaBadge = "کانَ / أَصْبَحَ / صارَ / لَیْسَ + اسم (مرفوع 🔵 ـُ) + خبر (منصوب 🔴 ـً)",
                tokens = listOf(
                    AnimatedGrammarToken("کانَ", "فعل ناقصه", Color(0xFF0284C7), true),
                    AnimatedGrammarToken("الْمُعَلِّمُ", "اسم کان (مرفوع با ـُ)", Color(0xFF0284C7), false),
                    AnimatedGrammarToken("مُخْلِصاً", "خبر کان (منصوب با ـً)", Color(0xFFE11D48), true)
                ),
                explanation = "افعال ناقصه (کان، صار، أصبح، لیس) دقیقاً برعکسِ إنّ عمل می‌کنند: اسمشان مرفوع (ـُ) می‌ماند و خبرشان منصوب (ـً) می‌شود!",
                memoryTrick = "کد رمزی: «کانَ اولی را ضمه (ـُ) می‌دهد، دومی را فتحه (ـً)!»"
            ),
            AnimatedGrammarStepFrame(
                stepNumber = 4,
                stepTitle = "ورود «لای نفی جنس» (نفی مطلق در عربی دوازدهم)",
                formulaBadge = "لا + اسم نکره مفرد (مبنی بر فتحه بدون تنوین 🔴 ـَ) + خبر (مرفوع 🔵)",
                tokens = listOf(
                    AnimatedGrammarToken("لا", "لای نفی جنس (هیچ ... نیست)", Color(0xFFE11D48), true),
                    AnimatedGrammarToken("یَأْسَ", "اسم لا (فتحه بدون تنوین!)", Color(0xFFE11D48), true),
                    AnimatedGrammarToken("مَعَ الْأَمَلِ", "خبر لا (جار و مجرور)", Color(0xFF059669), false)
                ),
                explanation = "لای نفی جنس بر سر اسم نکره می‌آید، تنوین آن را حذف کرده و یک فتحه (ـَ) به آن می‌دهد و معنی «هیچ ... وجود ندارد» می‌سازد.",
                memoryTrick = "کد رمزی: «لا + اسم بدون ال با یک فتحه (لا رَیْبَ / لا شَکَّ / لا فَقْرَ) = لای نفی جنس!»"
            )
        )
    ),
    StepByStepGrammarScenario(
        id = "arabic_mansoobat_anim",
        categoryBadge = "🕌 عربی دوازدهم",
        title = "انیمیشن تشخیص ۵ ثانیه‌ای «مفعول مطلق»، «حال» و «تمییز»",
        accentColor = Color(0xFFD97706),
        frames = listOf(
            AnimatedGrammarStepFrame(
                stepNumber = 1,
                stepTitle = "گام ۱: شکار مفعول مطلق (مصدر هم‌ریشه با فعل)",
                formulaBadge = "فعل + مصدر منصوب از همان ریشه = مفعول مطلق",
                tokens = listOf(
                    AnimatedGrammarToken("یَجْتَهِدُ", "فعل از ریشه (ج ه د)", Color(0xFF2563EB), false),
                    AnimatedGrammarToken("الطّالِبُ", "فاعل مرفوع", Color(0xFF64748B), false),
                    AnimatedGrammarToken("اِجْتِهاداً", "مفعول مطلق (مصدر هم‌ریشه!)", Color(0xFFE11D48), true),
                    AnimatedGrammarToken("کَثیراً", "صفت ⬅ مفعول مطلق نوعی", Color(0xFFD97706), true)
                ),
                explanation = "وقتی مصدر منصوب دقیقاً از حروف ریشهٔ فعلِ جمله ساخته شود، «مفعول مطلق» است. اگر بعدش صفت یا مضاف‌الیه بیاید «نوعی» و اگر تنها باشد «تأکیدی» است.",
                memoryTrick = "کد رمزی: «مصدر هم‌ریشه + تنها = تأکیدی (خیلی/قطعاً) | + صفت/مضاف‌الیه = نوعی (نوعی از...)»"
            ),
            AnimatedGrammarStepFrame(
                stepNumber = 2,
                stepTitle = "گام ۲: شکار «حال» (اسم مشتق نکره در جواب چگونه؟)",
                formulaBadge = "ذوالحال (معرفه) + اسم مشتق نکره منصوب (ـً / ینَ) = حال",
                tokens = listOf(
                    AnimatedGrammarToken("رَجَعَ", "فعل (برگشت)", Color(0xFF64748B), false),
                    AnimatedGrammarToken("الْجُنْدِیُّ", "ذوالحال (معرفه با ال)", Color(0xFF0284C7), true),
                    AnimatedGrammarToken("مُنْتَصِراً", "حال (چگونه؟ پیروزمندانه!)", Color(0xFF059669), true)
                ),
                explanation = "کلمه «مُنْتَصِراً» اسم فاعل (مشتق)، نکره و منصوب است و حالتِ «الْجُنْدِیُّ» (سرباز) را در هنگام بازگشت بیان می‌کند.",
                memoryTrick = "کد رمزی حال: «مشتق (مُـ / فاعل) + نکره منصوب + جواب چطور/چگونه = حال!»"
            ),
            AnimatedGrammarStepFrame(
                stepNumber = 3,
                stepTitle = "گام ۳: شکار «تمییز» (اسم جامد رفع‌کننده ابهام)",
                formulaBadge = "عدد / وزن / مساحت / اسم تفضیل (أَفْعَل) + اسم نکره جامد منصوب = تمییز",
                tokens = listOf(
                    AnimatedGrammarToken("أَنا", "مبتدا", Color(0xFF64748B), false),
                    AnimatedGrammarToken("أَکْثَرُ مِنْکَ", "اسم تفضیل (دارای ابهام نسبت)", Color(0xFF7C3AED), true),
                    AnimatedGrammarToken("مالاً", "تمییز (از چه نظر؟ از نظر مال!)", Color(0xFFD97706), true)
                ),
                explanation = "کلمه «مالاً» اسم جامد، نکره و منصوب است که ابهامِ «أَکْثَرُ» (بیشتر از چه نظر؟) را برطرف کرده است.",
                memoryTrick = "کد رمزی تمییز: «اسم نکره منصوب بعد از أَفْعَل (أکثر، أشدّ، أفضل) یا بعد از اعداد ۱۱ تا ۹۹ = ۱۰۰٪ تمییز!»"
            ),
            AnimatedGrammarStepFrame(
                stepNumber = 4,
                stepTitle = "گام ۴: مقایسه نهایی حال 🆚 تمییز در ۱ نگاه",
                formulaBadge = "حال = مشتق و توصیف حالت | تمییز = جامد و بیان جنس/نسبت",
                tokens = listOf(
                    AnimatedGrammarToken("جاءَ ضاحِکاً", "مشتق (چگونه؟) ⬅ حال", Color(0xFF059669), true),
                    AnimatedGrammarToken("عِشْرُونَ کِتاباً", "بعد از عدد ⬅ تمییز", Color(0xFFD97706), true),
                    AnimatedGrammarToken("أَحْسَنُ أَخْلاقاً", "بعد از أَفْعَل ⬅ تمییز", Color(0xFFE11D48), true)
                ),
                explanation = "با این ۳ بلوک مقایسه‌ای دیگر هرگز در تست‌های کنکور «حال» و «تمییز» را با هم اشتباه نمی‌گیرید.",
                memoryTrick = "کد طلایی: «ضاحِکاً / باکِیاً / مُسْرِعاً = حال | مالاً / عِلْماً / کِتاباً = تمییز»"
            )
        )
    )
)
