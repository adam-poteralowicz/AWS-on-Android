package com.android.aws.ui.notifications

import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager
import com.amazonaws.mobileconnectors.pinpoint.targeting.notification.NotificationClient
import com.amazonaws.mobileconnectors.pinpoint.targeting.notification.NotificationClient.INTENT_SNS_NOTIFICATION_DATA
import com.amazonaws.mobileconnectors.pinpoint.targeting.notification.NotificationClient.INTENT_SNS_NOTIFICATION_FROM
import com.amazonaws.mobileconnectors.pinpoint.targeting.notification.NotificationDetails
import com.android.aws.ui.storage.StorageActivity
import com.android.aws.util.getPinpointManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber


class PushListenerService : FirebaseMessagingService() {

    private var storageActivity = StorageActivity()
    private val actionPushNotification = "push-notification"

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Timber.d("Registering push notifications token: $token")
        getPinpointManager()?.notificationClient?.registerDeviceToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Timber.d("Message: ${message.data}")
        val notificationClient = getPinpointManager()?.notificationClient
        val notificationDetails = NotificationDetails.builder()
            .from(message.from)
            .mapData(message.data)
            .intentAction(NotificationClient.FCM_INTENT_ACTION)
            .build()
        val pushResult = notificationClient?.handleNotificationReceived(notificationDetails)
        if (pushResult != NotificationClient.PushResult.NOT_HANDLED) {
            /**
            The push message was due to a Pinpoint campaign.
            If the app was in the background, a local notification was added
            in the notification center. If the app was in the foreground, an
            event was recorded indicating the app was in the foreground,
            for the demo, we will broadcast the notification to let the main
            activity display it in a dialog.
             */

            if (NotificationClient.PushResult.APP_IN_FOREGROUND == pushResult) {
                val dataMap: HashMap<String, String> = HashMap(message.data)
                message.from?.let {
                    broadcast(it, dataMap)
                }
            }
        }
    }

    private fun broadcast(from: String, dataMap: HashMap<String, String>) {
        val intent = Intent(actionPushNotification)
        intent.putExtra(INTENT_SNS_NOTIFICATION_FROM, from)
        intent.putExtra(INTENT_SNS_NOTIFICATION_DATA, dataMap)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    private fun getPinpointManager(): PinpointManager? {
        val pinpointConfig = storageActivity.getPinpointConfig()
        return getPinpointManager(pinpointConfig)
    }
}