package com.chuify.xoomclient.presentation.ui.login


sealed class LoginIntent {
    data class PhoneChange(val data: String) : LoginIntent()
    object SignIn : LoginIntent()
}
