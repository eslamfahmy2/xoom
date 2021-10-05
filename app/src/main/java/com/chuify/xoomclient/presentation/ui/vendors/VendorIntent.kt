package com.chuify.xoomclient.presentation.ui.vendors


sealed class VendorIntent {
    object LoadVendors : VendorIntent()
    data class Filter(val searchText: String) : VendorIntent()
}
