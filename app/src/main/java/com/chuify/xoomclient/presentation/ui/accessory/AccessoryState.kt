package com.chuify.xoomclient.presentation.ui.accessory

import com.chuify.xoomclient.domain.model.Accessory


sealed class AccessoryState {
    data class Success(val data: List<Accessory> = listOf()) : AccessoryState()
    data class Error(val message: String? = null) : AccessoryState()
    object Loading : AccessoryState()
}