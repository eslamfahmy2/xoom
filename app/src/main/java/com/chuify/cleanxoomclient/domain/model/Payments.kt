package com.chuify.cleanxoomclient.domain.model

import androidx.annotation.DrawableRes
import com.chuify.cleanxoomclient.R

sealed class Payments(val name: String, @DrawableRes val icon: Int) {

    object CashOnDelivery : Payments("Cash on delivery", R.drawable.ic_cash)
    object MPESA : Payments("MPESA", R.drawable.ic_visa)
}
