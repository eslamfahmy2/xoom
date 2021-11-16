package com.chuify.xoomclient.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TrackDto(

    @Expose
    @SerializedName(value = "error", alternate = ["Error"])
    val error: String?,

    @Expose
    @SerializedName(value = "msg", alternate = ["Msg"])
    val msg: String?,

    )