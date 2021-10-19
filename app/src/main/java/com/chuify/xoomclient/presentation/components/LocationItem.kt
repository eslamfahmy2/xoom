package com.chuify.xoomclient.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.chuify.xoomclient.R
import com.chuify.xoomclient.domain.model.Location


@Composable
fun LocationItem(location: Location) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .padding(16.dp)
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(10)
            ),
    ) {

        val brush = Brush.linearGradient(
            colors = listOf(MaterialTheme.colors.surface, Color.Transparent),
        )

        Image(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(10)),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.map),
            contentDescription = null)



        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = brush,
                    shape = RoundedCornerShape(10)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween)
        {

            Row {
                Image(
                    modifier = Modifier.padding(16.dp),
                    painter = painterResource(id = R.drawable.ic_address),
                    contentDescription = null)

                Column {
                    Text(modifier = Modifier.padding(4.dp),
                        text = location.title.toString(),
                        color = MaterialTheme.colors.onSurface

                    )

                    Text(modifier = Modifier.padding(4.dp),
                        text = location.details.toString(),
                        color = MaterialTheme.colors.secondaryVariant
                    )

                }

            }

            Image(
                modifier = Modifier.padding(16.dp),
                painter = painterResource(id = R.drawable.ic_location),
                contentDescription = null)


        }

    }


}

