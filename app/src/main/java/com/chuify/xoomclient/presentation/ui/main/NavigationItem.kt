package com.chuify.xoomclient.presentation.ui.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(val icon: ImageVector, val title: String, var selected: Boolean = false) {
    object Home : NavigationItem(Icons.Filled.Home, "Home")
    object Order : NavigationItem(Icons.Filled.List, "Orders")
    object Notification : NavigationItem(Icons.Filled.Notifications, "Notifications")
    object Profile : NavigationItem(Icons.Filled.Person, "Profile")
}
