package com.chuify.xoomclient.presentation.ui.cart

import com.chuify.xoomclient.domain.model.CartPreview
import com.chuify.xoomclient.domain.model.Order


sealed class CartState {
    data class Success(val orders: List<Order>, val cartPreview: CartPreview) : CartState()
    data class Error(val message: String? = null) : CartState()
    object Loading : CartState()
}