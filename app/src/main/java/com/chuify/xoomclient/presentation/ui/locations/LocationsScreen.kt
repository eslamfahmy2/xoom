package com.chuify.xoomclient.presentation.ui.locations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chuify.xoomclient.R
import com.chuify.xoomclient.domain.model.Location
import com.chuify.xoomclient.presentation.components.DefaultSnackBar
import com.chuify.xoomclient.presentation.components.LoadingDialog
import com.chuify.xoomclient.presentation.components.LoadingListScreen
import com.chuify.xoomclient.presentation.components.SecondaryBar
import com.chuify.xoomclient.presentation.ui.checkout.CheckoutIntent
import com.chuify.xoomclient.presentation.ui.checkout.CheckoutViewModel
import com.chuify.xoomclient.presentation.ui.editProfile.EditProfileIntent
import com.chuify.xoomclient.presentation.ui.editProfile.EditProfileState
import com.chuify.xoomclient.presentation.ui.picklocation.LocationItemPicker
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun LocationsScreen(
    navHostController: NavHostController,
    viewModel: LocationsViewModel = hiltViewModel(),
) {

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val state = viewModel.state.collectAsState().value

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

            Column(modifier = Modifier.padding(8.dp)) {

                Text(
                    modifier = Modifier.padding(8.dp),
                    text = stringResource(id = R.string.delivery_address),
                    style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold)
                )


                when (state) {
                    is LocationsState.Error -> {
                        state.message?.let {
                            coroutineScope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = it,
                                    actionLabel = "Dismiss",
                                )
                            }
                        }
                    }
                    LocationsState.Loading -> {
                        LoadingListScreen(
                            count = 3,
                            height = 250.dp
                        )
                    }
                    is LocationsState.Success -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                        ) {

                            items(state.locations) {
                                LocationItem(it)
                            }

                            item {

                                Button(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth(),
                                    onClick = {

                                    })
                                {
                                    Text(
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .background(
                                                shape = MaterialTheme.shapes.large,
                                                color = MaterialTheme.colors.primary
                                            ),
                                        text = "Add address",
                                        style = TextStyle(
                                            fontSize = 16.sp
                                        )
                                    )
                                }
                            }


                        }
                    }
                }

            }

        }


    }
    LaunchedEffect(true) {
        viewModel.userIntent.send(LocationsIntent.LoadLocations)
    }

}


@Composable
fun LocationItem(location: Location) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(10)
            ),
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clip(RoundedCornerShape(10)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {

            Row {
                Icon(
                    modifier = Modifier.padding(16.dp),
                    painter = painterResource(id = R.drawable.ic_address),
                    contentDescription = null,
                    tint = Color.Green
                )

                Column {
                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = location.title.toString(),
                        color = MaterialTheme.colors.onSurface

                    )

                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = location.details.toString(),
                        color = MaterialTheme.colors.secondaryVariant
                    )

                }

            }

            Icon(
                modifier = Modifier.padding(16.dp),
                painter = painterResource(id = R.drawable.ic_location),
                contentDescription = null,
                tint = Color.Green
            )


        }

    }


}



