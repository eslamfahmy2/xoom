package com.chuify.xoomclient.presentation.ui.product.component

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
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.chuify.xoomclient.R
import com.chuify.xoomclient.domain.model.Product


@Composable
fun ProductItem(
    product: Product,
    increaseCartItem: (Product) -> Unit,
    decreaseCartItem: (Product) -> Unit,
) {

    Surface(
        modifier = Modifier
            .padding(8.dp),
        ) {

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = 15.dp,
        ) {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {

                val guideline = createGuidelineFromStart(0.3f)
                val guideline2 = createGuidelineFromStart(0.7f)
                val (image, content, third) = createRefs()

                Image(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(80.dp, 80.dp)
                        .clip(RoundedCornerShape(15))
                        .constrainAs(image) {
                            start.linkTo(parent.start)
                            end.linkTo(guideline)
                            top.linkTo(parent.top)
                        },
                    painter = rememberImagePainter(product.image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier.constrainAs(content) {
                        start.linkTo(guideline)
                        end.linkTo(guideline2)
                    },
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

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .constrainAs(third) {
                            start.linkTo(guideline2)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                        },
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