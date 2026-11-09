package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.adaptive.DiagnosticEngine
import com.example.data.adaptive.DiagnosticSession
import com.example.data.adaptive.QuestionAttempt
import com.example.data.adaptive.buildDiagnosticBank
import com.example.data.curriculum.SubjectCatalog
import com.example.data.curriculum.ZeroLesson
import com.example.data.curriculum.normalizedSubjectId
import com.example.ui.KonkoorUiState
import com.example.ui.KonkoorViewModel
import com.example.ui.components.BidiFormatter
import com.example.ui.theme.resolveDynamicAccent
import com.example.util.StreakCalculator

/**
 * صفحه تشخیص سطح یک مبحث (§۳ الحاقیه).
 *
 * کاربر اول درس را انتخاب می‌کند، بعد موتور سؤال‌به‌سؤال جلو می‌رود:
 * اول یک سؤال از خود درس، اگر غلط بود سؤال پایه، و بعد نتیجه با وضعیت
 * مبحث و درس پیشنهادی. اگر برای درسی سؤال تشخیصی نداریم، «داده کافی نیست»
 * نشان داده می‌شود — سؤال ساخته نمی‌شود.
 */
@Composable
fun DiagnosticFlowScreen(
    viewModel: KonkoorViewModel,
    uiState: KonkoorUiState,
    onOpenLesson: (String) -> Unit
) {
    var selectedSubjectId by remember { mutableStateOf<String?>(null) }
    var selectedLesson by remember { mutableStateOf<ZeroLesson?>(null) }
    var bank by remember { mutableStateOf<List<DiagnosticEngine.DiagnosticQuestionItem>>(emptyList()) }
    var answers by remember { mutableStateOf<List<QuestionAttempt>>(emptyList()) }
    var currentQuestion by remember { mutableStateOf<DiagnosticEngine.DiagnosticQuestionItem?>(null) }
    var selectedOption by remember { mutableStateOf<Int?>(null) }
    var result by remember { mutableStateOf<DiagnosticEngine.DiagnosticResult?>(null) }
    var noQuestions by remember { mutableStateOf(false) }

    fun resetToPicker() {
        selectedLesson = null
        bank = emptyList()
        answers = emptyList()
        currentQuestion = null
        selectedOption = null
        result = null
        noQuestions = false
    }

    fun startDiagnostic(lesson: ZeroLesson) {
        val lessonBank = buildDiagnosticBank(lesson)
        selectedLesson = lesson
        answers = emptyList()
        selectedOption = null
        result = null
        if (lessonBank.isEmpty()) {
            bank = emptyList()
            currentQuestion = null
            noQuestions = true
            return
        }
        noQuestions = false
        bank = lessonBank
        currentQuestion = viewModel.adaptiveNextDiagnosticQuestion(lessonBank, emptyList())
    }

    fun confirmAnswer() {
        val lesson = selectedLesson ?: return
        val question = currentQuestion ?: return
        val chosen = selectedOption ?: return
        val attempt = QuestionAttempt(
            questionId = question.id,
            topicId = lesson.id,
            subjectId = question.subjectId,
            level = question.level,
            isCorrect = chosen == question.correctIndex,
            wasSkipped = false,
            timeSpentSeconds = 0,
            answeredAtEpochDay = StreakCalculator.todayEpochDay()
        )
        viewModel.recordDiagnosticAnswer(attempt)
        val updatedAnswers = answers + attempt
        answers = updatedAnswers
        selectedOption = null
        val next = viewModel.adaptiveNextDiagnosticQuestion(bank, updatedAnswers)
        if (next == null) {
            val concluded = viewModel.adaptiveConcludeDiagnostic(
                topicId = lesson.id,
                subjectId = lesson.normalizedSubjectId(),
                answers = updatedAnswers,
                bank = bank,
                lessonId = lesson.id
            )
            result = concluded
            if (concluded != null) {
                viewModel.recordDiagnosticSession(
                    DiagnosticSession(
                        topicId = lesson.id,
                        subjectId = lesson.normalizedSubjectId(),
                        askedQuestionIds = updatedAnswers.map { it.questionId },
                        answers = updatedAnswers,
                        concludedStatus = concluded.status,
                        weakestLevel = concluded.weakestLevel,
                        recommendedLessonId = concluded.recommendedLessonId,
                        concludedAtEpochDay = StreakCalculator.todayEpochDay()
                    )
                )
            }
            currentQuestion = null
        } else {
            currentQuestion = next
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        listOf(
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.85f),
                            MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.70f)
                        )
                    ),
                    RoundedCornerShape(20.dp)
                )
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = "تشخیص سطح",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    softWrap = false,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = "چند سؤال کوتاه نشان می‌دهد از این مبحث کجا هستی؛ بعد، درس مناسب برای شروع پیشنهاد می‌شود.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))

        when {
            result != null -> DiagnosticResultView(
                result = result!!,
                uiState = uiState,
                onOpenLesson = onOpenLesson,
                onPickAnother = { resetToPicker() }
            )
            selectedLesson != null && noQuestions -> {
                Text(
                    text = "داده کافی نیست",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "برای این درس هنوز سؤال تشخیصی نداریم؛ درس را از قدم اول بخوان و بعد دوباره امتحان کن.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedButton(onClick = { resetToPicker() }) {
                    Text(text = "بازگشت به فهرست درس‌ها")
                }
            }
            selectedLesson != null && currentQuestion != null -> DiagnosticQuestionView(
                question = currentQuestion!!,
                questionNumber = answers.size + 1,
                selectedOption = selectedOption,
                onSelectOption = { selectedOption = it },
                onConfirm = { confirmAnswer() }
            )
            selectedLesson != null -> {
                Text(
                    text = "داده کافی نیست",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "موتور تطبیقی وصل نیست؛ سؤال تشخیصی در دسترس نیست.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedButton(onClick = { resetToPicker() }) {
                    Text(text = "بازگشت به فهرست درس‌ها")
                }
            }
            else -> DiagnosticLessonPicker(
                uiState = uiState,
                viewModel = viewModel,
                selectedSubjectId = selectedSubjectId,
                onSelectSubject = { selectedSubjectId = it },
                onStartDiagnostic = { startDiagnostic(it) }
            )
        }
    }
}

@Composable
private fun DiagnosticLessonPicker(
    uiState: KonkoorUiState,
    viewModel: KonkoorViewModel,
    selectedSubjectId: String?,
    onSelectSubject: (String) -> Unit,
    onStartDiagnostic: (ZeroLesson) -> Unit
) {
    val subjects = remember(uiState.allLessons) {
        uiState.allLessons.map { it.normalizedSubjectId() }.distinct().sorted()
    }
    val activeSubject = selectedSubjectId ?: subjects.firstOrNull()
    val lessons = remember(uiState.allLessons, activeSubject) {
        uiState.allLessons.filter { it.normalizedSubjectId() == activeSubject }
    }

    Text(
        text = "اول یک درس انتخاب کن:",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(8.dp))
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(subjects) { subjectId ->
            FilterChip(
                selected = subjectId == activeSubject,
                onClick = { onSelectSubject(subjectId) },
                label = { Text(text = SubjectCatalog.getSubjectById(subjectId).title) }
            )
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(lessons, key = { it.id }) { lesson ->
            val status = viewModel.adaptiveTopicStatus(lesson)
            val pickerAccent = resolveDynamicAccent(MaterialTheme.colorScheme.primary)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                MaterialTheme.colorScheme.surfaceVariant,
                                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
                            )
                        ),
                        RoundedCornerShape(16.dp)
                    )
                    .clickable { onStartDiagnostic(lesson) },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(
                                Brush.linearGradient(
                                    listOf(pickerAccent.copy(alpha = 0.85f), MaterialTheme.colorScheme.secondary.copy(alpha = 0.85f))
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
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = lesson.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = AdaptiveUiLabels.topicStatusLabel(status.status),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DiagnosticQuestionView(
    question: DiagnosticEngine.DiagnosticQuestionItem,
    questionNumber: Int,
    selectedOption: Int?,
    onSelectOption: (Int) -> Unit,
    onConfirm: () -> Unit
) {
    val answered = selectedOption != null
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = BidiFormatter.applyPreferredDigits("سؤال $questionNumber"),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = question.questionText,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(12.dp))
        question.options.forEachIndexed { idx, option ->
            val isChosen = selectedOption == idx
            val isCorrect = idx == question.correctIndex
            val containerColor = when {
                answered && isCorrect -> MaterialTheme.colorScheme.primaryContainer
                answered && isChosen -> MaterialTheme.colorScheme.errorContainer
                else -> MaterialTheme.colorScheme.surfaceVariant
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable(enabled = !answered) { onSelectOption(idx) },
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = containerColor)
            ) {
                Text(
                    text = option,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
        if (answered) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (selectedOption == question.correctIndex) "درست پاسخ دادی." else "پاسخ درست، گزینه بالا با رنگ سبز است.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = onConfirm,
            enabled = answered,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(text = "ادامه", maxLines = 1, softWrap = false)
        }
    }
}

@Composable
private fun DiagnosticResultView(
    result: DiagnosticEngine.DiagnosticResult,
    uiState: KonkoorUiState,
    onOpenLesson: (String) -> Unit,
    onPickAnother: () -> Unit
) {
    val recommendedLesson = result.recommendedLessonId
        ?.let { id -> uiState.allLessons.firstOrNull { it.id == id } }
    val resultAccent = resolveDynamicAccent(MaterialTheme.colorScheme.primary)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.85f),
                        MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.55f)
                    )
                ),
                RoundedCornerShape(20.dp)
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "نتیجه تشخیص",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            softWrap = false,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = AdaptiveUiLabels.topicStatusLabel(result.status),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Black,
            maxLines = 1,
            softWrap = false,
            color = resultAccent
        )
        Text(
            text = AdaptiveUiLabels.topicStatusDescription(result.status),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = BidiFormatter.applyPreferredDigits(
                "تعداد سؤال‌ها: ${result.askedCount} — سطح شواهد: ${AdaptiveUiLabels.evidenceLevelLabel(result.evidenceLevel)}"
            ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        if (recommendedLesson != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "درس پیشنهادی برای شروع:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onOpenLesson(recommendedLesson.id) },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = recommendedLesson.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(12.dp)
                )
            }
            Button(
                onClick = { onOpenLesson(recommendedLesson.id) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(
                        Brush.linearGradient(
                            listOf(resultAccent.copy(alpha = 0.90f), MaterialTheme.colorScheme.secondary.copy(alpha = 0.85f))
                        ),
                        RoundedCornerShape(16.dp)
                    ),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Text(text = "باز کردن درس", maxLines = 1, softWrap = false)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedButton(
            onClick = onPickAnother,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(text = "تشخیص مبحث دیگر", maxLines = 1, softWrap = false)
        }
    }
    }
}
