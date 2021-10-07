package com.chuify.xoomclient.presentation.ui.product.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.chuify.xoomclient.R
import com.chuify.xoomclient.domain.model.Product


@Composable
fun ProductItem(
    product: Product,
    onItemClick: (Product) -> Unit,
) {

    val quantity = remember {
        mutableStateOf(1)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 15.dp
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {

            val guideline = createGuidelineFromStart(0.3f)
            val guideline2 = createGuidelineFromStart(0.7f)
            val (image, content, third) = createRefs()

            Image(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .padding(8.dp)
                    .size(80.dp, 80.dp)
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
                    fontWeight = FontWeight.Bold
                )

                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(start = 8.dp, bottom = 8.dp, end = 8.dp)
                        .align(Alignment.Start),
                    text = product.refill,
                    color = MaterialTheme.colors.onSurface,
                )

                if (quantity.value > 0) {

                    Row(modifier = Modifier.padding(4.dp)) {

                        Button(
                            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
                            modifier = Modifier
                                .size(30.dp, 30.dp)
                                .border(
                                    width = 1.dp,
                                    shape = RoundedCornerShape(5.dp),
                                    color = Color.Gray,
                                ),
                            onClick = {
                                if (quantity.value > 0) {
                                    quantity.value--
                                }
                            },

                            ) {
                            Text(
                                text = "-",
                                fontSize = 16.sp
                            )
                        }

                        Text(modifier = Modifier.padding(4.dp), text = quantity.value.toString())

                        Button(
                            modifier = Modifier.size(28.dp, 28.dp),
                            onClick = { quantity.value++ }) {
                            Icon(
                                Icons.Filled.Add, contentDescription = null,
                                modifier = Modifier.size(28.dp, 28.dp),
                            )
                        }
                    }
                } else {
                    Button(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(28.dp, 28.dp),
                        onClick = { quantity.value++ }) {
                        Text(text = "+")
                    }

                }

            }

            Column(
                modifier = Modifier
                    .constrainAs(third) {
                        start.linkTo(guideline2)
                        end.linkTo(parent.end)
                    },
            ) {

                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(8.dp)
                        .align(Alignment.Start),
                    text = product.size,
                    color = MaterialTheme.colors.onSurface,
                    fontWeight = FontWeight.Bold
                )
                Row(modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 8.dp, bottom = 8.dp, end = 8.dp)) {
                    Text(
                        text = product.price,
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(R.string.currency),
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.Bold
                    )

                }


            }

        }


    }
}