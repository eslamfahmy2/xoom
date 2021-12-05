package com.chuify.cleanxoomclient.presentation.ui.authentication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.cleanxoomclient.domain.usecase.auth.LoginResult
import com.chuify.cleanxoomclient.domain.usecase.auth.SignInUseCase
import com.chuify.cleanxoomclient.domain.usecase.auth.SignUpUseCase
import com.chuify.cleanxoomclient.domain.utils.DataState

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "LoginViewModel"

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
) : ViewModel() {

    val userIntent = Channel<AuthenticationIntent>(Channel.UNLIMITED)

    private val _phone: MutableStateFlow<String> = MutableStateFlow("720242047")
    val phone get() = _phone.asStateFlow()

    private val _firstName: MutableStateFlow<String> = MutableStateFlow(String())
    val firstName get() = _firstName.asStateFlow()

    private val _lastName: MutableStateFlow<String> = MutableStateFlow(String())
    val lastName get() = _lastName.asStateFlow()

    private val _email: MutableStateFlow<String> = MutableStateFlow(String())
    val email get() = _email.asStateFlow()


    private val _state: MutableStateFlow<AuthenticationState> =
        MutableStateFlow(AuthenticationState.Idl)
    val state get() = _state.asStateFlow()


    private val _stateUp: MutableStateFlow<AuthenticationState> =
        MutableStateFlow(AuthenticationState.Idl)
    val stateUp get() = _stateUp.asStateFlow()


    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect { intent ->
                when (intent) {
                    AuthenticationIntent.SignIn -> {
                        login()
                    }
                    is AuthenticationIntent.PhoneChange -> {
                        _phone.value = intent.data
                    }
                    is AuthenticationIntent.EmailChange -> {
                        _email.value = intent.data
                    }
                    is AuthenticationIntent.FirstNameChange -> {
                        _firstName.value = intent.data
                    }
                    is AuthenticationIntent.LastNameChange -> {
                        _lastName.value = intent.data
                    }
                    AuthenticationIntent.SignUp -> {
                        signup()
                    }
                }
            }
        }
    }


    private suspend fun login() = viewModelScope.launch(Dispatchers.IO) {
        loginUseCase(
            phone = _phone.value
        ).collect { result ->
            when (result) {
                is DataState.Error -> {
                    Log.d(TAG, "Error: " + result.message)
                    _state.value = AuthenticationState.Error(result.message)
                }
                is DataState.Loading -> {
                    Log.d(TAG, "Loading: " + result.message)
                    _state.value = AuthenticationState.Loading
                }
                is DataState.Success -> {
                    Log.d(TAG, "Success: " + result.data)
                    result.data?.let {
                        _state.value = AuthenticationState.Success(it)
                    }

                }
            }
        }
    }

    private suspend fun signup() = viewModelScope.launch(Dispatchers.IO) {
        signUpUseCase(
            firstname = _firstName.value,
            lastname = _lastName.value,
            email = _email.value,
            phone = _phone.value
        ).collect { result ->
            when (result) {
                is DataState.Error -> {
                    Log.d(TAG, "Error: " + result.message)
                    _stateUp.value = AuthenticationState.Error(result.message)
                }
                is DataState.Loading -> {
                    Log.d(TAG, "Loading: " + result.message)
                    _stateUp.value = AuthenticationState.Loading
                }
                is DataState.Success -> {
                    Log.d(TAG, "Success: " + result.data)
                    _stateUp.value = AuthenticationState.Success(LoginResult.Login)
                }
            }
        }
    }

    fun idl() {
        _state.value = AuthenticationState.Idl
        _stateUp.value = AuthenticationState.Idl
    }


}