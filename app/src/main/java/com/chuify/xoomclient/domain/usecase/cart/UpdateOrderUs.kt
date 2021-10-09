package com.chuify.xoomclient.domain.usecase.cart

import com.chuify.xoomclient.data.local.entity.OrderEntity
import com.chuify.xoomclient.domain.repository.CartRepo
import com.chuify.xoomclient.domain.utils.DataState
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateOrderUs @Inject constructor(
    private val repo: CartRepo,
) {
    suspend operator fun invoke(model: OrderEntity) = flow<DataState<Unit>> {
        try {
            emit(DataState.Loading())
            repo.update(model)
            emit(DataState.Success())
        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }
}
