package com.chuify.xoomclient.presentation.ui.accessory

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.xoomclient.domain.model.Accessory
import com.chuify.xoomclient.domain.usecase.cart.DecreaseOrderUseCase
import com.chuify.xoomclient.domain.usecase.cart.IncreaseOrderUseCase
import com.chuify.xoomclient.domain.usecase.home.ListAccessoriesUseCase
import com.chuify.xoomclient.domain.utils.DataState
import com.chuify.xoomclient.presentation.ui.signup.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AccessoryViewModel @Inject constructor(
    private val listAccessoriesUseCase: ListAccessoriesUseCase,
    private val insertOrUpdateAccessoryUseCase: IncreaseOrderUseCase,
    private val deleteOrUpdateAccessoryUseCase: DecreaseOrderUseCase,
) : ViewModel() {

    val userIntent = Channel<AccessoryIntent>(Channel.UNLIMITED)

    private val _state: MutableState<AccessoryState> = mutableStateOf(AccessoryState.Loading)
    val state get() = _state

    private val _searchText: MutableState<String> = mutableStateOf(String())

    private val _accessoryList: MutableState<List<Accessory>> = mutableStateOf(listOf())


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


    private suspend fun loadAccessories() {
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
                        data = dataState.data ?: listOf())
                }
            }
        }
    }

    private suspend fun insert(accessory: Accessory) {

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

    private suspend fun decreaseOrRemove(accessory: Accessory) {

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