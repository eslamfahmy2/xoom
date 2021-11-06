package com.chuify.xoomclient.domain.usecase.location

import com.chuify.xoomclient.domain.repository.LocationRepo
import com.chuify.xoomclient.domain.utils.DataState
import com.chuify.xoomclient.domain.utils.ResponseState
import com.chuify.xoomclient.domain.utils.Validator
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class SaveLocationsUseCase @Inject constructor(
    private val locationRepo: LocationRepo,
) {

    suspend operator fun invoke(
        addressUrl: String,
        details: String,
        instructions: String,
        lat: Double,
        lng: Double,
    ) = flow<DataState<Nothing>> {
        try {
            emit(DataState.Loading())
            if (addressUrl.isEmpty()) {
                throw Exception("title required")
            }
            if (details.isEmpty()) {
                throw Exception("details required")
            }
            if (instructions.isEmpty()) {
                throw Exception("instructions required")
            }
            when (val response = locationRepo.saveAddress(
                addressUrl = addressUrl,
                details = details,
                instructions = instructions,
                lat = lat,
                lng = lng
            )) {
                is ResponseState.Error -> {
                    emit(DataState.Error(response.message))
                }
                is ResponseState.Success -> {
                    emit(DataState.Success())
                }
            }
        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }
}