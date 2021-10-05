package com.chuify.xoomclient.domain.di


import com.chuify.xoomclient.data.prefrences.SharedPrefs
import com.chuify.xoomclient.domain.mapper.AccessoryDtoMapper
import com.chuify.xoomclient.domain.mapper.ProductDtoMapper
import com.chuify.xoomclient.domain.mapper.UserDtoMapper
import com.chuify.xoomclient.domain.mapper.VendorDtoMapper
import com.chuify.xoomclient.domain.repository.AuthRepo
import com.chuify.xoomclient.domain.repository.VendorRepo
import com.chuify.xoomclient.domain.usecase.auth.AuthenticatePhoneUseCase
import com.chuify.xoomclient.domain.usecase.auth.SignInUseCase
import com.chuify.xoomclient.domain.usecase.auth.SignUpUseCase
import com.chuify.xoomclient.domain.usecase.vendor.ListAccessoriesUseCase
import com.chuify.xoomclient.domain.usecase.vendor.ListProductsUseCase
import com.chuify.xoomclient.domain.usecase.vendor.ListVendorsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Singleton
    @Provides
    fun provideSingInUseCase(
        repository: AuthRepo,
        mapperModule: UserDtoMapper,
        prefs: SharedPrefs,
    ) = SignInUseCase(repo = repository, mapper = mapperModule, sharedPreferences = prefs)

    @Singleton
    @Provides
    fun provideSingUpUseCase(
        repository: AuthRepo,
        mapperModule: UserDtoMapper,
        prefs: SharedPrefs,
    ) = SignUpUseCase(repo = repository, mapper = mapperModule, sharedPreferences = prefs)


    @Singleton
    @Provides
    fun providePhoneVerificationUseCase(
        repository: AuthRepo,
    ) = AuthenticatePhoneUseCase(repo = repository)

    @Singleton
    @Provides
    fun provideVendorsListUseCase(
        repository: VendorRepo,
        mapperModule: VendorDtoMapper,
    ) = ListVendorsUseCase(repo = repository, mapper = mapperModule)

    @Singleton
    @Provides
    fun provideAccessoryListUseCase(
        repository: VendorRepo,
        mapperModule: AccessoryDtoMapper,
    ) = ListAccessoriesUseCase(repo = repository, mapper = mapperModule)

    @Singleton
    @Provides
    fun provideProductListUseCase(
        repository: VendorRepo,
        mapperModule: ProductDtoMapper,
    ) = ListProductsUseCase(repo = repository, mapper = mapperModule)


}