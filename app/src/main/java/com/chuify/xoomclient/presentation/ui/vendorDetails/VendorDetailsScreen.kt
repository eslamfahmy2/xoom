package com.chuify.xoomclient.presentation.ui.vendorDetails

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chuify.xoomclient.R
import com.chuify.xoomclient.domain.model.Vendor
import com.chuify.xoomclient.presentation.components.CartPreview
import com.chuify.xoomclient.presentation.components.DefaultSnackBar
import com.chuify.xoomclient.presentation.components.SecondaryBar
import com.chuify.xoomclient.presentation.ui.accessory.component.AccessoryScreen
import com.chuify.xoomclient.presentation.ui.product.component.ProductsScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

enum class VendorDetails {
    GAS,
    ACCESSORIES
}

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun VendorDetailsScreen(
    vendor: Vendor,
    navHostController: NavHostController,
    viewModel: VendorDetailsViewModel = hiltViewModel(),
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
            Column(modifier = Modifier.padding(8.dp)) {

                Text(
                    modifier = Modifier.padding(8.dp),
                    text = vendor.name,
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                )

                val pagerState = rememberPagerState()

                Row(modifier = Modifier.padding(8.dp)) {

                    val selected = remember {
                        mutableStateOf(VendorDetails.GAS)
                    }

                    when (selected.value) {
                        VendorDetails.GAS -> {

                            Button(
                                onClick = {

                                }) {
                                Text(text = stringResource(R.string.gas))
                            }
                            Spacer(modifier = Modifier.padding(8.dp))

                            Button(
                                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
                                modifier = Modifier
                                    .border(
                                        width = 1.dp,
                                        shape = RoundedCornerShape(5.dp),
                                        color = Color.Gray,
                                    ),
                                onClick = {
                                    coroutineScope.launch {
                                        selected.value = VendorDetails.ACCESSORIES
                                        pagerState.animateScrollToPage(1)
                                    }
                                },

                                ) {
                                Text(text = stringResource(R.string.accessories))

                            }

                        }
                        VendorDetails.ACCESSORIES -> {

                            Button(
                                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
                                modifier = Modifier
                                    .border(
                                        width = 1.dp,
                                        shape = RoundedCornerShape(5.dp),
                                        color = Color.Gray,
                                    ),
                                onClick = {
                                    coroutineScope.launch {
                                        selected.value = VendorDetails.GAS
                                        pagerState.animateScrollToPage(0)
                                    }
                                },

                                ) {
                                Text(text = stringResource(R.string.gas))

                            }


                            Spacer(modifier = Modifier.padding(8.dp))

                            Button(
                                onClick = {

                                }) {
                                Text(text = stringResource(R.string.accessories))
                            }


                        }
                    }

                }

                Box {
                    HorizontalPager(
                        state = pagerState,
                        count = 2) {
                        if (it == 0) {
                            ProductsScreen(viewModel = hiltViewModel(), id = vendor.id)
                        } else {
                            AccessoryScreen(viewModel = hiltViewModel())
                        }
                    }
                    when (state) {
                        is VendorDetailsState.Error -> {
                        }
                        VendorDetailsState.Loading -> {
                        }
                        is VendorDetailsState.Success -> {

                            val data = (state as VendorDetailsState.Success).data
                            CartPreview(
                                modifier = Modifier.align(Alignment.BottomCenter),
                                quantity = data.totalQuantity.toString(),
                                price = data.totalPrice.toString()) {

                            }

                        }
                    }
                }

            }


        }


    }


}