package com.example.data.firebase

/**
 * Pure decision logic for the cloud-sync session of [com.example.ui.KonkoorViewModel].
 *
 * The ViewModel used to freeze `currentUserId` / `repository` at construction time, so a
 * user who signed in after launch never got cloud sync. The session is now dynamic and
 * every auth change goes through [resolveCloudSyncTransition] first; this function is
 * deliberately pure (no Android, no Firebase) so it can be unit-tested without a device.
 *
 * Blank user ids are treated exactly like null: an empty uid is not a signed-in user.
 */
enum class CloudSyncTransition {
    /** Nothing changes: keep the current session (or keep staying local). */
    NONE,

    /** No session yet and a signed-in user with a repository appeared: start sync. */
    START,

    /** Signed-in user changed (or the old session must be dropped): stop old, start new. */
    SWITCH,

    /** User signed out (or the user changed and no repository is available): stop sync. */
    STOP
}

fun resolveCloudSyncTransition(
    previousUserId: String?,
    newUserId: String?,
    previouslyHadCloud: Boolean,
    nowHasCloud: Boolean
): CloudSyncTransition {
    val previous = previousUserId?.takeIf { it.isNotBlank() }
    val next = newUserId?.takeIf { it.isNotBlank() }

    // Signed out (or never signed in): stop only if a cloud session was running.
    if (next == null) {
        return if (previouslyHadCloud) CloudSyncTransition.STOP else CloudSyncTransition.NONE
    }
    // First sign-in: start only when a repository could actually be created.
    if (previous == null) {
        return if (nowHasCloud) CloudSyncTransition.START else CloudSyncTransition.NONE
    }
    if (previous == next) {
        // Same user again (e.g. the first auth emission after launch, or a re-attach):
        // start only if the previous call had no cloud session and this one does.
        // If this call has no repository but the old session works, keep it (NONE)
        // instead of dropping a working session because of one redundant call.
        return if (!previouslyHadCloud && nowHasCloud) CloudSyncTransition.START
        else CloudSyncTransition.NONE
    }
    // A different user signed in: switch when a repository is available, otherwise
    // at least stop syncing the previous user's data; a later call with a repository
    // will START the new user's session.
    return if (nowHasCloud) CloudSyncTransition.SWITCH else CloudSyncTransition.STOP
}
