package com.chuify.xoomclient.presentation.components

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chuify.xoomclient.presentation.ui.signup.TAG

enum class BikePosition {
    Start, Finish
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun HomeBar(
    title: String,
    action: () -> Unit,
    cartCount: Int,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.surface
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

            var bikeState by remember { mutableStateOf(BikePosition.Start) }

            val offsetAnimation by animateDpAsState(
                targetValue = if (bikeState == BikePosition.Start) 0.dp else 1000.dp,
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

                Text(
                    text = title,
                    style = TextStyle(
                        color = MaterialTheme.colors.onSurface,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    ),
                )


                Image(
                    painterResource(com.chuify.xoomclient.R.drawable.xoom_gas_rider),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(55.dp, 55.dp)
                        .absoluteOffset(x = offsetAnimation)
                )

            }

            bikeState = BikePosition.Finish
            IconButton(
                modifier = Modifier.align(Alignment.CenterVertically),
                onClick = {
                    action()
                },

                ) {

                if (cartCount > 0) {

                    BadgedBox(badge = {
                        Text(text = cartCount.toString())
                    }) {
                        Icon(
                            Icons.Filled.ShoppingCart,
                            contentDescription = null
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