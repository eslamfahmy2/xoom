package com.chuify.xoomclient.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.chuify.xoomclient.domain.model.Vendor
import com.chuify.xoomclient.presentation.ui.cart.CartScreen
import com.chuify.xoomclient.presentation.ui.checkout.CheckoutScreen
import com.chuify.xoomclient.presentation.ui.checkout.CheckoutViewModel
import com.chuify.xoomclient.presentation.ui.editProfile.EditProfileScreen
import com.chuify.xoomclient.presentation.ui.locations.LocationsScreen
import com.chuify.xoomclient.presentation.ui.main.MainScreen
import com.chuify.xoomclient.presentation.ui.payment.PaymentScreen
import com.chuify.xoomclient.presentation.ui.picklocation.PickLocationScreen
import com.chuify.xoomclient.presentation.ui.track.TrackOrderScreen
import com.chuify.xoomclient.presentation.ui.vendorDetails.VendorDetailsScreen
import com.chuify.xoomclient.presentation.ui.vendors.component.VendorScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun MainNavigation(viewModel: CheckoutViewModel = hiltViewModel()) {

    val navHostController = rememberAnimatedNavController()

    AnimatedNavHost(navController = navHostController, startDestination = Screens.Main.route) {

        composable(route = Screens.Main.fullRoute() ,) {
            MainScreen(navHostController = navHostController)
        }

        composable(
            route = Screens.Vendors.route,
            enterTransition = { _, _ ->
                slideInVertically(initialOffsetY = { 5000 }, animationSpec = tween(500))
            },
            exitTransition = { _, _ ->
                slideOutVertically(targetOffsetY = { 5000 }, animationSpec = tween(1000))
            },
            popExitTransition = { _, _ ->
                slideOutVertically(targetOffsetY = { 5000 }, animationSpec = tween(1000))
            }
        ) {
            VendorScreen(navHostController = navHostController)
        }

        composable(
            route = Screens.VendorDetails.fullRoute(),
            arguments = listOf(navArgument(Screens.VendorDetails.vendorArg) {
                type = NavType.StringType
            }),
            enterTransition = { _, _ ->
                slideInVertically(initialOffsetY = { 5000 }, animationSpec = tween(500))
            },
            exitTransition = { _, _ ->
                slideOutVertically(targetOffsetY = { 5000 }, animationSpec = tween(1000))
            },
            popExitTransition = { _, _ ->
                slideOutVertically(targetOffsetY = { 5000 }, animationSpec = tween(1000))
            }
            ) { backStackEntry ->
            backStackEntry.arguments?.getString(Screens.VendorDetails.vendorArg)?.let {
                Gson().fromJson(it, Vendor::class.java)?.let { vendor ->
                    VendorDetailsScreen(
                        vendor = vendor,
                        navHostController = navHostController
                    )
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
            CheckoutScreen(navHostController = navHostController, viewModel = viewModel)
        }

        composable(route = Screens.PaymentMethod.fullRoute()) {
            PaymentScreen(navHostController = navHostController, viewModel = viewModel)
        }

        composable(route = Screens.PickLocation.fullRoute()) {
            PickLocationScreen(navHostController = navHostController, viewModel = viewModel)
        }

        composable(route = Screens.Track.fullRoute()) {
            TrackOrderScreen(navHostController = navHostController)
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

        composable(route = Screens.EditProfile.fullRoute(),
            enterTransition = { _, _ ->
                slideInVertically(initialOffsetY = { 5000 }, animationSpec = tween(500))
            },
            exitTransition = { _, _ ->
                slideOutVertically(targetOffsetY = { 5000 }, animationSpec = tween(1000))
            },
            popExitTransition = { _, _ ->
                slideOutVertically(targetOffsetY = { 5000 }, animationSpec = tween(1000))
            }
        ) {
            EditProfileScreen(navHostController = navHostController)
        }

        composable(route = Screens.Locations.fullRoute(),
            enterTransition = { _, _ ->
                slideInVertically(initialOffsetY = { 5000 }, animationSpec = tween(500))
            },
            exitTransition = { _, _ ->
                slideOutVertically(targetOffsetY = { 5000 }, animationSpec = tween(1000))
            },
            popExitTransition = { _, _ ->
                slideOutVertically(targetOffsetY = { 5000 }, animationSpec = tween(1000))
            }
        ) {
            LocationsScreen(navHostController = navHostController)
        }

    }

}