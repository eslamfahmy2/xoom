package com.chuify.xoomclient.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.chuify.xoomclient.domain.model.Payments


@Composable
fun PaymentItem(
    modifier: Modifier = Modifier,
    paymentMethod: Payments,
) {


    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(10)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start)
    {


        Image(
            modifier = Modifier
                .padding(12.dp)
                .size(24.dp, 24.dp),
            painter = painterResource(id = paymentMethod.icon),
            contentDescription = null)

        Text(modifier = Modifier.padding(4.dp),
            text = paymentMethod.name,
            color = MaterialTheme.colors.secondaryVariant
        )


    }


}