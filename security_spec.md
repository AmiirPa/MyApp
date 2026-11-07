# Security Specification (Phase 0 TDD)

## 1. Data Invariants
1. **User Isolation**: Every document in `/users/{userId}/lesson_progress/{lessonId}`, `/users/{userId}/flashcards/{cardId}`, `/users/{userId}/study_tasks/{taskId}`, and `/users/{userId}/exam_results/{resultId}` belongs strictly to `{userId}` (`request.auth.uid == userId` and `data.userId == userId`).
2. **Path Variable Hardening**: All document IDs (`userId`, `lessonId`, `cardId`, `taskId`, `resultId`) must satisfy `isValidId(id)` (`^[a-zA-Z0-9_\-]+$`, max 128 chars).
3. **Strict Schema & Bounds**:
   - `LessonProgress`: `userId` (string), `lessonId` (string, 1..100), `subjectId` (string, 1..50), `isCompleted` (bool), `isBookmarked` (bool), `userNote` (string, 0..1000), `masteryPercent` (int, 0..100), `createdAt` (timestamp <= request.time), `updatedAt` (timestamp <= request.time).
   - `FlashcardProgress`: `userId` (string), `cardId` (string, 1..100), `subjectId` (string, 1..50), `leitnerBox` (int, 1..5), `reviewCount` (int, >= 0), `createdAt` (timestamp <= request.time), `updatedAt` (timestamp <= request.time).
   - `StudyTask`: `userId` (string), `title` (string, 1..300), `subjectId` (string, 1..50), `stageTag` (string, 1..100), `durationMinutes` (int, 1..600), `isCompleted` (bool), `createdAt` (timestamp <= request.time), `updatedAt` (timestamp <= request.time).
   - `ExamResult`: `userId` (string), `subjectId` (string, 1..50), `subjectTitle` (string, 1..150), `correctCount` (int, 0..200), `totalCount` (int, 1..200), `scorePercent` (int, 0..100), `createdAt` (timestamp <= request.time), `updatedAt` (timestamp <= request.time).
4. **Immutable Fields**: `userId` and `createdAt` cannot be modified on `update`.

## 2. The "Dirty Dozen" Payloads
1. Unauthenticated read/list on `/users/alice_123/study_tasks`.
2. Cross-user read (`bob_456` reading `/users/alice_123/study_tasks/task_1`).
3. Cross-user write (`bob_456` creating a document under `/users/alice_123/study_tasks`).
4. Identity spoofing (`alice_123` writing `userId: "bob_456"` inside `/users/alice_123/study_tasks/task_1`).
5. Shadow field injection (`{"isVerifiedAdmin": true}` added to `study_tasks`).
6. Oversized string attack (`title` > 300 chars or `userNote` > 1000 chars).
7. Out-of-range integer (`leitnerBox: 99` or `masteryPercent: -5` or `durationMinutes: 0`).
8. Invalid type (`isCompleted: "yes"` instead of boolean).
9. Mutating immutable `createdAt` or `userId` on update.
10. Future timestamp injection (`createdAt` > `request.time`).
11. Invalid ID characters in document path.
12. Deleting another user's study task.
