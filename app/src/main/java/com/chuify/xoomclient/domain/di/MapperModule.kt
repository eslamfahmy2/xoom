package com.chuify.xoomclient.domain.di

import com.chuify.xoomclient.domain.mapper.AccessoryDtoMapper
import com.chuify.xoomclient.domain.mapper.ProductDtoMapper
import com.chuify.xoomclient.domain.mapper.UserDtoMapper
import com.chuify.xoomclient.domain.mapper.VendorDtoMapper
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


}