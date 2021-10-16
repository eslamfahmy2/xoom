package com.chuify.xoomclient.data.remote.dto

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
