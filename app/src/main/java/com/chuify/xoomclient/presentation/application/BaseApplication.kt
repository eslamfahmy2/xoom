package com.chuify.xoomclient.presentation.application

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import com.chuify.xoomclient.data.prefrences.SharedPrefs
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication : Application() {

    private val isDarkTheme by lazy {
        mutableStateOf(false)
    }

    fun toggleTheme() {
        this.isDarkTheme.value = !this.isDarkTheme.value
    }

    fun isDark(): Boolean = this.isDarkTheme.value
}