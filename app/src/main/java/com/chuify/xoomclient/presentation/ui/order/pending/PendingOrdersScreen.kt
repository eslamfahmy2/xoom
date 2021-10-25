package com.chuify.xoomclient.presentation.ui.order.pending

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chuify.xoomclient.R
import com.chuify.xoomclient.presentation.components.DefaultSnackBar
import com.chuify.xoomclient.presentation.components.LoadingListScreen
import com.chuify.xoomclient.presentation.ui.order.component.PendingOrderItem
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch


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
                LazyColumn() {
                    items(data) { it ->
                        PendingOrderItem(order = it, onTrack = {}, onCancel = {
                            coroutineScope.launch {
                                viewModel.userIntent.send(PendingOrdersIntent.ShowCancel(it))
                            }
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
                            Text(text = stringResource(id = R.string.cancel),
                                modifier = Modifier.padding(8.dp))
                        },
                        text = {

                            Column() {

                                Text(text = stringResource(id = R.string.are_sure),
                                    modifier = Modifier.padding(8.dp))


                                TextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    value = reason.value,
                                    onValueChange = { reason.value = it },
                                    label = {
                                        Text(text = "Reason")
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        autoCorrect = false,
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Done
                                    ),

                                    )

                            }
                        },
                        buttons = {

                            Row(modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {

                                Button(
                                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
                                    modifier = Modifier.padding(4.dp),
                                    onClick = {
                                        coroutineScope.launch {
                                            viewModel.userIntent.send(PendingOrdersIntent.ConfirmCancel(
                                                reason.value))
                                        }
                                    }
                                ) {
                                    Text("confirm")
                                }

                                Button(
                                    modifier = Modifier.padding(4.dp),
                                    onClick = {
                                        coroutineScope.launch {
                                            viewModel.userIntent.send(PendingOrdersIntent.DismissCancel)
                                        }
                                    }
                                ) {
                                    Text("cancel")
                                }
                            }
                        }
                    )

                }

            }
        }


        LaunchedEffect(true) {
            viewModel.userIntent.send(PendingOrdersIntent.LoadPendingOrders)
        }

    }
}