package com.chuify.xoomclient.presentation.ui.profile

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chuify.xoomclient.presentation.components.DefaultSnackBar
import com.chuify.xoomclient.presentation.components.SolidBar
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun ProfileScreen(
    navHostController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel(),
) {


    val scaffoldState = rememberScaffoldState()

    val coroutineScope = rememberCoroutineScope()

    val state = remember {
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
            LazyColumn(modifier = Modifier.fillMaxSize()) {

                item {

                    Card(
                        elevation = 4.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(50.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, Color.Gray, CircleShape)
                                    .padding(8.dp),
                                imageVector = Icons.Filled.Person,
                                contentDescription = "avatar",
                                contentScale = ContentScale.Crop,

                                )

                            Text(
                                text = "Name",
                                fontSize = 16.sp,
                                modifier = Modifier.padding(8.dp)
                            )
                        }

                    }

                }

                item {
                    Card(
                        elevation = 4.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        modifier = Modifier.padding(8.dp),
                                        imageVector = Icons.Filled.DarkMode,
                                        contentDescription = null,
                                        tint = Color.Green
                                    )
                                    Text(
                                        text = "Dark mode",
                                        fontSize = 16.sp,
                                        modifier = Modifier.padding(4.dp),
                                    )
                                }
                                val check = remember {
                                    mutableStateOf(true)
                                }

                                Switch(checked = check.value, onCheckedChange = {
                                    check.value = it
                                })

                            }

                            Divider(
                                color = Color.LightGray, thickness = 1.dp,
                                modifier = Modifier.padding(start = 56.dp),
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        modifier = Modifier.padding(8.dp),
                                        imageVector = Icons.Filled.DarkMode,
                                        contentDescription = null,
                                        tint = Color.Green
                                    )
                                    Text(
                                        text = "Test",
                                        fontSize = 16.sp,
                                        modifier = Modifier.padding(4.dp),
                                    )
                                }
                                Icon(
                                    modifier = Modifier,
                                    imageVector = Icons.Filled.KeyboardArrowRight,
                                    contentDescription = null,
                                    tint = Color.LightGray
                                )
                            }

                        }
                    }

                }

                item {
                    Button(modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(), onClick = { /*TODO*/ }) {
                        Text(text = "Logout")
                    }
                }
            }

        }

    }

    LaunchedEffect(true) {
        viewModel.userIntent.send(ProfileIntent.LoadProfile)
    }


}