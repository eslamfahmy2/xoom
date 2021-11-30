package com.chuify.cleanxoomclient.domain.repository

import com.chuify.cleanxoomclient.data.remote.dto.*
import com.chuify.cleanxoomclient.domain.utils.ResponseState

interface OrderRepo {


    suspend fun getPendingOrders(): ResponseState<OrderListDto>

    suspend fun getCompletedOrders(): ResponseState<OrderListDto>

    suspend fun cancelOrder(id: String, reason: String): ResponseState<StatusDto>

    suspend fun trackOrder(id: String): ResponseState<TrackDto>

    suspend fun submitOrder(body: String): ResponseState<SubmitOrderDto>

    suspend fun confirmPayment(id: String): ResponseState<PaymentDto>


}