package com.chuify.xoomclient.domain.usecase.cart

import com.chuify.xoomclient.domain.mapper.OrderEntityMapper
import com.chuify.xoomclient.domain.model.Order
import com.chuify.xoomclient.domain.repository.CartRepo
import com.chuify.xoomclient.domain.utils.DataState
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

enum class OrderITemAction {
    Increase,
    Decrease
}

class UpdateOrderUs @Inject constructor(
    private val repo: CartRepo,
    private val orderEntityMapper: OrderEntityMapper,
) {
    suspend operator fun invoke(order: Order, action: OrderITemAction) = flow<DataState<Unit>> {
        try {
            emit(DataState.Loading())
            when (action) {
                OrderITemAction.Increase -> {
                    val model = orderEntityMapper.mapFromDomainModel(order)
                    val result = model.copy(quantity = model.quantity + 1)
                    repo.update(result)
                    emit(DataState.Success())

                }
                OrderITemAction.Decrease -> {

                    val model = orderEntityMapper.mapFromDomainModel(order)
                    if (model.quantity > 1) {
                        val result = model.copy(quantity = model.quantity - 1)
                        repo.update(result)
                        emit(DataState.Success())
                    } 
                }
            }

        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }
}
