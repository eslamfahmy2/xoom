package com.chuify.xoomclient.domain.di


import com.chuify.xoomclient.domain.mapper.LocationDtoMapper
import com.chuify.xoomclient.domain.repository.LocationRepo
import com.chuify.xoomclient.domain.usecase.location.GetLocationsUseCase
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


}