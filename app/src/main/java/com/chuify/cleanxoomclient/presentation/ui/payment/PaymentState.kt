package com.chuify.cleanxoomclient.presentation.ui.payment

import com.chuify.cleanxoomclient.domain.model.Payments


sealed class PaymentState {
    data class Success(val method: Payments) : PaymentState()
    data class Error(val message: String? = null) : PaymentState()
    object Loading : PaymentState()
}