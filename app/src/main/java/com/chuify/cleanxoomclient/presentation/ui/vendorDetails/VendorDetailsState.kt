package com.chuify.cleanxoomclient.presentation.ui.vendorDetails

import com.chuify.cleanxoomclient.domain.model.CartPreview


sealed class VendorDetailsState {
    data class Success(val data: CartPreview) : VendorDetailsState()
    data class Error(val message: String? = null) : VendorDetailsState()
    object Loading : VendorDetailsState()
}