package com.example.data.adaptive

import com.example.data.curriculum.MiniCheckQuestion
import com.example.data.curriculum.ZeroLesson
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * تست خالص بانک سؤال تشخیصی (§۳ الحاقیه).
 *
 * بانک هرگز سؤال نمی‌سازد: همیشه مینی‌چک خود درس (CONCEPT) + سؤال‌های
 * ثابت همان درس (FOUNDATION) است؛ اگر برای درسی سؤال ثابت نباشد، بانک
 * فقط مینی‌چک را دارد و صفحه «داده کافی نیست» نشان می‌دهد.
 */
class DiagnosticBankTest {

    private fun lesson(id: String, subjectId: String) = ZeroLesson(
        id = id,
        subjectId = subjectId,
        orderIndex = 1,
        levelBadge = "سطح صفر (۰)",
        title = "درس آزمایشی",
        subtitle = "زیرعنوان",
        estimatedMinutes = 20,
        whyStudentsStruggle = "علت",
        steps = emptyList(),
        goldenFormulas = emptyList(),
        workedExamples = emptyList(),
        miniCheck = MiniCheckQuestion(
            question = "مینی‌چک آزمایشی؟",
            options = listOf("الف", "ب", "ج", "د"),
            correctIndex = 2,
            explanation = "توضیح"
        )
    )

    @Test
    fun mathLesson_bankStartsWithMiniCheckAsConcept() {
        val bank = buildDiagnosticBank(lesson("math_test_01", "math"))
        val first = bank.first()
        assertEquals("minicheck:math_test_01", first.id)
        assertEquals("math_test_01", first.topicId)
        assertEquals(QuestionLevel.CONCEPT, first.level)
        assertEquals("مینی‌چک آزمایشی؟", first.questionText)
        assertEquals(2, first.correctIndex)
        assertEquals(listOf("الف", "ب", "ج", "د"), first.options)
    }

    @Test
    fun mathLesson_bankIncludesFixedFoundationQuestions() {
        val bank = buildDiagnosticBank(lesson("math_test_01", "math"))
        val foundation = bank.filter { it.level == QuestionLevel.FOUNDATION }
        assertTrue(foundation.isNotEmpty())
        assertTrue(foundation.any { it.id == "diag_math_1" })
        assertTrue(foundation.all { it.subjectId == "math" })
        assertTrue(foundation.all { it.topicId == "math_test_01" })
    }

    @Test
    fun unknownSubject_bankHasOnlyMiniCheck() {
        val bank = buildDiagnosticBank(lesson("elem1-test", "elem1-test"))
        assertEquals(1, bank.size)
        assertEquals(QuestionLevel.CONCEPT, bank.single().level)
    }

    @Test
    fun bank_neverEmpty() {
        val bank = buildDiagnosticBank(lesson("elem9-test", "elem9-test"))
        assertTrue(bank.isNotEmpty())
    }

    @Test
    fun bank_idsAreUnique() {
        val bank = buildDiagnosticBank(lesson("math_test_01", "math"))
        assertEquals(bank.size, bank.map { it.id }.distinct().size)
    }
}
