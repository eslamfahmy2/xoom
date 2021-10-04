package com.chuify.xoomclient.presentation.ui.vendors


sealed class VendorIntent {
    data class PhoneChange(val data: String) : VendorIntent()
    object SignIn : VendorIntent()
}
