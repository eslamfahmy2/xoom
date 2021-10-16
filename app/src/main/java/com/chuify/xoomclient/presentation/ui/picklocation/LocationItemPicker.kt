package com.chuify.xoomclient.presentation.ui.picklocation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.chuify.xoomclient.R
import com.chuify.xoomclient.domain.model.Location
import com.chuify.xoomclient.presentation.components.LocationItem

@Composable
fun LocationItemPicker(
    location: Location,
    onPick: (Location) -> Unit,
) {

    Card(elevation = 2.dp, modifier = Modifier.padding(8.dp)) {

        Column(modifier = Modifier.fillMaxWidth()) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {

                Text(
                    text = stringResource(id = R.string.delivery_address),
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(16.dp),
                )

                Checkbox(checked = true, onCheckedChange = {
                    onPick(location)
                })

            }


            LocationItem(location = location)

        }


    }

}