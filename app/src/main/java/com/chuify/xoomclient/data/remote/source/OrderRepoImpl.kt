package com.chuify.xoomclient.data.remote.source

import android.util.Log
import com.chuify.xoomclient.data.remote.dto.OrderListDto
import com.chuify.xoomclient.data.remote.dto.StatusBooleanDto
import com.chuify.xoomclient.data.remote.dto.StatusDto
import com.chuify.xoomclient.data.remote.dto.TrackDto
import com.chuify.xoomclient.data.remote.network.ApiInterface
import com.chuify.xoomclient.domain.repository.OrderRepo
import com.chuify.xoomclient.domain.utils.ResponseState
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
        val trackOrders = apiInterface.trackOrders(id)
        Log.d("ofdfodkfod", "trackOrder: ofdfodkfod " + trackOrders)
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

    override suspend fun submitOrder(body: String): ResponseState<StatusBooleanDto> = try {
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

}