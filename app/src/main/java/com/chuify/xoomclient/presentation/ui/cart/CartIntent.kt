package com.chuify.xoomclient.presentation.ui.cart

import com.chuify.xoomclient.domain.model.Cart


sealed class CartIntent {
    object LoadCart : CartIntent()
    data class IncreaseItem(val order: Cart) : CartIntent()
    data class DecreaseItem(val order: Cart) : CartIntent()
    data class DeleteItem(val order: Cart) : CartIntent()
}
