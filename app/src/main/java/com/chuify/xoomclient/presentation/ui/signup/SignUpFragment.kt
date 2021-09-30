package com.chuify.xoomclient.presentation.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.chuify.xoomclient.presentation.components.AppBar
import com.chuify.xoomclient.presentation.components.DefaultSnackBar
import com.chuify.xoomclient.presentation.theme.XoomGasClientTheme
import com.chuify.xoomclient.presentation.ui.BaseApplication
import com.chuify.xoomclient.presentation.ui.signup.component.SignupScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private val viewModel: SignUpViewModel by viewModels()

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

                    val scaffoldState = rememberScaffoldState()

                    val state by remember {
                        viewModel.state
                    }

                    val firstName by remember {
                        viewModel.firstName
                    }

                    val lastName by remember {
                        viewModel.lastName
                    }

                    val email by remember {
                        viewModel.email
                    }

                    Scaffold(
                        topBar = {
                            AppBar(
                                title = "Signup",
                                onToggleTheme = {
                                    application.toggleTheme()
                                }
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

                        SignupScreen(
                            firstName = firstName,
                            lastName = lastName,
                            email = email,
                            coroutineScope = coroutineScope,
                            userIntent = viewModel.userIntent,
                            navController = findNavController()
                        )

                        when (state) {
                            is SignUpState.Error -> {
                                (state as SignUpState.Error).message?.let {
                                    coroutineScope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar(
                                            message = it,
                                            actionLabel = "Dismiss",
                                        )
                                        viewModel.userIntent.send(SignUpIntent.Idl)
                                    }
                                }
                            }
                            SignUpState.Idl -> {

                            }
                            SignUpState.Loading -> {
                                Box(Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.wrapContentSize()
                                    )
                                }
                            }
                            is SignUpState.Success -> {

                            }
                        }


                    }

                }
            }
        }
    }
}



