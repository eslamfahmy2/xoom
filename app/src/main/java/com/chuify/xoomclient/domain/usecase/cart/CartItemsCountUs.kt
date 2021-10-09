package com.chuify.xoomclient.domain.usecase.cart

import com.chuify.xoomclient.domain.repository.CartRepo
import com.chuify.xoomclient.domain.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CartItemsCountUs @Inject constructor(
    private val repo: CartRepo,
) {
    suspend operator fun invoke() = flow<DataState<Flow<Int>>> {
        try {
            emit(DataState.Loading())
            val data = repo.getCartItemsCount()
            emit(DataState.Success(data = data))
        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }
}
