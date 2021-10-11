package com.chuify.xoomclient.presentation.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.chuify.xoomclient.domain.model.Vendor
import com.chuify.xoomclient.presentation.ui.BaseApplication
import com.chuify.xoomclient.presentation.ui.vendorDetails.VendorDetailsScreen
import com.chuify.xoomclient.presentation.ui.vendors.component.VendorScreen
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun MainNavigation(navController: NavHostController, applicationContext: BaseApplication) {

    NavHost(navController = navController, startDestination = Screen.Vendors.route) {

        composable(route = Screen.Vendors.route) {
            VendorScreen(
                navHostController = navController,
                application = applicationContext)
        }

        composable(route = Screen.VendorDetails.route) {
            VendorDetailsScreen(vendor = Vendor(id = "11", name = "name", image = "image"), )
        }
    }

}