package com.chuify.cleanxoomclient.domain.usecase.cart

import com.chuify.cleanxoomclient.data.local.entity.CartEntity
import com.chuify.cleanxoomclient.domain.repository.CartRepo
import com.chuify.cleanxoomclient.domain.utils.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class IncreaseOrderUseCase @Inject constructor(
    private val repo: CartRepo,
) {
    suspend operator fun invoke(
        image: String,
        name: String,
        id: String,
        basePrice: Double,
    ) = flow<DataState<Unit>> {
        try {
            emit(DataState.Loading())
            val order = repo.getById(id)
            order?.let {
                val price = (order.quantity + 1) * order.basePrice
                val newOrder = order.copy(price = price, quantity = order.quantity + 1)
                repo.update(newOrder)
                emit(DataState.Success())
            } ?: run {
                val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
                val time = df.format(Calendar.getInstance().time)
                val orderDto = CartEntity(
                    image = image,
                    name = name,
                    id = id,
                    basePrice = basePrice,
                    quantity = 1,
                    price = basePrice,
                    time = time
                )
                repo.insert(orderDto)
                emit(DataState.Success())
            }


        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }.flowOn(Dispatchers.IO).conflate()
}
