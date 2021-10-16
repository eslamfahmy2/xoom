package com.chuify.xoomclient.presentation.ui.vendors.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chuify.xoomclient.R
import com.chuify.xoomclient.presentation.components.DefaultSnackBar
import com.chuify.xoomclient.presentation.components.HomeBar
import com.chuify.xoomclient.presentation.components.LoadingListScreen
import com.chuify.xoomclient.presentation.navigation.Screens
import com.chuify.xoomclient.presentation.ui.vendors.VendorIntent
import com.chuify.xoomclient.presentation.ui.vendors.VendorState
import com.chuify.xoomclient.presentation.ui.vendors.VendorViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun VendorScreen(
    viewModel: VendorViewModel = hiltViewModel(),
    navHostController: NavHostController,
) {

    val coroutineScope = rememberCoroutineScope()

    val state by remember { viewModel.state }

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        topBar = {
            HomeBar(
                title = stringResource(R.string.home),
                action = {
                    navHostController.navigate(Screens.Cart.route)
                },
                cartCount = 0
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
                    onAccessoryClicked = { accessory ->

                        Gson().toJson(accessory.toString())?.let { json ->
                            navHostController.navigate(Screens.AccessoryDetails.routeWithArgs(json))

                        }
                    }
                )
            }
        }


    }

}