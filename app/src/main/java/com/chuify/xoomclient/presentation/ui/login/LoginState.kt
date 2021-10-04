package com.chuify.xoomclient.presentation.ui.login

import com.chuify.xoomclient.domain.model.User


sealed class LoginState {
    data class Success(val data: User? = null) : LoginState()
    data class Error(val message: String? = null) : LoginState()
    object Loading : LoginState()
    object Idl : LoginState()
}