package com.chuify.xoomclient.presentation.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import io.github.farhanroy.cccp.CCPCountry
import io.github.farhanroy.cccp.getFlagMasterResID
import io.github.farhanroy.cccp.getLibraryMasterCountriesEnglish

@Composable
fun MyCountryCodeDialog(pickedCountry: (CCPCountry) -> Unit) {
    val countryList: List<CCPCountry> = getLibraryMasterCountriesEnglish()
    val picked = remember { mutableStateOf(countryList[0]) }
    val search = remember { mutableStateOf(String()) }
    val filteredCountryList: List<CCPCountry> =
        countryList.filter {
            it.name.lowercase().contains(search.value.lowercase())
                    || it.phoneCode.lowercase().contains(search.value.lowercase())
                    || it.nameCode.lowercase().contains(search.value.lowercase())
        }


    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        val openDialog = remember { mutableStateOf(false) }
        val dialogWidth = 300.dp
        val dialogHeight = 600.dp

        Row(
            modifier = Modifier.clickable {
                openDialog.value = true
            },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Image(
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp),
                painter = painterResource(
                    id = getFlagMasterResID(
                        picked.value
                    )
                ), contentDescription = null
            )
            Text(
                picked.value.nameCode,
                Modifier.padding(horizontal = 8.dp)
            )
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
        }

        if (openDialog.value) {
            Dialog(onDismissRequest = { openDialog.value = false }) {
                Column(
                    Modifier
                        .size(dialogWidth, dialogHeight)
                        .background(MaterialTheme.colors.surface)
                ) {

                    Spacer(modifier = Modifier.padding(16.dp))

                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(start = 8.dp),
                        text = "Pick country",
                        color = MaterialTheme.colors.onSurface,
                        fontSize = 28.sp
                    )

                    Spacer(modifier = Modifier.padding(16.dp))

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(
                                color = MaterialTheme.colors.surface,
                                shape = MaterialTheme.shapes.medium
                            ),
                        value = search.value,
                        onValueChange = {
                            search.value = it
                        },
                        label = {
                            Text(text = "Search")
                        },
                        textStyle = TextStyle(
                            color = MaterialTheme.colors.secondaryVariant,
                            fontSize = 16.sp
                        )

                    )

                    LazyColumn {
                        items(filteredCountryList.size) { index ->
                            Row(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clickable {
                                        pickedCountry(filteredCountryList[index])
                                        picked.value = filteredCountryList[index]
                                        openDialog.value = false
                                    },
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    modifier = Modifier
                                        .width(40.dp)
                                        .height(40.dp),
                                    painter = painterResource(
                                        id = getFlagMasterResID(
                                            filteredCountryList[index]
                                        )
                                    ), contentDescription = null
                                )
                                Text(
                                    filteredCountryList[index].name,
                                    Modifier.padding(horizontal = 18.dp)
                                )

                                Text(
                                    filteredCountryList[index].phoneCode,
                                    Modifier.padding(horizontal = 18.dp)
                                )
                            }
                        }
                    }
                }

            }
        }
    }


}