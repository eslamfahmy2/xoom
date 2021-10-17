package com.chuify.xoomclient.presentation.ui.order.complet

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chuify.xoomclient.presentation.components.DefaultSnackBar
import com.chuify.xoomclient.presentation.components.LoadingListScreen
import com.chuify.xoomclient.presentation.ui.order.component.CompleteOrderItem
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun CompletedOrdersScreen(
    navHostController: NavHostController,
    viewModel: CompletedOrdersViewModel = hiltViewModel(),
) {

    val scaffoldState = rememberScaffoldState()

    val coroutineScope = rememberCoroutineScope()

    val state by remember {
        viewModel.state
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
            is CompletedOrdersState.Error -> {
                (state as CompletedOrdersState.Error).message?.let {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = it,
                            actionLabel = "Dismiss",
                        )
                    }
                }
            }
            CompletedOrdersState.Loading -> {
                LoadingListScreen(
                    count = 3,
                    height = 250.dp
                )
            }

            is CompletedOrdersState.Success -> {
                val data = (state as CompletedOrdersState.Success).orders
                LazyColumn() {
                    items(data) {
                        CompleteOrderItem(order = it, onReorder = {})
                    }
                }
            }
        }


    }

    LaunchedEffect(true) {
        viewModel.userIntent.send(CompletedOrdersIntent.LoadCompletedOrders)
    }


}