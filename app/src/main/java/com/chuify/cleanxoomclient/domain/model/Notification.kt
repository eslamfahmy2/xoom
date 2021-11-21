package com.chuify.cleanxoomclient.domain.model

data class Notification(
    val id: String,
    val orderId: String,
    val status: String,
    val title: String,
    val description: String,
    val time: String,
    val open: Boolean,
)
