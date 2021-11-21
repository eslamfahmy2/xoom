package com.chuify.cleanxoomclient.presentation.ui.checkout


sealed class CheckoutState {
    object Success : CheckoutState()
    data class Error(val message: String? = null) : CheckoutState()
    object Loading : CheckoutState()
    object LoadingSubmit : CheckoutState()
    object OrderSubmitted : CheckoutState()
}