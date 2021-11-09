package com.chuify.xoomclient.presentation.ui.checkout

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.xoomclient.domain.model.Cart
import com.chuify.xoomclient.domain.model.CartPreview
import com.chuify.xoomclient.domain.model.Location
import com.chuify.xoomclient.domain.model.Payments
import com.chuify.xoomclient.domain.usecase.cart.DecreaseOrderUseCase
import com.chuify.xoomclient.domain.usecase.cart.GetCartItemsUseCase
import com.chuify.xoomclient.domain.usecase.cart.IncreaseOrderUseCase
import com.chuify.xoomclient.domain.usecase.cart.SubmitOrderUseCase
import com.chuify.xoomclient.domain.usecase.location.GetLocationsUseCase
import com.chuify.xoomclient.domain.utils.DataState

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "CheckoutViewModel"

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val decreaseOrderUseCase: DecreaseOrderUseCase,
    private val increaseOrderUseCase: IncreaseOrderUseCase,
    private val getLocationUseCase: GetLocationsUseCase,
    private val submitOrderUseCase: SubmitOrderUseCase
) : ViewModel() {

    val userIntent = Channel<CheckoutIntent>(Channel.UNLIMITED)

    private val _state: MutableStateFlow<CheckoutState> =
        MutableStateFlow(CheckoutState.Loading)
    val state get() = _state.asStateFlow()

    private val _orders: MutableStateFlow<Pair<List<Cart>, CartPreview>> = MutableStateFlow(
        Pair(
            emptyList(), CartPreview()
        )
    )
    val orders get() = _orders.asStateFlow()

    private val _paymentMethod: MutableStateFlow<Payments> =
        MutableStateFlow(Payments.CashOnDelivery)
    val paymentMethod get() = _paymentMethod.asStateFlow()

    private val _location: MutableStateFlow<List<Location>> = MutableStateFlow(listOf())
    val location get() = _location.asStateFlow()

    private val _totalPrice: MutableStateFlow<Int> = MutableStateFlow(0)
    val totalPrice get() = _totalPrice.asStateFlow()


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
                        decrease(intent.order)
                    }
                    is CheckoutIntent.IncreaseItem -> {
                        increase(intent.order)
                    }
                    is CheckoutIntent.ChangePayment -> {
                        changePayment(intent.payment)
                    }
                    CheckoutIntent.ConfirmOrder -> {
                        submitOrder()
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
                    is CheckoutIntent.ChangeStatus -> {
                        _state.value = CheckoutState.Success
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
                      //  _state.value = CheckoutState.Loading
                    }
                    is DataState.Success -> {
                        Log.d(TAG, "Success: location " + dataState.data)
                        dataState.data?.let {
                            _location.value = it
                        }

                    }
                }
            }
        }
    }

    private suspend fun submitOrder() = viewModelScope.launch(Dispatchers.IO) {
        submitOrderUseCase.invoke(
            _paymentMethod.value,
            _location.value,
            _orders.value.first,
        ).collect { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    Log.d(TAG, "Error: " + dataState.message)
                    _state.value = CheckoutState.Error(dataState.message)
                }
                is DataState.Loading -> {
                    _state.value = CheckoutState.LoadingSubmit
                }
                is DataState.Success -> {
                    _state.value = CheckoutState.OrderSubmitted
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
                        _orders.value = it
                        _state.value =
                            CheckoutState.Success
                    }

                }
            }
        }
    }

    private suspend fun increase(order: Cart) =
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

    private suspend fun decrease(order: Cart) =
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