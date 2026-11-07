package com.example.data.local

import android.content.Context
import com.example.data.firebase.CloudExamResult
import com.example.data.firebase.CloudFlashcardProgress
import com.example.data.firebase.CloudLessonProgress
import com.example.data.firebase.CloudStudyTask
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID

class LocalKonkoorStore(context: Context) {
    private val prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val _lessonProgress = MutableStateFlow(loadLessons())
    val lessonProgress: StateFlow<List<CloudLessonProgress>> = _lessonProgress.asStateFlow()

    private val _flashcardProgress = MutableStateFlow(loadFlashcards())
    val flashcardProgress: StateFlow<List<CloudFlashcardProgress>> = _flashcardProgress.asStateFlow()

    private val _studyTasks = MutableStateFlow(loadTasks())
    val studyTasks: StateFlow<List<CloudStudyTask>> = _studyTasks.asStateFlow()

    private val _examResults = MutableStateFlow(loadExams())
    val examResults: StateFlow<List<CloudExamResult>> = _examResults.asStateFlow()

    init {
        if (prefs.getBoolean(KEY_SEEDED, false).not() && _studyTasks.value.isEmpty()) {
            seedDefaultTasks()
            prefs.edit().putBoolean(KEY_SEEDED, true).apply()
        }
    }

    private fun seedDefaultTasks() {
        val initial = listOf(
            CloudStudyTask(
                id = "local_task_1",
                userId = LOCAL_USER_ID,
                title = "مطالعه درس ۰ ریاضی (جدول ضرب با مکمل ۱۰ و تقسیم پیتزایی)",
                subjectId = "math",
                stageTag = "پایه منفی صفر",
                durationMinutes = 30,
                isCompleted = false,
                createdAt = Timestamp.now()
            ),
            CloudStudyTask(
                id = "local_task_2",
                userId = LOCAL_USER_ID,
                title = "تمرین جدول ۱۴ صیغه عربی (ماضی و مضارع)",
                subjectId = "arabic",
                stageTag = "پایه منفی صفر",
                durationMinutes = 25,
                isCompleted = false,
                createdAt = Timestamp.now()
            ),
            CloudStudyTask(
                id = "local_task_3",
                userId = LOCAL_USER_ID,
                title = "تمرین تقطیع هجایی و تشخیص بحر هزج و رمل در عروض",
                subjectId = "arooz",
                stageTag = "تثبیت و تست",
                durationMinutes = 25,
                isCompleted = false,
                createdAt = Timestamp.now()
            ),
            CloudStudyTask(
                id = "local_task_4",
                userId = LOCAL_USER_ID,
                title = "مرور درس ۱ منطق دهم (جعبه کادو و کلیات خمس)",
                subjectId = "philosophy",
                stageTag = "پایه منفی صفر",
                durationMinutes = 30,
                isCompleted = false,
                createdAt = Timestamp.now()
            ),
            CloudStudyTask(
                id = "local_task_5",
                userId = LOCAL_USER_ID,
                title = "مطالعه فصل ۱ مهارت معلمی استاد قرائتی (ویژه فرهنگیان)",
                subjectId = "farhangian",
                stageTag = "دفترچه فرهنگیان",
                durationMinutes = 35,
                isCompleted = false,
                createdAt = Timestamp.now()
            )
        )
        _studyTasks.value = initial
        persistTasks(initial)
    }

    fun saveLessonProgress(
        lessonId: String,
        subjectId: String,
        isCompleted: Boolean,
        isBookmarked: Boolean,
        userNote: String,
        masteryPercent: Int
    ) {
        val current = _lessonProgress.value.toMutableList()
        val index = current.indexOfFirst { it.lessonId == lessonId }
        val updatedItem = CloudLessonProgress(
            id = lessonId,
            userId = LOCAL_USER_ID,
            lessonId = lessonId,
            subjectId = subjectId,
            isCompleted = isCompleted,
            isBookmarked = isBookmarked,
            userNote = userNote,
            masteryPercent = masteryPercent,
            updatedAt = Timestamp.now()
        )
        if (index >= 0) {
            current[index] = updatedItem
        } else {
            current.add(updatedItem)
        }
        _lessonProgress.value = current
        persistLessons(current)
    }

    fun updateFlashcardBox(
        cardId: String,
        subjectId: String,
        newBox: Int,
        currentReviews: Int
    ) {
        val current = _flashcardProgress.value.toMutableList()
        val index = current.indexOfFirst { it.cardId == cardId }
        val updatedItem = CloudFlashcardProgress(
            id = cardId,
            userId = LOCAL_USER_ID,
            cardId = cardId,
            subjectId = subjectId,
            leitnerBox = newBox.coerceIn(1, 5),
            reviewCount = currentReviews + 1,
            updatedAt = Timestamp.now()
        )
        if (index >= 0) {
            current[index] = updatedItem
        } else {
            current.add(updatedItem)
        }
        _flashcardProgress.value = current
        persistFlashcards(current)
    }

    fun createStudyTask(
        title: String,
        subjectId: String,
        stageTag: String,
        durationMinutes: Int
    ) {
        val current = _studyTasks.value.toMutableList()
        current.add(
            0,
            CloudStudyTask(
                id = UUID.randomUUID().toString(),
                userId = LOCAL_USER_ID,
                title = title,
                subjectId = subjectId,
                stageTag = stageTag,
                durationMinutes = durationMinutes,
                isCompleted = false,
                createdAt = Timestamp.now()
            )
        )
        _studyTasks.value = current
        persistTasks(current)
    }

    fun toggleStudyTask(taskId: String, currentCompleted: Boolean) {
        val current = _studyTasks.value.map {
            if (it.id == taskId) it.copy(isCompleted = !currentCompleted) else it
        }
        _studyTasks.value = current
        persistTasks(current)
    }

    fun deleteStudyTask(taskId: String) {
        val current = _studyTasks.value.filterNot { it.id == taskId }
        _studyTasks.value = current
        persistTasks(current)
    }

    fun recordExamResult(
        subjectId: String,
        subjectTitle: String,
        correctCount: Int,
        totalCount: Int
    ) {
        val percent = if (totalCount > 0) ((correctCount.toDouble() / totalCount) * 100.0).toInt() else 0
        val current = _examResults.value.toMutableList()
        current.add(
            0,
            CloudExamResult(
                id = UUID.randomUUID().toString(),
                userId = LOCAL_USER_ID,
                subjectId = subjectId,
                subjectTitle = subjectTitle,
                correctCount = correctCount,
                totalCount = totalCount,
                scorePercent = percent,
                createdAt = Timestamp.now()
            )
        )
        _examResults.value = current
        persistExams(current)
    }

    private fun loadLessons(): List<CloudLessonProgress> {
        val raw = prefs.getString(KEY_LESSONS, null) ?: return emptyList()
        return runCatching {
            val arr = JSONArray(raw)
            buildList {
                for (i in 0 until arr.length()) {
                    val obj = arr.getJSONObject(i)
                    val lessonId = obj.optString("lessonId")
                    add(
                        CloudLessonProgress(
                            id = lessonId,
                            userId = LOCAL_USER_ID,
                            lessonId = lessonId,
                            subjectId = obj.optString("subjectId"),
                            isCompleted = obj.optBoolean("isCompleted", false),
                            isBookmarked = obj.optBoolean("isBookmarked", false),
                            userNote = obj.optString("userNote", ""),
                            masteryPercent = obj.optInt("masteryPercent", 0),
                            updatedAt = Timestamp.now()
                        )
                    )
                }
            }
        }.getOrDefault(emptyList())
    }

    private fun persistLessons(list: List<CloudLessonProgress>) {
        val arr = JSONArray()
        list.forEach { item ->
            arr.put(
                JSONObject().apply {
                    put("lessonId", item.lessonId)
                    put("subjectId", item.subjectId)
                    put("isCompleted", item.isCompleted)
                    put("isBookmarked", item.isBookmarked)
                    put("userNote", item.userNote)
                    put("masteryPercent", item.masteryPercent)
                }
            )
        }
        prefs.edit().putString(KEY_LESSONS, arr.toString()).apply()
    }

    private fun loadFlashcards(): List<CloudFlashcardProgress> {
        val raw = prefs.getString(KEY_FLASHCARDS, null) ?: return emptyList()
        return runCatching {
            val arr = JSONArray(raw)
            buildList {
                for (i in 0 until arr.length()) {
                    val obj = arr.getJSONObject(i)
                    val cardId = obj.optString("cardId")
                    add(
                        CloudFlashcardProgress(
                            id = cardId,
                            userId = LOCAL_USER_ID,
                            cardId = cardId,
                            subjectId = obj.optString("subjectId"),
                            leitnerBox = obj.optInt("leitnerBox", 1),
                            reviewCount = obj.optInt("reviewCount", 0),
                            updatedAt = Timestamp.now()
                        )
                    )
                }
            }
        }.getOrDefault(emptyList())
    }

    private fun persistFlashcards(list: List<CloudFlashcardProgress>) {
        val arr = JSONArray()
        list.forEach { item ->
            arr.put(
                JSONObject().apply {
                    put("cardId", item.cardId)
                    put("subjectId", item.subjectId)
                    put("leitnerBox", item.leitnerBox)
                    put("reviewCount", item.reviewCount)
                }
            )
        }
        prefs.edit().putString(KEY_FLASHCARDS, arr.toString()).apply()
    }

    private fun loadTasks(): List<CloudStudyTask> {
        val raw = prefs.getString(KEY_TASKS, null) ?: return emptyList()
        return runCatching {
            val arr = JSONArray(raw)
            buildList {
                for (i in 0 until arr.length()) {
                    val obj = arr.getJSONObject(i)
                    add(
                        CloudStudyTask(
                            id = obj.optString("id"),
                            userId = LOCAL_USER_ID,
                            title = obj.optString("title"),
                            subjectId = obj.optString("subjectId"),
                            stageTag = obj.optString("stageTag"),
                            durationMinutes = obj.optInt("durationMinutes", 30),
                            isCompleted = obj.optBoolean("isCompleted", false),
                            createdAt = Timestamp.now()
                        )
                    )
                }
            }
        }.getOrDefault(emptyList())
    }

    private fun persistTasks(list: List<CloudStudyTask>) {
        val arr = JSONArray()
        list.forEach { item ->
            arr.put(
                JSONObject().apply {
                    put("id", item.id)
                    put("title", item.title)
                    put("subjectId", item.subjectId)
                    put("stageTag", item.stageTag)
                    put("durationMinutes", item.durationMinutes)
                    put("isCompleted", item.isCompleted)
                }
            )
        }
        prefs.edit().putString(KEY_TASKS, arr.toString()).apply()
    }

    private fun loadExams(): List<CloudExamResult> {
        val raw = prefs.getString(KEY_EXAMS, null) ?: return emptyList()
        return runCatching {
            val arr = JSONArray(raw)
            buildList {
                for (i in 0 until arr.length()) {
                    val obj = arr.getJSONObject(i)
                    add(
                        CloudExamResult(
                            id = obj.optString("id"),
                            userId = LOCAL_USER_ID,
                            subjectId = obj.optString("subjectId"),
                            subjectTitle = obj.optString("subjectTitle"),
                            correctCount = obj.optInt("correctCount", 0),
                            totalCount = obj.optInt("totalCount", 0),
                            scorePercent = obj.optInt("scorePercent", 0),
                            createdAt = Timestamp.now()
                        )
                    )
                }
            }
        }.getOrDefault(emptyList())
    }

    private fun persistExams(list: List<CloudExamResult>) {
        val arr = JSONArray()
        list.forEach { item ->
            arr.put(
                JSONObject().apply {
                    put("id", item.id)
                    put("subjectId", item.subjectId)
                    put("subjectTitle", item.subjectTitle)
                    put("correctCount", item.correctCount)
                    put("totalCount", item.totalCount)
                    put("scorePercent", item.scorePercent)
                }
            )
        }
        prefs.edit().putString(KEY_EXAMS, arr.toString()).apply()
    }

    fun getCompletedRoadmapSteps(): Set<String> {
        return prefs.getStringSet(KEY_ROADMAP_STEPS, emptySet()) ?: emptySet()
    }

    fun setRoadmapStepCompleted(stepId: String, isCompleted: Boolean) {
        val current = getCompletedRoadmapSteps().toMutableSet()
        if (isCompleted) {
            current.add(stepId)
        } else {
            current.remove(stepId)
        }
        prefs.edit().putStringSet(KEY_ROADMAP_STEPS, current).apply()
    }

    companion object {
        const val LOCAL_USER_ID = "local_offline_user"
        private const val PREFS_NAME = "konkoor_local_offline_store"
        private const val KEY_SEEDED = "seeded_v1"
        private const val KEY_LESSONS = "lessons_json"
        private const val KEY_FLASHCARDS = "flashcards_json"
        private const val KEY_TASKS = "tasks_json"
        private const val KEY_EXAMS = "exams_json"
        private const val KEY_ROADMAP_STEPS = "completed_roadmap_steps_v1"
    }
}
