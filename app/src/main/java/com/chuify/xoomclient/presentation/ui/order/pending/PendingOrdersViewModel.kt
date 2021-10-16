package com.chuify.xoomclient.presentation.ui.order.pending

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.xoomclient.domain.usecase.order.GetPendingOrderListUseCase
import com.chuify.xoomclient.domain.utils.DataState
import com.chuify.xoomclient.presentation.ui.signup.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PendingOrdersViewModel @Inject constructor(
    private val getPendingOrderListUseCase: GetPendingOrderListUseCase,
) : ViewModel() {

    val userIntent = Channel<PendingOrdersIntent>(Channel.UNLIMITED)

    private val _state: MutableState<PendingOrdersState> =
        mutableStateOf(PendingOrdersState.Loading)
    val state get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {

            userIntent.consumeAsFlow().collect { intent ->
                when (intent) {
                    PendingOrdersIntent.LoadPendingOrders -> {
                        loadPendingOrders()
                    }

                }
            }
        }
    }

    private suspend fun loadPendingOrders() = viewModelScope.launch(Dispatchers.IO) {
        getPendingOrderListUseCase().collect { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    Log.d(TAG, "Error: " + dataState.message)
                    _state.value = PendingOrdersState.Error(dataState.message)
                }
                is DataState.Loading -> {
                    _state.value = PendingOrdersState.Loading
                }
                is DataState.Success -> {
                    dataState.data?.let {
                        _state.value = PendingOrdersState.Success(orders = it)
                    }

                }
            }
        }
    }


}