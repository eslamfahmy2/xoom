package com.chuify.xoomclient.presentation.ui.login

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.xoomclient.domain.usecase.auth.AuthInteraction
import com.chuify.xoomclient.domain.utils.DataState
import com.chuify.xoomclient.presentation.ui.signup.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCase: AuthInteraction,
) : ViewModel() {

    val userIntent = Channel<LoginIntent>(Channel.UNLIMITED)

    private val _phone: MutableState<String> = mutableStateOf("1234567891234")
    val phone get() = _phone

    private val _state: MutableState<LoginState> = mutableStateOf(LoginState.Idl)
    val state get() = _state


    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect { intent ->
                when (intent) {
                    LoginIntent.SignIn -> TODO()
                    LoginIntent.SignUp -> {
                        useCase.signIn(
                            phone = _phone.value
                        ).collect { result ->
                            when (result) {
                                is DataState.Error -> {
                                    Log.d(TAG, "Error: " + result.message)
                                    _state.value = LoginState.Error(result.message)
                                }
                                is DataState.Loading -> {
                                    Log.d(TAG, "Loading: " + result.message)
                                    _state.value = LoginState.Loading
                                }
                                is DataState.Success -> {
                                    Log.d(TAG, "Success: " + result.data)
                                    _state.value = LoginState.Success(result.data)
                                }
                            }
                        }
                    }
                    is LoginIntent.PhoneChange -> {
                        _phone.value = intent.data
                    }
                }
            }
        }
    }

}