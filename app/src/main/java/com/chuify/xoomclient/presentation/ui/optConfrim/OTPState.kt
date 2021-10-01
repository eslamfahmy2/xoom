package com.chuify.xoomclient.presentation.ui.optConfrim

import com.chuify.xoomclient.domain.model.User


sealed class OTPState {

    data class Success(val data: User? = null) : OTPState()
    data class Error(val message: String? = null) : OTPState()
    object Loading : OTPState()
    object Idl : OTPState()
}