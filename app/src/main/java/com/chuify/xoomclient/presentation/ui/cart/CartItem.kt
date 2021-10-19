package com.chuify.xoomclient.presentation.ui.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Delete
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
import com.chuify.xoomclient.domain.model.Cart


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
        ConstraintLayout(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        ) {

            val guideline = createGuidelineFromStart(0.3f)
            val guideline2 = createGuidelineFromStart(0.7f)
            val (image, content, third) = createRefs()

            Row(modifier = Modifier.constrainAs(image) {
                start.linkTo(parent.start)
                end.linkTo(guideline)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            },
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
                            .padding(16.dp),
                        tint = Color.Red
                    )
                }


                Image(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .size(80.dp, 80.dp),
                    painter = rememberImagePainter(order.image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }

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
                    text = order.name,
                    color = MaterialTheme.colors.onSurface,
                )


                Row(modifier = Modifier.padding(8.dp)) {

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
                            decreaseCartItem(order)
                        },

                        ) {
                        Icon(Icons.Filled.AddShoppingCart,
                            contentDescription = "Localized description")

                    }

                    Text(modifier = Modifier.padding(4.dp), text = order.quantity.toString())

                    Button(
                        modifier = Modifier.size(28.dp, 28.dp),
                        onClick = {
                            increaseCartItem(order)
                        }) {
                        Icon(
                            Icons.Filled.Add, contentDescription = null,
                            modifier = Modifier.size(28.dp, 28.dp),
                        )
                    }
                }


            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .constrainAs(third) {
                        start.linkTo(content.end)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    },
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center

            ) {

                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(end = 16.dp, top = 8.dp)
                        .align(Alignment.End),
                    text = "X ${order.quantity}",
                    color = MaterialTheme.colors.onSurface,

                    )
                Row(modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 8.dp, bottom = 8.dp, end = 8.dp , top = 32.dp)) {
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


            }

        }


    }
}