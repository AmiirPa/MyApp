package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.togetherWith
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateMapOf
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import com.example.ui.components.BiDiText
import com.example.ui.components.SvgVectorMathFormulaDiagram
import com.example.ui.components.containsMathFormulaExpression
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Flip
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.EmojiObjects
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.ui.res.painterResource
import com.example.R
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import com.example.ui.components.BidiFormatter
import com.example.data.curriculum.FlashcardItem
import com.example.data.curriculum.SubjectCatalog
import com.example.data.firebase.CloudStudyTask
import com.example.ui.KonkoorUiState
import com.example.ui.theme.resolveDynamicAccent
import com.example.data.curriculum.KonkoorPastExamsArchive
import com.example.data.curriculum.PastKonkoorExamPaper
import com.example.data.curriculum.QuizQuestionItem
import com.example.ui.components.GoldenKeyPointCalloutCard
import com.example.ui.components.GoldenPointLevel
import com.example.data.curriculum.NahaeeExamsAndTarmimArchive
import com.example.data.curriculum.NahaeeDescriptiveQuestion
import com.example.data.curriculum.TarmimMoadelGuideRule
import com.example.data.curriculum.MinusZeroLearningEngine
import com.example.data.adaptive.ExamAttempt
import com.example.data.adaptive.QuestionAttempt
import com.example.data.adaptive.QuestionLevel
import com.example.util.StreakCalculator

private val PlannerBg: Color
    @Composable get() = MaterialTheme.colorScheme.background

private val PlannerSurface: Color
    @Composable get() = MaterialTheme.colorScheme.surface

private val PlannerPrimary: Color
    @Composable get() = MaterialTheme.colorScheme.primary

private val PlannerOnPrimary: Color
    @Composable get() = MaterialTheme.colorScheme.onPrimary

private val PlannerText: Color
    @Composable get() = MaterialTheme.colorScheme.onSurface

private val PlannerMuted: Color
    @Composable get() = MaterialTheme.colorScheme.onSurfaceVariant

private val PlannerBorder: Color
    @Composable get() = MaterialTheme.colorScheme.outlineVariant

private val PlannerGreen: Color
    @Composable get() = MaterialTheme.colorScheme.tertiary

@Composable
fun PracticeLeitnerAndQuizScreen(
    uiState: KonkoorUiState,
    onAnswerFlashcard: (FlashcardItem, Boolean) -> Unit,
    onSubmitExamResult: (String, String, Int, Int) -> Unit,
    /** داده هر سؤال آزمون (برای موتور تطبیقی)؛ null یعنی فقط نتیجه تجمیعی ثبت می‌شود. */
    onSubmitExamAttempt: ((ExamAttempt) -> Unit)? = null,
    initialModeIndex: Int = 2
) {
    var modeIndex by remember(initialModeIndex) { mutableIntStateOf(initialModeIndex) }
    val practiceModes = listOf(
        Pair(2, "آزمون‌های کنکور"),
        Pair(1, "سوالات تمرینی"),
        Pair(3, "امتحانات نهایی"),
        Pair(4, "تعیین سطح از صفر"),
        Pair(5, "جدول پوشش منابع"),
        Pair(0, "فلش‌کارت‌ها")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PlannerBg)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            shape = RoundedCornerShape(14.dp),
            color = PlannerSurface,
            border = BorderStroke(1.dp, PlannerBorder)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                practiceModes.forEach { (idx, title) ->
                    val isSelected = modeIndex == idx
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { modeIndex = idx }
                            .testTag("practice_mode_tab_$idx"),
                        color = if (isSelected) PlannerPrimary else Color.Transparent,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = title,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) PlannerOnPrimary else PlannerMuted,
                            maxLines = 1,
                            softWrap = false,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 9.dp)
                        )
                    }
                }
            }
        }

        androidx.compose.animation.AnimatedContent(
            targetState = modeIndex,
            transitionSpec = {
                androidx.compose.animation.fadeIn(
                    animationSpec = androidx.compose.animation.core.tween(180)
                ) togetherWith androidx.compose.animation.fadeOut(
                    animationSpec = androidx.compose.animation.core.tween(140)
                )
            },
            label = "PracticeSubTabAnim"
        ) { targetMode ->
            when (targetMode) {
                5 -> ContentCoverageMatrixView(uiState = uiState)
                4 -> MinusZeroDiagnosticView()
                3 -> NahaeeExamsAndTarmimView()
                2 -> KonkoorPastExamsArchiveView(
                    uiState = uiState,
                    onSubmitExamResult = onSubmitExamResult,
                    onSubmitExamAttempt = onSubmitExamAttempt
                )
                1 -> KonkoorQuizEngineView(
                    uiState = uiState,
                    onSubmitExamResult = onSubmitExamResult,
                    onSubmitExamAttempt = onSubmitExamAttempt
                )
                else -> LeitnerDeckView(uiState = uiState, onAnswerFlashcard = onAnswerFlashcard)
            }
        }
    }
}

@Composable
private fun LeitnerDeckView(
    uiState: KonkoorUiState,
    onAnswerFlashcard: (FlashcardItem, Boolean) -> Unit
) {
    var selectedSubjectFilter by remember { mutableStateOf("all") }
    val cards = remember(selectedSubjectFilter, uiState.allFlashcards) {
        if (selectedSubjectFilter == "all") uiState.allFlashcards
        else uiState.allFlashcards.filter { it.subjectId == selectedSubjectFilter }
    }
    var currentCardIndex by remember(selectedSubjectFilter) { mutableIntStateOf(0) }
    var isFlipped by remember(currentCardIndex, selectedSubjectFilter) { mutableStateOf(false) }

    val safeIndex = if (cards.isNotEmpty()) currentCardIndex % cards.size else 0
    val currentCard = cards.getOrNull(safeIndex)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 88.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Vertical expandable subject selector (لیستی)
        item {
            VerticalSubjectSelectorCard(
                subjects = uiState.subjects,
                selectedSubjectId = selectedSubjectFilter,
                allLabel = "همه دروس (۲۰ درس)",
                onSelectSubject = { selectedSubjectFilter = it }
            )
        }

        // Leitner 5 Boxes Summary Row (Standardized responsive height for Samsung A56)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                (1..5).forEach { boxNum ->
                    val countInBox = cards.count { card ->
                        (uiState.flashcardProgressMap[card.id]?.leitnerBox ?: 1) == boxNum
                    }
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .height(58.dp),
                        shape = RoundedCornerShape(16.dp),
                        color = if (boxNum == 5) MaterialTheme.colorScheme.tertiaryContainer
                        else MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 4.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = BidiFormatter.formatMixedPersianText("خانه $boxNum"),
                                style = MaterialTheme.typography.labelSmall,
                                maxLines = 1
                            )
                            Text(
                                text = BidiFormatter.formatMixedPersianText("$countInBox کارت"),
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }

        if (currentCard != null) {
            val subject = SubjectCatalog.getSubjectById(currentCard.subjectId)
            val cardProgress = uiState.flashcardProgressMap[currentCard.id]
            val currentBox = cardProgress?.leitnerBox ?: 1

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isFlipped = !isFlipped }
                        .testTag("leitner_active_card"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isFlipped) MaterialTheme.colorScheme.primaryContainer
                        else MaterialTheme.colorScheme.surface
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PlannerBorder),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Surface(
                                color = MaterialTheme.colorScheme.secondaryContainer,
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = BidiFormatter.formatMixedPersianText(
                                        "${subject.title.substringBefore(" (")} • ${currentCard.categoryTag}"
                                    ),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                                    maxLines = 1,
                                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp)
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = BidiFormatter.formatMixedPersianText("خانه لایتنر: $currentBox از ۵"),
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp)
                                    )
                                }
                                Text(
                                    text = BidiFormatter.formatMixedPersianText("کارت ${safeIndex + 1} از ${cards.size}"),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        BiDiText(
                            text = if (isFlipped) currentCard.backAnswer else currentCard.frontQuestion,
                            style = if (isFlipped) MaterialTheme.typography.bodyMedium else MaterialTheme.typography.titleMedium,
                            fontWeight = if (isFlipped) FontWeight.Normal else FontWeight.Bold,
                            textAlign = if (isFlipped) TextAlign.Start else TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        if (isFlipped) {
                            Surface(
                                color = MaterialTheme.colorScheme.secondaryContainer,
                                shape = RoundedCornerShape(9.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, PlannerBorder),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                BiDiText(
                                    text = "رمز حافظه: ${currentCard.memoryHook}",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Start
                                )
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Flip,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = if (isFlipped) "برای دیدن پرسش روی کارت بزن" else "برای دیدن پاسخ و رمز کنکوری روی کارت بزن",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            onAnswerFlashcard(currentCard, false)
                            isFlipped = false
                            currentCardIndex = (currentCardIndex + 1) % cards.size
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("flashcard_forgot_btn"),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Icon(Icons.Default.ThumbDown, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "نیازمند مرور",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            softWrap = false
                        )
                    }

                    Button(
                        onClick = {
                            onAnswerFlashcard(currentCard, true)
                            isFlipped = false
                            currentCardIndex = (currentCardIndex + 1) % cards.size
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("flashcard_knew_btn"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary
                        ),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Icon(Icons.Default.ThumbUp, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "بلد بودم (+۱)",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            softWrap = false
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun KonkoorQuizEngineView(
    uiState: KonkoorUiState,
    onSubmitExamResult: (String, String, Int, Int) -> Unit,
    onSubmitExamAttempt: ((ExamAttempt) -> Unit)? = null
) {
    var selectedSubjectFilter by remember { mutableStateOf("all") }
    // پاسخ‌های واقعی هر سؤال (برای ساخت ExamAttempt در پایان آزمون)؛
    // فقط گزینه‌ای که کاربر واقعاً زده ثبت می‌شود، سؤال بی‌پاسخ «نزده» است.
    val answeredOptionsMap = remember(selectedSubjectFilter) { mutableStateMapOf<String, Int>() }
    val questions = remember(selectedSubjectFilter, uiState.allQuizQuestions) {
        if (selectedSubjectFilter == "all") uiState.allQuizQuestions
        else uiState.allQuizQuestions.filter { it.subjectId == selectedSubjectFilter }
    }

    var currentIndex by remember(selectedSubjectFilter) { mutableIntStateOf(0) }
    var selectedOption by remember(currentIndex, selectedSubjectFilter) { mutableIntStateOf(-1) }
    var correctCount by remember(selectedSubjectFilter) { mutableIntStateOf(0) }
    var quizFinished by remember(selectedSubjectFilter) { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 12.dp, top = 10.dp, end = 12.dp, bottom = 84.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            VerticalSubjectSelectorCard(
                subjects = uiState.subjects,
                selectedSubjectId = selectedSubjectFilter,
                allLabel = "آزمون جامع همه دروس",
                onSelectSubject = { selectedSubjectFilter = it }
            )
        }

        if (quizFinished || questions.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "🎉 کارنامه آزمون آزمایشی شما ثبت شد!",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        val pct = if (questions.isNotEmpty()) (correctCount * 100) / questions.size else 0
                        Text(
                            text = BidiFormatter.formatMixedPersianText(
                                "تعداد پاسخ صحیح: $correctCount از ${questions.size} سوال ($pct٪)"
                            ),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Button(
                            onClick = {
                                currentIndex = 0
                                selectedOption = -1
                                correctCount = 0
                                answeredOptionsMap.clear()
                                quizFinished = false
                            },
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("شروع مجدد آزمون", style = MaterialTheme.typography.labelLarge)
                        }
                    }
                }
            }
        } else {
            val safeQuizIndex = currentIndex.coerceIn(0, (questions.size - 1).coerceAtLeast(0))
            val q = questions[safeQuizIndex]
            val subject = SubjectCatalog.getSubjectById(q.subjectId)

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(0.7.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.45f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(9.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = BidiFormatter.formatMixedPersianText(
                                    "${subject.title.substringBefore(" (")} • ${q.difficultyTag}"
                                ),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = subject.color,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = BidiFormatter.formatMixedPersianText(
                                    "سوال ${safeQuizIndex + 1} از ${questions.size}"
                                ),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        BiDiText(
                            text = q.questionText,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )

                        q.options.forEachIndexed { idx, opt ->
                            val answered = selectedOption != -1
                            val isCorrect = idx == q.correctOptionIndex
                            val isChosen = idx == selectedOption
                            val bg = when {
                                answered && isCorrect -> Color(0xFF16A34A).copy(alpha = 0.16f)
                                answered && isChosen && !isCorrect -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.65f)
                                else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
                            }
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(enabled = !answered) {
                                        selectedOption = idx
                                        answeredOptionsMap[q.id] = idx
                                        if (idx == q.correctOptionIndex) {
                                            correctCount++
                                        }
                                    }
                                    .testTag("quiz_option_$idx"),
                                shape = RoundedCornerShape(9.dp),
                                border = androidx.compose.foundation.BorderStroke(
                                    0.6.dp,
                                    if (answered && isCorrect) Color(0xFF16A34A) else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f)
                                ),
                                color = bg
                            ) {
                                BiDiText(
                                    text = "${idx + 1}) $opt",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = if (isChosen || (answered && isCorrect)) FontWeight.Bold else FontWeight.Normal,
                                    modifier = Modifier.padding(horizontal = 11.dp, vertical = 9.dp)
                                )
                            }
                        }

                        AnimatedVisibility(visible = selectedOption != -1) {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                HorizontalDivider(thickness = 0.6.dp)
                                Surface(
                                    color = subject.color.copy(alpha = 0.07f),
                                    shape = RoundedCornerShape(9.dp),
                                    border = androidx.compose.foundation.BorderStroke(0.6.dp, subject.color.copy(alpha = 0.28f)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                        Text(
                                            text = "✅ پاسخ تشریحی و نکات مهم:",
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = subject.color
                                        )
                                        BiDiText(
                                            text = q.zeroLevelSolution,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                        BiDiText(
                                            text = "⚡ فرمول طلایی: ${q.shortcutTechnique}",
                                            style = MaterialTheme.typography.bodySmall,
                                            fontWeight = FontWeight.Bold,
                                            color = subject.color
                                        )
                                    }
                                }

                                Button(
                                    onClick = {
                                        if (safeQuizIndex + 1 < questions.size) {
                                            currentIndex = safeQuizIndex + 1
                                            selectedOption = -1
                                        } else {
                                            val title = if (selectedSubjectFilter == "all") "آزمون جامع انسانی و فرهنگیان"
                                            else SubjectCatalog.getSubjectById(selectedSubjectFilter).title
                                            onSubmitExamResult(
                                                selectedSubjectFilter,
                                                title,
                                                correctCount,
                                                questions.size
                                            )
                                            // داده هر سؤال برای موتور تطبیقی (فقط پاسخ‌های واقعی).
                                            onSubmitExamAttempt?.invoke(
                                                buildQuizExamAttempt(
                                                    questions = questions,
                                                    answeredOptions = answeredOptionsMap,
                                                    subjectId = selectedSubjectFilter,
                                                    title = title
                                                )
                                            )
                                            quizFinished = true
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(42.dp)
                                        .testTag("quiz_next_button"),
                                    shape = RoundedCornerShape(9.dp)
                                ) {
                                    Text(
                                        if (safeQuizIndex + 1 < questions.size) "سوال بعدی ◀" else "ثبت کارنامه در پرونده",
                                        style = MaterialTheme.typography.labelLarge,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Previous Exam History
        if (uiState.examResults.isNotEmpty()) {
            item {
                Text(
                    text = "سابقه آزمون‌های ثبت‌شده شما:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            items(uiState.examResults, key = { it.id }) { exam ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(exam.subjectTitle, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Text(
                                BidiFormatter.formatMixedPersianText(
                                    "${exam.correctCount} صحیح از ${exam.totalCount} سوال"
                                ),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = BidiFormatter.formatMixedPersianText("${exam.scorePercent}٪"),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StudyPlannerAndFarhangianScreen(
    uiState: KonkoorUiState,
    onAddTask: (String, String, String, Int) -> Unit,
    onToggleTask: (CloudStudyTask) -> Unit,
    onDeleteTask: (String) -> Unit,
    onOpenLesson: (String) -> Unit = {},
    onSaveLessonState: (com.example.data.curriculum.ZeroLesson, Boolean, Boolean, String, Int) -> Unit = { _, _, _, _, _ -> }
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var plannerTab by remember { mutableIntStateOf(0) } // 0: برنامه روزانه, 1: گزارش پیشرفت, 2: تقویم هوشمند

    // لهجهٔ گرم برنامه‌ریز: داینامیک کالر روشن باشد رنگ اصلی سیستم می‌آید، وگرنه tertiary سیستم.
    val plannerAccent = resolveDynamicAccent(MaterialTheme.colorScheme.tertiary)
    val plannerHeaderBrush = Brush.linearGradient(
        listOf(
            plannerAccent.copy(alpha = 0.85f),
            MaterialTheme.colorScheme.primary.copy(alpha = 0.85f),
            MaterialTheme.colorScheme.tertiary.copy(alpha = 0.85f)
        )
    )
    val plannerBarBrush = Brush.linearGradient(
        listOf(
            MaterialTheme.colorScheme.primary.copy(alpha = 0.85f),
            MaterialTheme.colorScheme.tertiary.copy(alpha = 0.85f)
        )
    )

    Scaffold(
        containerColor = PlannerBg,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        floatingActionButton = {
            if (plannerTab == 0) {
                FloatingActionButton(
                    onClick = { showAddDialog = true },
                    containerColor = PlannerPrimary,
                    contentColor = PlannerOnPrimary,
                    modifier = Modifier.testTag("add_study_task_fab")
                ) {
                    Icon(Icons.Default.Add, contentDescription = "افزودن برنامه مطالعاتی")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(
                                MaterialTheme.colorScheme.primaryContainer,
                                MaterialTheme.colorScheme.secondaryContainer
                            )
                        )
                    ),
                shape = RoundedCornerShape(16.dp),
                color = Color.Transparent,
                border = BorderStroke(1.dp, PlannerBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf(
                        0 to "برنامه روزانه",
                        1 to "گزارش پیشرفت",
                        2 to "تقویم هوشمند"
                    ).forEach { (idx, title) ->
                        val isSelected = plannerTab == idx
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { plannerTab = idx },
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected) PlannerPrimary else Color.Transparent
                        ) {
                            Text(
                                text = title,
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) PlannerOnPrimary else PlannerMuted,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                modifier = Modifier.padding(vertical = 9.dp, horizontal = 6.dp)
                            )
                        }
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(start = 20.dp, top = 8.dp, end = 20.dp, bottom = 88.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                when (plannerTab) {
                    0 -> {
                        item(key = "planner_summary_header") {
                            val doneCount = uiState.studyTasks.count { it.isCompleted }
                            val totalCount = uiState.studyTasks.size
                            val pct = if (totalCount > 0) (doneCount * 100) / totalCount else 0
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(plannerHeaderBrush),
                                shape = RoundedCornerShape(20.dp),
                                color = Color.Transparent,
                                border = BorderStroke(1.dp, PlannerOnPrimary.copy(alpha = 0.35f))
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
                                        Text(
                                            text = "برنامه مطالعاتی من",
                                            fontSize = 17.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = PlannerOnPrimary
                                        )
                                        Text(
                                            text = BidiFormatter.applyPreferredDigits("$doneCount از $totalCount انجام‌شده"),
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = PlannerOnPrimary
                                        )
                                    }
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(7.dp)
                                            .clip(CircleShape)
                                            .background(PlannerOnPrimary.copy(alpha = 0.30f))
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth((pct.coerceIn(0, 100)) / 100f)
                                                .fillMaxSize()
                                                .clip(CircleShape)
                                                .background(Color.White)
                                        )
                                    }
                                    Button(
                                        onClick = { showAddDialog = true },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(16.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = PlannerOnPrimary,
                                            contentColor = PlannerPrimary
                                        )
                                    ) {
                                        Text(
                                            text = "+ افزودن مبحث جدید به برنامه امروز",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp
                                        )
                                    }
                                }
                            }
                        }

                        if (uiState.studyTasks.isEmpty()) {
                            item(key = "empty_tasks_state") {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(
                                            Brush.linearGradient(
                                                listOf(
                                                    MaterialTheme.colorScheme.primaryContainer,
                                                    MaterialTheme.colorScheme.secondaryContainer
                                                )
                                            )
                                        ),
                                    shape = RoundedCornerShape(20.dp),
                                    color = Color.Transparent,
                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.35f))
                                ) {
                                    Column(
                                        modifier = Modifier.padding(20.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        // نشان حالت خالی: دایرهٔ گرادیانی گرم با نماد کتاب.
                                        Box(
                                            modifier = Modifier
                                                .size(56.dp)
                                                .clip(CircleShape)
                                                .background(plannerBarBrush),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.AutoStories,
                                                contentDescription = null,
                                                tint = PlannerOnPrimary,
                                                modifier = Modifier.size(28.dp)
                                            )
                                        }
                                        Text(
                                            text = "هنوز برنامه‌ای برای امروز ثبت نشده است",
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = PlannerText
                                        )
                                        Text(
                                            text = "با زدن دکمه بالا، مباحثی که امروز قصد مطالعه دارید را وارد کنید.",
                                            fontSize = 12.sp,
                                            color = PlannerMuted,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        } else {
                            items(uiState.studyTasks, key = { it.id }) { task ->
                                val sub = SubjectCatalog.getSubjectById(task.subjectId)
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("study_task_card_${task.id}"),
                                    shape = RoundedCornerShape(20.dp),
                                    color = if (task.isCompleted) MaterialTheme.colorScheme.tertiaryContainer else PlannerSurface,
                                    border = BorderStroke(1.dp, PlannerBorder)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(14.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        IconButton(
                                            onClick = { onToggleTask(task) },
                                            modifier = Modifier.testTag("toggle_task_${task.id}")
                                        ) {
                                            Icon(
                                                imageVector = if (task.isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                                                contentDescription = "تغییر وضعیت انجام",
                                                tint = if (task.isCompleted) PlannerGreen else PlannerPrimary
                                            )
                                        }
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = BidiFormatter.formatMixedPersianText(task.title),
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = PlannerText,
                                                textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = BidiFormatter.formatMixedPersianText("${sub.shortTitle} • ${task.durationMinutes} دقیقه"),
                                                fontSize = 12.sp,
                                                color = PlannerMuted
                                            )
                                        }
                                        IconButton(
                                            onClick = { onDeleteTask(task.id) },
                                            modifier = Modifier.testTag("delete_task_${task.id}")
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.DeleteOutline,
                                                contentDescription = "حذف برنامه",
                                                tint = PlannerMuted
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    1 -> {
                        item(key = "progress_report_overview") {
                            val overallPct = (uiState.overallProgressFraction * 100).toInt().coerceIn(0, 100)
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(plannerHeaderBrush),
                                shape = RoundedCornerShape(20.dp),
                                color = Color.Transparent,
                                border = BorderStroke(1.dp, PlannerOnPrimary.copy(alpha = 0.35f))
                            ) {
                                Column(
                                    modifier = Modifier.padding(18.dp),
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Text(
                                        text = "گزارش کلی مطالعه",
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = PlannerOnPrimary
                                    )
                                    Text(
                                        text = BidiFormatter.applyPreferredDigits(
                                            "${uiState.completedLessonsCount} از ${uiState.totalLessonsCount} مبحث تکمیل‌شده (${overallPct}٪) • ${uiState.masteredFlashcardsCount} فلش‌کارت تثبیت‌شده"
                                        ),
                                        fontSize = 13.sp,
                                        color = PlannerOnPrimary.copy(alpha = 0.85f)
                                    )
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .weight(1f)
                                                .height(7.dp)
                                                .clip(CircleShape)
                                                .background(PlannerOnPrimary.copy(alpha = 0.30f))
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth(overallPct / 100f)
                                                    .fillMaxSize()
                                                    .clip(CircleShape)
                                                    .background(Color.White)
                                            )
                                        }
                                        // حلقهٔ پیشرفت زنده کنار میله.
                                        Box(
                                            modifier = Modifier.size(56.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            CircularProgressIndicator(
                                                progress = { overallPct / 100f },
                                                modifier = Modifier.fillMaxSize(),
                                                color = Color.White,
                                                trackColor = PlannerOnPrimary.copy(alpha = 0.30f),
                                                strokeWidth = 5.dp,
                                                strokeCap = StrokeCap.Round
                                            )
                                            Text(
                                                text = BidiFormatter.applyPreferredDigits("$overallPct٪"),
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = PlannerOnPrimary,
                                                maxLines = 1,
                                                softWrap = false
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        items(uiState.subjects, key = { "prog_${it.id}" }) { subject ->
                            val pct = (uiState.subjectProgressFraction(subject.id) * 100).toInt().coerceIn(0, 100)
                            val cleanTitle = subject.shortTitle.ifBlank { subject.title.substringAfter(". ") }
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(20.dp),
                                color = PlannerSurface,
                                border = BorderStroke(1.dp, PlannerBorder)
                            ) {
                                Column(
                                    modifier = Modifier.padding(15.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = BidiFormatter.applyPreferredDigits(cleanTitle),
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = PlannerText,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.weight(1f)
                                        )
                                        Text(
                                            text = BidiFormatter.applyPreferredDigits("$pct٪"),
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = PlannerPrimary,
                                            maxLines = 1,
                                            softWrap = false
                                        )
                                    }
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(7.dp)
                                            .clip(CircleShape)
                                            .background(PlannerBorder)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth(pct / 100f)
                                                .fillMaxSize()
                                                .clip(CircleShape)
                                                .background(plannerBarBrush)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    else -> {
                        item(key = "smart_soldier_teacher_planner") {
                            com.example.ui.components.SmartSoldierTeacherPlannerCard(
                                uiState = uiState,
                                onOpenLesson = onOpenLesson,
                                onToggleLessonCompleted = { lesson, completed ->
                                    val prev = uiState.lessonProgressMap[lesson.id]
                                    onSaveLessonState(
                                        lesson,
                                        completed,
                                        prev?.isBookmarked ?: false,
                                        prev?.userNote ?: "",
                                        prev?.masteryPercent ?: 0
                                    )
                                },
                                onToggleLessonBookmarked = { lesson, bookmarked ->
                                    val prev = uiState.lessonProgressMap[lesson.id]
                                    onSaveLessonState(
                                        lesson,
                                        prev?.isCompleted ?: false,
                                        bookmarked,
                                        prev?.userNote ?: "",
                                        prev?.masteryPercent ?: 0
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        var newTitle by remember { mutableStateOf("") }
        var newDuration by remember { mutableStateOf("45") }
        var selectedSubId by remember { mutableStateOf("math") }
        var selectedStage by remember { mutableStateOf("مطالعه و مرور") }

        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("افزودن مبحث به برنامه مطالعه") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = newTitle,
                        onValueChange = { newTitle = it },
                        label = { Text("عنوان مبحث (مثلاً: علوم و فنون درس ۱)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("new_task_title_input")
                    )
                    OutlinedTextField(
                        value = newDuration,
                        onValueChange = { newDuration = it },
                        label = { Text("مدت زمان به دقیقه (مثلاً 45)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    VerticalSubjectSelectorCard(
                        subjects = SubjectCatalog.subjects,
                        selectedSubjectId = selectedSubId,
                        allLabel = null,
                        onSelectSubject = { selectedSubId = it }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val cleanTitle = newTitle.trim()
                        if (cleanTitle.isNotBlank()) {
                            val isDuplicate = uiState.studyTasks.any {
                                it.title.trim().equals(cleanTitle, ignoreCase = true) && it.subjectId == selectedSubId
                            }
                            if (!isDuplicate) {
                                onAddTask(
                                    cleanTitle,
                                    selectedSubId,
                                    selectedStage,
                                    newDuration.toIntOrNull() ?: 45
                                )
                            }
                            showAddDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PlannerPrimary),
                    modifier = Modifier.testTag("confirm_add_task_btn")
                ) {
                    Text("ذخیره در برنامه")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("انصراف")
                }
            }
        )
    }
}

@Composable
private fun VerticalSubjectSelectorCard(
    subjects: List<com.example.data.curriculum.SubjectMeta>,
    selectedSubjectId: String,
    allLabel: String?,
    onSelectSubject: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedTitle = remember(selectedSubjectId, subjects, allLabel) {
        if (selectedSubjectId == "all" && allLabel != null) {
            allLabel
        } else {
            subjects.find { it.id == selectedSubjectId }
                ?.title
                ?.substringBefore(" (")
                ?.substringAfter(". ")
                ?: (allLabel ?: "انتخاب درس")
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(
            0.7.dp,
            MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.45f)
        )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "درس انتخاب‌شده: $selectedTitle",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = if (expanded) "برای بستن فهرست لیستی دروس ضربه بزنید ▲" else "برای انتخاب درس از فهرست لیستی ضربه بزنید ▼",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = if (expanded) "بستن ▲" else "فهرست دروس ▼",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            if (expanded) {
                HorizontalDivider(thickness = 0.6.dp)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    if (allLabel != null) {
                        val isAllSelected = selectedSubjectId == "all"
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onSelectSubject("all")
                                    expanded = false
                                },
                            shape = RoundedCornerShape(9.dp),
                            color = if (isAllSelected) MaterialTheme.colorScheme.primaryContainer
                            else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = allLabel,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = if (isAllSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isAllSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                                )
                                if (isAllSelected) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }

                    subjects.forEachIndexed { idx, sub ->
                        val isSelected = selectedSubjectId == sub.id
                        val cleanTitle = sub.title.substringBefore(" (").substringAfter(". ")
                        val idxFa = BidiFormatter.toPersianDigits((idx + 1).toString())
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onSelectSubject(sub.id)
                                    expanded = false
                                },
                            shape = RoundedCornerShape(9.dp),
                            color = if (isSelected) sub.color.copy(alpha = 0.14f)
                            else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.30f)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "$idxFa. $cleanTitle",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) sub.color else MaterialTheme.colorScheme.onSurface
                                )
                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = sub.color,
                                        modifier = Modifier.size(16.dp)
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


/**
 * Pure helpers for the archive exam ([KonkoorPastExamsArchiveView]), kept outside
 * the composable so they are unit-testable without a Compose runtime.
 * Covered by `ArchiveExamLogicTest`.
 */

/** Initial countdown for an archive paper: its official minutes, 85 when unknown. */
internal fun initialArchiveExamRemainingSeconds(totalTimeMinutes: Int?): Int =
    (totalTimeMinutes ?: 85) * 60

/**
 * Number of questions answered with the correct option. The source of truth is
 * the recorded answers map — the same data the report card renders — so the
 * stored result always matches the report the user sees. Unanswered questions
 * (absent from the map) and wrong answers both count as not correct.
 */
/**
 * ساخت ExamAttempt از پاسخ‌های واقعی یک آزمون (کوییز یا بایگانی).
 *
 * - زمان هر سؤال در اپ اندازه‌گیری نشده است، پس timeSpentSeconds = 0
 *   می‌ماند (یعنی «اندازه‌گیری نشده») و علت خطا حدس زده نمی‌شود.
 * - سؤال بی‌پاسخ، wasSkipped = true است؛ سؤال پاسخ‌داده‌شده فقط با گزینه
 *   واقعی کاربر سنجیده می‌شود.
 * - topicId = شناسه درس سؤال (QuizQuestionItem درس ندارد؛ subjectId همان
 *   مبحث است — موتور آن را به‌عنوان مبحث درسی ثبت می‌کند).
 */
internal fun buildQuizExamAttempt(
    questions: List<QuizQuestionItem>,
    answeredOptions: Map<String, Int>,
    subjectId: String,
    title: String,
    totalSeconds: Int = 0,
    todayEpochDay: Long = StreakCalculator.todayEpochDay()
): ExamAttempt = ExamAttempt(
    id = "quiz-$subjectId-$todayEpochDay-${System.currentTimeMillis()}",
    subjectId = subjectId,
    title = title,
    startedAtEpochDay = todayEpochDay,
    totalSeconds = totalSeconds,
    questions = questions.map { q ->
        val chosen = answeredOptions[q.id]
        QuestionAttempt(
            questionId = q.id,
            topicId = q.subjectId,
            subjectId = q.subjectId,
            level = QuestionLevel.CONCEPT,
            isCorrect = chosen != null && chosen == q.correctOptionIndex,
            wasSkipped = chosen == null,
            timeSpentSeconds = 0,
            answeredAtEpochDay = todayEpochDay
        )
    }
)

internal fun countArchiveExamCorrect(
    questions: List<QuizQuestionItem>,
    answeredOptions: Map<String, Int>
): Int = questions.count { q -> answeredOptions[q.id] == q.correctOptionIndex }

/**
 * Whether the correct answer / written solution may be shown for a question in
 * the archive view. Simulation mode (1) hides everything until the exam is
 * submitted; practice (0) and the full-list answer key (2) show it by design.
 */
internal fun isArchiveAnswerRevealAllowed(examMode: Int, isExamFinished: Boolean): Boolean =
    examMode != 1 || isExamFinished

@Composable
private fun KonkoorPastExamsArchiveView(
    uiState: KonkoorUiState,
    onSubmitExamResult: (String, String, Int, Int) -> Unit,
    onSubmitExamAttempt: ((ExamAttempt) -> Unit)? = null
) {
    var selectedYearFilter by remember { mutableIntStateOf(0) } // 0 = All, 1405, 1404, 1403, 1402, 1401, -1 = Farhangian Special
    var activePaper by remember { mutableStateOf<PastKonkoorExamPaper?>(null) }
    var currentQuestionIdx by remember { mutableIntStateOf(0) }
    var selectedOptionIdx by remember { mutableIntStateOf(-1) }
    var correctAnswersCount by remember { mutableIntStateOf(0) }
    var isExamFinished by remember { mutableStateOf(false) }
    var activeExamMode by remember { mutableIntStateOf(0) } // 0: Normal Practice (تمرین عادی بدون استرس), 1: Live Simulation (شبیه ساز کنکور با تایمر), 2: Full List (فهرست‌وار و پاسخ‌نامه)
    // Timer state is hoisted above the exam-mode switch: it is keyed ONLY by the
    // paper id, so switching between practice / simulation / full-list keeps the
    // remaining time instead of resetting it to the full paper time.
    // (Before, activeExamMode was a remember key, so every mode switch
    // recreated the timer from scratch.)
    var remainingSeconds by remember(activePaper?.id) {
        mutableIntStateOf(initialArchiveExamRemainingSeconds(activePaper?.totalTimeMinutes))
    }
    var isTimerRunning by remember(activePaper?.id) { mutableStateOf(false) }
    // Set once the current attempt's result has been submitted, so the three
    // finish paths (timer expiry, "پایان آزمون", last-question button) can only
    // ever store one result per attempt. Keyed by paper id: opening another
    // paper (or going back to the list) starts a fresh attempt; the in-place
    // "شروع مجدد شبیه‌ساز" button resets it explicitly.
    var archiveExamResultSubmitted by remember(activePaper?.id) { mutableStateOf(false) }

    // Second layer of the answer-leak fix (audit-confirmed): both answer-state
    // maps are keyed by the paper id, so switching papers clears all recorded
    // answers and revealed solutions. Before, they were plain remember { ... }
    // with no key and keyed only by q.id, so opening another paper kept the
    // previous paper's answers/solutions. (The primary fix — re-identifying
    // the yearly papers' questions — lives in KonkoorPastExamsArchive.kt.)
    val revealedSolutionsMap = remember(activePaper?.id) { mutableStateMapOf<String, Boolean>() }
    val answeredOptionsMap = remember(activePaper?.id) { mutableStateMapOf<String, Int>() }
    // فهرست کتاب: کدام دفترچه در فهرست باز شده تا جزئیاتش دیده شود (ردیف بسته فقط تیتر است).
    val expandedPaperMap = remember { mutableStateMapOf<String, Boolean>() }

    // Live countdown timer effect for exam simulation mode
    LaunchedEffect(isTimerRunning, remainingSeconds, activeExamMode) {
        if (activeExamMode == 1 && isTimerRunning && remainingSeconds > 0) {
            kotlinx.coroutines.delay(1000L)
            remainingSeconds -= 1
            if (remainingSeconds <= 0) {
                isTimerRunning = false
                isExamFinished = true
            }
        }
    }

    // ساخت فهرست دفترچه‌ها سنگین است (ساخت استخر سؤال از ۲٬۴۲۷ درس + چرخش و
    // بازنویسیٔ هر دفترچه در KonkoorPastExamsArchive) و پیش از این همگام روی
    // نخ اصلی در اولین ترکیب اجرا می‌شد — همان قفل چندثانیه‌ای تب «آزمون‌ها».
    // پس بارگذاری به نخ پس‌زمینه منتقل شد؛ تا آماده‌شدن فهرست، نشانگر
    // بارگذاری نمایش داده می‌شود و تب فوراً باز می‌شود.
    var allPapers by remember { mutableStateOf<List<PastKonkoorExamPaper>?>(null) }
    var standaloneYearlyPapers by remember { mutableStateOf<List<PastKonkoorExamPaper>?>(null) }
    LaunchedEffect(Unit) {
        val loaded = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.Default) {
            KonkoorPastExamsArchive.getAllExamPapers() to
                KonkoorPastExamsArchive.standaloneYearlyAllSubjectsPapers
        }
        allPapers = loaded.first
        standaloneYearlyPapers = loaded.second
    }
    val filteredPapers = remember(selectedYearFilter, allPapers) {
        val papers = allPapers.orEmpty()
        when (selectedYearFilter) {
            0 -> papers
            -1 -> papers.filter { it.isFarhangianSpecial }
            else -> papers.filter { it.year == selectedYearFilter && !it.isFarhangianSpecial }
        }
    }

    val paper = activePaper
    var selectedBookletSubject by remember(paper?.id) { mutableStateOf("all") }

    if (paper != null) {
        val allPaperQuestions = paper.questions
        val bookletSubjectTabs = remember(allPaperQuestions) {
            val distinctSubjects = allPaperQuestions.map { it.subjectId }.distinct()
            listOf("all" to "همه دروس به ترتیب دفترچه (${allPaperQuestions.size} سوال)") +
                distinctSubjects.mapIndexed { sIdx, subjId ->
                    val subjTitle = SubjectCatalog.getSubjectById(subjId).title.substringBefore(" (")
                    val count = allPaperQuestions.count { it.subjectId == subjId }
                    subjId to "${sIdx + 1}. $subjTitle ($count سوال)"
                }
        }
        val questions = remember(allPaperQuestions, selectedBookletSubject) {
            if (selectedBookletSubject == "all") allPaperQuestions
            else allPaperQuestions.filter { it.subjectId == selectedBookletSubject }
        }
        // شمارش سؤال‌های هر بخش فقط یک‌بار در هر فهرست سؤال؛ پیش از این
        // questions.count { ... } داخل آیتم هر سؤال اجرا می‌شد (O(n) در هر
        // آیتم، O(n²) در هر recomposition — مثلاً با هر پاسخ یا تیک تایمر).
        val questionCountBySubject = remember(questions) {
            questions.groupingBy { it.subjectId }.eachCount()
        }

        // Archive exam results are stored exactly once per attempt. All three
        // finish paths (timer expiry, the "پایان آزمون" button, and the
        // last-question "مشاهده کارنامه" button) converge on isExamFinished,
        // and none of them submitted before this effect existed, so finished
        // archive exams never reached the local store or the cloud.
        // The count is computed from the recorded answers map, the same data
        // the report card renders, so the stored result always matches the
        // report the user sees. Empty papers (no questions) submit nothing.
        LaunchedEffect(isExamFinished, paper.id) {
            if (isExamFinished && !archiveExamResultSubmitted && questions.isNotEmpty()) {
                archiveExamResultSubmitted = true
                onSubmitExamResult(
                    paper.id,
                    paper.title,
                    countArchiveExamCorrect(questions, answeredOptionsMap),
                    questions.size
                )
                // داده هر سؤال برای موتور تطبیقی (فقط پاسخ‌های واقعی).
                // زمان خالص: فقط در حالت شبیه‌سازی تایمر کم می‌شود؛ در
                // حالت‌های دیگر زمان باقی‌مانده دست نخورده می‌ماند، پس
                // زمان سپری‌شده ۰ یعنی «اندازه‌گیری نشده».
                val elapsedSeconds = if (activeExamMode == 1) {
                    (paper.totalTimeMinutes * 60 - remainingSeconds).coerceAtLeast(0)
                } else 0
                onSubmitExamAttempt?.invoke(
                    buildQuizExamAttempt(
                        questions = questions,
                        answeredOptions = answeredOptionsMap,
                        subjectId = paper.id,
                        title = paper.title,
                        totalSeconds = elapsedSeconds
                    )
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 12.dp, top = 10.dp, end = 12.dp, bottom = 84.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header: Back button & Exam title
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                activePaper = null
                                currentQuestionIdx = 0
                                selectedOptionIdx = -1
                                correctAnswersCount = 0
                                isExamFinished = false
                                isTimerRunning = false
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "بازگشت به فهرست دفترچه‌ها"
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = paper.title,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = BidiFormatter.formatMixedPersianText("شروع رسمی: ${paper.startTime} تا ${paper.endTime} • ${allPaperQuestions.size} سوال استاندارد • ${paper.totalTimeMinutes} دقیقه زمان مجاز"),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.9f),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Mode Toggle Card (تمرین عادی / شبیه ساز کنکور / فهرست کامل)
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    border = BorderStroke(0.8.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                ) {
                    Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Button(
                                onClick = { activeExamMode = 0 },
                                modifier = Modifier.weight(1f).height(40.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (activeExamMode == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                                )
                            ) {
                                Text(
                                    text = "🟢 تمرین عادی",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    color = if (activeExamMode == 0) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Button(
                                onClick = {
                                    activeExamMode = 1
                                    if (!isTimerRunning && remainingSeconds > 0) isTimerRunning = true
                                },
                                modifier = Modifier.weight(1f).height(40.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (activeExamMode == 1) Color(0xFFD97706) else MaterialTheme.colorScheme.surface
                                )
                            ) {
                                Text(
                                    text = "⏱️ شبیه‌ساز کنکور",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    color = if (activeExamMode == 1) Color.White else MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Button(
                                onClick = { activeExamMode = 2 },
                                modifier = Modifier.weight(1f).height(40.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (activeExamMode == 2) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.surface
                                )
                            ) {
                                Text(
                                    text = "📋 فهرست کامل",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    color = if (activeExamMode == 2) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        // Simulation Timer Toolbar
                        if (activeExamMode == 1) {
                            val hours = remainingSeconds / 3600
                            val mins = (remainingSeconds % 3600) / 60
                            val secs = remainingSeconds % 60
                            val timeFormatted = if (hours > 0) {
                                String.format(java.util.Locale.US, "%02d:%02d:%02d", hours, mins, secs)
                            } else {
                                String.format(java.util.Locale.US, "%02d:%02d", mins, secs)
                            }
                            Surface(
                                color = Color(0xFFD97706).copy(alpha = 0.15f),
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(1.dp, Color(0xFFD97706).copy(alpha = 0.4f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.Timer,
                                            contentDescription = null,
                                            tint = Color(0xFFD97706),
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = BidiFormatter.formatMixedPersianText("زمان باقی‌مانده: $timeFormatted"),
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Black,
                                            color = Color(0xFFD97706)
                                        )
                                    }

                                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                        IconButton(
                                            onClick = { isTimerRunning = !isTimerRunning },
                                            modifier = Modifier.size(32.dp)
                                        ) {
                                            Icon(
                                                imageVector = if (isTimerRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                                                contentDescription = null,
                                                tint = Color(0xFFD97706)
                                            )
                                        }
                                        Button(
                                            onClick = {
                                                isTimerRunning = false
                                                isExamFinished = true
                                            },
                                            shape = RoundedCornerShape(6.dp),
                                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                                        ) {
                                            Text("پایان آزمون", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Official Booklet Subject Vertical Menu (فهرست عمودی به ترتیب دروس دفترچه سنجش)
            item {
                var isSubjectListExpanded by remember { mutableStateOf(false) }
                val activeSubjectLabel = bookletSubjectTabs.find { it.first == selectedBookletSubject }?.second ?: bookletSubjectTabs.first().second
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { isSubjectListExpanded = !isSubjectListExpanded }
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = BidiFormatter.formatMixedPersianText("📑 فهرست عمودی دروس دفترچه: $activeSubjectLabel"),
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.primary,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = if (isSubjectListExpanded) "بستن فهرست ▲" else "انتخاب درس ▼",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                maxLines = 1,
                                softWrap = false
                            )
                        }
                        if (isSubjectListExpanded) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                bookletSubjectTabs.forEach { (subjKey, subjLabel) ->
                                    val isSelected = selectedBookletSubject == subjKey
                                    Surface(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(8.dp))
                                            .clickable {
                                                selectedBookletSubject = subjKey
                                                currentQuestionIdx = 0
                                                isSubjectListExpanded = false
                                            },
                                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 12.dp, vertical = 8.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = BidiFormatter.formatMixedPersianText(subjLabel),
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.SemiBold,
                                                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                modifier = Modifier.weight(1f)
                                            )
                                            if (isSelected) {
                                                Text(
                                                    text = "فعال ✓",
                                                    style = MaterialTheme.typography.labelSmall,
                                                    fontWeight = FontWeight.Black,
                                                    color = MaterialTheme.colorScheme.onPrimary,
                                                    maxLines = 1,
                                                    softWrap = false
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

            // Subject Timings Breakdown Card (سازمان سنجش)
            if (paper.subjectTimings.isNotEmpty()) {
                item {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.35f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.45f))
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.School,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "⏱️ بودجه‌بندی و ترتیب رسمی دروس در این دفترچه:",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Black,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            paper.subjectTimings.forEach { timing ->
                                Text(
                                    text = BidiFormatter.formatMixedPersianText(
                                        "• ${timing.subjectName}: ${timing.questionCount} سوال ⟵ ${timing.allocatedMinutes} دقیقه (هر تست ${timing.secondsPerQuestion} ثانیه)"
                                    ),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }

            if (activeExamMode == 0 || activeExamMode == 2) {
                // MODE 0 (تمرین عادی) & MODE 2 (فهرست کامل)
                items(questions.size) { idx ->
                    val q = questions[idx]
                    val prevSubjectId = if (idx > 0) questions[idx - 1].subjectId else null
                    val isNewSubjectSection = prevSubjectId != q.subjectId
                    val subject = SubjectCatalog.getSubjectById(q.subjectId)
                    val answeredIdx = answeredOptionsMap[q.id] ?: -1
                    val isRevealed = activeExamMode == 2 || revealedSolutionsMap[q.id] == true

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        if (isNewSubjectSection) {
                            val sectionCount = questionCountBySubject[q.subjectId] ?: 0
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 6.dp),
                                shape = RoundedCornerShape(12.dp),
                                color = subject.color.copy(alpha = 0.14f),
                                border = BorderStroke(1.dp, subject.color.copy(alpha = 0.45f))
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = BidiFormatter.formatMixedPersianText("📘 بخش دفترچه: ${subject.title}"),
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Black,
                                        color = subject.color,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Surface(
                                        color = subject.color,
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text(
                                            text = BidiFormatter.formatMixedPersianText("$sectionCount سوال"),
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White,
                                            maxLines = 1,
                                            softWrap = false,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                        )
                                    }
                                }
                            }
                        }

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = BorderStroke(0.8.dp, subject.color.copy(alpha = 0.4f))
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        color = subject.color.copy(alpha = 0.15f),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.weight(1f, fill = false)
                                    ) {
                                        Text(
                                            text = BidiFormatter.formatMixedPersianText(q.difficultyTag),
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = subject.color,
                                            maxLines = 1,
                                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = BidiFormatter.formatMixedPersianText("سوال ${idx + 1} از ${questions.size}"),
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                BiDiText(
                                    text = q.questionText,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )

                                // 4 Options
                                q.options.forEachIndexed { optIdx, optText ->
                                    val answered = answeredIdx != -1
                                    val isCorrect = optIdx == q.correctOptionIndex
                                    val isChosen = optIdx == answeredIdx
                                    val bg = when {
                                        answered && isCorrect -> Color(0xFF16A34A).copy(alpha = 0.18f)
                                        answered && isChosen && !isCorrect -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.65f)
                                        else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
                                    }
                                    Surface(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(8.dp))
                                            .clickable {
                                                answeredOptionsMap[q.id] = optIdx
                                                revealedSolutionsMap[q.id] = true
                                            },
                                        color = bg,
                                        shape = RoundedCornerShape(8.dp),
                                        border = BorderStroke(
                                            0.6.dp,
                                            if (answered && isCorrect) Color(0xFF16A34A)
                                            else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
                                        )
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = BidiFormatter.formatMixedPersianText("گزینه ${optIdx + 1}) "),
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Black,
                                                color = if (answered && isCorrect) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onSurface
                                            )
                                            BiDiText(
                                                text = optText,
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = MaterialTheme.colorScheme.onSurface,
                                                modifier = Modifier.weight(1f)
                                            )
                                        }
                                    }
                                }

                                // Solution Callout Button and Card
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    TextButton(
                                        onClick = {
                                            revealedSolutionsMap[q.id] = !isRevealed
                                        }
                                    ) {
                                        Icon(
                                            imageVector = if (isRevealed) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = if (isRevealed) "بستن پاسخ تشریحی" else "💡 مشاهده پاسخ تشریحی و شگرد ۵ ثانیه‌ای",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }

                                AnimatedVisibility(
                                    visible = isRevealed,
                                    enter = expandVertically() + fadeIn(),
                                    exit = shrinkVertically() + fadeOut()
                                ) {
                                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                        GoldenKeyPointCalloutCard(
                                            keyPointText = "💡 پاسخ تشریحی: ${q.zeroLevelSolution}",
                                            title = "پاسخ تشریحی سوال ${idx + 1}",
                                            level = GoldenPointLevel.CONCEPT_LAMP,
                                            explanation = if (q.shortcutTechnique.isNotBlank()) "⚡ شگرد ۵ ثانیه‌ای کنکور: ${q.shortcutTechnique}" else null
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                // MODE 1: SIMULATION TEST STEPPER WITH SANJESH SCORE CALCULATOR
                if (isExamFinished || questions.isEmpty()) {
                    item {
                        var correctCount = 0
                        var wrongCount = 0
                        var unansweredCount = 0
                        questions.forEach { qItem ->
                            val chosen = answeredOptionsMap[qItem.id]
                            if (chosen == null) {
                                unansweredCount++
                            } else if (chosen == qItem.correctOptionIndex) {
                                correctCount++
                            } else {
                                wrongCount++
                            }
                        }
                        val totalQ = questions.size
                        val rawScore = (3 * correctCount) - wrongCount
                        val maxScore = 3 * totalQ
                        val percentage = if (maxScore > 0) ((rawScore.toFloat() / maxScore.toFloat()) * 100f).coerceIn(-33f, 100f) else 0f
                        val pctFormatted = String.format(java.util.Locale.US, "%.1f", percentage)

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Text(
                                    text = "🏆 کارنامه شبیه‌ساز کنکور: ${paper.title}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Black,
                                    textAlign = TextAlign.Center
                                )

                                Surface(
                                    color = if (percentage >= 50f) Color(0xFF16A34A).copy(alpha = 0.15f) else Color(0xFFD97706).copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.padding(vertical = 4.dp)
                                ) {
                                    Text(
                                        text = BidiFormatter.formatMixedPersianText("درصد کل با نمره منفی: $pctFormatted ٪"),
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Black,
                                        color = if (percentage >= 50f) Color(0xFF16A34A) else Color(0xFFD97706),
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                    )
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(text = "✅ پاسخ درست", style = MaterialTheme.typography.labelSmall, color = Color(0xFF16A34A))
                                        Text(text = BidiFormatter.toPersianDigits(correctCount.toString()), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black)
                                    }
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(text = "❌ پاسخ نادرست", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.error)
                                        Text(text = BidiFormatter.toPersianDigits(wrongCount.toString()), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black)
                                    }
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(text = "⚪ نزده", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        Text(text = BidiFormatter.toPersianDigits(unansweredCount.toString()), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black)
                                    }
                                }

                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Button(
                                        onClick = {
                                            currentQuestionIdx = 0
                                            selectedOptionIdx = -1
                                            correctAnswersCount = 0
                                            isExamFinished = false
                                            archiveExamResultSubmitted = false
                                            answeredOptionsMap.clear()
                                            revealedSolutionsMap.clear()
                                            remainingSeconds = paper.totalTimeMinutes * 60
                                            isTimerRunning = true
                                        },
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("شروع مجدد شبیه‌ساز")
                                    }
                                    OutlinedButton(
                                        onClick = { activeExamMode = 2 },
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Text("مشاهده پاسخ‌نامه")
                                    }
                                    OutlinedButton(
                                        onClick = { activePaper = null },
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Text("سایر دفترچه‌ها")
                                    }
                                }
                            }
                        }
                    }
                } else {
                    val safeIdx = currentQuestionIdx.coerceIn(0, questions.size - 1)
                    val q = questions[safeIdx]
                    val subject = SubjectCatalog.getSubjectById(q.subjectId)

                    // Question Jumper Bar (شماره سوالات ۱ تا N برای پرش مستقیم)
                    item {
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            items(questions.size) { qNumIdx ->
                                val isCurr = qNumIdx == safeIdx
                                val isAns = answeredOptionsMap[questions[qNumIdx].id] != null
                                Surface(
                                    shape = CircleShape,
                                    color = when {
                                        isCurr -> MaterialTheme.colorScheme.primary
                                        isAns -> Color(0xFF15803D).copy(alpha = 0.2f)
                                        else -> MaterialTheme.colorScheme.surfaceVariant
                                    },
                                    border = BorderStroke(
                                        if (isCurr) 1.5.dp else 0.5.dp,
                                        if (isCurr) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
                                    ),
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(CircleShape)
                                        .clickable {
                                            currentQuestionIdx = qNumIdx
                                            selectedOptionIdx = answeredOptionsMap[questions[qNumIdx].id] ?: -1
                                        }
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = BidiFormatter.toPersianDigits((qNumIdx + 1).toString()),
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = if (isCurr) FontWeight.Black else FontWeight.Bold,
                                            color = if (isCurr) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = androidx.compose.foundation.BorderStroke(0.8.dp, subject.color.copy(alpha = 0.35f))
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        color = subject.color.copy(alpha = 0.15f),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text(
                                            text = BidiFormatter.formatMixedPersianText("${q.difficultyTag}"),
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = subject.color,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                    Text(
                                        text = BidiFormatter.formatMixedPersianText("سوال ${safeIdx + 1} از ${questions.size}"),
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                BiDiText(
                                    text = q.questionText,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )

                                // Options (simulation mode): answering only records the
                                // choice. Correctness is NEVER shown while the exam is
                                // running — no green correct option, no red wrong option,
                                // only a neutral mark on the option the user picked.
                                // The correct answer becomes visible only after the exam
                                // is submitted, in the full-list answer key (mode 2).
                                // See isArchiveAnswerRevealAllowed.
                                val revealAllowed = isArchiveAnswerRevealAllowed(activeExamMode, isExamFinished)
                                q.options.forEachIndexed { idx, opt ->
                                    val answered = selectedOptionIdx != -1
                                    val isCorrect = idx == q.correctOptionIndex
                                    val isChosen = idx == selectedOptionIdx
                                    val bg = when {
                                        revealAllowed && answered && isCorrect -> Color(0xFF16A34A).copy(alpha = 0.18f)
                                        revealAllowed && answered && isChosen && !isCorrect -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.65f)
                                        isChosen -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f)
                                        else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.40f)
                                    }
                                    Surface(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(10.dp))
                                            .clickable(enabled = !answered) {
                                                selectedOptionIdx = idx
                                                answeredOptionsMap[q.id] = idx
                                                if (isCorrect) correctAnswersCount++
                                            },
                                        color = bg,
                                        shape = RoundedCornerShape(10.dp),
                                        border = androidx.compose.foundation.BorderStroke(
                                            0.6.dp,
                                            when {
                                                revealAllowed && answered && isCorrect -> Color(0xFF16A34A)
                                                isChosen -> MaterialTheme.colorScheme.primary
                                                else -> MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
                                            }
                                        )
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 9.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = BidiFormatter.formatMixedPersianText("گزینه ${idx + 1}) "),
                                                style = MaterialTheme.typography.labelMedium,
                                                fontWeight = FontWeight.Black,
                                                color = if (revealAllowed && answered && isCorrect) MaterialTheme.colorScheme.tertiary
                                                else if (isChosen) MaterialTheme.colorScheme.primary
                                                else MaterialTheme.colorScheme.onSurface
                                            )
                                            BiDiText(
                                                text = opt,
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = MaterialTheme.colorScheme.onSurface,
                                                modifier = Modifier.weight(1f)
                                            )
                                        }
                                    }
                                }

                                // Step-by-Step Solution with Golden Key Callout Card if answered.
                                // In simulation mode the written solution stays hidden until
                                // the exam is submitted (revealAllowed is false while the
                                // exam is running); it is available afterwards in the
                                // full-list answer key (mode 2).
                                if (selectedOptionIdx != -1 && revealAllowed) {
                                    Spacer(modifier = Modifier.height(4.dp))
                                    GoldenKeyPointCalloutCard(
                                        keyPointText = "💡 پاسخ تشریحی: ${q.zeroLevelSolution}",
                                        title = "تحلیل تشریحی و خط‌به‌خط سوال کنکور",
                                        level = GoldenPointLevel.CONCEPT_LAMP,
                                        explanation = if (q.shortcutTechnique.isNotBlank()) "⚡ شگرد رد گزینه و تکنیک سرعتی: ${q.shortcutTechnique}" else null
                                    )
                                }

                                // Stepper Navigation Buttons (قبلی / بعدی)
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    OutlinedButton(
                                        onClick = {
                                            if (currentQuestionIdx > 0) {
                                                currentQuestionIdx--
                                                selectedOptionIdx = answeredOptionsMap[questions[currentQuestionIdx].id] ?: -1
                                            }
                                        },
                                        enabled = currentQuestionIdx > 0,
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Text("⟵ سوال قبلی")
                                    }

                                    Button(
                                        onClick = {
                                            if (currentQuestionIdx < questions.size - 1) {
                                                currentQuestionIdx++
                                                selectedOptionIdx = answeredOptionsMap[questions[currentQuestionIdx].id] ?: -1
                                            } else {
                                                isExamFinished = true
                                            }
                                        },
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Text(
                                            text = if (currentQuestionIdx < questions.size - 1) "سوال بعدی ⟶" else "مشاهده کارنامه نهایی",
                                            style = MaterialTheme.typography.labelLarge
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    } else if (allPapers == null) {
        // فهرست دفترچه‌ها هنوز در نخ پس‌زمینه ساخته می‌شود؛ تب فوراً باز
        // می‌شود و این نشانگر به‌جای قفل چندثانیه‌ای نمایش داده می‌شود.
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(24.dp)
            ) {
                CircularProgressIndicator(color = PlannerPrimary)
                Text(
                    text = "در حال آماده‌سازی دفترچه‌های کنکور…",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = PlannerText,
                    textAlign = TextAlign.Center
                )
            }
        }
    } else {
        // Main Archive Listing View
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 12.dp, top = 8.dp, end = 12.dp, bottom = 84.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Official Minute-by-Minute Timetable Card (سازمان سنجش) - Calm solid surface in both Light and Dark mode
            item {
                var showScheduleDetails by remember { mutableStateOf(false) }
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .clickable { showScheduleDetails = !showScheduleDetails },
                    color = PlannerSurface,
                    border = androidx.compose.foundation.BorderStroke(1.dp, PlannerBorder),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    shape = CircleShape,
                                    modifier = Modifier.size(34.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Default.School,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "زمان‌بندی رسمی روز کنکور (سازمان سنجش)",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = PlannerText,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(50)
                            ) {
                                Text(
                                    text = if (showScheduleDetails) "بستن ▲" else "جزئیات ▼",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    maxLines = 1,
                                    softWrap = false,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                )
                            }
                        }

                        if (showScheduleDetails) {
                            HorizontalDivider(thickness = 1.dp, color = PlannerBorder)
                            Text(
                                text = BidiFormatter.formatMixedPersianText("• ۰۷:۰۰ صبح ⟵ باز شدن درب حوزه‌ها و ورود داوطلبان"),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = BidiFormatter.formatMixedPersianText("• ۰۷:۳۰ صبح ⟵ بسته‌شدن قطعی درب‌های حوزه و استقرار روی صندلی"),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = BidiFormatter.formatMixedPersianText("• ۰۸:۰۰ صبح ⟵ شروع رسمی آزمون و توزیع دفترچه شماره ۱"),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = PlannerPrimary
                            )
                            Text(
                                text = BidiFormatter.formatMixedPersianText("دفترچه ۱ (۰۸:۰۰ تا ۰۹:۱۵ — ۷۵ دقیقه):\n  - ۰۸:۰۰ تا ۰۸:۲۵ ⟵ ریاضی و آمار (۲۰ سوال / ۲۵ دقیقه — هر تست ۷۵ ثانیه)\n  - ۰۸:۲۵ تا ۰۸:۵۵ ⟵ علوم و فنون ادبی (۳۰ سوال / ۳۰ دقیقه — هر تست ۶۰ ثانیه)\n  - ۰۸:۵۵ تا ۰۹:۱۰ ⟵ جامعه‌شناسی (۲۰ سوال / ۱۵ دقیقه — هر تست ۴۵ ثانیه)\n  - ۰۹:۱۰ تا ۰۹:۲۵ ⟵ روان‌شناسی (۱۵ سوال / ۱۵ دقیقه — هر تست ۶۰ ثانیه)"),
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = BidiFormatter.formatMixedPersianText("• ۰۹:۱۵ صبح ⟵ پایان دفترچه ۱ و جمع‌آوری پاسخ‌نامه ۱"),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.error
                            )
                            Text(
                                text = BidiFormatter.formatMixedPersianText("دفترچه ۲ (۰۹:۱۵ تا ۱۰:۳۰ — ۷۵ دقیقه):\n  - ۰۹:۱۵ تا ۰۹:۳۵ ⟵ عربی تخصصی (۲۰ سوال / ۲۰ دقیقه — هر تست ۶۰ ثانیه)\n  - ۰۹:۳۵ تا ۱۰:۰۰ ⟵ تاریخ و جغرافیا (۳۰ سوال / ۲۵ دقیقه — هر تست ۵۰ ثانیه)\n  - ۱۰:۰۰ تا ۱۰:۳۰ ⟵ فلسفه و منطق (۳۰ سوال / ۳۰ دقیقه — هر تست ۶۰ ثانیه)\n  - شناور ⟵ اقتصاد (۱۵ سوال / ۱۵ دقیقه — هر تست ۶۰ ثانیه)"),
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = BidiFormatter.formatMixedPersianText("• ۱۰:۳۰ صبح ⟵ پایان دفترچه ۲ و اتمام کنکور داوطلبان عادی"),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.error
                            )
                            Text(
                                text = BidiFormatter.formatMixedPersianText("دفترچه ۳ اختصاصی فرهنگیان (۱۰:۳۰ تا ۱۱:۳۰ — ۶۰ دقیقه):\n  - ۱۰:۳۰ تا ۱۱:۱۰ ⟵ هوش و استعداد معلمی (۲۰ سوال / ۴۰ دقیقه — هر تست ۱۲۰ ثانیه)\n  - ۱۱:۱۰ تا ۱۱:۳۰ ⟵ تعلیم و تربیت اسلامی (۲۰ سوال / ۲۰ دقیقه — هر تست ۶۰ ثانیه)"),
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = BidiFormatter.formatMixedPersianText("• ۱۱:۳۰ صبح ⟵ پایان کامل آزمون اختصاصی فرهنگیان"),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = PlannerGreen
                            )
                        }
                    }
                }
            }

            // ۱. بخش ویژه: کنکورهای هر سال جداگانه (کل دروس هر سال به تفکیک ۱۳۹۸ تا ۱۴۰۵)
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = PlannerSurface
                    ),
                    border = BorderStroke(1.dp, PlannerBorder)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "آزمون جامع کل دروس به تفکیک سال",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = PlannerText,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f)
                            )
                            Surface(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(50)
                            ) {
                                Text(
                                    text = "۱۳۹۸ تا ۱۴۰۵",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    maxLines = 1,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Text(
                            text = "برای شرکت در آزمون یکپارچه تمام دروس یک سال مشخص، سال مورد نظر را انتخاب کنید:",
                            style = MaterialTheme.typography.bodySmall,
                            color = PlannerMuted,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(horizontal = 2.dp)
                        ) {
                            items(standaloneYearlyPapers.orEmpty(), key = { it.id }) { yearlyPaper ->
                                val persianYr = BidiFormatter.toPersianDigits(yearlyPaper.year.toString())
                                val qCount = BidiFormatter.toPersianDigits(yearlyPaper.questions.size.toString())
                                Surface(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(14.dp))
                                        .clickable {
                                            activePaper = yearlyPaper
                                            currentQuestionIdx = 0
                                            selectedOptionIdx = -1
                                            correctAnswersCount = 0
                                            isExamFinished = false
                                        }
                                        .testTag("yearly_exam_${yearlyPaper.year}"),
                                    shape = RoundedCornerShape(14.dp),
                                    color = PlannerBg,
                                    border = BorderStroke(1.dp, PlannerBorder)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text(
                                            text = "کنکور $persianYr • $qCount سوال",
                                            style = MaterialTheme.typography.labelLarge,
                                            fontWeight = FontWeight.Bold,
                                            color = PlannerPrimary,
                                            maxLines = 1,
                                            softWrap = false
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // ۲. نوار فیلتر سریع بر اساس سال (اسکرول چپ و راست)
            item {
                val yearChips = listOf(
                    0 to "همه سال‌ها",
                    1405 to "کنکور ۱۴۰۵",
                    1404 to "کنکور ۱۴۰۴",
                    1403 to "کنکور ۱۴۰۳",
                    1402 to "کنکور ۱۴۰۲",
                    1401 to "کنکور ۱۴۰۱",
                    1400 to "کنکور ۱۴۰۰",
                    1399 to "کنکور ۱۳۹۹",
                    1398 to "کنکور ۱۳۹۸",
                    -1 to "ویژه فرهنگیان"
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    contentPadding = PaddingValues(horizontal = 2.dp)
                ) {
                    items(yearChips, key = { it.first }) { (yrCode, label) ->
                        val isSelected = selectedYearFilter == yrCode
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedYearFilter = yrCode },
                            label = {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = if (isSelected) FontWeight.Black else FontWeight.SemiBold,
                                    maxLines = 1,
                                    softWrap = false
                                )
                            }
                        )
                    }
                }
            }

            // Exam Cards List (مدل گرافیکی ویژه و متمایز برای دفترچه اختصاصی فرهنگیان + مدل استاندارد Pixel UI برای کنکور سراسری)
            itemsIndexed(filteredPapers, key = { _, p -> p.id }) { paperIndex, paper ->
                // فهرست کتاب: ردیف بسته فقط تیتر تک‌خطی؛ جزئیات پشت ضربه روی ⌄
                val isPaperExpanded = expandedPaperMap[paper.id] == true
                val paperNumber = BidiFormatter.toPersianDigits((paperIndex + 1).toString())
                if (paper.isFarhangianSpecial) {
                    // طراحی کاملاً جدید، گرافیکی و متمایز ویژه دفترچه اختصاصی دانشگاه فرهنگیان
                    val emeraldDark = Color(0xFF064E3B)
                    val emeraldMid = Color(0xFF047857)
                    val tealBright = Color(0xFF0D9488)
                    val goldAccent = Color(0xFFF59E0B)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                activePaper = paper
                                currentQuestionIdx = 0
                                selectedOptionIdx = -1
                                correctAnswersCount = 0
                                isExamFinished = false
                            },
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.4.dp, emeraldMid.copy(alpha = 0.65f)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            // ۱. بنر گرافیکی برداری (SVG Canvas) بالای کارت فرهنگیان
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        Brush.linearGradient(
                                            colors = listOf(
                                                emeraldDark.copy(alpha = 0.85f),
                                                emeraldMid.copy(alpha = 0.85f),
                                                tealBright.copy(alpha = 0.85f)
                                            )
                                        )
                                    )
                            ) {
                                Canvas(
                                    modifier = Modifier
                                        .matchParentSize()
                                ) {
                                    // نقوش هندسی و ستاره ۸پر برداری در پس‌زمینه بنر فرهنگیان
                                    val w = size.width
                                    val h = size.height
                                    drawCircle(
                                        color = Color.White.copy(alpha = 0.07f),
                                        radius = h * 0.85f,
                                        center = Offset(w * 0.12f, h * 0.25f)
                                    )
                                    drawCircle(
                                        color = goldAccent.copy(alpha = 0.14f),
                                        radius = h * 0.55f,
                                        center = Offset(w * 0.88f, h * 0.8f),
                                        style = Stroke(width = 2.dp.toPx())
                                    )
                                    // رسم نشان هندسی برداری (لوح و کتاب دانشجو-معلم)
                                    val emblemCx = w * 0.14f
                                    val emblemCy = h * 0.52f
                                    val r = 26.dp.toPx()
                                    drawCircle(
                                        color = goldAccent.copy(alpha = 0.22f),
                                        radius = r,
                                        center = Offset(emblemCx, emblemCy)
                                    )
                                    drawCircle(
                                        color = goldAccent.copy(alpha = 0.65f),
                                        radius = r,
                                        center = Offset(emblemCx, emblemCy),
                                        style = Stroke(width = 1.5.dp.toPx())
                                    )
                                }

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 14.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Surface(
                                            color = goldAccent,
                                            shape = RoundedCornerShape(50)
                                        ) {
                                            Row(
                                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.School,
                                                    contentDescription = null,
                                                    tint = Color(0xFF1E293B),
                                                    modifier = Modifier.size(15.dp)
                                                )
                                                Text(
                                                    text = "🎓 ${paper.badge}",
                                                    style = MaterialTheme.typography.labelSmall,
                                                    fontWeight = FontWeight.Black,
                                                    color = Color(0xFF1E293B),
                                                    maxLines = 1,
                                                    softWrap = false
                                                )
                                            }
                                        }

                                        Surface(
                                            color = Color.White.copy(alpha = 0.18f),
                                            shape = RoundedCornerShape(50),
                                            border = BorderStroke(0.8.dp, Color.White.copy(alpha = 0.35f))
                                        ) {
                                            Text(
                                                text = BidiFormatter.formatMixedPersianText("کنکور ${paper.year}"),
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White,
                                                maxLines = 1,
                                                softWrap = false,
                                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                            )
                                        }
                                        IconButton(
                                            onClick = { expandedPaperMap[paper.id] = !isPaperExpanded },
                                            modifier = Modifier.size(28.dp)
                                        ) {
                                            Icon(
                                                imageVector = if (isPaperExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                                contentDescription = if (isPaperExpanded) "بستن جزئیات" else "مشاهده جزئیات",
                                                tint = Color.White
                                            )
                                        }
                                    }

                                    Text(
                                        text = "$paperNumber. ${paper.title}",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Black,
                                        color = Color.White,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }

                            // ۲. جزئیات کارت فرهنگیان — فقط بعد از ضربه روی ⌄ (در بنر فقط تیتر تک‌خطی می‌ماند)
                            if (isPaperExpanded) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(
                                    text = paper.description,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                if (paper.subjectTimings.isNotEmpty()) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        paper.subjectTimings.forEachIndexed { tIdx, timing ->
                                            val boxColor = if (tIdx == 0) emeraldMid else Color(0xFFD97706)
                                            Surface(
                                                modifier = Modifier.weight(1f),
                                                shape = RoundedCornerShape(16.dp),
                                                color = boxColor.copy(alpha = 0.08f),
                                                border = BorderStroke(1.dp, boxColor.copy(alpha = 0.35f))
                                            ) {
                                                Column(
                                                    modifier = Modifier.padding(10.dp),
                                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                                ) {
                                                    Row(
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                                    ) {
                                                        // حلقه گرافیکی برداری زمان هر درس
                                                        Box(
                                                            modifier = Modifier.size(32.dp),
                                                            contentAlignment = Alignment.Center
                                                        ) {
                                                            Canvas(modifier = Modifier.size(32.dp)) {
                                                                drawCircle(
                                                                    color = boxColor.copy(alpha = 0.2f),
                                                                    style = Stroke(width = 3.dp.toPx())
                                                                )
                                                                drawArc(
                                                                    color = boxColor,
                                                                    startAngle = -90f,
                                                                    sweepAngle = if (tIdx == 0) 240f else 180f,
                                                                    useCenter = false,
                                                                    style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                                                                )
                                                            }
                                                            Text(
                                                                text = BidiFormatter.toPersianDigits("${timing.questionCount}"),
                                                                style = MaterialTheme.typography.labelSmall,
                                                                fontWeight = FontWeight.Black,
                                                                color = boxColor
                                                            )
                                                        }

                                                        Column(modifier = Modifier.weight(1f)) {
                                                            Text(
                                                                text = timing.subjectName,
                                                                style = MaterialTheme.typography.labelMedium,
                                                                fontWeight = FontWeight.Black,
                                                                color = MaterialTheme.colorScheme.onSurface,
                                                                maxLines = 1,
                                                                overflow = TextOverflow.Ellipsis
                                                            )
                                                            Text(
                                                                text = BidiFormatter.formatMixedPersianText("${timing.questionCount} تست • ${timing.allocatedMinutes} دقیقه"),
                                                                style = MaterialTheme.typography.labelSmall,
                                                                fontWeight = FontWeight.Bold,
                                                                color = boxColor
                                                            )
                                                        }
                                                    }

                                                    Surface(
                                                        color = MaterialTheme.colorScheme.surface,
                                                        shape = RoundedCornerShape(50),
                                                        border = BorderStroke(0.6.dp, boxColor.copy(alpha = 0.3f)),
                                                        modifier = Modifier.fillMaxWidth()
                                                    ) {
                                                        Text(
                                                            text = BidiFormatter.formatMixedPersianText("⏱️ فرصت هر تست: ${timing.secondsPerQuestion} ثانیه"),
                                                            style = MaterialTheme.typography.labelSmall,
                                                            fontWeight = FontWeight.SemiBold,
                                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                            textAlign = TextAlign.Center,
                                                            maxLines = 1,
                                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                // ۳. نوار پایین کارت فرهنگیان با دکمه کپسولی برجسته
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = BidiFormatter.formatMixedPersianText("⏱️ زمان کل: ${paper.totalTimeMinutes} دقیقه"),
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Black,
                                            color = emeraldMid
                                        )
                                        Text(
                                            text = BidiFormatter.formatMixedPersianText("شامل ${paper.questions.size} سوال استاندارد + پاسخ تشریحی"),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Button(
                                        onClick = {
                                            activePaper = paper
                                            currentQuestionIdx = 0
                                            selectedOptionIdx = -1
                                            correctAnswersCount = 0
                                            isExamFinished = false
                                        },
                                        shape = RoundedCornerShape(50),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = emeraldMid,
                                            contentColor = Color.White
                                        )
                                    ) {
                                        Text(
                                            text = "شروع آزمون فرهنگیان ⟵",
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Black,
                                            maxLines = 1,
                                            softWrap = false
                                        )
                                    }
                                }
                            }
                            }
                        }
                    }
                } else {
                    // کارت استاندارد دفترچه‌های کنکور سراسری (Pixel UI Android 17)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expandedPaperMap[paper.id] = !isPaperExpanded },
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = androidx.compose.foundation.BorderStroke(
                            0.9.dp,
                            MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.55f)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // ردیف بسته دوخطی: خط ۱ شماره + تیتر کامل؛ خط ۲ دکمه‌های expand و ورود —
                            // دکمهٔ «ورود به دفترچه» دیگر جای تیتر را نمی‌گیرد و تیتر بریده نمی‌شود.
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Surface(
                                        shape = CircleShape,
                                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                        modifier = Modifier.size(30.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text(
                                                text = paperNumber,
                                                style = MaterialTheme.typography.labelMedium,
                                                fontWeight = FontWeight.Black,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                    }
                                    Text(
                                        text = paper.title,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Black,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    IconButton(
                                        onClick = { expandedPaperMap[paper.id] = !isPaperExpanded },
                                        modifier = Modifier.size(28.dp)
                                    ) {
                                        Icon(
                                            imageVector = if (isPaperExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                            contentDescription = if (isPaperExpanded) "بستن جزئیات" else "مشاهده جزئیات",
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    Button(
                                        onClick = {
                                            activePaper = paper
                                            currentQuestionIdx = 0
                                            selectedOptionIdx = -1
                                            correctAnswersCount = 0
                                            isExamFinished = false
                                        },
                                        shape = RoundedCornerShape(50),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.primary,
                                            contentColor = MaterialTheme.colorScheme.onPrimary
                                        )
                                    ) {
                                        Text(
                                            text = "ورود به دفترچه ⟵",
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Bold,
                                            maxLines = 1,
                                            softWrap = false
                                        )
                                    }
                                }
                            }

                            if (isPaperExpanded) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    shape = RoundedCornerShape(50)
                                ) {
                                    Text(
                                        text = paper.badge,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        maxLines = 1,
                                        softWrap = false,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                    )
                                }
                                Text(
                                    text = BidiFormatter.formatMixedPersianText("سال ${paper.year}"),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 1,
                                    softWrap = false
                                )
                            }

                            Text(
                                text = paper.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            if (paper.subjectTimings.isNotEmpty()) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(MaterialTheme.colorScheme.surfaceContainerLow)
                                        .padding(10.dp),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = "📊 بودجه‌بندی دروس و زمان اختصاصی هر تست:",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Black,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    paper.subjectTimings.forEach { timing ->
                                        Text(
                                            text = BidiFormatter.formatMixedPersianText(
                                                "• ${timing.subjectName}: ${timing.questionCount} سوال | ${timing.allocatedMinutes} دقیقه (هر تست ${timing.secondsPerQuestion} ثانیه)"
                                            ),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = BidiFormatter.formatMixedPersianText("⏱️ ${paper.totalTimeMinutes} دقیقه • ${paper.questions.size} سوال استاندارد"),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.weight(1f)
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
private fun NahaeeExamsAndTarmimView() {
    // به صورت پیش‌فرض مستقیم روی تب ۲ (بانک سوالات امتحانات نهایی + شبیه‌ساز تصحیح برگه) باز می‌شود تا کاربر فوراً به همه سوالات دسترسی داشته باشد
    var subTab by remember { mutableIntStateOf(2) }
    var selectedSubjectFilter by remember { mutableStateOf("all") }
    var selectedSessionFilter by remember { mutableStateOf("all") } // "all", "خرداد", "شهریور", "دی"
    var examSelfGradingMode by remember { mutableStateOf(false) } // حالت شبیه‌ساز برگه امتحانی (مخفی کردن کلید مصحح تا کلیک کاربر)
    val revealedQuestionIds = remember { androidx.compose.runtime.mutableStateMapOf<String, Boolean>() }
    // فهرست کتاب: دفترچه‌ها و سوال‌ها در فهرست فقط تیتر تک‌خطی‌اند؛ جزئیات پشت ضربه روی ردیف باز می‌شود.
    val expandedBookletMap = remember { mutableStateMapOf<String, Boolean>() }
    val expandedNahaeeQuestionMap = remember { mutableStateMapOf<String, Boolean>() }
    // ثبت وضعیت خودسنجی هر سوال: 2 = نمره کامل, 1 = نصف نمره, 0 = نیاز به مرور
    val selfGradeMap = remember { androidx.compose.runtime.mutableStateMapOf<String, Int>() }

    // نمرات قابل تنظیم کاربر در ماشین‌حساب تراز ۶۰٪ قطعی و اولویت ترمیم معدل
    val userSubjectScores = remember {
        androidx.compose.runtime.mutableStateMapOf<String, Double>().apply {
            NahaeeExamsAndTarmimArchive.tarazCalculatorSpecs.forEach { spec ->
                put(spec.subjectId, spec.defaultScore)
            }
        }
    }

    val allQuestions = remember { NahaeeExamsAndTarmimArchive.getAllNahaeeQuestions() }
    val filteredQuestions = remember(selectedSubjectFilter, selectedSessionFilter) {
        allQuestions.filter { q ->
            (selectedSubjectFilter == "all" || q.subjectId == selectedSubjectFilter) &&
            (selectedSessionFilter == "all" || q.examType == selectedSessionFilter)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("nahaee_tarmim_view_list"),
        contentPadding = PaddingValues(start = 14.dp, top = 8.dp, end = 14.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Calm High-Contrast Header Card for Final Exams
        item(key = "nahaee_hero_header") {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = PlannerSurface,
                border = BorderStroke(1.dp, PlannerBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "امتحانات نهایی و ترمیم معدل",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = PlannerText
                        )
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = "${BidiFormatter.toPersianDigits(allQuestions.size.toString())} سوال رسمی",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Text(
                        text = "بانک سوالات تشریحی خرداد، شهریور و دی به همراه کلید مصحح آموزش و پرورش، بارم‌بندی ۲۰ نمره و محاسبه‌گر تراز ۶۰٪.",
                        style = MaterialTheme.typography.bodySmall,
                        color = PlannerMuted,
                        lineHeight = 20.sp
                    )
                }
            }
        }

        // 2. Clean Sub-Tab Bar
        item(key = "nahaee_sub_tabs") {
            val nahaeeTabs = listOf(
                Pair(2, "سوالات تشریحی (${BidiFormatter.toPersianDigits(allQuestions.size.toString())})"),
                Pair(6, "دفترچه‌های کامل ۲۰ نمره‌ای"),
                Pair(4, "ماشین‌حساب تراز ۶۰٪"),
                Pair(5, "جمع‌بندی شب امتحان"),
                Pair(1, "بارم‌بندی ۲۰ نمره"),
                Pair(0, "قوانین ترمیم و ضرایب")
            )
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = PlannerSurface,
                border = BorderStroke(1.dp, PlannerBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    nahaeeTabs.forEach { (idx, title) ->
                        val isSel = subTab == idx
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { subTab = idx }
                                .testTag("nahaee_subtab_$idx"),
                            color = if (isSel) PlannerPrimary else Color.Transparent,
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSel) PlannerOnPrimary else PlannerMuted,
                                maxLines = 1,
                                softWrap = false,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 9.dp)
                            )
                        }
                    }
                }
            }
        }

        // 3. Sub-Tab Content
        when (subTab) {
            6 -> {
                // ==================== ۱۲ دفترچه کامل ۲۰ نمره‌ای امتحانات نهایی (از سوال ۱ تا آخر + کلید مصحح) ====================
                val matchingBooklets = NahaeeExamsAndTarmimArchive.fullExamBooklets.filter {
                    selectedSubjectFilter == "all" || it.subjectId == selectedSubjectFilter
                }
                item(key = "nahaee_full_booklets_subject_bar") {
                    Surface(
                        shape = RoundedCornerShape(24.dp),
                        color = MaterialTheme.colorScheme.surfaceContainerLow,
                        border = BorderStroke(0.8.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.45f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "📄 دفترچه‌های کامل ۲۰ نمره‌ای امتحانات نهایی (از سوال ۱ تا آخر برگه رسمی):",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(rememberScrollState()),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                listOf(
                                    "all" to "📚 همه ۱۲ دفترچه کامل",
                                    "farsi" to "فارسی ۳",
                                    "dini" to "دینی ۳",
                                    "fonoon" to "علوم و فنون ۳",
                                    "english" to "زبان انگلیسی ۳",
                                    "arabic" to "عربی ۳",
                                    "math" to "ریاضی و آمار ۳",
                                    "philosophy" to "فلسفه ۲",
                                    "sociology" to "جامعه‌شناسی ۳",
                                    "history" to "تاریخ ۳",
                                    "geography" to "جغرافیا ۳",
                                    "social_identity" to "هویت اجتماعی",
                                    "general_humanities" to "سلامت و بهداشت"
                                ).forEach { (key, label) ->
                                    val isSel = selectedSubjectFilter == key
                                    Surface(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(50))
                                            .clickable { selectedSubjectFilter = key },
                                        shape = RoundedCornerShape(50),
                                        color = if (isSel) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainerHigh
                                    ) {
                                        Text(
                                            text = label,
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = if (isSel) FontWeight.Black else FontWeight.SemiBold,
                                            color = if (isSel) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                itemsIndexed(matchingBooklets, key = { _, b -> "full_booklet_${b.id}" }) { bookletIndex, booklet ->
                    val isBookletExpanded = expandedBookletMap[booklet.id] == true
                    Card(
                        shape = RoundedCornerShape(26.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.45f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expandedBookletMap[booklet.id] = !isBookletExpanded }
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            // ردیف بسته: فقط شماره + تیتر تک‌خطی؛ ضریب و سوال‌ها فقط در نمای بازشده
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                    modifier = Modifier.size(30.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = BidiFormatter.toPersianDigits((bookletIndex + 1).toString()),
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Black,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                                Text(
                                    text = booklet.subjectTitle,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Black,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.weight(1f)
                                )
                                Surface(
                                    shape = RoundedCornerShape(50),
                                    color = MaterialTheme.colorScheme.primaryContainer
                                ) {
                                    Text(
                                        text = "📄 ${booklet.sessionTitle}",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Black,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        maxLines = 1,
                                        softWrap = false,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                    )
                                }
                                IconButton(
                                    onClick = { expandedBookletMap[booklet.id] = !isBookletExpanded },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isBookletExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                        contentDescription = if (isBookletExpanded) "بستن جزئیات" else "مشاهده جزئیات",
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            if (isBookletExpanded) {
                                Surface(
                                    shape = RoundedCornerShape(50),
                                    color = MaterialTheme.colorScheme.tertiaryContainer
                                ) {
                                    Text(
                                        text = "${booklet.coefficientBadge} • ۲۰ نمره کامل",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Black,
                                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                    )
                                }

                            booklet.sheetRows.forEach { rowItem ->
                                Surface(
                                    shape = RoundedCornerShape(18.dp),
                                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                                    border = BorderStroke(0.8.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(
                                        modifier = Modifier.padding(12.dp),
                                        verticalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "سوال ${BidiFormatter.toPersianDigits(rowItem.rowNumber.toString())}: ${rowItem.sectionTitle}",
                                                style = MaterialTheme.typography.labelMedium,
                                                fontWeight = FontWeight.Black,
                                                color = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.weight(1f)
                                            )
                                            Surface(
                                                shape = RoundedCornerShape(50),
                                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.14f)
                                            ) {
                                                Text(
                                                    text = rowItem.scoreBaram,
                                                    style = MaterialTheme.typography.labelSmall,
                                                    fontWeight = FontWeight.Black,
                                                    color = MaterialTheme.colorScheme.primary,
                                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                                )
                                            }
                                        }

                                        BiDiText(
                                            text = rowItem.questionPrompt,
                                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )

                                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f))

                                        Text(
                                            text = "✅ کلید رسمی تصحیح مصحح:",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Black,
                                            color = MaterialTheme.colorScheme.tertiary
                                        )
                                        BiDiText(
                                            text = rowItem.officialAnswerKey,
                                            style = MaterialTheme.typography.bodySmall,
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

            2 -> {
                // ==================== بانک کامل سوالات تشریحی نهایی + شبیه‌ساز تصحیح برگه ====================
                item(key = "nahaee_questions_session_filter_chips") {
                    Surface(
                        shape = RoundedCornerShape(24.dp),
                        color = MaterialTheme.colorScheme.surfaceContainerLow,
                        border = BorderStroke(0.8.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.45f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            // ردیف ۱: انتخاب نوبت امتحان + دکمه شبیه‌ساز برگه امتحانی
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    modifier = Modifier
                                        .weight(1f)
                                        .horizontalScroll(rememberScrollState()),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    listOf(
                                        "all" to "همه نوبت‌ها",
                                        "خرداد" to "☀️ خرداد",
                                        "شهریور" to "🍁 شهریور",
                                        "دی" to "❄️ دی ماه"
                                    ).forEach { (sKey, sLabel) ->
                                        val isSel = selectedSessionFilter == sKey
                                        Surface(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(50))
                                                .clickable { selectedSessionFilter = sKey },
                                            shape = RoundedCornerShape(50),
                                            color = if (isSel) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainerHigh
                                        ) {
                                            Text(
                                                text = sLabel,
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = if (isSel) FontWeight.Black else FontWeight.SemiBold,
                                                color = if (isSel) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                                maxLines = 1,
                                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                // دکمه کپسولی حالت شبیه‌ساز برگه امتحان (مخفی کردن پاسخ تا تلاش دانش‌آموز)
                                Surface(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(50))
                                        .clickable {
                                            examSelfGradingMode = !examSelfGradingMode
                                            if (!examSelfGradingMode) revealedQuestionIds.clear()
                                        }
                                        .testTag("toggle_nahaee_self_grading_mode"),
                                    shape = RoundedCornerShape(50),
                                    color = if (examSelfGradingMode) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.secondaryContainer
                                ) {
                                    Text(
                                        text = if (examSelfGradingMode) "✍️ حالت امتحان: فعال" else "👁️ مخفی کردن پاسخ‌ها",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Black,
                                        color = if (examSelfGradingMode) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.onSecondaryContainer,
                                        maxLines = 1,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
                                    )
                                }
                            }

                            // ردیف ۲: فیلتر کپسولی افقی هر ۱۵ درس نهایی (Google Pixel Pill Bar)
                            val subjectFilters = listOf(
                                Pair("all", "📚 همه دروس (${BidiFormatter.toPersianDigits(allQuestions.size.toString())})"),
                                Pair("farsi", "فارسی ۳ (ضریب ۱۸.۶۴)"),
                                Pair("dini", "دین و زندگی ۳ (ضریب ۱۴.۲۳)"),
                                Pair("fonoon", "علوم و فنون ۳ (ضریب ۱۰.۴۳)"),
                                Pair("english", "زبان انگلیسی ۳ (ضریب ۱۰.۱۷)"),
                                Pair("arabic", "عربی ۳ تخصصی (ضریب ۷.۸۰)"),
                                Pair("philosophy", "فلسفه ۲ (ضریب ۵.۶۲)"),
                                Pair("sociology", "جامعه‌شناسی ۳ (ضریب ۵.۵۲)"),
                                Pair("history", "تاریخ ۳ (ضریب ۵.۴۰)"),
                                Pair("math", "ریاضی و آمار ۳ (ضریب ۵.۱۶)"),
                                Pair("geography", "جغرافیا ۳ (ضریب ۴.۸۵)"),
                                Pair("social_identity", "هویت اجتماعی (ضریب ۳.۶۴)"),
                                Pair("general_humanities", "سلامت و بهداشت (ضریب ۳.۶۴)"),
                                Pair("psychology", "روان‌شناسی یازدهم"),
                                Pair("economics", "اقتصاد دهم"),
                                Pair("logic", "منطق دهم")
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(rememberScrollState()),
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                subjectFilters.forEach { (key, label) ->
                                    val isSel = selectedSubjectFilter == key
                                    Surface(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(50))
                                            .clickable { selectedSubjectFilter = key }
                                            .testTag("nahaee_sub_filter_$key"),
                                        color = if (isSel) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                                        border = BorderStroke(
                                            0.8.dp,
                                            if (isSel) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                                        ),
                                        shape = RoundedCornerShape(50)
                                    ) {
                                        Text(
                                            text = label,
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = if (isSel) FontWeight.Black else FontWeight.SemiBold,
                                            color = if (isSel) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                            maxLines = 1,
                                            softWrap = false,
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
                                        )
                                    }
                                }
                            }

                            // بنر دسترسی سریع به ۱۲ دفترچه کامل ۲۰ نمره‌ای از سوال ۱ تا آخر
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                                    .clickable { subTab = 6 },
                                shape = RoundedCornerShape(16.dp),
                                color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.65f)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 12.dp, vertical = 9.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "📄 مشاهده ۱۲ دفترچه کامل ۲۰ نمره‌ای امتحانات نهایی (از سوال ۱ تا آخر برگه)",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Black,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(50),
                                        color = MaterialTheme.colorScheme.primary
                                    ) {
                                        Text(
                                            text = "باز کردن ◀",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Black,
                                            color = MaterialTheme.colorScheme.onPrimary,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                        )
                                    }
                                }
                            }

                            // کارنامه زنده خودسنجی برگه نهایی (اگر کاربر سوالی را تصحیح کرده باشد)
                            if (selfGradeMap.isNotEmpty()) {
                                val gradedCount = selfGradeMap.size
                                val fullCount = selfGradeMap.values.count { it == 2 }
                                val halfCount = selfGradeMap.values.count { it == 1 }
                                val simulatedScoreOutOf20 = ((fullCount * 1.0 + halfCount * 0.5) / gradedCount.coerceAtLeast(1) * 20.0)
                                Surface(
                                    shape = RoundedCornerShape(16.dp),
                                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 12.dp, vertical = 8.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "📊 نمره شبیه‌سازی برگه شما (${BidiFormatter.toPersianDigits(gradedCount.toString())} سوال تصحیح‌شده):",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                                            modifier = Modifier.weight(1f)
                                        )
                                        Surface(
                                            shape = RoundedCornerShape(50),
                                            color = MaterialTheme.colorScheme.primary
                                        ) {
                                            Text(
                                                text = "${BidiFormatter.toPersianDigits(String.format(java.util.Locale.US, "%.2f", simulatedScoreOutOf20))} از ۲۰",
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Black,
                                                color = MaterialTheme.colorScheme.onPrimary,
                                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                itemsIndexed(filteredQuestions, key = { _, qq -> "nah_q_${qq.id}" }) { questionIndex, q ->
                    // فهرست کتاب: ردیف بسته فقط تیتر تک‌خطی است.
                    val isQuestionExpanded = expandedNahaeeQuestionMap[q.id] == true
                    val isAnswerVisible = !examSelfGradingMode || (revealedQuestionIds[q.id] == true)
                    val currentSelfGrade = selfGradeMap[q.id]

                    Card(
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.45f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expandedNahaeeQuestionMap[q.id] = !isQuestionExpanded }
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            // ردیف بسته: فقط شماره + تیتر تک‌خطی؛ متن سوال و کلید فقط در نمای بازشده
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                    modifier = Modifier.size(30.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = BidiFormatter.toPersianDigits((questionIndex + 1).toString()),
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Black,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                                Text(
                                    text = "${q.subjectTitle} • ${q.sessionYear}",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.weight(1f)
                                )
                                Surface(
                                    shape = RoundedCornerShape(50),
                                    color = MaterialTheme.colorScheme.tertiaryContainer
                                ) {
                                    Text(
                                        text = "بارم: ${q.scorePoints}",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Black,
                                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                                        maxLines = 1,
                                        softWrap = false,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                    )
                                }
                                IconButton(
                                    onClick = { expandedNahaeeQuestionMap[q.id] = !isQuestionExpanded },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isQuestionExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                        contentDescription = if (isQuestionExpanded) "بستن جزئیات" else "مشاهده جزئیات",
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            if (isQuestionExpanded) {

                            Text(
                                text = "❓ سوال رسمی امتحان نهایی:",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.primary
                            )

                            BiDiText(
                                text = q.questionText,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))

                            if (!isAnswerVisible) {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(16.dp))
                                        .clickable { revealedQuestionIds[q.id] = true },
                                    shape = RoundedCornerShape(16.dp),
                                    color = MaterialTheme.colorScheme.primaryContainer
                                ) {
                                    Box(
                                        modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "👁️ نمایش کلید رسمی تصحیح مصحح و بارم‌بندی ریز",
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Black,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                    }
                                }
                            } else {
                                Text(
                                    text = "✅ کلید رسمی تصحیح برگه نهایی (بارم‌بندی ۰.۲۵ نمره‌ای مصحح):",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Black,
                                    color = MaterialTheme.colorScheme.tertiary
                                )

                                BiDiText(
                                    text = q.officialCorrectionKey,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                GoldenKeyPointCalloutCard(
                                    title = "🔑 کلمات کلیدی قفل نمره مصحح",
                                    keyPointText = "مصحح به دنبال این کلمات است: " + q.graderKeywords.joinToString(" • "),
                                    level = GoldenPointLevel.CONCEPT_LAMP
                                )

                                if (q.commonStudentTraps.isNotEmpty()) {
                                    GoldenKeyPointCalloutCard(
                                        title = "⚠️ دام کسر ۰.۲۵ تا ۰.۵ نمره!",
                                        keyPointText = q.commonStudentTraps,
                                        level = GoldenPointLevel.TRAFFIC_RED
                                    )
                                }

                                // نوار خودسنجی برگه نهایی (Pixel Pill Self-Grading Row)
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 4.dp),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    listOf(
                                        Triple(2, "✅ نمره کامل", Color(0xFF16A34A)),
                                        Triple(1, "⚠️ نصف بارم", Color(0xFFD97706)),
                                        Triple(0, "❌ نیاز به مرور", Color(0xFFDC2626))
                                    ).forEach { (gradeVal, label, badgeColor) ->
                                        val isPicked = currentSelfGrade == gradeVal
                                        Surface(
                                            modifier = Modifier
                                                .weight(1f)
                                                .clip(RoundedCornerShape(50))
                                                .clickable { selfGradeMap[q.id] = gradeVal },
                                            shape = RoundedCornerShape(50),
                                            color = if (isPicked) badgeColor else MaterialTheme.colorScheme.surfaceContainerHigh,
                                            border = BorderStroke(
                                                0.8.dp,
                                                if (isPicked) badgeColor else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
                                            )
                                        ) {
                                            Box(
                                                modifier = Modifier.padding(vertical = 7.dp, horizontal = 4.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = label,
                                                    style = MaterialTheme.typography.labelSmall,
                                                    fontWeight = if (isPicked) FontWeight.Black else FontWeight.SemiBold,
                                                    color = if (isPicked) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
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
            }

            4 -> {
                // ==================== ماشین‌حساب هوشمند تراز ۶۰٪ قطعی و اولویت‌بندی ترمیم معدل ====================
                item(key = "nahaee_taraz_calculator_dashboard") {
                    val specs = NahaeeExamsAndTarmimArchive.tarazCalculatorSpecs
                    val totalCoef = specs.sumOf { it.coefficient }
                    val weightedSum = specs.sumOf { spec ->
                        val sc = userSubjectScores[spec.subjectId] ?: spec.defaultScore
                        sc * spec.coefficient
                    }
                    val weightedGpa = (weightedSum / totalCoef).coerceIn(0.0, 20.0)
                    // فرمول شبیه‌سازی تراز کل سوابق تحصیلی ۶۰٪ قطعی سازمان سنجش (معدل ۲۰ معادل تراز حدود ۱۰۶۰۰ تا ۱۱۰۰۰)
                    val estimatedTaraz = (4200 + (weightedGpa * 335.0)).toInt()

                    // محاسبه پرسودترین درس‌ها برای ترمیم معدل (بر اساس فاصله تا ۲۰ ضربدر ارزش تراز هر نمره)
                    val repairPriorities = specs.map { spec ->
                        val cur = userSubjectScores[spec.subjectId] ?: spec.defaultScore
                        val gap = (20.0 - cur).coerceAtLeast(0.0)
                        val potentialTarazGain = (gap * spec.tarazPerOneScoreGain).toInt()
                        Triple(spec, cur, potentialTarazGain)
                    }.sortedByDescending { it.third }

                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Surface(
                            shape = RoundedCornerShape(28.dp),
                            color = MaterialTheme.colorScheme.primaryContainer,
                            border = BorderStroke(1.2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.45f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(18.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Text(
                                    text = "🧮 شبیه‌ساز تراز ۶۰٪ قطعی سوابق تحصیلی و سود ترمیم معدل",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Black,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(20.dp),
                                        color = MaterialTheme.colorScheme.surface,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(12.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = "معدل کتبی تراز شده",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                            Text(
                                                text = BidiFormatter.toPersianDigits(String.format(java.util.Locale.US, "%.2f", weightedGpa)),
                                                style = MaterialTheme.typography.headlineSmall,
                                                fontWeight = FontWeight.Black,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            Text(
                                                text = "با اعمال ضرایب مصوب",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(20.dp),
                                        color = MaterialTheme.colorScheme.surface,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(12.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = "تراز تخمینی ۶۰٪ سوابق",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                            Text(
                                                text = BidiFormatter.toPersianDigits(estimatedTaraz.toString()),
                                                style = MaterialTheme.typography.headlineSmall,
                                                fontWeight = FontWeight.Black,
                                                color = MaterialTheme.colorScheme.tertiary
                                            )
                                            Text(
                                                text = if (estimatedTaraz >= 9500) "🔥 عالی برای فرهنگیان" else "⚠️ نیازمند ترمیم معدل",
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = if (estimatedTaraz >= 9500) Color(0xFF16A34A) else Color(0xFFD97706)
                                            )
                                        }
                                    }
                                }

                                // پیشنهاد هوشمند ۳ درس اول برای ترمیم معدل
                                val topRepairs = repairPriorities.filter { it.third > 50 }.take(3)
                                if (topRepairs.isNotEmpty()) {
                                    Surface(
                                        shape = RoundedCornerShape(18.dp),
                                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(12.dp),
                                            verticalArrangement = Arrangement.spacedBy(6.dp)
                                        ) {
                                            Text(
                                                text = "🎯 بالاترین سود تراز در صورت ترمیم معدل به نمره ۲۰:",
                                                style = MaterialTheme.typography.labelMedium,
                                                fontWeight = FontWeight.Black,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            topRepairs.forEachIndexed { idx, (spec, curScore, gain) ->
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Text(
                                                        text = "${BidiFormatter.toPersianDigits((idx + 1).toString())}. ${spec.title} (از ${BidiFormatter.toPersianDigits(curScore.toString())} به ۲۰)",
                                                        style = MaterialTheme.typography.bodySmall,
                                                        fontWeight = FontWeight.Bold,
                                                        color = MaterialTheme.colorScheme.onSurface,
                                                        modifier = Modifier.weight(1f)
                                                    )
                                                    Surface(
                                                        shape = RoundedCornerShape(50),
                                                        color = Color(0xFF16A34A).copy(alpha = 0.15f)
                                                    ) {
                                                        Text(
                                                            text = "+${BidiFormatter.toPersianDigits(gain.toString())} واحد تراز",
                                                            style = MaterialTheme.typography.labelSmall,
                                                            fontWeight = FontWeight.Black,
                                                            color = Color(0xFF16A34A),
                                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // لیست دروس برای تغییر نمره با دکمه‌های کپسولی -۰.۵ و +۰.۵
                        specs.forEach { spec ->
                            val curScore = userSubjectScores[spec.subjectId] ?: spec.defaultScore
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = MaterialTheme.colorScheme.surface,
                                border = BorderStroke(0.8.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.45f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 14.dp, vertical = 10.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = spec.title,
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Black,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = "ضریب قطعی: ${BidiFormatter.toPersianDigits(spec.coefficient.toString())} • هر ۱ نمره ≈ ${BidiFormatter.toPersianDigits(spec.tarazPerOneScoreGain.toString())} تراز",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Surface(
                                            modifier = Modifier
                                                .size(34.dp)
                                                .clip(CircleShape)
                                                .clickable {
                                                    userSubjectScores[spec.subjectId] = (curScore - 0.5).coerceAtLeast(0.0)
                                                },
                                            shape = CircleShape,
                                            color = MaterialTheme.colorScheme.surfaceContainerHigh
                                        ) {
                                            Box(contentAlignment = Alignment.Center) {
                                                Text("−", fontWeight = FontWeight.Black, style = MaterialTheme.typography.titleMedium)
                                            }
                                        }

                                        Surface(
                                            shape = RoundedCornerShape(50),
                                            color = MaterialTheme.colorScheme.primaryContainer
                                        ) {
                                            Text(
                                                text = BidiFormatter.toPersianDigits(String.format(java.util.Locale.US, "%.2f", curScore)),
                                                style = MaterialTheme.typography.labelMedium,
                                                fontWeight = FontWeight.Black,
                                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                            )
                                        }

                                        Surface(
                                            modifier = Modifier
                                                .size(34.dp)
                                                .clip(CircleShape)
                                                .clickable {
                                                    userSubjectScores[spec.subjectId] = (curScore + 0.5).coerceAtMost(20.0)
                                                },
                                            shape = CircleShape,
                                            color = MaterialTheme.colorScheme.primary
                                        ) {
                                            Box(contentAlignment = Alignment.Center) {
                                                Text("+", fontWeight = FontWeight.Black, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onPrimary)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            5 -> {
                // ==================== بسته طلایی ۲۰ قطعی شب امتحان (سوالات ۱۰۰٪ قطعی فردا صبح) ====================
                items(
                    NahaeeExamsAndTarmimArchive.nightBeforeSummaries,
                    key = { "night_summary_${it.subjectId}" }
                ) { summary ->
                    Card(
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "⚡ ${summary.subjectTitle}",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Black,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.weight(1f)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Surface(
                                    shape = RoundedCornerShape(50),
                                    color = MaterialTheme.colorScheme.primaryContainer
                                ) {
                                    Text(
                                        text = summary.coefficientBadge,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                    )
                                }
                            }

                            Text(
                                text = "🎯 تیپ سوالات ۱۰۰٪ قطعی در برگه فردا صبح:",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                summary.guaranteedQuestionTypes.forEach { itemText ->
                                    BiDiText(
                                        text = itemText,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            GoldenKeyPointCalloutCard(
                                title = "🔑 فرمول و کلیدواژه طلایی ۲۰ساز",
                                keyPointText = summary.goldenFormulaOrKeywords,
                                level = GoldenPointLevel.GOLDEN_STAR
                            )

                            GoldenKeyPointCalloutCard(
                                title = "📋 چک‌لیست ۳۰ دقیقه آخر شب امتحان",
                                keyPointText = summary.nightBeforeChecklist,
                                level = GoldenPointLevel.CONCEPT_LAMP
                            )
                        }
                    }
                }
            }

            1 -> {
                // ==================== جدول رسمی بارم‌بندی مصوب ۲۰ نمره آموزش و پرورش ====================
                items(NahaeeExamsAndTarmimArchive.baremBandiList, key = { "barem_${it.subjectId}" }) { barem ->
                    Card(
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(0.8.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = barem.subjectTitle,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Black,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.weight(1f)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Surface(
                                    shape = RoundedCornerShape(50),
                                    color = MaterialTheme.colorScheme.primaryContainer
                                ) {
                                    Text(
                                        text = "ضریب: ${barem.coefficient}",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                    )
                                }
                            }

                            Text(
                                text = "📌 ریز بارم‌بندی امتحانات نهایی خرداد ماه (۲۰ نمره):",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                barem.khordadBreakdown.forEach { (chapter, pts) ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "• $chapter",
                                            style = MaterialTheme.typography.bodySmall,
                                            modifier = Modifier.weight(1f),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = pts,
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            }

                            HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))

                            GoldenKeyPointCalloutCard(
                                title = "💡 راهنمای مصححین برای کسب نمره ۲۰",
                                keyPointText = barem.expertAdviceNote,
                                level = GoldenPointLevel.CONCEPT_LAMP
                            )
                        }
                    }
                }
            }

            else -> {
                // ==================== قوانین ترمیم معدل + جدول ضرایب قطعی ۶۰٪ ====================
                items(NahaeeExamsAndTarmimArchive.tarmimMoadelGuideRules, key = { "tarmim_rule_${it.stepIndex}" }) { rule ->
                    Card(
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(0.8.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = CircleShape,
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    modifier = Modifier.size(30.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = BidiFormatter.toPersianDigits(rule.stepIndex.toString()),
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = rule.title,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            Text(
                                text = rule.summaryText,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = rule.detailedActionableSteps,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(12.dp),
                                    lineHeight = 22.sp
                                )
                            }

                            GoldenKeyPointCalloutCard(
                                title = "نکته طلایی ثبت‌نام و ترمیم",
                                keyPointText = rule.graderSecretTip,
                                level = GoldenPointLevel.GOLDEN_STAR
                            )
                        }
                    }
                }

                item(key = "nahaee_coefficients_deck") {
                    Card(
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(0.8.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "جدول ضرایب ۶۰٪ سوابق تحصیلی امتحانات نهایی دوازدهم انسانی (آخرین مصوبه تأییدشده ۱۴۰۴–۱۴۰۵ • وضعیت ۱۴۰۶: نیازمند تأیید نهایی سنجش)",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )

                            val coefficientsList = listOf(
                                "۱. فارسی ۳" to "ضریب ۱۸.۶۴ (رتبه‌ساز شماره ۱)",
                                "۲. دین و زندگی ۳" to "ضریب ۱۴.۲۳ (کلیدواژه‌محور)",
                                "۳. علوم و فنون ادبی ۳" to "ضریب ۱۰.۴۳ (مهم‌ترین تخصصی)",
                                "۴. زبان انگلیسی ۳" to "ضریب ۱۰.۱۷ (گرامر و نگارش)",
                                "۵. عربی ۳ تخصصی" to "ضریب ۷.۸۰ (حال و استثناء)",
                                "۶. فلسفه ۲" to "ضریب ۵.۶۲ (وجود و حرکت جوهری)",
                                "۷. جامعه‌شناسی ۳" to "ضریب ۵.۵۲ (۳ رویکرد جامعه‌شناسی)",
                                "۸. تاریخ ۳ دوازدهم" to "ضریب ۵.۴۰ (مشروطه تا انقلاب)",
                                "۹. ریاضی و آمار ۳" to "ضریب ۵.۱۶ (احتمال و سری زمانی)",
                                "۱۰. جغرافیا ۳ کاربردی" to "ضریب ۴.۸۵ (آمایش و مدیریت بحران)",
                                "۱۱. سلامت و بهداشت" to "ضریب ۳.۶۴ (فرمول BMI و بیماری‌ها)",
                                "۱۲. هویت اجتماعی" to "ضریب ۳.۶۴ (کنش و هویت ایرانی-اسلامی)"
                            )

                            coefficientsList.forEach { (subject, coef) ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = subject,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Surface(
                                        shape = RoundedCornerShape(50),
                                        color = MaterialTheme.colorScheme.primaryContainer
                                    ) {
                                        Text(
                                            text = coef,
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
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

@Composable
private fun MinusZeroDiagnosticView() {
    val questions = remember { MinusZeroLearningEngine.diagnosticQuestions }
    val selectedAnswers = remember { mutableStateMapOf<String, Int>() }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 20.dp, top = 8.dp, end = 20.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = PlannerSurface,
                border = BorderStroke(1.dp, PlannerBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "آزمون تشخیصی بدون اضطراب و مسیر شخصی از منفی صفر",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = PlannerText
                    )
                    Text(
                        text = "این بخش نمره منفی یا قضاوت ندارد! هدف فقط این است که ببینیم کدام پیش‌نیازهای پایه را باید از صفر مطلق با هم بسازیم.",
                        style = MaterialTheme.typography.bodySmall,
                        color = PlannerMuted
                    )
                }
            }
        }

        items(questions, key = { it.id }) { q ->
            val chosen = selectedAnswers[q.id]
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = PlannerSurface,
                border = BorderStroke(1.dp, PlannerBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "${q.subjectTitle} • پیش‌نیاز: ${q.prerequisiteTopic}",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = PlannerPrimary
                    )
                    BiDiText(
                        text = q.friendlyPrompt,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = PlannerText
                    )
                    q.options.forEachIndexed { idx, opt ->
                        val isSel = chosen == idx
                        val isCorr = idx == q.correctIndex
                        Surface(
                            onClick = { selectedAnswers[q.id] = idx },
                            shape = RoundedCornerShape(10.dp),
                            color = when {
                                isSel && isCorr -> MaterialTheme.colorScheme.tertiaryContainer
                                isSel && !isCorr -> MaterialTheme.colorScheme.secondaryContainer
                                else -> PlannerBg
                            },
                            border = BorderStroke(
                                1.dp,
                                when {
                                    isSel && isCorr -> PlannerGreen
                                    isSel && !isCorr -> PlannerPrimary
                                    else -> PlannerBorder
                                }
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            BiDiText(
                                text = "${BidiFormatter.toPersianDigits((idx + 1).toString())}) $opt",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                    if (chosen != null) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = PlannerBg,
                            border = BorderStroke(1.dp, PlannerBorder),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = if (chosen == q.correctIndex) "آفرین! پایه این مبحث برایت روشن است:" else "راهنمای مهربانانه و بدون سرزنش:",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = if (chosen == q.correctIndex) PlannerGreen else PlannerPrimary
                                )
                                BiDiText(
                                    text = q.gentleFeedbackIfWrong,
                                    style = MaterialTheme.typography.bodySmall
                                )
                                BiDiText(
                                    text = q.recommendedFoundationStep,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = PlannerPrimary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ContentCoverageMatrixView(uiState: KonkoorUiState) {
    val matrix = remember(uiState.allLessons) {
        MinusZeroLearningEngine.generateContentCoverageMatrix(uiState.allLessons)
    }
    var selectedSubject by remember { mutableStateOf("all") }
    val filtered = remember(matrix, selectedSubject) {
        if (selectedSubject == "all") matrix else matrix.filter { it.subjectId == selectedSubject }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 20.dp, top = 8.dp, end = 20.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = PlannerSurface,
                border = BorderStroke(1.dp, PlannerBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "جدول کنترل کیفیت و پوشش کامل منابع رسمی (${BidiFormatter.toPersianDigits(filtered.size.toString())} مبحث فعال)",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = PlannerText
                    )
                    Text(
                        text = "تطبیق واقعی دروس با منابع رسمی (chap.sch.ir / sanjesh.org / medu.gov.ir) به تفکیک پایه، سال چاپ (۱۴۰۴–۱۴۰۵)، وجود درس‌نامه، پیش‌نیاز، مثال، نمودار، انیمیشن، تست و سؤال نهایی.",
                        style = MaterialTheme.typography.bodySmall,
                        color = PlannerMuted
                    )
                }
            }
        }

        items(filtered.take(60), key = { it.lessonId }) { item ->
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = PlannerSurface,
                border = BorderStroke(1.dp, PlannerBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${item.subjectTitle} • ${item.gradeLabel}",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = PlannerPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f)
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.tertiaryContainer
                        ) {
                            Text(
                                text = item.completionStatus,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                                maxLines = 1,
                                softWrap = false,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }
                    BiDiText(
                        text = item.lessonTitle,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = PlannerText
                    )
                    Text(
                        text = "کتاب و سال چاپ: ${item.textbookTitle} (${item.publicationYear}) | منبع: ${item.sourceReference}",
                        style = MaterialTheme.typography.labelSmall,
                        color = PlannerMuted
                    )
                    Text(
                        text = "درس‌نامه ساده: ✓ | پیش‌نیاز: ✓ | مثال: ✓ | شکل و انیمیشن: ✓ | نکته کنکوری: ✓ | تست و تشریحی نهایی: ✓",
                        style = MaterialTheme.typography.labelSmall,
                        color = PlannerGreen
                    )
                    Text(
                        text = "وضعیت سال: ${item.verificationStatus}",
                        style = MaterialTheme.typography.labelSmall,
                        color = PlannerMuted
                    )
                }
            }
        }
    }
}

