package com.chuify.cleanxoomclient.presentation.ui.accessoryDetails

import com.chuify.cleanxoomclient.domain.model.Accessory


sealed class AccessoryDetailsState {
    data class Success(val data: Accessory? = Accessory()) : AccessoryDetailsState()
    data class Error(val message: String? = null) : AccessoryDetailsState()
    object Dismiss : AccessoryDetailsState()
}