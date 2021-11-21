package com.chuify.cleanxoomclient.presentation.ui.cart

import com.chuify.cleanxoomclient.domain.model.Cart
import com.chuify.cleanxoomclient.domain.model.CartPreview


sealed class CartState {
    data class Success(val orders: List<Cart>, val cartPreview: CartPreview) : CartState()
    data class Error(val message: String? = null) : CartState()
    object Loading : CartState()
}