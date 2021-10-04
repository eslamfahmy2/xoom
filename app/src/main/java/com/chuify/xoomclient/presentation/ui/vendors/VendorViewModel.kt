package com.chuify.xoomclient.presentation.ui.vendors

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.xoomclient.domain.usecase.auth.SignInUseCase
import com.chuify.xoomclient.domain.utils.DataState
import com.chuify.xoomclient.presentation.ui.signup.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class VendorViewModel @Inject constructor(
    private val useCase: SignInUseCase,
) : ViewModel() {

    val userIntent = Channel<VendorIntent>(Channel.UNLIMITED)

    private val _phone: MutableState<String> = mutableStateOf("+254703894372")
    val phone get() = _phone

    private val _state: MutableState<VendorState> = mutableStateOf(VendorState.Idl)
    val state get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect { intent ->
                when (intent) {
                    VendorIntent.SignIn -> {
                        useCase(
                            phone = _phone.value
                        ).collect { result ->
                            when (result) {
                                is DataState.Error -> {
                                    Log.d(TAG, "Error: " + result.message)
                                    _state.value = VendorState.Error(result.message)
                                }
                                is DataState.Loading -> {
                                    Log.d(TAG, "Loading: " + result.message)
                                    _state.value = VendorState.Loading
                                }
                                is DataState.Success -> {
                                    Log.d(TAG, "Success: " + result.data)
                                    _state.value = VendorState.Success(result.data)
                                }
                            }
                        }
                    }
                    is VendorIntent.PhoneChange -> {
                        _phone.value = intent.data
                    }
                }
            }
        }
    }

}