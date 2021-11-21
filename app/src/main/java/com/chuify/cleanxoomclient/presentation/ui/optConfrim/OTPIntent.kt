package com.chuify.cleanxoomclient.presentation.ui.optConfrim


sealed class OTPIntent {
    data class PhoneChange(val data: String) : OTPIntent()
    object Verify : OTPIntent()
    object Idl : OTPIntent()
}
