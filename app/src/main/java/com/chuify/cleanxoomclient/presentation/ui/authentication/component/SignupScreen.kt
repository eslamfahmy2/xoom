package com.chuify.cleanxoomclient.presentation.ui.authentication.component

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.chuify.cleanxoomclient.R
import com.chuify.cleanxoomclient.domain.utils.Validator
import com.chuify.cleanxoomclient.presentation.MainActivity
import com.chuify.cleanxoomclient.presentation.components.DefaultSnackBar
import com.chuify.cleanxoomclient.presentation.components.SolidBar
import com.chuify.cleanxoomclient.presentation.ui.authentication.AuthenticationIntent
import com.chuify.cleanxoomclient.presentation.ui.authentication.AuthenticationState
import com.chuify.cleanxoomclient.presentation.ui.authentication.LoginViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun SignupScreen(
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

    val coroutineScope = rememberCoroutineScope()
    val state = viewModel.stateUp.collectAsState().value

    val firstName = viewModel.firstName.collectAsState().value
    val lastName = viewModel.lastName.collectAsState().value
    val email = viewModel.email.collectAsState().value
    val phone = viewModel.phone.collectAsState().value

    val focusManager = LocalFocusManager.current

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

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top
                    ) {


                        Column(
                            modifier = Modifier
                                .wrapContentSize(),
                        ) {

                            Text(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(start = 8.dp),
                                text = "Hello There !",
                                color = MaterialTheme.colors.onSurface,
                                fontSize = 28.sp
                            )

                            Text(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(start = 8.dp),
                                text = "Welcome to Xoom Gas",
                                color = MaterialTheme.colors.onSurface,
                                fontSize = 28.sp
                            )

                        }

                        Column(
                            modifier = Modifier
                                .wrapContentSize()
                        ) {

                            Spacer(modifier = Modifier.padding(4.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Image(
                                    modifier = Modifier
                                        .border(
                                            width = 1.dp,
                                            shape = RoundedCornerShape(5.dp),
                                            color = Color.Gray
                                        )
                                        .padding(10.dp)
                                        .width(40.dp)
                                        .height(35.dp)
                                        .align(Alignment.CenterVertically),
                                    painter = painterResource(
                                        id = R.drawable.flag_kenya
                                    ), contentDescription = null
                                )
                                Spacer(modifier = Modifier.padding(4.dp))

                                TextField(
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(
                                            width = 1.dp,
                                            shape = RoundedCornerShape(5.dp),
                                            color = Color.Gray
                                        )
                                        .align(Alignment.CenterVertically),
                                    value = phone,
                                    onValueChange = {
                                        coroutineScope.launch {
                                            viewModel.userIntent.send(
                                                AuthenticationIntent.PhoneChange(
                                                    it.take(Validator.PHONE_LENGTH)
                                                )
                                            )
                                        }
                                    },
                                    leadingIcon = {
                                        Text(
                                            text = "+254",
                                            color = MaterialTheme.colors.onSurface,
                                            fontSize = 16.sp
                                        )
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        autoCorrect = false,
                                        keyboardType = KeyboardType.Phone,
                                        imeAction = ImeAction.Done
                                    ),
                                    textStyle = TextStyle(
                                        fontSize = 16.sp
                                    )

                                )
                            }

                            Spacer(modifier = Modifier.padding(4.dp))

                            TextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .background(
                                        color = MaterialTheme.colors.surface,
                                        shape = MaterialTheme.shapes.medium
                                    ),
                                value = firstName,
                                onValueChange = {
                                    coroutineScope.launch {
                                        viewModel.userIntent.send(
                                            AuthenticationIntent.FirstNameChange(
                                                it
                                            )
                                        )
                                    }
                                },
                                label = {
                                    Text(text = "First name")
                                },
                                keyboardOptions = KeyboardOptions(
                                    autoCorrect = false,
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next
                                ),
                                textStyle = TextStyle(
                                    fontSize = 16.sp
                                )

                            )


                            TextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .background(
                                        color = MaterialTheme.colors.surface,
                                    ),
                                value = lastName,
                                onValueChange = {
                                    coroutineScope.launch {
                                        viewModel.userIntent.send(
                                            AuthenticationIntent.LastNameChange(
                                                it
                                            )
                                        )
                                    }
                                },
                                label = {
                                    Text(text = "Last name")
                                },
                                keyboardOptions = KeyboardOptions(
                                    autoCorrect = false,
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next
                                ),
                                textStyle = TextStyle(
                                    fontSize = 16.sp
                                )

                            )


                            TextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .background(
                                        color = MaterialTheme.colors.surface,
                                    ),
                                value = email,
                                onValueChange = {
                                    coroutineScope.launch {
                                        viewModel.userIntent.send(
                                            AuthenticationIntent.EmailChange(
                                                it
                                            )
                                        )
                                    }
                                },
                                label = {
                                    Text(text = "Email")
                                },
                                keyboardOptions = KeyboardOptions(
                                    autoCorrect = false,
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next
                                ),
                                textStyle = TextStyle(
                                    fontSize = 16.sp
                                )

                            )


                            Button(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth(),
                                onClick = {
                                    focusManager.clearFocus()
                                    coroutineScope.launch {
                                        viewModel.userIntent.send(
                                            AuthenticationIntent.SignUp
                                        )
                                    }
                                })
                            {
                                Text(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .background(
                                            shape = MaterialTheme.shapes.large,
                                            color = MaterialTheme.colors.primary
                                        ),
                                    text = "Signup",
                                    style = TextStyle(
                                        fontSize = 16.sp
                                    )
                                )
                            }

                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center

                        ) {
                            Text(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(8.dp),
                                text = "Do you have account?",
                                color = MaterialTheme.colors.onSurface,
                                fontSize = 16.sp
                            )

                            Text(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(8.dp)
                                    .clickable {
                                        focusManager.clearFocus()
                                        navHostController.popBackStack()
                                    },
                                text = "Login",
                                color = MaterialTheme.colors.primary,
                                fontSize = 16.sp
                            )
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

            LocalContext.current.startActivity(
                Intent(
                    LocalContext.current,
                    MainActivity::class.java
                )
            )
            (LocalContext.current as? ComponentActivity)?.finish()
        }
    }


}