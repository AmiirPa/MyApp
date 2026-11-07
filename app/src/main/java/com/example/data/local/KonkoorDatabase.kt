package com.example.data.local

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "lesson_progress")
data class LessonProgressEntity(
    @PrimaryKey val lessonId: String,
    val subjectId: String,
    val isCompleted: Boolean = false,
    val isBookmarked: Boolean = false,
    val userNote: String = "",
    val masteryPercent: Int = 0,
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "flashcard_progress")
data class FlashcardProgressEntity(
    @PrimaryKey val cardId: String,
    val subjectId: String,
    val leitnerBox: Int = 1, // 1 to 5 (Box 5 = Mastered)
    val reviewCount: Int = 0,
    val lastReviewedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "study_tasks")
data class StudyTaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val subjectId: String,
    val stageTag: String, // e.g., "فاز ۱: منفی صفر", "فاز ۲: تثبیت پایه", "ویژه فرهنگیان"
    val durationMinutes: Int,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "exam_results")
data class ExamResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val subjectId: String,
    val subjectTitle: String,
    val correctCount: Int,
    val totalCount: Int,
    val scorePercent: Int,
    val timestamp: Long = System.currentTimeMillis()
)

@Dao
interface KonkoorDao {
    @Query("SELECT * FROM lesson_progress")
    fun getAllLessonProgress(): Flow<List<LessonProgressEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertLessonProgress(progress: LessonProgressEntity)

    @Query("SELECT * FROM flashcard_progress")
    fun getAllFlashcardProgress(): Flow<List<FlashcardProgressEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertFlashcardProgress(progress: FlashcardProgressEntity)

    @Query("SELECT * FROM study_tasks ORDER BY isCompleted ASC, id ASC")
    fun getAllStudyTasks(): Flow<List<StudyTaskEntity>>

    @Query("SELECT COUNT(*) FROM study_tasks")
    suspend fun getStudyTaskCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudyTask(task: StudyTaskEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudyTasks(tasks: List<StudyTaskEntity>)

    @Query("DELETE FROM study_tasks WHERE id = :taskId")
    suspend fun deleteStudyTask(taskId: Int)

    @Query("SELECT * FROM exam_results ORDER BY timestamp DESC")
    fun getAllExamResults(): Flow<List<ExamResultEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExamResult(result: ExamResultEntity)
}

@Database(
    entities = [
        LessonProgressEntity::class,
        FlashcardProgressEntity::class,
        StudyTaskEntity::class,
        ExamResultEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class KonkoorDatabase : RoomDatabase() {
    abstract fun konkoorDao(): KonkoorDao

    companion object {
        @Volatile
        private var INSTANCE: KonkoorDatabase? = null

        fun getInstance(context: Context): KonkoorDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KonkoorDatabase::class.java,
                    "konkoor_farhangian_1406.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
