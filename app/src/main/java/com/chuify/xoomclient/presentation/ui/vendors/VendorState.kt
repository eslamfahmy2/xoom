package com.chuify.xoomclient.presentation.ui.vendors

import com.chuify.xoomclient.domain.model.User


sealed class VendorState {
    data class Success(val data: User? = null) : VendorState()
    data class Error(val message: String? = null) : VendorState()
    object Loading : VendorState()
    object Idl : VendorState()
}