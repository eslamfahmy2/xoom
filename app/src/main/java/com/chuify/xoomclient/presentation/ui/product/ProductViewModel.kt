package com.chuify.xoomclient.presentation.ui.product

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.xoomclient.domain.usecase.vendor.ListProductsUseCase
import com.chuify.xoomclient.domain.utils.DataState
import com.chuify.xoomclient.presentation.ui.signup.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductViewModel @Inject constructor(
    private val useCase: ListProductsUseCase,
) : ViewModel() {

    val userIntent = Channel<ProductIntent>(Channel.UNLIMITED)

    private val _state: MutableState<ProductState> = mutableStateOf(ProductState.Loading)
    val state get() = _state

    init {
        handleIntent()
        viewModelScope.launch {
            userIntent.send(ProductIntent.LoadProducts)
        }
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect { intent ->
                when (intent) {
                    ProductIntent.LoadProducts -> {
                        useCase("111").collect { dataState ->
                            when (dataState) {
                                is DataState.Error -> {
                                    Log.d(TAG, "Error: " + dataState.message)
                                    _state.value = ProductState.Error(dataState.message)
                                }
                                is DataState.Loading -> {
                                    _state.value = ProductState.Loading
                                }
                                is DataState.Success -> {
                                    _state.value =
                                        ProductState.Success(data = dataState.data ?: listOf())
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}