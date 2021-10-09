package com.chuify.xoomclient.domain.usecase.cart

import com.chuify.xoomclient.domain.repository.CartRepo
import com.chuify.xoomclient.domain.utils.DataState
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EraseOrderUs @Inject constructor(
    private val repo: CartRepo,
) {
    suspend operator fun invoke() = flow<DataState<Unit>> {
        try {
            emit(DataState.Loading())
            repo.deleteAll()
            emit(DataState.Success())
        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }
}
