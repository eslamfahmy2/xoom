package com.chuify.xoomclient.presentation.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.xoomclient.domain.usecase.profile.GetLoyaltyPointsUseCase
import com.chuify.xoomclient.domain.usecase.profile.GetProfileUseCase
import com.chuify.xoomclient.domain.utils.DataState
import com.chuify.xoomclient.presentation.ui.signup.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val getLoyaltyPointsUseCase: GetLoyaltyPointsUseCase,
) : ViewModel() {

    val userIntent = Channel<ProfileIntent>(Channel.UNLIMITED)

    private val _state: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState.Loading)
    val state get() = _state.asStateFlow()

    private val _localityPoints: MutableStateFlow<String> = MutableStateFlow(String())
    val localityPoints get() : StateFlow<String> = _localityPoints

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect { intent ->
                when (intent) {
                    ProfileIntent.LoadProfile -> {
                        loadProfile()
                        getLoyaltyPoints()
                    }
                }
            }
        }
    }

    private suspend fun loadProfile() = viewModelScope.launch(Dispatchers.IO) {
        getProfileUseCase().collect { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    Log.d(TAG, "Error: " + dataState.message)
                    _state.value = ProfileState.Error(dataState.message)
                }
                is DataState.Loading -> {
                    _state.value = ProfileState.Loading
                }
                is DataState.Success -> {
                    dataState.data?.let {
                        _state.value = ProfileState.Success(dataState.data)
                    }

                }
            }
        }
    }

    private suspend fun getLoyaltyPoints() = viewModelScope.launch(Dispatchers.IO) {
        getLoyaltyPointsUseCase().collect { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    Log.d(TAG, "Error: " + dataState.message)
                    //    _state.value = ProfileState.Error(dataState.message)
                }
                is DataState.Loading -> {
                    //   _state.value = ProfileState.Loading
                }
                is DataState.Success -> {
                    dataState.data?.let {
                        _localityPoints.value = it
                    }

                }
            }
        }
    }

}