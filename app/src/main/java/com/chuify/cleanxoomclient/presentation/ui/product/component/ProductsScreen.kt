package com.chuify.cleanxoomclient.presentation.ui.product.component

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chuify.cleanxoomclient.presentation.components.DefaultSnackBar
import com.chuify.cleanxoomclient.presentation.components.LoadingListScreen
import com.chuify.cleanxoomclient.presentation.ui.product.ProductIntent
import com.chuify.cleanxoomclient.presentation.ui.product.ProductState
import com.chuify.cleanxoomclient.presentation.ui.product.ProductViewModel
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun ProductsScreen(
    id: String,
    viewModel: ProductViewModel = hiltViewModel()
) {

    val coroutineScope = rememberCoroutineScope()

    val state = viewModel.state.collectAsState().value

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { scaffoldState.snackbarHostState },
        bottomBar = {
            DefaultSnackBar(
                snackHostState = scaffoldState.snackbarHostState,
                onDismiss = { scaffoldState.snackbarHostState.currentSnackbarData?.dismiss() },
            )
        },
    ) {

        when (state) {
            is ProductState.Error -> {
                state.message?.let {
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

                ProductDataScreen(
                    data = state.data,
                    onIncrease = {
                        coroutineScope.launch {
                            viewModel.userIntent.send(ProductIntent.Insert(it))
                        }
                    },
                    onDecrease = {
                        coroutineScope.launch {
                            viewModel.userIntent.send(ProductIntent.DecreaseOrRemove(it))
                        }
                    }
                )

            }
        }

    }

    LaunchedEffect(true) {
        viewModel.userIntent.send(ProductIntent.InitLoad(id))
    }


}