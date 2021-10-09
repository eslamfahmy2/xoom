package com.chuify.xoomclient.presentation.ui.accessory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.chuify.xoomclient.presentation.components.AppBar
import com.chuify.xoomclient.presentation.components.DefaultSnackBar
import com.chuify.xoomclient.presentation.components.LoadingListScreen
import com.chuify.xoomclient.presentation.theme.XoomGasClientTheme
import com.chuify.xoomclient.presentation.ui.BaseApplication
import com.chuify.xoomclient.presentation.ui.accessory.component.AccessoryData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class AccessoryFragment : Fragment() {

    private val viewModel: AccessoryViewModel by viewModels()

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

                    val scaffoldState = rememberScaffoldState()

                    Scaffold(
                        topBar = {
                            AppBar(
                                title = "Vendors",
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

                        when (state) {
                            is AccessoryState.Error -> {
                                (state as AccessoryState.Error).message?.let {
                                    coroutineScope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar(
                                            message = it,
                                            actionLabel = "Dismiss",
                                        )
                                    }
                                }
                            }
                            AccessoryState.Loading -> {
                                LoadingListScreen(
                                    count = 3,
                                    height = 250.dp
                                )
                            }
                            is AccessoryState.Success -> {
                                AccessoryData(
                                    data = (state as AccessoryState.Success).data,
                                    searchText = (state as AccessoryState.Success).searchText
                                ) {
                                    coroutineScope.launch {
                                        viewModel.userIntent.send(AccessoryIntent.Filter(it))
                                    }
                                }
                            }
                        }
                    }
                }
            }
            lifecycleScope.launch {
                viewModel.userIntent.send(AccessoryIntent.LoadVendors)
            }
        }
    }
}



