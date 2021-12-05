package com.chuify.cleanxoomclient.presentation.ui.authentication.component

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.chuify.cleanxoomclient.R
import com.chuify.cleanxoomclient.domain.usecase.auth.LoginResult
import com.chuify.cleanxoomclient.presentation.MainActivity
import com.chuify.cleanxoomclient.presentation.components.DefaultSnackBar
import com.chuify.cleanxoomclient.presentation.components.SolidBar
import com.chuify.cleanxoomclient.presentation.navigation.Screens
import com.chuify.cleanxoomclient.presentation.ui.authentication.AuthenticationIntent
import com.chuify.cleanxoomclient.presentation.ui.authentication.AuthenticationState
import com.chuify.cleanxoomclient.presentation.ui.authentication.LoginViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun AuthenticationScreen(
    navHostController: NavHostController,
    viewModel: LoginViewModel,
) {

    val scaffoldState = rememberScaffoldState()
    val bottomScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(
            confirmStateChange = { false },
            initialValue = BottomSheetValue.Expanded
        )
    )
    val pagerState = rememberPagerState()
    LaunchedEffect(true) {
        pagerState.scroll {

        }
    }
    val coroutineScope = rememberCoroutineScope()

    val state = viewModel.state.collectAsState().value
    val phone = viewModel.phone.collectAsState().value


    Scaffold(
        topBar = { SolidBar() },
        scaffoldState = scaffoldState,
        snackbarHost = { scaffoldState.snackbarHostState },
        bottomBar = {
            DefaultSnackBar(
                snackHostState = scaffoldState.snackbarHostState,
                onDismiss = {
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                },
            )
        },
    ) {


        BottomSheetScaffold(
            scaffoldState = bottomScaffoldState,
            sheetContent = {

                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            coroutineScope.launch {
                                bottomScaffoldState.bottomSheetState.expand()
                            }
                        },
                    elevation = 20.dp
                ) {

                    LoginScreen(
                        phone = phone,
                        onLogin = {
                            coroutineScope.launch {
                                viewModel.userIntent.send(AuthenticationIntent.SignIn)
                            }
                        },
                        onValueChanged = {
                            coroutineScope.launch {
                                viewModel.userIntent.send(
                                    AuthenticationIntent.PhoneChange(
                                        it
                                    )
                                )
                            }
                        }
                    )
                }

            }

        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.primary)
            ) {

                Image(
                    painterResource(R.drawable.xoom_gas_rider),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.Center)
                )
            }

        }

    }


    when (state) {
        is AuthenticationState.Error -> {
            state.message?.let {
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = it,
                        actionLabel = "Dismiss",
                    )
                }
            }
        }
        AuthenticationState.Idl -> {

        }
        AuthenticationState.Loading -> {

            Dialog(
                onDismissRequest = { },
                DialogProperties(
                    dismissOnBackPress = false,
                    dismissOnClickOutside = false
                )
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(100.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    CircularProgressIndicator()
                }
            }
        }
        is AuthenticationState.Success -> {
            when (state.data) {
                LoginResult.Login -> {
                    LocalContext.current.startActivity(
                        Intent(
                            LocalContext.current,
                            MainActivity::class.java
                        )
                    )
                    (LocalContext.current as? ComponentActivity)?.finish()
                }
                LoginResult.Signup -> {
                    viewModel.idl()
                    navHostController.navigate(Screens.SignUp.route)
                }
            }
        }
    }

}



