package com.chuify.cleanxoomclient.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class AccessoryListDto(

    @Expose
    @SerializedName(value = "Status", alternate = ["status"])
    val status: String?,

    @Expose
    @SerializedName(value = "Msg", alternate = ["msg", "message", "Message"])
    val msg: String?,

    @Expose
    @SerializedName(value = "accessories", alternate = ["Accessories"])
    val accessories: List<AccessoryDto>?,

    )

data class AccessoryDto(

    @Expose
    @SerializedName(value = "product_name", alternate = ["Product_name"])
    val product_name: String?,

    @Expose
    @SerializedName(value = "product_id", alternate = ["Product_id"])
    val product_id: String?,

    @Expose
    @SerializedName(value = "image", alternate = ["Image"])
    val image: String?,

    @Expose
    @SerializedName(value = "selling_price", alternate = ["Selling_price"])
    val selling_price: String?,
)