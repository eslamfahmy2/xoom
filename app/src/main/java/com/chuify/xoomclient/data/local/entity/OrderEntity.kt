package com.chuify.xoomclient.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Order")
data class OrderEntity(

    @PrimaryKey
    val id: String,

    val name: String,

    val price: Double,

    val basePrice: Double,

    val quantity: Int,

    val time: String,

    val image: String,
)