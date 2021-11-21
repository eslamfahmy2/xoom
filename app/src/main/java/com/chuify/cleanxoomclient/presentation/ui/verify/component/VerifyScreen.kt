package com.chuify.cleanxoomclient.presentation.ui.verify.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.chuify.cleanxoomclient.R
import com.chuify.cleanxoomclient.presentation.ui.verify.VerifyIntent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch


@Composable
fun VerifyScreen(
    phone: String,
    coroutineScope: CoroutineScope,
    userIntent: Channel<VerifyIntent>,
    navController: NavController,
    scrollState: ScrollState = rememberScrollState(),
    activity: FragmentActivity,

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
                text = "First Step",
                color = MaterialTheme.colors.onSurface,
                fontSize = 28.sp
            )

            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 8.dp),
                text = "Let's verify phone",
                color = MaterialTheme.colors.onSurface,
                fontSize = 28.sp
            )

        }

        Spacer(modifier = Modifier.padding(32.dp))

        Column(modifier = Modifier
            .wrapContentSize()
        ) {

            TextField(
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(
                        color = MaterialTheme.colors.surface,
                        shape = MaterialTheme.shapes.medium
                    )
                    .align(CenterHorizontally),
                value = phone,
                onValueChange = {
                    coroutineScope.launch {
                        userIntent.send(VerifyIntent.PhoneChange(it))
                    }
                },
                label = {
                    Text(text = "Mobile number")
                },
                leadingIcon = {
                    Image(
                        modifier = Modifier
                            .padding(12.dp)
                            .width(40.dp)
                            .height(40.dp),
                        painter = painterResource(
                            id = R.drawable.flag_kenya
                        ), contentDescription = null
                    )
                },
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        coroutineScope.launch {
                            userIntent.send(VerifyIntent.Verify(activity))
                        }
                    }
                ),
                textStyle = TextStyle(
                    color = MaterialTheme.colors.secondaryVariant,
                    fontSize = 16.sp
                )

            )


            Spacer(modifier = Modifier.padding(16.dp))



            Button(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                onClick = {
                    coroutineScope.launch {
                        userIntent.send(VerifyIntent.Verify(activity))
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