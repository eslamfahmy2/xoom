package com.chuify.cleanxoomclient.presentation.ui.optConfrim.component

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
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
import androidx.navigation.NavController
import com.chuify.cleanxoomclient.R
import com.chuify.cleanxoomclient.presentation.ui.optConfrim.OTPIntent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch


@Composable
fun OTPScreen(
    phone: String,
    coroutineScope: CoroutineScope,
    userIntent: Channel<OTPIntent>,
    navController: NavController,
    scrollState: ScrollState = rememberScrollState(),

    ) {

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {


        Column(
            modifier = Modifier
                .wrapContentSize(),
        ) {

            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 8.dp),
                text = stringResource(R.string.verify_your_mobile_number),
                color = MaterialTheme.colors.onSurface,
                fontSize = 28.sp
            )

            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 8.dp),
                text = stringResource(R.string.enter_the_verification_code_sent_to),
                color = MaterialTheme.colors.onSurface,
                fontSize = 28.sp
            )

        }

        Spacer(modifier = Modifier.padding(32.dp))

        Column(modifier = Modifier
            .wrapContentSize()
        ) {

            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                (0..4).forEach {

                    TextField(
                        singleLine = true,
                        modifier = Modifier
                            .size(100.dp, 100.dp)
                            .padding(16.dp)
                            .background(
                                color = MaterialTheme.colors.surface,
                                shape = MaterialTheme.shapes.medium
                            ),
                        value = it.toString(),
                        onValueChange = {},
                        keyboardOptions = KeyboardOptions(
                            autoCorrect = false,
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        textStyle = TextStyle(
                            color = MaterialTheme.colors.secondaryVariant,
                            fontSize = 16.sp)
                    )


                }
            }

            Spacer(modifier = Modifier.padding(16.dp))

            Button(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                onClick = {
                    coroutineScope.launch {
                        userIntent.send(OTPIntent.Verify)
                    }
                })
            {
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(
                            shape = MaterialTheme.shapes.large,
                            color = MaterialTheme.colors.primary
                        ),
                    text = "Continue",
                    style = TextStyle(
                        fontSize = 16.sp
                    )
                )
            }

        }


    }

}