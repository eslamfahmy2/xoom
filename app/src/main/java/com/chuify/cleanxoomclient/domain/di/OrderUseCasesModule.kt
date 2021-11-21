package com.chuify.cleanxoomclient.domain.di


import com.chuify.cleanxoomclient.domain.mapper.OrderDtoMapper
import com.chuify.cleanxoomclient.domain.repository.CartRepo
import com.chuify.cleanxoomclient.domain.repository.OrderRepo
import com.chuify.cleanxoomclient.domain.usecase.cart.SubmitOrderUseCase
import com.chuify.cleanxoomclient.domain.usecase.order.GetCompletedOrderListUseCase
import com.chuify.cleanxoomclient.domain.usecase.order.GetPendingOrderListUseCase
import com.chuify.cleanxoomclient.domain.usecase.order.TrackOrderUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OrderUseCasesModule {

    @Singleton
    @Provides
    fun provideGetPendingOrderListUseCase(
        repository: OrderRepo,
        orderDtoMapper: OrderDtoMapper,
    ) = GetPendingOrderListUseCase(orderRepo = repository, orderDtoMapper = orderDtoMapper)

    @Singleton
    @Provides
    fun provideGetCompletedOrderListUseCase(
        repository: OrderRepo,
        orderDtoMapper: OrderDtoMapper,
    ) = GetCompletedOrderListUseCase(orderRepo = repository, orderDtoMapper = orderDtoMapper)


    @Singleton
    @Provides
    fun provideTrackOrderUseCase(
        repository: OrderRepo,
    ) = TrackOrderUseCase(orderRepo = repository)


    @Singleton
    @Provides
    fun provideSubmitOrderUseCaseUseCase(
        repository: OrderRepo,
        cartRepo: CartRepo
    ) = SubmitOrderUseCase(orderRepo = repository, cartRepo = cartRepo)

}