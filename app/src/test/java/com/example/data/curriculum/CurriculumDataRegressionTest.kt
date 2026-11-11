package com.example.data.curriculum

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/** Regression tests for fix/curriculum-data-bugs. */
class CurriculumDataRegressionTest {

    private fun lesson(id: String): ZeroLesson =
        FlashcardAndQuizBank.allLessons.first { it.id == id }

    @Test
    fun realMathBookLessons_areNotFiledUnderZeroTrack() {
        val expected = mapOf(
            "math_02_equations" to Grade.G10,
            "math_05d_sequences_arithmetic_deep" to Grade.G12,
            "math_05e_sequences_geometric_deep" to Grade.G12,
            "math_07_combinatorics_probability" to Grade.G12,
            "math_07_grade12_regression_time_series" to Grade.G11,
            "math_08_logic_propositions" to Grade.G11,
        )
        expected.forEach { (id, grade) ->
            assertEquals(id, grade, lesson(id).hierarchyGrade())
        }
    }

    @Test
    fun genuineMathZeroTrackLessons_stayInZeroTrack() {
        listOf(
            "math_000_bismillah_mastery",
            "math_00_elementary_school",
            "math_01_signs_fractions",
            "math_01b_powers_radicals_identities",
            "math_02b_sets_venn_intervals",
        ).forEach { id -> assertEquals(id, Grade.SUB_ZERO, lesson(id).hierarchyGrade()) }
    }

    @Test
    fun yearlyAllSubjectsView_usesOnlyRealQuestions() {
        val yearly = KonkoorPastExamsArchive.standaloneYearlyAllSubjectsPapers
        assertTrue(yearly.isNotEmpty())
        yearly.forEach { paper ->
            assertFalse(paper.id, paper.questions.any { it.id.contains("lesson_mc_") })
            assertFalse(paper.id, paper.questions.any { it.difficultyTag.contains("تمرین تکمیلی") })
            assertFalse(paper.id, paper.questions.any { it.difficultyTag.contains("شبیه‌ساز") })
        }
        val y1405 = yearly.first { it.year == 1405 }
        // دفترچه اول (۷۸) + دفترچه دوم (۸۰) مرداد ۱۴۰۵
        assertEquals(158, y1405.questions.size)
    }

    @Test
    fun paddedQuestions_areNotRelabeledAsThatYearsKonkoor() {
        val mock = KonkoorPastExamsArchive.getAllExamPapers().first { it.id == "exam_1404_tirmaah" }
        val padded = mock.questions.filter { it.difficultyTag.startsWith("تمرین تکمیلی") }
        assertTrue(padded.isNotEmpty())
        padded.forEach { assertFalse(it.difficultyTag, it.difficultyTag.startsWith("کنکور ۱۴۰۴")) }
    }
}
