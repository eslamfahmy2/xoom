package com.chuify.xoomclient.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.chuify.xoomclient.domain.model.Vendor
import com.chuify.xoomclient.presentation.ui.cart.CartScreen
import com.chuify.xoomclient.presentation.ui.checkout.CheckoutScreen
import com.chuify.xoomclient.presentation.ui.main.MainScreen
import com.chuify.xoomclient.presentation.ui.payment.PaymentScreen
import com.chuify.xoomclient.presentation.ui.picklocation.PickLocationScreen
import com.chuify.xoomclient.presentation.ui.vendorDetails.VendorDetailsScreen
import com.chuify.xoomclient.presentation.ui.vendors.component.VendorScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.gson.Gson

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun MainNavigation() {

    val navHostController = rememberAnimatedNavController()

    AnimatedNavHost(navController = navHostController, startDestination = Screens.Main.route) {

        composable(route = Screens.Main.fullRoute()) {
            MainScreen(navHostController = navHostController)
        }

        composable(
            route = Screens.Vendors.route,
        ) {
            VendorScreen(navHostController = navHostController)
        }

        composable(
            route = Screens.VendorDetails.fullRoute(),
            arguments = listOf(navArgument(Screens.VendorDetails.vendorArg) {
                type = NavType.StringType
            }),

            ) { backStackEntry ->
            backStackEntry.arguments?.getString(Screens.VendorDetails.vendorArg)?.let {
                Gson().fromJson(it, Vendor::class.java)?.let { vendor ->
                    VendorDetailsScreen(
                        vendor = vendor,
                        navHostController = navHostController)
                }
            }

        }


        composable(
            route = Screens.Cart.fullRoute(),
        ) {
            CartScreen(navHostController = navHostController)
        }

        composable(
            route = Screens.Checkout.fullRoute(),

            ) {
            CheckoutScreen(navHostController = navHostController)
        }

        composable(
            route = Screens.AccessoryDetails.fullRoute(),
            arguments = listOf(navArgument(Screens.AccessoryDetails.accessoryArg) {
                type = NavType.StringType
            })

        ) { backStackEntry ->
            backStackEntry.arguments?.getString(Screens.AccessoryDetails.accessoryArg)?.let {
            }

        }

        composable(route = Screens.PaymentMethod.fullRoute()) {
            PaymentScreen(navHostController = navHostController)
        }

        composable(route = Screens.PickLocation.fullRoute()) {
            PickLocationScreen(navHostController = navHostController)
        }

    }

}