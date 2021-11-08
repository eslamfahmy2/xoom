package com.chuify.xoomclient.presentation.ui.product.component

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chuify.xoomclient.presentation.components.DefaultSnackBar
import com.chuify.xoomclient.presentation.components.LoadingListScreen
import com.chuify.xoomclient.presentation.ui.product.ProductIntent
import com.chuify.xoomclient.presentation.ui.product.ProductState
import com.chuify.xoomclient.presentation.ui.product.ProductViewModel
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun ProductsScreen(
    id: String,
    viewModel: ProductViewModel = hiltViewModel()
) {

    val coroutineScope = rememberCoroutineScope()

    val state by remember {
        viewModel.state
    }

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

                ProductDataScreen(
                    data = (state as ProductState.Success).data,
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