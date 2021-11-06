package com.chuify.xoomclient.presentation.ui.product

import com.chuify.xoomclient.domain.model.Product


sealed class ProductIntent {
    data class IncreaseCartItem(val product: Product) : ProductIntent()
    data class InitLoad(val id: String) : ProductIntent()
    class Insert(val product: Product) : ProductIntent()
    data class DecreaseOrRemove(val product: Product) : ProductIntent()
}
