package com.chuify.cleanxoomclient.presentation.ui.vendors.component

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chuify.cleanxoomclient.R
import com.chuify.cleanxoomclient.presentation.components.DefaultSnackBar
import com.chuify.cleanxoomclient.presentation.components.HomeBar
import com.chuify.cleanxoomclient.presentation.components.LoadingListScreen
import com.chuify.cleanxoomclient.presentation.navigation.Screens
import com.chuify.cleanxoomclient.presentation.ui.accessoryDetails.AccessoryDetailsIntent
import com.chuify.cleanxoomclient.presentation.ui.accessoryDetails.AccessoryDetailsState
import com.chuify.cleanxoomclient.presentation.ui.accessoryDetails.AccessoryDetailsViewModel
import com.chuify.cleanxoomclient.presentation.ui.accessoryDetails.component.AccessoryPref
import com.chuify.cleanxoomclient.presentation.ui.vendors.VendorIntent
import com.chuify.cleanxoomclient.presentation.ui.vendors.VendorState
import com.chuify.cleanxoomclient.presentation.ui.vendors.VendorViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.gson.Gson
import kotlinx.coroutines.launch

private const val TAG = "VendorScreen"

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

    val state = viewModel.state.collectAsState().value

    val cartCount = viewModel.cartCount.collectAsState().value

    val stateAccessoryPref by remember { accessoryDetailsViewModel.state }

    val scaffoldState = rememberScaffoldState()

    val context = LocalContext.current.applicationContext


    Scaffold(
        topBar = {
            HomeBar(
                action = {
                    // context.startActivity(Intent(context, MapsActivity::class.java))
                    // navHostController.navigate(Screens.Cart.route)
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


        Card(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primary)
                .clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
                .background(MaterialTheme.colors.surface),
            elevation = 20.dp
        )
        {
            Column(modifier = Modifier.padding(8.dp)) {

                Text(
                    modifier = Modifier.padding(8.dp),
                    text = stringResource(id = R.string.home),
                    style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold)
                )

                when (state) {
                    is VendorState.Error -> {
                        state.message?.let {
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
                                    viewModel.userIntent.send(VendorIntent.LoadVendors)
                                }
                            },
                        ) {
                            VendorIdlScreen(
                                data = state.data,
                                accessories = viewModel.accessories.value,
                                onItemClicked = {
                                    Gson().toJson(it.copy(image = String()))?.let { json ->
                                        navHostController.navigate(
                                            Screens.VendorDetails.routeWithArgs(
                                                json
                                            )
                                        )
                                    }
                                },
                                searchText = state.searchText,
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
                                    AccessoryPref(
                                        accessory = it,
                                        viewModel = accessoryDetailsViewModel
                                    )
                                }
                            }
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