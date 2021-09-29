package com.chuify.xoomclient.presentation.ui.signup

sealed class SignUpIntent {

    object SignUp : SignUpIntent()
    object SignIn : SignUpIntent()
}
