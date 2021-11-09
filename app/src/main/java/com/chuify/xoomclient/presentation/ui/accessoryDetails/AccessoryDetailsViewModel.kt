package com.chuify.xoomclient.presentation.ui.accessoryDetails

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

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "AccessoryDetailsViewMod"

@HiltViewModel
class AccessoryDetailsViewModel @Inject constructor(
    private val listAccessoriesUseCase: ListAccessoriesUseCase,
    private val insertOrUpdateAccessoryUseCase: IncreaseOrderUseCase,
    private val deleteOrUpdateAccessoryUseCase: DecreaseOrderUseCase,
) : ViewModel() {


    val userIntent = Channel<AccessoryDetailsIntent>(Channel.UNLIMITED)

    private val _state: MutableState<AccessoryDetailsState> =
        mutableStateOf(AccessoryDetailsState.Dismiss)
    val state get() = _state

    private lateinit var job: Job


    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {

            userIntent.consumeAsFlow().collect { intent ->
                Log.d(TAG, "handleIntent: $intent")
                when (intent) {
                    is AccessoryDetailsIntent.DecreaseAccessoryCart -> {
                        decreaseOrRemove(intent.accessory)
                    }
                    AccessoryDetailsIntent.DismissAcDetails -> {
                        dismiss()
                    }
                    is AccessoryDetailsIntent.IncreaseAccessoryCart -> {
                        insert(intent.accessory)
                    }
                    is AccessoryDetailsIntent.OpenAccessoryPreview -> {
                        loadAccessoryDetails(intent.accessory)
                    }
                }
            }

        }

    }

    private fun dismiss() = viewModelScope.launch(Dispatchers.IO) {
        job.cancel()
        _state.value = AccessoryDetailsState.Dismiss

    }

    private suspend
    fun insert(accessory: Accessory) = viewModelScope.launch(Dispatchers.IO) {

        insertOrUpdateAccessoryUseCase(
            image = accessory.image,
            name = accessory.name,
            id = accessory.id,
            basePrice = accessory.price
        ).collect { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    Log.d(TAG, "Error: " + dataState.message)
                    _state.value = AccessoryDetailsState.Error(dataState.message)
                }
                is DataState.Loading -> {

                }
                is DataState.Success -> {
                    Log.d(TAG, "Success: $dataState")
                }
            }
        }


    }

    private suspend
    fun decreaseOrRemove(accessory: Accessory) =
        viewModelScope.launch(Dispatchers.IO) {

            Log.d(TAG, "handleIntent: ")
            deleteOrUpdateAccessoryUseCase(accessory.id).collect { dataState ->
                when (dataState) {
                    is DataState.Error -> {
                        Log.d(TAG, "Error: " + dataState.message)
                        _state.value = AccessoryDetailsState.Error(dataState.message)
                    }
                    is DataState.Loading -> {
                    }
                    is DataState.Success -> {
                        Log.d(TAG, "Success: $dataState")
                    }
                }
            }


        }

    private suspend
    fun loadAccessoryDetails(accessory: Accessory) {
        job = viewModelScope.launch(Dispatchers.IO) {
            listAccessoriesUseCase().collect { dataState ->
                when (dataState) {
                    is DataState.Error -> {
                        Log.d(TAG, "Error: " + dataState.message)
                        _state.value =
                            AccessoryDetailsState.Error(dataState.message)
                    }
                    is DataState.Loading -> {
                    }
                    is DataState.Success -> {
                        dataState.data?.first { it.id == accessory.id }?.let {
                            Log.d(TAG, "loadAccessoryDetails: $it")
                            _state.value = AccessoryDetailsState.Success(data = it)
                        }

                    }
                }
            }

        }


    }

}