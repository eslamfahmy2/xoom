package com.chuify.xoomclient.presentation.ui.accessoryDetails.component

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddShoppingCart
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
import androidx.hilt.navigation.compose.hiltViewModel
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


@Composable
fun AccessoryPopUp(
    accessory: Accessory,
    increaseCartItem: (Accessory) -> Unit,
    decreaseCartItem: (Accessory) -> Unit,
    dismiss: () -> Unit,
) {


    Card(
        elevation = 8.dp,
        modifier = Modifier.animateContentSize()
    ) {
        Column {

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
                modifier = Modifier
                    .background(MaterialTheme.colors.primary)
                    .clip(RoundedCornerShape(bottomEnd = 12.dp, bottomStart = 12.dp))
                    .background(MaterialTheme.colors.background)
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

                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Button(
                                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
                                modifier = Modifier
                                    .border(
                                        width = 1.dp,
                                        shape = RoundedCornerShape(5.dp),
                                        color = Color.Gray,
                                    ),
                                onClick = {
                                    decreaseCartItem(accessory)
                                },

                                ) {
                                Text(text = "-")
                            }

                            Text(
                                modifier = Modifier.padding(4.dp),
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
                    color = MaterialTheme.colors.onSurface,
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


        }


    }
}