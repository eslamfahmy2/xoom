package com.chuify.xoomclient.presentation.ui.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chuify.xoomclient.presentation.ui.notification.NotificationScreen
import com.chuify.xoomclient.presentation.ui.notification.NotificationViewModel
import com.chuify.xoomclient.presentation.ui.order.OrdersScreen
import com.chuify.xoomclient.presentation.ui.profile.ProfileScreen
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
    val notificationViewModel: NotificationViewModel = hiltViewModel()
    val count by remember {
        notificationViewModel.notReadCount
    }
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                notificationBadge = count,
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
            count = 4) {
            when (it) {
                0 -> {
                    VendorScreen(navHostController = navHostController)
                }
                1 -> {
                    OrdersScreen(navHostController = navHostController)
                }
                2 -> {
                    NotificationScreen(navHostController = navHostController)
                }
                else -> {
                    ProfileScreen(navHostController = navHostController)
                }
            }
        }

    }
}