package com.chuify.cleanxoomclient.presentation.ui.checkout

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.cleanxoomclient.domain.model.Cart
import com.chuify.cleanxoomclient.domain.model.CartPreview
import com.chuify.cleanxoomclient.domain.model.Location
import com.chuify.cleanxoomclient.domain.model.Payments
import com.chuify.cleanxoomclient.domain.repository.LocationRepo
import com.chuify.cleanxoomclient.domain.usecase.cart.*
import com.chuify.cleanxoomclient.domain.usecase.location.GetLocationsUseCase
import com.chuify.cleanxoomclient.domain.usecase.location.SaveLocationsUseCase
import com.chuify.cleanxoomclient.domain.utils.DataState
import com.chuify.cleanxoomclient.domain.utils.ResponseState
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
    private val submitOrderUseCase: SubmitOrderUseCase,
    private val saveLocationsUseCase: SaveLocationsUseCase,
    private val locationRepo: LocationRepo
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
        MutableStateFlow(Payments.MPESA)
    val paymentMethod get() = _paymentMethod.asStateFlow()

    private val _location: MutableStateFlow<List<Location>> = MutableStateFlow(listOf())
    val location get() = _location.asStateFlow()

    private val _totalPrice: MutableStateFlow<Int> = MutableStateFlow(0)
    val totalPrice get() = _totalPrice.asStateFlow()

    val show: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val selectedLocation: MutableStateFlow<Location?> = MutableStateFlow(null)

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
                        res.firstOrNull { it.selected }?.let {
                            selectedLocation.value = it
                        }
                    }
                    is CheckoutIntent.ChangeStatus -> {
                        _state.value = CheckoutState.Success.OrderSubmitted()
                    }
                    is CheckoutIntent.SaveAddress -> {
                        saveLocation(intent.title, intent.details, intent.instructions)
                    }
                }
            }
        }
    }

    fun delete(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            loading.value = true
            when (val res = locationRepo.deleteAddress(id)) {
                is ResponseState.Error -> {
                    loading.value = false
                    // _state.value = LocationsState.Error(res.message)
                }
                is ResponseState.Success -> {
                    loading.value = false
                    loadLocations()
                }
            }
        }
    }


    private suspend fun saveLocation(
        addressUrl: String,
        details: String,
        instructions: String,
    ) {

        viewModelScope.launch(Dispatchers.IO) {
            loading.value = true
            saveLocationsUseCase(
                addressUrl = addressUrl,
                details = details,
                instructions = instructions,
                lat = 0.0,
                lng = 0.0,
            ).collect { dataState ->
                loading.value = false
                show.value = false
                when (dataState) {
                    is DataState.Error -> {
                        Log.d(TAG, "Error: " + dataState.message)

                    }
                    is DataState.Loading -> {

                    }
                    is DataState.Success -> {
                        Log.d(TAG, "Success: location $dataState")

                        loadLocations()

                    }
                }
            }
        }

    }


    private fun changePayment(payments: Payments) {
        _paymentMethod.value = payments
    }

    private suspend fun loadLocations() {
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
            selectedLocation.value,
            _orders.value.first,
        ).collect { dataState ->
            when (dataState) {
                is SubmitOrderStatus.Loading -> {
                    _state.value = CheckoutState.Loading
                }
                is SubmitOrderStatus.OrderSubmitted -> {
                    Log.d(TAG, "Error: " + dataState.msg)
                    _state.value = CheckoutState.Success.OrderSubmitted(dataState.msg)
                }
                is SubmitOrderStatus.PaymentFail -> {
                    _state.value = CheckoutState.Error.PaymentError(dataState.msg)
                }
                is SubmitOrderStatus.PaymentSuccess -> {
                    Log.d(TAG, "Error: " + dataState.msg)
                    _state.value = CheckoutState.Success.PaymentSuccess(dataState.msg)

                }
            }
        }
    }

    private suspend fun loadOrders() = viewModelScope.launch(Dispatchers.IO) {
        getCartItemsUseCase().collect { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    Log.d(TAG, "Error: " + dataState.message)
                    //   _state.value = CheckoutState.Error.SubmitOrderError(dataState.message)
                }
                is DataState.Loading -> {
                    // _state.value = CheckoutState.Loading
                }
                is DataState.Success -> {
                    dataState.data?.let {
                        _orders.value = it
                        _state.value =
                            CheckoutState.Success.OrderSubmitted()
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
            ).collect()

        }

    private suspend fun decrease(order: Cart) =
        viewModelScope.launch(Dispatchers.IO) {
            decreaseOrderUseCase(order.id).collect()
        }


}