package com.chuify.xoomclient.presentation.ui.payment


sealed class PaymentIntent {

    object Points : PaymentIntent()
    object CreditCard : PaymentIntent()
    object CashOnDelivery : PaymentIntent()
    object Mpesa : PaymentIntent()
}
