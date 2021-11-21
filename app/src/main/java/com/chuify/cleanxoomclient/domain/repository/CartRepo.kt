package com.chuify.cleanxoomclient.domain.repository

import com.chuify.cleanxoomclient.data.local.entity.CartEntity
import kotlinx.coroutines.flow.Flow

interface CartRepo {

    suspend fun insert(model: CartEntity)

    suspend fun update(model: CartEntity)

    suspend fun delete(model: CartEntity)

    suspend fun clear()

    suspend fun getAll(): Flow<List<CartEntity>>

    suspend fun getCartItemsCount(): Flow<Int>

    suspend fun getById(id: String): CartEntity?

}