package com.chuify.xoomclient.domain.model

import androidx.annotation.DrawableRes
import com.chuify.xoomclient.R

sealed class Payments(val name: String, @DrawableRes val icon: Int) {

    object Points : Payments("Points", R.drawable.ic_points)
    object CashOnDelivery : Payments("Cash on delivery", R.drawable.ic_cash)
    object MPESA : Payments("Mpesa", R.drawable.ic_visa)
}
