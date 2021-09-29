package com.chuify.xoomclient.presentation.ui.signup

sealed class SignUpIntent {
    class FirstNameChange(data: String) : SignUpIntent()
    class LastNameChange(data: String) : SignUpIntent()
    class EmailNameChange(data: String) : SignUpIntent()
    object SignUp : SignUpIntent()
    object SignIn : SignUpIntent()
}
