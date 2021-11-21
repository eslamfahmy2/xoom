package com.chuify.cleanxoomclient.presentation.ui.accessory

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.cleanxoomclient.domain.model.Accessory
import com.chuify.cleanxoomclient.domain.usecase.cart.DecreaseOrderUseCase
import com.chuify.cleanxoomclient.domain.usecase.cart.IncreaseOrderUseCase
import com.chuify.cleanxoomclient.domain.usecase.home.ListAccessoriesUseCase
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


private const val TAG = "AccessoryViewModel"

@HiltViewModel
class AccessoryViewModel @Inject constructor(
    private val listAccessoriesUseCase: ListAccessoriesUseCase,
    private val insertOrUpdateAccessoryUseCase: IncreaseOrderUseCase,
    private val deleteOrUpdateAccessoryUseCase: DecreaseOrderUseCase,
) : ViewModel() {

    val userIntent = Channel<AccessoryIntent>(Channel.UNLIMITED)

    private val _state: MutableStateFlow<AccessoryState> = MutableStateFlow(AccessoryState.Loading)
    val state get() = _state.asStateFlow()

    private val _searchText: MutableStateFlow<String> = MutableStateFlow(String())

    private val _accessoryList: MutableStateFlow<List<Accessory>> = MutableStateFlow(listOf())


    init {
        handleIntent()
        viewModelScope.launch {
            loadAccessories()
        }
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect { intent ->
                when (intent) {
                    AccessoryIntent.LoadVendors -> {
                        loadAccessories()
                    }
                    is AccessoryIntent.Filter -> {
                        filter(intent.searchText)
                    }
                    is AccessoryIntent.DecreaseAccessoryCart -> {
                        decreaseOrRemove(intent.accessory)
                    }
                    is AccessoryIntent.IncreaseAccessoryCart -> {
                        insert(intent.accessory)
                    }
                }
            }
        }
    }

    private fun filter(searchText: String) {

        _searchText.value = searchText
        val list = _accessoryList.value.filter {
            it.name.lowercase().contains(_searchText.value.lowercase())
        }
        _state.value = AccessoryState.Success(data = list)
    }


    private suspend fun loadAccessories() = viewModelScope.launch(Dispatchers.IO) {
        listAccessoriesUseCase().collect { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    Log.d(TAG, "Error: " + dataState.message)
                    _state.value = AccessoryState.Error(dataState.message)
                }
                is DataState.Loading -> {
                    _state.value = AccessoryState.Loading
                }
                is DataState.Success -> {
                    _accessoryList.value = dataState.data ?: listOf()
                    _state.value = AccessoryState.Success(
                        data = dataState.data ?: listOf()
                    )
                }
            }
        }
    }

    private suspend fun insert(accessory: Accessory) = viewModelScope.launch(Dispatchers.IO) {

        insertOrUpdateAccessoryUseCase(
            image = accessory.image,
            name = accessory.name,
            id = accessory.id,
            basePrice = accessory.price
        ).collect { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    Log.d(TAG, "Error: " + dataState.message)
                    _state.value = AccessoryState.Error(dataState.message)
                }
                is DataState.Loading -> {
                }
                is DataState.Success -> {
                    Log.d(TAG, "Success: $dataState")
                }
            }
        }


    }

    private suspend fun decreaseOrRemove(accessory: Accessory) =
        viewModelScope.launch(Dispatchers.IO) {

            Log.d(TAG, "handleIntent: ")
            deleteOrUpdateAccessoryUseCase(accessory.id).collect { dataState ->
                when (dataState) {
                    is DataState.Error -> {
                        Log.d(TAG, "Error: " + dataState.message)
                        _state.value = AccessoryState.Error(dataState.message)
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