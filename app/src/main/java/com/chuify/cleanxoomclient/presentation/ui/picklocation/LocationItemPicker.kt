package com.chuify.cleanxoomclient.presentation.ui.picklocation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.chuify.cleanxoomclient.R
import com.chuify.cleanxoomclient.domain.model.Location
import com.chuify.cleanxoomclient.presentation.components.LocationItem

@Composable
fun LocationItemPicker(
    location: Location,
    onPick: (Location) -> Unit,
) {

    Card(elevation = 8.dp, modifier = Modifier
        .padding(8.dp)
        .clickable {
            onPick(location)
        }) {

        Column(modifier = Modifier.fillMaxWidth()) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = stringResource(id = R.string.delivery_address),
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(16.dp),
                )

                Checkbox(
                    checked = location.selected, onCheckedChange = {
                        onPick(location)
                    }, enabled = true,

                    modifier = Modifier.padding(16.dp)
                )

            }


            LocationItem(location = location)

        }


    }

}