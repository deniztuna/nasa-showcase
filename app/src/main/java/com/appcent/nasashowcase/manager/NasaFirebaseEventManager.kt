package com.appcent.nasashowcase.manager

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import javax.inject.Singleton

@Singleton
class NasaFirebaseEventManager(context: Context) {
    val firebaseAnalytics = FirebaseAnalytics.getInstance(context)

    fun logExampleEvent(roverName: String, roverCameraName: String, photoId: Int) {
        firebaseAnalytics.logEvent(FIREBASE_LOG_TITLE) {
            param(FIREBASE_LOG_PARAM_ROVERNAME, roverName)
            param(FIREBASE_LOG_PARAM_ROVERCAMERANAME, roverCameraName)
            param(FIREBASE_LOG_PARAM_PHOTOID, photoId.toLong())
        }
    }

    companion object {
        private const val FIREBASE_LOG_TITLE = "listItemClick"
        private const val FIREBASE_LOG_PARAM_ROVERNAME = "roverName"
        private const val FIREBASE_LOG_PARAM_ROVERCAMERANAME = "roverCameraName"
        private const val FIREBASE_LOG_PARAM_PHOTOID = "photoId"
    }
}