package com.chuify.xoomclient.presentation.navigation

sealed class Screen(val route: String) {
    object Vendors : Screen("vendor")
    object Products : Screen("product")
    object VendorDetails : Screen("details")


}
