package com.chuify.xoomclient.presentation.ui.payment

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
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
import com.chuify.xoomclient.domain.model.Payments
import com.chuify.xoomclient.presentation.components.DefaultSnackBar
import com.chuify.xoomclient.presentation.components.PaymentItem
import com.chuify.xoomclient.presentation.components.SecondaryBar
import com.chuify.xoomclient.presentation.ui.checkout.CheckoutIntent
import com.chuify.xoomclient.presentation.ui.checkout.CheckoutViewModel
import com.chuify.xoomclient.presentation.ui.signup.TAG
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch

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

    val paymentMethod by remember {
        viewModel.paymentMethod
    }


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

                    Card(elevation = 2.dp, modifier = Modifier.padding(16.dp)) {

                        Column {

                            Text(
                                text = stringResource(id = R.string.select_payment_methid),
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(16.dp),
                            )

                            Row(modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween) {

                                PaymentItem(modifier = Modifier.fillMaxWidth(0.9f),
                                    paymentMethod = paymentMethod)

                                Checkbox(modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterVertically)
                                    .padding(end = 16.dp),
                                    checked = paymentMethod is Payments.Mpesa,
                                    onCheckedChange = {
                                        Log.d(TAG, "PaymentScreen: ")
                                        coroutineScope.launch {
                                            viewModel.userIntent.send(CheckoutIntent.ChangePayment(Payments.Mpesa))
                                        }
                                    })

                            }


                            Row(modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween) {

                                PaymentItem(modifier = Modifier.fillMaxWidth(0.9f),
                                    paymentMethod = paymentMethod)

                                Checkbox(modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterVertically)
                                    .padding(end = 16.dp),
                                    checked = paymentMethod is Payments.CashOnDelivery,
                                    onCheckedChange = {
                                        coroutineScope.launch {
                                            viewModel.userIntent.send(CheckoutIntent.ChangePayment(Payments.CashOnDelivery))
                                        }
                                    })

                            }


                            Row(modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween) {

                                PaymentItem(modifier = Modifier.fillMaxWidth(0.9f),
                                    paymentMethod = paymentMethod)

                                Checkbox(modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterVertically)
                                    .padding(end = 16.dp),
                                    checked = paymentMethod is Payments.Points,
                                    onCheckedChange = {
                                        coroutineScope.launch {
                                            viewModel.userIntent.send(CheckoutIntent.ChangePayment(
                                                Payments.Points))
                                        }
                                    })

                            }


                        }

                    }

                }

                item {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                viewModel.userIntent.send(CheckoutIntent.ConfirmOrder)
                            }
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
                            text = stringResource(id = R.string.next))
                    }
                }


            }


        }


    }

}

