package com.chuify.cleanxoomclient.domain.usecase.home

import com.chuify.cleanxoomclient.domain.mapper.VendorDtoMapper
import com.chuify.cleanxoomclient.domain.model.Vendor
import com.chuify.cleanxoomclient.domain.repository.VendorRepo
import com.chuify.cleanxoomclient.domain.utils.DataState
import com.chuify.cleanxoomclient.domain.utils.ResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ListVendorsUseCase @Inject constructor(
    private val repo: VendorRepo,
    private val mapper: VendorDtoMapper,

    ) {

    suspend operator fun invoke() = flow<DataState<List<Vendor>>> {
        try {
            when (val response = repo.listVendors()) {
                is ResponseState.Error -> {
                    emit(DataState.Error(response.message))
                }
                is ResponseState.Success -> {
                    val result = mapper.toDomainList(response.data.vendors ?: listOf())
                    emit(DataState.Success(result))
                }
            }
        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }.flowOn(Dispatchers.IO).conflate()
}