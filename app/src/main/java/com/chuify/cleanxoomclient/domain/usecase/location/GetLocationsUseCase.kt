package com.chuify.cleanxoomclient.domain.usecase.location

import com.chuify.cleanxoomclient.domain.mapper.LocationDtoMapper
import com.chuify.cleanxoomclient.domain.model.Location
import com.chuify.cleanxoomclient.domain.repository.LocationRepo
import com.chuify.cleanxoomclient.domain.utils.DataState
import com.chuify.cleanxoomclient.domain.utils.ResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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
    }.flowOn(Dispatchers.IO).conflate()
}