package com.chuify.xoomclient.presentation.ui.verify

import android.app.Activity


sealed class VerifyIntent {
    data class PhoneChange(val data: String) : VerifyIntent()
    data class Verify(val activity: Activity) : VerifyIntent()
    object Idl : VerifyIntent()
}
