package com.chuify.xoomclient.presentation.ui.picklocation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.xoomclient.domain.usecase.location.GetLocationsUseCase
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
class PickLocationViewModel @Inject constructor(
    private val getLocationUseCase: GetLocationsUseCase
) : ViewModel() {

    val userIntent = Channel<PickLocationIntent>(Channel.UNLIMITED)

    private val _state: MutableState<PickLocationState> =
        mutableStateOf(PickLocationState.Loading)
    val state get() = _state


    init {
        handleIntent()
        viewModelScope.launch {
            loadLocations()
        }
    }

    private fun handleIntent() {
        viewModelScope.launch {

            userIntent.consumeAsFlow().collect { intent ->
                when (intent) {
                    PickLocationIntent.LoadLocations -> {
                        loadLocations()
                    }
                }
            }
        }
    }


    private suspend fun loadLocations() = viewModelScope.launch(Dispatchers.IO) {
        getLocationUseCase().collect { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    Log.d(TAG, "Error: " + dataState.message)
                    _state.value = PickLocationState.Error(dataState.message)
                }
                is DataState.Loading -> {
                    _state.value = PickLocationState.Loading
                }
                is DataState.Success -> {
                    dataState.data?.let {
                        _state.value = PickLocationState.Success(locations = it)
                    }

                }
            }
        }
    }


}