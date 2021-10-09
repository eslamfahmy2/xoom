package com.chuify.xoomclient.domain.di


import com.chuify.xoomclient.data.prefrences.SharedPrefs
import com.chuify.xoomclient.domain.mapper.AccessoryDtoMapper
import com.chuify.xoomclient.domain.mapper.ProductDtoMapper
import com.chuify.xoomclient.domain.mapper.UserDtoMapper
import com.chuify.xoomclient.domain.mapper.VendorDtoMapper
import com.chuify.xoomclient.domain.repository.AuthRepo
import com.chuify.xoomclient.domain.repository.CartRepo
import com.chuify.xoomclient.domain.repository.VendorRepo
import com.chuify.xoomclient.domain.usecase.auth.AuthenticatePhoneUseCase
import com.chuify.xoomclient.domain.usecase.auth.SignInUseCase
import com.chuify.xoomclient.domain.usecase.auth.SignUpUseCase
import com.chuify.xoomclient.domain.usecase.home.ListAccessoriesUseCase
import com.chuify.xoomclient.domain.usecase.home.ListProductsUseCase
import com.chuify.xoomclient.domain.usecase.home.ListVendorsUseCase
import com.chuify.xoomclient.domain.usecase.cart.*
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
        cartRepo: CartRepo,
        mapperModule: ProductDtoMapper,
    ) = ListProductsUseCase(repo = repository, mapper = mapperModule, cartRepo = cartRepo)

    //-------------------------------------------------------------------------------

    @Singleton
    @Provides
    fun provideCartItemsUseCase(
        repository: CartRepo,
    ) = CartItemsCountUs(repo = repository)

    @Singleton
    @Provides
    fun provideDeleteOrderUseCase(
        repository: CartRepo,
    ) = DeleteOrUpdateProductUseCase(repo = repository)

    @Singleton
    @Provides
    fun provideEraseOrdersUseCase(
        repository: CartRepo,
    ) = EraseOrderUs(repo = repository)

    @Singleton
    @Provides
    fun provideInsertOrderUseCase(
        repository: CartRepo,
    ) = InsertOrUpdateProductUseCase(repo = repository)

    @Singleton
    @Provides
    fun provideListOrderUseCase(
        repository: CartRepo,
    ) = ListOrdersUs(repo = repository)

    @Singleton
    @Provides
    fun provideUpdateOrderUseCase(
        repository: CartRepo,
    ) = UpdateOrderUs(repo = repository)

}