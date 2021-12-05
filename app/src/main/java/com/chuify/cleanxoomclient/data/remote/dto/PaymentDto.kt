package com.chuify.cleanxoomclient.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PaymentDto(

    @Expose
    @SerializedName(value = "msg")
    val msg: String?,

    @Expose
    @SerializedName(value = "status")
    val status: Int,
)
