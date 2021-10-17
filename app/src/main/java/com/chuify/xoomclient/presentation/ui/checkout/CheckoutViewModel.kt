package com.chuify.xoomclient.presentation.ui.checkout

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.xoomclient.domain.model.Cart
import com.chuify.xoomclient.domain.model.Location
import com.chuify.xoomclient.domain.model.Payments
import com.chuify.xoomclient.domain.usecase.cart.DeleteOrderUs
import com.chuify.xoomclient.domain.usecase.cart.GetCartItemsUseCase
import com.chuify.xoomclient.domain.usecase.cart.OrderITemAction
import com.chuify.xoomclient.domain.usecase.cart.UpdateOrderUs
import com.chuify.xoomclient.domain.usecase.location.GetLocationsUseCase
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
class CheckoutViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val updateOrderUs: UpdateOrderUs,
    private val deleteOrderUs: DeleteOrderUs,
    private val getLocationUseCase: GetLocationsUseCase,
) : ViewModel() {

    val userIntent = Channel<CheckoutIntent>(Channel.UNLIMITED)

    private val _state: MutableState<CheckoutState> =
        mutableStateOf(CheckoutState.Loading)
    val state get() = _state

    private val _paymentMethod: MutableState<Payments> = mutableStateOf(Payments.Mpesa)
    val paymentMethod get() = _paymentMethod

    private val _location: MutableState<Location?> = mutableStateOf(null)
    val location get() = _location

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {

            userIntent.consumeAsFlow().collect { intent ->
                when (intent) {
                    CheckoutIntent.LoadCart -> {
                        loadOrders()
                    }
                    is CheckoutIntent.DecreaseItem -> {
                        decrease(intent.order, OrderITemAction.Decrease)
                    }
                    is CheckoutIntent.DeleteItem -> {
                        delete(intent.order)
                    }
                    is CheckoutIntent.IncreaseItem -> {
                        increase(intent.order, OrderITemAction.Increase)
                    }
                    is CheckoutIntent.ChangePayment -> {
                        changePayment(intent.payment)
                    }
                    CheckoutIntent.ConfirmOrder -> TODO()
                    is CheckoutIntent.OnLocationSelect -> {
                        Log.d(TAG, "OnLocationSelect: " + intent.location)
                    }
                }
            }
        }
    }


    private fun changePayment(payments: Payments) {
        _paymentMethod.value = payments
    }

    private suspend fun loadOrders() = viewModelScope.launch(Dispatchers.IO) {
        getCartItemsUseCase().collect { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    Log.d(TAG, "Error: " + dataState.message)
                    _state.value = CheckoutState.Error(dataState.message)
                }
                is DataState.Loading -> {
                    _state.value = CheckoutState.Loading
                }
                is DataState.Success -> {
                    dataState.data?.let {
                        _state.value =
                            CheckoutState.Success(orders = it.first, cartPreview = it.second)
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
                        _state.value = CheckoutState.Error(dataState.message)
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
                        _state.value = CheckoutState.Error(dataState.message)
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
                    _state.value = CheckoutState.Error(dataState.message)
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