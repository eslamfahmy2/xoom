package com.chuify.xoomclient.presentation.ui.editProfile

import com.chuify.xoomclient.domain.model.User


sealed class EditProfileState {
    data class Success(val user: User) : EditProfileState()
    data class Error(val message: String? = null) : EditProfileState()
    object Loading : EditProfileState()
    object ProfileUpdated : EditProfileState()
}