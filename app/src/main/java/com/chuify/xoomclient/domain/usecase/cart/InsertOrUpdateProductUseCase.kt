package com.chuify.xoomclient.domain.usecase.cart

import com.chuify.xoomclient.data.local.entity.OrderEntity
import com.chuify.xoomclient.domain.model.Product
import com.chuify.xoomclient.domain.repository.CartRepo
import com.chuify.xoomclient.domain.utils.DataState
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class InsertOrUpdateProductUseCase @Inject constructor(
    private val repo: CartRepo,
) {
    suspend operator fun invoke(model: Product) = flow<DataState<Unit>> {
        try {
            emit(DataState.Loading())

            val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
            val time = df.format(Calendar.getInstance().time)

            val price = (model.quantity + 1) * model.price

            val orderDto = OrderEntity(
                image = model.image,
                name = model.name,
                id = model.id,
                basePrice = model.price,
                quantity = model.quantity + 1,
                price = price,
                time = time
            )
            repo.insert(orderDto)

            emit(DataState.Success())
        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }
}
