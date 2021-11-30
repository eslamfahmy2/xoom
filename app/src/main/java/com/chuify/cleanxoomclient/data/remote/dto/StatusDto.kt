package com.chuify.cleanxoomclient.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StatusDto(
    @Expose
    @SerializedName(value = "Status", alternate = ["status"])
    val status: String?,

    @Expose
    @SerializedName(value = "Msg", alternate = ["msg", "message", "Message"])
    val msg: String?,

    )


data class SubmitOrderDto(
    @Expose
    @SerializedName(value = "Status")
    val status: Boolean = false,

    @Expose
    @SerializedName(value = "Msg")
    val msg: String?,

    @Expose
    @SerializedName(value = "Order")
    val Order: OrderEntryDto?,

    )

data class OrderEntryDto(

    @Expose
    @SerializedName(value = "order_id")
    val order_id: String?,
)

data class StatusBooleanDto(
    @Expose
    @SerializedName(value = "Status")
    val status: Boolean = false,

    @Expose
    @SerializedName(value = "Msg")
    val msg: String?,

    )