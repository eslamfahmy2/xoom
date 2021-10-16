package com.chuify.xoomclient.presentation.ui.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.chuify.xoomclient.presentation.ui.order.OrdersScreen
import com.chuify.xoomclient.presentation.ui.vendors.component.VendorScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun MainScreen(navHostController: NavHostController) {


    HorizontalPager(
        count = 2) {
        if (it == 0) {
            VendorScreen(navHostController = navHostController)
        } else {
            OrdersScreen(navHostController = navHostController)
        }
    }


}