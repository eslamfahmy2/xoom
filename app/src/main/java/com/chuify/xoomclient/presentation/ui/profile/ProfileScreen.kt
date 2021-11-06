package com.chuify.xoomclient.presentation.ui.profile

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chuify.xoomclient.R
import com.chuify.xoomclient.presentation.components.DefaultSnackBar
import com.chuify.xoomclient.presentation.components.LoadingListScreen
import com.chuify.xoomclient.presentation.components.SolidBar
import com.chuify.xoomclient.presentation.navigation.Screens
import com.chuify.xoomclient.presentation.ui.signup.TAG
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
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

    val state = viewModel.state.collectAsState().value
    val isDark = viewModel.isDark.collectAsState().value

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
                    text = stringResource(id = R.string.profile),
                    style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold)
                )

                when (state) {
                    is ProfileState.Error -> {
                        state.message?.let {
                            coroutineScope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = it,
                                    actionLabel = "Dismiss",
                                )
                            }
                        }
                    }
                    ProfileState.Loading -> {
                        LoadingListScreen(
                            count = 1,
                            height = 10000.dp
                        )
                    }
                    is ProfileState.Success -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {

                            item {

                                Card(
                                    elevation = 4.dp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(32.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxWidth()
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
                                                text = state.user.firstname,
                                                fontSize = 16.sp,
                                                modifier = Modifier.padding(8.dp)
                                            )
                                        }

                                        Divider(
                                            color = Color.LightGray, thickness = 1.dp,
                                            modifier = Modifier.padding(start = 56.dp),
                                        )


                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(8.dp)
                                                .clickable {
                                                    navHostController.navigate(Screens.EditProfile.fullRoute())
                                                },
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Icon(
                                                    modifier = Modifier.padding(8.dp),
                                                    imageVector = Icons.Filled.Edit,
                                                    contentDescription = null,
                                                    tint = Color.Green
                                                )
                                                Text(
                                                    text = "Edit profile",
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


                                        Divider(
                                            color = Color.LightGray, thickness = 1.dp,
                                            modifier = Modifier.padding(start = 56.dp),
                                        )


                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(8.dp)
                                                .clickable {
                                                    navHostController.navigate(Screens.Locations.fullRoute())
                                                },
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Icon(
                                                    modifier = Modifier.padding(8.dp),
                                                    imageVector = Icons.Filled.LocationCity,
                                                    contentDescription = null,
                                                    tint = Color.Green
                                                )
                                                Text(
                                                    text = stringResource(id = R.string.delivery_address),
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
                                Card(
                                    elevation = 4.dp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(32.dp)
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
                                            Log.d(TAG, "ProfileScreen: is dark $isDark")
                                            Switch(
                                                checked = isDark,
                                                onCheckedChange = {
                                                    coroutineScope.launch {
                                                        viewModel.userIntent.send(
                                                            ProfileIntent.ChangeTheme(
                                                                it
                                                            )
                                                        )
                                                    }
                                                },
                                                colors = SwitchDefaults.colors(
                                                    checkedThumbColor = Color.Green
                                                )

                                            )

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
                                                    imageVector = Icons.Filled.PermIdentity,
                                                    contentDescription = null,
                                                    tint = Color.Green
                                                )
                                                Text(
                                                    text = "First name " + state.user.firstname,
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
                                                    imageVector = Icons.Filled.PermIdentity,
                                                    contentDescription = null,
                                                    tint = Color.Green
                                                )
                                                Text(
                                                    text = "Last name " + state.user.lastname,
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
                                                    imageVector = Icons.Filled.Phone,
                                                    contentDescription = null,
                                                    tint = Color.Green
                                                )
                                                Text(
                                                    text = "Phone number " + state.user.phone,
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


                                        if (state.user.points.isNotEmpty() && state.user.points != "0") {

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
                                                        imageVector = Icons.Filled.Money,
                                                        contentDescription = null,
                                                        tint = Color.Green
                                                    )
                                                    Text(
                                                        text = "Total points  " + state.user.points,
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

                            }

                            item {
                                Button(modifier = Modifier
                                    .padding(32.dp)
                                    .fillMaxWidth(), onClick = { /*TODO*/ }) {
                                    Text(text = "Logout")
                                }
                            }
                        }

                    }
                }


            }


        }

    }

    LaunchedEffect(true) {
        viewModel.userIntent.send(ProfileIntent.LoadProfile)
    }


}