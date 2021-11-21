package com.chuify.cleanxoomclient.presentation.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.cleanxoomclient.domain.usecase.profile.GetLoyaltyPointsUseCase
import com.chuify.cleanxoomclient.domain.usecase.profile.GetProfileUseCase
import com.chuify.cleanxoomclient.domain.usecase.profile.LogOutUseCase
import com.chuify.cleanxoomclient.domain.usecase.profile.ThemeUseCase
import com.chuify.cleanxoomclient.domain.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "ProfileScreen"

@ExperimentalCoroutinesApi
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val getLoyaltyPointsUseCase: GetLoyaltyPointsUseCase,
    private val themeUseCase: ThemeUseCase,
    private val logOutUseCase: LogOutUseCase
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
                    ProfileIntent.LogOut -> {
                        logOut()
                    }
                }
            }
        }
    }

    private suspend fun logOut() {
        viewModelScope.launch(Dispatchers.IO) {
            logOutUseCase().collect { dataSate ->
                when (dataSate) {
                    is DataState.Error -> {
                        Log.d(TAG, "updateTHeme: " + dataSate.message)
                    }
                    is DataState.Loading -> {
                    }
                    is DataState.Success -> {
                        Log.d(TAG, "logOut: $dataSate")
                        _state.value = ProfileState.Success(logged = true)
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
          //          _state.value = ProfileState.MyError(dataState.message)
                }
                is DataState.Loading -> {
                    _state.value = ProfileState.Loading
                }
                is DataState.Success -> {
                    dataState.data?.let {
                        _state.value = ProfileState.Success(user = it)
                    }

                }
            }
        }
    }

    private suspend fun getLoyaltyPoints() = viewModelScope.launch(Dispatchers.IO) {
        getLoyaltyPointsUseCase().collect()
    }

}