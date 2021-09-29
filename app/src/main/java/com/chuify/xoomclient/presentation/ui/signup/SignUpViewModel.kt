package com.chuify.xoomclient.presentation.ui.signup

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

private const val TAG = "ListViewModel"

@HiltViewModel
class ListViewModel @Inject constructor(
    private val useCase: AuthInteraction,
) : ViewModel() {

    val userIntent = Channel<SignUpIntent>(Channel.UNLIMITED)

    private val _firstName: MutableState<String> = mutableStateOf("")
    val firstName get() = _firstName

    private val _lastName: MutableState<String> = mutableStateOf("")
    val lastName get() = _lastName

    private val _email: MutableState<String> = mutableStateOf("")
    val email get() = _email

    private val _phone: MutableState<String> = mutableStateOf("")
    val phone get() = _phone

    private val _state: MutableState<SignUpState> = mutableStateOf(SignUpState.Loading)
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
                                    _state.value = SignUpState.Error(result.message)
                                }
                                is DataState.Loading -> {
                                    _state.value = SignUpState.Loading
                                }
                                is DataState.Success -> {
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