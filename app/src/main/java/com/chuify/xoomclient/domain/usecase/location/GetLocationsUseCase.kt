package com.chuify.xoomclient.domain.usecase.location

import com.chuify.xoomclient.domain.mapper.LocationDtoMapper
import com.chuify.xoomclient.domain.model.Location
import com.chuify.xoomclient.domain.repository.LocationRepo
import com.chuify.xoomclient.domain.utils.DataState
import com.chuify.xoomclient.domain.utils.ResponseState
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetLocationsUseCase @Inject constructor(
    private val locationRepo: LocationRepo,
    private val locationDtoMapper: LocationDtoMapper,
) {

    suspend operator fun invoke() = flow<DataState<List<Location>>> {
        try {
            emit(DataState.Loading())
            when (val response = locationRepo.getLocations()) {
                is ResponseState.Error -> {
                    emit(DataState.Error(response.message))
                }
                is ResponseState.Success -> {

                    val res = response.data.addresses?.let { it ->
                        it.map { locationDtoMapper.mapToDomainModel(it) }
                    }
                    res?.let {
                        emit(DataState.Success(res))
                    }
                }
            }

        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }
}