package com.chuify.cleanxoomclient.presentation.ui.vendors.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chuify.cleanxoomclient.R
import com.chuify.cleanxoomclient.domain.model.Vendor


@ExperimentalMaterialApi
@Composable
fun VendorIdlScreen(
    data: List<Vendor>,
    onItemClicked: (Vendor) -> Unit,
    searchText: String,
    onTextChange: (String) -> Unit,
) {

    Column(
        Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.padding(top = 8.dp))

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 8.dp, end = 8.dp,
                    bottom = 8.dp
                ),
            value = searchText,
            onValueChange = { onTextChange(it) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    tint = MaterialTheme.colors.primary,
                    contentDescription = "Localized description"
                )
            },
            label = {
                Text(text = stringResource(R.string.search_on_xoom))
            },
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search,
            ),
            textStyle = TextStyle(
                color = MaterialTheme.colors.secondaryVariant,
                fontSize = 16.sp
            )

        )
        Spacer(modifier = Modifier.padding(top = 8.dp))
/*
        LazyRow {
            items(accessories) {
                AccessoryChip(accessory = it) { accessory ->
                    onAccessoryClicked(accessory)
                }
            }
        }

        Spacer(modifier = Modifier.padding(top = 8.dp))
*/
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(data) { it ->
                VendorItem(vendor = it, onItemClick = { onItemClicked(it) })
            }
        }


    }


}
