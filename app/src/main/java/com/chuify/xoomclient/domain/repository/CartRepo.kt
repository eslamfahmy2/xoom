package com.chuify.xoomclient.domain.repository

import com.chuify.xoomclient.data.local.entity.OrderEntity
import kotlinx.coroutines.flow.Flow

interface CartRepo {

    suspend fun insert(model: OrderEntity)

    suspend fun update(model: OrderEntity)

    suspend fun delete(model: OrderEntity)

    suspend fun deleteAll()

    suspend fun getAll(): Flow<List<OrderEntity>>

    suspend fun getCartItemsCount(): Flow<Int>

    suspend fun getById(id: String): Flow<OrderEntity?>
}