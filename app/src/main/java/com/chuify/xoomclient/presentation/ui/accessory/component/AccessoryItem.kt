package com.chuify.xoomclient.presentation.ui.accessory.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.chuify.xoomclient.R
import com.chuify.xoomclient.domain.model.Accessory
import com.chuify.xoomclient.domain.model.Vendor

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AccessoryItem(
    vendor: Accessory,
    onItemClick: () -> Unit,
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(8.dp),
        elevation = 15.dp,
        onClick = { onItemClick() }
    ) {
        Box() {

            Image(
                painter = rememberImagePainter(vendor.image),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(12.dp)
                    .align(Alignment.BottomStart),
                text = vendor.name,
                color = MaterialTheme.colors.onSurface,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(12.dp)
                    .background(
                        color = MaterialTheme.colors.primary,
                        shape = RoundedCornerShape(5.dp)
                    )
                    .padding(
                        start = 24.dp,
                        end = 24.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .align(Alignment.BottomEnd),
                text = stringResource(R.string.from_kes_1_500),
                fontSize = 17.sp
            )

        }


    }
}