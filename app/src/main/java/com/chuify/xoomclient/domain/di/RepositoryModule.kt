package com.chuify.xoomclient.domain.di

import com.chuify.xoomclient.data.local.dao.OrderDao
import com.chuify.xoomclient.data.local.source.CartRepoImpl
import com.chuify.xoomclient.data.remote.network.ApiInterface
import com.chuify.xoomclient.data.remote.source.AuthRepoImpl
import com.chuify.xoomclient.data.remote.source.VendorRepoImpl
import com.chuify.xoomclient.domain.repository.AuthRepo
import com.chuify.xoomclient.domain.repository.CartRepo
import com.chuify.xoomclient.domain.repository.VendorRepo
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
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

    @Singleton
    @Provides
    fun provideOrderRepository(
        api: OrderDao,
    ): CartRepo {
        return CartRepoImpl(api)
    }
}