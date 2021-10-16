package com.chuify.xoomclient.presentation.ui.accessoryDetails

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.xoomclient.domain.model.Accessory
import com.chuify.xoomclient.domain.usecase.cart.DeleteOrUpdateAccessoryUseCase
import com.chuify.xoomclient.domain.usecase.cart.InsertOrUpdateAccessoryUseCase
import com.chuify.xoomclient.domain.usecase.home.GetAccessoryDetailsUseCase
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
class AccessoryDetailsViewModel @Inject constructor(
    private val getAccessoryDetailsUseCase: GetAccessoryDetailsUseCase,
    private val insertOrUpdateAccessoryUseCase: InsertOrUpdateAccessoryUseCase,
    private val deleteOrUpdateAccessoryUseCase: DeleteOrUpdateAccessoryUseCase,
) : ViewModel() {


    val userIntent = Channel<AccessoryDetailsIntent>(Channel.UNLIMITED)

    private val _state: MutableState<AccessoryDetailsState> =
        mutableStateOf(AccessoryDetailsState.Dismiss)
    val state get() = _state

    val ace = Accessory(
        name = "Bunner",
        id = "351",
        image = "https://xoom-prod.s3.eu-west-2.amazonaws.com/Bunner/burner1.jpg")

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {

            userIntent.consumeAsFlow().collect { intent ->
                Log.d(TAG, "handleIntent: ")
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
        _state.value = AccessoryDetailsState.Dismiss
    }

    private suspend
    fun insert(accessory: Accessory) = viewModelScope.launch(Dispatchers.IO) {

        insertOrUpdateAccessoryUseCase(accessory).collect { dataState ->
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
            deleteOrUpdateAccessoryUseCase(accessory).collect { dataState ->
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
    fun loadAccessoryDetails(accessory: Accessory) =
        viewModelScope.launch(Dispatchers.IO) {
            getAccessoryDetailsUseCase(accessory).collect { dataState ->
                when (dataState) {
                    is DataState.Error -> {
                        Log.d(TAG, "Error: " + dataState.message)
                        _state.value =
                            AccessoryDetailsState.Error(dataState.message)
                    }
                    is DataState.Loading -> {
                    }
                    is DataState.Success -> {
                        dataState.data?.let {
                            Log.d(TAG, "loadAccessoryDetails: $it")
                            _state.value = AccessoryDetailsState.Success(data = it)
                        }
                    }
                }
            }

        }


}