package com.chuify.xoomclient.presentation.ui.accessory.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chuify.xoomclient.domain.model.Accessory


@Composable
fun AccessoryData(
    data: List<Accessory>,
    onIncrease: (Accessory) -> Unit,
    onDecrease: (Accessory) -> Unit,
) {

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(data) { accessory ->
            AccessoryItem(accessory = accessory,
                increaseCartItem = { onIncrease(it) },
                decreaseCartItem = { onDecrease(it) })
        }
    }


}