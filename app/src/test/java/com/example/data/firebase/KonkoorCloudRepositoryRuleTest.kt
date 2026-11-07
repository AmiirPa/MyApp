package com.example.data.firebase

import com.example.base.FirestoreEmulatorTestBase
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class KonkoorCloudRepositoryRuleTest : FirestoreEmulatorTestBase() {

    @Test
    fun createAndObserveStudyTask_authenticatedOwner_succeeds() = runBlocking {
        val uid = signInTestUser(ALICE_EMAIL)
        val repo = KonkoorCloudRepository(firestore)

        val createRes = withTimeout(DEFAULT_TIMEOUT_MS) {
            repo.createStudyTask(
                title = "تمرین تقطیع عروض درس اول",
                subjectId = "arooz",
                stageTag = "گام ۱: شروع از منفی صفر",
                durationMinutes = 30
            )
        }
        assertTrue(createRes.isSuccess)
        val taskId = createRes.getOrThrow()

        val emitted = withTimeout(FLOW_TIMEOUT_MS) {
            repo.observeStudyTasks(uid).first { list -> list.any { it.id == taskId } }
        }
        assertTrue(emitted.any { it.id == taskId })
    }

    @Test
    fun getStudyTask_crossUserAccess_failsWithPermissionDenied() = runBlocking {
        val aliceUid = signInTestUser(ALICE_EMAIL)
        val aliceRepo = KonkoorCloudRepository(firestore)
        val taskId = withTimeout(DEFAULT_TIMEOUT_MS) {
            aliceRepo.createStudyTask("Alice Task", "math", "Stage 1", 25).getOrThrow()
        }

        signInTestUser(BOB_EMAIL)
        val bobRepo = KonkoorCloudRepository(firestore)
        val crossUserResult = withTimeout(DEFAULT_TIMEOUT_MS) {
            bobRepo.getStudyTaskById(aliceUid, taskId)
        }
        assertTrue(crossUserResult.isFailure)
        val ex = crossUserResult.exceptionOrNull()?.unwrapFirestoreException()
        assertNotNull(ex)
        assertEquals(FirebaseFirestoreException.Code.PERMISSION_DENIED, ex?.code)
    }

    @Test
    fun observeStudyTasks_unauthenticatedUser_failsWithPermissionDenied() = runBlocking {
        val aliceUid = signInTestUser(ALICE_EMAIL)
        auth.signOut()
        val repo = KonkoorCloudRepository(firestore)

        var caught: Throwable? = null
        try {
            withTimeout(FLOW_TIMEOUT_MS) {
                repo.observeStudyTasks(aliceUid).first()
            }
        } catch (e: Throwable) {
            caught = e
        }
        assertNotNull(caught)
        val firestoreException = checkNotNull(caught?.unwrapFirestoreException()) {
            "Expected nested FirebaseFirestoreException, got $caught"
        }
        assertEquals(FirebaseFirestoreException.Code.PERMISSION_DENIED, firestoreException.code)
    }

    private companion object {
        const val ALICE_EMAIL = "alice@konkoor.test"
        const val BOB_EMAIL = "bob@konkoor.test"
        const val DEFAULT_TIMEOUT_MS = 5000L
        const val FLOW_TIMEOUT_MS = 3000L
    }
}
