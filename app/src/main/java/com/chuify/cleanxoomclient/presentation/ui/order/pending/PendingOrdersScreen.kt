package com.chuify.cleanxoomclient.presentation.ui.order.pending

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chuify.cleanxoomclient.R
import com.chuify.cleanxoomclient.presentation.components.DefaultSnackBar
import com.chuify.cleanxoomclient.presentation.components.LoadingListScreen
import com.chuify.cleanxoomclient.presentation.navigation.Screens
import com.chuify.cleanxoomclient.presentation.ui.order.component.PendingOrderItem
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.gson.Gson
import kotlinx.coroutines.launch


@SuppressLint("StateFlowValueCalledInComposition")
@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun PendingOrdersScreen(
    navHostController: NavHostController,
    viewModel: PendingOrdersViewModel = hiltViewModel(),
) {

    val scaffoldState = rememberScaffoldState()

    val coroutineScope = rememberCoroutineScope()

    val state by remember {
        viewModel.state
    }

    val dialogState by remember {
        viewModel.cancelDialog
    }


    Scaffold(
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

        when (state) {
            is PendingOrdersState.Error -> {
                (state as PendingOrdersState.Error).message?.let {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = it,
                            actionLabel = "Dismiss",
                        )
                    }
                }
            }

            PendingOrdersState.Loading -> {
                LoadingListScreen(
                    count = 3,
                    height = 250.dp
                )
            }

            is PendingOrdersState.Success -> {
                val data = (state as PendingOrdersState.Success).orders
                LazyColumn {
                    items(data) { it ->
                        PendingOrderItem(order = it, onTrack = {
                            Gson().toJson(
                                it.copy(
                                    image = String(),
                                    products = emptyList(),
                                    locationID = String(),
                                    paymentMethod = String(),
                                    price = String(),
                                    totalPrice = String(),
                                    refill = String(),
                                    size = String(),
                                    name = String()
                                )
                            )?.let { json ->
                                navHostController.navigate(
                                    Screens.Track.routeWithArgs(json)
                                )
                            }
                        }, onCancel = {
                            coroutineScope.launch {
                                viewModel.userIntent.send(PendingOrdersIntent.ShowCancel(it))
                            }
                        }, onCheckPayment = {
                            navHostController.navigate(Screens.CheckPayment.routeWithArgs(it.id))
                        })
                    }
                }

                if (dialogState) {

                    val reason = remember {
                        mutableStateOf(String())
                    }
                    AlertDialog(
                        onDismissRequest = {
                            coroutineScope.launch {
                                viewModel.userIntent.send(PendingOrdersIntent.DismissCancel)
                            }
                        },
                        title = {
                            Text(
                                text = "Order cancellation",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(8.dp)
                            )
                        },
                        text = {

                            val down = remember {
                                mutableStateOf(false)
                            }
                            val reasons = listOf(
                                "Delivery took too long",
                                "Changed my mind",
                                "Pricing - Expensive",
                                "Don't need so, please Refund",
                                "Declined - Payment Issue",
                                "Competition - Local competition preferred",
                                "Other"
                            )
                            Column {

                                Text(
                                    text = stringResource(id = R.string.are_sure),
                                    modifier = Modifier.padding(8.dp)
                                )

                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .clickable { down.value = true },
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = if (reason.value.isEmpty()) "Select a reason" else reason.value,
                                        modifier = Modifier
                                            .weight(0.9f, true)
                                            .padding(8.dp),
                                        color = MaterialTheme.colors.onSurface,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                    )

                                    Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                                }


                                DropdownMenu(expanded = down.value, onDismissRequest = {
                                    down.value = false
                                }) {

                                    reasons.forEach {
                                        DropdownMenuItem(onClick = {
                                            reason.value = it
                                            down.value = false
                                        }) {
                                            Text(
                                                text = it,
                                                modifier = Modifier.padding(8.dp)
                                            )
                                        }
                                    }

                                }

                            }
                        },
                        buttons = {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {

                                Button(
                                    modifier = Modifier.weight(0.5f, true),
                                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
                                    onClick = {

                                        if (reason.value.isEmpty()) {
                                            coroutineScope.launch {
                                                scaffoldState.snackbarHostState.showSnackbar(
                                                    message = "please add reason",
                                                    actionLabel = "Dismiss",
                                                )
                                            }
                                        } else {
                                            coroutineScope.launch {
                                                viewModel.userIntent.send(
                                                    PendingOrdersIntent.ConfirmCancel(
                                                        reason.value
                                                    )
                                                )
                                            }
                                        }
                                    }
                                ) {
                                    Text("Confirm")
                                }

                                Button(
                                    modifier = Modifier.weight(0.5f, true),
                                    onClick = {
                                        coroutineScope.launch {
                                            viewModel.userIntent.send(PendingOrdersIntent.DismissCancel)
                                        }
                                    }
                                ) {
                                    Text("Cancel")
                                }
                            }
                        }
                    )

                }

                val progress = viewModel.progress.collectAsState(initial = false).value
                if (progress) {

                    Dialog(
                        onDismissRequest = { },
                        DialogProperties(
                            dismissOnBackPress = false,
                            dismissOnClickOutside = false
                        )
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(100.dp)
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(8.dp)
                                )
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

            }
        }


        LaunchedEffect(true) {
            viewModel.userIntent.send(PendingOrdersIntent.LoadPendingOrders)
        }

    }
}