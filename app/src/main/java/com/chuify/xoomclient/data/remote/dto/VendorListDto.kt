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
    @SerializedName(value = "Vendors", alternate = ["vendors"])
    val vendors: List<VendorDto>,
)

data class VendorDto(
    @Expose
    @SerializedName(value = "Vendor_id", alternate = ["vendor_id"])
    val vendor_id: String?,

    @Expose
    @SerializedName(value = "Vendor_name", alternate = ["vendor_name"])
    val vendor_name: String?,

    @Expose
    @SerializedName(value = "Image", alternate = ["image"])
    val image: String?,
)