package com.chuify.cleanxoomclient.domain.usecase.cart

import com.chuify.cleanxoomclient.domain.model.CartPreview
import com.chuify.cleanxoomclient.domain.repository.CartRepo
import com.chuify.cleanxoomclient.domain.utils.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CartPreviewUC @Inject constructor(
    private val repo: CartRepo,
) {
    suspend operator fun invoke() = flow<DataState<CartPreview>> {
        try {
            emit(DataState.Loading())
            repo.getAll().collect {
                if (it.isNullOrEmpty()) {
                    emit(DataState.Error("Empty"))
                } else {
                    val totalPrice = it.sumOf { item -> item.price }
                    val quantity = it.sumOf { item -> item.quantity }

                    emit(
                        DataState.Success(
                            CartPreview(
                                totalPrice = totalPrice,
                                totalQuantity = quantity
                            )
                        )
                    )
                }
            }

        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }.flowOn(Dispatchers.IO).conflate()
}

