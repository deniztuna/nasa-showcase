package com.appcent.nasashowcase.manager

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

class NasaFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.e("NasaFirebaseMessagingService::Token %s", token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        // executes only when app was in the foreground
        Timber.e("NasaFirebaseMessagingService::onMessageReceived ${remoteMessage?.data}")
        if (remoteMessage?.notification != null) {
            Timber.e("NasaFirebaseMessagingService::onMessageReceived ${remoteMessage.notification}")
        }
    }
}