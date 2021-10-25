package com.chuify.xoomclient.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notification")
data class NotificationEntity(

    @PrimaryKey
    val id: String,
    val orderId: String,
    val status: String,
    val title: String,
    val description: String,
    val time: String,
    val open: Boolean,

    )
