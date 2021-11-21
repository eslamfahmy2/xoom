package com.chuify.cleanxoomclient.presentation.ui.notification

import com.chuify.cleanxoomclient.domain.model.Notification


sealed class NotificationIntent {
    object LoadNotifications : NotificationIntent()
    data class MarkRead(val notification: Notification) : NotificationIntent()
}
