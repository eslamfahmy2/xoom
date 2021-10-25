package com.chuify.xoomclient.domain.usecase.notification

import com.chuify.xoomclient.domain.repository.NotificationRepo
import com.chuify.xoomclient.domain.utils.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class GetNotReadCountUseCase @Inject constructor(
    private val notificationRepo: NotificationRepo,
) {

    suspend operator fun invoke() = flow<DataState<Int>> {
        try {
            emit(DataState.Loading())
            notificationRepo.getNotReadNotificationCount().collect {
                emit(DataState.Success(it))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }.flowOn(Dispatchers.IO).conflate()
}