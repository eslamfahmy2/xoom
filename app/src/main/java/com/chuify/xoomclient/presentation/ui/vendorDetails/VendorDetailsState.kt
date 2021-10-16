package com.chuify.xoomclient.presentation.ui.vendorDetails

import com.chuify.xoomclient.domain.model.CartPreview


sealed class VendorDetailsState {
    data class Success(val data: CartPreview) : VendorDetailsState()
    data class Error(val message: String? = null) : VendorDetailsState()
    object Loading : VendorDetailsState()
}