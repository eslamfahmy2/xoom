package com.chuify.cleanxoomclient.domain.usecase.cart

import com.chuify.cleanxoomclient.domain.repository.CartRepo
import com.chuify.cleanxoomclient.domain.utils.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class EraseOrderUs @Inject constructor(
    private val repo: CartRepo,
) {
    suspend operator fun invoke() = flow<DataState<Unit>> {
        try {
            emit(DataState.Loading())
            repo.clear()
            emit(DataState.Success())
        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }.flowOn(Dispatchers.IO).conflate()
}
