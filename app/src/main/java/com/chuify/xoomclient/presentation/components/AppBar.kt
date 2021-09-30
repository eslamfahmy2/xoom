package com.chuify.xoomclient.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chuify.xoomclient.presentation.ui.signup.TAG


@Composable
fun AppBar(
    title: String,
    onToggleTheme: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.surface
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

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
                modifier = Modifier.align(Alignment.CenterVertically),
                onClick = {
                    Log.d(TAG, "AppBar: ")
                    onToggleTheme()
                }
            ) {
                Icon(Icons.Filled.Settings, contentDescription = "Localized description")
            }


        }
    }

}