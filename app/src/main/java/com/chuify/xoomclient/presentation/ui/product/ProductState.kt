package com.chuify.xoomclient.presentation.ui.product

import com.chuify.xoomclient.domain.model.Product


sealed class ProductState {
    data class Success(val data: List<Product> = listOf()) : ProductState()
    data class Error(val message: String? = null) : ProductState()
    object Loading : ProductState()
}