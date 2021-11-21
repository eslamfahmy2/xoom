package com.chuify.cleanxoomclient.presentation.ui.order.complet

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.cleanxoomclient.domain.model.Order
import com.chuify.cleanxoomclient.domain.usecase.order.GetCompletedOrderListUseCase
import com.chuify.cleanxoomclient.domain.usecase.order.RecorderUseCase
import com.chuify.cleanxoomclient.domain.utils.DataState

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "CompletedOrdersViewMode"

@HiltViewModel
class CompletedOrdersViewModel @Inject constructor(
    private val getCompletedOrderListUseCase: GetCompletedOrderListUseCase,
    private val reorderUseCase: RecorderUseCase,
) : ViewModel() {

    val userIntent = Channel<CompletedOrdersIntent>(Channel.UNLIMITED)

    private val _state: MutableState<CompletedOrdersState> =
        mutableStateOf(CompletedOrdersState.Loading)
    val state get() = _state

    private val _progress: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val progress get() = _progress.asSharedFlow()



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
                    is CompletedOrdersIntent.Reorder -> {
                        reorder(intent.order)
                    }
                }
            }
        }
    }

    private suspend fun reorder(order: Order) = viewModelScope.launch(Dispatchers.IO) {
        _progress.value = true
        reorderUseCase.invoke(order).collect { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    Log.d(TAG, "Error: " + dataState.message)
                    _progress.value = false
                    //  _state.value = CompletedOrdersState.Error(dataState.message)
                }
                is DataState.Loading -> {
                    _progress.value = true
                    //     _state.value = CompletedOrdersState.Loading
                }
                is DataState.Success -> {
                    _progress.value = false
                    loadCompletedOrders()
                    //  _state.value = CompletedOrdersState.Success()
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