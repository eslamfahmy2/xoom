package com.chuify.xoomclient.data.remote.data_source

import com.chuify.xoomclient.data.remote.dto.VendorListDto
import com.chuify.xoomclient.data.remote.network.ApiInterface
import com.chuify.xoomclient.domain.model.Accessory
import com.chuify.xoomclient.domain.repository.VendorRepo
import com.chuify.xoomclient.domain.utils.ResponseState
import javax.inject.Inject

class VendorRepoImpl @Inject constructor(
    private val apiInterface: ApiInterface,
) : VendorRepo {

    override suspend fun listVendors(): ResponseState<VendorListDto> {
        return apiInterface.listVendors()
    }

    override suspend fun listAccessories(): ResponseState<Accessory> {
        TODO("Not yet implemented")
    }

    override suspend fun listProducts(vendorId: String): ResponseState<Accessory> {
        TODO("Not yet implemented")
    }
}