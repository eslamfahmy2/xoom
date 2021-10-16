package com.chuify.xoomclient.domain.usecase.cart

import android.util.Log
import com.chuify.xoomclient.domain.model.CartPreview
import com.chuify.xoomclient.domain.repository.CartRepo
import com.chuify.xoomclient.domain.utils.DataState
import com.chuify.xoomclient.presentation.ui.signup.TAG
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
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
                    Log.d(TAG, "invoke: $it")
                    Log.d(TAG, "totalPrice: $totalPrice")
                    Log.d(TAG, "quantity: $quantity")
                    emit(DataState.Success(
                        CartPreview(
                            totalPrice = totalPrice,
                            totalQuantity = quantity)))
                }
            }

        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }
}

