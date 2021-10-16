package com.chuify.xoomclient.presentation.ui.cart

import com.chuify.xoomclient.domain.model.Order


sealed class CartIntent {
    object LoadCart : CartIntent()
    data class IncreaseItem(val order: Order) : CartIntent()
    data class DecreaseItem(val order: Order) : CartIntent()
    data class DeleteItem(val order: Order) : CartIntent()
}
