package com.chuify.xoomclient.presentation.ui.profile

import com.chuify.xoomclient.domain.model.User


sealed class ProfileState {
    data class Success(val user: User) : ProfileState()
    data class Error(val message: String? = null) : ProfileState()
    object Loading : ProfileState()
    object LoggedOut : ProfileState()
}