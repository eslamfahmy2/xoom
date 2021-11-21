package com.chuify.cleanxoomclient.presentation.ui.notification

import com.chuify.cleanxoomclient.domain.model.Notification


sealed class NotificationState {
    data class Success(val notifications: List<Notification>) : NotificationState()
    data class Error(val message: String? = null) : NotificationState()
    object Loading : NotificationState()
}