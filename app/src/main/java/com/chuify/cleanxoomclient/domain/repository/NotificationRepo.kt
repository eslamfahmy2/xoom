package com.chuify.cleanxoomclient.domain.repository

import com.chuify.cleanxoomclient.data.local.entity.NotificationEntity
import com.chuify.cleanxoomclient.data.remote.dto.NotificationListDto
import com.chuify.cleanxoomclient.domain.utils.ResponseState
import kotlinx.coroutines.flow.Flow

interface NotificationRepo {

    suspend fun listNotifications(): Flow<List<NotificationEntity>>

    suspend fun getNotReadNotificationCount(): Flow<Int>

    suspend fun markAsRead(notification: NotificationEntity)

    suspend fun listNotificationApi(): ResponseState<NotificationListDto>

    suspend fun insert(entity: NotificationEntity)

    suspend fun clear()

}