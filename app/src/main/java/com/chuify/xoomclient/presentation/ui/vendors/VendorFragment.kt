package com.chuify.xoomclient.presentation.ui.vendors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.chuify.xoomclient.R
import com.chuify.xoomclient.presentation.components.AppBar
import com.chuify.xoomclient.presentation.components.DefaultSnackBar
import com.chuify.xoomclient.presentation.components.LoadingListScreen
import com.chuify.xoomclient.presentation.theme.XoomGasClientTheme
import com.chuify.xoomclient.presentation.ui.BaseApplication
import com.chuify.xoomclient.presentation.ui.vendors.component.VendorScreen
import com.chuify.xoomclient.presentation.utils.TRANS
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class VendorFragment : Fragment() {

    private val viewModel: VendorViewModel by viewModels()

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
                    //  VendorScreenUI()
                }
            }
            lifecycleScope.launch {
                viewModel.userIntent.send(VendorIntent.LoadVendors)
            }
        }
    }

    @ExperimentalMaterialApi
    @Composable
    fun VendorScreenUI(navController: NavController, viewModel: VendorViewModel) {

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
                is VendorState.Error -> {
                    (state as VendorState.Error).message?.let {
                        coroutineScope.launch {
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = it,
                                actionLabel = "Dismiss",
                            )
                        }
                    }
                }
                VendorState.Loading -> {
                    LoadingListScreen(
                        count = 3,
                        height = 250.dp
                    )
                }
                is VendorState.Success -> {
                    VendorScreen(
                        data = (state as VendorState.Success).data,
                        onItemClicked = {
                            val bundle = Bundle()
                            bundle.putSerializable(TRANS, it)
                            navController.navigate(
                                R.id.action_vendorFragment_to_productFragment,
                                bundle
                            )
                        },
                        searchText = (state as VendorState.Success).searchText,
                        onTextChange = {
                            coroutineScope.launch {
                                viewModel.userIntent.send(VendorIntent.Filter(it))
                            }
                        }
                    )
                }
            }
        }

    }
}



