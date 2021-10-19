package com.chuify.xoomclient.presentation.ui.order.complet

import com.chuify.xoomclient.domain.model.Order


sealed class CompletedOrdersIntent {
    object LoadCompletedOrders : CompletedOrdersIntent()
    data class Reorder(val order: Order) : CompletedOrdersIntent()
}
