package com.chuify.cleanxoomclient.presentation.ui.accessory.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.chuify.cleanxoomclient.R
import com.chuify.cleanxoomclient.domain.model.Accessory

@Composable
fun AccessoryItem(
    accessory: Accessory,
    increaseCartItem: (Accessory) -> Unit,
    decreaseCartItem: (Accessory) -> Unit,
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        elevation = 15.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {

            Image(
                painter = rememberImagePainter(accessory.image),
                contentDescription = null,
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,

                ) {

                Text(
                    modifier = Modifier
                        .wrapContentSize(Alignment.CenterStart)
                        .weight(0.7f),
                    text = accessory.name,
                    color = MaterialTheme.colors.onSurface,
                    textAlign = TextAlign.Start

                )

                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .weight(0.3f)
                ) {
                    Text(
                        text = accessory.price.toString(),
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.SemiBold

                    )
                    Text(
                        text = stringResource(R.string.currency),
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.SemiBold
                    )

                }


            }
            if (accessory.quantity > 0) {

                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    OutlinedButton(
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
                        border = BorderStroke(
                            width = 1.dp,
                            color = Color.Gray,
                        ),
                        onClick = {
                            decreaseCartItem(accessory)
                        },

                        ) {
                        Text(text = "-")
                    }

                    Text(
                        modifier = Modifier.padding(6.dp),
                        text = accessory.quantity.toString()
                    )

                    Button(
                        onClick = {
                            increaseCartItem(accessory)
                        }) {
                        Text(text = "+")
                    }
                }
            } else {
                Box(modifier = Modifier.padding(8.dp)) {
                    Button(
                        onClick = {
                            increaseCartItem(accessory)
                        }) {
                        Text(text = "+")
                    }
                }
            }

        }


    }
}