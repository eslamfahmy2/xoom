package com.chuify.xoomclient.presentation.ui.cart

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.xoomclient.domain.model.Order
import com.chuify.xoomclient.domain.usecase.cart.DeleteOrderUs
import com.chuify.xoomclient.domain.usecase.cart.GetCartItemsUseCase
import com.chuify.xoomclient.domain.usecase.cart.OrderITemAction
import com.chuify.xoomclient.domain.usecase.cart.UpdateOrderUs
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
class CartViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val updateOrderUs: UpdateOrderUs,
    private val deleteOrderUs: DeleteOrderUs,
) : ViewModel() {

    val userIntent = Channel<CartIntent>(Channel.UNLIMITED)

    private val _state: MutableState<CartState> =
        mutableStateOf(CartState.Loading)
    val state get() = _state

    init {
        handleIntent()
        viewModelScope.launch {
            loadOrders()
        }
    }

    private fun handleIntent() {
        viewModelScope.launch {

            userIntent.consumeAsFlow().collect { intent ->
                when (intent) {
                    CartIntent.LoadCart -> {
                        loadOrders()
                    }
                    is CartIntent.DecreaseItem -> {
                        decrease(intent.order, OrderITemAction.Decrease)
                    }
                    is CartIntent.DeleteItem -> {
                        delete(intent.order)
                    }
                    is CartIntent.IncreaseItem -> {
                        increase(intent.order, OrderITemAction.Increase)
                    }
                }
            }
        }
    }

    private suspend fun loadOrders() {

        getCartItemsUseCase().collect { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    Log.d(TAG, "Error: " + dataState.message)
                    _state.value = CartState.Error(dataState.message)
                }
                is DataState.Loading -> {
                    _state.value = CartState.Loading
                }
                is DataState.Success -> {
                    dataState.data?.let {
                        _state.value = CartState.Success(orders = it.first, cartPreview = it.second)
                    }

                }
            }
        }
    }

    private suspend fun increase(order: Order, action: OrderITemAction) =
        viewModelScope.launch(Dispatchers.IO) {

            updateOrderUs(order, action).collect { dataState ->
                when (dataState) {
                    is DataState.Error -> {
                        Log.d(TAG, "Error: " + dataState.message)
                        _state.value = CartState.Error(dataState.message)
                    }
                    is DataState.Loading -> {

                    }
                    is DataState.Success -> {
                        Log.d(TAG, "Success: $dataState")
                    }
                }
            }


        }

    private suspend fun decrease(order: Order, action: OrderITemAction) =
        viewModelScope.launch(Dispatchers.IO) {

            updateOrderUs(order, action).collect { dataState ->
                when (dataState) {
                    is DataState.Error -> {
                        Log.d(TAG, "Error: " + dataState.message)
                        _state.value = CartState.Error(dataState.message)
                    }
                    is DataState.Loading -> {

                    }
                    is DataState.Success -> {
                        Log.d(TAG, "Success: $dataState")
                    }
                }
            }


        }


    private suspend fun delete(order: Order) = viewModelScope.launch(Dispatchers.IO) {

        deleteOrderUs(order).collect { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    Log.d(TAG, "Error: " + dataState.message)
                    _state.value = CartState.Error(dataState.message)
                }
                is DataState.Loading -> {

                }
                is DataState.Success -> {
                    Log.d(TAG, "Success: $dataState")
                }
            }
        }


    }


}