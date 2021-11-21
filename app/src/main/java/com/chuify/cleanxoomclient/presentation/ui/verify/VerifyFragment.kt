package com.chuify.cleanxoomclient.presentation.ui.verify

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
import com.chuify.cleanxoomclient.presentation.application.BaseApplication
import com.chuify.cleanxoomclient.presentation.components.DefaultSnackBar
import com.chuify.cleanxoomclient.presentation.components.HomeBar
import com.chuify.cleanxoomclient.presentation.theme.XoomGasClientTheme
import com.chuify.cleanxoomclient.presentation.ui.verify.component.VerifyScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalAnimationApi
@AndroidEntryPoint
class VerifyFragment : Fragment() {

    private val viewModel: VerifyViewModel by viewModels()

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
                    darkTheme = false
                ) {

                    val coroutineScope = rememberCoroutineScope()

                    val activity = requireActivity()

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

                                action = {

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

                        VerifyScreen(
                            phone = phone,
                            coroutineScope = coroutineScope,
                            userIntent = viewModel.userIntent,
                            navController = findNavController(),
                            activity = activity
                        )
                        when (state) {
                            is VerifyState.Error -> {
                                (state as VerifyState.Error).message?.let {
                                    coroutineScope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar(
                                            message = it,
                                            actionLabel = "Dismiss",
                                        )
                                    }
                                }
                            }
                            VerifyState.Idl -> {

                            }
                            VerifyState.Loading -> {
                                Box(
                                    Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.wrapContentSize()
                                    )
                                }
                            }
                            is VerifyState.Success -> {


                            }
                        }


                    }

                }
            }
        }
    }
}



