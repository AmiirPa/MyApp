package com.example.data.adaptive

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TopicStatusEngineTest {

    private val today = 20000L

    private fun attempt(
        topic: String = "t1",
        correct: Boolean,
        seconds: Int = 60,
        skipped: Boolean = false,
        level: QuestionLevel = QuestionLevel.CONCEPT
    ) = QuestionAttempt(
        questionId = "q-$topic-${System.nanoTime()}",
        topicId = topic,
        subjectId = "math",
        level = level,
        isCorrect = correct,
        wasSkipped = skipped,
        timeSpentSeconds = seconds,
        answeredAtEpochDay = today
    )

    private fun lesson(completed: Boolean, mastery: Int = 0) = LessonEvidence(
        lessonId = "t1",
        subjectId = "math",
        isCompleted = completed,
        masteryPercent = mastery,
        updatedAtEpochDay = today
    )

    @Test
    fun noEvidence_isNotStarted() {
        val result = TopicStatusEngine.resolveTopicStatus(
            topicId = "t1", lesson = null, attempts = emptyList(),
            reviewState = null, prerequisiteStatus = null,
            miniCheckCorrect = false, miniCheckAttempted = false
        )
        assertEquals(TopicStatus.NOT_STARTED, result.status)
        assertEquals(EvidenceLevel.INSUFFICIENT, result.evidenceLevel)
    }

    @Test
    fun startedLesson_isLearning() {
        val result = TopicStatusEngine.resolveTopicStatus(
            topicId = "t1", lesson = lesson(completed = false, mastery = 30),
            attempts = emptyList(), reviewState = null, prerequisiteStatus = null,
            miniCheckCorrect = false, miniCheckAttempted = false
        )
        assertEquals(TopicStatus.LEARNING, result.status)
    }

    @Test
    fun singleCorrectAnswer_doesNotPromoteToMastered() {
        val attempts = listOf(attempt(correct = true))
        val result = TopicStatusEngine.resolveTopicStatus(
            topicId = "t1", lesson = lesson(completed = true, mastery = 90),
            attempts = attempts, reviewState = null, prerequisiteStatus = null,
            miniCheckCorrect = true, miniCheckAttempted = true
        )
        // فقط یک تلاش درست: حداقل ۲ تلاش لازم است.
        assertFalse(result.status == TopicStatus.MASTERED)
    }

    @Test
    fun twoCorrectAnswers_withCompletedLesson_isMastered() {
        val attempts = listOf(attempt(correct = true), attempt(correct = true), attempt(correct = true))
        val result = TopicStatusEngine.resolveTopicStatus(
            topicId = "t1", lesson = lesson(completed = true, mastery = 90),
            attempts = attempts, reviewState = null, prerequisiteStatus = null,
            miniCheckCorrect = true, miniCheckAttempted = true
        )
        assertEquals(TopicStatus.MASTERED, result.status)
    }

    @Test
    fun singleWrongAnswer_doesNotDemoteFromMastered() {
        // مسلط با سابقه درست، بعد یک غلط: نباید فوراً تنزل کند.
        val attempts = listOf(
            attempt(correct = true), attempt(correct = true), attempt(correct = true),
            attempt(correct = false)
        )
        val result = TopicStatusEngine.resolveTopicStatus(
            topicId = "t1", lesson = lesson(completed = true, mastery = 90),
            attempts = attempts, reviewState = null, prerequisiteStatus = null,
            miniCheckCorrect = true, miniCheckAttempted = true
        )
        assertEquals(TopicStatus.MASTERED, result.status)
    }

    @Test
    fun twoConsecutiveWrong_afterCompletion_isPartial() {
        val attempts = listOf(
            attempt(correct = true),
            attempt(correct = false),
            attempt(correct = false)
        )
        val result = TopicStatusEngine.resolveTopicStatus(
            topicId = "t1", lesson = lesson(completed = true, mastery = 40),
            attempts = attempts, reviewState = null, prerequisiteStatus = null,
            miniCheckCorrect = false, miniCheckAttempted = true
        )
        assertEquals(TopicStatus.PARTIAL, result.status)
    }

    @Test
    fun luckyCorrect_isDetected() {
        val fastCorrect = attempt(correct = true, seconds = 3)
        assertTrue(TopicStatusEngine.isLuckyCorrect(fastCorrect, priorAttemptsOnTopic = 0))
        assertFalse(TopicStatusEngine.isLuckyCorrect(fastCorrect, priorAttemptsOnTopic = 2))
        val slowCorrect = attempt(correct = true, seconds = 60)
        assertFalse(TopicStatusEngine.isLuckyCorrect(slowCorrect, priorAttemptsOnTopic = 0))
    }

    @Test
    fun luckyCorrect_doesNotCountTowardMastery() {
        // یک جواب شانسی + یک جواب درست واقعی: فقط یکی شمرده می‌شود → مسلط نمی‌شود.
        val attempts = listOf(
            attempt(correct = true, seconds = 3),
            attempt(correct = true, seconds = 60)
        )
        val result = TopicStatusEngine.resolveTopicStatus(
            topicId = "t1", lesson = lesson(completed = true, mastery = 90),
            attempts = attempts, reviewState = null, prerequisiteStatus = null,
            miniCheckCorrect = true, miniCheckAttempted = true
        )
        assertFalse(result.status == TopicStatus.MASTERED)
    }

    @Test
    fun failedSpacedReview_afterMastery_needsReview() {
        val attempts = listOf(attempt(correct = true), attempt(correct = true), attempt(correct = true))
        val review = ReviewCardState(
            itemId = "t1", topicId = "t1", subjectId = "math",
            leitnerBox = 3, errorCount = 1, totalReviews = 3,
            consecutiveCorrect = 0, lastReviewEpochDay = today - 4, nextReviewEpochDay = today
        )
        val result = TopicStatusEngine.resolveTopicStatus(
            topicId = "t1", lesson = lesson(completed = true, mastery = 90),
            attempts = attempts, reviewState = review, prerequisiteStatus = null,
            miniCheckCorrect = true, miniCheckAttempted = true
        )
        assertEquals(TopicStatus.NEEDS_REVIEW, result.status)
    }

    @Test
    fun twoSpacedCorrectReviews_afterMastery_isStable() {
        val attempts = listOf(attempt(correct = true), attempt(correct = true), attempt(correct = true))
        val review = ReviewCardState(
            itemId = "t1", topicId = "t1", subjectId = "math",
            leitnerBox = 3, errorCount = 0, totalReviews = 3,
            consecutiveCorrect = 2, lastReviewEpochDay = today - 4, nextReviewEpochDay = today + 4
        )
        val result = TopicStatusEngine.resolveTopicStatus(
            topicId = "t1", lesson = lesson(completed = true, mastery = 90),
            attempts = attempts, reviewState = review, prerequisiteStatus = null,
            miniCheckCorrect = true, miniCheckAttempted = true
        )
        assertEquals(TopicStatus.STABLE, result.status)
    }

    @Test
    fun prerequisiteWeak_overridesTopicEvidence() {
        val attempts = listOf(attempt(correct = true), attempt(correct = true))
        val result = TopicStatusEngine.resolveTopicStatus(
            topicId = "t1", lesson = lesson(completed = false),
            attempts = attempts, reviewState = null,
            prerequisiteStatus = TopicStatus.PREREQUISITE_WEAK,
            miniCheckCorrect = false, miniCheckAttempted = false
        )
        assertEquals(TopicStatus.PREREQUISITE_WEAK, result.status)
    }

    @Test
    fun evidenceLevel_thresholds() {
        assertEquals(EvidenceLevel.INSUFFICIENT, TopicStatusEngine.evidenceLevel(0))
        assertEquals(EvidenceLevel.INSUFFICIENT, TopicStatusEngine.evidenceLevel(2))
        assertEquals(EvidenceLevel.LOW, TopicStatusEngine.evidenceLevel(3))
        assertEquals(EvidenceLevel.LOW, TopicStatusEngine.evidenceLevel(4))
        assertEquals(EvidenceLevel.SUFFICIENT, TopicStatusEngine.evidenceLevel(5))
    }
}
