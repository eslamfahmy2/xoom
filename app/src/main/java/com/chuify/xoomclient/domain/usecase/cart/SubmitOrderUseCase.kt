package com.chuify.xoomclient.domain.usecase.cart

import android.text.format.DateFormat
import com.chuify.xoomclient.domain.model.Cart
import com.chuify.xoomclient.domain.model.Location
import com.chuify.xoomclient.domain.model.Payments
import com.chuify.xoomclient.domain.repository.CartRepo
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


class SubmitOrderUseCase @Inject constructor(
    private val orderRepo: OrderRepo,
    private val cartRepo: CartRepo
) {

    suspend operator fun invoke(
        payment: Payments,
        locations: List<Location>,
        items: List<Cart>,
    ) = flow<DataState<Boolean>> {
        try {
            emit(DataState.Loading())
            locations.firstOrNull { it.selected }?.let { location ->

                val totalPrice = items.sumOf { item -> item.basePrice * item.quantity }
                val orderJson = JsonObject()
                orderJson.addProperty("location_id", location.id)
                orderJson.addProperty("totalprice", totalPrice)
                orderJson.addProperty("paymentMethod", payment.name)

                val now = Calendar.getInstance()
                val tmp = now.clone() as Calendar
                val tmpPlusHalfHour = now.clone() as Calendar
                tmpPlusHalfHour.add(Calendar.MINUTE, 30)

                orderJson.addProperty(
                    "deliveryDate",
                    DateFormat.format("yyy-MM-dd", tmp.time).toString()
                )
                orderJson.addProperty(
                    "deliveryTime",
                    DateFormat.format("hh:mm:ss", tmpPlusHalfHour.time).toString()
                )

                val array = JsonArray()
                for (product in items) {
                    val productJson = JsonObject()
                    productJson.addProperty("product_id", product.id)
                    productJson.addProperty("units_order", product.quantity)
                    productJson.addProperty("total_price", product.price)
                    array.add(productJson)
                }
                orderJson.add("products", array)
                val body = orderJson.toString()


                when (val response = orderRepo.submitOrder(body)) {
                    is ResponseState.Error -> {
                        emit(DataState.Error(response.message))
                    }
                    is ResponseState.Success -> {
                        emit(DataState.Success(true))
                        cartRepo.clear()
                    }
                }

            } ?: throw Exception("please select location")


        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }.flowOn(Dispatchers.IO).conflate()
}