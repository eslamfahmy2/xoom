package com.chuify.xoomclient.domain.di


import com.chuify.xoomclient.data.prefrences.SharedPrefs
import com.chuify.xoomclient.domain.mapper.*
import com.chuify.xoomclient.domain.repository.AuthRepo
import com.chuify.xoomclient.domain.repository.CartRepo
import com.chuify.xoomclient.domain.repository.VendorRepo
import com.chuify.xoomclient.domain.usecase.auth.AuthenticatePhoneUseCase
import com.chuify.xoomclient.domain.usecase.auth.SignInUseCase
import com.chuify.xoomclient.domain.usecase.auth.SignUpUseCase
import com.chuify.xoomclient.domain.usecase.cart.*
import com.chuify.xoomclient.domain.usecase.home.ListAccessoriesUseCase
import com.chuify.xoomclient.domain.usecase.home.ListProductsUseCase
import com.chuify.xoomclient.domain.usecase.home.ListVendorsUseCase
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
        prefs: SharedPrefs,
    ) = SignInUseCase(repo = repository, sharedPreferences = prefs)

    @Singleton
    @Provides
    fun provideSingUpUseCase(
        repository: AuthRepo,
        prefs: SharedPrefs,
    ) = SignUpUseCase(repo = repository, sharedPreferences = prefs)


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
        cartRepo: CartRepo,
        mapperModule: AccessoryDtoMapper,
    ) = ListAccessoriesUseCase(repo = repository, mapper = mapperModule, cartRepo = cartRepo)

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
    fun provideInsertAccessoryUseCase(
        repository: CartRepo,
    ) = IncreaseOrderUseCase(repo = repository)


    @Singleton
    @Provides
    fun provideDeleteAccessoryUseCase(
        repository: CartRepo,
    ) = DecreaseOrderUseCase(repo = repository)


    @Singleton
    @Provides
    fun provideCartItemsUseCase(
        repository: CartRepo,
        orderEntityMapper: CartEntityMapper,
    ) = GetCartItemsUseCase(repo = repository, orderEntityMapper = orderEntityMapper)


    @Singleton
    @Provides
    fun provideEraseOrdersUseCase(
        repository: CartRepo,
    ) = EraseOrderUs(repo = repository)

    @Singleton
    @Provides
    fun provideListOrderUseCase(
        repository: CartRepo,
    ) = CartPreviewUC(repo = repository)

    @Singleton
    @Provides
    fun provideUpdateOrderUseCase(
        repository: CartRepo,
        orderEntityMapper: CartEntityMapper,
    ) = UpdateOrderUs(repo = repository, orderEntityMapper = orderEntityMapper)


    @Singleton
    @Provides
    fun provideDeleteOrderUseCase(
        repository: CartRepo,
        orderEntityMapper: CartEntityMapper,
    ) = DeleteOrderUs(repo = repository, orderEntityMapper = orderEntityMapper)


}