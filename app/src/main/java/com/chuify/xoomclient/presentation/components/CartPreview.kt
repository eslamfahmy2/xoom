package com.chuify.xoomclient.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.chuify.xoomclient.R

@ExperimentalMaterialApi
@Composable
fun CartPreview(
    quantity: String,
    price: String,
    onClick: () -> Unit,
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 15.dp,
        backgroundColor = MaterialTheme.colors.primary,
        shape = RoundedCornerShape(22.dp),
        onClick = { onClick() }

    ) {
        Box(modifier = Modifier.padding(8.dp)) {

            Row(
                modifier = Modifier.align(Alignment.CenterStart),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start) {
                Text(
                    text = "100",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(2.dp)
                )
                Text(
                    text = stringResource(R.string.currency),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(2.dp)
                )

            }

            Row(
                modifier = Modifier.align(Alignment.CenterEnd),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "100",
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "x",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(2.dp)
                )
                Text(
                    text = stringResource(R.string.checkout),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(2.dp))

                Icon(Icons.Filled.ArrowForward, contentDescription = null,
                    modifier = Modifier.padding(4.dp))


            }

        }
    }

}