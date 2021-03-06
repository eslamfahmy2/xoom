package com.chuify.cleanxoomclient.data.remote.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FireBase {

    @Provides
    @Singleton
    fun provideFireBaseAut(): FirebaseAuth = FirebaseAuth.getInstance()


}