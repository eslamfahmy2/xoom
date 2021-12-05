package com.chuify.cleanxoomclient.presentation.navigation.authentication

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.chuify.cleanxoomclient.presentation.navigation.Screens
import com.chuify.cleanxoomclient.presentation.ui.authentication.LoginViewModel
import com.chuify.cleanxoomclient.presentation.ui.authentication.component.AuthenticationScreen
import com.chuify.cleanxoomclient.presentation.ui.authentication.component.SignupScreen
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
fun AuthenticationNavigation(loginViewModel: LoginViewModel = hiltViewModel()) {

    val navHostController = rememberAnimatedNavController()

    AnimatedNavHost(navController = navHostController, startDestination = Screens.Login.route) {


        composable(
            route = Screens.Login.fullRoute(),
        ) {
            AuthenticationScreen(viewModel = loginViewModel, navHostController = navHostController)
        }

        composable(
            route = Screens.SignUp.route,
        ) {
            SignupScreen(viewModel = loginViewModel, navHostController = navHostController)
        }

    }
}