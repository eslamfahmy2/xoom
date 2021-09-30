package com.chuify.xoomclient.presentation.ui.signup

import com.chuify.xoomclient.domain.model.User


sealed class SignUpState {

    data class Success(val data: User? = null) : SignUpState()
    data class Error(val message: String? = null) : SignUpState()
    object Loading : SignUpState()
    object Idl : SignUpState()
}