package com.chuify.cleanxoomclient.presentation.ui.profile

import com.chuify.cleanxoomclient.domain.model.User


sealed class ProfileState {

    data class MyError(val message: String? = null) : ProfileState()

    data class Success(
        val user: User? = null,
        val logged: Boolean = false,
        val error: MyError? = null
    ) : ProfileState()

    object Loading : ProfileState()

}
