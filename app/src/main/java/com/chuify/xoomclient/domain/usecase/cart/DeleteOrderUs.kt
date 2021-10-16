package com.chuify.xoomclient.domain.usecase.cart

import com.chuify.xoomclient.domain.mapper.OrderEntityMapper
import com.chuify.xoomclient.domain.model.Order
import com.chuify.xoomclient.domain.repository.CartRepo
import com.chuify.xoomclient.domain.utils.DataState
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteOrderUs @Inject constructor(
    private val repo: CartRepo,
    private val orderEntityMapper: OrderEntityMapper,
) {
    suspend operator fun invoke(order: Order) = flow<DataState<Unit>> {
        try {
            emit(DataState.Loading())
            val model = orderEntityMapper.mapFromDomainModel(order)
            repo.delete(model)
            emit(DataState.Success())
        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }
}
