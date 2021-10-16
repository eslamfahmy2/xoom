package com.chuify.xoomclient.presentation.ui.product.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chuify.xoomclient.domain.model.Product


@ExperimentalMaterialApi
@Composable
fun ProductDataScreen(
    data: List<Product>,
    onIncrease: (Product) -> Unit,
    onDecrease: (Product) -> Unit,
) {
    Box(modifier = Modifier.background(
        shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp) ,
        color = MaterialTheme.colors.surface
    )) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
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

