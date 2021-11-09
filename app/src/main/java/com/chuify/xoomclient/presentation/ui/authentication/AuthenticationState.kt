package com.chuify.xoomclient.presentation.ui.authentication

import com.chuify.xoomclient.domain.usecase.auth.LoginResult


sealed class AuthenticationState {
    data class Success(val data: LoginResult) : AuthenticationState()
    data class Error(val message: String? = null) : AuthenticationState()
    object Loading : AuthenticationState()
    object Idl : AuthenticationState()
}