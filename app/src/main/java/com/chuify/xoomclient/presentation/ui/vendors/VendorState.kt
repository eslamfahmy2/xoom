package com.chuify.xoomclient.presentation.ui.vendors

import com.chuify.xoomclient.domain.model.Vendor


sealed class VendorState {
    data class Success(val data: List<Vendor> = listOf(), val searchText: String) : VendorState()
    data class Error(val message: String? = null) : VendorState()
    object Loading : VendorState()
}