package com.chuify.xoomclient.domain.usecase.auth

data class AuthInteraction(
    val signIn: SignInUseCase,
    val signUp: SignUpUseCase,
)
