package com.chuify.xoomclient.domain.di


import com.chuify.xoomclient.data.prefrences.SharedPrefs
import com.chuify.xoomclient.domain.mapper.*
import com.chuify.xoomclient.domain.repository.AuthRepo
import com.chuify.xoomclient.domain.repository.CartRepo
import com.chuify.xoomclient.domain.repository.LocationRepo
import com.chuify.xoomclient.domain.repository.VendorRepo
import com.chuify.xoomclient.domain.usecase.auth.AuthenticatePhoneUseCase
import com.chuify.xoomclient.domain.usecase.auth.SignInUseCase
import com.chuify.xoomclient.domain.usecase.auth.SignUpUseCase
import com.chuify.xoomclient.domain.usecase.cart.*
import com.chuify.xoomclient.domain.usecase.home.GetAccessoryDetailsUseCase
import com.chuify.xoomclient.domain.usecase.home.ListAccessoriesUseCase
import com.chuify.xoomclient.domain.usecase.home.ListProductsUseCase
import com.chuify.xoomclient.domain.usecase.home.ListVendorsUseCase
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
        locationDtoMapper: LocationDtoMapper
    ) = GetLocationsUseCase(locationRepo = repository , locationDtoMapper = locationDtoMapper)



}