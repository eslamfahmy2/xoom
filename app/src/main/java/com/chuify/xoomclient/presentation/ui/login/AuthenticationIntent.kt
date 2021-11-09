package com.chuify.xoomclient.presentation.ui.login


sealed class AuthenticationIntent {
    data class PhoneChange(val data: String) : AuthenticationIntent()
    data class FirstNameChange(val data: String) : AuthenticationIntent()
    data class LastNameChange(val data: String) : AuthenticationIntent()
    data class EmailChange(val data: String) : AuthenticationIntent()
    object SignUp : AuthenticationIntent()
    object SignIn : AuthenticationIntent()
}
