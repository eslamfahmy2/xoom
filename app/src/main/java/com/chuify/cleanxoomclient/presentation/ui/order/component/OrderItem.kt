package com.chuify.cleanxoomclient.presentation.ui.order.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.chuify.cleanxoomclient.R
import com.chuify.cleanxoomclient.domain.model.Order
import com.chuify.cleanxoomclient.presentation.theme.myGreen
import com.chuify.cleanxoomclient.presentation.theme.myRed

enum class OrderStatus(val status: String) {
    ORDER_STATUS_SUBMITTED("1"),
    ORDER_STATUS_PROCESSING("2"),
    ORDER_STATUS_DELIVERING("3"),
    ORDER_STATUS_COMPLETED("4"),
    ORDER_STATUS_CANCELLED("5"),


}

@Composable
fun CompleteOrderItem(
    order: Order,
    onReorder: (Order) -> Unit,
) {


    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {

                Image(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(80.dp, 80.dp)
                        .clip(RoundedCornerShape(10)),
                    painter = rememberImagePainter(order.image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {


                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.7f, true)
                            .padding(start = 8.dp),
                        horizontalAlignment = Alignment.Start,
                    ) {

                        Text(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(8.dp)
                                .align(Alignment.Start),
                            text = "#${order.id} " + order.name,
                            color = MaterialTheme.colors.onSurface,
                            maxLines = 1

                        )

                        Text(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(start = 8.dp, bottom = 8.dp, end = 8.dp)
                                .align(Alignment.Start),
                            text = order.refill,
                            color = MaterialTheme.colors.onSurface,
                            maxLines = 1
                        )

                        when (order.status) {
                            OrderStatus.ORDER_STATUS_SUBMITTED.status -> {

                                Text(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .padding(start = 8.dp, bottom = 8.dp, end = 8.dp)
                                        .align(Alignment.Start),
                                    text = stringResource(id = R.string.confirm_processing),
                                    color = myGreen,
                                    maxLines = 1
                                )

                            }

                            OrderStatus.ORDER_STATUS_PROCESSING.status -> {

                                Text(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .padding(start = 8.dp, bottom = 8.dp, end = 8.dp)
                                        .align(Alignment.Start),
                                    text = stringResource(id = R.string.accepted),
                                    color = myGreen,
                                    maxLines = 1
                                )

                            }

                            OrderStatus.ORDER_STATUS_DELIVERING.status -> {

                                Text(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .padding(start = 8.dp, bottom = 8.dp, end = 8.dp)
                                        .align(Alignment.Start),
                                    text = stringResource(id = R.string.on_transit),
                                    color = myGreen,
                                    maxLines = 1
                                )

                            }
                            OrderStatus.ORDER_STATUS_COMPLETED.status -> {

                                Text(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .padding(start = 8.dp, bottom = 8.dp, end = 8.dp)
                                        .align(Alignment.Start),
                                    text = stringResource(id = R.string.completed),
                                    color = myGreen,
                                    maxLines = 1
                                )

                            }
                            OrderStatus.ORDER_STATUS_CANCELLED.status -> {

                                Text(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .padding(start = 8.dp, bottom = 8.dp, end = 8.dp)
                                        .align(Alignment.Start),
                                    text = stringResource(id = R.string.cancelled),
                                    color = myRed,
                                    maxLines = 1
                                )


                            }

                        }


                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.3f, true),
                        horizontalAlignment = Alignment.End,
                    ) {

                        Text(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(8.dp)
                                .align(Alignment.End),
                            text = order.size + "x",
                            color = MaterialTheme.colors.onSurface,
                            maxLines = 1
                        )
                        Row(
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.End)
                                .padding(start = 8.dp, bottom = 8.dp, end = 8.dp)
                        ) {
                            Text(
                                text = order.price,
                                color = MaterialTheme.colors.primary,
                                modifier = Modifier.padding(end = 4.dp),
                                maxLines = 1
                            )
                            Text(
                                text = stringResource(R.string.currency),
                                color = MaterialTheme.colors.primary,
                                maxLines = 1

                            )

                        }


                    }


                }

            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(bottomStartPercent = 20, bottomEndPercent = 20),
                        color = Color.Gray
                    ),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
                onClick = { onReorder(order) }) {
                Image(
                    modifier = Modifier.padding(4.dp),
                    painter = painterResource(id = R.drawable.ic_reorder),
                    contentDescription = null
                )
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = stringResource(id = R.string.reorder),
                    maxLines = 1
                )
            }

        }


    }
}


@Composable
fun PendingOrderItem(
    order: Order,
    onTrack: (Order) -> Unit,
    onCheckPayment: (Order) -> Unit,
    onCancel: (Order) -> Unit,
) {

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column(Modifier.fillMaxWidth()) {

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {

                Image(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(80.dp, 80.dp)
                        .clip(RoundedCornerShape(10)),
                    painter = rememberImagePainter(order.image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column(
                        modifier = Modifier
                            .weight(0.7f, true)
                            .padding(start = 8.dp),
                        horizontalAlignment = Alignment.Start
                    ) {

                        Text(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(8.dp)
                                .align(Alignment.Start),
                            text = "#${order.id} " + order.name,
                            color = MaterialTheme.colors.onSurface,
                            maxLines = 1

                        )

                        Text(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(start = 8.dp, bottom = 8.dp, end = 8.dp)
                                .align(Alignment.Start),
                            text = order.refill,
                            color = MaterialTheme.colors.onSurface,
                            maxLines = 1
                        )

                        when (order.status) {
                            OrderStatus.ORDER_STATUS_SUBMITTED.status -> {

                                Text(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .padding(start = 8.dp, bottom = 8.dp, end = 8.dp)
                                        .align(Alignment.Start),
                                    text = stringResource(id = R.string.confirm_processing),
                                    color = myGreen,
                                    maxLines = 1
                                )

                            }

                            OrderStatus.ORDER_STATUS_PROCESSING.status -> {


                                Text(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .padding(start = 8.dp, bottom = 8.dp, end = 8.dp)
                                        .align(Alignment.Start),
                                    text = stringResource(id = R.string.accepted),
                                    color = myGreen,
                                    maxLines = 1
                                )

                            }

                            OrderStatus.ORDER_STATUS_DELIVERING.status -> {

                                Text(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .padding(start = 8.dp, bottom = 8.dp, end = 8.dp)
                                        .align(Alignment.Start),
                                    text = stringResource(id = R.string.on_transit),
                                    color = myGreen,
                                    maxLines = 1
                                )

                            }
                            OrderStatus.ORDER_STATUS_COMPLETED.status -> {

                                Text(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .padding(start = 8.dp, bottom = 8.dp, end = 8.dp)
                                        .align(Alignment.Start),
                                    text = stringResource(id = R.string.completed),
                                    color = myGreen,
                                    maxLines = 1
                                )

                            }
                            OrderStatus.ORDER_STATUS_CANCELLED.status -> {

                                Text(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .padding(start = 8.dp, bottom = 8.dp, end = 8.dp)
                                        .align(Alignment.Start),
                                    text = stringResource(id = R.string.cancelled),
                                    color = myRed,
                                    maxLines = 1
                                )


                            }

                        }


                    }

                    Column(
                        modifier = Modifier
                            .weight(0.3f, true),
                        horizontalAlignment = Alignment.End
                    ) {

                        Text(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(8.dp)
                                .align(Alignment.End),
                            text = order.size + "x",
                            color = MaterialTheme.colors.onSurface,
                            maxLines = 1

                        )
                        Row(
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.End)
                                .padding(start = 8.dp, bottom = 8.dp, end = 8.dp)
                        ) {
                            Text(
                                text = order.price,
                                color = MaterialTheme.colors.primary,
                                modifier = Modifier.padding(end = 4.dp),
                                maxLines = 1
                            )
                            Text(
                                text = stringResource(R.string.currency),
                                color = MaterialTheme.colors.primary,
                                maxLines = 1
                            )

                        }


                    }

                }

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(bottomStartPercent = 20),
                            color = Color.Gray
                        ),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
                    onClick = { onCancel(order) }) {
                    Image(
                        modifier = Modifier.padding(4.dp),
                        painter = painterResource(id = R.drawable.ic_cancel),
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = stringResource(id = R.string.cancel)
                    )
                }

                if (order.status == OrderStatus.ORDER_STATUS_SUBMITTED.status) {

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                shape = RoundedCornerShape(bottomEndPercent = 20),
                                color = Color.Gray
                            ),
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
                        onClick = { onCheckPayment(order) }) {

                        Image(
                            modifier = Modifier.padding(4.dp),
                            painter = painterResource(id = R.drawable.ic_baseline_add_card_24),
                            contentDescription = null
                        )

                        Text(
                            modifier = Modifier.padding(4.dp),
                            text = stringResource(id = R.string.check),
                            maxLines = 1,
                            color = MaterialTheme.colors.primary
                        )
                    }

                } else {


                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                shape = RoundedCornerShape(bottomEndPercent = 20),
                                color = Color.Gray
                            ),
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
                        onClick = { onTrack(order) }) {

                        Image(
                            modifier = Modifier.padding(4.dp),
                            painter = painterResource(id = R.drawable.ic_track_cude),
                            contentDescription = null
                        )

                        Text(
                            modifier = Modifier.padding(4.dp),
                            text = stringResource(id = R.string.track_order),
                            maxLines = 1,
                            color = MaterialTheme.colors.primary
                        )
                    }

                }

            }
        }


    }
}