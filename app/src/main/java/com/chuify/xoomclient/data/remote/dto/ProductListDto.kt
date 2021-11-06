package com.chuify.xoomclient.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProductListDto(

    @Expose
    @SerializedName(value = "Status", alternate = ["status"])
    val status: String?,

    @Expose
    @SerializedName(value = "Msg", alternate = ["msg", "message", "Message"])
    val msg: String?,

    @Expose
    @SerializedName(value = "products", alternate = ["Products"])
    val products: List<ProductDto>?,

    )

data class ProductDto(

    @Expose
    @SerializedName(value = "product_id", alternate = ["Product_id"])
    val product_id: String?,

    @Expose
    @SerializedName(value = "product_name", alternate = ["Product_name"])
    val product_name: String?,

    @Expose
    @SerializedName(value = "image", alternate = ["Image"])
    val image: String?,

    @Expose
    @SerializedName(value = "product_size", alternate = ["Product_size"])
    val product_size: String?,

    @Expose
    @SerializedName(value = "refill_new", alternate = ["Refill_new"])
    val refill_new: String?,

    @Expose
    @SerializedName(value = "selling_price", alternate = ["Selling_price"])
    val selling_price: String?,

    @Expose
    @SerializedName(value = "quantity", alternate = ["Quantity"])
    val quantity: String?,




    )