package com.chuify.xoomclient.presentation.ui.vendors.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chuify.xoomclient.R
import com.chuify.xoomclient.domain.model.Vendor


@Composable
fun VendorScreen(
    data: List<Vendor>,
    onItemClicked: (Vendor) -> Unit,
    searchText: String,
    onTextChange: (String) -> Unit,
) {

    Column {

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            value = searchText,
            onValueChange = { onTextChange(it) },
            label = {
                Text(text = stringResource(R.string.search_on_xoom))
            },
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            textStyle = TextStyle(
                color = MaterialTheme.colors.secondaryVariant,
                fontSize = 16.sp
            )

        )

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(data) { it ->
                VendorItem(vendor = it, onItemClick = { onItemClicked(it) })
            }
        }

    }


}