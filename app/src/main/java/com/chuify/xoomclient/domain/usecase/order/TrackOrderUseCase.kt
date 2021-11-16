package com.chuify.xoomclient.domain.usecase.order

import com.chuify.xoomclient.data.remote.dto.TrackDto
import com.chuify.xoomclient.domain.repository.OrderRepo
import com.chuify.xoomclient.domain.utils.DataState
import com.chuify.xoomclient.domain.utils.ResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class TrackOrderUseCase @Inject constructor(
    private val orderRepo: OrderRepo,
) {

    suspend operator fun invoke(id: String) = flow<DataState<TrackDto>> {
        try {
            emit(DataState.Loading())
            when (val response = orderRepo.trackOrder(id)) {
                is ResponseState.Error -> {
                    emit(DataState.Error(response.message))
                }
                is ResponseState.Success -> {
                    if (response.data.error.equals("400")) {
                        emit(DataState.Error(response.data.msg))
                    } else
                        emit(DataState.Success(response.data))
                }
            }

        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }.flowOn(Dispatchers.IO).conflate()
}