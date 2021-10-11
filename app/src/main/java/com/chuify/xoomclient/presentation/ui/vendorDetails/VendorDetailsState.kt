package com.chuify.xoomclient.presentation.ui.vendorDetails

import com.chuify.xoomclient.domain.usecase.cart.CartPreview


sealed class VendorDetailsState {
    data class Success(val data: CartPreview) : VendorDetailsState()
    data class Error(val message: String? = null) : VendorDetailsState()
    object Loading : VendorDetailsState()
}