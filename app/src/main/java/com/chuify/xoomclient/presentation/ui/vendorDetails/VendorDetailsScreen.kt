package com.chuify.xoomclient.presentation.ui.vendorDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chuify.xoomclient.domain.model.Vendor
import com.chuify.xoomclient.presentation.components.AppBar
import com.chuify.xoomclient.presentation.components.CartPreview
import com.chuify.xoomclient.presentation.components.DefaultSnackBar
import com.chuify.xoomclient.presentation.ui.accessory.component.AccessoryScreen
import com.chuify.xoomclient.presentation.ui.product.component.ProductsScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun VendorDetailsScreen(vendor: Vendor, viewModel: VendorDetailsViewModel = hiltViewModel()) {

    val scaffoldState = rememberScaffoldState()

    val state by remember {
        viewModel.state
    }

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
        Box (modifier = Modifier.padding(start = 8.dp , end = 8.dp ,
            bottom = 8.dp ))
        {
            HorizontalPager(count = 2) {
                if (it == 0) {
                    ProductsScreen(viewModel = hiltViewModel(), vendor = vendor)
                } else {
                    AccessoryScreen(viewModel = hiltViewModel())
                }
            }
            when(state){
                is VendorDetailsState.Error -> {}
                VendorDetailsState.Loading -> {}
                is VendorDetailsState.Success -> {

                        val data = (state as VendorDetailsState.Success).data
                        CartPreview(
                            modifier = Modifier.align(Alignment.BottomCenter),
                            quantity = data.totalQuantity.toString(),
                            price = data.totalPrice.toString()) {

                        }

                }
            }



        }


    }


}