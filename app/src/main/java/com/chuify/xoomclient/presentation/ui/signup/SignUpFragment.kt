package com.chuify.xoomclient.presentation.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.chuify.xoomclient.presentation.components.AppBar
import com.chuify.xoomclient.presentation.theme.XoomGasClientTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private val viewModel: ListViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                XoomGasClientTheme(
                    darkTheme = false
                ) {
                    Scaffold(
                        topBar = {
                            AppBar(
                                title = "Signup",
                                onToggleTheme = {

                                }
                            )
                        }
                    ) {

                        val firstName by remember {
                            viewModel.firstName
                        }

                        val lastName by remember {
                            viewModel.lastName
                        }

                        val email by remember {
                            viewModel.email
                        }

                        val coroutineScope = rememberCoroutineScope()

                        ConstraintLayout(modifier = Modifier.fillMaxSize()) {

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
                                        ),
                                    value = firstName,
                                    onValueChange = {
                                        coroutineScope.launch {
                                            viewModel.userIntent.send(SignUpIntent.FirstNameChange(
                                                it))
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
                                        fontSize = 22.sp
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
                                            viewModel.userIntent.send(SignUpIntent.LastNameChange(it))
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
                                        fontSize = 22.sp
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
                                            viewModel.userIntent.send(SignUpIntent.EmailChange(it))
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
                                        fontSize = 22.sp
                                    )

                                )


                                Button(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth(),
                                    onClick = {
                                        coroutineScope.launch {
                                            viewModel.userIntent.send(SignUpIntent.SignUp)
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
                                        text = "Signup"
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
                }
            }
        }
    }
}



