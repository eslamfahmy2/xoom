package com.chuify.xoomclient.presentation.ui.order.pending


sealed class PendingOrdersIntent {
    object LoadPendingOrders : PendingOrdersIntent()
}
