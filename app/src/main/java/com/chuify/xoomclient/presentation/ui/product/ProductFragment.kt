package com.chuify.xoomclient.presentation.ui.product

import android.os.Bundle
import android.util.Log
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
import com.chuify.xoomclient.domain.model.Vendor
import com.chuify.xoomclient.presentation.components.AppBar
import com.chuify.xoomclient.presentation.components.DefaultSnackBar
import com.chuify.xoomclient.presentation.components.LoadingListScreen
import com.chuify.xoomclient.presentation.theme.XoomGasClientTheme
import com.chuify.xoomclient.presentation.ui.BaseApplication
import com.chuify.xoomclient.presentation.ui.product.component.ProductScreen
import com.chuify.xoomclient.presentation.ui.signup.TAG
import com.chuify.xoomclient.presentation.utils.TRANS
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class ProductFragment : Fragment() {

    private val viewModel: ProductViewModel by viewModels()

    @Inject
    lateinit var application: BaseApplication


    @ExperimentalMaterialApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        val vendor = arguments?.getSerializable(TRANS) as Vendor

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
                                title = vendor.name,
                                onToggleTheme = {
                                    application.toggleTheme()
                                }
                            )
                        },
                        scaffoldState = scaffoldState,
                        snackbarHost = { scaffoldState.snackbarHostState },
                        bottomBar = {
                            DefaultSnackBar(
                                snackHostState = scaffoldState.snackbarHostState,
                                onDismiss = { scaffoldState.snackbarHostState.currentSnackbarData?.dismiss() },
                            )
                        }
                    ) {

                        when (state) {
                            is ProductState.Error -> {
                                (state as ProductState.Error).message?.let {
                                    coroutineScope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar(
                                            message = it,
                                            actionLabel = "Dismiss",
                                        )
                                    }
                                }
                            }
                            ProductState.Loading -> {
                                LoadingListScreen(
                                    count = 5,
                                    height = 100.dp
                                )
                            }
                            is ProductState.Success -> {

                                ProductScreen(
                                    data = (state as ProductState.Success).data,
                                    onIncrease = {
                                        viewModel.insert(it)
                                    } ,
                                    onDecrease = {
                                        viewModel.decreaseOrRemove(it)
                                    }
                                )

                            }
                        }

                    }
                }

                lifecycleScope.launch {
                  viewModel.userIntent.send(ProductIntent.InitLoad)
                }
            }
        }
    }
}



