package com.chuify.xoomclient.presentation.ui.vendors

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.xoomclient.domain.model.Accessory
import com.chuify.xoomclient.domain.model.Vendor
import com.chuify.xoomclient.domain.usecase.home.ListAccessoriesUseCase
import com.chuify.xoomclient.domain.usecase.home.ListVendorsUseCase
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
class VendorViewModel @Inject constructor(
    private val useCase: ListVendorsUseCase,
    private val useCaseAccessoriesUseCase: ListAccessoriesUseCase,
) : ViewModel() {


    val userIntent = Channel<VendorIntent>(Channel.UNLIMITED)

    private val _state: MutableState<VendorState> = mutableStateOf(VendorState.Loading)
    val state get() = _state


    private val _searchText: MutableState<String> = mutableStateOf(String())

    private val _vendors: MutableState<List<Vendor>> = mutableStateOf(listOf())

    private val _accessories: MutableState<List<Accessory>> = mutableStateOf(listOf())
    private val _accessoriesFilter: MutableState<List<Accessory>> = mutableStateOf(listOf())
    val accessories get() = _accessories


    init {
        handleIntent()
        viewModelScope.launch {
            loadVendors()
            loadAccessories()
        }
    }

    private fun handleIntent() {
        viewModelScope.launch {

            userIntent.consumeAsFlow().collect { intent ->
                Log.d(TAG, "handleIntent: ")
                when (intent) {
                    VendorIntent.LoadVendors -> {
                        loadVendors()
                    }
                    is VendorIntent.Filter -> {
                        filter(intent.searchText)
                    }
                }
            }

        }

    }

    private suspend fun loadVendors() = viewModelScope.launch(Dispatchers.IO) {
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
                        searchText = _searchText.value)
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