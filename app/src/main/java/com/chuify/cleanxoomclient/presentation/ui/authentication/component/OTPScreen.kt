package com.chuify.cleanxoomclient.presentation.ui.authentication.component

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.chuify.cleanxoomclient.R
import com.chuify.cleanxoomclient.presentation.AuthenticationActivity
import com.chuify.cleanxoomclient.presentation.components.DefaultSnackBar
import com.chuify.cleanxoomclient.presentation.components.LoadingDialog
import com.chuify.cleanxoomclient.presentation.components.SolidBar
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
fun OTPScreen(
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
    val phone = viewModel.phone.collectAsState().value
    val state = viewModel.stateVerify.collectAsState().value
    val activity = LocalContext.current as AuthenticationActivity



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
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {


                        Text(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(start = 8.dp),
                            text = stringResource(R.string.verify_your_mobile_number),
                            color = MaterialTheme.colors.onSurface,
                            fontSize = 25.sp
                        )
                        Spacer(modifier = Modifier.padding(4.dp))

                        Text(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(start = 8.dp),
                            text = stringResource(R.string.enter_the_verification_code_sent_to) + " " + phone,
                            color = MaterialTheme.colors.onSurface,
                            fontSize = 18.sp
                        )

                        Spacer(modifier = Modifier.padding(16.dp))

                        val focusRequesterOne = FocusRequester()
                        val one = remember {
                            mutableStateOf("")
                        }
                        val focusRequesterTwo = FocusRequester()
                        val two = remember {
                            mutableStateOf("")
                        }
                        val focusRequesterThree = FocusRequester()
                        val three = remember {
                            mutableStateOf("")
                        }
                        val focusRequesterFour = FocusRequester()

                        val four = remember {
                            mutableStateOf("")
                        }
                        val max = 1

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {

                            OutlinedTextField(
                                value = one.value,
                                singleLine = true,
                                onValueChange = {
                                    if (it.length <= max) {
                                        one.value = it
                                        focusRequesterTwo.requestFocus()
                                    }
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(60.dp)
                                    .focusRequester(focusRequesterOne),
                                maxLines = 1,
                                textStyle = LocalTextStyle.current.copy(
                                    textAlign = TextAlign.Center
                                )
                            )
                            //------------------------------------------------------
                            OutlinedTextField(
                                value = two.value,
                                singleLine = true,
                                onValueChange = {
                                    if (it.length <= max) {
                                        two.value = it
                                        focusRequesterThree.requestFocus()
                                    }
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(60.dp)
                                    .focusRequester(focusRequesterTwo),
                                maxLines = 1,
                                textStyle = LocalTextStyle.current.copy(
                                    textAlign = TextAlign.Center
                                )
                            )
                            //-----------------------------------------------------
                            OutlinedTextField(
                                value = three.value,
                                singleLine = true,
                                onValueChange = {
                                    if (it.length <= max) {
                                        three.value = it
                                        focusRequesterFour.requestFocus()
                                    }
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(60.dp)
                                    .focusRequester(focusRequesterThree),
                                maxLines = 1,
                                textStyle = LocalTextStyle.current.copy(
                                    textAlign = TextAlign.Center
                                )
                            )
                            //-------------------------------------------------------
                            OutlinedTextField(
                                value = four.value,
                                singleLine = true,
                                onValueChange = {
                                    if (it.length <= max) {
                                        four.value = it
                                        if (one.value.isNotEmpty() &&
                                            two.value.isNotEmpty() &&
                                            three.value.isNotEmpty() &&
                                            four.value.isNotEmpty()
                                        ) {
                                            viewModel.verifyNumberCode(
                                                one.value,
                                                activity = activity
                                            )
                                        }
                                    }
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(60.dp)
                                    .focusRequester(focusRequesterFour),
                                maxLines = 1,
                                textStyle = LocalTextStyle.current.copy(
                                    textAlign = TextAlign.Center
                                )
                            )
                        }
                        /*
                        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                            (0..4).forEach {

                                TextField(
                                    singleLine = true,
                                    modifier = Modifier
                                        .size(100.dp, 100.dp)
                                        .padding(16.dp)
                                        .background(
                                            color = MaterialTheme.colors.surface,
                                            shape = MaterialTheme.shapes.medium
                                        ),
                                    value = it.toString(),
                                    onValueChange = {},
                                    keyboardOptions = KeyboardOptions(
                                        autoCorrect = false,
                                        keyboardType = KeyboardType.Number,
                                        imeAction = ImeAction.Next
                                    ),
                                    textStyle = TextStyle(
                                        color = MaterialTheme.colors.secondaryVariant,
                                        fontSize = 16.sp,
                                        textAlign = TextAlign.Center
                                    ),

                                    )


                            }
                        }
                         */
                        Spacer(modifier = Modifier.padding(16.dp))

                        Button(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            onClick = {
                                viewModel.verifyNumberCode(
                                    "+201112331246",
                                    activity = activity
                                )
                            })
                        {
                            Text(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .background(
                                        shape = MaterialTheme.shapes.large,
                                        color = MaterialTheme.colors.primary
                                    ),
                                text = "Continue",
                                style = TextStyle(
                                    fontSize = 16.sp
                                )
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
            LoadingDialog()
        }
        is AuthenticationState.Success -> {

        }
    }


}

@Composable
fun CommonOtpTextField(otp: MutableState<String>, focusRequester: FocusRequester) {
    val max = 1
    OutlinedTextField(
        value = otp.value,
        singleLine = true,
        onValueChange = {
            if (it.length <= max) {
                otp.value = it
                focusRequester.requestFocus()
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .width(60.dp)
            .height(60.dp)
            .focusRequester(focusRequester),
        maxLines = 1,
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Center
        )
    )
}