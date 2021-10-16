package com.chuify.xoomclient.presentation.ui.order.complet

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.xoomclient.domain.usecase.order.GetCompletedOrderListUseCase
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
class CompletedOrdersViewModel @Inject constructor(
    private val getCompletedOrderListUseCase: GetCompletedOrderListUseCase,
) : ViewModel() {

    val userIntent = Channel<CompletedOrdersIntent>(Channel.UNLIMITED)

    private val _state: MutableState<CompletedOrdersState> =
        mutableStateOf(CompletedOrdersState.Loading)
    val state get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {

            userIntent.consumeAsFlow().collect { intent ->
                when (intent) {
                    CompletedOrdersIntent.LoadCompletedOrders -> {
                        loadCompletedOrders()
                    }

                }
            }
        }
    }

    private suspend fun loadCompletedOrders() = viewModelScope.launch(Dispatchers.IO) {
        getCompletedOrderListUseCase().collect { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    Log.d(TAG, "Error: " + dataState.message)
                    _state.value = CompletedOrdersState.Error(dataState.message)
                }
                is DataState.Loading -> {
                    _state.value = CompletedOrdersState.Loading
                }
                is DataState.Success -> {
                    dataState.data?.let {
                        _state.value = CompletedOrdersState.Success(orders = it)
                    }

                }
            }
        }
    }


}