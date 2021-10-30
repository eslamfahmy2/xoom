package com.chuify.xoomclient.domain.repository

import com.chuify.xoomclient.data.remote.dto.LocationListDto
import com.chuify.xoomclient.data.remote.dto.StatusDto
import com.chuify.xoomclient.domain.utils.ResponseState

interface LocationRepo {

    suspend fun getLocations(): ResponseState<LocationListDto>

    suspend fun saveAddress(
        addressUrl: String,
        details: String,
        instructions: String,
        lat: Double,
        lng: Double,
    ): ResponseState<StatusDto>
}