package com.chuify.xoomclient.presentation.ui.cart

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chuify.xoomclient.R
import com.chuify.xoomclient.presentation.components.CartPreview
import com.chuify.xoomclient.presentation.components.DefaultSnackBar
import com.chuify.xoomclient.presentation.components.LoadingListScreen
import com.chuify.xoomclient.presentation.components.SecondaryBar
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun CartScreen(
    navHostController: NavHostController,
    viewModel: CartViewModel = hiltViewModel(),
) {

    val scaffoldState = rememberScaffoldState()

    val coroutineScope = rememberCoroutineScope()

    val state by remember {
        viewModel.state
    }

    Scaffold(
        topBar = {
            SecondaryBar() {
                navHostController.popBackStack()
            }
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
        Card(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primary)
                .clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
                .background(MaterialTheme.colors.background),
            elevation = 20.dp
        )
        {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)) {

                Text(
                    modifier = Modifier.padding(8.dp),
                    text = stringResource(id = R.string.shoping_cart),
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                )
                when (state) {
                    is CartState.Error -> {
                        (state as CartState.Error).message?.let {
                            coroutineScope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = it,
                                    actionLabel = "Dismiss",
                                )
                            }
                        }
                    }
                    CartState.Loading -> {
                        LoadingListScreen(
                            count = 3,
                            height = 250.dp
                        )
                    }
                    is CartState.Success -> {

                        Box(modifier = Modifier
                            .fillMaxSize()) {

                            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                                items((state as CartState.Success).orders) {
                                    CartItem(order = it,
                                        increaseCartItem = {
                                            coroutineScope.launch {
                                                viewModel.userIntent.send(CartIntent.IncreaseItem(it))
                                            }
                                        },
                                        decreaseCartItem = {
                                            coroutineScope.launch {
                                                viewModel.userIntent.send(CartIntent.DecreaseItem(it))
                                            }
                                        },
                                        delete = {
                                            coroutineScope.launch {
                                                viewModel.userIntent.send(CartIntent.DeleteItem(it))
                                            }
                                        }
                                    )
                                }
                            }

                            val cartPreview = (state as CartState.Success).cartPreview
                            CartPreview(
                                modifier = Modifier.align(Alignment.BottomCenter),
                                quantity = cartPreview.totalQuantity.toString(),
                                price = cartPreview.totalPrice.toString()) {

                            }


                        }

                    }
                }

            }


        }


    }


}