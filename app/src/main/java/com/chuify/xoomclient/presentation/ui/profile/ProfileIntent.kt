package com.chuify.xoomclient.presentation.ui.profile


sealed class ProfileIntent {
    object LoadProfile : ProfileIntent()
    object LogOut : ProfileIntent()
    data class ChangeTheme(val boolean: Boolean) : ProfileIntent()
}
