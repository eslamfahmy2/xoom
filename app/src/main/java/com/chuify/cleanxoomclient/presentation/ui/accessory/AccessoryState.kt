package com.chuify.cleanxoomclient.presentation.ui.accessory

import com.chuify.cleanxoomclient.domain.model.Accessory


sealed class AccessoryState {
    data class Success(val data: List<Accessory> = listOf()) : AccessoryState()
    data class Error(val message: String? = null) : AccessoryState()
    object Loading : AccessoryState()
}