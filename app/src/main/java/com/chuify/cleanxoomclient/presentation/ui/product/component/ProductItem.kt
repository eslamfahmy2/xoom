package com.chuify.cleanxoomclient.presentation.ui.product.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.chuify.cleanxoomclient.R
import com.chuify.cleanxoomclient.domain.model.Product


@Composable
fun ProductItem(
    product: Product,
    increaseCartItem: (Product) -> Unit,
    decreaseCartItem: (Product) -> Unit,
) {

    Surface(
        modifier = Modifier

    ) {

        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            elevation = 15.dp,
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {

                    Image(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(80.dp, 80.dp)
                            .clip(RoundedCornerShape(15)),
                        painter = rememberImagePainter(product.image),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        modifier = Modifier,
                    ) {

                        Text(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(8.dp)
                                .align(Alignment.Start),
                            text = product.name,
                            color = MaterialTheme.colors.onSurface,

                            )

                        Text(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(start = 8.dp, bottom = 8.dp, end = 8.dp)
                                .align(Alignment.Start),
                            text = product.refill,
                            color = MaterialTheme.colors.onSurface,
                        )

                        if (product.quantity > 0) {

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
                                        decreaseCartItem(product)
                                    },

                                    ) {
                                    Text(text = "-")


                                }

                                Text(
                                    modifier = Modifier.padding(6.dp),
                                    text = product.quantity.toString()
                                )

                                Button(
                                    onClick = {
                                        increaseCartItem(product)
                                    }) {
                                    Text(text = "+")

                                }
                            }
                        } else {

                            Box(modifier = Modifier.padding(8.dp)) {
                                Button(
                                    onClick = {
                                        increaseCartItem(product)
                                    }) {
                                    Text(text = "+")
                                }
                            }
                        }

                    }

                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {

                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(8.dp)
                            .align(Alignment.End),
                        text = product.size,
                        color = MaterialTheme.colors.onSurface,

                        )
                    Row(
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.End)
                            .padding(start = 8.dp, bottom = 8.dp, end = 8.dp, top = 32.dp)
                    ) {
                        Text(
                            text = product.price.toString(),
                            color = MaterialTheme.colors.primary,

                            )
                        Text(
                            text = stringResource(R.string.currency),
                            color = MaterialTheme.colors.primary,

                            )

                    }


                }

            }


        }
    }
}