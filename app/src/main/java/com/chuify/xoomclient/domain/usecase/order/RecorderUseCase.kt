package com.chuify.xoomclient.domain.usecase.order

import com.chuify.xoomclient.domain.mapper.OrderDtoMapper
import com.chuify.xoomclient.domain.model.Order
import com.chuify.xoomclient.domain.repository.OrderRepo
import com.chuify.xoomclient.domain.utils.DataState
import com.chuify.xoomclient.domain.utils.ResponseState
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class RecorderUseCase @Inject constructor(
    private val orderRepo: OrderRepo,
    private val orderDtoMapper: OrderDtoMapper,
) {

    suspend operator fun invoke(order: com.chuify.xoomclient.domain.model.Order) = flow<DataState<List<Order>>> {
        try {
            emit(DataState.Loading())
            when (val response = orderRepo.getCompletedOrders()) {
                is ResponseState.Error -> {
                    emit(DataState.Error(response.message))
                }
                is ResponseState.Success -> {


                    val res = response.data.Orders?.let { it ->
                        it.map { orderDtoMapper.mapToDomainModel(it) }
                    }

                    res?.let {
                        emit(DataState.Success(res))
                    }

                }
            }

        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }
}