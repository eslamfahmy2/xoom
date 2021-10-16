package com.chuify.xoomclient.presentation.ui.optConfrim

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.navigation.fragment.findNavController
import com.chuify.xoomclient.R
import com.chuify.xoomclient.presentation.components.HomeBar
import com.chuify.xoomclient.presentation.components.DefaultSnackBar
import com.chuify.xoomclient.presentation.theme.XoomGasClientTheme
import com.chuify.xoomclient.presentation.application.BaseApplication
import com.chuify.xoomclient.presentation.ui.optConfrim.component.OTPScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalAnimationApi
@AndroidEntryPoint
class OTPFragment : Fragment() {

    private val viewModel: OTPViewModel by viewModels()

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
                                title = "Sign up",
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

                        OTPScreen(
                            phone = phone,
                            coroutineScope = coroutineScope,
                            userIntent = viewModel.userIntent,
                            navController = findNavController()
                        )
                        when (state) {
                            is OTPState.Error -> {
                                (state as OTPState.Error).message?.let {
                                    coroutineScope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar(
                                            message = it,
                                            actionLabel = "Dismiss",
                                        )
                                    }
                                }
                            }
                            OTPState.Idl -> {

                            }
                            OTPState.Loading -> {
                                Box(Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.wrapContentSize()
                                    )
                                }
                            }
                            is OTPState.Success -> {

                                findNavController().navigate(
                                    R.id.action_OTPFragment_to_signUpFragment,
                                    Bundle()
                                )

                                coroutineScope.launch {
                                    viewModel.userIntent.send(OTPIntent.Idl)
                                }

                            }
                        }


                    }

                }
            }
        }
    }
}



