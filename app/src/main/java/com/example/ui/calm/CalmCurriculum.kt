package com.example.ui.calm

import com.example.data.curriculum.Grade
import com.example.data.curriculum.LearningPath1406
import com.example.data.curriculum.LearningPathElementary1406
import com.example.data.curriculum.LearningPathMiddle1406
import com.example.data.curriculum.LearningPathStatus
import com.example.data.curriculum.LearningPathStep
import com.example.data.curriculum.SubjectMeta
import com.example.data.curriculum.ZeroLesson
import com.example.data.curriculum.normalizedSubjectId
import com.example.data.firebase.CloudLessonProgress

/*
 * کمک‌کننده‌های فقط-نمایشی روی دادهٔ موجود. هیچ دادهٔ درسی اینجا ساخته یا تغییر داده نمی‌شود؛
 * فقط درس‌های موجود گروه‌بندی و مرتب می‌شوند.
 */

/** گروه‌های صفحهٔ «دروس». */
enum class SubjectGroup(val title: String) {
    GENERAL("عمومی"),
    SPECIALIZED("اختصاصی"),
    FOUNDATION("پایه و مهارت‌ها")
}

/** دروس عمومی کنکور انسانی. */
private val generalIds = listOf("farsi", "dini", "english")

/** دروس اختصاصی کنکور انسانی. */
private val specializedIds = listOf(
    "math", "economics", "fonoon", "arabic", "history", "geography",
    "sociology", "philosophy", "psychology"
)

fun subjectGroupOf(subjectId: String): SubjectGroup = when (subjectId) {
    in generalIds -> SubjectGroup.GENERAL
    in specializedIds -> SubjectGroup.SPECIALIZED
    else -> SubjectGroup.FOUNDATION
}

private fun groupOrder(subjectId: String): Int {
    val g = generalIds.indexOf(subjectId)
    if (g >= 0) return g
    val s = specializedIds.indexOf(subjectId)
    if (s >= 0) return s
    return 100
}

/** ترتیب آموزشی واحد: پایه ← فصل ← بخش ← نوع محتوا ← ترتیب اصلی. */
val lessonTeachingOrder: Comparator<ZeroLesson> =
    compareBy<ZeroLesson>({ it.grade.number }, { it.chapterNumber }, { it.sectionNumber }, { it.sectionType.ordinal }, { it.orderIndex })

data class SubjectSummary(
    val meta: SubjectMeta,
    val group: SubjectGroup,
    val lessons: List<ZeroLesson>,
    val done: Int
) {
    val total: Int get() = lessons.size
    val fraction: Float get() = if (total == 0) 0f else done.toFloat() / total
}

fun buildSubjectSummaries(
    subjects: List<SubjectMeta>,
    allLessons: List<ZeroLesson>,
    progress: Map<String, CloudLessonProgress>
): List<SubjectSummary> {
    val bySubject = allLessons.groupBy { it.normalizedSubjectId() }
    return subjects.mapNotNull { meta ->
        val lessons = bySubject[meta.id].orEmpty().sortedWith(lessonTeachingOrder)
        if (lessons.isEmpty()) return@mapNotNull null
        SubjectSummary(
            meta = meta,
            group = subjectGroupOf(meta.id),
            lessons = lessons,
            done = lessons.count { progress[it.id]?.isCompleted == true }
        )
    }.sortedWith(compareBy({ it.group.ordinal }, { groupOrder(it.meta.id) }))
}

data class ChapterGroup(
    val key: String,
    val title: String,
    val caption: String,
    val lessons: List<ZeroLesson>
)

private fun gradeCaption(grade: Grade): String =
    if (grade == Grade.SUB_ZERO) "پایه‌ها، از صفر" else "پایهٔ ${grade.label}"

/** فصل‌های یک درس به ترتیب آموزشی. */
fun buildChapters(subjectLessons: List<ZeroLesson>): List<ChapterGroup> {
    val ordered = subjectLessons.sortedWith(lessonTeachingOrder)
    val groups = LinkedHashMap<String, MutableList<ZeroLesson>>()
    ordered.forEach { l ->
        val key = "${l.grade.name}|${l.chapterNumber}|${l.chapterTitle}"
        groups.getOrPut(key) { mutableListOf() }.add(l)
    }
    return groups.map { (key, lessons) ->
        val first = lessons.first()
        val title = when {
            first.chapterTitle.isNotBlank() -> first.chapterTitle
            first.chapterNumber > 0 -> "فصل ${first.chapterNumber}"
            first.bookTitle.isNotBlank() -> first.bookTitle
            else -> "درس‌های پایه"
        }
        val caption = buildString {
            append(gradeCaption(first.grade))
            if (first.chapterNumber > 0 && first.chapterTitle.isNotBlank()) append(" · فصل ${first.chapterNumber}")
        }
        ChapterGroup(key = key, title = title, caption = caption, lessons = lessons)
    }
}

/** یک قدم از مسیر «صفر تا صد» همراه با درس‌هایی که واقعاً در برنامه هست. */
data class PathStepView(
    val step: LearningPathStep,
    val stageLabel: String,
    val lessons: List<ZeroLesson>
)

data class PathState(
    val steps: List<PathStepView>,
    val nextLesson: ZeroLesson?,
    val nextStep: PathStepView?,
    val doneLessons: Int,
    val totalLessons: Int
) {
    val fraction: Float get() = if (totalLessons == 0) 0f else doneLessons.toFloat() / totalLessons
}

/**
 * مسیر کامل از صفر: دبستان ← متوسطه اول ← متوسطه دوم/کنکور (از همان داده‌های مسیر موجود)،
 * از نقطهٔ شروعی که کاربر انتخاب کرده. اگر هیچ درس مسیری وجود نداشت، به ترتیب آموزشی
 * همهٔ درس‌ها برمی‌گردد تا کارت «درس بعدی» هرگز خالی نماند.
 */
fun buildPathState(
    start: PathStage,
    allLessons: List<ZeroLesson>,
    lessonById: Map<String, ZeroLesson>,
    progress: Map<String, CloudLessonProgress>
): PathState {
    val stages = buildList {
        if (start == PathStage.ELEMENTARY) add("دبستان" to LearningPathElementary1406.steps)
        if (start != PathStage.HIGH) add("متوسطه اول" to LearningPathMiddle1406.steps)
        add("متوسطه دوم و کنکور" to LearningPath1406.steps)
    }
    val seen = HashSet<String>()
    val views = stages.flatMap { (label, steps) ->
        steps.filter { it.status == LearningPathStatus.READY && it.lessonIds.isNotEmpty() }
            .mapNotNull { step ->
                val lessons = step.lessonIds.mapNotNull { id -> lessonById[id]?.takeIf { seen.add(id) } }
                if (lessons.isEmpty()) null else PathStepView(step, label, lessons)
            }
    }
    if (views.isEmpty()) {
        val ordered = allLessons.sortedWith(lessonTeachingOrder)
        val next = ordered.firstOrNull { progress[it.id]?.isCompleted != true }
        return PathState(emptyList(), next, null, ordered.count { progress[it.id]?.isCompleted == true }, ordered.size)
    }
    var nextLesson: ZeroLesson? = null
    var nextStep: PathStepView? = null
    var done = 0
    var total = 0
    views.forEach { v ->
        v.lessons.forEach { l ->
            total++
            if (progress[l.id]?.isCompleted == true) done++
            else if (nextLesson == null) { nextLesson = l; nextStep = v }
        }
    }
    return PathState(views, nextLesson, nextStep, done, total)
}

/** ترتیب درس‌های یک درس (برای قبلی/بعدی داخل صفحهٔ درس). */
fun orderedSiblings(lesson: ZeroLesson, allLessons: List<ZeroLesson>): List<ZeroLesson> {
    val sid = lesson.normalizedSubjectId()
    return allLessons.filter { it.normalizedSubjectId() == sid }.sortedWith(lessonTeachingOrder)
}

fun faDigits(n: Int): String = faDigits(n.toString())

fun faDigits(s: String): String {
    val sb = StringBuilder(s.length)
    s.forEach { c -> sb.append(if (c in '0'..'9') ('۰' + (c - '0')) else c) }
    return sb.toString()
}
