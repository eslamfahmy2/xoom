package com.chuify.cleanxoomclient.presentation.ui.vendors

import com.chuify.cleanxoomclient.domain.model.Vendor


sealed class VendorState {

    data class Success(val data: List<Vendor> = listOf(), val searchText: String) : VendorState()
    data class Error(val message: String? = null) : VendorState()
    object Loading : VendorState()
}