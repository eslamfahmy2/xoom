package com.chuify.xoomclient.domain.usecase.home

import android.util.Log
import com.chuify.xoomclient.domain.mapper.ProductDtoMapper
import com.chuify.xoomclient.domain.model.Product
import com.chuify.xoomclient.domain.repository.CartRepo
import com.chuify.xoomclient.domain.repository.VendorRepo
import com.chuify.xoomclient.domain.utils.DataState
import com.chuify.xoomclient.domain.utils.ResponseState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val TAG = "ListProductsUseCase"

class ListProductsUseCase @Inject constructor(
    private val repo: VendorRepo,
    private val cartRepo: CartRepo,
    private val mapper: ProductDtoMapper,
) {


    suspend operator fun invoke(vendorID: String) = flow<DataState<List<Product>>> {
        try {
            when (val response = repo.listProducts(vendorID)) {
                is ResponseState.Error -> {
                    Log.d(TAG, "Error: " + response.message)
                    emit(DataState.Error(response.message))
                }
                is ResponseState.Success -> {

                    val products = mapper.toDomainList(response.data.products ?: listOf())
                    emit(DataState.Success(products))
                    Log.d(TAG, "Success: $products")


                    cartRepo.getAll().collect { cartOrders ->
                        Log.d(TAG, "cartOrders: $cartOrders")
                        val result = products.map { product ->
                            val productInCart = cartOrders.find { it.id == product.id }
                            productInCart?.let { item ->
                                Log.d(TAG, "map: $item")
                                product.copy(quantity = item.quantity)
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
    }
}