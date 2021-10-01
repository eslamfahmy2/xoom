package com.chuify.xoomclient.presentation.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import com.chuify.xoomclient.data.prefrences.SharedPrefs
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication : Application() {

    @Inject
    lateinit var sharedPreferences: SharedPrefs

    private val isDarkTheme by lazy {
        mutableStateOf(sharedPreferences.isDark())
    }

    override fun onCreate() {
        super.onCreate()
        isDarkTheme.value = sharedPreferences.isDark()
    }

    fun toggleTheme() {
        this.isDarkTheme.value = !this.isDarkTheme.value
        sharedPreferences.saveTheme(isDark = isDark())
    }

    fun isDark(): Boolean = this.isDarkTheme.value
}