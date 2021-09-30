package com.chuify.xoomclient.presentation.ui.signup.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.chuify.xoomclient.presentation.ui.signup.SignUpIntent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch


@Composable
fun SignUpIdleScreen(
    firstName: String,
    lastName: String,
    email: String,
    coroutineScope: CoroutineScope,
    userIntent: Channel<SignUpIntent>,

    ) {

    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)) {

        val (header, content, footer) = createRefs()

        Column(
            modifier = Modifier
                .wrapContentSize()
                .constrainAs(header) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(content.top)
                },
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

        Column(modifier = Modifier
            .wrapContentSize()
            .constrainAs(content) {
                top.linkTo(header.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(footer.top)
            }) {

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
                    coroutineScope.launch {
                        userIntent.send(SignUpIntent.FirstNameChange(it))
                    }
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
                    color = MaterialTheme.colors.secondaryVariant,
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
                    coroutineScope.launch {
                        userIntent.send(SignUpIntent.LastNameChange(it))
                    }
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
                    color = MaterialTheme.colors.secondaryVariant,
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
                    coroutineScope.launch {
                        userIntent.send(SignUpIntent.EmailChange(it))
                    }
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
                    color = MaterialTheme.colors.secondaryVariant,
                    fontSize = 16.sp
                )

            )


            Button(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                onClick = {
                    coroutineScope.launch {
                        userIntent.send(SignUpIntent.SignUp)
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
                    text = "Signup",
                    style = TextStyle(
                        fontSize = 16.sp
                    )
                )
            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(footer) {
                    top.linkTo(content.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center

        ) {
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 8.dp),
                text = "Do you have account?",
                color = MaterialTheme.colors.onSurface,
                fontSize = 16.sp
            )

            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 8.dp),
                text = "Login",
                color = MaterialTheme.colors.primary,
                fontSize = 16.sp
            )
        }


    }

}