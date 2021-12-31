package com.chuify.cleanxoomclient.presentation.ui.vendors.component

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.chuify.cleanxoomclient.domain.model.Vendor


@ExperimentalMaterialApi
@Composable
fun VendorItem(
    vendor: Vendor,
    onItemClick: (Vendor) -> Unit,
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 15.dp,
        onClick = { onItemClick(vendor) },
    ) {
        Column {

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                painter = rememberImagePainter(
                    data = vendor.image,
                    builder = {
                        crossfade(true)
                    }
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(
                        color = MaterialTheme.colors.primary,
                        shape = RoundedCornerShape(0.dp)
                    )
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(12.dp),
                    text = vendor.name,
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )


            }

        }


    }
}