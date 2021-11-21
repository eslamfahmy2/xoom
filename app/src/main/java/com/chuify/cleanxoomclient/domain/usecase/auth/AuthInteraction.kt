package com.chuify.cleanxoomclient.domain.usecase.auth

data class AuthInteraction(
    val signIn: SignInUseCase,
    val signUp: SignUpUseCase,
)
