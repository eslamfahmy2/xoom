package com.chuify.xoomclient.presentation.ui.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chuify.xoomclient.presentation.ui.notification.NotificationIntent
import com.chuify.xoomclient.presentation.ui.notification.NotificationScreen
import com.chuify.xoomclient.presentation.ui.notification.NotificationViewModel
import com.chuify.xoomclient.presentation.ui.order.OrdersScreen
import com.chuify.xoomclient.presentation.ui.profile.ProfileScreen
import com.chuify.xoomclient.presentation.ui.vendors.component.VendorScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun MainScreen(navHostController: NavHostController) {

    val pagerState = rememberPagerState()

    val coroutineScope = rememberCoroutineScope()

    val notificationViewModel: NotificationViewModel = hiltViewModel()
    val count = notificationViewModel.notReadCount.collectAsState().value

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                notificationBadge = count,
                action = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(it)
                    }
                },
                selected = pagerState.currentPage
            )
        }
    ) { innerPadding ->
        HorizontalPager(
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
            state = pagerState,
            count = 4
        ) {
            when (it) {
                0 -> {
                    VendorScreen(navHostController = navHostController)
                }
                1 -> {
                    OrdersScreen(navHostController = navHostController)
                }
                2 -> {
                    NotificationScreen(viewModel = notificationViewModel)
                }
                else -> {
                    ProfileScreen(navHostController = navHostController)
                }
            }
        }

    }
    LaunchedEffect(true) {
        notificationViewModel.userIntent.send(NotificationIntent.LoadNotifications)
    }

}