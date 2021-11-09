package com.chuify.xoomclient.presentation.ui.vendors

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.xoomclient.domain.model.Accessory
import com.chuify.xoomclient.domain.model.Vendor
import com.chuify.xoomclient.domain.usecase.cart.CartPreviewUC
import com.chuify.xoomclient.domain.usecase.home.ListAccessoriesUseCase
import com.chuify.xoomclient.domain.usecase.home.ListVendorsUseCase
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

private const val TAG = "VendorViewModel"

@HiltViewModel
class VendorViewModel @Inject constructor(
    private val useCase: ListVendorsUseCase,
    private val useCaseAccessoriesUseCase: ListAccessoriesUseCase,
    private val cartPreviewUC: CartPreviewUC
) : ViewModel() {


    val userIntent = Channel<VendorIntent>(Channel.UNLIMITED)

    private val _state: MutableState<VendorState> = mutableStateOf(VendorState.Loading)
    val state get() = _state


    private val _searchText: MutableState<String> = mutableStateOf(String())

    private val _vendors: MutableState<List<Vendor>> = mutableStateOf(listOf())

    private val _accessories: MutableState<List<Accessory>> = mutableStateOf(listOf())
    private val _accessoriesFilter: MutableState<List<Accessory>> = mutableStateOf(listOf())
    val accessories get() = _accessories


    private val _cartCount: MutableState<Int> = mutableStateOf(0)
    val cartCount get() = _cartCount

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