package com.chuify.xoomclient.domain.usecase.cart

import com.chuify.xoomclient.domain.mapper.CartEntityMapper
import com.chuify.xoomclient.domain.model.CartPreview
import com.chuify.xoomclient.domain.model.Cart
import com.chuify.xoomclient.domain.repository.CartRepo
import com.chuify.xoomclient.domain.utils.DataState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
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

                    val totalPrice = it.sumOf { item -> item.price }
                    val quantity = it.sumOf { item -> item.quantity }

                    val cartPreview = CartPreview(
                        totalPrice = totalPrice,
                        totalQuantity = quantity)

                    val res = Pair(listOrders, cartPreview)

                    emit(DataState.Success(res))
                }
            }

        } catch (e: Exception) {

            emit(DataState.Error(e.message))


        }
    }
}
