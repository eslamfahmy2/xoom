package com.chuify.cleanxoomclient.presentation.ui.notification

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chuify.cleanxoomclient.R
import com.chuify.cleanxoomclient.domain.model.Notification
import com.chuify.cleanxoomclient.presentation.components.DefaultSnackBar
import com.chuify.cleanxoomclient.presentation.components.LoadingListScreen
import com.chuify.cleanxoomclient.presentation.components.SolidBar
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun NotificationScreen(
    viewModel: NotificationViewModel,
) {


    val scaffoldState = rememberScaffoldState()

    val coroutineScope = rememberCoroutineScope()

    val state = viewModel.state.collectAsState().value

    Scaffold(
        topBar = { SolidBar() },
        scaffoldState = scaffoldState,
        snackbarHost = {
            scaffoldState.snackbarHostState
        },
        bottomBar = {
            DefaultSnackBar(
                snackHostState = scaffoldState.snackbarHostState,
                onDismiss = {
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                },
            )
        }
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primary)
                .clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
                .background(MaterialTheme.colors.surface),
            elevation = 20.dp
        )
        {
            Column(modifier = Modifier.padding(8.dp)) {

                Text(
                    modifier = Modifier.padding(8.dp),
                    text = stringResource(id = R.string.notifications),
                    style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold)
                )

                when (state) {
                    is NotificationState.Error -> {
                        state.message?.let {
                            coroutineScope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = it,
                                    actionLabel = "Dismiss",
                                )
                            }
                        }
                    }
                    NotificationState.Loading -> {
                        LoadingListScreen(
                            count = 5,
                            height = 100.dp
                        )
                    }

                    is NotificationState.Success -> {
                        val data = state.notifications
                        LazyColumn {
                            items(data) {
                                NotificationItem(notification = it, action = {
                                    coroutineScope.launch {
                                        viewModel.userIntent.send(NotificationIntent.MarkRead(it))
                                    }
                                })
                            }
                        }
                    }
                }


            }


        }

    }



    LaunchedEffect(true) {
        viewModel.userIntent.send(NotificationIntent.LoadNotifications)
    }


}

@Composable
fun NotificationItem(
    notification: Notification,
    action: (Notification) -> Unit,
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { action(notification) }
            .padding(8.dp),
        elevation = 5.dp,
        backgroundColor = if (notification.open) MaterialTheme.colors.surface else Color.Gray
    ) {

        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text(text = notification.title+" "+notification.orderId, modifier = Modifier.padding(8.dp))
            Text(text = notification.description, modifier = Modifier.padding(start = 8.dp))
            Text(
                text = notification.time,
                modifier = Modifier
                    .padding(8.dp)
                    .align(End),

                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace
            )

        }

    }

}