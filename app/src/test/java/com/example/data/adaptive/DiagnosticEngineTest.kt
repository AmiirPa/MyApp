package com.example.data.adaptive

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class DiagnosticEngineTest {

    private fun bank() = listOf(
        DiagnosticEngine.DiagnosticQuestionItem(
            id = "d-c1", topicId = "t1", subjectId = "math",
            level = QuestionLevel.CONCEPT, questionText = "مفهوم؟",
            options = listOf("الف", "ب"), correctIndex = 0
        ),
        DiagnosticEngine.DiagnosticQuestionItem(
            id = "d-c2", topicId = "t1", subjectId = "math",
            level = QuestionLevel.CONCEPT, questionText = "مفهوم ۲؟",
            options = listOf("الف", "ب"), correctIndex = 1
        ),
        DiagnosticEngine.DiagnosticQuestionItem(
            id = "d-f1", topicId = "t1", subjectId = "math",
            level = QuestionLevel.FOUNDATION, questionText = "پیش‌نیاز؟",
            options = listOf("الف", "ب"), correctIndex = 0,
            foundationLessonId = "found-math-01"
        ),
        DiagnosticEngine.DiagnosticQuestionItem(
            id = "d-f2", topicId = "t1", subjectId = "math",
            level = QuestionLevel.FOUNDATION, questionText = "پیش‌نیاز ۲؟",
            options = listOf("الف", "ب"), correctIndex = 1,
            foundationLessonId = "found-math-01"
        ),
        DiagnosticEngine.DiagnosticQuestionItem(
            id = "d-a1", topicId = "t1", subjectId = "math",
            level = QuestionLevel.APPLICATION, questionText = "کاربرد؟",
            options = listOf("الف", "ب"), correctIndex = 0
        ),
        DiagnosticEngine.DiagnosticQuestionItem(
            id = "d-a2", topicId = "t1", subjectId = "math",
            level = QuestionLevel.APPLICATION, questionText = "کاربرد ۲؟",
            options = listOf("الف", "ب"), correctIndex = 1
        )
    )

    private fun answer(id: String, level: QuestionLevel, correct: Boolean) = QuestionAttempt(
        questionId = id, topicId = "t1", subjectId = "math",
        level = level, isCorrect = correct, wasSkipped = false,
        timeSpentSeconds = 60, answeredAtEpochDay = 20000L
    )

    @Test
    fun firstQuestion_isConceptLevel() {
        val next = DiagnosticEngine.nextQuestion(bank(), emptyList())
        assertEquals(QuestionLevel.CONCEPT, next?.level)
    }

    @Test
    fun correctConcept_movesToApplication() {
        val answers = listOf(answer("d-c1", QuestionLevel.CONCEPT, true))
        val next = DiagnosticEngine.nextQuestion(bank(), answers)
        assertEquals(QuestionLevel.APPLICATION, next?.level)
    }

    @Test
    fun wrongConcept_movesDownToFoundation() {
        val answers = listOf(answer("d-c1", QuestionLevel.CONCEPT, false))
        val next = DiagnosticEngine.nextQuestion(bank(), answers)
        assertEquals(QuestionLevel.FOUNDATION, next?.level)
    }

    @Test
    fun threeCorrectInSameLevel_stops() {
        // ۳ پاسخ متوالی در یک سطح با نتیجه یکسان → توقف.
        val threeApp = listOf(
            answer("d-a1", QuestionLevel.APPLICATION, true),
            answer("d-a2", QuestionLevel.APPLICATION, true),
            answer("d-a1", QuestionLevel.APPLICATION, true)
        )
        assertTrue(DiagnosticEngine.shouldStop(threeApp))
        assertNull(DiagnosticEngine.nextQuestion(bank(), threeApp))
    }

    @Test
    fun mixedLevels_doNotStopEarly() {
        val answers = listOf(
            answer("d-c1", QuestionLevel.CONCEPT, true),
            answer("d-c2", QuestionLevel.CONCEPT, true),
            answer("d-a1", QuestionLevel.APPLICATION, true)
        )
        // سطوح مخلوط‌اند، پس توقف زودهنگام نداریم؛ سؤال بعدی APPLICATION است.
        assertTrue(!DiagnosticEngine.shouldStop(answers))
        assertEquals(
            QuestionLevel.APPLICATION,
            DiagnosticEngine.nextQuestion(bank(), answers)?.level
        )
    }

    @Test
    fun stopsAtMaxQuestions() {
        val answers = (1..6).map { answer("d-c1", QuestionLevel.CONCEPT, it % 2 == 0) }
        assertNull(DiagnosticEngine.nextQuestion(bank(), answers))
    }

    @Test
    fun foundationWrong_concludesPrerequisiteWeak_withFoundationLesson() {
        val answers = listOf(
            answer("d-c1", QuestionLevel.CONCEPT, false),
            answer("d-f1", QuestionLevel.FOUNDATION, false)
        )
        val result = DiagnosticEngine.conclude("t1", "math", answers, bank(), "lesson-t1")
        assertEquals(TopicStatus.PREREQUISITE_WEAK, result.status)
        assertEquals(QuestionLevel.FOUNDATION, result.weakestLevel)
        assertEquals("found-math-01", result.recommendedLessonId)
    }

    @Test
    fun allCorrect_concludesLearning_notMastered() {
        val answers = listOf(
            answer("d-c1", QuestionLevel.CONCEPT, true),
            answer("d-a1", QuestionLevel.APPLICATION, true)
        )
        val result = DiagnosticEngine.conclude("t1", "math", answers, bank(), "lesson-t1")
        // تشخیصی به‌تنهایی «مسلط» نمی‌سازد.
        assertEquals(TopicStatus.LEARNING, result.status)
        assertEquals("lesson-t1", result.recommendedLessonId)
    }

    @Test
    fun noAnswers_concludesNotStarted() {
        val result = DiagnosticEngine.conclude("t1", "math", emptyList(), bank(), "lesson-t1")
        assertEquals(TopicStatus.NOT_STARTED, result.status)
    }
}
