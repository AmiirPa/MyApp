package com.example.data.adaptive

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExamAnalyzerTest {

    private val today = 20000L

    private fun q(
        id: String,
        topic: String,
        correct: Boolean,
        seconds: Int,
        skipped: Boolean = false,
        level: QuestionLevel = QuestionLevel.CONCEPT
    ) = QuestionAttempt(
        questionId = id, topicId = topic, subjectId = "math",
        level = level, isCorrect = correct, wasSkipped = skipped,
        timeSpentSeconds = seconds, answeredAtEpochDay = today
    )

    private fun exam(questions: List<QuestionAttempt>, totalSeconds: Int = 600) = ExamAttempt(
        id = "e1", subjectId = "math", title = "آزمون ریاضی",
        startedAtEpochDay = today, totalSeconds = totalSeconds, questions = questions
    )

    @Test
    fun percentages_correctWrongSkipped() {
        val attempt = exam(
            listOf(
                q("q1", "t1", true, 60), q("q2", "t1", true, 60),
                q("q3", "t1", false, 60), q("q4", "t2", false, 60, skipped = true)
            )
        )
        val summary = ExamAnalyzer.summarize(attempt)
        assertEquals(50.0, summary.correctPercent, 0.001)
        assertEquals(25.0, summary.wrongPercent, 0.001)
        assertEquals(25.0, summary.skippedPercent, 0.001)
        assertEquals(2, summary.correctCount)
        assertEquals(1, summary.wrongCount)
        assertEquals(1, summary.skippedCount)
    }

    @Test
    fun emptyExam_hasZeroPercentsAndNullMedian() {
        val summary = ExamAnalyzer.summarize(exam(emptyList(), totalSeconds = 0))
        assertEquals(0.0, summary.correctPercent, 0.001)
        assertNull(summary.medianSecondsPerQuestion)
        assertNull(summary.averageSecondsPerQuestion)
    }

    @Test
    fun perTopicPercent_needsTwoQuestions() {
        val attempt = exam(
            listOf(
                q("q1", "t1", true, 60), q("q2", "t1", false, 60),
                q("q3", "t2", true, 60) // فقط یک سؤال روی t2
            )
        )
        val summary = ExamAnalyzer.summarize(attempt)
        assertEquals(50.0, summary.perTopicPercent["t1"]!!, 0.001)
        assertTrue(!summary.perTopicPercent.containsKey("t2"))
    }

    @Test
    fun weakestTopics_belowFiftyPercent() {
        val attempt = exam(
            listOf(
                q("q1", "t1", false, 60), q("q2", "t1", false, 60),
                q("q3", "t2", true, 60), q("q4", "t2", true, 60)
            )
        )
        val summary = ExamAnalyzer.summarize(attempt)
        assertEquals(listOf("t1"), summary.weakestTopics)
    }

    @Test
    fun difficultQuestions_wrongAndSlow() {
        // میانه زمان: ۶۰؛ دشوار = غلط + بیشتر از ۹۰ ثانیه.
        val attempt = exam(
            listOf(
                q("q1", "t1", true, 60), q("q2", "t1", true, 60),
                q("q3", "t1", true, 60), q("q4", "t1", false, 150),
                q("q5", "t1", false, 40)
            )
        )
        val summary = ExamAnalyzer.summarize(attempt)
        assertEquals(1, summary.difficultQuestions.size)
        assertEquals("q4", summary.difficultQuestions.first().questionId)
    }

    @Test
    fun suspiciousQuestions_fastCorrect() {
        val attempt = exam(
            listOf(
                q("q1", "t1", true, 3), // ۳ ثانیه → مشکوک
                q("q2", "t1", true, 60), q("q3", "t1", true, 60),
                q("q4", "t1", false, 60)
            )
        )
        val summary = ExamAnalyzer.summarize(attempt)
        assertEquals(1, summary.suspiciousQuestions.size)
        assertEquals("q1", summary.suspiciousQuestions.first().questionId)
    }

    @Test
    fun delta_needsPreviousAttempt() {
        val current = exam(listOf(q("q1", "t1", true, 60), q("q2", "t1", true, 60)))
        val analysisWithoutPrevious = ExamAnalyzer.analyze(current, emptyList())
        assertNull(analysisWithoutPrevious.deltaPercentVsPrevious)

        val previous = exam(listOf(q("q1", "t1", true, 60), q("q2", "t1", false, 60)))
        val analysis = ExamAnalyzer.analyze(current, listOf(previous))
        assertEquals(50.0, analysis.deltaPercentVsPrevious!!, 0.001)
    }

    @Test
    fun inferCause_skippedIsTimeManagement() {
        val skipped = q("q1", "t1", false, 0, skipped = true)
        assertEquals(
            ErrorCause.TIME_MANAGEMENT,
            ExamAnalyzer.inferErrorCause(skipped, medianSeconds = 60.0, prerequisiteWeak = false, isCombinedQuestion = false)
        )
    }

    @Test
    fun inferCause_fastWrongIsCarelessness_slowWrongWithWeakPrerequisite() {
        val fast = q("q1", "t1", false, 5)
        assertEquals(
            ErrorCause.CARELESSNESS,
            ExamAnalyzer.inferErrorCause(fast, medianSeconds = 60.0, prerequisiteWeak = false, isCombinedQuestion = false)
        )
        val slowWeak = q("q2", "t1", false, 150)
        assertEquals(
            ErrorCause.PREREQUISITE_WEAK,
            ExamAnalyzer.inferErrorCause(slowWeak, medianSeconds = 60.0, prerequisiteWeak = true, isCombinedQuestion = false)
        )
        val slowStrong = q("q3", "t1", false, 150)
        assertEquals(
            ErrorCause.REASONING_WEAK,
            ExamAnalyzer.inferErrorCause(slowStrong, medianSeconds = 60.0, prerequisiteWeak = false, isCombinedQuestion = false)
        )
    }

    @Test
    fun inferCause_fastCorrectIsCorrectWithoutMastery() {
        val fastCorrect = q("q1", "t1", true, 3)
        assertEquals(
            ErrorCause.CORRECT_WITHOUT_MASTERY,
            ExamAnalyzer.inferErrorCause(fastCorrect, medianSeconds = 60.0, prerequisiteWeak = false, isCombinedQuestion = false)
        )
    }

    @Test
    fun selfReportedCause_beatsInference() {
        val wrong = q("q1", "t1", false, 5).copy(selfReportedCause = ErrorCause.MISREAD_QUESTION)
        val attempt = exam(listOf(wrong, q("q2", "t1", true, 60)))
        val analysis = ExamAnalyzer.analyze(attempt)
        assertEquals(1, analysis.wrongAnswers.size)
        assertEquals(ErrorCause.MISREAD_QUESTION, analysis.wrongAnswers.first().cause)
        assertTrue(!analysis.wrongAnswers.first().causeIsInferred)
    }
}
