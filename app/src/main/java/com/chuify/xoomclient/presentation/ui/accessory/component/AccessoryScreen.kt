package com.chuify.xoomclient.presentation.ui.accessory.component

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import com.chuify.xoomclient.presentation.components.DefaultSnackBar
import com.chuify.xoomclient.presentation.components.LoadingListScreen
import com.chuify.xoomclient.presentation.ui.accessory.AccessoryIntent
import com.chuify.xoomclient.presentation.ui.accessory.AccessoryState
import com.chuify.xoomclient.presentation.ui.accessory.AccessoryViewModel
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun AccessoryScreen(viewModel: AccessoryViewModel) {


    val coroutineScope = rememberCoroutineScope()

    val state = viewModel.state.collectAsState().value

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { scaffoldState.snackbarHostState },
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
            is AccessoryState.Error -> {
                state.message?.let {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = it,
                            actionLabel = "Dismiss",
                        )
                    }
                }
            }
            AccessoryState.Loading -> {
                LoadingListScreen(
                    count = 3,
                    height = 250.dp
                )
            }
            is AccessoryState.Success -> {
                AccessoryData(
                    data = state.data,
                    onIncrease = {
                        coroutineScope.launch {
                            viewModel.userIntent.send(AccessoryIntent.IncreaseAccessoryCart(it))
                        }
                    },
                    onDecrease = {
                        coroutineScope.launch {
                            viewModel.userIntent.send(AccessoryIntent.DecreaseAccessoryCart(it))
                        }
                    }
                )
            }
        }
    }


}