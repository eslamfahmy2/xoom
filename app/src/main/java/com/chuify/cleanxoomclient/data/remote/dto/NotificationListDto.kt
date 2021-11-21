package com.chuify.cleanxoomclient.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class NotificationListDto(

    @Expose
    @SerializedName(value = "Notifications", alternate = ["notifications"])
    val notifications: List<NotificationDto>?,

    )

data class NotificationDto(

    @Expose
    @SerializedName(value = "notificationId", alternate = ["NotificationId"])
    val notificationId: String?,


    @Expose
    @SerializedName(value = "orderId", alternate = ["OrderId"])
    val orderId: String?,


    @Expose
    @SerializedName(value = "orderstatus", alternate = ["Orderstatus"])
    val orderstatus: String?,


    @Expose
    @SerializedName(value = "title", alternate = ["Title"])
    val title: String?,

    @Expose
    @SerializedName(value = "description", alternate = ["Description"])
    val description: String?,

    @Expose
    @SerializedName(value = "date", alternate = ["Date"])
    val date: String?,

    )

