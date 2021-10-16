package com.chuify.xoomclient.presentation.ui.order.complet


sealed class CompletedOrdersIntent {
    object LoadCompletedOrders : CompletedOrdersIntent()
}
