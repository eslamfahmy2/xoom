package com.chuify.xoomclient.presentation.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
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

                YarabTheme(
                    isDark = false
                ) {

                    Scaffold(
                        topBar = {
                            AppBar(
                                title = "Api ",
                                query = viewModel.query.value,
                                onValueChanged = {
                                    lifecycleScope.launch {
                                        viewModel.userIntent.send(SignUpIntent.QueryChange(it))
                                    }
                                },
                                onSearch = {
                                    lifecycleScope.launch {
                                        viewModel.userIntent.send(SignUpIntent.Search)
                                    }
                                },
                                onToggleTheme = {

                                }
                            )
                        }
                    ) {

                        when (val result = viewModel.state.value) {

                            is SignUpState.Error -> {
                                Text(text = result.message ?: "")
                            }
                            SignUpState.Loading -> {
                                LoadingScreen()
                            }
                            is SignUpState.Success -> {
                                DataScreen(data = result.data, findNavController())
                            }
                        }
                    }
                }
            }
        }
    }
}



