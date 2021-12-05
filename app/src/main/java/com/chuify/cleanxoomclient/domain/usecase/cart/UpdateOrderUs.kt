package com.chuify.cleanxoomclient.domain.usecase.cart

import com.chuify.cleanxoomclient.domain.mapper.CartEntityMapper
import com.chuify.cleanxoomclient.domain.model.Cart
import com.chuify.cleanxoomclient.domain.repository.CartRepo
import com.chuify.cleanxoomclient.domain.utils.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

enum class OrderITemAction {
    Increase,
    Decrease
}

class UpdateOrderUs @Inject constructor(
    private val repo: CartRepo,
    private val orderEntityMapper: CartEntityMapper,
) {
    suspend operator fun invoke(order: Cart, action: OrderITemAction) = flow<DataState<Unit>> {
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
                    } else {
                        repo.delete(model)
                        emit(DataState.Success())
                    }
                }
            }

        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }.flowOn(Dispatchers.IO).conflate()
}
