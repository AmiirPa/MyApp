package com.example.ui.calm

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Autorenew
import androidx.compose.material.icons.automirrored.outlined.Assignment
import androidx.compose.material.icons.outlined.Quiz
import androidx.compose.material.icons.outlined.Style
import androidx.compose.material.icons.outlined.TrackChanges
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.curriculum.SubjectCatalog
import com.example.ui.KonkoorUiState
import com.example.ui.KonkoorViewModel
import com.example.ui.screens.buildQuizExamAttempt

@Composable
fun PracticeScreen(viewModel: KonkoorViewModel, uiState: KonkoorUiState, nav: CalmNavigator) {
    val queue by viewModel.adaptiveReviewQueue.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp)
    ) {
        ScreenTitle("تمرین", "کوتاه و منظم، هر روز کمی")
        Spacer(Modifier.height(8.dp))
        CalmRow(
            title = "مرور امروز",
            subtitle = if (queue.isEmpty()) "امروز مروری نمانده" else "${faDigits(queue.size)} مورد منتظر توست",
            icon = Icons.Outlined.Autorenew,
            onClick = { nav.push(Route.Review) }
        )
        CalmRow(
            title = "آزمون کوتاه",
            subtitle = "۱۰ سؤال، با توضیح هر جواب",
            icon = Icons.Outlined.Quiz,
            onClick = { nav.push(Route.PickSubject(forQuiz = true)) }
        )
        CalmRow(
            title = "فلش‌کارت",
            subtitle = "سؤال ببین، جواب را به یاد بیاور",
            icon = Icons.Outlined.Style,
            onClick = { nav.push(Route.PickSubject(forQuiz = false)) }
        )
        SectionLabel("بیشتر")
        CalmRow(
            title = "آزمون‌های کنکور و امتحان نهایی",
            subtitle = "کنکورهای سال‌های قبل و آزمون کامل",
            icon = Icons.AutoMirrored.Outlined.Assignment,
            onClick = { nav.push(Route.Tool(ToolId.EXAM_ARCHIVE)) }
        )
        CalmRow(
            title = "سنجش سطح",
            subtitle = "ببین از کجا باید شروع کنی",
            icon = Icons.Outlined.TrackChanges,
            onClick = { nav.push(Route.Tool(ToolId.DIAGNOSTIC)) }
        )
    }
}

@Composable
fun ReviewScreen(viewModel: KonkoorViewModel, uiState: KonkoorUiState, nav: CalmNavigator) {
    val queue by viewModel.adaptiveReviewQueue.collectAsStateWithLifecycle()
    val lessonById = remember(uiState.allLessons) { uiState.allLessons.associateBy { it.id } }
    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 24.dp)) {
        item { BackHeader("مرور امروز", subtitle = if (queue.isEmpty()) null else "${faDigits(queue.size)} مورد", onBack = { nav.pop() }) }
        if (queue.isEmpty()) {
            item { QuietNote("برای امروز مروری نمانده. فردا دوباره سر بزن.") }
        }
        items(queue, key = { it.itemId }) { card ->
            val lesson = lessonById[card.topicId]
            val title = lesson?.title ?: SubjectCatalog.getSubjectById(card.subjectId).shortTitle
            CalmCard(modifier = Modifier.padding(horizontal = ScreenPadding, vertical = 6.dp)) {
                Text(title, style = MaterialTheme.typography.titleMedium, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Spacer(Modifier.height(4.dp))
                Text(
                    "یادت هست این درس دربارهٔ چه بود؟",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(14.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    PrimaryAction("یادم بود", modifier = Modifier.weight(1f)) { viewModel.recordReviewAttempt(card, true) }
                    SecondaryAction("یادم نبود", modifier = Modifier.weight(1f)) { viewModel.recordReviewAttempt(card, false) }
                }
                if (lesson != null) {
                    androidx.compose.material3.TextButton(onClick = { nav.openLesson(lesson.id) }) {
                        Text("دوباره خواندن درس")
                    }
                }
            }
        }
    }
}

private fun matchesSubject(raw: String, target: String?): Boolean =
    target == null || SubjectCatalog.getSubjectById(raw).id == target

@Composable
fun FlashcardScreen(subjectId: String?, viewModel: KonkoorViewModel, uiState: KonkoorUiState, nav: CalmNavigator) {
    // کارت‌هایی که هنوز در جعبهٔ آخر نیستند، کم‌جعبه‌ترها اول. فهرست یک بار ساخته می‌شود.
    val deck = remember(subjectId, uiState.allFlashcards.size) {
        uiState.allFlashcards
            .filter { matchesSubject(it.subjectId, subjectId) }
            .filter { (uiState.flashcardProgressMap[it.id]?.leitnerBox ?: 1) < 5 }
            .sortedBy { uiState.flashcardProgressMap[it.id]?.leitnerBox ?: 1 }
            .take(20)
    }
    var index by remember(subjectId) { mutableIntStateOf(0) }
    var flipped by remember(subjectId) { mutableStateOf(false) }
    val title = subjectId?.let { SubjectCatalog.getSubjectById(it).shortTitle } ?: "همهٔ دروس"

    Column(modifier = Modifier.fillMaxSize()) {
        BackHeader("فلش‌کارت", subtitle = title, onBack = { nav.pop() })
        if (deck.isEmpty()) {
            QuietNote("برای این درس کارتی نمانده.")
            return@Column
        }
        if (index >= deck.size) {
            Column(Modifier.padding(ScreenPadding)) {
                Text("این دسته تمام شد.", style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(16.dp))
                PrimaryAction("برگرد") { nav.pop() }
            }
            return@Column
        }
        ThinProgress((index + 1f) / deck.size, modifier = Modifier.padding(horizontal = ScreenPadding), height = 4.dp)
        val card = deck[index]
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(ScreenPadding)
        ) {
            Text(
                "${faDigits(index + 1)} از ${faDigits(deck.size)}",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(12.dp))
            CalmCard(onClick = { flipped = !flipped }, contentPadding = PaddingValues(24.dp), modifier = Modifier.heightIn(min = 220.dp)) {
                AnimatedContent(
                    targetState = flipped,
                    transitionSpec = { fadeIn(tween(220)) togetherWith fadeOut(tween(150)) },
                    label = "flip"
                ) { f ->
                    Column {
                        Text(
                            if (f) "جواب" else "سؤال",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.height(12.dp))
                        ReadingText(if (f) card.backAnswer else card.frontQuestion)
                        if (f && card.memoryHook.isNotBlank()) {
                            Spacer(Modifier.height(16.dp))
                            QuietBox(title = "برای یادآوری") { ReadingText(card.memoryHook) }
                        }
                        if (!f) {
                            Spacer(Modifier.height(16.dp))
                            Text(
                                "اول در ذهنت جواب بده، بعد روی کارت بزن.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = ScreenPadding, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (!flipped) {
                PrimaryAction("نشانم بده", modifier = Modifier.fillMaxWidth()) { flipped = true }
            } else {
                SecondaryAction("بلد نبودم", modifier = Modifier.weight(1f)) {
                    viewModel.answerFlashcard(card, false); flipped = false; index++
                }
                PrimaryAction("بلد بودم", modifier = Modifier.weight(1f)) {
                    viewModel.answerFlashcard(card, true); flipped = false; index++
                }
            }
        }
    }
}

@Composable
fun QuizScreen(subjectId: String?, viewModel: KonkoorViewModel, uiState: KonkoorUiState, nav: CalmNavigator) {
    val questions = remember(subjectId, uiState.allQuizQuestions.size) {
        uiState.allQuizQuestions
            .filter { matchesSubject(it.subjectId, subjectId) && it.options.size >= 2 }
            .shuffled()
            .take(10)
    }
    var index by remember(subjectId) { mutableIntStateOf(0) }
    val answers = remember(subjectId) { mutableStateMapOf<String, Int>() }
    var submitted by remember(subjectId) { mutableStateOf(false) }
    val title = subjectId?.let { SubjectCatalog.getSubjectById(it).shortTitle } ?: "همهٔ دروس"

    Column(modifier = Modifier.fillMaxSize()) {
        BackHeader("آزمون کوتاه", subtitle = title, onBack = { nav.pop() })
        if (questions.isEmpty()) {
            QuietNote("برای این درس سؤالی پیدا نشد.")
            return@Column
        }
        if (index >= questions.size) {
            androidx.compose.runtime.LaunchedEffect(questions) {
                if (!submitted) {
                    submitted = true
                    val attempt = buildQuizExamAttempt(
                        questions = questions,
                        answeredOptions = answers.toMap(),
                        subjectId = subjectId ?: "mixed",
                        title = "آزمون کوتاه $title"
                    )
                    viewModel.submitExamAttempt(attempt)
                }
            }
            val correct = questions.count { answers[it.id] == it.correctOptionIndex }
            Column(Modifier.padding(ScreenPadding), horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(Modifier.height(24.dp))
                ProgressRing(correct.toFloat() / questions.size, size = 120.dp, stroke = 10.dp, label = "${faDigits(correct)} از ${faDigits(questions.size)}")
                Spacer(Modifier.height(20.dp))
                Text(
                    if (correct * 2 >= questions.size) "خوب بود! همین‌طور ادامه بده." else "اشکالی ندارد؛ با مرور درس‌ها بهتر می‌شود.",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(24.dp))
                PrimaryAction("تمام", modifier = Modifier.fillMaxWidth()) { nav.pop() }
            }
            return@Column
        }
        ThinProgress((index + 1f) / questions.size, modifier = Modifier.padding(horizontal = ScreenPadding), height = 4.dp)
        val q = questions[index]
        val chosen = answers[q.id] ?: -1
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(ScreenPadding)
        ) {
            Text(
                "سؤال ${faDigits(index + 1)} از ${faDigits(questions.size)}",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(12.dp))
            ReadingText(q.questionText)
            Spacer(Modifier.height(18.dp))
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                q.options.forEachIndexed { i, opt ->
                    OptionCard(
                        text = opt,
                        state = when {
                            chosen == -1 -> OptionState.IDLE
                            i == q.correctOptionIndex -> OptionState.CORRECT
                            i == chosen -> OptionState.WRONG
                            else -> OptionState.DIM
                        },
                        onClick = { if (chosen == -1) answers[q.id] = i }
                    )
                }
            }
            if (chosen != -1 && q.zeroLevelSolution.isNotBlank()) {
                Spacer(Modifier.height(18.dp))
                QuietBox(title = "توضیح ساده") { ReadingText(q.zeroLevelSolution) }
                if (q.shortcutTechnique.isNotBlank()) {
                    Spacer(Modifier.height(12.dp))
                    QuietBox(title = "راه میان‌بر") { ReadingText(q.shortcutTechnique) }
                }
            }
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        Row(Modifier.fillMaxWidth().padding(horizontal = ScreenPadding, vertical = 12.dp)) {
            PrimaryAction(
                if (chosen == -1) "رد شو" else if (index == questions.lastIndex) "دیدن نتیجه" else "سؤال بعدی",
                modifier = Modifier.fillMaxWidth()
            ) { index++ }
        }
    }
}
