package com.example.data.firebase

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.ServerTimestamp

@IgnoreExtraProperties
data class CloudLessonProgress(
    @DocumentId val id: String = "",
    val userId: String = "",
    val lessonId: String = "",
    val subjectId: String = "",
    @get:PropertyName("isCompleted") @set:PropertyName("isCompleted")
    var isCompleted: Boolean = false,
    @get:PropertyName("isBookmarked") @set:PropertyName("isBookmarked")
    var isBookmarked: Boolean = false,
    val userNote: String = "",
    val masteryPercent: Int = 0,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

@IgnoreExtraProperties
data class CloudFlashcardProgress(
    @DocumentId val id: String = "",
    val userId: String = "",
    val cardId: String = "",
    val subjectId: String = "",
    val leitnerBox: Int = 1,
    val reviewCount: Int = 0,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

@IgnoreExtraProperties
data class CloudStudyTask(
    @DocumentId val id: String = "",
    val userId: String = "",
    val title: String = "",
    val subjectId: String = "",
    val stageTag: String = "",
    val durationMinutes: Int = 30,
    @get:PropertyName("isCompleted") @set:PropertyName("isCompleted")
    var isCompleted: Boolean = false,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

@IgnoreExtraProperties
data class CloudExamResult(
    @DocumentId val id: String = "",
    val userId: String = "",
    val subjectId: String = "",
    val subjectTitle: String = "",
    val correctCount: Int = 0,
    val totalCount: Int = 1,
    val scorePercent: Int = 0,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)
