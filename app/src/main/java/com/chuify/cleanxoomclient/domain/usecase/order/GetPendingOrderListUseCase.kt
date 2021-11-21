package com.chuify.cleanxoomclient.domain.usecase.order

import com.chuify.cleanxoomclient.domain.mapper.OrderDtoMapper
import com.chuify.cleanxoomclient.domain.model.Order
import com.chuify.cleanxoomclient.domain.repository.OrderRepo
import com.chuify.cleanxoomclient.domain.utils.DataState
import com.chuify.cleanxoomclient.domain.utils.ResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class GetPendingOrderListUseCase @Inject constructor(
    private val orderRepo: OrderRepo,
    private val orderDtoMapper: OrderDtoMapper,
) {

    suspend operator fun invoke() = flow<DataState<List<Order>>> {
        try {
            emit(DataState.Loading())
            when (val response = orderRepo.getPendingOrders()) {
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
    }.flowOn(Dispatchers.IO).conflate()
}