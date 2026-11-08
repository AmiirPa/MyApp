package com.example.data.adaptive

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class TutorEngineTest {

    @Test
    fun firstVariant_isTextSimple() {
        assertEquals(ExplanationVariant.TEXT_SIMPLE, TutorEngine.nextVariant(emptyList()))
    }

    @Test
    fun nextVariant_neverRepeatsPrevious() {
        var tried = emptyList<ExplanationVariant>()
        repeat(10) {
            val next = TutorEngine.nextVariant(tried)!!
            assertTrue(tried.isEmpty() || next != tried.last())
            tried = tried + next
        }
    }

    @Test
    fun nextVariant_skipsTriedOnes() {
        val next = TutorEngine.nextVariant(
            listOf(ExplanationVariant.TEXT_SIMPLE, ExplanationVariant.EVERYDAY_EXAMPLE)
        )
        assertEquals(ExplanationVariant.DIAGRAM, next)
    }

    @Test
    fun nextVariant_cyclesAfterAllTried() {
        val all = TutorEngine.DEFAULT_VARIANT_ORDER
        val next = TutorEngine.nextVariant(all)
        // همه امتحان شده‌اند؛ از اول می‌چرخد ولی نه همان آخری.
        assertTrue(next != null && next != all.last())
    }

    @Test
    fun nextVariant_emptyOrder_returnsNull() {
        assertNull(TutorEngine.nextVariant(emptyList(), order = emptyList()))
    }

    @Test
    fun needsDifferentApproach_afterTwoFailures() {
        assertTrue(TutorEngine.needsDifferentApproach(2, listOf(ExplanationVariant.TEXT_SIMPLE)))
        assertTrue(TutorEngine.needsDifferentApproach(1, listOf(ExplanationVariant.TEXT_SIMPLE, ExplanationVariant.DIAGRAM)))
        assertTrue(!TutorEngine.needsDifferentApproach(0, emptyList()))
        assertTrue(!TutorEngine.needsDifferentApproach(1, listOf(ExplanationVariant.TEXT_SIMPLE)))
    }

    @Test
    fun fifteenStages_mapHasFifteenEntries() {
        val stages = TutorEngine.mapFifteenStagesToLesson(lessonStepCount = 4, workedExampleCount = 2)
        assertEquals(15, stages.size)
        assertEquals(1, stages.first().first)
        assertEquals(15, stages.last().first)
    }
}
