package com.chuify.cleanxoomclient.presentation.ui.product.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chuify.cleanxoomclient.domain.model.Product


@ExperimentalMaterialApi
@Composable
fun ProductDataScreen(
    data: List<Product>,
    onIncrease: (Product) -> Unit,
    onDecrease: (Product) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()

        ) {
            items(data) { it ->
                ProductItem(
                    product = it,
                    increaseCartItem = { onIncrease(it) },
                    decreaseCartItem = { onDecrease(it) }
                )
            }
        }


    }

}

