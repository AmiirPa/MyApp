package com.example.data.adaptive

import com.example.data.curriculum.MiniCheckQuestion
import com.example.data.curriculum.WorkedExample
import com.example.data.curriculum.ZeroLesson
import com.example.data.curriculum.ZeroStep
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class AdaptivePlannerTest {

    private val today = 20000L

    private fun lesson(id: String, subject: String, order: Int) = ZeroLesson(
        id = id,
        subjectId = subject,
        orderIndex = order,
        levelTitle = "سطح صفر",
        title = "درس $id",
        zeroHook = "معنی ساده $id",
        steps = listOf(ZeroStep(1, "به زبان خیلی ساده", "توضیح ساده")),
        goldenFormulas = listOf("قاعده ۱"),
        workedExamples = listOf(WorkedExample("سؤال؟", "حل", "دام")),
        miniCheck = MiniCheckQuestion("سؤال؟", listOf("الف", "ب"), 0, "توضیح")
    )

    private fun evidence(id: String, completed: Boolean) = LessonEvidence(
        lessonId = id, subjectId = "math", isCompleted = completed,
        masteryPercent = if (completed) 90 else 0, updatedAtEpochDay = today
    )

    private fun reviewCard(id: String, subject: String = "math", errors: Int = 0) = ReviewCardState(
        itemId = id, topicId = id, subjectId = subject,
        leitnerBox = 1, errorCount = errors, totalReviews = 1,
        consecutiveCorrect = 0, lastReviewEpochDay = today - 1, nextReviewEpochDay = today
    )

    @Test
    fun totalMinutes_neverExceedAvailable() {
        val lessons = listOf(
            lesson("l1", "math", 1), lesson("l2", "math", 2), lesson("l3", "arabic", 3)
        )
        val plan = AdaptivePlanner.buildDailyPlan(
            AdaptivePlanner.PlanInput(
                lessons = lessons,
                lessonEvidence = emptyMap(),
                topicStatuses = emptyMap(),
                dueReviews = listOf(reviewCard("l1"), reviewCard("l2"), reviewCard("l3")),
                availableMinutes = 60,
                todayEpochDay = today
            )
        )
        val total = plan.items.sumOf { it.minutes }
        assertTrue(total <= 60)
    }

    @Test
    fun reviews_cappedAtFortyPercentOfDay() {
        val lessons = listOf(lesson("l1", "math", 1))
        val plan = AdaptivePlanner.buildDailyPlan(
            AdaptivePlanner.PlanInput(
                lessons = lessons,
                lessonEvidence = emptyMap(),
                topicStatuses = emptyMap(),
                dueReviews = (1..10).map { reviewCard("r$it") },
                availableMinutes = 60,
                todayEpochDay = today
            )
        )
        val reviewMinutes = plan.items.filter { it.kind == PlanItemKind.REVIEW }.sumOf { it.minutes }
        assertTrue(reviewMinutes <= 24) // ۴۰٪ از ۶۰ دقیقه
    }

    @Test
    fun prerequisiteWeakTopic_schedulesFoundationLessonFirst() {
        val foundation = lesson("found-math-01", "math", 1)
        val main = lesson("l-main", "math", 2)
        val plan = AdaptivePlanner.buildDailyPlan(
            AdaptivePlanner.PlanInput(
                lessons = listOf(foundation, main),
                lessonEvidence = emptyMap(),
                topicStatuses = mapOf(
                    "l-main" to TopicStatusResult(
                        TopicStatus.PREREQUISITE_WEAK, EvidenceLevel.LOW, "پیش‌نیاز ضعیف است."
                    )
                ),
                dueReviews = emptyList(),
                availableMinutes = 60,
                todayEpochDay = today,
                prerequisiteLessonIdByTopic = mapOf("l-main" to "found-math-01")
            )
        )
        assertEquals("found-math-01", plan.items.first().lessonId)
    }

    @Test
    fun completedLessons_areSkipped() {
        val lessons = listOf(lesson("l1", "math", 1), lesson("l2", "math", 2))
        val plan = AdaptivePlanner.buildDailyPlan(
            AdaptivePlanner.PlanInput(
                lessons = lessons,
                lessonEvidence = mapOf("l1" to evidence("l1", completed = true)),
                topicStatuses = emptyMap(),
                dueReviews = emptyList(),
                availableMinutes = 60,
                todayEpochDay = today
            )
        )
        assertTrue(plan.items.none { it.lessonId == "l1" })
        assertTrue(plan.items.any { it.lessonId == "l2" })
    }

    @Test
    fun replanAfterMissedDays_carriesOverWhatDoesNotFit() {
        val missed = listOf(
            DailyPlanItem("l1", "math", PlanItemKind.LEARN, 30, "درس"),
            DailyPlanItem("l2", "math", PlanItemKind.LEARN, 30, "درس"),
            DailyPlanItem("l3", "math", PlanItemKind.LEARN, 30, "درس")
        )
        val result = AdaptivePlanner.replanAfterMissedDays(missed, availableMinutes = 60, todayEpochDay = today)
        assertEquals(2, result.plan.items.size)
        assertEquals(1, result.carriedOver.size)
        assertEquals("l3", result.carriedOver.first().lessonId)
    }

    @Test
    fun planAdherence_nullWithoutPlan() {
        assertNull(AdaptivePlanner.planAdherence(null, emptySet()))
        val emptyPlan = DailyPlan(today, emptyList(), today)
        assertNull(AdaptivePlanner.planAdherence(emptyPlan, emptySet()))
    }

    @Test
    fun planAdherence_ratioIsCorrect() {
        val plan = DailyPlan(
            today,
            listOf(
                DailyPlanItem("l1", "math", PlanItemKind.LEARN, 20, "درس"),
                DailyPlanItem("l2", "math", PlanItemKind.LEARN, 20, "درس")
            ),
            today
        )
        assertEquals(0.5, AdaptivePlanner.planAdherence(plan, setOf("l1"))!!, 0.001)
        assertEquals(1.0, AdaptivePlanner.planAdherence(plan, setOf("l1", "l2"))!!, 0.001)
    }
}
