package com.chuify.xoomclient.presentation.ui.notification

import com.chuify.xoomclient.domain.model.Notification


sealed class NotificationIntent {
    object LoadNotifications : NotificationIntent()
    data class MarkRead(val notification: Notification) : NotificationIntent()
}
