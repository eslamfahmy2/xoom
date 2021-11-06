package com.chuify.xoomclient.domain.repository

import com.chuify.xoomclient.data.remote.dto.OrderListDto
import com.chuify.xoomclient.data.remote.dto.StatusDto
import com.chuify.xoomclient.data.remote.dto.TrackDto
import com.chuify.xoomclient.domain.utils.ResponseState

interface OrderRepo {


    suspend fun getPendingOrders(): ResponseState<OrderListDto>

    suspend fun getCompletedOrders(): ResponseState<OrderListDto>

    suspend fun cancelOrder(id: String, reason: String): ResponseState<StatusDto>

    suspend fun trackOrder(id: String): ResponseState<TrackDto>

    suspend fun submitOrder(body: String): ResponseState<StatusDto>



}