package com.chuify.cleanxoomclient.presentation.ui.verify


sealed class VerifyState {

    data class Success(val data: String? = null) : VerifyState()
    data class Error(val message: String? = null) : VerifyState()
    object Loading : VerifyState()
    object Idl : VerifyState()
}