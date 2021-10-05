package com.chuify.xoomclient.presentation.ui.accessory


sealed class AccessoryIntent {
    object LoadVendors : AccessoryIntent()
    data class Filter(val searchText: String) : AccessoryIntent()
}
