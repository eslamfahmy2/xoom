package com.chuify.cleanxoomclient.presentation.ui.locations

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.cleanxoomclient.domain.repository.LocationRepo
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

private const val TAG = "LocationsViewModel"

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val getLocationUseCase: GetLocationsUseCase,
    private val saveLocationsUseCase: SaveLocationsUseCase,
    private val locationRepo: LocationRepo
) : ViewModel() {

    val userIntent = Channel<LocationsIntent>(Channel.UNLIMITED)

    private val _state: MutableStateFlow<LocationsState> =
        MutableStateFlow(LocationsState.Loading)
    val state get() = _state.asStateFlow()

    private val _showDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showDialog get() = _showDialog.asStateFlow()

    private val _title: MutableStateFlow<String> = MutableStateFlow(String())
    val title get() = _title.asStateFlow()

    private val _details: MutableStateFlow<String> = MutableStateFlow(String())
    val details get() = _details.asStateFlow()

    private val _instructions: MutableStateFlow<String> = MutableStateFlow(String())
    val instructions get() = _instructions.asStateFlow()


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
                    is LocationsIntent.DetailsChange -> {
                        _details.value = intent.data
                    }
                    is LocationsIntent.InstructionsChange -> {
                        _instructions.value = intent.data
                    }
                    LocationsIntent.SaveAddress -> {
                        _showDialog.value = false
                        saveLocation(
                            addressUrl = _title.value,
                            details = _details.value,
                            instructions = _instructions.value
                        )
                    }
                    is LocationsIntent.TitleChange -> {
                        _title.value = intent.data
                    }
                    LocationsIntent.DismissDialog -> {
                        _showDialog.value = false
                    }
                    LocationsIntent.ShowDialog -> {
                        _showDialog.value = true
                    }
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
            saveLocationsUseCase(
                addressUrl = addressUrl,
                details = details,
                instructions = instructions,
                lat = 0.0,
                lng = 0.0,
            ).collect { dataState ->
                when (dataState) {
                    is DataState.Error -> {
                        Log.d(TAG, "Error: " + dataState.message)
                        _state.value = LocationsState.Error(dataState.message)
                    }
                    is DataState.Loading -> {
                        _state.value = LocationsState.Loading
                    }
                    is DataState.Success -> {
                        Log.d(TAG, "Success: location $dataState")

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

    fun delete(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val res = locationRepo.deleteAddress(id)) {
                is ResponseState.Error -> {
                    Log.d(TAG, "Error: " + res.message)
                    _state.value = LocationsState.Error(res.message)
                }
                is ResponseState.Success -> {
                    loadLocations()
                }
            }
        }

    }


}