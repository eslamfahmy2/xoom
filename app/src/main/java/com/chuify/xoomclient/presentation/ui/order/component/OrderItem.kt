package com.chuify.xoomclient.presentation.ui.order.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.chuify.xoomclient.R
import com.chuify.xoomclient.domain.model.Order

@Composable
fun CompleteOrderItem(
    order: Order,
    onTrack: (Order) -> Unit,
    onCancel: (Order) -> Unit,
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        elevation = 4.dp
    ) {
        Column {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {

                val guideline = createGuidelineFromStart(0.3f)
                val guideline2 = createGuidelineFromStart(0.7f)
                val (image, content, third) = createRefs()

                Image(
                    modifier = Modifier
                        .clip(RoundedCornerShape(25.dp))
                        .padding(8.dp)
                        .size(80.dp, 80.dp)
                        .constrainAs(image) {
                            start.linkTo(parent.start)
                            end.linkTo(guideline)
                            top.linkTo(parent.top)
                        },
                    painter = rememberImagePainter(order.image),
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
                        text = order.name,
                        color = MaterialTheme.colors.onSurface,

                        )

                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(start = 8.dp, bottom = 8.dp, end = 8.dp)
                            .align(Alignment.Start),
                        text = order.refill,
                        color = MaterialTheme.colors.onSurface,
                    )

                    Text(text = order.status)

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
                        text = order.size,
                        color = MaterialTheme.colors.onSurface,

                        )
                    Row(modifier = Modifier
                        .wrapContentSize()
                        .padding(start = 8.dp, bottom = 8.dp, end = 8.dp)) {
                        Text(
                            text = order.price.toString(),
                            color = MaterialTheme.colors.primary,

                            )
                        Text(
                            text = stringResource(R.string.currency),
                            color = MaterialTheme.colors.primary,

                            )

                    }


                }

            }

            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedTextField(
                    modifier = Modifier.padding(16.dp),
                    value = "Text",
                    onValueChange = {},
                    leadingIcon = { (Icons.Filled.Search) },
                )

                OutlinedTextField(
                    modifier = Modifier.padding(16.dp),
                    value = "Text",
                    onValueChange = {},
                    leadingIcon = { (Icons.Filled.Search) },
                )
            }
        }


    }
}


@Composable
fun PendingOrderItem(
    order: Order,
    onTrack: (Order) -> Unit,
    onCancel: (Order) -> Unit,
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        elevation = 4.dp
    ) {
        Column {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {

                val guideline = createGuidelineFromStart(0.3f)
                val guideline2 = createGuidelineFromStart(0.7f)
                val (image, content, third) = createRefs()

                Image(
                    modifier = Modifier
                        .clip(RoundedCornerShape(25.dp))
                        .padding(8.dp)
                        .size(80.dp, 80.dp)
                        .constrainAs(image) {
                            start.linkTo(parent.start)
                            end.linkTo(guideline)
                            top.linkTo(parent.top)
                        },
                    painter = rememberImagePainter(order.image),
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
                        text = order.name,
                        color = MaterialTheme.colors.onSurface,

                        )

                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(start = 8.dp, bottom = 8.dp, end = 8.dp)
                            .align(Alignment.Start),
                        text = order.refill,
                        color = MaterialTheme.colors.onSurface,
                    )

                    Text(text = order.status)

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
                        text = order.size,
                        color = MaterialTheme.colors.onSurface,

                        )
                    Row(modifier = Modifier
                        .wrapContentSize()
                        .padding(start = 8.dp, bottom = 8.dp, end = 8.dp)) {
                        Text(
                            text = order.price.toString(),
                            color = MaterialTheme.colors.primary,

                            )
                        Text(
                            text = stringResource(R.string.currency),
                            color = MaterialTheme.colors.primary,

                            )

                    }


                }

            }


            OutlinedTextField(
                modifier = Modifier.padding(16.dp),
                value = "Reorder",
                onValueChange = {},
                leadingIcon = { (Icons.Filled.Search) },
            )

        }


    }
}