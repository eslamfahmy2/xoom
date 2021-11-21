package com.chuify.cleanxoomclient.domain.di


import com.chuify.cleanxoomclient.domain.mapper.LocationDtoMapper
import com.chuify.cleanxoomclient.domain.repository.LocationRepo
import com.chuify.cleanxoomclient.domain.usecase.location.GetLocationsUseCase
import com.chuify.cleanxoomclient.domain.usecase.location.SaveLocationsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationsUseCasesModule {

    @Singleton
    @Provides
    fun provideGetLocationsUseCase(
        repository: LocationRepo,
        locationDtoMapper: LocationDtoMapper,
    ) = GetLocationsUseCase(locationRepo = repository, locationDtoMapper = locationDtoMapper)

    @Singleton
    @Provides
    fun provideSaveLocationsUseCase(
        repository: LocationRepo,
    ) = SaveLocationsUseCase(locationRepo = repository)


}