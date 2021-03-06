package com.chuify.cleanxoomclient.presentation.ui.main

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@ExperimentalMaterialApi
@Composable
fun BottomNavigationBar(
    action: (Int) -> Unit,
    selected: Int,
    notificationBadge: Int,
) {
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Order,
        NavigationItem.Notification,
        NavigationItem.Profile
    )

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 20.dp
    ) {
        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                icon = {
                    if (item is NavigationItem.Notification && notificationBadge > 0) {

                        BadgedBox(badge = {
                            Badge {
                                Text(text = notificationBadge.toString())
                            }
                        }) {
                            Icon(item.icon, contentDescription = item.title)
                        }

                    } else
                        Icon(item.icon, contentDescription = item.title)
                },
                label = { Text(text = item.title) },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = Color.Gray,
                alwaysShowLabel = index == selected,
                selected = index == selected,
                onClick = {
                    action(index)
                }
            )
        }
    }
}
