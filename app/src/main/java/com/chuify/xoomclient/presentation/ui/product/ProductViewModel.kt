package com.chuify.xoomclient.presentation.ui.product

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.xoomclient.domain.model.Product
import com.chuify.xoomclient.domain.usecase.cart.DeleteOrUpdateProductUseCase
import com.chuify.xoomclient.domain.usecase.cart.InsertOrUpdateProductUseCase
import com.chuify.xoomclient.domain.usecase.home.ListProductsUseCase
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
    private val insetCartItem: InsertOrUpdateProductUseCase,
    private val deleteCartItem: DeleteOrUpdateProductUseCase,
) : ViewModel() {

    val userIntent = Channel<ProductIntent>(Channel.UNLIMITED)

    private val _state: MutableState<ProductState> = mutableStateOf(ProductState.Loading)
    val state get() = _state


    init {
        handleIntent()
        viewModelScope.launch {
            loadProducts()
        }
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect { intent ->
                Log.d(TAG, "handleIntent: ")
                when (intent) {
                    ProductIntent.InitLoad -> {
                        loadProducts()
                    }
                    is ProductIntent.IncreaseCartItem -> {

                    }
                }
            }
        }
    }

    private suspend fun loadProducts() {

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
                    Log.d(TAG, "Success: " + dataState.data)
                    _state.value = ProductState.Success(data = dataState.data ?: listOf())
                }
            }
        }
    }

    fun insert(product: Product) {

        viewModelScope.launch {

            Log.d(TAG, "handleIntent: ")
            insetCartItem(product).collect { dataState ->
                when (dataState) {
                    is DataState.Error -> {
                        Log.d(TAG, "Error: " + dataState.message)
                        _state.value = ProductState.Error(dataState.message)
                    }
                    is DataState.Loading -> {
                    }
                    is DataState.Success -> {
                        Log.d(TAG, "Success: $dataState")
                    }
                }
            }


        }
    }

    fun decreaseOrRemove(product: Product) {

        viewModelScope.launch {

            Log.d(TAG, "handleIntent: ")
            deleteCartItem(product).collect { dataState ->
                when (dataState) {
                    is DataState.Error -> {
                        Log.d(TAG, "Error: " + dataState.message)
                        _state.value = ProductState.Error(dataState.message)
                    }
                    is DataState.Loading -> {
                    }
                    is DataState.Success -> {
                        Log.d(TAG, "Success: $dataState")
                    }
                }
            }

        }

    }


}