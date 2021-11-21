package com.chuify.cleanxoomclient.presentation.ui.vendors.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.chuify.cleanxoomclient.domain.model.Accessory

@Composable
fun AccessoryChip(
    accessory: Accessory,
    action: (Accessory) -> Unit,
) {
    Surface(
        modifier = Modifier
            .padding(start = 8.dp)
            .clickable {
                action(accessory)
            },
        elevation = 1.dp,
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colors.primary,
    ) {

        Text(
            text = accessory.name,
            style = MaterialTheme.typography.body1,
            color = Color.White,
            modifier = Modifier.padding(
                start = 10.dp,
                end = 10.dp,
                top = 8.dp,
                bottom = 8.dp
            )
        )
    }

}
