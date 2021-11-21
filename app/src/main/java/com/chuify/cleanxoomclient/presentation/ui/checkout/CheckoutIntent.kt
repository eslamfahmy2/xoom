package com.chuify.cleanxoomclient.presentation.ui.checkout

import com.chuify.cleanxoomclient.domain.model.Cart
import com.chuify.cleanxoomclient.domain.model.Payments


sealed class CheckoutIntent {

    object LoadCart : CheckoutIntent()
    object ConfirmOrder : CheckoutIntent()
    data class ChangePayment(val payment: Payments) : CheckoutIntent()

    data class OnLocationSelect(val id: String) : CheckoutIntent()

    data class IncreaseItem(val order: Cart) : CheckoutIntent()
    data class DecreaseItem(val order: Cart) : CheckoutIntent()
    data class ChangeStatus(val state: CheckoutState) : CheckoutIntent()


}
