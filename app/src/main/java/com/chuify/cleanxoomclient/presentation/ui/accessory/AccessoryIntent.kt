package com.chuify.cleanxoomclient.presentation.ui.accessory

import com.chuify.cleanxoomclient.domain.model.Accessory


sealed class AccessoryIntent {
    object LoadVendors : AccessoryIntent()
    data class Filter(val searchText: String) : AccessoryIntent()
    data class IncreaseAccessoryCart(val accessory: Accessory) : AccessoryIntent()
    data class DecreaseAccessoryCart(val accessory: Accessory) : AccessoryIntent()
}
