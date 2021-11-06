package com.chuify.xoomclient.presentation.ui.locations

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.xoomclient.domain.usecase.location.GetLocationsUseCase
import com.chuify.xoomclient.domain.usecase.location.SaveLocationsUseCase
import com.chuify.xoomclient.domain.utils.DataState
import com.chuify.xoomclient.presentation.ui.signup.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val getLocationUseCase: GetLocationsUseCase,
    private val saveLocationsUseCase: SaveLocationsUseCase,
) : ViewModel() {

    val userIntent = Channel<LocationsIntent>(Channel.UNLIMITED)

    private val _state: MutableStateFlow<LocationsState> =
        MutableStateFlow(LocationsState.Loading)
    val state get() = _state.asStateFlow()


    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {

            userIntent.consumeAsFlow().collect { intent ->
                Log.d(TAG, "handleIntent:$intent")
                when (intent) {
                    LocationsIntent.LoadLocations -> {
                        loadLocations()
                    }
                }
            }
        }
    }


    private suspend fun loadLocations() {
        viewModelScope.launch(Dispatchers.IO) {
            getLocationUseCase().collect { dataState ->
                when (dataState) {
                    is DataState.Error -> {
                        Log.d(TAG, "Error: " + dataState.message)
                        _state.value = LocationsState.Error(dataState.message)
                    }
                    is DataState.Loading -> {
                        _state.value = LocationsState.Loading
                    }
                    is DataState.Success -> {
                        Log.d(TAG, "Success: location " + dataState.data)
                        dataState.data?.let {
                            _state.value = LocationsState.Success(it)
                        }

                    }
                }
            }
        }

    }


}