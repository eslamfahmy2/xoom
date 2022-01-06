package com.chuify.cleanxoomclient.presentation.ui.authentication.component

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.widget.Toast
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
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
import com.chuify.cleanxoomclient.presentation.navigation.Screens
import com.chuify.cleanxoomclient.presentation.ui.authentication.LoginViewModel
import com.chuify.cleanxoomclient.presentation.ui.authentication.OTPState
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

    val resend = remember {
        mutableStateOf(false)
    }

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

    val focusRequesterFive = FocusRequester()
    val five = remember {
        mutableStateOf("")
    }

    val focusRequesterSix = FocusRequester()
    val six = remember {
        mutableStateOf("")
    }
    val max = 1



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
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .size(50.dp)
                                    .focusRequester(focusRequesterOne),
                                maxLines = 1,
                                textStyle = LocalTextStyle.current.copy(
                                    textAlign = TextAlign.Center
                                )
                            )
                            //------------------------------------------------------
                            Spacer(modifier = Modifier.padding(2.dp))
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
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .size(50.dp)
                                    .focusRequester(focusRequesterTwo),
                                maxLines = 1,
                                textStyle = LocalTextStyle.current.copy(
                                    textAlign = TextAlign.Center
                                )
                            )
                            //-----------------------------------------------------
                            Spacer(modifier = Modifier.padding(2.dp))
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
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .size(50.dp)
                                    .focusRequester(focusRequesterThree),
                                maxLines = 1,
                                textStyle = LocalTextStyle.current.copy(
                                    textAlign = TextAlign.Center
                                )
                            )
                            //-------------------------------------------------------
                            Spacer(modifier = Modifier.padding(2.dp))
                            OutlinedTextField(
                                value = four.value,
                                singleLine = true,
                                onValueChange = {
                                    if (it.length <= max) {
                                        four.value = it
                                        focusRequesterFive.requestFocus()
                                    }
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .size(50.dp)
                                    .focusRequester(focusRequesterFour),
                                maxLines = 1,
                                textStyle = LocalTextStyle.current.copy(
                                    textAlign = TextAlign.Center
                                )
                            )
                            //-------------------------------------------------------
                            Spacer(modifier = Modifier.padding(2.dp))
                            OutlinedTextField(
                                value = five.value,
                                singleLine = true,
                                onValueChange = {
                                    if (it.length <= max) {
                                        five.value = it
                                        focusRequesterSix.requestFocus()
                                    }
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .size(50.dp)
                                    .focusRequester(focusRequesterFive),
                                maxLines = 1,
                                textStyle = LocalTextStyle.current.copy(
                                    textAlign = TextAlign.Center
                                )
                            )
                            //-------------------------------------------------------
                            Spacer(modifier = Modifier.padding(2.dp))
                            OutlinedTextField(
                                value = six.value,
                                singleLine = true,
                                onValueChange = {
                                    if (it.length <= max) {
                                        six.value = it
                                        if (one.value.isNotEmpty() &&
                                            two.value.isNotEmpty() &&
                                            three.value.isNotEmpty() &&
                                            four.value.isNotEmpty() &&
                                            five.value.isNotEmpty() &&
                                            six.value.isNotEmpty()
                                        ) {
                                            viewModel.verifyCode(
                                                one.value + two.value + three.value + four.value
                                                        + five.value + six.value,
                                                activity = activity
                                            )
                                        }
                                    }
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .size(50.dp)
                                    .focusRequester(focusRequesterSix),
                                maxLines = 1,
                                textStyle = LocalTextStyle.current.copy(
                                    textAlign = TextAlign.Center
                                )
                            )
                        }

                        Spacer(modifier = Modifier.padding(16.dp))
                        val context = LocalContext.current

                        Button(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            onClick = {
                                if (one.value.isNotEmpty() &&
                                    two.value.isNotEmpty() &&
                                    three.value.isNotEmpty() &&
                                    four.value.isNotEmpty() &&
                                    five.value.isNotEmpty() &&
                                    six.value.isNotEmpty()
                                ) {
                                    viewModel.verifyCode(
                                        one.value + two.value + three.value + four.value
                                                + five.value + six.value,
                                        activity = activity
                                    )
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Please check your SMS",
                                        Toast.LENGTH_SHORT
                                    ).show()
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
                                text = "Continue",
                                style = TextStyle(
                                    fontSize = 16.sp
                                )
                            )
                        }

                        Spacer(modifier = Modifier.padding(8.dp))

                        if (resend.value) {

                            val timer = rememberSaveable { mutableStateOf(40 * 1000L) }
                            LaunchedEffect(key1 = Unit, block = {
                                object : CountDownTimer(40 * 1000L, 1000) {
                                    override fun onTick(millisRemaining: Long) {
                                        timer.value = millisRemaining
                                    }

                                    override fun onFinish() {
                                        timer.value = 0L
                                    }
                                }.start()
                            })

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
                                    text = "Did not get the code ?",
                                    color = MaterialTheme.colors.onSurface,
                                    fontSize = 16.sp
                                )

                                Text(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .clickable {
                                            if (timer.value == 0L) {
                                                viewModel.sendSmsCode(
                                                    phone,
                                                    activity = activity
                                                )
                                            }
                                        }
                                        .padding(8.dp),
                                    text = stringResource(
                                        R.string.resend,
                                        "" + timer.value / 1000L
                                    ),
                                    color = if (timer.value == 0L) MaterialTheme.colors.primary else Color.Gray,
                                    fontSize = 16.sp
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

        OTPState.Idl -> {
            resend.value = false
        }
        OTPState.Loading -> {
            LoadingDialog()
        }
        is OTPState.OnCodeSent -> {
            resend.value = true
            Toast.makeText(
                LocalContext.current,
                "Code sent successfully",
                Toast.LENGTH_SHORT
            ).show()
        }
        is OTPState.OnVerificationCompleted -> {
            viewModel.idl()
            navHostController.navigate(Screens.SignUp.route)
        }
        is OTPState.OnVerificationFailed -> {
            one.value = String()
            two.value = String()
            three.value = String()
            four.value = String()
            five.value = String()
            six.value = String()
            resend.value = true
            state.message?.let {
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = it,
                        actionLabel = "Dismiss",
                    )
                }
            }
        }
    }

    LaunchedEffect(true) {
        viewModel.sendSmsCode(
            phone,
            activity = activity
        )
    }

}

