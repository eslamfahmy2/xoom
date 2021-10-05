package com.chuify.xoomclient.domain.usecase.vendor

import com.chuify.xoomclient.domain.mapper.ProductDtoMapper
import com.chuify.xoomclient.domain.model.Product
import com.chuify.xoomclient.domain.repository.VendorRepo
import com.chuify.xoomclient.domain.utils.DataState
import com.chuify.xoomclient.domain.utils.ResponseState
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ListProductsUseCase @Inject constructor(
    private val repo: VendorRepo,
    private val mapper: ProductDtoMapper,

    ) {

    suspend operator fun invoke(vendorID: String) = flow<DataState<List<Product>>> {
        try {
            when (val response = repo.listProducts(vendorID)) {
                is ResponseState.Error -> {
                    emit(DataState.Error(response.message))
                }
                is ResponseState.Success -> {
                    val result = mapper.toDomainList(response.data.products ?: listOf())
                    emit(DataState.Success(result))
                }
            }
        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }
}