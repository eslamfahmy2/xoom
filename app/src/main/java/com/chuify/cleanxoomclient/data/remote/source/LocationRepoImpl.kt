package com.chuify.cleanxoomclient.data.remote.source

import com.chuify.cleanxoomclient.data.remote.dto.LocationListDto
import com.chuify.cleanxoomclient.data.remote.dto.StatusDto
import com.chuify.cleanxoomclient.data.remote.network.ApiInterface
import com.chuify.cleanxoomclient.domain.repository.LocationRepo
import com.chuify.cleanxoomclient.domain.utils.ResponseState
import javax.inject.Inject

class LocationRepoImpl @Inject constructor(
    private val apiInterface: ApiInterface,
) : LocationRepo {


    override suspend fun getLocations(): ResponseState<LocationListDto> = try {
        val response = apiInterface.listLocations()
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

    override suspend fun saveAddress(
        addressUrl: String,
        details: String,
        instructions: String,
        lat: Double,
        lng: Double,
    ): ResponseState<StatusDto> = try {
        val response = apiInterface.saveAddress(
            address_url = addressUrl,
            details = details,
            instructions = instructions,
            latitude = lat,
            longitude = lng
        )
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

    override suspend fun deleteAddress(id: String): ResponseState<StatusDto> = try {
        val response = apiInterface.deleteAddress(
            id = id
        )
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