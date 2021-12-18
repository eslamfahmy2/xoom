package com.chuify.cleanxoomclient.presentation.ui.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.cleanxoomclient.domain.model.Cart
import com.chuify.cleanxoomclient.domain.model.CartPreview
import com.chuify.cleanxoomclient.domain.usecase.cart.DeleteOrderUs
import com.chuify.cleanxoomclient.domain.usecase.cart.GetCartItemsUseCase
import com.chuify.cleanxoomclient.domain.usecase.cart.OrderITemAction
import com.chuify.cleanxoomclient.domain.usecase.cart.UpdateOrderUs
import com.chuify.cleanxoomclient.domain.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "CartViewModel"

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val updateOrderUs: UpdateOrderUs,
    private val deleteOrderUs: DeleteOrderUs,
) : ViewModel() {

    val userIntent = Channel<CartIntent>(Channel.UNLIMITED)

    private val _state: MutableStateFlow<CartState> =
        MutableStateFlow(CartState.Loading)
    val state get() = _state.asStateFlow()

    val preview: MutableStateFlow<CartPreview> = MutableStateFlow(CartPreview())

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

    private suspend fun loadOrders() = viewModelScope.launch((Dispatchers.IO)) {

        getCartItemsUseCase().collect { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    Log.d(TAG, "Error: " + dataState.message)
                    preview.value = CartPreview()
                    _state.value = CartState.Error(dataState.message)
                }
                is DataState.Loading -> {
                    _state.value = CartState.Loading
                }
                is DataState.Success -> {
                    dataState.data?.let {
                        preview.value = it.second
                        _state.value = CartState.Success(orders = it.first, cartPreview = it.second)
                    }

                }
            }
        }
    }

    private suspend fun increase(order: Cart, action: OrderITemAction) =
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

    private suspend fun decrease(order: Cart, action: OrderITemAction) =
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


    private suspend fun delete(order: Cart) = viewModelScope.launch(Dispatchers.IO) {

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