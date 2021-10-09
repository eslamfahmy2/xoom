package com.chuify.xoomclient.presentation.ui.product

import com.chuify.xoomclient.domain.model.Product


sealed class ProductIntent {
    class IncreaseCartItem(val product : Product) : ProductIntent()
    object InitLoad : ProductIntent()
}
