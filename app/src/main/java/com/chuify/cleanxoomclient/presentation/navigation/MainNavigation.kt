package com.chuify.cleanxoomclient.presentation.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.chuify.cleanxoomclient.domain.model.Order
import com.chuify.cleanxoomclient.domain.model.Vendor
import com.chuify.cleanxoomclient.presentation.ui.cart.CartScreen
import com.chuify.cleanxoomclient.presentation.ui.checkout.CheckoutScreen
import com.chuify.cleanxoomclient.presentation.ui.checkout.CheckoutViewModel
import com.chuify.cleanxoomclient.presentation.ui.checkout.FailScreen
import com.chuify.cleanxoomclient.presentation.ui.checkout.SuccessScreen
import com.chuify.cleanxoomclient.presentation.ui.editProfile.EditProfileScreen
import com.chuify.cleanxoomclient.presentation.ui.locations.LocationsScreen
import com.chuify.cleanxoomclient.presentation.ui.main.MainScreen
import com.chuify.cleanxoomclient.presentation.ui.payment.PaymentScreen
import com.chuify.cleanxoomclient.presentation.ui.picklocation.PickLocationScreen
import com.chuify.cleanxoomclient.presentation.ui.track.QrScreen
import com.chuify.cleanxoomclient.presentation.ui.track.TrackOrderScreen
import com.chuify.cleanxoomclient.presentation.ui.vendorDetails.VendorDetailsScreen
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

    AnimatedNavHost(
        modifier = Modifier.background(MaterialTheme.colors.primary),
        navController = navHostController,
        startDestination = Screens.Main.route
    ) {

        composable(
            route = Screens.Main.fullRoute(),
        ) {
            MainScreen(navHostController = navHostController)
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
            route = Screens.QR.fullRoute(),
            arguments = listOf(navArgument(Screens.QR.arg) {
                type = NavType.StringType
            }),
            enterTransition = { _, _ ->
                slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(1000))
            },
            exitTransition = { _, _ ->
                slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(1000))
            },
            popEnterTransition = { _, _ ->
                slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(1000))
            },
            popExitTransition = { _, _ ->
                slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(1000))
            }
        ) { backStackEntry ->
            backStackEntry.arguments?.getString(Screens.QR.arg)?.let {
                QrScreen(navHostController = navHostController, id = it)
            }

        }


        composable(
            route = Screens.Cart.fullRoute(),
            enterTransition = { _, _ ->
                slideInVertically(initialOffsetY = { 5000 }, animationSpec = tween(500))
            },
            exitTransition = { _, _ ->
                slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(1000))
            },
            popEnterTransition = { _, _ ->
                slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(1000))
            },
            popExitTransition = { _, _ ->
                slideOutVertically(targetOffsetY = { 5000 }, animationSpec = tween(1000))
            }
        ) {
            CartScreen(navHostController = navHostController)
        }



        composable(route = Screens.PaymentMethod.fullRoute(),
            enterTransition = { _, _ ->
                slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(1000))
            },
            exitTransition = { _, _ ->
                slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(1000))
            },
            popEnterTransition = { _, _ ->
                slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(1000))
            },
            popExitTransition = { _, _ ->
                slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(1000))
            }
        ) {
            PaymentScreen(navHostController = navHostController, viewModel = viewModel)
        }

        composable(route = Screens.PickLocation.fullRoute(),
            enterTransition = { _, _ ->
                slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(1000))
            },
            exitTransition = { _, _ ->
                slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(1000))
            },
            popEnterTransition = { _, _ ->
                slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(1000))
            },
            popExitTransition = { _, _ ->
                slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(1000))
            }
        ) {
            PickLocationScreen(navHostController = navHostController, viewModel = viewModel)
        }

        composable(
            route = Screens.Track.fullRoute(),
            arguments = listOf(navArgument(Screens.Track.trackArg) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString(Screens.Track.trackArg)?.let {
                Gson().fromJson(it, Order::class.java)?.let { order ->
                    TrackOrderScreen(navHostController = navHostController, order = order)
                }


            }


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


        //-----------------------------------------------------------------------------------
        //checkout


        composable(
            route = Screens.Checkout.fullRoute(),
            enterTransition = { _, _ ->
                slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(1000))
            },
            exitTransition = { _, _ ->
                slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(1000))
            },
            popEnterTransition = { _, _ ->
                slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(1000))
            },
            popExitTransition = { _, _ ->
                slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(1000))
            }
        ) {
            CheckoutScreen(navHostController = navHostController, viewModel = viewModel)
        }


        composable(
            route = Screens.Success.fullRoute(),
        ) {
            SuccessScreen(navHostController = navHostController)
        }

        composable(
            route = Screens.Fail.fullRoute(),
            arguments = listOf(navArgument(Screens.Fail.arg) {
                type = NavType.StringType
            }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getString(Screens.Fail.arg)?.let {
                FailScreen(navHostController = navHostController, msg = it, viewModel = viewModel)
            }

        }

    }

}