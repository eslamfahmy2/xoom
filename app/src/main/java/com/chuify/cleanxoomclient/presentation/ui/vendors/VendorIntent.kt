package com.chuify.cleanxoomclient.presentation.ui.vendors


sealed class VendorIntent {
    object LoadVendors : VendorIntent()
    data class Filter(val searchText: String) : VendorIntent()

}
