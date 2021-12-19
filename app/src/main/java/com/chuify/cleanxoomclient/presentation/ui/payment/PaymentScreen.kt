package com.chuify.cleanxoomclient.presentation.ui.payment

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chuify.cleanxoomclient.R
import com.chuify.cleanxoomclient.domain.model.Order
import com.chuify.cleanxoomclient.domain.model.Payments
import com.chuify.cleanxoomclient.presentation.components.DefaultSnackBar
import com.chuify.cleanxoomclient.presentation.components.PaymentItem
import com.chuify.cleanxoomclient.presentation.components.SecondaryBar
import com.chuify.cleanxoomclient.presentation.navigation.Screens
import com.chuify.cleanxoomclient.presentation.ui.checkout.CheckoutIntent
import com.chuify.cleanxoomclient.presentation.ui.checkout.CheckoutViewModel
import com.chuify.cleanxoomclient.presentation.ui.order.component.OrderStatus
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.gson.Gson
import kotlinx.coroutines.launch

private const val TAG = "PaymentScreen"

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun PaymentScreen(
    navHostController: NavHostController,
    viewModel: CheckoutViewModel = hiltViewModel(),
) {

    val scaffoldState = rememberScaffoldState()

    val coroutineScope = rememberCoroutineScope()

    val paymentMethod = viewModel.paymentMethod.collectAsState().value

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
                .background(MaterialTheme.colors.background),
            elevation = 20.dp
        )
        {


            LazyColumn(modifier = Modifier.fillMaxSize()) {

                item {

                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = stringResource(id = R.string.payment_method),
                        style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold)
                    )
                }

                item {

                    Card(elevation = 8.dp, modifier = Modifier.padding(8.dp)) {

                        Column {

                            Text(
                                text = stringResource(id = R.string.select_payment_methid),
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(16.dp),
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                PaymentItem(
                                    modifier = Modifier.fillMaxWidth(0.9f),
                                    paymentMethod = Payments.MPESA
                                )

                                Checkbox(modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterVertically)
                                    .padding(end = 16.dp),
                                    checked = paymentMethod is Payments.MPESA,
                                    onCheckedChange = {
                                        Log.d(TAG, "PaymentScreen: ")
                                        coroutineScope.launch {
                                            viewModel.userIntent.send(
                                                CheckoutIntent.ChangePayment(
                                                    Payments.MPESA
                                                )
                                            )
                                            navHostController.popBackStack()
                                        }
                                    })

                            }


                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                PaymentItem(
                                    modifier = Modifier.fillMaxWidth(0.9f),
                                    paymentMethod = Payments.CashOnDelivery
                                )

                                Checkbox(modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterVertically)
                                    .padding(end = 16.dp),
                                    checked = paymentMethod is Payments.CashOnDelivery,
                                    onCheckedChange = {
                                        coroutineScope.launch {
                                            viewModel.userIntent.send(
                                                CheckoutIntent.ChangePayment(
                                                    Payments.CashOnDelivery
                                                )
                                            )
                                            navHostController.popBackStack()
                                        }
                                    })

                            }


                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                PaymentItem(
                                    modifier = Modifier.fillMaxWidth(0.9f),
                                    paymentMethod = Payments.Points
                                )

                                Checkbox(modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterVertically)
                                    .padding(end = 16.dp),
                                    checked = paymentMethod is Payments.Points,
                                    onCheckedChange = {
                                        coroutineScope.launch {
                                            viewModel.userIntent.send(
                                                CheckoutIntent.ChangePayment(
                                                    Payments.Points
                                                )
                                            )
                                            navHostController.popBackStack()
                                        }
                                    })

                            }


                        }

                    }

                }

                item {
                    Button(
                        onClick = {
                            navHostController.popBackStack()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        shape = RoundedCornerShape(50)
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.CenterVertically),
                            text = stringResource(id = R.string.next)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CheckPaymentScreen(
    navHostController: NavHostController,
    id: String,
    viewModel: PaymentViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState().value
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(true) {
        if (state is CheckPaymentState.Loading)
            viewModel.userIntent.send(CheckPaymentIntent.Check(id))
    }

    when (state) {
        is CheckPaymentState.Error -> {

            FailScreen(navHostController, state.message ?: "", true)
        }
        is CheckPaymentState.Success -> {

            SuccessScreen(navHostController, state.message ?: "", true)

        }
        is CheckPaymentState.Loading -> {

            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Spacer(modifier = Modifier.padding(8.dp))

                CircularProgressIndicator()

                Spacer(modifier = Modifier.padding(8.dp))
                Spacer(modifier = Modifier.padding(8.dp))


                Text(
                    text = "Waiting for payment \n to be confirmed",
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textAlign = TextAlign.Center,
                )


                Spacer(modifier = Modifier.padding(8.dp))

                Spacer(modifier = Modifier.padding(8.dp))

                LinearProgressIndicator()


                Button(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.userIntent.send(CheckPaymentIntent.Check(id))
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                    shape = RoundedCornerShape(30)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.CenterVertically),
                        text = "Check again...",
                    )
                }
            }

        }
    }
}


@Composable
fun SuccessScreen(
    navHostController: NavHostController,
    msg: String,
    popUp: Boolean? = false
) {

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            Icons.Filled.Check,
            contentDescription = null,
            modifier = Modifier
                .size(150.dp, 150.dp)
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.padding(8.dp))


        Spacer(modifier = Modifier.padding(8.dp))

        Text(
            text = msg,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Spacer(modifier = Modifier.padding(8.dp))


        Button(
            onClick = {
                Gson().toJson(
                    Order(
                        image = String(),
                        products = emptyList(),
                        locationID = String(),
                        paymentMethod = String(),
                        price = String(),
                        totalPrice = String(),
                        refill = String(),
                        size = String(),
                        name = String(),
                        id = "",
                        status = OrderStatus.ORDER_STATUS_PROCESSING.status
                    )
                )?.let { json ->

                    navHostController.popBackStack(navHostController.graph.startDestinationId, true)
                    navHostController.graph.setStartDestination(Screens.Main.route)
                    navHostController.navigate(Screens.Main.route)

                    navHostController.navigate(
                        Screens.Track.routeWithArgs(json)
                    )
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
            shape = RoundedCornerShape(30)
        ) {
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterVertically),
                text = "Track order",
            )
        }

    }

}


@Composable
fun FailScreen(navHostController: NavHostController, msg: String, popUp: Boolean? = false) {


    val shimmerColorShades = listOf(
        MaterialTheme.colors.primary,
        MaterialTheme.colors.error
    )

    val brush = Brush.linearGradient(
        colors = shimmerColorShades,
    )

    Column(
        Modifier
            .fillMaxSize()
            .background(
                brush = brush
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            Icons.Filled.ErrorOutline,
            contentDescription = null,
            modifier = Modifier
                .size(150.dp, 150.dp)
                .padding(8.dp),
            tint = Color.White
        )

        Spacer(modifier = Modifier.padding(8.dp))



        Spacer(modifier = Modifier.padding(8.dp))

        Text(
            text = msg,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.Center,

            )

        Spacer(modifier = Modifier.padding(8.dp))


        Button(
            onClick = {
                onClick(popUp, navHostController)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
            shape = RoundedCornerShape(30)
        ) {
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterVertically),
                text = "Check later",
            )
        }

    }
}

private fun onClick(popUp: Boolean?, navHostController: NavHostController) {

    if (popUp == true) {
        navHostController.popBackStack()
    } else {

        navHostController.popBackStack(navHostController.graph.startDestinationId, true)
        navHostController.graph.setStartDestination(Screens.Main.route)
        navHostController.navigate(Screens.Main.route)

    }
}
