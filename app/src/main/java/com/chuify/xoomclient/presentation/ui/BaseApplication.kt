package com.chuify.xoomclient.presentation.ui

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.chuify.xoomclient.presentation.ui.signup.TAG
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {

    private val isDark = mutableStateOf(false)

    fun toggleTheme() {
        Log.d(TAG, "toggleTheme: ")
        this.isDark.value = !this.isDark.value
    }

    fun isDark(): Boolean = this.isDark.value
}