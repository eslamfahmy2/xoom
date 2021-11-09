package com.chuify.xoomclient.presentation.ui.accessoryDetails.component

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberImagePainter
import com.chuify.xoomclient.R
import com.chuify.xoomclient.domain.model.Accessory
import com.chuify.xoomclient.presentation.ui.accessoryDetails.AccessoryDetailsIntent
import com.chuify.xoomclient.presentation.ui.accessoryDetails.AccessoryDetailsViewModel
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun AccessoryPref(
    accessory: Accessory,
    viewModel: AccessoryDetailsViewModel
) {

    val coroutineScope = rememberCoroutineScope()

    Dialog(onDismissRequest = {
        coroutineScope.launch {
            viewModel.userIntent.send(AccessoryDetailsIntent.DismissAcDetails)
        }
    }) {
        AccessoryPopUp(
            accessory = accessory,
            increaseCartItem = {
                coroutineScope.launch {
                    viewModel.userIntent.send(AccessoryDetailsIntent.IncreaseAccessoryCart(it))
                }
            },
            decreaseCartItem = {
                coroutineScope.launch {
                    viewModel.userIntent.send(AccessoryDetailsIntent.DecreaseAccessoryCart(it))
                }
            },
            dismiss = {
                coroutineScope.launch {
                    viewModel.userIntent.send(AccessoryDetailsIntent.DismissAcDetails)
                }
            },
        )

    }


}


@ExperimentalAnimationApi
@Composable
fun AccessoryPopUp(
    accessory: Accessory,
    increaseCartItem: (Accessory) -> Unit,
    decreaseCartItem: (Accessory) -> Unit,
    dismiss: () -> Unit,
) {

   // val visible = remember { mutableStateOf(false) }

    Card(
        elevation = 8.dp,
        modifier = Modifier.animateContentSize(),
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(modifier = Modifier.animateContentSize()) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            dismiss()
                        },
                    imageVector = Icons.Filled.Close,
                    contentDescription = null,
                )
                Text(
                    text = accessory.name.uppercase(),
                    fontSize = 18.sp,
                )

            }

            Column(
                modifier = Modifier.clip(RoundedCornerShape(bottomEnd = 12.dp, bottomStart = 12.dp))
            ) {


                Card(elevation = 4.dp, modifier = Modifier.padding(4.dp)) {
                    Image(
                        painter = rememberImagePainter(
                            data = accessory.image,
                            builder = {
                                crossfade(true)
                            },
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(8.dp),
                        text = accessory.name,
                        color = MaterialTheme.colors.onSurface,
                    )

                    if (accessory.quantity > 0) {
                     //   visible.value = true
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            OutlinedButton(
                                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
                                onClick = {
                                    decreaseCartItem(accessory)
                                },
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = Color.Gray,
                                )

                            ) {
                                Text(text = "-")
                            }

                            Text(
                                modifier = Modifier.padding(6.dp),
                                text = accessory.quantity.toString()
                            )

                            Button(
                                onClick = {
                                    increaseCartItem(accessory)
                                }) {
                                Text(text = "+")
                            }
                        }
                    } else {
                       // visible.value = false
                        Box(modifier = Modifier.padding(8.dp)) {
                            Button(
                                onClick = {
                                    increaseCartItem(accessory)
                                }) {
                                Text(text = "+")
                            }
                        }
                    }


                }
            }
/*
            val density = LocalDensity.current
            AnimatedVisibility(
                visible = visible.value,
                enter = slideInVertically(
                    // Slide in from 40 dp from the top.
                    initialOffsetY = { with(density) { -40.dp.roundToPx() } }
                ) + expandVertically(
                    // Expand from the top.
                    expandFrom = Alignment.Top
                ) + fadeIn(
                    // Fade in with the initial alpha of 0.3f.
                    initialAlpha = 0.3f
                ),
                exit = slideOutVertically() + shrinkVertically() + fadeOut()
            ) {
*/
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.primary)
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(8.dp),
                        text = stringResource(id = R.string.total),
                        color = Color.Black,
                    )

                    Row(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(start = 8.dp, bottom = 8.dp, end = 8.dp)
                    ) {
                        Text(
                            text = accessory.totalPrice.toString(),
                            color = Color.Black,

                            )
                        Spacer(modifier = Modifier.padding(2.dp))
                        Text(
                            text = stringResource(R.string.currency),
                            color = Color.Black,

                            )

                    }


                }

      //      }
        }


    }
}