package com.example.data.firebase

import android.content.Context
import com.example.R
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.util.UUID

class KonkoorCloudRepository(private val db: FirebaseFirestore) {

    constructor(context: Context) : this(
        FirebaseFirestore.getInstance(
            context.applicationContext.getString(R.string.firestore_database_id)
        )
    )

    private val auth = Firebase.auth

    private fun requireUserId(): String {
        return auth.currentUser?.uid
            ?: throw IllegalStateException("کاربر باید ابتدا با حساب گوگل وارد شود.")
    }

    // 1. Observe Lesson Progress
    fun observeLessonProgress(userId: String): Flow<List<CloudLessonProgress>> {
        val path = "users/$userId/lesson_progress"
        return db.collection("users").document(userId).collection("lesson_progress")
            .whereEqualTo("userId", userId)
            .snapshots()
            .map { snapshot ->
                snapshot.toObjects(
                    CloudLessonProgress::class.java,
                    DocumentSnapshot.ServerTimestampBehavior.ESTIMATE
                )
            }
            .catch { error ->
                if (error is Exception) handleFirestoreError(error, OperationType.LIST, path)
                throw error
            }
    }

    // 2. Save / Upsert Lesson Progress
    suspend fun saveLessonProgress(
        lessonId: String,
        subjectId: String,
        isCompleted: Boolean,
        isBookmarked: Boolean,
        userNote: String,
        masteryPercent: Int
    ): Result<Unit> {
        val uid = requireUserId()
        val docRef = db.collection("users").document(uid)
            .collection("lesson_progress").document(lessonId)
        return try {
            val snapshot = docRef.get().await()
            if (snapshot.exists()) {
                docRef.update(
                    mapOf(
                        "isCompleted" to isCompleted,
                        "isBookmarked" to isBookmarked,
                        "userNote" to userNote.take(1000),
                        "masteryPercent" to masteryPercent.coerceIn(0, 100),
                        "updatedAt" to FieldValue.serverTimestamp()
                    )
                ).await()
            } else {
                docRef.set(
                    mapOf(
                        "userId" to uid,
                        "lessonId" to lessonId,
                        "subjectId" to subjectId,
                        "isCompleted" to isCompleted,
                        "isBookmarked" to isBookmarked,
                        "userNote" to userNote.take(1000),
                        "masteryPercent" to masteryPercent.coerceIn(0, 100),
                        "createdAt" to FieldValue.serverTimestamp(),
                        "updatedAt" to FieldValue.serverTimestamp()
                    )
                ).await()
            }
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.WRITE, docRef.path)
            Result.failure(e)
        }
    }

    // 3. Observe Flashcard Progress
    fun observeFlashcardProgress(userId: String): Flow<List<CloudFlashcardProgress>> {
        val path = "users/$userId/flashcards"
        return db.collection("users").document(userId).collection("flashcards")
            .whereEqualTo("userId", userId)
            .snapshots()
            .map { snapshot ->
                snapshot.toObjects(
                    CloudFlashcardProgress::class.java,
                    DocumentSnapshot.ServerTimestampBehavior.ESTIMATE
                )
            }
            .catch { error ->
                if (error is Exception) handleFirestoreError(error, OperationType.LIST, path)
                throw error
            }
    }

    // 4. Update Flashcard Box
    suspend fun updateFlashcardBox(
        cardId: String,
        subjectId: String,
        newBox: Int,
        currentReviews: Int
    ): Result<Unit> {
        val uid = requireUserId()
        val docRef = db.collection("users").document(uid)
            .collection("flashcards").document(cardId)
        return try {
            val snapshot = docRef.get().await()
            if (snapshot.exists()) {
                docRef.update(
                    mapOf(
                        "leitnerBox" to newBox.coerceIn(1, 5),
                        "reviewCount" to (currentReviews + 1).coerceAtLeast(0),
                        "updatedAt" to FieldValue.serverTimestamp()
                    )
                ).await()
            } else {
                docRef.set(
                    mapOf(
                        "userId" to uid,
                        "cardId" to cardId,
                        "subjectId" to subjectId,
                        "leitnerBox" to newBox.coerceIn(1, 5),
                        "reviewCount" to (currentReviews + 1).coerceAtLeast(0),
                        "createdAt" to FieldValue.serverTimestamp(),
                        "updatedAt" to FieldValue.serverTimestamp()
                    )
                ).await()
            }
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.WRITE, docRef.path)
            Result.failure(e)
        }
    }

    // 5. Observe Study Tasks
    fun observeStudyTasks(userId: String): Flow<List<CloudStudyTask>> {
        val path = "users/$userId/study_tasks"
        return db.collection("users").document(userId).collection("study_tasks")
            .whereEqualTo("userId", userId)
            .snapshots()
            .map { snapshot ->
                snapshot.toObjects(
                    CloudStudyTask::class.java,
                    DocumentSnapshot.ServerTimestampBehavior.ESTIMATE
                )
            }
            .catch { error ->
                if (error is Exception) handleFirestoreError(error, OperationType.LIST, path)
                throw error
            }
    }

    // 6. Add Study Task
    suspend fun createStudyTask(
        title: String,
        subjectId: String,
        stageTag: String,
        durationMinutes: Int
    ): Result<String> {
        val uid = requireUserId()
        val taskId = "task_${UUID.randomUUID().toString().replace("-", "").take(16)}"
        val docRef = db.collection("users").document(uid)
            .collection("study_tasks").document(taskId)
        return try {
            docRef.set(
                mapOf(
                    "userId" to uid,
                    "title" to title.trim().take(300),
                    "subjectId" to subjectId,
                    "stageTag" to stageTag.take(100),
                    "durationMinutes" to durationMinutes.coerceIn(1, 600),
                    "isCompleted" to false,
                    "createdAt" to FieldValue.serverTimestamp(),
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            ).await()
            Result.success(taskId)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, docRef.path)
            Result.failure(e)
        }
    }

    // 7. Get Study Task by ID (also used for cross-user security rule verification)
    suspend fun getStudyTaskById(ownerUid: String, taskId: String): Result<CloudStudyTask?> {
        val docRef = db.collection("users").document(ownerUid)
            .collection("study_tasks").document(taskId)
        return try {
            val snapshot = docRef.get().await()
            Result.success(
                snapshot.toObject(
                    CloudStudyTask::class.java,
                    DocumentSnapshot.ServerTimestampBehavior.ESTIMATE
                )
            )
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.GET, docRef.path)
            Result.failure(e)
        }
    }

    // 8. Toggle Study Task Completion
    suspend fun toggleStudyTask(taskId: String, currentCompleted: Boolean): Result<Unit> {
        val uid = requireUserId()
        val docRef = db.collection("users").document(uid)
            .collection("study_tasks").document(taskId)
        return try {
            docRef.update(
                mapOf(
                    "isCompleted" to !currentCompleted,
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            ).await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.UPDATE, docRef.path)
            Result.failure(e)
        }
    }

    // 9. Delete Study Task
    suspend fun deleteStudyTask(taskId: String): Result<Unit> {
        val uid = requireUserId()
        val docRef = db.collection("users").document(uid)
            .collection("study_tasks").document(taskId)
        return try {
            docRef.delete().await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.DELETE, docRef.path)
            Result.failure(e)
        }
    }

    // 10. Observe Exam Results
    fun observeExamResults(userId: String): Flow<List<CloudExamResult>> {
        val path = "users/$userId/exam_results"
        return db.collection("users").document(userId).collection("exam_results")
            .whereEqualTo("userId", userId)
            .snapshots()
            .map { snapshot ->
                snapshot.toObjects(
                    CloudExamResult::class.java,
                    DocumentSnapshot.ServerTimestampBehavior.ESTIMATE
                )
            }
            .catch { error ->
                if (error is Exception) handleFirestoreError(error, OperationType.LIST, path)
                throw error
            }
    }

    // 11. Record Exam Result
    suspend fun recordExamResult(
        subjectId: String,
        subjectTitle: String,
        correctCount: Int,
        totalCount: Int
    ): Result<String> {
        val uid = requireUserId()
        val resultId = "exam_${UUID.randomUUID().toString().replace("-", "").take(16)}"
        val docRef = db.collection("users").document(uid)
            .collection("exam_results").document(resultId)
        val safeTotal = totalCount.coerceIn(1, 200)
        val safeCorrect = correctCount.coerceIn(0, safeTotal)
        val percent = ((safeCorrect * 100f) / safeTotal).toInt().coerceIn(0, 100)
        return try {
            docRef.set(
                mapOf(
                    "userId" to uid,
                    "subjectId" to subjectId,
                    "subjectTitle" to subjectTitle.take(150),
                    "correctCount" to safeCorrect,
                    "totalCount" to safeTotal,
                    "scorePercent" to percent,
                    "createdAt" to FieldValue.serverTimestamp(),
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            ).await()
            Result.success(resultId)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, docRef.path)
            Result.failure(e)
        }
    }

    // 12. Seed Initial Study Roadmap into Firestore if user has no tasks yet
    suspend fun seedInitialCloudRoadmapIfNeeded() {
        val uid = auth.currentUser?.uid ?: return
        val colRef = db.collection("users").document(uid).collection("study_tasks")
        try {
            val existing = colRef.limit(1).get().await()
            if (existing.isEmpty) {
                val starterTasks = listOf(
                    Triple("ریاضی از منفی صفر: جدول علامت‌ها و جمع و تفریق به روش پول و بدهی", "math", "گام ۱: شروع از منفی صفر" to 35),
                    Triple("عروض از منفی صفر: تمرین بخش‌کردن ۱۰ کلمه و تشخیص هجای کوتاه (U) و بلند (–)", "arooz", "گام ۱: شروع از منفی صفر" to 30),
                    Triple("عربی از منفی صفر: حفظ جدول ۱۴ ضمیر و نشانه‌های اسم و فعل بدون ترجمه", "arabic", "گام ۱: شروع از منفی صفر" to 40),
                    Triple("زبان انگلیسی از منفی صفر: فرمول طلایی جمله‌سازی (فاعل + فعل + مفعول) و To Be", "english", "گام ۱: شروع از منفی صفر" to 30),
                    Triple("علوم و فنون: تفکیک تصویری ۴ آرایه تشبیه، استعاره، مجاز و کنایه", "fonoon", "گام ۲: تثبیت پایه" to 45),
                    Triple("اختصاصی فرهنگیان ۱۴۰۶: مطالعه فصل اول کتاب مهارت معلمی استاد قرائتی + ۱۰ تست", "farhangian", "ویژه فرهنگیان ۱۴۰۶" to 50)
                )
                starterTasks.forEachIndexed { index, (title, subjectId, stageAndDuration) ->
                    val docId = "starter_task_${index + 1}"
                    colRef.document(docId).set(
                        mapOf(
                            "userId" to uid,
                            "title" to title,
                            "subjectId" to subjectId,
                            "stageTag" to stageAndDuration.first,
                            "durationMinutes" to stageAndDuration.second,
                            "isCompleted" to false,
                            "createdAt" to FieldValue.serverTimestamp(),
                            "updatedAt" to FieldValue.serverTimestamp()
                        )
                    ).await()
                }
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.WRITE, colRef.path)
        }
    }
}
