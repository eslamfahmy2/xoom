package com.chuify.xoomclient.presentation.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.chuify.xoomclient.presentation.components.AppBar
import com.chuify.xoomclient.presentation.theme.XoomGasClientTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

                        val firstName = viewModel.firstName.value
                        val coroutineScope = rememberCoroutineScope()

                        Column(modifier = Modifier.fillMaxWidth()) {

                            TextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                value = firstName,
                                onValueChange = {
                                    coroutineScope.launch {
                                        viewModel.userIntent.send(SignUpIntent.FirstNameChange(it))
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
                                    fontSize = 12.sp
                                ),

                                )

                        }

                    }
                }
            }
        }
    }
}



