package com.chuify.xoomclient.presentation.ui.signup

import com.chuify.xoomclient.domain.model.User
import com.chuify.xoomclient.presentation.ui.login.LoginState


sealed class SignUpState {

    data class Success(val data: User? = null) : SignUpState()
    data class Error(val message: String? = null) : SignUpState()
    object Loading : SignUpState()
    object Idl : SignUpState()
}