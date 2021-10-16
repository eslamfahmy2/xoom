package com.chuify.xoomclient.domain.usecase.cart

import com.chuify.xoomclient.domain.model.Accessory
import com.chuify.xoomclient.domain.repository.CartRepo
import com.chuify.xoomclient.domain.utils.DataState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteOrUpdateAccessoryUseCase @Inject constructor(
    private val repo: CartRepo,
) {
    suspend operator fun invoke(model: Accessory) = flow<DataState<Unit>> {
        try {
            emit(DataState.Loading())
            val order = repo.getById(model.id).first()

            order?.let {
                if (order.quantity > 1) {
                    val price = (order.quantity - 1) * model.price
                    val newOrder = order.copy(price = price, quantity = model.quantity - 1)
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
