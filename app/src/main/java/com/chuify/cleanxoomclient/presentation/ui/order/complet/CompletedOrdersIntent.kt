package com.chuify.cleanxoomclient.presentation.ui.order.complet

import com.chuify.cleanxoomclient.domain.model.Order


sealed class CompletedOrdersIntent {
    object LoadCompletedOrders : CompletedOrdersIntent()
    data class Reorder(val order: Order) : CompletedOrdersIntent()
}
