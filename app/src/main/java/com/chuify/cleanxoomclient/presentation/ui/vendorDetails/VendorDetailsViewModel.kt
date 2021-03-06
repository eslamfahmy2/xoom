package com.chuify.cleanxoomclient.presentation.ui.vendorDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.cleanxoomclient.domain.model.CartPreview
import com.chuify.cleanxoomclient.domain.usecase.cart.CartPreviewUC
import com.chuify.cleanxoomclient.domain.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "VendorDetailsViewModel"

@HiltViewModel
class VendorDetailsViewModel @Inject constructor(
    private val useCase: CartPreviewUC,
) : ViewModel() {

    val userIntent = Channel<VendorDetailsIntent>(Channel.UNLIMITED)

    val preview: MutableStateFlow<CartPreview> = MutableStateFlow(CartPreview())


    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {

            userIntent.consumeAsFlow().collect { intent ->
                when (intent) {
                    VendorDetailsIntent.LoadVendorDetails -> {
                        loadVendors()
                    }

                }
            }
        }
    }

    private suspend fun loadVendors() = viewModelScope.launch(Dispatchers.IO) {
        useCase().collect { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    Log.d(TAG, "Error: " + dataState.message)
                    preview.value = CartPreview()
                    //  _state.value = VendorDetailsState.Error(dataState.message)
                }
                is DataState.Loading -> {
                    //    _state.value = VendorDetailsState.Loading
                }
                is DataState.Success -> {
                    dataState.data?.let {
                        preview.value = it
                    }

                }
            }
        }
    }


}