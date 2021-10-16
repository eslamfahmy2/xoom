package com.chuify.xoomclient.presentation.ui.payment

import com.chuify.xoomclient.domain.model.Payments


sealed class PaymentState {
    data class Success(val method: Payments) : PaymentState()
    data class Error(val message: String? = null) : PaymentState()
    object Loading : PaymentState()
}