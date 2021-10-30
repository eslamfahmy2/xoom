package com.chuify.xoomclient.data.remote.source

import com.chuify.xoomclient.data.remote.dto.LocationListDto
import com.chuify.xoomclient.data.remote.dto.StatusDto
import com.chuify.xoomclient.data.remote.network.ApiInterface
import com.chuify.xoomclient.domain.repository.LocationRepo
import com.chuify.xoomclient.domain.utils.ResponseState
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

}