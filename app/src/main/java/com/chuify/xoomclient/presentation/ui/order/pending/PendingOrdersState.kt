package com.chuify.xoomclient.presentation.ui.order.pending

import com.chuify.xoomclient.domain.model.Order


sealed class PendingOrdersState {
    data class Success(val orders: List<Order>) : PendingOrdersState()
    data class Error(val message: String? = null) : PendingOrdersState()
    object Loading : PendingOrdersState()
}