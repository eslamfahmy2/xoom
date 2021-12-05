package com.chuify.cleanxoomclient.presentation.ui.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.cleanxoomclient.data.remote.dto.PaymentDto
import com.chuify.cleanxoomclient.domain.repository.OrderRepo
import com.chuify.cleanxoomclient.domain.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class CheckPaymentIntent {
    data class Check(val id: String) : CheckPaymentIntent()
}

sealed class CheckPaymentState {
    data class Success(val method: PaymentDto) : CheckPaymentState()
    data class Error(val message: String? = null) : CheckPaymentState()
    object Loading : CheckPaymentState()
    object Idl : CheckPaymentState()
}

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val orderRepo: OrderRepo
) : ViewModel() {

    val userIntent = Channel<CheckPaymentIntent>(Channel.UNLIMITED)

    private val _state: MutableStateFlow<CheckPaymentState> =
        MutableStateFlow(CheckPaymentState.Idl)
    val state get() = _state.asStateFlow()

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                if (it is CheckPaymentIntent.Check) {
                    checkPayment(it.id)
                }
            }
        }
    }

    private suspend fun checkPayment(id: String) {

        viewModelScope.launch(Dispatchers.IO) {
            when (val res = orderRepo.confirmPayment(id)) {
                is ResponseState.Error -> {
                    res.message?.let {
                        _state.value = CheckPaymentState.Error(it)
                    }
                }
                is ResponseState.Success -> {
                    _state.value = CheckPaymentState.Success(res.data)
                }
            }
        }
    }

    fun idle() {
        _state.value = CheckPaymentState.Loading
    }

}