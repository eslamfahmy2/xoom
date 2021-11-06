package com.chuify.xoomclient.domain.model

import com.chuify.xoomclient.data.remote.dto.ProductDto

data class Order(
    val id: String,

    val image: String,
    val name: String,
    val refill: String,
    val size: String,
    val price: String,
    val status: String,

    val locationID: String ,

    val paymentMethod: String ,

    val totalPrice: String ,

    val products : List<ProductDto>


    )