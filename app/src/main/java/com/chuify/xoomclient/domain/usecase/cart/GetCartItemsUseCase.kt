package com.chuify.xoomclient.domain.usecase.cart

import android.util.Log
import com.chuify.xoomclient.domain.mapper.CartEntityMapper
import com.chuify.xoomclient.domain.model.CartPreview
import com.chuify.xoomclient.domain.model.Cart
import com.chuify.xoomclient.domain.repository.CartRepo
import com.chuify.xoomclient.domain.utils.DataState
import com.chuify.xoomclient.presentation.ui.signup.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCartItemsUseCase @Inject constructor(
    private val repo: CartRepo,
    private val orderEntityMapper: CartEntityMapper,
) {
    suspend operator fun invoke() = flow<DataState<Pair<List<Cart>, CartPreview>>> {
        try {
            emit(DataState.Loading())
            repo.getAll().collect { it ->
                if (it.isNullOrEmpty()) {
                    emit(DataState.Error("Empty"))
                } else {
                    val listOrders = it.map { orderEntityMapper.mapToDomainModel(it) }

                    val quantity = it.sumOf { item -> item.quantity }
                    val totalPrice = it.sumOf { item -> item.basePrice * item.quantity }

                    val cartPreview = CartPreview(
                        totalPrice = totalPrice,
                        totalQuantity = quantity)

                    val res = Pair(listOrders, cartPreview)
                    Log.d(TAG, "invoke: $cartPreview")
                    emit(DataState.Success(res))
                }
            }

        } catch (e: Exception) {

            emit(DataState.Error(e.message))


        }
    }.flowOn(Dispatchers.IO).conflate()
}
