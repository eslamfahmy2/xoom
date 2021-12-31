package com.chuify.cleanxoomclient.presentation.ui.cart

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import com.chuify.cleanxoomclient.domain.model.Cart


@Composable
fun CartItem(
    order: Cart,
    increaseCartItem: (Cart) -> Unit,
    decreaseCartItem: (Cart) -> Unit,
    deletable: Boolean = false,
    delete: (Cart) -> Unit,
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {


            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                if (deletable) {

                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                delete(order)
                            }
                            .padding(8.dp),
                        tint = Color.Red
                    )
                }

                Image(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(80.dp, 80.dp)
                        .clip(RoundedCornerShape(15)),
                    painter = rememberImagePainter(order.image),
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
                        text = order.name,
                        color = MaterialTheme.colors.onSurface,
                    )


                    Row(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(start = 8.dp)
                    ) {
                        Text(
                            text = order.price.toString(),
                            color = MaterialTheme.colors.primary,
                            modifier = Modifier.padding(2.dp)

                        )
                        Text(

                            text = stringResource(R.string.currency),
                            color = MaterialTheme.colors.primary,
                            modifier = Modifier.padding(2.dp)
                        )

                    }


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
                                decreaseCartItem(order)
                            },

                            ) {
                            Text(text = "-")
                        }

                        Text(modifier = Modifier.padding(6.dp), text = order.quantity.toString())

                        Button(
                            onClick = {
                                increaseCartItem(order)
                            }) {
                            Text(text = "+")
                        }
                    }


                }
            }

       /*
            Text(
                text = "X ${order.quantity}",
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(end = 16.dp, top = 8.dp)

            )
*/
        }


    }
}