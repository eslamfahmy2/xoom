package com.chuify.xoomclient.presentation.ui.order.pending

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.xoomclient.domain.model.Order
import com.chuify.xoomclient.domain.usecase.order.CancelUseCase
import com.chuify.xoomclient.domain.usecase.order.GetPendingOrderListUseCase
import com.chuify.xoomclient.domain.utils.DataState
import com.chuify.xoomclient.presentation.ui.signup.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PendingOrdersViewModel @Inject constructor(
    private val getPendingOrderListUseCase: GetPendingOrderListUseCase,
    private val cancelUseCase: CancelUseCase,
) : ViewModel() {

    val userIntent = Channel<PendingOrdersIntent>(Channel.UNLIMITED)

    private val _state: MutableState<PendingOrdersState> =
        mutableStateOf(PendingOrdersState.Loading)
    val state get() = _state

    private val _cancelDialog: MutableState<Boolean> = mutableStateOf(false)
    val cancelDialog get() = _cancelDialog

    private val _progress: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val progress get() = _progress.asSharedFlow()

    private val _orderToCancel: MutableState<Order?> = mutableStateOf(null)

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
                    is PendingOrdersIntent.ShowCancel -> {
                        _orderToCancel.value = intent.order
                        _cancelDialog.value = true
                    }
                    is PendingOrdersIntent.Track -> {

                    }
                    is PendingOrdersIntent.ConfirmCancel -> {
                        _orderToCancel.value?.let {
                            cancelOrder(it, intent.reason)
                        }
                    }
                    PendingOrdersIntent.DismissCancel -> {
                        _orderToCancel.value = null
                        _cancelDialog.value = false
                    }
                }
            }
        }
    }

    private suspend fun cancelOrder(order: Order, reason: String) =
        viewModelScope.launch(Dispatchers.IO) {

            _cancelDialog.value = false
            cancelUseCase.invoke(order.id, reason).collect { dataState ->
                when (dataState) {
                    is DataState.Error -> {
                        Log.d(TAG, "Error: " + dataState.message)
                        _progress.value = false
                        //  _state.value = PendingOrdersState.Error(dataState.message)
                    }
                    is DataState.Loading -> {
                        _progress.value = true
                    }
                    is DataState.Success -> {
                        _progress.value = false
                        loadPendingOrders()
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
                    _progress.value = false
                }
            }
        }
    }


}