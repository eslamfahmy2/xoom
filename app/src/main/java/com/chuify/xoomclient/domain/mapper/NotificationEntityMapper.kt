package com.chuify.xoomclient.domain.mapper

import com.chuify.xoomclient.data.local.entity.NotificationEntity
import com.chuify.xoomclient.data.remote.dto.NotificationDto
import com.chuify.xoomclient.domain.model.Notification

class NotificationEntityMapper : DomainMapper<NotificationEntity, Notification> {
    override fun mapToDomainModel(model: NotificationEntity): Notification {
        return Notification(
            id = model.id,
            title = model.title,
            time = model.time,
            status = model.status,
            description = model.description,
            open = model.open,
            orderId = model.orderId
        )
    }

    fun mapToDataModel(model: Notification): NotificationEntity {
        return NotificationEntity(
            id = model.id,
            title = model.title,
            time = model.time,
            status = model.status,
            description = model.description,
            open = model.open,
            orderId = model.orderId
        )
    }

    fun dtoToEntity(model: NotificationDto, time: String, open: Boolean): NotificationEntity {
        return NotificationEntity(
            id = model.notificationId!!,
            title = model.title!!,
            time = time,
            status = model.orderstatus!!,
            description = model.description!!,
            open = open,
            orderId = model.orderId!!
        )
    }
}