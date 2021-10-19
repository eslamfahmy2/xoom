package com.chuify.xoomclient.domain.usecase.cart

import com.chuify.xoomclient.domain.repository.CartRepo
import com.chuify.xoomclient.domain.utils.DataState
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DecreaseOrderUseCase @Inject constructor(
    private val repo: CartRepo,
) {
    suspend operator fun invoke(id: String) = flow<DataState<Unit>> {
        try {
            emit(DataState.Loading())
            val order = repo.getById(id)
            order?.let {
                if (order.quantity > 1) {
                    val price = (order.quantity - 1) * order.basePrice
                    val newOrder = order.copy(price = price, quantity = order.quantity - 1)
                    repo.update(newOrder)
                } else {
                    repo.delete(order)

                }
            }
            emit(DataState.Success())

        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }
}
