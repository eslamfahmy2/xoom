package com.chuify.xoomclient.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoyaltyPointDto(

    @Expose
    @SerializedName(value = "loyalPoints", alternate = ["LoyalPoints"])
    val loyalPoints: String?,
)
