package com.chuify.cleanxoomclient.presentation.ui.track

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.cleanxoomclient.domain.usecase.order.TrackOrderUseCase
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

private const val TAG = "TrackOrderViewModel"

@HiltViewModel
class TrackOrderViewModel @Inject constructor(
    private val trackOrderUseCase: TrackOrderUseCase,
) : ViewModel() {

    val userIntent = Channel<TrackOrderIntent>(Channel.UNLIMITED)

    private val _state: MutableStateFlow<TrackOrderState> =
        MutableStateFlow(TrackOrderState.Loading)
    val state get() = _state.asStateFlow()


    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {

            userIntent.consumeAsFlow().collect { intent ->
                when (intent) {
                    is TrackOrderIntent.TrackOrder -> {
                        trackOrder(intent.id)
                    }

                }
            }
        }
    }

    private suspend fun trackOrder(id: String) = viewModelScope.launch(Dispatchers.IO) {
        trackOrderUseCase.invoke(id).collect { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    Log.d(TAG, "Error: " + dataState.message)
                    _state.value = TrackOrderState.Error(dataState.message)
                }
                is DataState.Loading -> {
                    _state.value = TrackOrderState.Loading
                }
                is DataState.Success -> {
                    dataState.data?.let {
                        _state.value = TrackOrderState.Success(it)
                    }

                }
            }
        }
    }


}
