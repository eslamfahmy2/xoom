package com.chuify.xoomclient.presentation.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.chuify.xoomclient.presentation.components.AppBar
import com.chuify.xoomclient.presentation.theme.XoomGasClientTheme
import com.chuify.xoomclient.presentation.ui.BaseApplication
import com.chuify.xoomclient.presentation.ui.signup.component.SignUpIdleScreen
import dagger.hilt.android.AndroidEntryPoint
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
                        bottomBar = {
                            if (state is SignUpState.Error) {
                                val show = mutableStateOf(true)
                                if (show.value) {
                                    Snackbar(
                                        modifier = Modifier.fillMaxWidth(),
                                        action = {
                                            show.value = false
                                        }
                                    ) {
                                        Text(text = (state as SignUpState.Error).message.toString())
                                    }
                                }
                            }
                        }
                    ) {

                        SignUpIdleScreen(
                            firstName = firstName,
                            lastName = lastName,
                            email = email,
                            coroutineScope = coroutineScope,
                            userIntent = viewModel.userIntent
                        )

                        if (state is SignUpState.Loading) {
                            Box(Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.wrapContentSize()
                                )
                            }

                        }


                    }

                }
            }
        }
    }
}



