package com.chuify.xoomclient.presentation.ui.product

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.xoomclient.domain.model.Product
import com.chuify.xoomclient.domain.usecase.cart.DecreaseOrderUseCase
import com.chuify.xoomclient.domain.usecase.cart.IncreaseOrderUseCase
import com.chuify.xoomclient.domain.usecase.home.ListProductsUseCase
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
class ProductViewModel @Inject constructor(
    private val useCase: ListProductsUseCase,
    private val insetCartItem: IncreaseOrderUseCase,
    private val deleteCartItem: DecreaseOrderUseCase,
) : ViewModel() {


    val userIntent = Channel<ProductIntent>(Channel.UNLIMITED)

    private val _state: MutableState<ProductState> = mutableStateOf(ProductState.Loading)
    val state get() = _state


    init {
        handleIntent()

    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect { intent ->
                Log.d(TAG, "handleIntent: ")
                when (intent) {
                    is ProductIntent.InitLoad -> {
                        loadProducts(intent.id)
                    }
                    is ProductIntent.IncreaseCartItem -> {

                    }
                    is ProductIntent.DecreaseOrRemove -> {
                        decreaseOrRemove(intent.product)
                    }
                    is ProductIntent.Insert -> {
                        insert(intent.product)
                    }
                }
            }
        }
    }

    private suspend fun loadProducts(id: String) = viewModelScope.launch(Dispatchers.IO) {

        useCase(id).collect { dataState ->
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

    private suspend fun insert(product: Product) = viewModelScope.launch(Dispatchers.IO) {

        Log.d(TAG, "handleIntent: ")
        insetCartItem(
            image = product.image,
            name = product.name,
            id = product.id,
            basePrice = product.price
        ).collect { dataState ->
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

    private suspend fun decreaseOrRemove(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        Log.d(TAG, "handleIntent: ")
        deleteCartItem(product.id).collect { dataState ->
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


