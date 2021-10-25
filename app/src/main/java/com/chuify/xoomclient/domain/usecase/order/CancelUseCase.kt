package com.chuify.xoomclient.domain.usecase.order

import com.chuify.xoomclient.domain.mapper.OrderDtoMapper
import com.chuify.xoomclient.domain.repository.OrderRepo
import com.chuify.xoomclient.domain.utils.DataState
import com.chuify.xoomclient.domain.utils.ResponseState
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class CancelUseCase @Inject constructor(
    private val orderRepo: OrderRepo,
    private val orderDtoMapper: OrderDtoMapper,
) {

    suspend operator fun invoke(id: String, reason: String) = flow<DataState<Nothing>> {
        try {
            emit(DataState.Loading())
            when (val response = orderRepo.cancelOrder(id, reason)) {
                is ResponseState.Error -> {
                    emit(DataState.Error(response.message))
                }
                is ResponseState.Success -> {
                    emit(DataState.Success())
                }
            }

        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }
}