package com.chuify.xoomclient.presentation.ui.optConfrim

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.xoomclient.domain.usecase.auth.AuthenticatePhoneUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OTPViewModel @Inject constructor(
    private val useCase: AuthenticatePhoneUseCase,
) : ViewModel() {

    val userIntent = Channel<OTPIntent>(Channel.UNLIMITED)

    private val _phone: MutableState<String> = mutableStateOf("1234567891234")
    val phone get() = _phone

    private val _state: MutableState<OTPState> = mutableStateOf(OTPState.Idl)
    val state get() = _state


    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect { intent ->
                when (intent) {
                    OTPIntent.Verify -> {
                        _state.value = OTPState.Success()
                    }
                    is OTPIntent.PhoneChange -> {
                        _phone.value = intent.data
                    }
                    OTPIntent.Idl -> {
                        _state.value = OTPState.Idl
                    }
                }
            }
        }
    }

}