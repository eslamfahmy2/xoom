package com.chuify.xoomclient.domain.di

import com.chuify.xoomclient.data.remote.data_source.AuthRepoImpl
import com.chuify.xoomclient.data.remote.network.ApiInterface
import com.chuify.xoomclient.domain.mapper.UserDtoMapper
import com.chuify.xoomclient.domain.mapper.VendorDtoMapper
import com.chuify.xoomclient.domain.repository.AuthRepo
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Singleton
    @Provides
    fun provideUserMapper() = UserDtoMapper()

    @Singleton
    @Provides
    fun provideVendorMapper() = VendorDtoMapper()


}