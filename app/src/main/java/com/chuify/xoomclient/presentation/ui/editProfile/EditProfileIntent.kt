package com.chuify.xoomclient.presentation.ui.editProfile


sealed class EditProfileIntent {
    object LoadProfile : EditProfileIntent()
    object EditProfile : EditProfileIntent()
    data class FirstNameChange(val data: String) : EditProfileIntent()
    data class LastNameChange(val data: String) : EditProfileIntent()
    data class EmailChange(val data: String) : EditProfileIntent()
}
