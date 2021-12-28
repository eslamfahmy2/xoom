package com.chuify.cleanxoomclient.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TrackDto(


    @Expose
    @SerializedName(value = "orderstatus")
    val orderstatus: String?,

    @Expose
    @SerializedName(value = "error", alternate = ["Error"])
    val error: String?,

    @Expose
    @SerializedName(value = "msg", alternate = ["Msg"])
    val msg: String?,

    @Expose
    @SerializedName(value = "deliveryLocationDetails")
    val deliveryLocationDetails: String?,

    @Expose
    @SerializedName(value = "deliveryLocationLat")
    val deliveryLocationLat: String?,

    @Expose
    @SerializedName(value = "deliveryLocationLng")
    val deliveryLocationLng: String?,

    @Expose
    @SerializedName(value = "deliveryLocationTitle")
    val deliveryLocationTitle: String?,

    @Expose
    @SerializedName(value = "driverCurrentLat")
    val driverCurrentLat: String?,

    @Expose
    @SerializedName(value = "driverCurrentLng")
    val driverCurrentLng: String?,

    @Expose
    @SerializedName(value = "driverFirstName")
    val driverFirstName: String?,

    @Expose
    @SerializedName(value = "driverImg")
    val driverImg: String?,

    @Expose
    @SerializedName(value = "driverLastName")
    val driverLastName: String?,

    @Expose
    @SerializedName(value = "driverNumberOfTrips")
    val driverNumberOfTrips: String?,

    @Expose
    @SerializedName(value = "driverPhone")
    val driverPhone: String?,

    @Expose
    @SerializedName(value = "driverPlateNumber")
    val driverPlateNumber: String?,

    @Expose
    @SerializedName(value = "driverRating")
    val driverRating: String?,

    @Expose
    @SerializedName(value = "expectedDeliveryTime")
    val expectedDeliveryTime: String?

) {

    fun getDriverName(): String = "$driverFirstName  $driverLastName"
}