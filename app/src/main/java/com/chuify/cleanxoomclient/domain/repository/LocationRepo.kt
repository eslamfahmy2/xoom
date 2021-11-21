package com.chuify.cleanxoomclient.domain.repository

import com.chuify.cleanxoomclient.data.remote.dto.LocationListDto
import com.chuify.cleanxoomclient.data.remote.dto.StatusDto
import com.chuify.cleanxoomclient.domain.utils.ResponseState

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