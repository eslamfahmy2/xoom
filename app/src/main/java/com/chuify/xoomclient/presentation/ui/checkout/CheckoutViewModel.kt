package com.chuify.xoomclient.presentation.ui.checkout

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.xoomclient.domain.model.Cart
import com.chuify.xoomclient.domain.model.Location
import com.chuify.xoomclient.domain.model.Payments
import com.chuify.xoomclient.domain.usecase.cart.DecreaseOrderUseCase
import com.chuify.xoomclient.domain.usecase.cart.GetCartItemsUseCase
import com.chuify.xoomclient.domain.usecase.cart.IncreaseOrderUseCase
import com.chuify.xoomclient.domain.usecase.cart.OrderITemAction
import com.chuify.xoomclient.domain.usecase.location.GetLocationsUseCase
import com.chuify.xoomclient.domain.usecase.location.SaveLocationsUseCase
import com.chuify.xoomclient.domain.utils.DataState
import com.chuify.xoomclient.presentation.ui.signup.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val decreaseOrderUseCase: DecreaseOrderUseCase,
    private val increaseOrderUseCase: IncreaseOrderUseCase,
    private val getLocationUseCase: GetLocationsUseCase,
    private val saveLocationsUseCase: SaveLocationsUseCase,
) : ViewModel() {

    val userIntent = Channel<CheckoutIntent>(Channel.UNLIMITED)

    private val _state: MutableState<CheckoutState> =
        mutableStateOf(CheckoutState.Loading)
    val state get() = _state

    private val _paymentMethod: MutableState<Payments> = mutableStateOf(Payments.CashOnDelivery)
    val paymentMethod get() = _paymentMethod

    private val _location: MutableState<List<Location>> = mutableStateOf(listOf())
    val location get() = _location

    private val _totalPrice: MutableState<Int> = mutableStateOf(0)
    val totalPrice get() = _totalPrice


    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {

            userIntent.consumeAsFlow().collect { intent ->
                Log.d(TAG, "handleIntent:$intent")
                when (intent) {
                    CheckoutIntent.LoadCart -> {
                        loadOrders()
                        loadLocations()
                    }
                    is CheckoutIntent.DecreaseItem -> {
                        decrease(intent.order, OrderITemAction.Decrease)
                    }
                    is CheckoutIntent.IncreaseItem -> {
                        increase(intent.order, OrderITemAction.Increase)
                    }
                    is CheckoutIntent.ChangePayment -> {
                        changePayment(intent.payment)
                    }
                    CheckoutIntent.ConfirmOrder -> {

                    }
                    is CheckoutIntent.OnLocationSelect -> {
                        Log.d(TAG, "handleIntent: " + intent.id)
                        val res = _location.value.map {
                            if (it.id == intent.id) {
                                it.copy(selected = true)
                            } else {
                                it.copy(selected = false)
                            }
                        }
                        Log.d(TAG, "handleIntent: $res")
                        _location.value = res
                    }
                }
            }
        }
    }


    private fun changePayment(payments: Payments) {
        _paymentMethod.value = payments
    }

    private suspend fun loadLocations() {
        if (_location.value.isNotEmpty())
            return
        viewModelScope.launch(Dispatchers.IO) {
            getLocationUseCase().collect { dataState ->
                when (dataState) {
                    is DataState.Error -> {
                        Log.d(TAG, "Error: " + dataState.message)
                        //    _state.value = CheckoutState.Error(dataState.message)
                    }
                    is DataState.Loading -> {
                        _state.value = CheckoutState.Loading
                    }
                    is DataState.Success -> {
                        Log.d(TAG, "Success: location " + dataState.data)
                        dataState.data?.let {
                            _location.value = it
                            cancel()
                        }

                    }
                }
            }
        }
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
            increaseOrderUseCase(
                image = order.image,
                name = order.name,
                id = order.id,
                basePrice = order.basePrice
            ).collect { dataState ->
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
            decreaseOrderUseCase(order.id).collect { dataState ->
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