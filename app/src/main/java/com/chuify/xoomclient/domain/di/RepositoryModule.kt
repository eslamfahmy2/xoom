package com.chuify.xoomclient.domain.di

import com.chuify.xoomclient.data.remote.data_source.AuthRepoImpl
import com.chuify.xoomclient.data.remote.data_source.VendorRepoImpl
import com.chuify.xoomclient.data.remote.network.ApiInterface
import com.chuify.xoomclient.domain.repository.AuthRepo
import com.chuify.xoomclient.domain.repository.VendorRepo
import com.google.firebase.auth.FirebaseAuth
import dagger.Provides
import javax.inject.Singleton

object RepositoryModule {


    @Singleton
    @Provides
    fun provideAuthRepository(
        api: ApiInterface,
        auth: FirebaseAuth,
    ): AuthRepo {
        return AuthRepoImpl(api, auth)
    }


    @Singleton
    @Provides
    fun provideVendorRepository(
        api: ApiInterface,
    ): VendorRepo {
        return VendorRepoImpl(api)
    }
}