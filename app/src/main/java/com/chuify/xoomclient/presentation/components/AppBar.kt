package com.chuify.xoomclient.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun AppBar(
    title: String,
    onToggleTheme: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colors.surface,
        elevation = 8.dp,
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {

            Text(
                text = title,
                modifier = Modifier.padding(12.dp),
                style = TextStyle(
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            IconButton(
                onClick = { onToggleTheme() },
                modifier = Modifier.align(CenterVertically)
            ) {
                Icon(Icons.Rounded.MoreVert, contentDescription = "Localized description")
            }


        }
    }

}