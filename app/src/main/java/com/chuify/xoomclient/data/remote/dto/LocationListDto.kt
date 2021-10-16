package com.chuify.xoomclient.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LocationListDto(

    @Expose
    @SerializedName(value = "Status", alternate = ["status"])
    val status: String?,

    @Expose
    @SerializedName(value = "Msg", alternate = ["msg", "message", "Message"])
    val msg: String?,

    @Expose
    @SerializedName(value = "addresses", alternate = ["Addresses"])
    val addresses: List<LocationDto>?,

    )


data class LocationDto(


    @Expose
    @SerializedName(value = "address_url", alternate = ["Address_url"])
    val address_url: String?,


    @Expose
    @SerializedName(value = "details", alternate = ["Details"])
    val details: String?,


    @Expose
    @SerializedName(value = "instructions", alternate = ["Instructions"])
    val instructions: String?,

    @Expose
    @SerializedName(value = "latitude", alternate = ["Latitude"])
    val latitude: String?,

    @Expose
    @SerializedName(value = "longitude", alternate = ["Longitude"])
    val longitude: String?,


    )