package com.chuify.cleanxoomclient.domain.usecase.cart

import android.text.format.DateFormat
import android.util.Log
import com.chuify.cleanxoomclient.domain.model.Cart
import com.chuify.cleanxoomclient.domain.model.Location
import com.chuify.cleanxoomclient.domain.model.Payments
import com.chuify.cleanxoomclient.domain.repository.CartRepo
import com.chuify.cleanxoomclient.domain.repository.OrderRepo
import com.chuify.cleanxoomclient.domain.utils.ResponseState
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
        locations: Location?,
        items: List<Cart>,
    ) = flow<SubmitOrderStatus> {
        try {
            emit(SubmitOrderStatus.Loading())
            if (locations == null || locations.id.isNullOrEmpty()) throw Exception("please select location")
            locations.let { location ->

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
                Log.d("okhttp", "order body  $body")
                when (val response = orderRepo.submitOrder(body)) {
                    is ResponseState.Error -> {
                        throw Exception(response.message)
                    }
                    is ResponseState.Success -> {

                        emit(SubmitOrderStatus.OrderSubmitted(response.toString()))
                        cartRepo.clear()
                        emit(SubmitOrderStatus.PaymentSuccess(response.data.Order?.order_id))

                    }
                }

            } ?: throw Exception("please select location")


        } catch (e: Exception) {
            emit(SubmitOrderStatus.PaymentFail(e.message))
        }
    }.flowOn(Dispatchers.IO).conflate()
}

sealed class SubmitOrderStatus {
    data class Loading(val msg: String? = null) : SubmitOrderStatus()
    data class OrderSubmitted(val msg: String? = null) : SubmitOrderStatus()
    data class PaymentSuccess(val msg: String? = null) : SubmitOrderStatus()
    data class PaymentFail(val msg: String? = null) : SubmitOrderStatus()
}