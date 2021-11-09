package com.chuify.xoomclient.domain.usecase.cart

import com.chuify.xoomclient.domain.mapper.CartEntityMapper
import com.chuify.xoomclient.domain.model.Cart
import com.chuify.xoomclient.domain.repository.CartRepo
import com.chuify.xoomclient.domain.utils.DataState
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
