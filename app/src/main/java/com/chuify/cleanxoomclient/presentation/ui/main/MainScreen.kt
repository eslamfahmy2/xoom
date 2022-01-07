package com.chuify.cleanxoomclient.presentation.ui.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chuify.cleanxoomclient.presentation.ui.notification.NotificationIntent
import com.chuify.cleanxoomclient.presentation.ui.notification.NotificationScreen
import com.chuify.cleanxoomclient.presentation.ui.notification.NotificationViewModel
import com.chuify.cleanxoomclient.presentation.ui.order.OrdersScreen
import com.chuify.cleanxoomclient.presentation.ui.profile.ProfileScreen
import com.chuify.cleanxoomclient.presentation.ui.vendors.component.VendorScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
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
                3 -> {
                    ProfileScreen(navHostController = navHostController)
                }
            }
        }
    }
    LaunchedEffect(true) {
        notificationViewModel.userIntent.send(NotificationIntent.LoadNotifications)
    }

}