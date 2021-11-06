package com.chuify.xoomclient.presentation.ui.notification

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
import androidx.compose.runtime.*
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chuify.xoomclient.R
import com.chuify.xoomclient.domain.model.Notification
import com.chuify.xoomclient.presentation.components.DefaultSnackBar
import com.chuify.xoomclient.presentation.components.LoadingListScreen
import com.chuify.xoomclient.presentation.components.SolidBar
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun NotificationScreen(
    navHostController: NavHostController,
    viewModel: NotificationViewModel,
) {


    val scaffoldState = rememberScaffoldState()

    val coroutineScope = rememberCoroutineScope()

    val state by remember {
        viewModel.state
    }

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
                        (state as NotificationState.Error).message?.let {
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
                            count = 3,
                            height = 100.dp
                        )
                    }

                    is NotificationState.Success -> {
                        val data = (state as NotificationState.Success).notifications
                        LazyColumn() {
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

        Column(modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()) {
            Text(text = notification.title, modifier = Modifier.padding(8.dp))
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