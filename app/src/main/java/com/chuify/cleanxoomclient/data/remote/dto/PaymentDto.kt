package com.chuify.cleanxoomclient.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PaymentDto(

    @Expose
    @SerializedName(value = "description")
    val description: String?,

    @Expose
    @SerializedName(value = "status")
    val status: Boolean?,
)
