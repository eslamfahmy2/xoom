package com.chuify.xoomclient.presentation.ui.vendorDetails

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.chuify.xoomclient.domain.model.Vendor
import com.chuify.xoomclient.presentation.components.AppBar
import com.chuify.xoomclient.presentation.components.DefaultSnackBar
import com.chuify.xoomclient.presentation.ui.accessory.component.AccessoryScreen
import com.chuify.xoomclient.presentation.ui.product.component.ProductsScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun VendorDetailsScreen(vendor: Vendor) {

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        topBar = {
            AppBar(
                title = vendor.name,
                onToggleTheme = { // application.toggleTheme()
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

        HorizontalPager(count = 2) {
            if (it == 0) {
                ProductsScreen(viewModel = hiltViewModel(), vendor = vendor)
            } else {
                AccessoryScreen(viewModel = hiltViewModel())
            }
        }

    }


}