package com.chuify.xoomclient.presentation.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class BikePosition {
    Start, Finish
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun HomeBar(
    action: () -> Unit,
    cartCount: Int,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.primary
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp, end = 16.dp,
                    top = 8.dp, bottom = 8.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            val bikeState = remember { mutableStateOf(BikePosition.Start) }

            val offsetAnimation by animateDpAsState(
                targetValue = if (bikeState.value == BikePosition.Start) 0.dp else 1000.dp,
                animationSpec = infiniteRepeatable(
                    /*
                     Tween Animates between values over specified [durationMillis]
                    */
                    tween(
                        delayMillis = 10000,
                        durationMillis = 10000,
                        easing = FastOutLinearInEasing
                    ),
                    RepeatMode.Reverse
                )
            )


            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {


                Image(
                    painterResource(com.chuify.xoomclient.R.drawable.xoom_gas_rider),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(55.dp, 55.dp)
                        .absoluteOffset(x = offsetAnimation)
                )

            }

            bikeState.value = BikePosition.Finish
            IconButton(
                modifier = Modifier.align(Alignment.CenterVertically),
                onClick = {
                    action()
                },

                ) {


                if (cartCount > 0) {

                    BadgedBox(badge = {
                        Badge() {
                            Text(text = cartCount.toString() , fontSize = 16.sp)
                        }
                    }) {
                        Icon(
                            Icons.Filled.ShoppingCart,
                            contentDescription = null,
                        )
                    }

                } else {
                    Icon(
                        Icons.Filled.ShoppingCart,
                        contentDescription = null,
                    )
                }
            }


        }
    }

}