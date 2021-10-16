package com.chuify.xoomclient.domain.model

data class Cart(

    val id: String,

    val name: String,

    val price: Double,

    val basePrice: Double,

    val quantity: Int,

    val time: String,

    val image: String,
)
