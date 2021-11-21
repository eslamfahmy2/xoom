package com.chuify.cleanxoomclient.domain.repository

import com.chuify.cleanxoomclient.data.remote.dto.OrderListDto
import com.chuify.cleanxoomclient.data.remote.dto.StatusBooleanDto
import com.chuify.cleanxoomclient.data.remote.dto.StatusDto
import com.chuify.cleanxoomclient.data.remote.dto.TrackDto
import com.chuify.cleanxoomclient.domain.utils.ResponseState

interface OrderRepo {


    suspend fun getPendingOrders(): ResponseState<OrderListDto>

    suspend fun getCompletedOrders(): ResponseState<OrderListDto>

    suspend fun cancelOrder(id: String, reason: String): ResponseState<StatusDto>

    suspend fun trackOrder(id: String): ResponseState<TrackDto>

    suspend fun submitOrder(body: String): ResponseState<StatusBooleanDto>


}