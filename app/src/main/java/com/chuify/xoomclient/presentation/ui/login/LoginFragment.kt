package com.chuify.xoomclient.presentation.ui.login

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
import androidx.navigation.fragment.findNavController
import com.chuify.xoomclient.presentation.components.AppBar
import com.chuify.xoomclient.presentation.theme.XoomGasClientTheme
import com.chuify.xoomclient.presentation.ui.BaseApplication
import com.chuify.xoomclient.presentation.ui.login.component.LoginScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


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


                    Scaffold(
                        topBar = {
                            AppBar(
                                title = "Signing",
                                onToggleTheme = {
                                    application.toggleTheme()
                                }
                            )
                        },
                        bottomBar = {
                            if (state is LoginState.Error) {
                                val show = mutableStateOf(true)
                                if (show.value) {
                                    Snackbar(
                                        modifier = Modifier.fillMaxWidth(),
                                        action = {
                                            show.value = false
                                        }
                                    ) {
                                        Text(text = (state as LoginState.Error).message.toString())
                                    }
                                }
                            }
                        }
                    ) {

                        LoginScreen(
                            phone = phone,
                            coroutineScope = coroutineScope,
                            userIntent = viewModel.userIntent,
                            navController = findNavController()
                        )

                        if (state is LoginState.Loading) {
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



