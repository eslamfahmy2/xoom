package com.chuify.xoomclient.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class VendorListDto(

    @Expose
    @SerializedName(value = "Status", alternate = ["status"])
    val status: String?,

    @Expose
    @SerializedName(value = "Msg", alternate = ["msg", "message", "Message"])
    val msg: String?,

    @Expose
    @SerializedName(value = "vendors", alternate = ["Vendors"])
    val vendors: List<VendorDto>?,

    )

data class VendorDto(
    @Expose
    @SerializedName(value = "vendor_id", alternate = ["Vendor_id"])
    val vendor_id: String?,

    @Expose
    @SerializedName(value = "vendor_name", alternate = ["Vendor_name"])
    val vendor_name: String?,

    @Expose
    @SerializedName(value = "image", alternate = ["Image"])
    val image: String?,
)