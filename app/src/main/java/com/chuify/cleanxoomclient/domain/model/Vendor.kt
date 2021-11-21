package com.chuify.cleanxoomclient.domain.model

import java.io.Serializable


data class Vendor(
    val id: String,
    val name: String,
    val image: String,
) : Serializable
