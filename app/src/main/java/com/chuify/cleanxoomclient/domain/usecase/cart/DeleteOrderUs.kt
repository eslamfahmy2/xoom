package com.chuify.cleanxoomclient.domain.usecase.cart

import com.chuify.cleanxoomclient.domain.mapper.CartEntityMapper
import com.chuify.cleanxoomclient.domain.model.Cart
import com.chuify.cleanxoomclient.domain.repository.CartRepo
import com.chuify.cleanxoomclient.domain.utils.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DeleteOrderUs @Inject constructor(
    private val repo: CartRepo,
    private val orderEntityMapper: CartEntityMapper,
) {
    suspend operator fun invoke(order: Cart) = flow<DataState<Unit>> {
        try {
            emit(DataState.Loading())
            val model = orderEntityMapper.mapFromDomainModel(order)
            repo.delete(model)
            emit(DataState.Success())
        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }.flowOn(Dispatchers.IO).conflate()
}
