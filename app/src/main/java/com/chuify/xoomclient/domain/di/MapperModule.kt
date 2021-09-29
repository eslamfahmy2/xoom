package com.chuify.xoomclient.domain.di

import com.chuify.xoomclient.data.remote.data_source.AuthRepoImpl
import com.chuify.xoomclient.data.remote.network.ApiInterface
import com.chuify.xoomclient.domain.mapper.UserDtoMapper
import com.chuify.xoomclient.domain.repository.AuthRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MapperModule {

    @Singleton
    @Provides
    fun provideRecipeMapper() = UserDtoMapper()

    @Singleton
    @Provides
    fun provideRepository(
        api: ApiInterface,
    ): AuthRepo {
        return AuthRepoImpl(api)
    }

}