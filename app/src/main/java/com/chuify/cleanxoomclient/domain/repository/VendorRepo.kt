package com.chuify.cleanxoomclient.domain.repository

import com.chuify.cleanxoomclient.data.remote.dto.AccessoryListDto
import com.chuify.cleanxoomclient.data.remote.dto.ProductListDto
import com.chuify.cleanxoomclient.data.remote.dto.VendorListDto
import com.chuify.cleanxoomclient.domain.utils.ResponseState

interface VendorRepo {

    suspend fun listVendors(): ResponseState<VendorListDto>

    suspend fun listAccessories(): ResponseState<AccessoryListDto>

    suspend fun listProducts(vendorId: String): ResponseState<ProductListDto>

}