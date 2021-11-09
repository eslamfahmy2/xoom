package com.chuify.xoomclient.presentation.ui.authentication.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun SignupScreen(
    firstName: String,
    lastName: String,
    email: String,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onEmailNameChange: (String) -> Unit,
    onSignup: () -> Unit,
    onLogin: () -> Unit,


    ) {

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {


        Column(
            modifier = Modifier
                .wrapContentSize(),
        ) {

            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 8.dp),
                text = "Hello There !",
                color = MaterialTheme.colors.onSurface,
                fontSize = 28.sp
            )

            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 8.dp),
                text = "Welcome to Xoom Gas",
                color = MaterialTheme.colors.onSurface,
                fontSize = 28.sp
            )

        }

        Column(
            modifier = Modifier
                .wrapContentSize()
        ) {

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(
                        color = MaterialTheme.colors.surface,
                        shape = MaterialTheme.shapes.medium
                    ),
                value = firstName,
                onValueChange = {
                    onFirstNameChange(it)
                },
                label = {
                    Text(text = "First name")
                },
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                textStyle = TextStyle(
                    fontSize = 16.sp
                )

            )


            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(
                        color = MaterialTheme.colors.surface,
                    ),
                value = lastName,
                onValueChange = {
                    onLastNameChange(it)
                },
                label = {
                    Text(text = "Last name")
                },
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                textStyle = TextStyle(
                    fontSize = 16.sp
                )

            )


            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(
                        color = MaterialTheme.colors.surface,
                    ),
                value = email,
                onValueChange = {
                    onEmailNameChange(it)
                },
                label = {
                    Text(text = "Email")
                },
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                textStyle = TextStyle(
                    fontSize = 16.sp
                )

            )


            Button(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                onClick = {
                    focusManager.clearFocus()
                    onSignup()
                })
            {
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(
                            shape = MaterialTheme.shapes.large,
                            color = MaterialTheme.colors.primary
                        ),
                    text = "Signup",
                    style = TextStyle(
                        fontSize = 16.sp
                    )
                )
            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center

        ) {
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp),
                text = "Do you have account?",
                color = MaterialTheme.colors.onSurface,
                fontSize = 16.sp
            )

            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp)
                    .clickable {
                        focusManager.clearFocus()
                        onLogin()
                    },
                text = "Login",
                color = MaterialTheme.colors.primary,
                fontSize = 16.sp
            )
        }


    }

}