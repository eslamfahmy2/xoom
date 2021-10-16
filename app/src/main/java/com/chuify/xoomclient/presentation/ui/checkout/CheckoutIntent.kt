package com.chuify.xoomclient.presentation.ui.checkout

import com.chuify.xoomclient.domain.model.Location
import com.chuify.xoomclient.domain.model.Cart
import com.chuify.xoomclient.domain.model.Payments


sealed class CheckoutIntent {

    object LoadCart : CheckoutIntent()
    object ConfirmOrder : CheckoutIntent()
    data class ChangePayment(val payment: Payments) : CheckoutIntent()

    data class OnLocationSelect(val location: Location) : CheckoutIntent()

    data class IncreaseItem(val order: Cart) : CheckoutIntent()
    data class DecreaseItem(val order: Cart) : CheckoutIntent()
    data class DeleteItem(val order: Cart) : CheckoutIntent()


}
