package com.chuify.xoomclient.presentation.ui.authentication.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chuify.xoomclient.R
import com.chuify.xoomclient.domain.utils.Validator


@Composable
fun LoginScreen(
    phone: String,
    onLogin: () -> Unit,
    onValueChanged: (String) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {


        Text(
            modifier = Modifier
                .wrapContentSize()
                .padding(start = 8.dp),
            text = stringResource(id = R.string.enter_your_mobile_number),
            color = MaterialTheme.colors.onSurface,
            fontSize = 28.sp
        )


        Spacer(modifier = Modifier.padding(4.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(5.dp),
                        color = Color.Gray
                    )
                    .padding(10.dp)
                    .width(40.dp)
                    .height(35.dp)
                    .align(Alignment.CenterVertically),
                painter = painterResource(
                    id = R.drawable.flag_kenya
                ), contentDescription = null
            )
            Spacer(modifier = Modifier.padding(4.dp))

            TextField(
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(5.dp),
                        color = Color.Gray
                    )
                    .align(Alignment.CenterVertically),
                value = phone,
                onValueChange = { onValueChanged(it.take(Validator.PHONE_LENGTH)) },
                leadingIcon = {
                    Text(
                        text = "+254",
                        color = MaterialTheme.colors.onSurface,
                        fontSize = 16.sp
                    )
                },
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onLogin() }
                ),
                textStyle = TextStyle(
                    fontSize = 16.sp
                )

            )
        }

        Spacer(modifier = Modifier.padding(4.dp))
        val focusManager = LocalFocusManager.current
        Button(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            onClick = {
                focusManager.clearFocus()
                onLogin()
            })
        {
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .background(
                        shape = MaterialTheme.shapes.large,
                        color = MaterialTheme.colors.primary
                    ),
                text = stringResource(R.string.con),
                style = TextStyle(
                    fontSize = 16.sp
                )
            )
        }

    }


}