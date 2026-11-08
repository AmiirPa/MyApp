package com.example.data.adaptive

import com.example.data.curriculum.MinusZeroLearningEngine
import com.example.data.curriculum.ZeroLesson
import com.example.data.curriculum.normalizedSubjectId

/**
 * بانک سؤال تشخیصی یک درس — خالص و قابل تست (§۳ الحاقیه).
 *
 * - مینی‌چک خود درس → سطح CONCEPT (مفهوم).
 * - سؤال‌های تشخیصی ثابت همان درس (پیش‌نیاز) → سطح FOUNDATION.
 * - سؤال APPLICATION در این بانک نیست: سؤال‌های کوییز به هیچ درسی وصل
 *   نیستند، پس موتور با همین بانک کار می‌کند؛ اگر سؤال کم بود، نتیجه با
 *   برچسب «شواهد کم — قطعی نیست» برمی‌گردد. هرگز سؤالی ساخته نمی‌شود.
 */
fun buildDiagnosticBank(lesson: ZeroLesson): List<DiagnosticEngine.DiagnosticQuestionItem> {
    val subjectId = lesson.normalizedSubjectId()
    val items = ArrayList<DiagnosticEngine.DiagnosticQuestionItem>()
    items.add(
        DiagnosticEngine.DiagnosticQuestionItem(
            id = "minicheck:${lesson.id}",
            topicId = lesson.id,
            subjectId = subjectId,
            level = QuestionLevel.CONCEPT,
            questionText = lesson.miniCheck.question,
            options = lesson.miniCheck.options,
            correctIndex = lesson.miniCheck.correctIndex
        )
    )
    for (q in MinusZeroLearningEngine.diagnosticQuestions) {
        if (q.subjectId != subjectId) continue
        items.add(
            DiagnosticEngine.DiagnosticQuestionItem(
                id = q.id,
                topicId = lesson.id,
                subjectId = q.subjectId,
                level = QuestionLevel.FOUNDATION,
                questionText = q.friendlyPrompt,
                options = q.options,
                correctIndex = q.correctIndex
            )
        )
    }
    return items
}
