package com.chuify.cleanxoomclient.presentation.ui.notification

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.cleanxoomclient.domain.model.Notification
import com.chuify.cleanxoomclient.domain.usecase.notification.GetNotReadCountUseCase
import com.chuify.cleanxoomclient.domain.usecase.notification.GetNotificationListUseCase
import com.chuify.cleanxoomclient.domain.usecase.notification.MarkAsReadUseCase
import com.chuify.cleanxoomclient.domain.utils.DataState

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "NotificationViewModel"
@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getNotificationList: GetNotificationListUseCase,
    private val markAsReadUseCase: MarkAsReadUseCase,
    private val getNotReadCountUseCase: GetNotReadCountUseCase,
) : ViewModel() {

    val userIntent = Channel<NotificationIntent>(Channel.UNLIMITED)

    private val _state: MutableStateFlow<NotificationState> =
        MutableStateFlow(NotificationState.Loading)
    val state get() = _state.asStateFlow()

    private val _notReadCount = MutableStateFlow(0)
    val notReadCount get() = _notReadCount.asStateFlow()

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {

            userIntent.consumeAsFlow().collect { intent ->
                when (intent) {
                    NotificationIntent.LoadNotifications -> {
                        loadNotifications()
                        loadNotReadNotifications()
                    }
                    is NotificationIntent.MarkRead -> {
                        markAsRead(intent.notification)
                    }
                }
            }
        }
    }

    private fun loadNotReadNotifications() = viewModelScope.launch(Dispatchers.IO) {
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


    private fun loadNotifications() = viewModelScope.launch(Dispatchers.IO) {
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
                        Log.d(TAG, "loadNotifications: $it")
                        _state.value = NotificationState.Success(notifications = it)
                    }

                }
            }
        }
    }

    private fun markAsRead(notification: Notification) = viewModelScope.launch(Dispatchers.IO) {
        markAsReadUseCase(notification).collect { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    Log.d(TAG, "Error: " + dataState.message)
                    _state.value = NotificationState.Error(dataState.message)
                }
                is DataState.Loading -> {
                    //  _state.value = NotificationState.Loading
                }
                is DataState.Success -> {
                    Log.d(TAG, "marked suuessfully: ")
                }
            }
        }
    }


}