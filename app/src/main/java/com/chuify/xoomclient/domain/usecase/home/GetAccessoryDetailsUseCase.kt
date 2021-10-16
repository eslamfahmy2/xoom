package com.chuify.xoomclient.domain.usecase.home

import android.util.Log
import com.chuify.xoomclient.domain.model.Accessory
import com.chuify.xoomclient.domain.repository.CartRepo
import com.chuify.xoomclient.domain.utils.DataState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val TAG = "GetAccessoryD"

class GetAccessoryDetailsUseCase @Inject constructor(
    private val cartRepo: CartRepo,
) {

    suspend operator fun invoke(accessory: Accessory) = flow<DataState<Accessory>> {
        try {
            emit(DataState.Loading())
            cartRepo.getById(accessory.id).collect { order ->
                order?.let {
                    Log.d(TAG, "order: $it")
                    val res = accessory.copy(quantity = it.quantity, price = it.price)
                    Log.d(TAG, "invoke: $res")
                    emit(DataState.Success(data = res))
                } ?: emit(DataState.Success(accessory))
            }

        } catch (e: Exception) {
            Log.d(TAG, "invoke: " + e.message)
            emit(DataState.Error(e.message))
        }
    }
}