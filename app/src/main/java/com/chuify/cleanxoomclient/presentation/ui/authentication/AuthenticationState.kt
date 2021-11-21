package com.chuify.cleanxoomclient.presentation.ui.authentication

import com.chuify.cleanxoomclient.domain.usecase.auth.LoginResult


sealed class AuthenticationState {
    data class Success(val data: LoginResult) : AuthenticationState()
    data class Error(val message: String? = null) : AuthenticationState()
    object Loading : AuthenticationState()
    object Idl : AuthenticationState()
}