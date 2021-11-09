package com.chuify.xoomclient.presentation.ui.checkout

import com.chuify.xoomclient.domain.model.CartPreview
import com.chuify.xoomclient.domain.model.Cart


sealed class CheckoutState {
    object Success : CheckoutState()
    data class Error(val message: String? = null) : CheckoutState()
    object Loading : CheckoutState()
    object LoadingSubmit : CheckoutState()
    object OrderSubmitted : CheckoutState()
}