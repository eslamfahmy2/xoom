package com.chuify.xoomclient.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TrackDto(

    @Expose
    @SerializedName(value = "driverImg", alternate = ["driverImg"])
    val driverImg: String?,

    @Expose
    @SerializedName(value = "driverPlateNumber", alternate = ["driverPlateNumber"])
    val driverPlateNumber: String?,

    @Expose
    @SerializedName(value = "driverRating", alternate = ["driverRating"])
    val driverRating: String?,

    @Expose
    @SerializedName(value = "driverNumberOfTrips", alternate = ["driverNumberOfTrips"])
    val stdriverNumberOfTripsatus: String?,

    @Expose
    @SerializedName(value = "driverPhone", alternate = ["driverPhone"])
    val driverPhone: String?,

    @Expose
    @SerializedName(value = "expectedDeliveryTime", alternate = ["expectedDeliveryTime"])
    val expectedDeliveryTime: String?,

    @Expose
    @SerializedName(value = "deliveryLocationLat", alternate = ["deliveryLocationLat"])
    val deliveryLocationLat: String?,


    @Expose
    @SerializedName(value = "deliveryLocationLng", alternate = ["deliveryLocationLng"])
    val deliveryLocationLng: String?,


    @Expose
    @SerializedName(value = "driverCurrentLat", alternate = ["driverCurrentLat"])
    val driverCurrentLat: String?,

    @Expose
    @SerializedName(value = "driverFirstName", alternate = ["driverFirstName"])
    val driverFirstName: String?,


    @Expose
    @SerializedName(value = "driverLastName", alternate = ["driverLastName"])
    val driverLastName: String?,

    @Expose
    @SerializedName(value = "deliveryLocationDetails", alternate = ["deliveryLocationDetails"])
    val deliveryLocationDetails: String?,


    )