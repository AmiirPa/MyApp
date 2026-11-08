package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.curriculum.LearningPath1406
import com.example.data.curriculum.LearningPathStatus
import com.example.data.curriculum.LearningPathStep
import com.example.ui.KonkoorUiState
import com.example.ui.components.BidiFormatter

/**
 * قدم فعلی مسیر: اولین قدمی که همه درس‌هایش کامل نشده‌اند.
 * درسی که در [KonkoorUiState.lessonProgressMap] نیست، کامل‌نشده حساب می‌شود.
 * قدم‌های GAP درس ندارند، پس قدم فعلی نیستند و رد می‌شوند؛ اگر همه قدم‌های
 * آماده کامل شده باشند، آخرین قدم (۱۰۰) برگردانده می‌شود.
 */
fun currentLearningPathStep(uiState: KonkoorUiState): LearningPathStep {
    val progress = uiState.lessonProgressMap
    return LearningPath1406.steps.firstOrNull { step ->
        step.status == LearningPathStatus.READY &&
            step.lessonIds.isNotEmpty() &&
            step.lessonIds.any { progress[it]?.isCompleted != true }
    } ?: LearningPath1406.steps.last()
}

/** اولین درس حل‌نشده قدم که واقعا در فهرست درس‌ها هست؛ اگر نباشد null. */
fun nextUnfinishedPathLessonId(
    step: LearningPathStep,
    uiState: KonkoorUiState
): String? {
    val byId = uiState.allLessons.associateBy { it.id }
    val progress = uiState.lessonProgressMap
    return step.lessonIds.firstOrNull { id ->
        byId.containsKey(id) && progress[id]?.isCompleted != true
    }
}

/** چند قدم آماده کامل شده‌اند (قدم‌های GAP شمرده نمی‌شوند). */
fun countCompletedPathSteps(uiState: KonkoorUiState): Int {
    val progress = uiState.lessonProgressMap
    return LearningPath1406.steps.count { step ->
        step.status == LearningPathStatus.READY &&
            step.lessonIds.isNotEmpty() &&
            step.lessonIds.all { progress[it]?.isCompleted == true }
    }
}

/**
 * جاده عمودی ۱ تا ۱۰۰: هر قدم یک ردیف با شماره در دایره، تیتر، زیرتیتر،
 * تعداد درس‌ها و وضعیت. زدن روی قدم، درس‌هایش را باز می‌کند (درس‌هایی که
 * در فهرست درس‌ها نیستند، «هنوز ساخته نشده» نشان داده می‌شوند).
 * باز کردن درس دقیقا با همان الگوی AddedLessonsScreen است: شناسه درس به
 * onOpenLesson داده می‌شود و MainActivity تب درس‌ها را باز می‌کند.
 */
@Composable
fun LearningPathScreen(
    uiState: KonkoorUiState,
    onOpenLesson: (String) -> Unit
) {
    val steps = LearningPath1406.steps
    val progress = uiState.lessonProgressMap
    val lessonById = remember(uiState.allLessons) {
        uiState.allLessons.associateBy { it.id }
    }
    val currentStep = remember(progress) { currentLearningPathStep(uiState) }
    val completedSteps = remember(progress) { countCompletedPathSteps(uiState) }
    val nextLessonId = remember(progress, uiState.allLessons, currentStep) {
        nextUnfinishedPathLessonId(currentStep, uiState)
    }
    val nextLesson = nextLessonId?.let { lessonById[it] }
    val allReadyDone = completedSteps == steps.count {
        it.status == LearningPathStatus.READY && it.lessonIds.isNotEmpty()
    }

    // قدم فعلی از اول باز است تا کاربر درس‌هایش را ببیند.
    var expandedStep by remember { mutableStateOf<Int?>(currentStep.stepNumber) }
    val listState = rememberLazyListState()
    LaunchedEffect(Unit) {
        listState.scrollToItem((currentStep.stepNumber - 1).coerceAtLeast(0))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .testTag("learning_path_root")
    ) {
        Text(
            text = "مسیر یادگیری ۱ تا ۱۰۰",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "از صفر تا کنکور انسانی و فرهنگیان ۱۴۰۶؛ یک قدم در یک زمان.",
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp)
        )
        Text(
            text = BidiFormatter.toPersianDigits(completedSteps.toString()) +
                " قدم از " + BidiFormatter.toPersianDigits("100") + " کامل شد",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 8.dp, bottom = 6.dp)
        )
        LinearProgressIndicator(
            progress = { completedSteps.toFloat() / steps.size },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        )

        if (nextLesson != null) {
            Button(
                onClick = { onOpenLesson(nextLesson.id) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
                    .testTag("learning_path_next_lesson_btn"),
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "درس بعدی: " + nextLesson.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 6.dp)
                )
            }
        } else if (allReadyDone) {
            Text(
                text = "همه قدم‌های آماده کامل شد؛ قدم‌های ۹۹ و ۱۰۰ هنوز ساخته نشده‌اند.",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 10.dp)
            )
        } else if (currentStep.status == LearningPathStatus.GAP) {
            Text(
                text = "قدم فعلی هنوز ساخته نشده است.",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(steps, key = { it.stepNumber }) { step ->
                val isCurrent = step.stepNumber == currentStep.stepNumber
                val isDone = step.status == LearningPathStatus.READY &&
                    step.lessonIds.isNotEmpty() &&
                    step.lessonIds.all { progress[it]?.isCompleted == true }
                val isGap = step.status == LearningPathStatus.GAP
                val isExpanded = expandedStep == step.stepNumber
                val resolvedLessons = step.lessonIds.mapNotNull { lessonById[it] }
                val missingCount = step.lessonIds.size - resolvedLessons.size

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            expandedStep = if (isExpanded) null else step.stepNumber
                        }
                        .testTag("learning_path_step_${step.stepNumber}"),
                    shape = RoundedCornerShape(14.dp),
                    color = if (isCurrent) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.surface
                    },
                    border = BorderStroke(
                        1.dp,
                        if (isCurrent) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.outlineVariant
                        }
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isDone) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.tertiary,
                                        modifier = Modifier.size(40.dp)
                                    )
                                } else {
                                    Surface(
                                        shape = CircleShape,
                                        color = if (isCurrent) {
                                            MaterialTheme.colorScheme.primary
                                        } else {
                                            MaterialTheme.colorScheme.surfaceVariant
                                        }
                                    ) {
                                        Box(
                                            modifier = Modifier.size(40.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = BidiFormatter.toPersianDigits(
                                                    step.stepNumber.toString()
                                                ),
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (isCurrent) {
                                                    MaterialTheme.colorScheme.onPrimary
                                                } else {
                                                    MaterialTheme.colorScheme.onSurfaceVariant
                                                }
                                            )
                                        }
                                    }
                                }
                            }

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = step.title,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = step.subtitle,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = if (isGap) {
                                        "هنوز ساخته نشده"
                                    } else {
                                        BidiFormatter.toPersianDigits(
                                            step.lessonIds.size.toString()
                                        ) + " درس • " + when {
                                            isDone -> "کامل شد"
                                            isCurrent -> "قدم فعلی"
                                            else -> "خوانده نشده"
                                        }
                                    },
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = when {
                                        isGap -> MaterialTheme.colorScheme.onSurfaceVariant
                                        isDone -> MaterialTheme.colorScheme.tertiary
                                        isCurrent -> MaterialTheme.colorScheme.primary
                                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                                    }
                                )
                            }

                            Icon(
                                imageVector = if (isExpanded) {
                                    Icons.Default.ExpandLess
                                } else {
                                    Icons.Default.ExpandMore
                                },
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        if (isExpanded) {
                            if (isGap) {
                                Text(
                                    text = step.gapNote,
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            } else {
                                resolvedLessons.forEach { lesson ->
                                    val done =
                                        progress[lesson.id]?.isCompleted == true
                                    Surface(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { onOpenLesson(lesson.id) }
                                            .testTag(
                                                "learning_path_lesson_${lesson.id}"
                                            ),
                                        shape = RoundedCornerShape(10.dp),
                                        color = MaterialTheme.colorScheme.surfaceVariant.copy(
                                            alpha = 0.5f
                                        )
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(
                                                    horizontal = 12.dp,
                                                    vertical = 9.dp
                                                ),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            Icon(
                                                imageVector = if (done) {
                                                    Icons.Default.CheckCircle
                                                } else {
                                                    Icons.Default.RadioButtonUnchecked
                                                },
                                                contentDescription = null,
                                                tint = if (done) {
                                                    MaterialTheme.colorScheme.tertiary
                                                } else {
                                                    MaterialTheme.colorScheme.onSurfaceVariant
                                                },
                                                modifier = Modifier.size(18.dp)
                                            )
                                            Text(
                                                text = lesson.title,
                                                fontSize = 13.sp,
                                                color = MaterialTheme.colorScheme.onSurface,
                                                modifier = Modifier.weight(1f)
                                            )
                                            Text(
                                                text = BidiFormatter.toPersianDigits(
                                                    lesson.estimatedMinutes.toString()
                                                ) + " دقیقه",
                                                fontSize = 11.sp,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                }
                                if (missingCount > 0) {
                                    Text(
                                        text = BidiFormatter.toPersianDigits(
                                            missingCount.toString()
                                        ) + " درس این قدم هنوز ساخته نشده است.",
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(top = 2.dp)
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
