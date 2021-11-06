package com.chuify.xoomclient.presentation.ui.editProfile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.xoomclient.domain.usecase.profile.GetProfileUseCase
import com.chuify.xoomclient.domain.usecase.profile.UpdateUserUseCase
import com.chuify.xoomclient.domain.utils.DataState
import com.chuify.xoomclient.presentation.ui.signup.TAG
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


@ExperimentalCoroutinesApi
@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel() {

    val userIntent = Channel<EditProfileIntent>(Channel.UNLIMITED)


    private val _firstName: MutableStateFlow<String> = MutableStateFlow(String())
    val firstName get() = _firstName.asStateFlow()

    private val _lastName: MutableStateFlow<String> = MutableStateFlow(String())
    val lastName get() = _lastName.asStateFlow()

    private val _email: MutableStateFlow<String> = MutableStateFlow(String())
    val email get() = _email.asStateFlow()


    private val _state: MutableStateFlow<EditProfileState> =
        MutableStateFlow(EditProfileState.Loading)
    val state get() = _state.asStateFlow()


    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect { intent ->
                Log.d(TAG, "handleIntent: $intent")
                when (intent) {
                    EditProfileIntent.LoadProfile -> {
                        loadProfile()
                    }
                    EditProfileIntent.EditProfile -> {
                        updateUser(_firstName.value, _lastName.value, _email.value)
                    }
                    is EditProfileIntent.EmailChange -> {
                        _email.value = intent.data
                    }
                    is EditProfileIntent.FirstNameChange -> {
                        _firstName.value = intent.data
                    }
                    is EditProfileIntent.LastNameChange -> {
                        _lastName.value = intent.data
                    }
                }
            }
        }
    }


    private suspend fun updateUser(firstName: String, lastName: String, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            updateUserUseCase(
                firstName = firstName,
                lastName = lastName,
                email = email
            ).collect { dataSate ->
                when (dataSate) {
                    is DataState.Error -> {
                        Log.d(TAG, "updateTHeme: " + dataSate.message)
                        _state.value = EditProfileState.Error(dataSate.message)
                    }
                    is DataState.Loading -> {
                        _state.value = EditProfileState.Loading
                    }
                    is DataState.Success -> {
                        dataSate.data?.let {
                            _state.value = EditProfileState.ProfileUpdated
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
                    _state.value = EditProfileState.Error(dataState.message)
                }
                is DataState.Loading -> {
                    _state.value = EditProfileState.Loading
                }
                is DataState.Success -> {
                    dataState.data?.let {
                        _firstName.value = dataState.data.firstname
                        _lastName.value = dataState.data.lastname
                        _email.value = dataState.data.email
                        _state.value = EditProfileState.Success(it)
                    }

                }
            }
        }
    }


}