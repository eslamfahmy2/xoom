package com.chuify.cleanxoomclient.presentation.ui.optConfrim

import com.chuify.cleanxoomclient.domain.model.User


sealed class OTPState {

    data class Success(val data: User? = null) : OTPState()
    data class Error(val message: String? = null) : OTPState()
    object Loading : OTPState()
    object Idl : OTPState()
}