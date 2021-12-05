package com.chuify.cleanxoomclient.presentation.ui.vendorDetails

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import com.chuify.cleanxoomclient.R
import com.chuify.cleanxoomclient.domain.model.Vendor
import com.chuify.cleanxoomclient.presentation.components.CartPreview
import com.chuify.cleanxoomclient.presentation.components.SecondaryBar
import com.chuify.cleanxoomclient.presentation.navigation.Screens
import com.chuify.cleanxoomclient.presentation.ui.accessory.component.AccessoryScreen
import com.chuify.cleanxoomclient.presentation.ui.product.component.ProductsScreen
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

    val preview = viewModel.preview.collectAsState().value


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

            CartPreview(
                quantity = preview.totalQuantity.toString(),
                price = preview.totalPrice.toString()
            ) {
                navHostController.navigate(Screens.Cart.fullRoute())
            }
        }
    ) { it ->
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = it.calculateBottomPadding())
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
                        count = 2
                    ) {
                        if (it == 0) {
                            ProductsScreen(id = vendor.id)
                        } else {
                            AccessoryScreen(viewModel = hiltViewModel())
                        }
                    }
                }

            }


        }


    }

    LaunchedEffect(true) {
        viewModel.userIntent.send(VendorDetailsIntent.LoadVendorDetails)
    }


}