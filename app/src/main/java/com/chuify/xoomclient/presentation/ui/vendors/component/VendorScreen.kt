package com.chuify.xoomclient.presentation.ui.vendors.component

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
import com.chuify.xoomclient.presentation.components.AppBar
import com.chuify.xoomclient.presentation.components.DefaultSnackBar
import com.chuify.xoomclient.presentation.components.LoadingListScreen
import com.chuify.xoomclient.presentation.navigation.Screen
import com.chuify.xoomclient.presentation.ui.BaseApplication
import com.chuify.xoomclient.presentation.ui.vendors.VendorIntent
import com.chuify.xoomclient.presentation.ui.vendors.VendorState
import com.chuify.xoomclient.presentation.ui.vendors.VendorViewModel
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun VendorScreen(viewModel: VendorViewModel = hiltViewModel(), navHostController: NavHostController , application : BaseApplication) {

    val coroutineScope = rememberCoroutineScope()

    val state by remember { viewModel.state }

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(R.string.home),
                onToggleTheme = {
                    application.toggleTheme()
                }
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
                    onItemClicked = {
                        navHostController.navigate(Screen.VendorDetails.route)
                    },
                    searchText = (state as VendorState.Success).searchText,
                    onTextChange = {
                        coroutineScope.launch {
                            viewModel.userIntent.send(VendorIntent.Filter(it))
                        }
                    }
                )
            }
        }
    }

}