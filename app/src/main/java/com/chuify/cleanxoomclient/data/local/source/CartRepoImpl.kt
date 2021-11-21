package com.chuify.cleanxoomclient.data.local.source

import com.chuify.cleanxoomclient.data.local.dao.CartDao
import com.chuify.cleanxoomclient.data.local.entity.CartEntity
import com.chuify.cleanxoomclient.domain.repository.CartRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepoImpl @Inject constructor(
    private val dp: CartDao,
) : CartRepo {

    override suspend fun insert(model: CartEntity) {
        return dp.insert(model)
    }

    override suspend fun update(model: CartEntity) {
        return dp.update(model)
    }

    override suspend fun delete(model: CartEntity) {
        return dp.delete(model)
    }


    override suspend fun clear() {
        return dp.deleteAll()
    }

    override suspend fun getAll(): Flow<List<CartEntity>> {
        return dp.getAll()
    }

    override suspend fun getCartItemsCount(): Flow<Int> {
        return dp.getCartItemsCount()
    }

    override suspend fun getById(id: String): CartEntity? {
        return dp.getById(id)
    }


}