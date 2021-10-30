package com.chuify.xoomclient.presentation.ui.notification

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.xoomclient.domain.model.Notification
import com.chuify.xoomclient.domain.usecase.notification.GetNotReadCountUseCase
import com.chuify.xoomclient.domain.usecase.notification.GetNotificationListUseCase
import com.chuify.xoomclient.domain.usecase.notification.MarkAsReadUseCase
import com.chuify.xoomclient.domain.utils.DataState
import com.chuify.xoomclient.presentation.ui.signup.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getNotificationList: GetNotificationListUseCase,
    private val markAsReadUseCase: MarkAsReadUseCase,
    private val getNotReadCountUseCase: GetNotReadCountUseCase,
) : ViewModel() {

    val userIntent = Channel<NotificationIntent>(Channel.UNLIMITED)

    private val _state: MutableState<NotificationState> =
        mutableStateOf(NotificationState.Loading)
    val state get() = _state

    private val _notReadCount = mutableStateOf(0)
    val notReadCount get() = _notReadCount

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {

            userIntent.consumeAsFlow().collect { intent ->
                when (intent) {
                    NotificationIntent.LoadNotifications -> {
                        loadNotifications()
                    }
                    is NotificationIntent.MarkRead -> {
                        markAsRead(intent.notification)
                    }
                }
            }
        }
    }

    private fun loadNotReadNotifications() = viewModelScope.launch {
        getNotReadCountUseCase().collect { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    Log.d(TAG, "Error: " + dataState.message)
                }
                is DataState.Loading -> {
                }
                is DataState.Success -> {
                    dataState.data?.let {
                        _notReadCount.value = it
                    }
                }
            }
        }
    }


    private fun loadNotifications() = viewModelScope.launch {
        getNotificationList().collect { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    Log.d(TAG, "Error: " + dataState.message)
                    _state.value = NotificationState.Error(dataState.message)
                }
                is DataState.Loading -> {
                    _state.value = NotificationState.Loading
                }
                is DataState.Success -> {
                    dataState.data?.let {
                        _state.value = NotificationState.Success(notifications = it)
                    }

                }
            }
        }
    }

    private fun markAsRead(notification: Notification) = viewModelScope.launch {
        markAsReadUseCase(notification).collect { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    Log.d(TAG, "Error: " + dataState.message)
                    _state.value = NotificationState.Error(dataState.message)
                }
                is DataState.Loading -> {
                    _state.value = NotificationState.Loading
                }
                is DataState.Success -> {
                    Log.d(TAG, "marked suuessfully: ")
                }
            }
        }
    }


}