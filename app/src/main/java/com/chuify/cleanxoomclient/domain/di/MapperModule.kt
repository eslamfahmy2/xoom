package com.chuify.cleanxoomclient.domain.di

import com.chuify.cleanxoomclient.domain.mapper.*
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

    @Singleton
    @Provides
    fun provideAccessoryMapper() = AccessoryDtoMapper()

    @Singleton
    @Provides
    fun provideProductMapper() = ProductDtoMapper()

    @Singleton
    @Provides
    fun providePreOrderMapper() = CartEntityMapper()

    @Singleton
    @Provides
    fun provideLocationMapper() = LocationDtoMapper()

    @Singleton
    @Provides
    fun provideOrderMapper() = OrderDtoMapper()

    @Singleton
    @Provides
    fun provideNotificationMapper() = NotificationEntityMapper()
}