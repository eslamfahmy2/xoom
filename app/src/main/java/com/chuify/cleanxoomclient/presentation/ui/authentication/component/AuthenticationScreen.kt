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
import androidx.hilt.navigation.compose.hiltViewModel
import com.chuify.cleanxoomclient.R
import com.chuify.cleanxoomclient.domain.usecase.auth.LoginResult
import com.chuify.cleanxoomclient.presentation.MainActivity
import com.chuify.cleanxoomclient.presentation.components.DefaultSnackBar
import com.chuify.cleanxoomclient.presentation.components.SolidBar
import com.chuify.cleanxoomclient.presentation.ui.authentication.AuthenticationIntent
import com.chuify.cleanxoomclient.presentation.ui.authentication.AuthenticationState
import com.chuify.cleanxoomclient.presentation.ui.authentication.LoginViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
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
    viewModel: LoginViewModel = hiltViewModel(),
) {


    val scaffoldState = rememberScaffoldState()
    val bottomScaffoldState = rememberBottomSheetScaffoldState()
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val state = viewModel.state.collectAsState().value
    val phone = viewModel.phone.collectAsState().value
    val firstName = viewModel.firstName.collectAsState().value
    val lastName = viewModel.lastName.collectAsState().value
    val email = viewModel.email.collectAsState().value

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

                    HorizontalPager(
                        state = pagerState,
                        count = 2
                    ) {
                        when (it) {
                            0 -> {
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
                            1 -> {
                                SignupScreen(
                                    firstName = firstName,
                                    lastName = lastName,
                                    email = email,
                                    onEmailNameChange = {
                                        coroutineScope.launch {
                                            viewModel.userIntent.send(
                                                AuthenticationIntent.EmailChange(
                                                    it
                                                )
                                            )
                                        }
                                    },
                                    onFirstNameChange = {
                                        coroutineScope.launch {
                                            viewModel.userIntent.send(
                                                AuthenticationIntent.FirstNameChange(
                                                    it
                                                )
                                            )
                                        }
                                    },
                                    onLastNameChange = {
                                        coroutineScope.launch {
                                            viewModel.userIntent.send(
                                                AuthenticationIntent.LastNameChange(
                                                    it
                                                )
                                            )
                                        }
                                    },
                                    onLogin = {
                                        coroutineScope.launch {
                                            pagerState.animateScrollToPage(0)
                                        }
                                    },
                                    onSignup = {
                                        coroutineScope.launch {
                                            viewModel.userIntent.send(AuthenticationIntent.SignUp)
                                        }
                                    }
                                )

                            }

                        }
                    }

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
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                }
            }

        }
    }


}



