package com.chuify.xoomclient.presentation.ui.picklocation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.chuify.xoomclient.presentation.components.DefaultSnackBar
import com.chuify.xoomclient.presentation.components.LoadingListScreen

import com.chuify.xoomclient.presentation.components.SecondaryBar
import com.chuify.xoomclient.presentation.ui.checkout.CheckoutIntent
import com.chuify.xoomclient.presentation.ui.checkout.CheckoutViewModel
import com.chuify.xoomclient.presentation.ui.vendors.VendorState
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun PickLocationScreen(
    navHostController: NavHostController,
    checkoutViewModel: CheckoutViewModel = hiltViewModel(),
    selectLocationViewModel: PickLocationViewModel = hiltViewModel(),
) {

    val scaffoldState = rememberScaffoldState()

    val coroutineScope = rememberCoroutineScope()

    val state by remember {
        selectLocationViewModel.state
    }


    Scaffold(
        topBar = {
            SecondaryBar {
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

            when (state) {
                is PickLocationState.Error -> {
                    (state as VendorState.Error).message?.let {
                        coroutineScope.launch {
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = it,
                                actionLabel = "Dismiss",
                            )
                        }
                    }
                }
                PickLocationState.Loading -> {
                    LoadingListScreen(
                        count = 3,
                        height = 250.dp
                    )
                }
                is PickLocationState.Success -> {

                    val locations = (state as PickLocationState.Success).locations
                    LazyColumn(modifier = Modifier.fillMaxSize()) {

                        item {

                            Text(
                                modifier = Modifier.padding(16.dp),
                                text = stringResource(id = R.string.delivery_address),
                                style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold)
                            )
                        }

                        items(locations) {
                            LocationItemPicker(it) {
                                coroutineScope.launch {
                                    checkoutViewModel.userIntent.send(CheckoutIntent.OnLocationSelect(
                                        it))
                                    navHostController.popBackStack()
                                }
                            }
                        }


                    }

                }
            }


        }


    }

}

