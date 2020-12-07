package com.android.aws.util

import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager
import com.google.firebase.messaging.FirebaseMessaging
import timber.log.Timber

fun getPinpointManager(pinpointConfig: PinpointConfiguration): PinpointManager? {

    val pinpointManager = PinpointManager(pinpointConfig)
    FirebaseMessaging.getInstance().token
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token: String? = task.result
                Timber.d("Registering push notifications token: $token")
                pinpointManager.notificationClient?.registerDeviceToken(token)
            }
        }

    return pinpointManager
}