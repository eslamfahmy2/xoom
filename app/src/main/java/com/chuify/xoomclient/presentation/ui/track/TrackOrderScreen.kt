package com.chuify.xoomclient.presentation.ui.track

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chuify.xoomclient.R
import com.chuify.xoomclient.presentation.components.DefaultSnackBar
import com.chuify.xoomclient.presentation.components.LoadingListScreen
import com.chuify.xoomclient.presentation.components.SecondaryBar
import com.google.accompanist.pager.ExperimentalPagerApi

@SuppressLint("StateFlowValueCalledInComposition")
@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun TrackOrderScreen(
    navHostController: NavHostController,
    viewModel: TrackOrderViewModel = hiltViewModel(),
) {

    val scaffoldState = rememberScaffoldState()

    val coroutineScope = rememberCoroutineScope()

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
                                modifier = Modifier.padding(8.dp),
                                imageVector = Icons.Filled.QrCode,
                                contentDescription = null,
                                tint = Color.Green
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

                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .align(Alignment.CenterEnd)
                                        ) {
                                            Image(
                                                modifier = Modifier
                                                    .align(Alignment.CenterEnd)
                                                    .padding(32.dp),
                                                painter = painterResource(
                                                    id = R.drawable.driver
                                                ),
                                                contentDescription = null,
                                                contentScale = ContentScale.Crop,

                                                )
                                        }
                                        Column(Modifier.align(Alignment.CenterStart)) {
                                            Text(
                                                modifier = Modifier
                                                    .padding(4.dp)
                                                    .padding(start = 24.dp),
                                                text = "Get it by",
                                                color = Color.Black
                                            )
                                            Text(
                                                modifier = Modifier
                                                    .padding(4.dp)
                                                    .padding(start = 24.dp),
                                                text = "12/5",
                                                color = Color.Black
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
                                        verticalAlignment = Alignment.Top
                                    ) {


                                        Icon(
                                            modifier = Modifier.padding(8.dp),
                                            imageVector = Icons.Filled.DarkMode,
                                            contentDescription = null,
                                            tint = Color.Green
                                        )
                                        Surface(
                                            modifier = Modifier.size(4.dp, 50.dp),
                                            color = Color.Green
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
                                        verticalAlignment = Alignment.Top
                                    ) {


                                        Icon(
                                            modifier = Modifier.padding(8.dp),
                                            imageVector = Icons.Filled.Book,
                                            contentDescription = null,
                                            tint = Color.Green
                                        )
                                        Surface(
                                            modifier = Modifier.size(4.dp, 50.dp),
                                            color = Color.LightGray
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
                                        verticalAlignment = Alignment.Top
                                    ) {


                                        Icon(
                                            modifier = Modifier.padding(8.dp),
                                            imageVector = Icons.Filled.Motorcycle,
                                            contentDescription = null,
                                            tint = Color.Green
                                        )
                                        Surface(
                                            modifier = Modifier.size(4.dp, 50.dp),
                                            color = Color.LightGray
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
                                        verticalAlignment = Alignment.Top
                                    ) {


                                        Icon(
                                            modifier = Modifier.padding(8.dp),
                                            imageVector = Icons.Filled.DeliveryDining,
                                            contentDescription = null,
                                            tint = Color.Green
                                        )
                                        Surface(
                                            modifier = Modifier.size(4.dp, 50.dp),
                                            color = Color.LightGray
                                        ) {}

                                        Text(
                                            modifier = Modifier.padding(8.dp),
                                            text = "Delivered"
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

                                    Column {
                                        Row(
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(8.dp)
                                        ) {

                                            Text(
                                                modifier = Modifier.padding(8.dp),
                                                text = stringResource(id = R.string.track_order),
                                                style = TextStyle(
                                                    fontSize = 25.sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            )

                                            Icon(
                                                modifier = Modifier.padding(8.dp),
                                                imageVector = Icons.Filled.Phone,
                                                contentDescription = null,
                                                tint = Color.Green
                                            )


                                        }


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
                                                painter = painterResource(id = R.drawable.driver),
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
                                                    text = "Name",
                                                    color = MaterialTheme.colors.onSurface,

                                                    )

                                                Text(
                                                    modifier = Modifier
                                                        .wrapContentSize()
                                                        .padding(
                                                            start = 8.dp,
                                                            bottom = 8.dp,
                                                            end = 8.dp
                                                        )
                                                        .align(Alignment.Start),
                                                    text = "Phone",
                                                    color = MaterialTheme.colors.onSurface,
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


    }

    LaunchedEffect(true) {
        viewModel.userIntent.send(TrackOrderIntent.TrackOrder("25944"))
    }


}