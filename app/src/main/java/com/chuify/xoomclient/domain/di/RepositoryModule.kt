package com.chuify.xoomclient.domain.di

import com.chuify.xoomclient.data.local.dao.CartDao
import com.chuify.xoomclient.data.local.dao.NotificationDao
import com.chuify.xoomclient.data.local.source.CartRepoImpl
import com.chuify.xoomclient.data.local.source.NotificationRepoImpl
import com.chuify.xoomclient.data.prefrences.SharedPrefs
import com.chuify.xoomclient.data.remote.network.ApiInterface
import com.chuify.xoomclient.data.remote.source.*
import com.chuify.xoomclient.domain.repository.*
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
    fun provideCartRepository(
        api: CartDao,
    ): CartRepo {
        return CartRepoImpl(api)
    }

    @Singleton
    @Provides
    fun provideLocationRepository(
        api: ApiInterface,
    ): LocationRepo {
        return LocationRepoImpl(api)
    }

    @Singleton
    @Provides
    fun provideOrderRepository(
        api: ApiInterface,
    ): OrderRepo {
        return OrderRepoImpl(api)
    }

    @Singleton
    @Provides
    fun provideNotificationRepository(
        dp: NotificationDao,
        api: ApiInterface,
    ): NotificationRepo {
        return NotificationRepoImpl(dp, apiInterface = api)
    }

    @Singleton
    @Provides
    fun provideProfileRepository(
        sharedPrefs: SharedPrefs ,
        api: ApiInterface,
    ): ProfileRepo {
        return ProfileRepoImpl(sharedPrefs = sharedPrefs, apiInterface = api)
    }
}