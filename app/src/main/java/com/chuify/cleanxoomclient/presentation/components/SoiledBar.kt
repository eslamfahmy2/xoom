package com.chuify.cleanxoomclient.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@ExperimentalMaterialApi
@Composable
fun SolidBar(
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
    ) {

        Icon(
            Icons.Filled.ArrowBackIosNew,
            tint = MaterialTheme.colors.primary,
            contentDescription = null,
            modifier = Modifier
                .padding(12.dp)
                .padding(start = 6.dp)
        )
    }

}