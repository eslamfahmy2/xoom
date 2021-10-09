package com.chuify.xoomclient.presentation.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.chuify.xoomclient.domain.model.Vendor
import com.chuify.xoomclient.presentation.ui.vendorDetails.VendorDetailsScreen
import com.chuify.xoomclient.presentation.ui.vendors.component.VendorScreenUI
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun MainNavigation(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screen.Vendors.route) {

        composable(route = Screen.Vendors.route) {
            VendorScreenUI(viewModel = hiltViewModel(), navHostController = navController)
        }

        composable(route = Screen.VendorDetails.route) {
            VendorDetailsScreen(vendor = Vendor(id = "11", name = "name", image = "image"))
        }
    }

}