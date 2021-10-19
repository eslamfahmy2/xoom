package com.chuify.xoomclient.presentation.ui.checkout

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chuify.xoomclient.R
import com.chuify.xoomclient.presentation.components.*
import com.chuify.xoomclient.presentation.navigation.Screens
import com.chuify.xoomclient.presentation.ui.cart.CartItem
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun CheckoutScreen(
    navHostController: NavHostController,
    viewModel: CheckoutViewModel = hiltViewModel(),
) {

    val scaffoldState = rememberScaffoldState()

    val coroutineScope = rememberCoroutineScope()

    val state by remember {
        viewModel.state
    }


    Scaffold(
        topBar = {
            SecondaryBar() {
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

            when (state) {
                is CheckoutState.Error -> {
                    (state as CheckoutState.Error).message?.let {
                        coroutineScope.launch {
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = it,
                                actionLabel = "Dismiss",
                            )
                        }
                    }
                }
                CheckoutState.Loading -> {
                    LoadingListScreen(
                        count = 3,
                        height = 250.dp
                    )
                }


                is CheckoutState.Success -> {

                    val orders = (state as CheckoutState.Success).orders

                    val total = (state as CheckoutState.Success).cartPreview.totalPrice

                    val paymentMethod by remember {
                        viewModel.paymentMethod
                    }

                    val location by remember {
                        viewModel.location
                    }


                    LazyColumn(modifier = Modifier.fillMaxSize()) {

                        item {
                            Text(
                                modifier = Modifier.padding(16.dp),
                                text = stringResource(id = R.string.checkout),
                                style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold)
                            )
                        }

                        item {


                            Card(elevation = 8.dp, modifier = Modifier.padding(16.dp)) {

                                Column(modifier = Modifier.fillMaxWidth()) {

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween) {

                                        Text(
                                            text = stringResource(id = R.string.delivery_address),
                                            modifier = Modifier
                                                .wrapContentSize()
                                                .padding(16.dp),
                                        )


                                        Text(
                                            text = stringResource(id = R.string.change),
                                            color = MaterialTheme.colors.primary,
                                            modifier = Modifier
                                                .wrapContentSize()
                                                .padding(16.dp)
                                                .clickable {
                                                    navHostController.navigate(Screens.PickLocation.route)
                                                }
                                        )

                                    }
                                    location.firstOrNull { it.selected }?.let {
                                        LocationItem(location = it)
                                    }


                                }


                            }


                        }

                        item {

                            Card(elevation = 8.dp, modifier = Modifier.padding(16.dp)) {

                                Column(modifier = Modifier.fillMaxWidth()) {

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween) {

                                        Text(
                                            text = stringResource(id = R.string.select_payment_methid),
                                            modifier = Modifier
                                                .wrapContentSize()
                                                .padding(16.dp)
                                        )

                                        Text(
                                            text = stringResource(id = R.string.change),
                                            color = MaterialTheme.colors.primary,
                                            modifier = Modifier
                                                .wrapContentSize()
                                                .padding(16.dp)
                                                .clickable {
                                                    navHostController.navigate(Screens.PaymentMethod.route)
                                                }
                                        )

                                    }

                                    PaymentItem(paymentMethod = paymentMethod)


                                }


                            }

                        }

                        item {
                            Card(elevation = 8.dp, modifier = Modifier.padding(16.dp)) {

                                Column {
                                    Text(
                                        modifier = Modifier.padding(16.dp),
                                        text = stringResource(id = R.string.order_details),
                                    )
                                    LazyRow(modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)) {
                                        items(orders) {
                                            CartItem(
                                                order = it,
                                                increaseCartItem = {
                                                    coroutineScope.launch {
                                                        viewModel.userIntent.send(CheckoutIntent.IncreaseItem(
                                                            it))
                                                    }
                                                },
                                                decreaseCartItem = {
                                                    coroutineScope.launch {
                                                        viewModel.userIntent.send(CheckoutIntent.DecreaseItem(
                                                            it))
                                                    }
                                                },
                                                delete = {}
                                            )
                                        }
                                    }

                                }


                            }
                        }

                        item {


                            Column(modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)) {

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween) {

                                    Text(
                                        text = stringResource(id = R.string.sub_total),
                                        color = MaterialTheme.colors.secondaryVariant,
                                    )

                                    Row {
                                        Text(
                                            text = total.toString(),
                                            color = MaterialTheme.colors.secondaryVariant,
                                            modifier = Modifier.padding(2.dp)

                                        )
                                        Text(

                                            text = stringResource(R.string.currency),
                                            color = MaterialTheme.colors.secondaryVariant,
                                            modifier = Modifier.padding(2.dp)
                                        )

                                    }

                                }

                                Spacer(modifier = Modifier.padding(4.dp))

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween) {

                                    Text(
                                        text = stringResource(id = R.string.total)
                                    )

                                    Row {
                                        Text(
                                            text = total.toString(),
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

                        item {
                            Button(
                                onClick = {
                                    navHostController.navigate(Screens.Main.route)

                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                shape = RoundedCornerShape(50)
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .align(CenterVertically),
                                    text = stringResource(id = R.string.confirm_order))
                            }
                        }

                    }


                }


            }
        }

    }

    LaunchedEffect(true) {
        viewModel.userIntent.send(CheckoutIntent.LoadCart)
    }

}

