package com.chuify.xoomclient.presentation.ui.vendors.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chuify.xoomclient.domain.model.Vendor
import com.chuify.xoomclient.presentation.components.VendorItem


@Composable
fun VendorScreen(
    data: List<Vendor>,
) {

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(data) {
            VendorItem(vendor = it)
        }
    }

}