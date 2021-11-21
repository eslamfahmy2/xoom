package com.chuify.cleanxoomclient.presentation.ui.order.pending

import com.chuify.cleanxoomclient.domain.model.Order


sealed class PendingOrdersIntent {
    object LoadPendingOrders : PendingOrdersIntent()
    data class ShowCancel(val order: Order) : PendingOrdersIntent()
    data class ConfirmCancel(val reason : String) : PendingOrdersIntent()
    object DismissCancel : PendingOrdersIntent()
    data class Track(val order: Order) : PendingOrdersIntent()
}
