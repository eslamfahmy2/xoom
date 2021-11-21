package com.chuify.cleanxoomclient.presentation.ui.vendors

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.cleanxoomclient.domain.model.Accessory
import com.chuify.cleanxoomclient.domain.model.Vendor
import com.chuify.cleanxoomclient.domain.usecase.cart.CartPreviewUC
import com.chuify.cleanxoomclient.domain.usecase.home.ListAccessoriesUseCase
import com.chuify.cleanxoomclient.domain.usecase.home.ListVendorsUseCase
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

private const val TAG = "VendorViewModel"

@HiltViewModel
class VendorViewModel @Inject constructor(
    private val useCase: ListVendorsUseCase,
    private val useCaseAccessoriesUseCase: ListAccessoriesUseCase,
    private val cartPreviewUC: CartPreviewUC
) : ViewModel() {


    val userIntent = Channel<VendorIntent>(Channel.UNLIMITED)

    private val _state: MutableStateFlow<VendorState> = MutableStateFlow(VendorState.Loading)
    val state get() = _state.asStateFlow()


    private val _searchText: MutableStateFlow<String> = MutableStateFlow(String())

    private val _vendors: MutableStateFlow<List<Vendor>> = MutableStateFlow(listOf())

    private val _accessories: MutableStateFlow<List<Accessory>> = MutableStateFlow(listOf())
    private val _accessoriesFilter: MutableStateFlow<List<Accessory>> = MutableStateFlow(listOf())
    val accessories get() = _accessories.asStateFlow()


    private val _cartCount: MutableStateFlow<Int> = MutableStateFlow(0)
    val cartCount get() = _cartCount.asStateFlow()

    private val _refreshing: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val refreshing get() = _refreshing.asStateFlow()

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {

            userIntent.consumeAsFlow().collect { intent ->
                Log.d(TAG, "handleIntent: ")
                when (intent) {
                    VendorIntent.LoadVendors -> {
                        loadVendors()
                        loadAccessories()
                        cartPref()
                    }
                    is VendorIntent.Filter -> {
                        filter(intent.searchText)
                    }
                }
            }

        }

    }

    private suspend fun loadVendors() = viewModelScope.launch(Dispatchers.IO) {
        _refreshing.value = true
        useCase().collect { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    Log.d(TAG, "Error: " + dataState.message)
                    _state.value = VendorState.Error(dataState.message)
                }
                is DataState.Loading -> {
                    _state.value = VendorState.Loading
                }
                is DataState.Success -> {
                    _vendors.value = dataState.data ?: listOf()
                    _state.value = VendorState.Success(
                        data = dataState.data ?: listOf(),
                        searchText = _searchText.value
                    )
                }
            }
            _refreshing.value = false
        }
    }

    private suspend fun cartPref() = viewModelScope.launch(Dispatchers.IO) {

        cartPreviewUC().collect { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    Log.d(TAG, "Error: " + dataState.message)
                }
                is DataState.Loading -> {
                }
                is DataState.Success -> {
                    dataState.data?.let {
                        _cartCount.value = it.totalQuantity
                    }

                }
            }
        }

    }

    private suspend fun loadAccessories() = viewModelScope.launch(Dispatchers.IO) {

        useCaseAccessoriesUseCase().collect { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    Log.d(TAG, "Error: " + dataState.message)
                    _state.value = VendorState.Error(dataState.message)
                }
                is DataState.Loading -> {
                }
                is DataState.Success -> {
                    val res = dataState.data ?: listOf()
                    _accessoriesFilter.value = res
                    _accessories.value = res

                }
            }
        }

    }

    private fun filter(searchText: String) = viewModelScope.launch(Dispatchers.IO) {

        _searchText.value = searchText

        val list = _vendors.value.filter {
            it.name.lowercase().contains(_searchText.value.lowercase())
        }

        _accessories.value = _accessoriesFilter.value.filter {
            it.name.lowercase().contains(_searchText.value.lowercase())
        }

        _state.value =
            VendorState.Success(
                data = list,
                searchText = _searchText.value
            )


    }


}