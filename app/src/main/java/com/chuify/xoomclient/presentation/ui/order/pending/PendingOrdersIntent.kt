package com.chuify.xoomclient.presentation.ui.order.pending

import com.chuify.xoomclient.domain.model.Order


sealed class PendingOrdersIntent {
    object LoadPendingOrders : PendingOrdersIntent()
    data class Cancel(val order: Order) : PendingOrdersIntent()
    data class Track(val order: Order) : PendingOrdersIntent()
}
