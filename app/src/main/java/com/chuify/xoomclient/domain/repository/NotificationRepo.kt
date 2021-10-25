package com.chuify.xoomclient.domain.repository

import com.chuify.xoomclient.data.local.entity.NotificationEntity
import com.chuify.xoomclient.data.remote.dto.NotificationListDto
import com.chuify.xoomclient.domain.utils.ResponseState
import kotlinx.coroutines.flow.Flow

interface NotificationRepo {

    suspend fun listNotifications(): Flow<List<NotificationEntity>>

    suspend fun getNotReadNotificationCount(): Flow<Int>

    suspend fun markAsRead(notification: NotificationEntity)

    suspend fun listNotificationApi(): ResponseState<NotificationListDto>

    suspend fun insert(entity: NotificationEntity)

}