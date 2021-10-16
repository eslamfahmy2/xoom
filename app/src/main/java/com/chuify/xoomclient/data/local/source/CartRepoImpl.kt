package com.chuify.xoomclient.data.local.source

import com.chuify.xoomclient.data.local.dao.OrderDao
import com.chuify.xoomclient.data.local.entity.OrderEntity
import com.chuify.xoomclient.domain.repository.CartRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepoImpl @Inject constructor(
    private val dp: OrderDao,
) : CartRepo {

    override suspend fun insert(model: OrderEntity) {
        return dp.insert(model)
    }

    override suspend fun update(model: OrderEntity) {
        return dp.update(model)
    }

    override suspend fun delete(model: OrderEntity) {
        return dp.delete(model)
    }

    override suspend fun deleteAll() {
        return dp.deleteAll()
    }

    override suspend fun getAll(): Flow<List<OrderEntity>> {
        return dp.getAll()
    }

    override suspend fun getCartItemsCount(): Flow<Int> {
        return dp.getCartItemsCount()
    }

    override suspend fun getById(id: String): Flow<OrderEntity?> {
        return dp.getById(id)
    }


}