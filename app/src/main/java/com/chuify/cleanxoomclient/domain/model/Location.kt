package com.chuify.cleanxoomclient.domain.model

data class Location(

    val id: String?,

    val title: String?,

    val details: String?,

    val latitude: String?,

    val longitude: String?,

    var selected: Boolean = false,

    )
