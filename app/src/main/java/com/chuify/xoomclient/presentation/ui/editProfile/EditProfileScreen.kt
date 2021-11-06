package com.chuify.xoomclient.presentation.ui.editProfile

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chuify.xoomclient.R
import com.chuify.xoomclient.presentation.components.DefaultSnackBar
import com.chuify.xoomclient.presentation.components.LoadingDialog
import com.chuify.xoomclient.presentation.components.SecondaryBar
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun EditProfileScreen(
    navHostController: NavHostController,
    viewModel: EditProfileViewModel = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()

    val coroutineScope = rememberCoroutineScope()

    val state = viewModel.state.collectAsState().value


    Scaffold(
        topBar = {
            SecondaryBar {
                navHostController.popBackStack()
            }
        },
        scaffoldState = scaffoldState,
        snackbarHost = {
            scaffoldState.snackbarHostState
        },
        bottomBar = {
            DefaultSnackBar(
                snackHostState = scaffoldState.snackbarHostState,
                onDismiss = {
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                },
            )
        }
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primary)
                .clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
                .background(MaterialTheme.colors.surface),
            elevation = 20.dp
        )
        {

            Column(modifier = Modifier.padding(8.dp)) {

                Text(
                    modifier = Modifier.padding(8.dp),
                    text = stringResource(id = R.string.edit_profile),
                    style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold)
                )

                Box(modifier = Modifier.fillMaxSize()) {

                    val firstName = viewModel.firstName.collectAsState().value
                    val lastName = viewModel.lastName.collectAsState().value
                    val email = viewModel.email.collectAsState().value

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
                                coroutineScope.launch {
                                    viewModel.userIntent.send(
                                        EditProfileIntent.FirstNameChange(
                                            it
                                        )
                                    )
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
                                    viewModel.userIntent.send(
                                        EditProfileIntent.LastNameChange(
                                            it
                                        )
                                    )
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
                                    viewModel.userIntent.send(EditProfileIntent.EmailChange(it))
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
                                    viewModel.userIntent.send(EditProfileIntent.EditProfile)
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
                                text = "Edit",
                                style = TextStyle(
                                    fontSize = 16.sp
                                )
                            )
                        }

                    }

                    when (state) {
                        is EditProfileState.Error -> {
                            state.message?.let {
                                coroutineScope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        message = it,
                                        actionLabel = "Dismiss",
                                    )
                                }
                            }
                        }
                        EditProfileState.Loading -> {
                            LoadingDialog()
                        }
                        is EditProfileState.Success -> {
                            navHostController.popBackStack()
                        }
                    }

                }
            }


        }

    }

    LaunchedEffect(true) {
        viewModel.userIntent.send(EditProfileIntent.LoadProfile)
    }


}