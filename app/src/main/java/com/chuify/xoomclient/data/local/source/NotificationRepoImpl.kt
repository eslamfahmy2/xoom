package com.chuify.xoomclient.data.local.source

import com.chuify.xoomclient.data.local.dao.NotificationDao
import com.chuify.xoomclient.data.local.entity.NotificationEntity
import com.chuify.xoomclient.data.remote.dto.NotificationListDto
import com.chuify.xoomclient.data.remote.network.ApiInterface
import com.chuify.xoomclient.domain.repository.NotificationRepo
import com.chuify.xoomclient.domain.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotificationRepoImpl @Inject constructor(
    private val dp: NotificationDao,
    private val apiInterface: ApiInterface,
) : NotificationRepo {
    override suspend fun listNotifications(): Flow<List<NotificationEntity>> {
        return dp.getAll()
    }

    override suspend fun getNotReadNotificationCount(): Flow<Int> {
        return dp.getCountNotRead()
    }

    override suspend fun markAsRead(notification: NotificationEntity) {
        return dp.update(notification)
    }

    override suspend fun listNotificationApi(): ResponseState<NotificationListDto> = try {
        val response = apiInterface.listNotifications()
        if (response.isSuccessful) {
            response.body()?.let {
                ResponseState.Success(it)
            } ?: ResponseState.Error("api : boy is empty")
        } else {
            ResponseState.Error(response.errorBody()?.string())
        }
    } catch (e: Exception) {
        ResponseState.Error(e.message)
    }

    override suspend fun insert(entity: NotificationEntity) {
        return dp.insert(entity)
    }


}