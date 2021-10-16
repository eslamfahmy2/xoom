package com.chuify.xoomclient.presentation.ui.order.complet

import com.chuify.xoomclient.domain.model.Order


sealed class CompletedOrdersState {
    data class Success(val orders: List<Order>) : CompletedOrdersState()
    data class Error(val message: String? = null) : CompletedOrdersState()
    object Loading : CompletedOrdersState()
}