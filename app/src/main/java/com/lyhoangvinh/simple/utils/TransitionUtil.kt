package com.lyhoangvinh.simple.utils

import android.app.Activity
import androidx.annotation.Nullable
import androidx.core.util.Pair
import android.view.View

/**
 * Helper class for creating content transitions used with [android.app.ActivityOptions].
 */
object TransitionUtil {

    /**
     * Create the transition participants required during a activity transition while
     * avoiding glitches with the system UI.
     *
     * @param activity The activity used as start for the transition.
     * @param includeStatusBar If false, the status bar will not be added as the transition
     * participant.
     * @return All transition participants.
     */
    fun createSafeTransitionParticipants(
        activity: Activity,
        includeStatusBar: Boolean, @Nullable vararg otherParticipants: Pair<View, String>
    ): Array<Pair<View, String>> {
        // Avoid system UI glitches as described here:
        // https://plus.google.com/+AlexLockwood/posts/RPtwZ5nNebb
        val decor = activity.window.decorView
        var statusBar: View? = null
        if (includeStatusBar) {
            statusBar = decor.findViewById(android.R.id.statusBarBackground)
        }
        val navBar = decor.findViewById<View>(android.R.id.navigationBarBackground)

        // Create pair of transition participants.
        val participants = ArrayList<Pair<View, String>>(3)
        addNonNullViewToTransitionParticipants(statusBar, participants)
        addNonNullViewToTransitionParticipants(navBar, participants)
        // only add transition participants if there's at least one none-null element
        participants.addAll(mutableListOf(*otherParticipants))
        return participants.toTypedArray()
    }

    private fun addNonNullViewToTransitionParticipants(view: View?, participants: ArrayList<Pair<View, String>>) {
        if (view == null) {
            return
        }
        participants.add(Pair(view, view.transitionName))
    }
}