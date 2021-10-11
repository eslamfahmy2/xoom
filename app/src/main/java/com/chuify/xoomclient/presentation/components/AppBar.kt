package com.chuify.xoomclient.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.surface
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = title,
                style = TextStyle(
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            IconButton(
                modifier = Modifier.align(Alignment.CenterVertically),
                onClick = {
                    onToggleTheme()
                },

                ) {
                Box {
                    Icon(Icons.Filled.ShoppingCart, contentDescription = "Localized description")
                    Text(text = "1",
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colors.primary,
                                shape = CircleShape
                            )
                            .padding(4.dp))
                }
            }


        }
    }

}