package com.chuify.cleanxoomclient.domain.usecase.order

import com.chuify.cleanxoomclient.domain.mapper.OrderDtoMapper
import com.chuify.cleanxoomclient.domain.repository.OrderRepo
import com.chuify.cleanxoomclient.domain.utils.DataState
import com.chuify.cleanxoomclient.domain.utils.ResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class CancelUseCase @Inject constructor(
    private val orderRepo: OrderRepo,
    private val orderDtoMapper: OrderDtoMapper,
) {

    suspend operator fun invoke(id: String, reason: String) = flow<DataState<Nothing>> {
        try {
            emit(DataState.Loading())
            if (reason.isEmpty()) {
                emit(DataState.Error("Please add a reason "))
            } else {
                when (val response = orderRepo.cancelOrder(id, reason)) {
                    is ResponseState.Error -> {
                        emit(DataState.Error(response.message))
                    }
                    is ResponseState.Success -> {
                        emit(DataState.Success())
                    }
                }
            }

        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }.flowOn(Dispatchers.IO).conflate()
}