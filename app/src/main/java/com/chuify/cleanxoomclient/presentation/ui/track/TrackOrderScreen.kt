package com.chuify.cleanxoomclient.presentation.ui.track

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.chuify.cleanxoomclient.R
import com.chuify.cleanxoomclient.domain.model.Order
import com.chuify.cleanxoomclient.presentation.components.DefaultSnackBar
import com.chuify.cleanxoomclient.presentation.components.LoadingListScreen
import com.chuify.cleanxoomclient.presentation.components.SecondaryBar
import com.chuify.cleanxoomclient.presentation.navigation.Screens
import com.chuify.cleanxoomclient.presentation.ui.order.component.OrderStatus
import com.google.accompanist.pager.ExperimentalPagerApi


@SuppressLint("StateFlowValueCalledInComposition")
@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun TrackOrderScreen(
    navHostController: NavHostController,
    viewModel: TrackOrderViewModel = hiltViewModel(),
    order: Order,
) {

    val scaffoldState = rememberScaffoldState()

    val state = viewModel.state.collectAsState().value


    Scaffold(
        topBar = {
            SecondaryBar {
                navHostController.popBackStack()
            }
        },
        scaffoldState = scaffoldState,
        snackbarHost = {
            scaffoldState.snackbarHostState
        },
        bottomBar = {
            DefaultSnackBar(
                snackHostState = scaffoldState.snackbarHostState,
                onDismiss = {
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                },
            )
        }
    ) {

        Card(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primary)
                .clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
                .background(MaterialTheme.colors.surface),
            elevation = 20.dp
        )
        {

            when (state) {
                is TrackOrderState.Error -> {
                    state.message?.let {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = it,
                            style = TextStyle(fontSize = 18.sp)
                        )
                    }
                }
                TrackOrderState.Loading -> {
                    LoadingListScreen(count = 5, height = 240.dp)

                }
                is TrackOrderState.Success -> {
                    val trackingData = state.trackData
                    val status = order.status
                    Column(modifier = Modifier.padding(8.dp)) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = stringResource(id = R.string.track_order),
                                style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold)
                            )

                            Icon(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .clickable {
                                        navHostController.navigate(Screens.QR.routeWithArgs(order.id))
                                    },
                                imageVector = Icons.Filled.QrCode,
                                contentDescription = null,
                                tint = MaterialTheme.colors.primary
                            )

                        }

                        LazyColumn(modifier = Modifier.fillMaxSize()) {

                            item {
                                Card(
                                    elevation = 4.dp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color.LightGray)
                                            .padding(4.dp)
                                            .clip(RoundedCornerShape(4))
                                    ) {

                                        Row(
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(8.dp)
                                        ) {


                                            Column {
                                                Text(
                                                    modifier = Modifier
                                                        .padding(4.dp)
                                                        .padding(start = 24.dp),
                                                    text = "Get it by",
                                                    color = Color.Black
                                                )
                                                trackingData.expectedDeliveryTime?.let {
                                                    Text(
                                                        modifier = Modifier
                                                            .padding(4.dp)
                                                            .padding(start = 24.dp),
                                                        text = it,
                                                        color = Color.Black
                                                    )
                                                }

                                            }

                                            Image(
                                                modifier = Modifier,
                                                painter = painterResource(
                                                    id = R.drawable.driver
                                                ),
                                                contentDescription = null,
                                                contentScale = ContentScale.Crop,

                                                )
                                        }


                                    }
                                }
                            }

                            item {

                                Card(
                                    elevation = 4.dp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)

                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {

                                        Icon(
                                            modifier = Modifier.padding(8.dp),
                                            imageVector = Icons.Filled.DarkMode,
                                            contentDescription = null,
                                            tint = if (status == OrderStatus.ORDER_STATUS_SUBMITTED.status ||
                                                status == OrderStatus.ORDER_STATUS_PROCESSING.status ||
                                                status == OrderStatus.ORDER_STATUS_DELIVERING.status ||
                                                status == OrderStatus.ORDER_STATUS_COMPLETED.status
                                            ) Color.Green else
                                                Color.Gray
                                        )
                                        Surface(
                                            modifier = Modifier.size(4.dp, 50.dp),
                                            color = if (status == OrderStatus.ORDER_STATUS_SUBMITTED.status ||
                                                status == OrderStatus.ORDER_STATUS_PROCESSING.status ||
                                                status == OrderStatus.ORDER_STATUS_DELIVERING.status ||
                                                status == OrderStatus.ORDER_STATUS_COMPLETED.status
                                            ) Color.Green else
                                                Color.Gray
                                        ) {}

                                        Text(
                                            modifier = Modifier.padding(8.dp),
                                            text = "Submitted"
                                        )
                                    }

                                }

                            }

                            item {
                                Card(
                                    elevation = 4.dp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)

                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {


                                        Icon(
                                            modifier = Modifier.padding(8.dp),
                                            imageVector = Icons.Filled.Book,
                                            contentDescription = null,
                                            tint = if (status == OrderStatus.ORDER_STATUS_PROCESSING.status ||
                                                status == OrderStatus.ORDER_STATUS_DELIVERING.status ||
                                                status == OrderStatus.ORDER_STATUS_COMPLETED.status
                                            ) Color.Green else
                                                Color.Gray
                                        )
                                        Surface(
                                            modifier = Modifier.size(4.dp, 50.dp),
                                            color = if (status == OrderStatus.ORDER_STATUS_PROCESSING.status ||
                                                status == OrderStatus.ORDER_STATUS_DELIVERING.status ||
                                                status == OrderStatus.ORDER_STATUS_COMPLETED.status
                                            ) Color.Green else
                                                Color.Gray
                                        ) {}

                                        Text(
                                            modifier = Modifier.padding(8.dp),
                                            text = "Accepted"
                                        )
                                    }
                                }
                            }

                            item {
                                Card(
                                    elevation = 4.dp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)

                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {


                                        Icon(
                                            modifier = Modifier.padding(8.dp),
                                            imageVector = Icons.Filled.Motorcycle,
                                            contentDescription = null,
                                            tint = if (status == OrderStatus.ORDER_STATUS_DELIVERING.status ||
                                                status == OrderStatus.ORDER_STATUS_COMPLETED.status
                                            ) Color.Green else
                                                Color.Gray
                                        )
                                        Surface(
                                            modifier = Modifier.size(4.dp, 50.dp),
                                            color = if (status == OrderStatus.ORDER_STATUS_DELIVERING.status ||
                                                status == OrderStatus.ORDER_STATUS_COMPLETED.status
                                            ) Color.Green else
                                                Color.Gray
                                        ) {}

                                        Text(
                                            modifier = Modifier.padding(8.dp),
                                            text = "On Transit"
                                        )
                                    }
                                }
                            }

                            item {
                                Card(
                                    elevation = 4.dp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)

                                ) {

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {


                                        Icon(
                                            modifier = Modifier.padding(8.dp),
                                            imageVector = Icons.Filled.DeliveryDining,
                                            contentDescription = null,
                                            tint = if (status == OrderStatus.ORDER_STATUS_COMPLETED.status) Color.Green else
                                                Color.Gray
                                        )
                                        Surface(
                                            modifier = Modifier.size(4.dp, 50.dp),
                                            color = if (status == OrderStatus.ORDER_STATUS_COMPLETED.status) Color.Green else
                                                Color.Gray
                                        ) {}

                                        Text(
                                            modifier = Modifier.padding(8.dp),
                                            text = "Delivered"
                                        )
                                    }

                                }
                            }
                            // Driver Details
                            item {

                                Card(
                                    elevation = 4.dp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                ) {

                                    Column {

                                        Text(
                                            modifier = Modifier.padding(12.dp),
                                            text = stringResource(id = R.string.driver_details),
                                            style = TextStyle(
                                                fontSize = 25.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        )


                                        Row(
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(8.dp)
                                        ) {

                                            Row(
                                                horizontalArrangement = Arrangement.Start,
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier.padding(8.dp)
                                            ) {

                                                Image(
                                                    modifier = Modifier
                                                        .padding(8.dp)
                                                        .size(80.dp, 80.dp)
                                                        .clip(RoundedCornerShape(10)),
                                                    painter = rememberImagePainter(trackingData.driverImg),
                                                    contentDescription = null,
                                                    contentScale = ContentScale.Crop
                                                )

                                                Column(
                                                    modifier = Modifier.padding(start = 8.dp),
                                                ) {

                                                    Text(
                                                        modifier = Modifier
                                                            .wrapContentSize()
                                                            .padding(8.dp)
                                                            .align(Alignment.Start),
                                                        text = trackingData.getDriverName(),
                                                        color = MaterialTheme.colors.onSurface,
                                                        fontWeight = FontWeight.Bold

                                                    )

                                                    trackingData.driverPhone?.let {
                                                        Text(
                                                            modifier = Modifier
                                                                .wrapContentSize()
                                                                .padding(start = 8.dp)
                                                                .align(Alignment.Start),
                                                            text = it,
                                                            color = MaterialTheme.colors.onSurface,

                                                            )
                                                    }

                                                    Row(
                                                        horizontalArrangement = Arrangement.Start,
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        modifier = Modifier
                                                            .padding(8.dp)
                                                    ) {

                                                        trackingData.driverRating?.let {
                                                            Text(
                                                                modifier = Modifier
                                                                    .wrapContentSize(),
                                                                text = it.toString(),
                                                                color = MaterialTheme.colors.primary,
                                                                fontWeight = FontWeight.Bold
                                                            )
                                                            Icon(
                                                                modifier = Modifier
                                                                    .padding(2.dp),
                                                                imageVector = Icons.Filled.Star,
                                                                contentDescription = null,
                                                                tint = MaterialTheme.colors.primary,
                                                            )
                                                        }

                                                        trackingData.driverNumberOfTrips?.let {
                                                            Text(
                                                                modifier = Modifier.wrapContentSize(),
                                                                text = "(" + it.toString() + ")",
                                                                color = Color.DarkGray
                                                            )
                                                        }
                                                    }


                                                }

                                            }

                                            val context = LocalContext.current

                                            Icon(
                                                modifier = Modifier
                                                    .padding(8.dp)
                                                    .clickable {
                                                        trackingData.driverPhone?.let {
                                                            val dialIntent =
                                                                Intent(Intent.ACTION_DIAL)
                                                            dialIntent.data =
                                                                Uri.parse("tel:$it")
                                                            startActivity(context, dialIntent, null)
                                                        }
                                                    }
                                                    .background(
                                                        Color.Green,
                                                        shape = RoundedCornerShape(8.dp)
                                                    )
                                                    .padding(8.dp),
                                                imageVector = Icons.Filled.Phone,
                                                contentDescription = null,
                                                tint = Color.White
                                            )

                                        }

                                    }

                                }
                            }
                        }

                    }
                }
            }

        }

    }

    LaunchedEffect(true) {
        viewModel.userIntent.send(TrackOrderIntent.TrackOrder(order.id))
        // viewModel.userIntent.send(TrackOrderIntent.TrackOrder("25514"))
    }


}