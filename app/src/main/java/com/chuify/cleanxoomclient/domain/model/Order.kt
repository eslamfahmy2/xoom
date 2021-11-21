package com.chuify.cleanxoomclient.domain.model

import com.chuify.cleanxoomclient.data.remote.dto.ProductDto

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