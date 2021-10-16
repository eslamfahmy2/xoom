package com.chuify.xoomclient.presentation.ui.checkout

import com.chuify.xoomclient.domain.model.CartPreview
import com.chuify.xoomclient.domain.model.Cart


sealed class CheckoutState {
    data class Success(val orders: List<Cart>, val cartPreview: CartPreview) : CheckoutState()
    data class Error(val message: String? = null) : CheckoutState()
    object Loading : CheckoutState()
}