package com.chuify.cleanxoomclient.presentation.ui.checkout


sealed class CheckoutState {


    object Loading : CheckoutState()

    sealed class Error : CheckoutState() {
        data class SubmitOrderError(val message: String? = null) : Error()
        data class PaymentError(val message: String? = null) : Error()
    }

    sealed class Success : CheckoutState() {
        data class PaymentSuccess(val message: String? = null) : CheckoutState()
        data class OrderSubmitted(val message: String? = null) : CheckoutState()
    }

}