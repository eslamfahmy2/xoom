package com.chuify.xoomclient.domain.model

data class Product(
    val id: String,
    val image: String,
    val name: String,
    val refill: String,
    val size: String,
    val price: Double,

    val quantity: Int = 0,

    )
