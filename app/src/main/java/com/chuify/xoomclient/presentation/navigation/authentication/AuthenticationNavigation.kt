package com.chuify.xoomclient.presentation.navigation.authentication

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import com.chuify.xoomclient.presentation.navigation.Screens
import com.chuify.xoomclient.presentation.ui.authentication.component.AuthenticationScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun AuthenticationNavigation() {

    val navHostController = rememberAnimatedNavController()

    AnimatedNavHost(navController = navHostController, startDestination = Screens.Login.route) {

        composable(
            route = Screens.Login.fullRoute(),
        ) {
            AuthenticationScreen()
        }


    }
}