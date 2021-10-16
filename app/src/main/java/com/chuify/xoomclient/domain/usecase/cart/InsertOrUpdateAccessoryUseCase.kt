package com.chuify.xoomclient.domain.usecase.cart

import com.chuify.xoomclient.data.local.entity.OrderEntity
import com.chuify.xoomclient.domain.model.Accessory
import com.chuify.xoomclient.domain.repository.CartRepo
import com.chuify.xoomclient.domain.utils.DataState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class InsertOrUpdateAccessoryUseCase @Inject constructor(
    private val repo: CartRepo,
) {
    suspend operator fun invoke(model: Accessory) = flow<DataState<Unit>> {
        try {
            emit(DataState.Loading())
            val order = repo.getById(model.id).first()
            order?.let {
                val price = (order.quantity + 1) * model.price
                val newOrder = order.copy(price = price, quantity = order.quantity + 1)
                repo.update(newOrder)
                emit(DataState.Success())
            } ?: run {
                val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
                val time = df.format(Calendar.getInstance().time)
                val orderDto = OrderEntity(
                    image = model.image,
                    name = model.name,
                    id = model.id,
                    basePrice = model.price,
                    quantity = 1,
                    price = model.price,
                    time = time
                )
                repo.insert(orderDto)
                emit(DataState.Success())
            }


        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }
}
