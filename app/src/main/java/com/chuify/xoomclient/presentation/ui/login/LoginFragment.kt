package com.chuify.xoomclient.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.chuify.xoomclient.presentation.MainActivity
import com.chuify.xoomclient.presentation.components.HomeBar
import com.chuify.xoomclient.presentation.components.DefaultSnackBar
import com.chuify.xoomclient.presentation.theme.XoomGasClientTheme
import com.chuify.xoomclient.presentation.application.BaseApplication
import com.chuify.xoomclient.presentation.ui.login.component.LoginScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalPagerApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var application: BaseApplication


    @ExperimentalMaterialApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                XoomGasClientTheme(
                    darkTheme = application.isDark()
                ) {

                    val coroutineScope = rememberCoroutineScope()

                    val state by remember {
                        viewModel.state
                    }

                    val phone by remember {
                        viewModel.phone
                    }

                    val scaffoldState = rememberScaffoldState()

                    Scaffold(
                        topBar = {
                            HomeBar(
                                title = "",
                                action = {
                                    application.toggleTheme()
                                },
                                cartCount = 1
                            )
                        },
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

                        LoginScreen(
                            phone = phone,
                            onLogin = {
                                coroutineScope.launch {
                                    viewModel.userIntent.send(LoginIntent.SignIn)
                                }
                            },
                            onValueChanged = {
                                coroutineScope.launch {
                                    viewModel.userIntent.send(LoginIntent.PhoneChange(it))
                                }
                            }
                        )

                        when (state) {
                            is LoginState.Error -> {
                                (state as LoginState.Error).message?.let {
                                    coroutineScope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar(
                                            message = it,
                                            actionLabel = "Dismiss",
                                        )
                                    }
                                }
                            }
                            LoginState.Idl -> {

                            }
                            LoginState.Loading -> {

                                Dialog(
                                    onDismissRequest = { },
                                    DialogProperties(dismissOnBackPress = false,
                                        dismissOnClickOutside = false)
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier
                                            .size(100.dp)
                                            .background(
                                                color = Color.White,
                                                shape = RoundedCornerShape(8.dp))
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }
                            }
                            is LoginState.Success -> {
                                startActivity(Intent(activity, MainActivity::class.java))
                            }
                        }


                    }

                }
            }
        }
    }
}



