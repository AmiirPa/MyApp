package com.example.data.adaptive

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ProgressReportTest {

    private val today = 20000L

    private fun q(
        id: String,
        topic: String,
        correct: Boolean,
        seconds: Int = 60
    ) = QuestionAttempt(
        questionId = id, topicId = topic, subjectId = "math",
        level = QuestionLevel.CONCEPT, isCorrect = correct, wasSkipped = false,
        timeSpentSeconds = seconds, answeredAtEpochDay = today
    )

    private fun exam(id: String, questions: List<QuestionAttempt>) = ExamAttempt(
        id = id, subjectId = "math", title = "آزمون",
        startedAtEpochDay = today, totalSeconds = 600, questions = questions
    )

    private fun emptyInput() = ProgressReport.ReportInput(
        completedDays = emptySet(),
        lessonEvidence = emptyList(),
        topicStatuses = emptyMap(),
        examAttempts = emptyList(),
        reviewCards = emptyList(),
        todayEpochDay = today,
        todaysPlan = null,
        completedLessonIdsToday = emptySet(),
        recordedFocusMinutesToday = null
    )

    @Test
    fun emptyInput_allMetricsInsufficient() {
        val report = ProgressReport.build(emptyInput())
        assertFalse(report.studyDays.hasEnoughData)
        assertFalse(report.completedLessons.hasEnoughData)
        assertFalse(report.masteryBySubject.hasEnoughData)
        assertFalse(report.examTrend.hasEnoughData)
        assertFalse(report.forgettingRate.hasEnoughData)
        assertFalse(report.dueReviewsToday.hasEnoughData)
        assertFalse(report.repeatedErrorTopics.hasEnoughData)
        assertFalse(report.planAdherence.hasEnoughData)
        assertFalse(report.weaknessChange.hasEnoughData)
        assertNull(report.examTrend.value)
    }

    @Test
    fun examTrend_needsTwoExams() {
        val oneExam = emptyInput().copy(
            examAttempts = listOf(exam("e1", listOf(q("q1", "t1", true), q("q2", "t1", false))))
        )
        assertFalse(ProgressReport.build(oneExam).examTrend.hasEnoughData)

        val twoExams = emptyInput().copy(
            examAttempts = listOf(
                exam("e1", listOf(q("q1", "t1", true), q("q2", "t1", false))),
                exam("e2", listOf(q("q1", "t1", true), q("q2", "t1", true)))
            )
        )
        val report = ProgressReport.build(twoExams)
        assertTrue(report.examTrend.hasEnoughData)
        assertEquals(listOf(50.0, 100.0), report.examTrend.value)
    }

    @Test
    fun forgettingRate_needsThreeReviews() {
        val few = emptyInput().copy(
            reviewCards = listOf(
                ReviewCardState("c1", "t1", "math", 1, 1, 2, 0, today - 1, today)
            )
        )
        assertFalse(ProgressReport.build(few).forgettingRate.hasEnoughData)

        val enough = emptyInput().copy(
            reviewCards = listOf(
                ReviewCardState("c1", "t1", "math", 1, 1, 3, 0, today - 1, today),
                ReviewCardState("c2", "t2", "math", 2, 0, 2, 2, today - 2, today + 2)
            )
        )
        val report = ProgressReport.build(enough)
        assertTrue(report.forgettingRate.hasEnoughData)
        assertEquals(1.0 / 5.0, report.forgettingRate.value!!, 0.001)
    }

    @Test
    fun repeatedErrorTopics_listsTopicsWithTwoOrMoreErrors() {
        val input = emptyInput().copy(
            reviewCards = listOf(
                ReviewCardState("c1", "t1", "math", 1, 3, 5, 0, today - 1, today),
                ReviewCardState("c2", "t2", "math", 2, 1, 3, 1, today - 1, today),
                ReviewCardState("c3", "t3", "math", 1, 2, 2, 0, today - 1, today)
            )
        )
        val report = ProgressReport.build(input)
        assertTrue(report.repeatedErrorTopics.hasEnoughData)
        assertEquals(listOf("t1", "t3"), report.repeatedErrorTopics.value)
    }

    @Test
    fun weaknessChange_detectsImprovementAndWorsening() {
        val input = emptyInput().copy(
            topicStatuses = mapOf(
                "t1" to TopicStatusResult(TopicStatus.MASTERED, EvidenceLevel.SUFFICIENT, ""),
                "t2" to TopicStatusResult(TopicStatus.PARTIAL, EvidenceLevel.SUFFICIENT, "")
            ),
            previousTopicStatuses = mapOf(
                "t1" to TopicStatus.LEARNING,
                "t2" to TopicStatus.MASTERED
            )
        )
        val report = ProgressReport.build(input)
        assertTrue(report.weaknessChange.hasEnoughData)
        assertEquals(listOf("t1"), report.weaknessChange.value!!.improvedTopics)
        assertEquals(listOf("t2"), report.weaknessChange.value!!.worsenedTopics)
    }

    @Test
    fun dueReviewsToday_countsDueCards() {
        val input = emptyInput().copy(
            reviewCards = listOf(
                ReviewCardState("c1", "t1", "math", 1, 0, 1, 1, today - 1, today),
                ReviewCardState("c2", "t2", "math", 1, 0, 1, 1, today - 1, today + 5)
            )
        )
        val report = ProgressReport.build(input)
        assertTrue(report.dueReviewsToday.hasEnoughData)
        assertEquals(1, report.dueReviewsToday.value)
    }
}
