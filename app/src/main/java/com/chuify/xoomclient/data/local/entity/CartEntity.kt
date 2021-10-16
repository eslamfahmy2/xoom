package com.chuify.xoomclient.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Order")
data class CartEntity(

    @PrimaryKey
    val id: String,

    val name: String = "",

    val price: Double = 0.0,

    val basePrice: Double = 0.0,

    val quantity: Int =0,

    val time: String ="",

    val image: String ="",
)