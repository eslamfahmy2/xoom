package com.chuify.xoomclient.presentation.ui.verify

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.xoomclient.domain.usecase.auth.AuthenticatePhoneUseCase
import com.chuify.xoomclient.domain.utils.DataState
import com.chuify.xoomclient.presentation.ui.signup.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class VerifyViewModel @Inject constructor(
    private val useCase: AuthenticatePhoneUseCase,
) : ViewModel() {

    val userIntent = Channel<VerifyIntent>(Channel.UNLIMITED)

    private val _phone: MutableState<String> = mutableStateOf("+201112331246")
    val phone get() = _phone

    private val _state: MutableState<VerifyState> = mutableStateOf(VerifyState.Idl)
    val state get() = _state


    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect { intent ->
                when (intent) {

                    is VerifyIntent.PhoneChange -> {
                        _phone.value = intent.data
                    }
                    VerifyIntent.Idl -> {
                        _state.value = VerifyState.Idl
                    }
                    is VerifyIntent.Verify -> {
                        useCase(_phone.value, intent.activity).collect { result ->
                            when (result) {
                                is DataState.Error -> {
                                    Log.d(TAG, "handleIntent: " + result.message)
                                    _state.value = VerifyState.Error(result.message)
                                }
                                is DataState.Loading -> {
                                    _state.value = VerifyState.Loading
                                }
                                is DataState.Success -> {
                                    _state.value = VerifyState.Success(result.data ?: "")
                                }
                            }
                        }

                    }
                }
            }
        }
    }

}