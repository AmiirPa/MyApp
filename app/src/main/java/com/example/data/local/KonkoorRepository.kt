package com.example.data.local

import kotlinx.coroutines.flow.Flow

class KonkoorRepository(private val dao: KonkoorDao) {
    val allLessonProgress: Flow<List<LessonProgressEntity>> = dao.getAllLessonProgress()
    val allFlashcardProgress: Flow<List<FlashcardProgressEntity>> = dao.getAllFlashcardProgress()
    val allStudyTasks: Flow<List<StudyTaskEntity>> = dao.getAllStudyTasks()
    val allExamResults: Flow<List<ExamResultEntity>> = dao.getAllExamResults()

    suspend fun saveLessonProgress(
        lessonId: String,
        subjectId: String,
        isCompleted: Boolean,
        isBookmarked: Boolean,
        userNote: String,
        masteryPercent: Int
    ) {
        dao.upsertLessonProgress(
            LessonProgressEntity(
                lessonId = lessonId,
                subjectId = subjectId,
                isCompleted = isCompleted,
                isBookmarked = isBookmarked,
                userNote = userNote,
                masteryPercent = masteryPercent,
                updatedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun updateFlashcardBox(cardId: String, subjectId: String, newBox: Int, currentReviews: Int) {
        dao.upsertFlashcardProgress(
            FlashcardProgressEntity(
                cardId = cardId,
                subjectId = subjectId,
                leitnerBox = newBox.coerceIn(1, 5),
                reviewCount = currentReviews + 1,
                lastReviewedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun addStudyTask(title: String, subjectId: String, stageTag: String, durationMinutes: Int) {
        dao.insertStudyTask(
            StudyTaskEntity(
                title = title,
                subjectId = subjectId,
                stageTag = stageTag,
                durationMinutes = durationMinutes,
                isCompleted = false
            )
        )
    }

    suspend fun toggleStudyTask(task: StudyTaskEntity) {
        dao.insertStudyTask(task.copy(isCompleted = !task.isCompleted))
    }

    suspend fun deleteStudyTask(id: Int) {
        dao.deleteStudyTask(id)
    }

    suspend fun recordExamResult(
        subjectId: String,
        subjectTitle: String,
        correctCount: Int,
        totalCount: Int
    ) {
        val percent = if (totalCount > 0) ((correctCount * 100f) / totalCount).toInt() else 0
        dao.insertExamResult(
            ExamResultEntity(
                subjectId = subjectId,
                subjectTitle = subjectTitle,
                correctCount = correctCount,
                totalCount = totalCount,
                scorePercent = percent
            )
        )
    }

    suspend fun seedInitialRoadmapIfNeeded() {
        if (dao.getStudyTaskCount() == 0) {
            val defaultTasks = listOf(
                StudyTaskEntity(
                    title = "ریاضی از منفی صفر: تسلط بر جدول علامت‌ها و جمع و تفریق به روش پول و بدهی",
                    subjectId = "math",
                    stageTag = "گام ۱: شروع از منفی صفر",
                    durationMinutes = 35,
                    isCompleted = false
                ),
                StudyTaskEntity(
                    title = "عروض از منفی صفر: تمرین بخش‌کردن ۱۰ کلمه و تشخیص هجای کوتاه (U) و بلند (–)",
                    subjectId = "arooz",
                    stageTag = "گام ۱: شروع از منفی صفر",
                    durationMinutes = 30,
                    isCompleted = false
                ),
                StudyTaskEntity(
                    title = "عربی از منفی صفر: حفظ جدول ۱۴ ضمیر و نشانه‌های اسم و فعل بدون ترجمه",
                    subjectId = "arabic",
                    stageTag = "گام ۱: شروع از منفی صفر",
                    durationMinutes = 40,
                    isCompleted = false
                ),
                StudyTaskEntity(
                    title = "زبان انگلیسی از منفی صفر: فرمول طلایی جمله‌سازی (فاعل + فعل + مفعول) و افعال To Be",
                    subjectId = "english",
                    stageTag = "گام ۱: شروع از منفی صفر",
                    durationMinutes = 30,
                    isCompleted = false
                ),
                StudyTaskEntity(
                    title = "علوم و فنون: تفکیک تصویری ۴ آرایه تشبیه، استعاره، مجاز و کنایه",
                    subjectId = "fonoon",
                    stageTag = "گام ۲: تثبیت پایه",
                    durationMinutes = 45,
                    isCompleted = false
                ),
                StudyTaskEntity(
                    title = "اختصاصی فرهنگیان ۱۴۰۶: مطالعه فصل اول کتاب مهارت معلمی استاد قرائتی + ۱۰ تست",
                    subjectId = "farhangian",
                    stageTag = "ویژه فرهنگیان ۱۴۰۶",
                    durationMinutes = 50,
                    isCompleted = false
                ),
                StudyTaskEntity(
                    title = "کارگاه تعاملی تقطیع عروض: تقطیع ۳ بیت معروف کنکور در آزمایشگاه برنامه",
                    subjectId = "arooz",
                    stageTag = "گام ۲: تثبیت پایه",
                    durationMinutes = 25,
                    isCompleted = false
                ),
                StudyTaskEntity(
                    title = "هوش و استعداد معلمی: حل ۵ تیپ سوال استدلال منطقی و تحلیل جدول",
                    subjectId = "farhangian",
                    stageTag = "ویژه فرهنگیان ۱۴۰۶",
                    durationMinutes = 40,
                    isCompleted = false
                )
            )
            dao.insertStudyTasks(defaultTasks)
        }
    }
}
