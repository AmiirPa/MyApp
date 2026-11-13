package com.example.ui.calm

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.curriculum.FlashcardAndQuizBank
import com.example.data.curriculum.SubjectCatalog
import com.example.data.curriculum.WorkedExample
import com.example.data.curriculum.ZeroLesson
import com.example.data.curriculum.ZeroStep
import com.example.data.curriculum.getPrerequisiteLinks
import com.example.data.curriculum.normalizedSubjectId
import com.example.ui.KonkoorUiState
import com.example.ui.KonkoorViewModel
import com.example.ui.components.BidiFormatter
import com.example.ui.components.LessonInteractiveVisualCard
import com.example.ui.components.SmartDiagramAndFormulaBlock
import com.example.ui.theme.LocalAppThemeController

/** هر بخش درس فقط یک ایده است. */
private sealed interface LessonSection {
    data object Intro : LessonSection
    data class Step(val index: Int, val total: Int, val step: ZeroStep, val withDiagram: Boolean) : LessonSection
    data class Example(val index: Int, val example: WorkedExample) : LessonSection
    data class Summary(val points: List<String>, val traps: List<String>) : LessonSection
    data object Check : LessonSection
}

private fun buildSections(lesson: ZeroLesson): List<LessonSection> = buildList {
    add(LessonSection.Intro)
    val steps = lesson.steps.filter { it.title.isNotBlank() || it.simpleExplanation.isNotBlank() }
    steps.forEachIndexed { i, s -> add(LessonSection.Step(i, steps.size, s, withDiagram = i == 0)) }
    lesson.workedExamples.filter { it.question.isNotBlank() }.take(3).forEachIndexed { i, e -> add(LessonSection.Example(i, e)) }
    val points = (lesson.keyPoints + lesson.goldenFormulas).map { it.trim() }.filter { it.isNotBlank() }.distinct().take(8)
    val traps = lesson.examTraps.map { it.trim() }.filter { it.isNotBlank() }.distinct().take(4)
    if (points.isNotEmpty() || traps.isNotEmpty()) add(LessonSection.Summary(points, traps))
    if (lesson.miniCheck.question.isNotBlank() && lesson.miniCheck.options.size >= 2) add(LessonSection.Check)
}

@Composable
fun LessonReaderScreen(lessonId: String, viewModel: KonkoorViewModel, uiState: KonkoorUiState, nav: CalmNavigator) {
    val raw = remember(lessonId, uiState.allLessons) { uiState.allLessons.firstOrNull { it.id == lessonId } }
    if (raw == null) {
        Column(Modifier.fillMaxSize()) {
            BackHeader("درس", onBack = { nav.pop() })
            QuietNote("این درس پیدا نشد.")
        }
        return
    }
    val lesson = remember(raw.id) { runCatching { FlashcardAndQuizBank.getEnrichedLesson(raw) }.getOrDefault(raw) }
    val sections = remember(lesson.id) { buildSections(lesson) }
    val subject = remember(lesson.id) { SubjectCatalog.getSubjectById(lesson.normalizedSubjectId()) }
    val progress = uiState.lessonProgressMap[lesson.id]
    val accent = MaterialTheme.colorScheme.primary

    var index by remember(lesson.id) { mutableIntStateOf(0) }
    var finished by remember(lesson.id) { mutableStateOf(false) }
    var chosenOption by remember(lesson.id) { mutableIntStateOf(-1) }

    val prefs = LocalCalmPrefs.current
    val prerequisiteLinks = remember(lesson.id, uiState.allLessons) {
        runCatching { lesson.getPrerequisiteLinks(uiState.allLessons) }.getOrDefault(emptyList())
    }
    val lessonById = remember(uiState.allLessons) { uiState.allLessons.associateBy { it.id } }
    val path = remember(prefs.startStage, uiState.allLessons, uiState.lessonProgressMap) {
        buildPathState(prefs.startStage, uiState.allLessons, lessonById, uiState.lessonProgressMap)
    }
    val inPath = remember(path.steps, lesson.id) { path.steps.any { s -> s.lessons.any { it.id == lesson.id } } }
    val siblings = remember(lesson.id, uiState.allLessons) { orderedSiblings(raw, uiState.allLessons) }
    val siblingNext = remember(siblings, lesson.id) {
        val i = siblings.indexOfFirst { it.id == lesson.id }
        if (i >= 0) siblings.getOrNull(i + 1) else null
    }
    val nextLesson = if (inPath) path.nextLesson?.takeIf { it.id != lesson.id } ?: siblingNext else siblingNext

    Column(modifier = Modifier.fillMaxSize()) {
        // ── سربرگ آرام ──
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { nav.pop() }) {
                Icon(Icons.Outlined.Close, contentDescription = "بستن درس", tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Text(
                subject.shortTitle,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1
            )
            val bookmarked = progress?.isBookmarked == true
            IconButton(onClick = { viewModel.toggleBookmark(lesson) }) {
                Icon(
                    if (bookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                    contentDescription = "نشان کردن",
                    tint = if (bookmarked) accent else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        ThinProgress(
            fraction = if (finished) 1f else (index + 1f) / sections.size,
            modifier = Modifier.padding(horizontal = ScreenPadding),
            height = 4.dp
        )

        // ── متن بخش ──
        Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
            val reduceMotion = LocalAppThemeController.current.reduceMotionEnabled
            AnimatedContent(
                targetState = if (finished) -1 else index,
                transitionSpec = {
                    if (reduceMotion) {
                        fadeIn(tween(0)) togetherWith fadeOut(tween(0))
                    } else {
                        val forward = targetState == -1 || targetState > initialState
                        if (forward) {
                            (slideInHorizontally(tween(240)) { -it / 8 } + fadeIn(tween(240))) togetherWith fadeOut(tween(150))
                        } else {
                            (slideInHorizontally(tween(240)) { it / 8 } + fadeIn(tween(240))) togetherWith fadeOut(tween(150))
                        }
                    }
                },
                label = "lessonSection"
            ) { i ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .widthIn(max = 720.dp)
                        .padding(horizontal = 24.dp, vertical = 20.dp)
                ) {
                    if (i == -1) {
                        FinishedSection(nextLesson, onNext = { n -> nav.openLesson(n.id) }, onBack = { nav.pop() })
                    } else {
                        when (val s = sections[i]) {
                            LessonSection.Intro -> IntroSection(
                                lesson,
                                sections.size,
                                prerequisiteLinks = prerequisiteLinks,
                                onOpenLesson = { id -> nav.openLesson(id) }
                            )
                            is LessonSection.Step -> StepSection(lesson, s, accent)
                            is LessonSection.Example -> ExampleSection(s, accent)
                            is LessonSection.Summary -> SummarySection(s)
                            LessonSection.Check -> CheckSection(lesson, chosenOption) { opt ->
                                if (chosenOption == -1) {
                                    chosenOption = opt
                                    viewModel.recordMiniCheckAttempt(lesson, opt == lesson.miniCheck.correctIndex)
                                }
                            }
                        }
                    }
                }
            }
        }

        // ── قبلی / متوجه شدم ──
        if (!finished) {
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = ScreenPadding, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SecondaryAction("قبلی", modifier = Modifier.weight(1f), enabled = index > 0) { index-- }
                val isLast = index == sections.lastIndex
                PrimaryAction(
                    if (isLast) "متوجه شدم، تمام" else "متوجه شدم",
                    modifier = Modifier.weight(2f)
                ) {
                    if (isLast) {
                        viewModel.saveLessonState(
                            lesson,
                            true,
                            progress?.isBookmarked ?: false,
                            progress?.userNote ?: "",
                            progress?.masteryPercent ?: 0
                        )
                        finished = true
                    } else {
                        index++
                    }
                }
            }
        }
    }
}

@Composable
private fun readingStyle(): androidx.compose.ui.text.TextStyle {
    val scale = LocalCalmPrefs.current.readingScale
    val controller = LocalAppThemeController.current
    val base = MaterialTheme.typography.bodyLarge
    return base.copy(
        fontSize = (18f * controller.fontScale * scale).sp,
        lineHeight = (34f * controller.fontScale * scale * controller.lineSpacingScale).sp,
        textDirection = TextDirection.Content
    )
}

private const val BoxChars = "│┌┐└┘├┤┬┴┼═║╔╗╚╝─|"

/** متن درس: پاراگراف‌های جدا، فونت بزرگ؛ نمودارهای متنی با همان نمایشگر قبلی. */
@Composable
fun ReadingText(raw: String, accent: Color = MaterialTheme.colorScheme.primary) {
    val paragraphs = remember(raw) { raw.trim().split(Regex("\\n\\s*\\n")).map { it.trim() }.filter { it.isNotEmpty() } }
    val style = readingStyle()
    val onSurface = MaterialTheme.colorScheme.onSurface
    val controller = LocalAppThemeController.current
    val paragraphSpacing = controller.textVerticalSpacingDp.dp
    val textAlign = when (controller.textAlignmentPreset) {
        com.example.ui.theme.TextAlignmentPreset.RIGHT -> TextAlign.Right
        com.example.ui.theme.TextAlignmentPreset.CENTER -> TextAlign.Center
        com.example.ui.theme.TextAlignmentPreset.LEFT -> TextAlign.Left
        else -> TextAlign.Start
    }
    Column(verticalArrangement = Arrangement.spacedBy(paragraphSpacing)) {
        paragraphs.forEach { p ->
            if (p.any { it in BoxChars }) {
                SmartDiagramAndFormulaBlock(rawText = p, accentColor = accent)
            } else {
                val formatted = remember(p) {
                    runCatching { BidiFormatter.formatMixedPersianText(BidiFormatter.convertCaretsToSuperscripts(p)) }.getOrDefault(p)
                }
                Text(
                    text = emphasize(formatted, accent),
                    style = style,
                    color = onSurface,
                    textAlign = textAlign
                )
            }
        }
    }
}

/** **پررنگ** و «کلیدواژه» را آرام برجسته می‌کند؛ بقیه دست‌نخورده.
 * نکته‌های **پررنگ** همان «نکته کلیدی»‌اند: هایلایت و خط زیر آن‌ها از تنظیمات
 * می‌آید (رنگ و شدت هایلایت، روشن/خاموش بودن خط زیر). */
@Composable
private fun emphasize(text: String, accent: Color): AnnotatedString {
    val controller = LocalAppThemeController.current
    val isDark = androidx.compose.foundation.isSystemInDarkTheme()
    val highlightBg = if (controller.isHighlighterEnabled) {
        controller.selectedHighlighterPalette.resolveBackground(isDark, controller.highlightIntensity)
    } else null
    val underline = controller.isUnderlineKeyPointsEnabled
    return buildAnnotatedString {
        var i = 0
        while (i < text.length) {
            when {
                text.startsWith("**", i) -> {
                    val end = text.indexOf("**", i + 2)
                    if (end > i + 2) {
                        withStyle(
                            SpanStyle(
                                fontWeight = FontWeight.Bold,
                                background = highlightBg ?: Color.Transparent,
                                textDecoration = if (underline) TextDecoration.Underline else null
                            )
                        ) { append(text.substring(i + 2, end)) }
                        i = end + 2
                    } else { append("**"); i += 2 }
                }
                text[i] == '«' -> {
                    val end = text.indexOf('»', i + 1)
                    if (end > i && end - i < 60) {
                        withStyle(SpanStyle(color = accent, fontWeight = FontWeight.Medium)) { append(text.substring(i, end + 1)) }
                        i = end + 1
                    } else { append(text[i]); i++ }
                }
                else -> { append(text[i]); i++ }
            }
        }
    }
}

@Composable
private fun SectionKicker(text: String) {
    Text(text, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
    Spacer(Modifier.height(8.dp))
}

@Composable
private fun SectionHeading(text: String) {
    val formatted = remember(text) { runCatching { BidiFormatter.formatMixedPersianText(text) }.getOrDefault(text) }
    Text(formatted, style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onSurface)
    Spacer(Modifier.height(18.dp))
}

@Composable
private fun IntroSection(
    lesson: ZeroLesson,
    sectionCount: Int,
    prerequisiteLinks: List<com.example.data.curriculum.LessonPrerequisiteLink> = emptyList(),
    onOpenLesson: (String) -> Unit = {}
) {
    val controller = LocalAppThemeController.current
    SectionKicker("درس تازه")
    SectionHeading(lesson.title)
    if (lesson.subtitle.isNotBlank()) ReadingText(lesson.subtitle)
    if (controller.showPrerequisitesBeforeLesson && prerequisiteLinks.isNotEmpty()) {
        Spacer(Modifier.height(22.dp))
        QuietBox(title = "پیش‌نیاز این درس") {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                prerequisiteLinks.take(4).forEach { link ->
                    Text(
                        text = "• ${link.prerequisiteTitle}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onOpenLesson(link.prerequisiteLessonId) }
                            .padding(vertical = 2.dp)
                    )
                }
            }
        }
    }
    if (lesson.whyStudentsStruggle.isNotBlank() && lesson.whyStudentsStruggle != lesson.subtitle) {
        Spacer(Modifier.height(22.dp))
        QuietBox(title = "چرا این درس برای خیلی‌ها سخت است؟") { ReadingText(lesson.whyStudentsStruggle) }
    }
    Spacer(Modifier.height(22.dp))
    Text(
        "${faDigits(sectionCount)} بخش کوتاه · حدود ${faDigits(lesson.estimatedMinutes.coerceAtLeast(5))} دقیقه",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Spacer(Modifier.height(6.dp))
    Text(
        "هر بخش را که فهمیدی، «متوجه شدم» را بزن.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
private fun StepSection(lesson: ZeroLesson, s: LessonSection.Step, accent: Color) {
    val controller = LocalAppThemeController.current
    SectionKicker("بخش ${faDigits(s.index + 1)} از ${faDigits(s.total)}")
    if (s.step.title.isNotBlank()) SectionHeading(s.step.title)
    ReadingText(s.step.simpleExplanation, accent)
    if (s.step.visualDiagramText.isNotBlank()) {
        Spacer(Modifier.height(18.dp))
        SmartDiagramAndFormulaBlock(rawText = s.step.visualDiagramText, accentColor = accent)
    }
    if (controller.showMnemonicsAndMemoryTricks && s.step.memoryTrick.isNotBlank()) {
        Spacer(Modifier.height(18.dp))
        QuietBox(title = "برای اینکه یادت بماند") { ReadingText(s.step.memoryTrick, accent) }
    }
    if (s.withDiagram) {
        Spacer(Modifier.height(22.dp))
        Text("نمودار متحرک", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(8.dp))
        LessonInteractiveVisualCard(lesson = lesson, subjectColor = accent)
    }
}

@Composable
private fun ExampleSection(s: LessonSection.Example, accent: Color) {
    var show by remember(s.index, s.example.question) { mutableStateOf(false) }
    val reduceMotion = LocalAppThemeController.current.reduceMotionEnabled
    SectionKicker("مثال ${faDigits(s.index + 1)}")
    ReadingText(s.example.question, accent)
    Spacer(Modifier.height(20.dp))
    if (!show) {
        SecondaryAction("دیدن راه‌حل قدم‌به‌قدم", modifier = Modifier.fillMaxWidth()) { show = true }
    }
    AnimatedVisibility(
        visible = show,
        enter = if (reduceMotion) fadeIn(tween(0)) else fadeIn(tween(250)) + expandVertically(tween(250))
    ) {
        Column {
            QuietBox(title = "راه‌حل") {
                if (s.example.stepByStepSolution.any { it in BoxChars }) {
                    SmartDiagramAndFormulaBlock(rawText = s.example.stepByStepSolution, accentColor = accent)
                } else {
                    ReadingText(s.example.stepByStepSolution, accent)
                }
            }
            if (s.example.commonTrapAlert.isNotBlank()) {
                Spacer(Modifier.height(14.dp))
                QuietBox(title = "حواست باشد") { ReadingText(s.example.commonTrapAlert, accent) }
            }
        }
    }
}

@Composable
private fun SummarySection(s: LessonSection.Summary) {
    SectionKicker("جمع‌بندی")
    SectionHeading("نکته‌های اصلی این درس")
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        s.points.forEach { p -> QuietBox { ReadingText(p) } }
    }
    if (s.traps.isNotEmpty()) {
        Spacer(Modifier.height(22.dp))
        Text("دام‌های تستی", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(10.dp))
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            s.traps.forEach { p -> QuietBox { ReadingText(p) } }
        }
    }
}

@Composable
private fun CheckSection(lesson: ZeroLesson, chosen: Int, onChoose: (Int) -> Unit) {
    val q = lesson.miniCheck
    SectionKicker("یک سؤال کوتاه")
    ReadingText(q.question)
    Spacer(Modifier.height(18.dp))
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        q.options.forEachIndexed { i, opt ->
            OptionCard(
                text = opt,
                state = when {
                    chosen == -1 -> OptionState.IDLE
                    i == q.correctIndex -> OptionState.CORRECT
                    i == chosen -> OptionState.WRONG
                    else -> OptionState.DIM
                },
                onClick = { onChoose(i) }
            )
        }
    }
    AnimatedVisibility(visible = chosen != -1, enter = fadeIn(tween(250)) + expandVertically(tween(250))) {
        Column {
            Spacer(Modifier.height(18.dp))
            Text(
                if (chosen == q.correctIndex) "درست است." else "اشکالی ندارد؛ جواب درست مشخص شد.",
                style = MaterialTheme.typography.titleMedium,
                color = if (chosen == q.correctIndex) CalmSuccess else MaterialTheme.colorScheme.onSurface
            )
            if (q.explanation.isNotBlank()) {
                Spacer(Modifier.height(10.dp))
                QuietBox(title = "توضیح") { ReadingText(q.explanation) }
            }
        }
    }
}

enum class OptionState { IDLE, CORRECT, WRONG, DIM }

@Composable
fun OptionCard(text: String, state: OptionState, onClick: () -> Unit) {
    val c = MaterialTheme.colorScheme
    val border = when (state) {
        OptionState.CORRECT -> BorderStroke(2.dp, CalmSuccess)
        OptionState.WRONG -> BorderStroke(2.dp, c.error)
        else -> BorderStroke(1.dp, c.outlineVariant)
    }
    val formatted = remember(text) { runCatching { BidiFormatter.formatMixedPersianText(text) }.getOrDefault(text) }
    Surface(
        onClick = onClick,
        enabled = state == OptionState.IDLE,
        shape = MaterialTheme.shapes.medium,
        color = c.surface,
        border = border,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            formatted,
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 16.dp),
            style = MaterialTheme.typography.bodyLarge.copy(textDirection = TextDirection.Content),
            color = if (state == OptionState.DIM) c.onSurfaceVariant else c.onSurface
        )
    }
}

@Composable
fun QuietBox(title: String? = null, content: @Composable () -> Unit) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            if (title != null) {
                Text(title, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.height(8.dp))
            }
            content()
        }
    }
}

@Composable
private fun FinishedSection(next: ZeroLesson?, onNext: (ZeroLesson) -> Unit, onBack: () -> Unit) {
    Spacer(Modifier.height(32.dp))
    Text("آفرین!", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)
    Spacer(Modifier.height(8.dp))
    Text(
        "این درس تمام شد و ذخیره شد. مرورش به‌موقع در «امروز» می‌آید.",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Spacer(Modifier.height(28.dp))
    if (next != null) {
        CalmCard(tonal = true, onClick = { onNext(next) }, contentPadding = PaddingValues(20.dp)) {
            Text("درس بعدی", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(6.dp))
            Text(next.title, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onPrimaryContainer)
            Spacer(Modifier.height(16.dp))
            PrimaryAction("برو به درس بعدی") { onNext(next) }
        }
        Spacer(Modifier.height(12.dp))
    }
    TextButton(onClick = onBack) { Text("فعلاً کافی است، برگرد") }
}
