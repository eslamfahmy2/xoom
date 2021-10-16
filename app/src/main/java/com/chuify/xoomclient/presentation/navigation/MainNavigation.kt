package com.chuify.xoomclient.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.chuify.xoomclient.domain.model.Vendor
import com.chuify.xoomclient.presentation.ui.BaseApplication
import com.chuify.xoomclient.presentation.ui.cart.CartScreen
import com.chuify.xoomclient.presentation.ui.vendorDetails.VendorDetailsScreen
import com.chuify.xoomclient.presentation.ui.vendors.component.VendorScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.gson.Gson

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun MainNavigation(applicationContext: BaseApplication) {

    val navHostController = rememberAnimatedNavController()

    AnimatedNavHost(navController = navHostController, startDestination = Screens.Vendors.route) {

        composable(
            route = Screens.Vendors.route,
        ) {
            VendorScreen(
                navHostController = navHostController,
                application = applicationContext)
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
                        navHostController = navHostController)
                }
            }

        }


        composable(
            route = Screens.Cart.fullRoute(),
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
            CartScreen(navHostController = navHostController)
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
    }

}