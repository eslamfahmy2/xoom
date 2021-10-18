package com.chuify.xoomclient.presentation.ui.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import com.chuify.xoomclient.presentation.ui.order.OrdersScreen
import com.chuify.xoomclient.presentation.ui.vendors.component.VendorScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun MainScreen(navHostController: NavHostController) {

    val pagerState = rememberPagerState()

    val coroutineScope = rememberCoroutineScope()

    val currentScreen = remember {
        mutableStateOf(0)
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                action = {
                    coroutineScope.launch {
                        currentScreen.value = it
                        pagerState.animateScrollToPage(it)
                    }
                },
                selected = currentScreen.value
            )
        }
    ) {

        HorizontalPager(
            state = pagerState,
            count = 2) {
            if (it == 0) {
                VendorScreen(navHostController = navHostController)
            } else {
                OrdersScreen(navHostController = navHostController)
            }
        }

    }
}