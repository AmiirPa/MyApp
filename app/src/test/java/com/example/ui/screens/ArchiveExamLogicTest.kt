package com.example.ui.screens

import com.example.data.curriculum.QuizQuestionItem
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Unit tests for the pure archive-exam helpers in PracticeAndPlannerScreen.kt:
 * - exam-result counting (problem 1: the stored result must match the report),
 * - answer-reveal rule (problem 2: simulation hides the correct answer),
 * - timer initial value (problem 3: hoisted timer keeps its remaining time).
 *
 * The Compose wiring itself (remember keys, the submit LaunchedEffect, the
 * neutral option styling) is verified by code inspection + grep in the report
 * and needs a device/emulator run for a live check; see the task report.
 */
class ArchiveExamLogicTest {

    private fun question(id: String, correctOptionIndex: Int) = QuizQuestionItem(
        id = id,
        subjectId = "math",
        difficultyTag = "متوسط",
        questionText = "سؤال $id",
        options = listOf("الف", "ب", "ج", "د"),
        correctOptionIndex = correctOptionIndex,
        zeroLevelSolution = "راه‌حل",
        shortcutTechnique = "شگرد"
    )

    // --- problem 1: countArchiveExamCorrect ---

    @Test
    fun countCorrect_allCorrect_countsAll() {
        val qs = listOf(question("q1", 0), question("q2", 2))
        assertEquals(2, countArchiveExamCorrect(qs, mapOf("q1" to 0, "q2" to 2)))
    }

    @Test
    fun countCorrect_wrongAndUnanswered_doNotCount() {
        val qs = listOf(question("q1", 0), question("q2", 2), question("q3", 1))
        // q1 answered wrong, q2 answered right, q3 never answered (absent).
        assertEquals(1, countArchiveExamCorrect(qs, mapOf("q1" to 3, "q2" to 2)))
    }

    @Test
    fun countCorrect_noAnswers_isZero() {
        val qs = listOf(question("q1", 0), question("q2", 2))
        assertEquals(0, countArchiveExamCorrect(qs, emptyMap()))
    }

    @Test
    fun countCorrect_emptyQuestions_isZero() {
        assertEquals(0, countArchiveExamCorrect(emptyList(), mapOf("q1" to 0)))
    }

    // --- problem 2: isArchiveAnswerRevealAllowed ---

    @Test
    fun reveal_simulationWhileRunning_isHidden() {
        assertFalse(isArchiveAnswerRevealAllowed(examMode = 1, isExamFinished = false))
    }

    @Test
    fun reveal_simulationAfterSubmit_isAllowed() {
        assertTrue(isArchiveAnswerRevealAllowed(examMode = 1, isExamFinished = true))
    }

    @Test
    fun reveal_practiceMode_isAllowed() {
        assertTrue(isArchiveAnswerRevealAllowed(examMode = 0, isExamFinished = false))
        assertTrue(isArchiveAnswerRevealAllowed(examMode = 0, isExamFinished = true))
    }

    @Test
    fun reveal_fullListAnswerKey_isAllowed() {
        assertTrue(isArchiveAnswerRevealAllowed(examMode = 2, isExamFinished = false))
        assertTrue(isArchiveAnswerRevealAllowed(examMode = 2, isExamFinished = true))
    }

    // --- problem 3: initialArchiveExamRemainingSeconds ---

    @Test
    fun timerInit_usesPaperMinutes() {
        assertEquals(150 * 60, initialArchiveExamRemainingSeconds(150))
        assertEquals(85 * 60, initialArchiveExamRemainingSeconds(85))
    }

    @Test
    fun timerInit_unknownMinutes_fallsBackTo85() {
        assertEquals(85 * 60, initialArchiveExamRemainingSeconds(null))
    }
}
