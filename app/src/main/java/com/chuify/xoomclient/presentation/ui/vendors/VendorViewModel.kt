package com.chuify.xoomclient.presentation.ui.vendors

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.xoomclient.domain.usecase.vendor.ListVendorsUseCase
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
    private val useCase: ListVendorsUseCase,
) : ViewModel() {

    val userIntent = Channel<VendorIntent>(Channel.UNLIMITED)

    private val _state: MutableState<VendorState> = mutableStateOf(VendorState.Loading)
    val state get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect { intent ->
                when (intent) {
                    VendorIntent.LoadVendors -> {
                        useCase().collect { dataState ->
                            when (dataState) {
                                is DataState.Error -> {
                                    Log.d(TAG, "Error: "+ dataState.message)
                                    _state.value = VendorState.Error(dataState.message)
                                }
                                is DataState.Loading -> {
                                    _state.value = VendorState.Loading
                                }
                                is DataState.Success -> {
                                    _state.value = VendorState.Success(dataState.data ?: listOf())
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}