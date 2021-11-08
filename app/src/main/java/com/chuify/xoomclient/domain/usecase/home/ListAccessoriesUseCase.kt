package com.chuify.xoomclient.domain.usecase.home

import android.util.Log
import com.chuify.xoomclient.domain.mapper.AccessoryDtoMapper
import com.chuify.xoomclient.domain.model.Accessory
import com.chuify.xoomclient.domain.repository.CartRepo
import com.chuify.xoomclient.domain.repository.VendorRepo
import com.chuify.xoomclient.domain.utils.DataState
import com.chuify.xoomclient.domain.utils.ResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

private const val TAG = "ListAccessoriesUseCase"

class ListAccessoriesUseCase @Inject constructor(
    private val repo: VendorRepo,
    private val cartRepo: CartRepo,
    private val mapper: AccessoryDtoMapper,

    ) {

    suspend operator fun invoke() = flow<DataState<List<Accessory>>> {
        try {
            when (val response = repo.listAccessories()) {
                is ResponseState.Error -> {
                    Log.d(TAG, "Error: " + response.message)
                    emit(DataState.Error(response.message))
                }
                is ResponseState.Success -> {

                    val accessories = mapper.toDomainList(response.data.accessories ?: listOf())
                    emit(DataState.Success(accessories))
                    Log.d(TAG, "Success: $accessories")

                    cartRepo.getAll().collect { cartOrders ->
                        Log.d(TAG, "cartOrders: $cartOrders")
                        val result = accessories.map { product ->
                            val productInCart = cartOrders.find { it.id == product.id }
                            productInCart?.let { item ->
                                Log.d(TAG, "map: $item")
                                product.copy(quantity = item.quantity , totalPrice = (item.quantity*item.price))
                            } ?: product
                        }
                        Log.d(TAG, "after map-----------")
                        Log.d(TAG, "after map products$result")
                        emit(DataState.Success(result))
                    }


                }
            }
        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }.flowOn(Dispatchers.IO).conflate()
}