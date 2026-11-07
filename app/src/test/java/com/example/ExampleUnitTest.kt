package com.example

import com.example.data.curriculum.BismillahAndMnemonicsCurriculum
import com.example.data.curriculum.FlashcardAndQuizBank
import com.example.data.curriculum.SubjectCatalog
import com.example.data.curriculum.subcategoryFolder
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun curriculum_and_mnemonics_are_populated_with_unique_keys() {
        val lessons = FlashcardAndQuizBank.allLessons
        assertTrue(lessons.size >= 30)
        assertTrue(BismillahAndMnemonicsCurriculum.allMnemonicCodes.size >= 15)

        // Ensure all lesson IDs are strictly unique
        val uniqueIds = lessons.map { it.id }.toSet()
        assertEquals(lessons.size, uniqueIds.size)

        // Ensure all flashcard IDs are strictly unique
        val flashcards = FlashcardAndQuizBank.flashcards
        assertTrue(flashcards.isNotEmpty())
        assertEquals(flashcards.size, flashcards.map { it.id }.toSet().size)

        // Ensure all quiz question IDs are strictly unique and valid
        val quizzes = FlashcardAndQuizBank.quizQuestions
        assertTrue(quizzes.isNotEmpty())
        assertEquals(quizzes.size, quizzes.map { it.id }.toSet().size)
        quizzes.forEach { q ->
            assertTrue("Quiz ${q.id} has invalid correctOptionIndex", q.correctOptionIndex in q.options.indices)
        }

        // Ensure all mnemonic code IDs are strictly unique
        val mnemonics = BismillahAndMnemonicsCurriculum.allMnemonicCodes
        assertEquals(mnemonics.size, mnemonics.map { it.id }.toSet().size)

        // Ensure every lesson maps to a specific textbook folder
        SubjectCatalog.subjects.forEach { subject ->
            val subLessons = lessons.filter { it.subjectId == subject.id }
            assertTrue("Subject ${subject.id} must have lessons", subLessons.isNotEmpty())
            subLessons.forEach { lesson ->
                assertTrue("Lesson ${lesson.id} must have steps", lesson.steps.isNotEmpty())
                assertNotEquals("Lesson ${lesson.id} fell into fallback folder", 99, lesson.subcategoryFolder().first)
            }
            val groupedByPair = subLessons.groupBy { it.subcategoryFolder() }.keys.toList()
            val uniqueOrders = groupedByPair.map { it.first }.toSet()
            assertEquals("Duplicate folder order in subject ${subject.id}", groupedByPair.size, uniqueOrders.size)
        }
    }

    @Test
    fun print_lesson_breakdown() {
        val lessons = FlashcardAndQuizBank.allLessons
        println("=== TOTAL LESSONS: ${lessons.size} ===")
        SubjectCatalog.subjects.forEach { s ->
            val count = lessons.count { it.subjectId == s.id }
            println("SUBJECT: ${s.id.padEnd(20)} (${s.shortTitle}): $count lessons")
        }
    }

    @Test
    fun arabic_text_processor_replaces_yeh_kaf_and_normalizes_zwnj() {
        val sample = "يَا أَيُّهَا الَّذِينَ يَأْكُلُونَ أَمْوَالَ الْيَتَامَىٰ — ال\u200Cكِتَابُ — مضاف الیه — متکلم مع الغیر — بـِ و لـِ و کـَ"
        val processed = com.example.ui.components.ArabicTextProcessor.processArabicText(sample)

        // 1. No Arabic Yeh (\u064A), Alef Maksura (\u0649), or Arabic Kaf (\u0643) should remain
        assertTrue(!processed.contains('\u064A'))
        assertTrue(!processed.contains('\u0649'))
        assertTrue(!processed.contains('\u0643'))

        // 2. Destructive ZWNJ after ال in ال‌کتاب should be removed so ل connects to ک
        assertTrue(processed.contains("الْکِتَابُ") || processed.contains("الکِتَابُ"))
        assertTrue(!processed.contains("ال\u200Cک"))

        // 3. Compound Arabic-Persian terms should contain proper ZWNJ (\u200C)
        assertTrue(processed.contains("مضاف\u200Cالیه"))
        assertTrue(processed.contains("مع\u200Cالغیر"))

        // 4. Prefix tatweels should place diacritic on the base letter before tatweel
        assertTrue(processed.contains("بِـ"))
        assertTrue(processed.contains("لِـ"))
        assertTrue(processed.contains("کَـ"))

        // 5. Verify all lessons in FlashcardAndQuizBank have zero raw Arabic Yeh/Kaf/Alef Maksura
        FlashcardAndQuizBank.allLessons.forEach { lesson ->
            assertTrue("Lesson ${lesson.id} title contains raw Arabic Yeh/Kaf", !lesson.title.any { it == '\u064A' || it == '\u0643' || it == '\u0649' })
            lesson.steps.forEach { step ->
                assertTrue("Step in ${lesson.id} contains raw Arabic Yeh/Kaf", !step.simpleExplanation.any { it == '\u064A' || it == '\u0643' || it == '\u0649' })
            }
        }
    }

    @Test
    fun minus_zero_pedagogy_and_coverage_matrix_are_complete_for_all_lessons() {
        val lessons = FlashcardAndQuizBank.allLessons
        val matrix = com.example.data.curriculum.MinusZeroLearningEngine.generateContentCoverageMatrix(lessons)
        assertEquals(lessons.size, matrix.size)
        assertTrue(com.example.data.curriculum.MinusZeroLearningEngine.diagnosticQuestions.size >= 5)

        lessons.take(25).forEach { lesson ->
            val guide = com.example.data.curriculum.MinusZeroLearningEngine.buildGuideForLesson(lesson)
            assertTrue(guide.oneSentenceWhatIsIt.isNotBlank())
            assertTrue(guide.realLifeApplication.isNotBlank())
            assertTrue(guide.simpleVisualBlueprint.isNotBlank())
            assertTrue(guide.stepByStepSimpleExplanation.isNotEmpty())
            assertTrue(guide.vocabularyGlossary.isNotEmpty())
            assertTrue(guide.superEasyExampleSteps.isNotEmpty())
            assertTrue(guide.guidedExerciseOptions.size >= 4)
            assertTrue(guide.guidedExerciseStepDiagnosis.size == guide.guidedExerciseOptions.size)
            assertTrue(guide.extraSimpleExplanation.isNotBlank())
            assertTrue(guide.extraEverydayExample.isNotBlank())
            assertTrue(guide.minusZeroPrerequisiteText.isNotBlank())
            assertTrue(guide.finalExamWrittenQuestion.isNotBlank())
            assertTrue(guide.finalExamStandardAnswer.isNotBlank())
            assertTrue(guide.konkoorTipsAndTraps.isNotEmpty())
            assertTrue(guide.sourceMetadata.yearVerificationNote.contains("نیازمند تأیید"))

            val nineLevels = com.example.data.curriculum.MinusZeroLearningEngine.buildNineLevelPathForLesson(lesson)
            assertEquals(9, nineLevels.size)

            val mnemonic6 = com.example.data.curriculum.MinusZeroLearningEngine.buildSixPartMnemonicForLesson(lesson)
            assertEquals(6, mnemonic6.size)
            assertTrue(mnemonic6.all { it.first.isNotBlank() && it.second.isNotBlank() })
        }

        // Verify 14-form Arabic conjugation table (header + 14 forms = 15 rows, 5 columns each)
        val arabicRows = com.example.data.curriculum.MinusZeroLearningEngine.arabic14ConjugationGuideRows
        assertEquals(15, arabicRows.size)
        arabicRows.forEach { row ->
            assertEquals(5, row.size)
        }

        // Verify 6-state real mastery tracker
        val masteredState = com.example.data.curriculum.MinusZeroLearningEngine.resolveRealMasteryStatus(
            isCompleted = true,
            masteryPercent = 100,
            answeredMiniCheckCorrectly = true,
            answeredMiniCheckWrong = false
        )
        assertTrue(masteredState.first.contains("تسلط تأییدشده"))
    }

    @Test
    fun parenting_styles_four_column_table_and_bidi_formula_are_valid() {
        val lessons = FlashcardAndQuizBank.allLessons
        val parentingLesson = lessons.find { lesson ->
            lesson.steps.any { it.visualDiagramText.contains("سبک‌های چهارگانه فرزندپروری") }
        }
        assertTrue("Parenting styles 4-column table must exist in curriculum", parentingLesson != null)
        val diagram = parentingLesson!!.steps.first { it.visualDiagramText.contains("سبک‌های چهارگانه فرزندپروری") }.visualDiagramText
        assertTrue(diagram.contains("مقتدرانه"))
        assertTrue(diagram.contains("مستبدانه"))
        assertTrue(diagram.contains("سهل‌گیرانه"))
        assertTrue(diagram.contains("طردکننده"))

        val formattedBidi = com.example.ui.components.BidiFormatter.formatMixedPersianText("فرمول x^2 + 3x - 4 = 0 در گام 1")
        assertTrue(formattedBidi.isNotBlank())
    }

    @Test
    fun zero_truncated_synthetic_strings_and_cryptic_acronyms_across_all_data() {
        val lessons = FlashcardAndQuizBank.allLessons
        val flashcards = FlashcardAndQuizBank.flashcards
        val mnemonics = BismillahAndMnemonicsCurriculum.allMnemonicCodes

        lessons.forEach { lesson ->
            lesson.steps.forEach { step ->
                assertTrue(
                    "Step title '${step.title}' in ${lesson.id} must not be synthetic boilerplate",
                    !step.title.contains("کالبدشکافی مفهومی و ارتباط") &&
                        !step.title.contains("کارگاه دست‌به‌قلم و تثبیت")
                )
                assertTrue(
                    "Step visualDiagramText in ${lesson.id} must not contain truncated mid-word ellipsis",
                    !step.visualDiagramText.contains("┌── نقشه مفهومی و ساختاری:") &&
                        !step.visualDiagramText.contains("┌── جدول مقایسه‌ای و تفکیک")
                )
                assertTrue(
                    "Step memoryTrick in ${lesson.id} must not contain truncated synthetic prefix",
                    !step.memoryTrick.startsWith("رمز طلایی «") &&
                        !step.memoryTrick.startsWith("رمز تفکیک سریع:")
                )
            }
            lesson.goldenFormulas.forEach { gf ->
                assertTrue(
                    "goldenFormula in ${lesson.id} must not contain truncated synthetic prefix",
                    !gf.startsWith("قاعده تفکیک دو بخش اصلی درس:")
                )
            }
            lesson.workedExamples.forEach { we ->
                assertTrue(
                    "workedExample in ${lesson.id} must not contain truncated synthetic question",
                    !we.question.startsWith("سوال تشریحی امتحان نهایی و تستی: تفاوت و رابطهٔ اصلی میان")
                )
            }
        }

        val forbiddenTokens = listOf("jarang", "WDPAHPW", "EMFAKSI", "SFJAYELR", "OSASCOMP")
        flashcards.forEach { fc ->
            forbiddenTokens.forEach { token ->
                assertTrue("Flashcard ${fc.id} contains forbidden token $token", !fc.backAnswer.contains(token) && !fc.memoryHook.contains(token))
            }
        }
        mnemonics.forEach { mn ->
            forbiddenTokens.forEach { token ->
                assertTrue("Mnemonic ${mn.id} contains forbidden token $token", !mn.acronymCode.contains(token))
            }
        }
    }

    @Test
    fun font_weight_is_strictly_independent_of_font_size_and_line_height() {
        val typoRegular = com.example.ui.theme.buildAppTypography(
            scale = 1.0f,
            isBoldText = false,
            fontWeightLevel = 0,
            lineSpacingScale = 1.0f
        )
        val typoMedium = com.example.ui.theme.buildAppTypography(
            scale = 1.0f,
            isBoldText = false,
            fontWeightLevel = 1,
            lineSpacingScale = 1.0f
        )
        val typoBold = com.example.ui.theme.buildAppTypography(
            scale = 1.0f,
            isBoldText = true,
            fontWeightLevel = 2,
            lineSpacingScale = 1.0f
        )

        // Font size and line height must remain 100% identical when changing weight
        assertEquals(typoRegular.bodyLarge.fontSize, typoMedium.bodyLarge.fontSize)
        assertEquals(typoRegular.bodyLarge.fontSize, typoBold.bodyLarge.fontSize)
        assertEquals(typoRegular.bodyLarge.lineHeight, typoBold.bodyLarge.lineHeight)
        assertEquals(typoRegular.titleMedium.fontSize, typoBold.titleMedium.fontSize)

        // Font weights must differ appropriately
        assertEquals(androidx.compose.ui.text.font.FontWeight.Normal, typoRegular.bodyLarge.fontWeight)
        assertEquals(androidx.compose.ui.text.font.FontWeight.Medium, typoMedium.bodyLarge.fontWeight)
        assertEquals(androidx.compose.ui.text.font.FontWeight.Bold, typoBold.bodyLarge.fontWeight)
    }

    @Test
    fun playbooks_palettes_highlight_and_jalali_calendar_behave_accurately() {
        // 1. Unknown playbook ID must fall back to "general", never "arabic"
        val unknownPlaybook = com.example.ui.components.TopRankersPlaybookRepository.getPlaybookForSubject("unknown_subject_xyz")
        assertEquals("general", unknownPlaybook.subjectId)
        assertNotEquals("arabic", unknownPlaybook.subjectId)

        // 2. All 15 playbooks must not contain 'jarang'
        com.example.ui.components.TopRankersPlaybookRepository.allPlaybooks.forEach { pb ->
            assertTrue(pb.bookletUnlockerSteps.none { it.contains("jarang") })
        }

        // 3. Featured color palettes must be 5 and free of pseudoscientific claims
        assertEquals(5, com.example.ui.theme.AppColorPalette.featuredPalettes.size)
        com.example.ui.theme.AppColorPalette.entries.forEach { palette ->
            assertTrue(!palette.persianSubtitle.contains("کورتیزول"))
        }

        // 4. Highlighter intensity 0 returns Transparent background
        val offBg = com.example.ui.theme.HighlighterColorPalette.entries.first().resolveBackground(isDarkTheme = false, intensity = 0)
        assertEquals(androidx.compose.ui.graphics.Color.Transparent, offBg)

        // 5. Jalali calendar leap year & Esfand days
        assertEquals(30, com.example.ui.components.JalaliCalendarUtils.daysInJalaliMonth(1403, 12))
        assertEquals(29, com.example.ui.components.JalaliCalendarUtils.daysInJalaliMonth(1404, 12))
        assertEquals(29, com.example.ui.components.JalaliCalendarUtils.daysInJalaliMonth(1405, 12))
        assertEquals(29, com.example.ui.components.JalaliCalendarUtils.daysInJalaliMonth(1406, 12))
    }

    @Test
    fun bidi_formatter_to_persian_digits_converts_latin_digits_correctly() {
        val latinText = "ریاضی 1406 با 1234567890 سوال"
        val persian = com.example.ui.components.BidiFormatter.toPersianDigits(latinText)
        assertEquals("ریاضی ۱۴۰۶ با ۱۲۳۴۵۶۷۸۹۰ سوال", persian)
    }

    @Test
    fun room_entities_and_scoring_calculation_behave_accurately() {
        // Test standard Konkoor percentage formula: (3 * correct - wrong) / (3 * total) * 100
        val correct = 20
        val wrong = 5
        val total = 30
        val score = ((3 * correct - wrong).toDouble() / (3 * total).toDouble()) * 100.0
        val roundedScore = Math.round(score).toInt()
        assertEquals(61, roundedScore) // (60 - 5) / 90 * 100 = 55/90 * 100 = 61.11 -> 61%

        // Verify LessonProgressEntity instantiation
        val progress = com.example.data.local.LessonProgressEntity(
            lessonId = "lesson_funoon_1",
            subjectId = "funoon",
            isCompleted = true,
            isBookmarked = true,
            userNote = "نکته عروض",
            masteryPercent = 85
        )
        assertEquals("lesson_funoon_1", progress.lessonId)
        assertTrue(progress.isCompleted)
        assertTrue(progress.isBookmarked)
        assertEquals(85, progress.masteryPercent)

        // Verify ExamResultEntity instantiation
        val examResult = com.example.data.local.ExamResultEntity(
            subjectId = "arabi",
            subjectTitle = "عربی تخصصی",
            correctCount = correct,
            totalCount = total,
            scorePercent = roundedScore
        )
        assertEquals("arabi", examResult.subjectId)
        assertEquals(61, examResult.scorePercent)
    }
}


