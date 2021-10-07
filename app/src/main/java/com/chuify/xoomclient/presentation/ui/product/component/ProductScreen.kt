package com.chuify.xoomclient.presentation.ui.product.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chuify.xoomclient.domain.model.Product


@Composable
fun ProductScreen(
    data: List<Product>,
) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(data) {
            ProductItem(product = it , onItemClick = {})
        }
    }
}

