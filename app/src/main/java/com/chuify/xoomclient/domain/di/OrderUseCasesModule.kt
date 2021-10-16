package com.chuify.xoomclient.domain.di


import com.chuify.xoomclient.domain.mapper.OrderDtoMapper
import com.chuify.xoomclient.domain.repository.OrderRepo
import com.chuify.xoomclient.domain.usecase.order.GetCompletedOrderListUseCase
import com.chuify.xoomclient.domain.usecase.order.GetPendingOrderListUseCase
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


}