package com.chuify.xoomclient.presentation.ui.signup

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.xoomclient.domain.usecase.auth.AuthInteraction
import com.chuify.xoomclient.domain.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "SignUpViewModel"

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val useCase: AuthInteraction,
) : ViewModel() {

    val userIntent = Channel<SignUpIntent>(Channel.UNLIMITED)

    private val _firstName: MutableState<String> = mutableStateOf("eslam")
    val firstName get() = _firstName

    private val _lastName: MutableState<String> = mutableStateOf("fahmy")
    val lastName get() = _lastName

    private val _email: MutableState<String> = mutableStateOf("eslam@fa.com")
    val email get() = _email

    private val _phone: MutableState<String> = mutableStateOf("1234567891234")
    val phone get() = _phone

    private val _state: MutableState<SignUpState> = mutableStateOf(SignUpState.Idl)
    val state get() = _state


    init {
        handleIntent()

    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect { intent ->
                when (intent) {
                    SignUpIntent.SignIn -> TODO()
                    SignUpIntent.SignUp -> {
                        useCase.signUp(
                            firstname = _firstName.value,
                            lastname = _lastName.value,
                            email = _email.value,
                            phone = _phone.value
                        ).collect { result ->
                            when (result) {
                                is DataState.Error -> {
                                    Log.d(TAG, "Error: " + result.message)
                                    _state.value = SignUpState.Error(result.message)
                                }
                                is DataState.Loading -> {
                                    Log.d(TAG, "Loading: " + result.message)
                                    _state.value = SignUpState.Loading
                                }
                                is DataState.Success -> {
                                    Log.d(TAG, "Success: " + result.data)
                                    _state.value = SignUpState.Success(result.data)
                                }
                            }
                        }
                    }
                    is SignUpIntent.EmailChange -> {
                        _email.value = intent.data
                    }
                    is SignUpIntent.FirstNameChange -> {
                        _firstName.value = intent.data
                    }
                    is SignUpIntent.LastNameChange -> {
                        _lastName.value = intent.data
                    }
                }
            }
        }
    }

}