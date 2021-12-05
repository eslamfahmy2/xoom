package com.chuify.cleanxoomclient.presentation.ui.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    data class Success(val message: String? = null) : CheckPaymentState()
    data class Error(val message: String? = null) : CheckPaymentState()
    data class Loading(val message: String? = null) : CheckPaymentState()
}

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val orderRepo: OrderRepo
) : ViewModel() {

    val userIntent = Channel<CheckPaymentIntent>(Channel.UNLIMITED)

    private val _state: MutableStateFlow<CheckPaymentState> =
        MutableStateFlow(CheckPaymentState.Loading())
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
            _state.value = CheckPaymentState.Loading()
            when (val res = orderRepo.confirmPayment(id)) {
                is ResponseState.Error -> {
                    res.message?.let {
                        _state.value = CheckPaymentState.Error(it)
                    }
                }
                is ResponseState.Success -> {
                    when (res.data.status) {
                        300 -> {
                            _state.value = CheckPaymentState.Loading(res.data.msg)
                        }
                        200 -> {
                            _state.value = CheckPaymentState.Success(res.data.msg)
                        }
                        400 -> {
                            _state.value = CheckPaymentState.Error(res.data.msg)
                        }
                    }
                }
            }
        }
    }


}