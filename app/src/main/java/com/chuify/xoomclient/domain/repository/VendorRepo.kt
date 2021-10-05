package com.chuify.xoomclient.domain.repository

import com.chuify.xoomclient.data.remote.dto.AccessoryListDto
import com.chuify.xoomclient.data.remote.dto.ProductListDto
import com.chuify.xoomclient.data.remote.dto.VendorListDto
import com.chuify.xoomclient.domain.utils.ResponseState

interface VendorRepo {

    suspend fun listVendors(): ResponseState<VendorListDto>

    suspend fun listAccessories(): ResponseState<AccessoryListDto>

    suspend fun listProducts(vendorId: String): ResponseState<ProductListDto>

}