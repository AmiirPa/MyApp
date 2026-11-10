package com.example

import com.example.data.curriculum.FlashcardAndQuizBank
import com.example.data.curriculum.SubjectCatalog
import com.example.data.curriculum.isLogicLesson
import com.example.data.curriculum.normalizedSubjectId
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Regression tests for the unknown-subject fallback in
 * SubjectCatalog.getSubjectById: an id with no catalog entry must resolve to
 * the explicit "unknown" meta — never silently to Math (subjects.first()).
 */
class SubjectCatalogFallbackTest {

    @Test
    fun unknownId_returnsExplicitUnknownMeta_notMath() {
        val meta = SubjectCatalog.getSubjectById("no_such_subject")
        assertEquals("unknown", meta.id)
        assertEquals("رشته ناشناخته", meta.shortTitle)
        assertNotEquals("math", meta.id)
    }

    @Test
    fun unknownMeta_isNotPartOfTheBrowsableSubjectList() {
        // The fallback must not leak into the subject grid / selectors.
        assertFalse(SubjectCatalog.subjects.any { it.id == "unknown" })
        assertTrue(SubjectCatalog.subjects.isNotEmpty())
    }

    @Test
    fun allFilterValue_isNotASubject() {
        // "all" is a filter value, not a subject id: callers must guard it
        // themselves (they do). It must not resolve to Math either.
        val meta = SubjectCatalog.getSubjectById("all")
        assertEquals("unknown", meta.id)
    }

    @Test
    fun aroozLessons_resolveToFonoonCatalogEntry() {
        // Arooz (prosody) merged into fonoon (19 Aban 1405): prosody lessons
        // now normalize to "fonoon", and the catalog has the real "fonoon"
        // entry, so those lessons must show علوم و فنون ادبی — not Math and not "unknown".
        val meta = SubjectCatalog.getSubjectById("fonoon")
        assertEquals("fonoon", meta.id)
        val aroozLesson = FlashcardAndQuizBank.allLessons.firstOrNull { it.id.startsWith("arooz") || it.id.startsWith("arozero") }
        assertTrue(aroozLesson != null)
        assertEquals("fonoon", aroozLesson!!.normalizedSubjectId())
    }

    @Test
    fun logicLessons_resolveToPhilosophyCatalogEntry() {
        // Logic merged into philosophy (19 Aban 1405): logic lessons now
        // normalize to "philosophy" (فلسفه و منطق).
        val meta = SubjectCatalog.getSubjectById("philosophy")
        assertEquals("philosophy", meta.id)
        val logicLesson = FlashcardAndQuizBank.allLessons.firstOrNull { it.isLogicLesson() }
        assertTrue(logicLesson != null)
        assertEquals("philosophy", logicLesson!!.normalizedSubjectId())
    }

    @Test
    fun legacyCompositeIds_stillMapToTheirRealSubject() {
        assertEquals("history", SubjectCatalog.getSubjectById("history_geo").id)
        assertEquals("sociology", SubjectCatalog.getSubjectById("social_psych_econ").id)
    }

    @Test
    fun knownIds_resolveToThemselves() {
        SubjectCatalog.subjects.forEach { subject ->
            assertEquals(subject.id, SubjectCatalog.getSubjectById(subject.id).id)
        }
        assertEquals("math", SubjectCatalog.getSubjectById("math").id)
        assertEquals("fonoon", SubjectCatalog.getSubjectById("fonoon").id)
    }
}
