package com.chuify.cleanxoomclient.domain.usecase.notification

import com.chuify.cleanxoomclient.domain.mapper.NotificationEntityMapper
import com.chuify.cleanxoomclient.domain.model.Notification
import com.chuify.cleanxoomclient.domain.repository.NotificationRepo
import com.chuify.cleanxoomclient.domain.utils.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class MarkAsReadUseCase @Inject constructor(
    private val notificationRepo: NotificationRepo,
    private val notificationEntityMapper: NotificationEntityMapper,
) {

    suspend operator fun invoke(notification: Notification) = flow<DataState<Any>> {
        try {
            emit(DataState.Loading())
            val temp = notificationEntityMapper.mapToDataModel(notification).copy(open = true)
            notificationRepo.markAsRead(notification = temp)
            emit(DataState.Success())
        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }.flowOn(Dispatchers.IO).conflate()
}