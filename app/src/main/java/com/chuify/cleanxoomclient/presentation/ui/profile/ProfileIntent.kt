package com.chuify.cleanxoomclient.presentation.ui.profile


sealed class ProfileIntent {
    object LoadProfile : ProfileIntent()
    object LogOut : ProfileIntent()
    data class ChangeTheme(val boolean: Boolean) : ProfileIntent()
}
