package com.chuify.xoomclient.presentation.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.xoomclient.domain.usecase.profile.GetLoyaltyPointsUseCase
import com.chuify.xoomclient.domain.usecase.profile.GetProfileUseCase
import com.chuify.xoomclient.domain.usecase.profile.ThemeUseCase
import com.chuify.xoomclient.domain.utils.DataState
import com.chuify.xoomclient.presentation.ui.signup.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val getLoyaltyPointsUseCase: GetLoyaltyPointsUseCase,
    private val themeUseCase: ThemeUseCase
) : ViewModel() {

    val userIntent = Channel<ProfileIntent>(Channel.UNLIMITED)

    private val _state: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState.Loading)
    val state get() = _state.asStateFlow()

    private val _isDark: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isDark get() = _isDark.asStateFlow()


    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect { intent ->
                Log.d(TAG, "handleIntent: $intent")
                when (intent) {
                    ProfileIntent.LoadProfile -> {
                        getTheme()
                        loadProfile()
                        getLoyaltyPoints()

                    }
                    is ProfileIntent.ChangeTheme -> {
                        updateTHeme(intent.boolean)
                    }
                }
            }
        }
    }


    private suspend fun getTheme() {
        Log.d(TAG, "getTheme: ")
        viewModelScope.launch(Dispatchers.IO) {
            themeUseCase.getTheme().collect { dataSate ->
                when (dataSate) {
                    is DataState.Error -> {
                        Log.d(TAG, "updateTHeme: " + dataSate.message)
                    }
                    is DataState.Loading -> {
                    }
                    is DataState.Success -> {
                        dataSate.data?.let {
                            _isDark.value = it
                        }
                    }
                }
            }
        }
    }

    private suspend fun updateTHeme(boolean: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            themeUseCase.changeTheme(boolean = boolean).collect { dataSate ->
                when (dataSate) {
                    is DataState.Error -> {
                        Log.d(TAG, "updateTHeme: " + dataSate.message)
                    }
                    is DataState.Loading -> {
                    }
                    is DataState.Success -> {
                        dataSate.data?.let {
                            _isDark.value = it
                        }
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
        getLoyaltyPointsUseCase().collect()
    }

}