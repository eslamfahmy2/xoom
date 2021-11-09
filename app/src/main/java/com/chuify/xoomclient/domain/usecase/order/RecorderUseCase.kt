package com.chuify.xoomclient.domain.usecase.order

import android.text.format.DateFormat
import android.util.Log
import com.chuify.xoomclient.domain.mapper.OrderDtoMapper
import com.chuify.xoomclient.domain.model.Order
import com.chuify.xoomclient.domain.repository.OrderRepo
import com.chuify.xoomclient.domain.utils.DataState
import com.chuify.xoomclient.domain.utils.ResponseState

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject

private const val TAG = "RecorderUseCase"

class RecorderUseCase @Inject constructor(
    private val orderRepo: OrderRepo,
    private val orderDtoMapper: OrderDtoMapper,
) {

    suspend operator fun invoke(
        order: Order,
    ) =
        flow<DataState<Boolean>> {
            try {
                emit(DataState.Loading())

                val now = Calendar.getInstance()
                val tmp = now.clone() as Calendar
                val tmpPlusHalfHour = now.clone() as Calendar
                tmpPlusHalfHour.add(Calendar.MINUTE, 30)

                val orderJson = JsonObject()

                orderJson.addProperty("location_id", order.locationID)
                orderJson.addProperty("totalprice", order.totalPrice)
                orderJson.addProperty("paymentMethod", order.paymentMethod)
                orderJson.addProperty(
                    "deliveryDate",
                    DateFormat.format("yyy-MM-dd", tmp.time).toString()
                )
                orderJson.addProperty(
                    "deliveryTime",
                    DateFormat.format("hh:mm:ss", tmpPlusHalfHour.time).toString()
                )

                val array = JsonArray()
                for (product in order.products) {
                    val productJson = JsonObject()
                    productJson.addProperty("product_id", product.product_id)
                    productJson.addProperty("units_order", product.quantity)
                    productJson.addProperty("total_price", product.selling_price)
                    array.add(productJson)
                }
                orderJson.add("products", array)
                Log.d(TAG, "invoke submit $orderJson")
                when (val response = orderRepo.submitOrder(orderJson.toString())) {
                    is ResponseState.Error -> {
                        emit(DataState.Error(response.message))
                    }
                    is ResponseState.Success -> {

                        emit(DataState.Success(true))

                    }
                }

            } catch (e: Exception) {
                emit(DataState.Error(e.message))
            }
        }.flowOn(Dispatchers.IO).conflate()
}