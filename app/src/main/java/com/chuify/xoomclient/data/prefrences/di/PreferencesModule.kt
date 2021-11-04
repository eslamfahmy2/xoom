package com.chuify.xoomclient.data.prefrences.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.chuify.xoomclient.data.prefrences.SharedPrefs
import com.chuify.xoomclient.data.prefrences.flow.FlowSharedPreferences
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    private const val PREF = "xoom"

    @Provides
    @Singleton
    fun provideSysPref(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(PREF, Context.MODE_PRIVATE)


    @Provides
    @Singleton
    fun providePref(sharedPreferences: SharedPreferences): SharedPrefs =
        SharedPrefs(sharedPref = sharedPreferences)


    @ExperimentalCoroutinesApi
    @Provides
    @Singleton
    fun providePrefFlow(sharedPreferences: SharedPreferences): FlowSharedPreferences =
        FlowSharedPreferences(sharedPreferences = sharedPreferences)


}