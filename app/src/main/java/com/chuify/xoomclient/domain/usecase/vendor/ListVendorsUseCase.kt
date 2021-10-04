package com.chuify.xoomclient.domain.usecase.vendor

import com.chuify.xoomclient.domain.mapper.VendorDtoMapper
import com.chuify.xoomclient.domain.model.Vendor
import com.chuify.xoomclient.domain.repository.VendorRepo
import com.chuify.xoomclient.domain.utils.DataState
import com.chuify.xoomclient.domain.utils.ResponseState
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ListVendorsUseCase @Inject constructor(
    private val repo: VendorRepo,
    private val mapper: VendorDtoMapper,

    ) {

    suspend operator fun invoke(
    ) = flow<DataState<List<Vendor>>> {
        try {
            when (val response = repo.listVendors()) {
                is ResponseState.Error -> {
                    emit(DataState.Error(response.message))
                }
                is ResponseState.Success -> {
                    val result = mapper.toDomainList(response.data.vendors)
                    emit(DataState.Success(result))
                }
            }
        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }
}