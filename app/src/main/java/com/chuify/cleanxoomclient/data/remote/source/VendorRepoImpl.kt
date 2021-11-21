package com.chuify.cleanxoomclient.data.remote.source

import com.chuify.cleanxoomclient.data.remote.dto.AccessoryListDto
import com.chuify.cleanxoomclient.data.remote.dto.ProductListDto
import com.chuify.cleanxoomclient.data.remote.dto.VendorListDto
import com.chuify.cleanxoomclient.data.remote.network.ApiInterface
import com.chuify.cleanxoomclient.domain.repository.VendorRepo
import com.chuify.cleanxoomclient.domain.utils.ResponseState
import javax.inject.Inject

class VendorRepoImpl @Inject constructor(
    private val apiInterface: ApiInterface,
) : VendorRepo {

    override suspend fun listVendors(): ResponseState<VendorListDto> = try {
        val response = apiInterface.listVendors()
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

    override suspend fun listAccessories(): ResponseState<AccessoryListDto> = try {
        val response = apiInterface.listAccessories()
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

    override suspend fun listProducts(vendorId: String): ResponseState<ProductListDto> = try {
        val response = apiInterface.listProducts(vendorId = vendorId)
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