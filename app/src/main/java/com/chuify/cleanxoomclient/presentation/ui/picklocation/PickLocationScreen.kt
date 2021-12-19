package com.chuify.cleanxoomclient.presentation.ui.picklocation

import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import com.chuify.cleanxoomclient.R
import com.chuify.cleanxoomclient.presentation.LocationActivity
import com.chuify.cleanxoomclient.presentation.components.DefaultSnackBar
import com.chuify.cleanxoomclient.presentation.components.LoadingDialog
import com.chuify.cleanxoomclient.presentation.components.SecondaryBar
import com.chuify.cleanxoomclient.presentation.ui.checkout.CheckoutIntent
import com.chuify.cleanxoomclient.presentation.ui.checkout.CheckoutViewModel
import com.chuify.cleanxoomclient.presentation.ui.locations.LocationItem
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun PickLocationScreen(
    navHostController: NavHostController,
    viewModel: CheckoutViewModel,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
) {

    val scaffoldState = rememberScaffoldState()

    val coroutineScope = rememberCoroutineScope()

    val showDialog = viewModel.show.collectAsState()

    val loading = viewModel.loading.collectAsState()

    val context = LocalContext.current.applicationContext

    // If `lifecycleOwner` changes, dispose and reset the effect
    DisposableEffect(lifecycleOwner) {
        // Create an observer that triggers our remembered callbacks
        // for sending analytics events
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadLocationsAgain()
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
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
            val locations = viewModel.location.collectAsState().value

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {

                item {

                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = stringResource(id = R.string.delivery_address),
                        style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold)
                    )
                }

                item {

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        onClick = {
                            viewModel.show.value = true
                        }) {
                        Text(
                            text = stringResource(id = R.string.save_location),
                        )
                    }
                }

                item {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        onClick = {
                            val intent = Intent(context, LocationActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            context.startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface)
                    ) {

                        Icon(
                            modifier = Modifier.padding(8.dp),
                            imageVector = Icons.Filled.MyLocation,
                            contentDescription = null
                        )
                        Text(
                            text = stringResource(id = R.string.location_map),
                            color = MaterialTheme.colors.primary
                        )
                    }
                }

                items(locations) {
                    LocationItem(it,
                        Modifier.clickable {
                            coroutineScope.launch {
                                it.id?.let { id ->
                                    viewModel.userIntent.send(CheckoutIntent.OnLocationSelect(id))
                                    navHostController.popBackStack()
                                }
                            }
                        }
                    ) { location ->
                        location.id?.let {
                            viewModel.delete(location.id)
                        }
                    }
                }
            }

            if (showDialog.value) {
                Dialog(
                    onDismissRequest = {
                        viewModel.show.value = false
                    },
                    DialogProperties(
                        dismissOnBackPress = true,
                        dismissOnClickOutside = true
                    )
                ) {
                    Card(modifier = Modifier, elevation = 20.dp) {
                        Column(modifier = Modifier.padding(8.dp)) {

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    modifier = Modifier.padding(8.dp),
                                    imageVector = Icons.Filled.LocationCity,
                                    contentDescription = null,
                                    tint = Color.Green
                                )
                                Text(
                                    text = stringResource(id = R.string.save_location),
                                    style = TextStyle(
                                        fontSize = 25.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.padding(4.dp),
                                )
                            }

                            val title = remember { mutableStateOf(String()) }
                            val details = remember { mutableStateOf(String()) }
                            val instructions = remember { mutableStateOf(String()) }

                            Column(
                                modifier = Modifier.wrapContentSize()
                            ) {

                                TextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                        .background(
                                            color = MaterialTheme.colors.surface,
                                            shape = MaterialTheme.shapes.medium
                                        ),
                                    value = title.value,
                                    onValueChange = {
                                        title.value = it
                                    },
                                    label = {
                                        Text(text = "Title")
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        autoCorrect = false,
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Next
                                    ),
                                    textStyle = TextStyle(
                                        color = MaterialTheme.colors.secondaryVariant,
                                        fontSize = 16.sp
                                    ),
                                    singleLine = true

                                )

                                TextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                        .background(
                                            color = MaterialTheme.colors.surface,
                                        ),
                                    value = details.value,
                                    onValueChange = {
                                        details.value = it
                                    },
                                    label = {
                                        Text(text = "Details")
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        autoCorrect = false,
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Next
                                    ),
                                    textStyle = TextStyle(
                                        color = MaterialTheme.colors.secondaryVariant,
                                        fontSize = 16.sp
                                    ),
                                    singleLine = true
                                )

                                TextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                        .background(
                                            color = MaterialTheme.colors.surface,
                                        ),
                                    value = instructions.value,
                                    onValueChange = {
                                        instructions.value = it
                                    },
                                    label = {
                                        Text(text = "Instructions")
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        autoCorrect = false,
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Next
                                    ),
                                    textStyle = TextStyle(
                                        color = MaterialTheme.colors.secondaryVariant,
                                        fontSize = 16.sp
                                    ),
                                    singleLine = true
                                )

                                Button(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth(),
                                    onClick = {
                                        coroutineScope.launch {
                                            viewModel.userIntent.send(
                                                CheckoutIntent.SaveAddress(
                                                    title = title.value,
                                                    details = details.value,
                                                    instructions = instructions.value
                                                )
                                            )
                                        }
                                    })
                                {
                                    Text(
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .background(
                                                shape = MaterialTheme.shapes.large,
                                                color = MaterialTheme.colors.primary
                                            ),
                                        text = "Save address",
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

            if (loading.value) {
                LoadingDialog()
            }
        }
    }


}

