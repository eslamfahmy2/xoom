package com.chuify.xoomclient.presentation.ui.login.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.chuify.xoomclient.R
import com.chuify.xoomclient.presentation.ui.login.LoginIntent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(
    phone: String,
    coroutineScope: CoroutineScope,
    userIntent: Channel<LoginIntent>,
    navController: NavController,

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
            }
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
                        userIntent.send(LoginIntent.PhoneChange(it))
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
                            userIntent.send(LoginIntent.SignIn)
                        }
                    }
                ),
                textStyle = TextStyle(
                    color = MaterialTheme.colors.secondaryVariant,
                    fontSize = 16.sp
                )

            )


            Button(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                onClick = {
                    coroutineScope.launch {
                        userIntent.send(LoginIntent.SignUp)
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
                    text = "Login",
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
                    .padding(8.dp),
                text = "Don't have account?",
                color = MaterialTheme.colors.onSurface,
                fontSize = 16.sp
            )

            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp)
                    .clickable {
                        val bundle = bundleOf()
                        navController.navigate(
                            R.id.action_loginFragment_to_signUpFragment,
                            bundle
                        )
                    },
                text = "Signup",
                color = MaterialTheme.colors.primary,
                fontSize = 16.sp
            )
        }


    }

}