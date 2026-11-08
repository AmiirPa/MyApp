package com.example.data.achievements

import com.example.data.adaptive.DailyPlan
import com.example.data.adaptive.DailyPlanItem
import com.example.data.adaptive.DiagnosticSession
import com.example.data.adaptive.ExamAttempt
import com.example.data.adaptive.PlanItemKind
import com.example.data.adaptive.ReviewCardState
import com.example.data.adaptive.TopicStatus
import com.example.data.focus.FocusSession
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * تست‌های خالص ارزیاب دستاوردها (§۱۰).
 *
 * همه‌چیز از شواهد ساخته می‌شود؛ هیچ Android و هیچ I/O اینجا نیست.
 * «امروز» در تست‌ها یک روز ثابت (۲۰۰۰۰) است تا رشته‌ها قطعی باشند.
 */
class AchievementEvaluatorTest {

    private val today = 20000L

    private fun lessons(n: Int): Set<String> = (1..n).map { "lesson-$it" }.toSet()

    private fun consecutiveDays(n: Int, endDay: Long = today): Set<Long> =
        (0 until n).map { endDay - it }.toSet()

    private fun exam(id: String = "exam-1") = ExamAttempt(
        id = id,
        subjectId = "farsi",
        title = "آزمون",
        startedAtEpochDay = today,
        totalSeconds = 0,
        questions = emptyList()
    )

    private fun diagnostic(topicId: String = "topic-1") = DiagnosticSession(
        topicId = topicId,
        subjectId = "farsi",
        askedQuestionIds = listOf("q1"),
        answers = emptyList(),
        concludedStatus = TopicStatus.NOT_STARTED,
        weakestLevel = null,
        recommendedLessonId = null,
        concludedAtEpochDay = today
    )

    private fun focusSession(seconds: Int, id: String = "focus-1") = FocusSession(
        id = id,
        dateEpochDay = today,
        startedAtEpochMillis = 0L,
        actualFocusSeconds = seconds,
        interruptions = 0,
        completed = seconds >= 1500,
        endedAtEpochMillis = seconds * 1000L
    )

    private fun reviewCard(totalReviews: Int, id: String = "card-1") = ReviewCardState(
        itemId = id,
        topicId = "topic-1",
        subjectId = "farsi",
        leitnerBox = 1,
        errorCount = 0,
        totalReviews = totalReviews,
        consecutiveCorrect = 0,
        lastReviewEpochDay = today,
        nextReviewEpochDay = today + 1
    )

    private fun plan(lessonIds: List<String>, day: Long = today) = DailyPlan(
        dateEpochDay = day,
        items = lessonIds.map {
            DailyPlanItem(
                lessonId = it,
                subjectId = "farsi",
                kind = PlanItemKind.LEARN,
                minutes = 20,
                reason = "تست"
            )
        },
        generatedAtEpochDay = day
    )

    private fun earnedIds(evaluation: AchievementEvaluation): Set<String> =
        evaluation.earned.map { it.definition.id }.toSet()

    @Test
    fun emptyEvidence_nothingEarned_nearestIsFirstLesson() {
        val evaluation = AchievementEvaluator.evaluate(AchievementEvidence(todayEpochDay = today))
        assertTrue(evaluation.earned.isEmpty())
        val nearest = evaluation.nearestUnearned
        assertTrue(nearest != null)
        assertEquals("first-lesson", nearest!!.definition.id)
        assertEquals(0, nearest.progress)
        assertEquals("0 از 1 درس", nearest.progressText())
    }

    @Test
    fun allDefinitions_uniqueIdsAndTargets() {
        val ids = AchievementEvaluator.ALL.map { it.id }
        assertEquals(10, AchievementEvaluator.ALL.size)
        assertEquals(ids.size, ids.toSet().size)
        assertTrue(AchievementEvaluator.ALL.all { it.target >= 1 })
    }

    @Test
    fun firstLesson_thresholdIsExactlyOne() {
        val none = AchievementEvaluator.evaluate(
            AchievementEvidence(todayEpochDay = today)
        )
        assertFalse("first-lesson" in earnedIds(none))

        val one = AchievementEvaluator.evaluate(
            AchievementEvidence(completedLessonIds = lessons(1), todayEpochDay = today)
        )
        assertTrue("first-lesson" in earnedIds(one))
    }

    @Test
    fun tenLessons_thresholdIsExactlyTen() {
        val nine = AchievementEvaluator.evaluate(
            AchievementEvidence(completedLessonIds = lessons(9), todayEpochDay = today)
        )
        assertFalse("ten-lessons" in earnedIds(nine))

        val ten = AchievementEvaluator.evaluate(
            AchievementEvidence(completedLessonIds = lessons(10), todayEpochDay = today)
        )
        assertTrue("ten-lessons" in earnedIds(ten))
        // ده درس، «اولین درس» را هم می‌گیرد.
        assertTrue("first-lesson" in earnedIds(ten))
    }

    @Test
    fun firstDiagnostic_thresholdIsExactlyOne() {
        val evaluation = AchievementEvaluator.evaluate(
            AchievementEvidence(diagnosticSessions = listOf(diagnostic()), todayEpochDay = today)
        )
        assertTrue("first-diagnostic" in earnedIds(evaluation))
    }

    @Test
    fun firstExam_thresholdIsExactlyOne() {
        val evaluation = AchievementEvaluator.evaluate(
            AchievementEvidence(examAttempts = listOf(exam()), todayEpochDay = today)
        )
        assertTrue("first-exam" in earnedIds(evaluation))
    }

    @Test
    fun firstFocus_needsRealFocusSeconds() {
        val zeroSeconds = AchievementEvaluator.evaluate(
            AchievementEvidence(focusSessions = listOf(focusSession(0)), todayEpochDay = today)
        )
        assertFalse("first-focus" in earnedIds(zeroSeconds))

        val real = AchievementEvaluator.evaluate(
            AchievementEvidence(focusSessions = listOf(focusSession(600)), todayEpochDay = today)
        )
        assertTrue("first-focus" in earnedIds(real))
    }

    @Test
    fun streak7_thresholdIsExactlySeven() {
        val six = AchievementEvaluator.evaluate(
            AchievementEvidence(completedStudyDays = consecutiveDays(6), todayEpochDay = today)
        )
        assertFalse("streak-7" in earnedIds(six))

        val seven = AchievementEvaluator.evaluate(
            AchievementEvidence(completedStudyDays = consecutiveDays(7), todayEpochDay = today)
        )
        assertTrue("streak-7" in earnedIds(seven))
        assertFalse("streak-30" in earnedIds(seven))
    }

    @Test
    fun streak30_thresholdIsExactlyThirty() {
        val thirty = AchievementEvaluator.evaluate(
            AchievementEvidence(completedStudyDays = consecutiveDays(30), todayEpochDay = today)
        )
        assertTrue("streak-7" in earnedIds(thirty))
        assertTrue("streak-30" in earnedIds(thirty))
    }

    @Test
    fun streak_gapBreaksTheRun() {
        // هفت روز پیاپی، بعد یک روز جاافتاده، بعد فقط امروز: رشته فعلی ۱ است.
        val days = consecutiveDays(7, endDay = today - 2) + today
        val evidence = AchievementEvidence(completedStudyDays = days, todayEpochDay = today)
        assertEquals(1, AchievementEvaluator.progressOf(AchievementKind.STREAK_7, evidence))
        val evaluation = AchievementEvaluator.evaluate(evidence)
        assertFalse("streak-7" in earnedIds(evaluation))
        // با پیشرفت ۱ از ۷، رشته ۷ نزدیک‌ترین نگرفته است.
        assertEquals("streak-7", evaluation.nearestUnearned!!.definition.id)
        assertEquals(1, evaluation.nearestUnearned!!.progress)
    }

    @Test
    fun streak_todayMissingButYesterdayPresentCounts() {
        // امروز هنوز درس نخوانده، ولی هفت روز تا دیروز پیاپی بوده است.
        val days = consecutiveDays(7, endDay = today - 1)
        val evaluation = AchievementEvaluator.evaluate(
            AchievementEvidence(completedStudyDays = days, todayEpochDay = today)
        )
        assertTrue("streak-7" in earnedIds(evaluation))
    }

    @Test
    fun reviews100_sumsAcrossCards() {
        val ninetyNine = AchievementEvaluator.evaluate(
            AchievementEvidence(
                reviewCards = listOf(reviewCard(60, "c1"), reviewCard(39, "c2")),
                todayEpochDay = today
            )
        )
        assertFalse("reviews-100" in earnedIds(ninetyNine))

        val hundred = AchievementEvaluator.evaluate(
            AchievementEvidence(
                reviewCards = listOf(reviewCard(60, "c1"), reviewCard(40, "c2")),
                todayEpochDay = today
            )
        )
        assertTrue("reviews-100" in earnedIds(hundred))
    }

    @Test
    fun firstPlanDone_needsNonEmptyPlanWithAllItemsCompleted() {
        val done = lessons(2)
        val emptyPlan = AchievementEvaluator.evaluate(
            AchievementEvidence(
                completedLessonIds = done,
                dailyPlans = listOf(plan(emptyList())),
                todayEpochDay = today
            )
        )
        assertFalse("first-plan-done" in earnedIds(emptyPlan))

        val partial = AchievementEvaluator.evaluate(
            AchievementEvidence(
                completedLessonIds = done,
                dailyPlans = listOf(plan(listOf("lesson-1", "lesson-99"))),
                todayEpochDay = today
            )
        )
        assertFalse("first-plan-done" in earnedIds(partial))

        val complete = AchievementEvaluator.evaluate(
            AchievementEvidence(
                completedLessonIds = done,
                dailyPlans = listOf(plan(listOf("lesson-1", "lesson-2"))),
                todayEpochDay = today
            )
        )
        assertTrue("first-plan-done" in earnedIds(complete))
    }

    @Test
    fun focus300_neverRoundsUp() {
        // ۲۹۹ دقیقه و ۵۹ ثانیه = ۲۹۹ دقیقه، نه ۳۰۰.
        val almost = AchievementEvaluator.evaluate(
            AchievementEvidence(
                focusSessions = listOf(focusSession(299 * 60 + 59)),
                todayEpochDay = today
            )
        )
        assertFalse("focus-300" in earnedIds(almost))

        val exact = AchievementEvaluator.evaluate(
            AchievementEvidence(
                focusSessions = listOf(focusSession(300 * 60)),
                todayEpochDay = today
            )
        )
        assertTrue("focus-300" in earnedIds(exact))
    }

    @Test
    fun focus300_sumsAcrossSessions() {
        val evidence = AchievementEvidence(
            focusSessions = listOf(
                focusSession(1500, "f1"),
                focusSession(1500, "f2")
            ),
            todayEpochDay = today
        )
        // دو جلسه ۲۵ دقیقه‌ای = ۵۰ دقیقه (نه ۳۰۰).
        assertEquals(50, AchievementEvaluator.progressOf(AchievementKind.FOCUS_300, evidence))
        assertFalse("focus-300" in earnedIds(AchievementEvaluator.evaluate(evidence)))
    }

    @Test
    fun derivedState_disappearsWhenEvidenceIsRemoved() {
        val withEvidence = AchievementEvaluator.evaluate(
            AchievementEvidence(completedLessonIds = lessons(10), todayEpochDay = today)
        )
        assertTrue("ten-lessons" in earnedIds(withEvidence))

        // همان ارزیابی با شواهد خالی: دستاورد می‌رود (ذخیره جداگانه نیست).
        val withoutEvidence = AchievementEvaluator.evaluate(
            AchievementEvidence(todayEpochDay = today)
        )
        assertFalse("ten-lessons" in earnedIds(withoutEvidence))
    }

    @Test
    fun nearestUnearned_picksHighestRatio() {
        // ۵ درس از ۱۰ (نسبت ۰٫۵) در برابر رشته ۳ از ۷ (نسبت ~۰٫۴۳):
        // نزدیک‌ترین باید «ده درس کامل» باشد.
        val evaluation = AchievementEvaluator.evaluate(
            AchievementEvidence(
                completedLessonIds = lessons(5),
                completedStudyDays = consecutiveDays(3),
                todayEpochDay = today
            )
        )
        val nearest = evaluation.nearestUnearned
        assertTrue(nearest != null)
        assertEquals("ten-lessons", nearest!!.definition.id)
        assertEquals("5 از 10 درس", nearest.progressText())
    }

    @Test
    fun nearestUnearned_tieGoesToEarlierDefinition() {
        // «اولین درس» و «ده درس کامل» هر دو ۰ از هدف‌اند (نسبت ۰)؛
        // «اولین درس» زودتر در فهرست است و باید نزدیک‌ترین باشد.
        // ولی «اولین تشخیص سطح» هم ۰ است؛ اولینِ همه «اولین درس» است.
        val evaluation = AchievementEvaluator.evaluate(
            AchievementEvidence(todayEpochDay = today)
        )
        assertEquals("first-lesson", evaluation.nearestUnearned!!.definition.id)
    }

    @Test
    fun nearestUnearned_nullWhenAllEarned() {
        val evidence = AchievementEvidence(
            completedLessonIds = lessons(10),
            completedStudyDays = consecutiveDays(30),
            examAttempts = listOf(exam()),
            diagnosticSessions = listOf(diagnostic()),
            focusSessions = listOf(focusSession(300 * 60)),
            reviewCards = listOf(reviewCard(100)),
            dailyPlans = listOf(plan(listOf("lesson-1"))),
            todayEpochDay = today
        )
        val evaluation = AchievementEvaluator.evaluate(evidence)
        assertEquals(10, evaluation.earned.size)
        assertNull(evaluation.nearestUnearned)
    }
}
