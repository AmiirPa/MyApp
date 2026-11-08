package com.example.data.adaptive

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ReviewSchedulerTest {

    private val today = 20000L

    private fun card(
        id: String,
        subject: String = "math",
        box: Int = 1,
        errors: Int = 0,
        reviews: Int = 0,
        consecutive: Int = 0,
        nextDay: Long = today
    ) = ReviewCardState(
        itemId = id, topicId = id, subjectId = subject,
        leitnerBox = box, errorCount = errors, totalReviews = reviews,
        consecutiveCorrect = consecutive, lastReviewEpochDay = today - 1,
        nextReviewEpochDay = nextDay
    )

    @Test
    fun intervals_growWithBox() {
        assertEquals(1L, ReviewScheduler.intervalDaysForBox(1))
        assertEquals(2L, ReviewScheduler.intervalDaysForBox(2))
        assertEquals(4L, ReviewScheduler.intervalDaysForBox(3))
        assertEquals(8L, ReviewScheduler.intervalDaysForBox(4))
        assertEquals(16L, ReviewScheduler.intervalDaysForBox(5))
    }

    @Test
    fun correctReview_movesBoxUp_andSetsNextDay() {
        val next = ReviewScheduler.applyReview(card("c1", box = 2), correct = true, reviewEpochDay = today)
        assertEquals(3, next.leitnerBox)
        assertEquals(today + 4, next.nextReviewEpochDay)
        assertEquals(1, next.consecutiveCorrect)
    }

    @Test
    fun wrongReview_resetsToBoxOne() {
        val next = ReviewScheduler.applyReview(card("c1", box = 4, errors = 1, reviews = 5, consecutive = 3), correct = false, reviewEpochDay = today)
        assertEquals(1, next.leitnerBox)
        assertEquals(today + 1, next.nextReviewEpochDay)
        assertEquals(2, next.errorCount)
        assertEquals(0, next.consecutiveCorrect)
    }

    @Test
    fun boxCapsAtFive() {
        val next = ReviewScheduler.applyReview(card("c1", box = 5), correct = true, reviewEpochDay = today)
        assertEquals(5, next.leitnerBox)
        assertEquals(today + 16, next.nextReviewEpochDay)
    }

    @Test
    fun dueCards_onlyThoseWithNextDayTodayOrEarlier() {
        val cards = listOf(
            card("c1", nextDay = today - 2),
            card("c2", nextDay = today),
            card("c3", nextDay = today + 3)
        )
        val due = ReviewScheduler.dueCards(cards, today)
        assertEquals(2, due.size)
        assertTrue(due.any { it.itemId == "c1" })
        assertTrue(due.any { it.itemId == "c2" })
    }

    @Test
    fun emptyHistory_hasNoDueCards() {
        assertTrue(ReviewScheduler.dueCards(emptyList(), today).isEmpty())
    }

    @Test
    fun priority_repeatedErrorsComeFirst() {
        val low = card("low", errors = 0, nextDay = today - 5)
        val high = card("high", errors = 4, nextDay = today)
        assertTrue(ReviewScheduler.priorityScore(high, today) > ReviewScheduler.priorityScore(low, today))
    }

    @Test
    fun reviewQueue_isInterleavedAcrossSubjects() {
        val cards = listOf(
            card("m1", subject = "math", errors = 3),
            card("m2", subject = "math", errors = 2),
            card("a1", subject = "arabic", errors = 3),
            card("a2", subject = "arabic", errors = 2)
        )
        val queue = ReviewScheduler.buildReviewQueue(cards, today, maxItems = 4)
        assertEquals(4, queue.size)
        // چرخشی: دو تای اول نباید هر دو از یک درس باشند.
        assertTrue(queue[0].subjectId != queue[1].subjectId)
    }

    @Test
    fun retentionRate_needsAtLeastThreeReviews() {
        assertNull(ReviewScheduler.retentionRate(listOf(card("c1", reviews = 2, errors = 0))))
        val rate = ReviewScheduler.retentionRate(
            listOf(card("c1", reviews = 4, errors = 1), card("c2", reviews = 2, errors = 0))
        )
        // درست = (۴-۱) + (۲-۰) = ۵ از ۶
        assertEquals(5.0 / 6.0, rate!!, 0.001)
    }

    @Test
    fun newCard_startsInBoxOne() {
        val c = ReviewScheduler.newCard("n1", "n1", "math", today)
        assertEquals(1, c.leitnerBox)
        assertEquals(today + 1, c.nextReviewEpochDay)
        assertEquals(0, c.totalReviews)
    }
}
