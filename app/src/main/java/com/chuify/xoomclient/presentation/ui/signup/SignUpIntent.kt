package com.chuify.xoomclient.presentation.ui.signup

sealed class SignUpIntent {
    data class FirstNameChange(val data: String) : SignUpIntent()
    data class LastNameChange(val data: String) : SignUpIntent()
    data class EmailChange(val data: String) : SignUpIntent()
    object SignUp : SignUpIntent()
    object SignIn : SignUpIntent()
    object Idl : SignUpIntent()
}
