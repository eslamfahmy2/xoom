package com.chuify.cleanxoomclient.presentation.ui.vendors.component

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
import com.chuify.cleanxoomclient.presentation.ui.accessory.component.AccessoryScreen
import com.chuify.cleanxoomclient.presentation.ui.accessoryDetails.AccessoryDetailsState
import com.chuify.cleanxoomclient.presentation.ui.accessoryDetails.AccessoryDetailsViewModel
import com.chuify.cleanxoomclient.presentation.ui.accessoryDetails.component.AccessoryPref
import com.chuify.cleanxoomclient.presentation.ui.vendorDetails.VendorDetails
import com.chuify.cleanxoomclient.presentation.ui.vendors.VendorIntent
import com.chuify.cleanxoomclient.presentation.ui.vendors.VendorState
import com.chuify.cleanxoomclient.presentation.ui.vendors.VendorViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.gson.Gson
import kotlinx.coroutines.launch

private const val TAG = "VendorScreen"

@ExperimentalPagerApi
@ExperimentalComposeUiApi
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

    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            HomeBar(
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

                            Column(Modifier.fillMaxSize()) {

                                val pagerState = rememberPagerState()

                                Row(modifier = Modifier.padding(8.dp)) {

                                    val selected = remember {
                                        mutableStateOf(VendorDetails.GAS)
                                    }

                                    when (selected.value) {
                                        VendorDetails.GAS -> {
                                            Button(
                                                modifier = Modifier.height(40.dp),
                                                onClick = {

                                                }) {
                                                Text(text = "LPG")
                                            }
                                            Spacer(modifier = Modifier.padding(8.dp))

                                            Button(
                                                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
                                                modifier = Modifier
                                                    .border(
                                                        width = 1.dp,
                                                        shape = RoundedCornerShape(5.dp),
                                                        color = Color.Gray,
                                                    )
                                                    .height(40.dp),
                                                onClick = {
                                                    coroutineScope.launch {
                                                        selected.value = VendorDetails.ACCESSORIES
                                                        pagerState.animateScrollToPage(1)
                                                        keyboardController?.hide()
                                                    }
                                                },

                                                ) {
                                                Text(
                                                    modifier = Modifier.fillMaxHeight(),
                                                    text = "ACCESSORIES"
                                                )

                                            }

                                        }
                                        VendorDetails.ACCESSORIES -> {
                                            Button(
                                                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
                                                modifier = Modifier
                                                    .border(
                                                        width = 1.dp,
                                                        shape = RoundedCornerShape(5.dp),
                                                        color = Color.Gray,
                                                    )
                                                    .height(40.dp),
                                                onClick = {
                                                    coroutineScope.launch {
                                                        selected.value = VendorDetails.GAS
                                                        pagerState.animateScrollToPage(0)
                                                    }
                                                },

                                                ) {
                                                Text(
                                                    modifier = Modifier.fillMaxHeight(),
                                                    text = "LPG"
                                                )

                                            }

                                            Spacer(modifier = Modifier.padding(8.dp))

                                            Button(
                                                modifier = Modifier.height(40.dp),
                                                onClick = {

                                                }) {
                                                Text(text = "ACCESSORIES")
                                            }


                                        }
                                    }

                                }

                                HorizontalPager(
                                    state = pagerState,
                                    count = 2,
                                ) {
                                    if (it == 0) {
                                        VendorIdlScreen(
                                            data = state.data,
                                            onItemClicked = {
                                                Gson().toJson(it.copy(image = String()))
                                                    ?.let { json ->
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
                                        )
                                    } else {
                                        AccessoryScreen(viewModel = hiltViewModel())
                                    }
                                }

                            }
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