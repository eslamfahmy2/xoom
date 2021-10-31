package com.chuify.xoomclient.presentation.ui.vendors.component

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chuify.xoomclient.R
import com.chuify.xoomclient.presentation.components.DefaultSnackBar
import com.chuify.xoomclient.presentation.components.HomeBar
import com.chuify.xoomclient.presentation.components.LoadingListScreen
import com.chuify.xoomclient.presentation.navigation.Screens
import com.chuify.xoomclient.presentation.ui.accessoryDetails.AccessoryDetailsIntent
import com.chuify.xoomclient.presentation.ui.accessoryDetails.AccessoryDetailsState
import com.chuify.xoomclient.presentation.ui.accessoryDetails.AccessoryDetailsViewModel
import com.chuify.xoomclient.presentation.ui.accessoryDetails.component.AccessoryPref
import com.chuify.xoomclient.presentation.ui.order.complet.CompletedOrdersIntent
import com.chuify.xoomclient.presentation.ui.signup.TAG
import com.chuify.xoomclient.presentation.ui.vendors.VendorIntent
import com.chuify.xoomclient.presentation.ui.vendors.VendorState
import com.chuify.xoomclient.presentation.ui.vendors.VendorViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.gson.Gson
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun VendorScreen(
    viewModel: VendorViewModel = hiltViewModel(),
    accessoryDetailsViewModel: AccessoryDetailsViewModel = hiltViewModel(),
    navHostController: NavHostController,
) {

    val coroutineScope = rememberCoroutineScope()

    val state by remember { viewModel.state }

    val cartCount by remember { viewModel.cartCount }

    val stateAccessoryPref by remember { accessoryDetailsViewModel.state }

    val scaffoldState = rememberScaffoldState()




    Scaffold(
        topBar = {
            HomeBar(
                title = stringResource(R.string.home),
                action = {
                    navHostController.navigate(Screens.Cart.route)
                },
                cartCount = cartCount
            )
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


        when (state) {
            is VendorState.Error -> {
                (state as VendorState.Error).message?.let {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = it,
                            actionLabel = "Dismiss",
                        )
                    }
                }
            }
            VendorState.Loading -> {
                LoadingListScreen(
                    count = 3,
                    height = 250.dp
                )
            }
            is VendorState.Success -> {

                val isRefreshing by viewModel.refreshing.collectAsState()
                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing),
                    onRefresh = {
                        coroutineScope.launch {

                        }
                    },
                ) {
                    VendorIdlScreen(
                        data = (state as VendorState.Success).data,
                        accessories = viewModel.accessories.value,
                        onItemClicked = {
                            Gson().toJson(it.copy(image = String()))?.let { json ->
                                navHostController.navigate(Screens.VendorDetails.routeWithArgs(json))
                            }
                        },
                        searchText = (state as VendorState.Success).searchText,
                        onTextChange = {
                            coroutineScope.launch {
                                viewModel.userIntent.send(VendorIntent.Filter(it))
                            }
                        },
                        onAccessoryClicked = {
                            coroutineScope.launch {
                                Log.d(TAG, "onAccessoryClicked: $it")
                                accessoryDetailsViewModel.userIntent.send(
                                    AccessoryDetailsIntent.OpenAccessoryPreview(
                                        it
                                    )
                                )
                            }
                        }
                    )
                }

                when (stateAccessoryPref) {
                    AccessoryDetailsState.Dismiss -> {
                    }
                    is AccessoryDetailsState.Error -> {
                    }
                    is AccessoryDetailsState.Success -> {
                        (stateAccessoryPref as AccessoryDetailsState.Success).data?.let {
                            Log.d(TAG, "VendorScreen: on Success render $it")
                            AccessoryPref(accessory = it, viewModel = accessoryDetailsViewModel)
                        }
                    }
                }
            }
        }

        LaunchedEffect(true) {
            viewModel.userIntent.send(VendorIntent.LoadVendors)
        }

    }

}