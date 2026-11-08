package com.example.data.firebase

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests for the pure auth-transition decision used by KonkoorViewModel.
 * No Android runtime, no Firebase, no device: every case is decided by
 * [resolveCloudSyncTransition] alone, so a wrong branch here is a wrong
 * sync start/stop on the phone.
 */
class CloudSyncTransitionTest {

    @Test
    fun signedOut_to_signedInWithRepo_startsSync() {
        assertEquals(
            CloudSyncTransition.START,
            resolveCloudSyncTransition(
                previousUserId = null,
                newUserId = "uid-1",
                previouslyHadCloud = false,
                nowHasCloud = true
            )
        )
    }

    @Test
    fun signedOut_to_signedInWithoutRepo_staysLocal() {
        // Repository creation failed: claiming cloud sync would be a lie.
        assertEquals(
            CloudSyncTransition.NONE,
            resolveCloudSyncTransition(
                previousUserId = null,
                newUserId = "uid-1",
                previouslyHadCloud = false,
                nowHasCloud = false
            )
        )
    }

    @Test
    fun signedOut_to_signedOut_isNoop() {
        assertEquals(
            CloudSyncTransition.NONE,
            resolveCloudSyncTransition(
                previousUserId = null,
                newUserId = null,
                previouslyHadCloud = false,
                nowHasCloud = false
            )
        )
    }

    @Test
    fun signedIn_to_signedOut_stopsSync() {
        assertEquals(
            CloudSyncTransition.STOP,
            resolveCloudSyncTransition(
                previousUserId = "uid-1",
                newUserId = null,
                previouslyHadCloud = true,
                nowHasCloud = false
            )
        )
    }

    @Test
    fun signedIn_to_signedOutWithoutCloud_isNoop() {
        assertEquals(
            CloudSyncTransition.NONE,
            resolveCloudSyncTransition(
                previousUserId = "uid-1",
                newUserId = null,
                previouslyHadCloud = false,
                nowHasCloud = false
            )
        )
    }

    @Test
    fun sameUserEmittedAgain_isNoop() {
        // The first auth emission after launch repeats the constructor snapshot.
        assertEquals(
            CloudSyncTransition.NONE,
            resolveCloudSyncTransition(
                previousUserId = "uid-1",
                newUserId = "uid-1",
                previouslyHadCloud = true,
                nowHasCloud = true
            )
        )
    }

    @Test
    fun sameUser_repoBecomesAvailable_startsSync() {
        // Signed in at launch but repository creation failed; a later emission
        // with a repository must start sync instead of staying local forever.
        assertEquals(
            CloudSyncTransition.START,
            resolveCloudSyncTransition(
                previousUserId = "uid-1",
                newUserId = "uid-1",
                previouslyHadCloud = false,
                nowHasCloud = true
            )
        )
    }

    @Test
    fun sameUser_redundantCallWithoutRepo_keepsWorkingSession() {
        assertEquals(
            CloudSyncTransition.NONE,
            resolveCloudSyncTransition(
                previousUserId = "uid-1",
                newUserId = "uid-1",
                previouslyHadCloud = true,
                nowHasCloud = false
            )
        )
    }

    @Test
    fun userSwitches_withRepo_switchesSync() {
        assertEquals(
            CloudSyncTransition.SWITCH,
            resolveCloudSyncTransition(
                previousUserId = "uid-1",
                newUserId = "uid-2",
                previouslyHadCloud = true,
                nowHasCloud = true
            )
        )
    }

    @Test
    fun userSwitches_withoutRepo_stopsOldUsersSync() {
        // Must not keep syncing the previous user's data under the new user.
        assertEquals(
            CloudSyncTransition.STOP,
            resolveCloudSyncTransition(
                previousUserId = "uid-1",
                newUserId = "uid-2",
                previouslyHadCloud = true,
                nowHasCloud = false
            )
        )
    }

    @Test
    fun blankUserId_isTreatedAsSignedOut() {
        assertEquals(
            CloudSyncTransition.START,
            resolveCloudSyncTransition(
                previousUserId = "",
                newUserId = "uid-1",
                previouslyHadCloud = false,
                nowHasCloud = true
            )
        )
        assertEquals(
            CloudSyncTransition.STOP,
            resolveCloudSyncTransition(
                previousUserId = "uid-1",
                newUserId = "   ",
                previouslyHadCloud = true,
                nowHasCloud = false
            )
        )
    }
}
