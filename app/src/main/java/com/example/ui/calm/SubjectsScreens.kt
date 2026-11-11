package com.example.ui.calm

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.data.curriculum.SubjectCatalog
import com.example.data.curriculum.ZeroLesson
import com.example.ui.KonkoorUiState
import com.example.ui.screens.getSubjectIcon

/** فهرست آرام دروس، گروه‌بندی‌شده به عمومی / اختصاصی / پایه. */
@Composable
fun SubjectsScreen(uiState: KonkoorUiState, nav: CalmNavigator) {
    val summaries = remember(uiState.subjects, uiState.allLessons, uiState.lessonProgressMap) {
        buildSubjectSummaries(uiState.subjects, uiState.allLessons, uiState.lessonProgressMap)
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item { ScreenTitle("دروس", "هر درس از پایه شروع می‌شود") }
        SubjectGroup.entries.forEach { group ->
            val inGroup = summaries.filter { it.group == group }
            if (inGroup.isNotEmpty()) {
                item(key = "g_${group.name}") { SectionLabel(group.title) }
                items(inGroup, key = { it.meta.id }) { s ->
                    CalmRow(
                        title = s.meta.shortTitle,
                        subtitle = "${faDigits(s.done)} از ${faDigits(s.total)} درس",
                        icon = getSubjectIcon(s.meta.id),
                        progress = s.fraction,
                        onClick = { nav.push(Route.Subject(s.meta.id)) }
                    )
                }
            }
        }
        if (summaries.isEmpty()) {
            item { QuietNote("در حال آماده کردن درس‌ها…") }
        }
    }
}

/** فصل‌های یک درس. اگر درس فقط یک فصل داشته باشد، مستقیم درس‌ها نشان داده می‌شوند. */
@Composable
fun SubjectDetailScreen(subjectId: String, uiState: KonkoorUiState, nav: CalmNavigator) {
    val meta = remember(subjectId) { SubjectCatalog.getSubjectById(subjectId) }
    val summary = remember(subjectId, uiState.allLessons, uiState.lessonProgressMap) {
        buildSubjectSummaries(listOf(meta), uiState.allLessons, uiState.lessonProgressMap).firstOrNull()
    }
    val chapters = remember(summary?.lessons) { buildChapters(summary?.lessons.orEmpty()) }

    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 24.dp)) {
        item {
            BackHeader(
                title = meta.shortTitle,
                subtitle = summary?.let { "${faDigits(it.done)} از ${faDigits(it.total)} درس تمام شده" },
                onBack = { nav.pop() }
            )
        }
        item {
            ThinProgress(summary?.fraction ?: 0f, modifier = Modifier.padding(horizontal = ScreenPadding, vertical = 8.dp))
        }
        if (meta.zeroReassurance.isNotBlank()) {
            item { QuietNote(meta.zeroReassurance, Modifier.padding(top = 4.dp)) }
        }
        if (chapters.size <= 1) {
            val lessons = chapters.firstOrNull()?.lessons.orEmpty()
            item { SectionLabel("درس‌ها به ترتیب") }
            lessonItems(lessons, uiState, nav)
        } else {
            item { SectionLabel("فصل‌ها به ترتیب") }
            items(chapters, key = { it.key }) { ch ->
                val done = ch.lessons.count { uiState.lessonProgressMap[it.id]?.isCompleted == true }
                CalmRow(
                    title = ch.title,
                    subtitle = ch.caption,
                    trailingText = "${faDigits(done)}/${faDigits(ch.lessons.size)}",
                    progress = done.toFloat() / ch.lessons.size.coerceAtLeast(1),
                    onClick = { nav.push(Route.Chapter(subjectId, ch.key)) }
                )
            }
        }
        item { SectionLabel("ابزار این درس") }
        item {
            CalmRow(
                title = "واژه‌نامهٔ ساده",
                subtitle = "معنی اصطلاح‌های سخت این درس به زبان ساده",
                icon = Icons.Outlined.Translate,
                onClick = { nav.push(Route.Tool(ToolId.JARGON, subjectId)) }
            )
        }
    }
}

@Composable
fun ChapterScreen(subjectId: String, chapterKey: String, uiState: KonkoorUiState, nav: CalmNavigator) {
    val meta = remember(subjectId) { SubjectCatalog.getSubjectById(subjectId) }
    val chapter = remember(subjectId, chapterKey, uiState.allLessons) {
        val summary = buildSubjectSummaries(listOf(meta), uiState.allLessons, emptyMap()).firstOrNull()
        buildChapters(summary?.lessons.orEmpty()).firstOrNull { it.key == chapterKey }
    }
    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 24.dp)) {
        item { BackHeader(title = chapter?.title ?: meta.shortTitle, subtitle = "${meta.shortTitle} · ${chapter?.caption.orEmpty()}", onBack = { nav.pop() }) }
        lessonItems(chapter?.lessons.orEmpty(), uiState, nav)
    }
}

private fun androidx.compose.foundation.lazy.LazyListScope.lessonItems(
    lessons: List<ZeroLesson>,
    uiState: KonkoorUiState,
    nav: CalmNavigator
) {
    val nextId = lessons.firstOrNull { uiState.lessonProgressMap[it.id]?.isCompleted != true }?.id
    items(lessons.size, key = { lessons[it].id }) { i ->
        val l = lessons[i]
        val done = uiState.lessonProgressMap[l.id]?.isCompleted == true
        CalmRow(
            title = l.title,
            subtitle = if (l.id == nextId) "درس بعدی تو" else "${faDigits(l.estimatedMinutes.coerceAtLeast(5))} دقیقه",
            leading = { LessonNumber(i + 1, done, l.id == nextId) },
            onClick = { nav.openLesson(l.id) }
        )
    }
}

@Composable
private fun LessonNumber(n: Int, done: Boolean, isNext: Boolean) {
    val c = MaterialTheme.colorScheme
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(
                when {
                    done -> c.primary
                    isNext -> c.primaryContainer
                    else -> c.surfaceVariant
                }
            )
            .then(if (isNext) Modifier.border(1.5.dp, c.primary, CircleShape) else Modifier),
        contentAlignment = Alignment.Center
    ) {
        if (done) {
            Icon(Icons.Filled.Check, contentDescription = "تمام شده", tint = c.onPrimary, modifier = Modifier.size(18.dp))
        } else {
            Text(faDigits(n), style = MaterialTheme.typography.labelLarge, color = if (isNext) c.primary else c.onSurfaceVariant)
        }
    }
}

@Composable
fun PickSubjectScreen(forQuiz: Boolean, uiState: KonkoorUiState, nav: CalmNavigator) {
    val summaries = remember(uiState.subjects, uiState.allLessons) {
        buildSubjectSummaries(uiState.subjects, uiState.allLessons, uiState.lessonProgressMap)
    }
    val available = remember(summaries, uiState.allQuizQuestions, uiState.allFlashcards) {
        val raw = if (forQuiz) uiState.allQuizQuestions.map { it.subjectId }.toSet()
        else uiState.allFlashcards.map { it.subjectId }.toSet()
        val ids = raw.map { SubjectCatalog.getSubjectById(it).id }.toSet()
        summaries.filter { it.meta.id in ids }
    }
    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 24.dp)) {
        item {
            BackHeader(
                title = if (forQuiz) "آزمون کوتاه" else "فلش‌کارت",
                subtitle = "یک درس انتخاب کن",
                onBack = { nav.pop() }
            )
        }
        item {
            CalmRow(
                title = "همهٔ دروس با هم",
                subtitle = "ترکیبی از همه",
                onClick = { nav.push(if (forQuiz) Route.Quiz(null) else Route.Flashcards(null)) }
            )
        }
        item { Spacer(Modifier.height(8.dp)) }
        items(available, key = { it.meta.id }) { s ->
            CalmRow(
                title = s.meta.shortTitle,
                icon = getSubjectIcon(s.meta.id),
                onClick = { nav.push(if (forQuiz) Route.Quiz(s.meta.id) else Route.Flashcards(s.meta.id)) }
            )
        }
    }
}
