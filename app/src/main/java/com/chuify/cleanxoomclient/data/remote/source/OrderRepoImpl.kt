package com.chuify.cleanxoomclient.data.remote.source

import com.chuify.cleanxoomclient.data.remote.dto.*
import com.chuify.cleanxoomclient.data.remote.network.ApiInterface
import com.chuify.cleanxoomclient.domain.repository.OrderRepo
import com.chuify.cleanxoomclient.domain.utils.ResponseState
import javax.inject.Inject


class OrderRepoImpl @Inject constructor(
    private val apiInterface: ApiInterface,
) : OrderRepo {


    override suspend fun getPendingOrders(): ResponseState<OrderListDto> = try {
        val response = apiInterface.listPendingOrders()
        if (response.isSuccessful) {
            response.body()?.let {
                ResponseState.Success(it)
            } ?: ResponseState.Error("api : boy is empty")
        } else {
            ResponseState.Error(response.errorBody()?.string())
        }
    } catch (e: Exception) {
        ResponseState.Error(e.message)
    }

    override suspend fun getCompletedOrders(): ResponseState<OrderListDto> = try {
        val response = apiInterface.listCompletedOrders()
        if (response.isSuccessful) {
            response.body()?.let {
                ResponseState.Success(it)
            } ?: ResponseState.Error("api : boy is empty")
        } else {
            ResponseState.Error(response.errorBody()?.string())
        }
    } catch (e: Exception) {
        ResponseState.Error(e.message)
    }

    override suspend fun cancelOrder(id: String, reason: String): ResponseState<StatusDto> = try {
        val response = apiInterface.cancelOrder(id, reason)
        if (response.isSuccessful) {
            response.body()?.let {
                ResponseState.Success(it)
            } ?: ResponseState.Error("api : boy is empty")
        } else {
            ResponseState.Error(response.errorBody()?.string())
        }
    } catch (e: Exception) {
        ResponseState.Error(e.message)
    }

    override suspend fun trackOrder(id: String): ResponseState<TrackDto> = try {
        val response = apiInterface.trackOrder(id)
        if (response.isSuccessful) {
            response.body()?.let {
                ResponseState.Success(it)
            } ?: ResponseState.Error("api : boy is empty")
        } else {
            ResponseState.Error(response.errorBody()?.string())
        }
    } catch (e: Exception) {
        ResponseState.Error(e.message)
    }

    override suspend fun submitOrder(body: String): ResponseState<SubmitOrderDto> = try {
        val response = apiInterface.saveOrder(body)
        if (response.isSuccessful) {
            response.body()?.let {
                ResponseState.Success(it)
            } ?: ResponseState.Error("api : boy is empty")
        } else {
            ResponseState.Error(response.errorBody()?.string())
        }
    } catch (e: Exception) {
        ResponseState.Error(e.message)
    }

    override suspend fun confirmPayment(id: String): ResponseState<PaymentDto> = try {
        val response = apiInterface.confirmOrder(id)
        if (response.isSuccessful) {
            response.body()?.let {
                ResponseState.Success(it)
            } ?: ResponseState.Error("api : boy is empty")
        } else {
            ResponseState.Error(response.errorBody()?.string())
        }
    } catch (e: Exception) {
        ResponseState.Error(e.message)
    }
}