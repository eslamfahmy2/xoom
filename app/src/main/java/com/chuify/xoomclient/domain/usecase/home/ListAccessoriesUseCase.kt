package com.chuify.xoomclient.domain.usecase.home

import com.chuify.xoomclient.domain.mapper.AccessoryDtoMapper
import com.chuify.xoomclient.domain.model.Accessory
import com.chuify.xoomclient.domain.repository.VendorRepo
import com.chuify.xoomclient.domain.utils.DataState
import com.chuify.xoomclient.domain.utils.ResponseState
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ListAccessoriesUseCase @Inject constructor(
    private val repo: VendorRepo,
    private val mapper: AccessoryDtoMapper,

    ) {

    suspend operator fun invoke() = flow<DataState<List<Accessory>>> {
        try {
            when (val response = repo.listAccessories()) {
                is ResponseState.Error -> {
                    emit(DataState.Error(response.message))
                }
                is ResponseState.Success -> {
                    val result = mapper.toDomainList(response.data.accessories ?: listOf())
                    emit(DataState.Success(result))
                }
            }
        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }
}