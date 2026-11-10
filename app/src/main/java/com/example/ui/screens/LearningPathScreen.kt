package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RadioButtonUnchecked
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.curriculum.LearningPath1406
import com.example.data.curriculum.LearningPathElementary1406
import com.example.data.curriculum.LearningPathMiddle1406
import com.example.data.curriculum.LearningPathStatus
import com.example.data.curriculum.LearningPathStep
import com.example.data.curriculum.ZeroLesson
import com.example.data.firebase.CloudLessonProgress
import com.example.data.local.LearningPathPreference
import com.example.ui.KonkoorUiState
import com.example.ui.components.BidiFormatter
import com.example.ui.theme.resolveDynamicAccent

/**
 * قدم فعلی مسیر: اولین قدمی که همه درس‌هایش کامل نشده‌اند.
 * درسی که در [KonkoorUiState.lessonProgressMap] نیست، کامل‌نشده حساب می‌شود.
 * قدم‌های GAP درس ندارند، پس قدم فعلی نیستند و رد می‌شوند؛ اگر همه قدم‌های
 * آماده کامل شده باشند، آخرین قدم برگردانده می‌شود.
 */
fun currentLearningPathStep(
    uiState: KonkoorUiState,
    steps: List<LearningPathStep>
): LearningPathStep {
    val progress = uiState.lessonProgressMap
    return steps.firstOrNull { step ->
        step.status == LearningPathStatus.READY &&
            step.lessonIds.isNotEmpty() &&
            step.lessonIds.any { progress[it]?.isCompleted != true }
    } ?: steps.last()
}

/** نسخه تک‌آرگومانی: مسیر ۱ تا ۱۰۰ (کارت hero خانه همین را صدا می‌زند). */
fun currentLearningPathStep(uiState: KonkoorUiState): LearningPathStep {
    return currentLearningPathStep(uiState, LearningPath1406.steps)
}

/**
 * اولین درس حل‌نشده قدم که واقعا در فهرست درس‌ها هست؛ اگر نباشد null.
 * [lessonById] باید کش‌شده (remember روی allLessons) داده شود — ساختن
 * associateBy روی ۲٬۴۲۷ درس در هر فراخوانی گران است، پس نقشه اینجا
 * ساخته نمی‌شود.
 */
fun nextUnfinishedPathLessonId(
    step: LearningPathStep,
    progress: Map<String, CloudLessonProgress>,
    lessonById: Map<String, ZeroLesson>
): String? {
    return step.lessonIds.firstOrNull { id ->
        lessonById.containsKey(id) && progress[id]?.isCompleted != true
    }
}

/** چند قدم آماده کامل شده‌اند (قدم‌های GAP شمرده نمی‌شوند). */
fun countCompletedPathSteps(
    uiState: KonkoorUiState,
    steps: List<LearningPathStep>
): Int {
    val progress = uiState.lessonProgressMap
    return steps.count { step ->
        step.status == LearningPathStatus.READY &&
            step.lessonIds.isNotEmpty() &&
            step.lessonIds.all { progress[it]?.isCompleted == true }
    }
}

/** نسخه تک‌آرگومانی: مسیر ۱ تا ۱۰۰ (کارت hero خانه همین را صدا می‌زند). */
fun countCompletedPathSteps(uiState: KonkoorUiState): Int {
    return countCompletedPathSteps(uiState, LearningPath1406.steps)
}

/** سه بخش مسیر یادگیری: دبستان، متوسطه اول، متوسطه دوم و فرهنگیان. */
enum class LearningPathSection {
    ELEMENTARY,
    MIDDLE,
    HIGH
}

/**
 * جاده عمودی سه مسیر یادگیری (دبستان، متوسطه اول، متوسطه دوم و فرهنگیان):
 * هر قدم یک ردیف با شماره در دایره، تیتر، زیرتیتر،
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
    // بخش ذخیره‌شدهٔ قبلی (پیش‌فرض: مسیر فعلی، متوسطه دوم و فرهنگیان).
    val context = LocalContext.current
    var selectedSection by remember {
        mutableStateOf(
            runCatching {
                LearningPathSection.valueOf(
                    LearningPathPreference.loadSectionName(context)
                )
            }.getOrDefault(LearningPathSection.HIGH)
        )
    }
    val steps = when (selectedSection) {
        LearningPathSection.ELEMENTARY -> LearningPathElementary1406.steps
        LearningPathSection.MIDDLE -> LearningPathMiddle1406.steps
        LearningPathSection.HIGH -> LearningPath1406.steps
    }
    val pathTitle = when (selectedSection) {
        LearningPathSection.ELEMENTARY -> "مسیر دبستان"
        LearningPathSection.MIDDLE -> "مسیر متوسطه اول"
        LearningPathSection.HIGH -> "مسیر یادگیری ۱ تا ۱۰۰"
    }
    // پیشوند یکتای testTag قدم‌ها برای هر مسیر.
    val stepTagPrefix = when (selectedSection) {
        LearningPathSection.ELEMENTARY -> "learning_path_step_elem_"
        LearningPathSection.MIDDLE -> "learning_path_step_mid_"
        LearningPathSection.HIGH -> "learning_path_step_"
    }
    val progress = uiState.lessonProgressMap
    val lessonById = remember(uiState.allLessons) {
        uiState.allLessons.associateBy { it.id }
    }
    val currentStep = remember(progress, selectedSection) {
        currentLearningPathStep(uiState, steps)
    }
    val completedSteps = remember(progress, selectedSection) {
        countCompletedPathSteps(uiState, steps)
    }
    val nextLessonId = remember(progress, lessonById, currentStep) {
        nextUnfinishedPathLessonId(currentStep, progress, lessonById)
    }
    val nextLesson = nextLessonId?.let { lessonById[it] }
    val allReadyDone = completedSteps == steps.count {
        it.status == LearningPathStatus.READY && it.lessonIds.isNotEmpty()
    }

    // قدم فعلی از اول باز است تا کاربر درس‌هایش را ببیند؛ با عوض شدن
    // مسیر، قدم فعلی مسیر تازه باز می‌شود.
    var expandedStep by remember(selectedSection) {
        mutableStateOf<Int?>(currentStep.stepNumber)
    }
    // وضعیت اسکرول به‌ازای هر مسیر جداست تا با عوض شدن بخش، موقعیت کهنهٔ
    // مسیر قبلی نماند و کارت اول نیمه‌بریده دیده نشود؛ هر مسیر از قدم فعلی‌اش شروع می‌شود.
    val listState = remember(selectedSection) {
        LazyListState(
            firstVisibleItemIndex = (currentStep.stepNumber - 1).coerceAtLeast(0)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .testTag("learning_path_root")
    ) {
        // انتخاب‌گر سه‌تایی مسیر — به همان سبک چیپ‌های سه‌گانهٔ بخش
        // «بیشتر» (سه دکمه هم‌اندازه، فعال با رنگ primary). برچسب‌ها کوتاه‌اند
        // تا هر سه چیپ یک‌خطی شوند؛ «متوسطه دوم و فرهنگیان» کاملش در زیرتیتر صفحه است.
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                listOf(
                    Triple(
                        LearningPathSection.ELEMENTARY,
                        "دبستان",
                        "learning_path_section_elementary"
                    ),
                    Triple(
                        LearningPathSection.MIDDLE,
                        "متوسطه اول",
                        "learning_path_section_middle"
                    ),
                    Triple(
                        LearningPathSection.HIGH,
                        "متوسطه دوم",
                        "learning_path_section_high"
                    )
                ).forEach { (section, label, tag) ->
                    val isSelected = selectedSection == section
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .clickable {
                                selectedSection = section
                                LearningPathPreference.saveSectionName(context, section.name)
                            }
                            .testTag(tag),
                        color = if (isSelected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            Color.Transparent
                        },
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp, vertical = 9.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = label,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) {
                                    FontWeight.Bold
                                } else {
                                    FontWeight.Medium
                                },
                                color = if (isSelected) {
                                    MaterialTheme.colorScheme.onPrimary
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                },
                                maxLines = 1,
                                softWrap = false,
                                textAlign = TextAlign.Center,
                                lineHeight = 15.sp
                            )
                        }
                    }
                }
            }
        }
        // قهرمان «درس بعدی»: کارت بزرگ گرادیانی (همان گرادیان گرم خانه)
        // بلافاصله بعد از چیپ‌ها، در اول صفحه — «درس بعدی» بزرگ + شمارهٔ
        // قدم + تیتر درس + «قدم X از N کامل شد» + نوار پیشرفت. تیتر مسیر
        // هم داخل همین کارت است تا صفحه خلوت‌تر شود. زدن کارت درس را باز می‌کند.
        if (nextLesson != null) {
            // گرادیان کارت از رنگ‌های scheme (همان گرادیان گرم خانه در
            // حالت داینامیک): لهجهٔ اول با resolveDynamicAccent از رنگ
            // سیستم می‌آید و دو توقف بعدی secondary و tertiary هستند —
            // دیگر نارنجی هاردکد ندارد.
            val heroAccent = resolveDynamicAccent(MaterialTheme.colorScheme.primary)
            val heroGradientColors = listOf(
                heroAccent.copy(alpha = 0.85f),
                MaterialTheme.colorScheme.secondary.copy(alpha = 0.85f),
                MaterialTheme.colorScheme.tertiary.copy(alpha = 0.85f)
            )
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.linearGradient(heroGradientColors)
                    )
                    .clickable { onOpenLesson(nextLesson.id) }
                    .testTag("learning_path_next_lesson_btn"),
                shape = RoundedCornerShape(20.dp),
                color = Color.Transparent
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = pathTitle,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = "درس بعدی",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(start = 6.dp)
                        )
                        Text(
                            text = "قدم " + BidiFormatter.toPersianDigits(
                                currentStep.stepNumber.toString()
                            ),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White.copy(alpha = 0.9f),
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                    Text(
                        text = nextLesson.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = BidiFormatter.toPersianDigits(completedSteps.toString()) +
                            " قدم از " + BidiFormatter.toPersianDigits(steps.size.toString()) + " کامل شد",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    LinearProgressIndicator(
                        progress = { completedSteps.toFloat() / steps.size },
                        color = Color.White,
                        trackColor = Color.White.copy(alpha = 0.3f),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        } else {
            Text(
                text = BidiFormatter.toPersianDigits(completedSteps.toString()) +
                    " قدم از " + BidiFormatter.toPersianDigits(steps.size.toString()) + " کامل شد",
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
            if (allReadyDone) {
                Text(
                    text = if (selectedSection == LearningPathSection.HIGH) {
                        "همه قدم‌های آماده کامل شد؛ قدم‌های ۹۹ و ۱۰۰ هنوز ساخته نشده‌اند."
                    } else {
                        "همه قدم‌های این مسیر کامل شد؛ آفرین، برو سراغ مسیر بعدی."
                    },
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
                        .testTag(stepTagPrefix + step.stepNumber),
                    shape = RoundedCornerShape(16.dp),
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
                            modifier = Modifier.fillMaxWidth(),
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
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
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
                                if (uiState.allLessons.isEmpty()) {
                                    Text(
                                        text = "در حال بارگذاری درس‌ها…",
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(top = 2.dp)
                                    )
                                } else if (missingCount > 0) {
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
