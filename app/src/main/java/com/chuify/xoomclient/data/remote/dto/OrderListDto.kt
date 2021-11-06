package com.chuify.xoomclient.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OrderListDto(

    @Expose
    @SerializedName(value = "Msg", alternate = ["msg"])
    val Msg: String?,

    @Expose
    @SerializedName(value = "Orders", alternate = ["orders"])
    val Orders: List<OrderDto>?,

    @Expose
    @SerializedName(value = "Status", alternate = ["status"])
    val Status: Boolean?,
)

data class OrderDto(

    @Expose
    @SerializedName(value = "Order_Date", alternate = ["order_Date"])
    val Order_Date: String?,

    @Expose
    @SerializedName(value = "deliveryDate", alternate = ["DeliveryDate"])
    val deliveryDate: String?,

    @Expose
    @SerializedName(value = "deliveryTime", alternate = ["DeliveryTime"])
    val deliveryTime: String?,

    @Expose
    @SerializedName(value = "gainedPoints", alternate = ["GainedPoints"])
    val gainedPoints: String?,

    @Expose
    @SerializedName(value = "location_id", alternate = ["Location_id"])
    val location_id: String?,

    @Expose
    @SerializedName(value = "order_id", alternate = ["Order_id"])
    val order_id: String?,

    @Expose
    @SerializedName(value = "orderdetails", alternate = ["Orderdetails"])
    val orderdetails: List<ProductDto>?,

    @Expose
    @SerializedName(value = "orderstatus", alternate = ["Orderstatus"])
    val orderstatus: String?,

    @Expose
    @SerializedName(value = "paymentMethod", alternate = ["PaymentMethod"])
    val paymentMethod: String?,

    @Expose
    @SerializedName(value = "totalprice", alternate = ["Totalprice"])
    val totalprice: String?,

    @Expose
    @SerializedName(value = "user_id", alternate = ["User_id"])
    val user_id: String?,




)