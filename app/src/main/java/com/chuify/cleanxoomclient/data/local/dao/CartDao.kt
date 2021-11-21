package com.chuify.cleanxoomclient.data.local.dao

import androidx.room.*
import com.chuify.cleanxoomclient.data.local.entity.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(model: CartEntity)

    @Update
    suspend fun update(model: CartEntity)

    @Delete
    suspend fun delete(model: CartEntity)

    @Query("DELETE FROM `Order`")
    suspend fun deleteAll()

    @Query("SELECT * FROM `Order` ORDER BY time")
    fun getAll(): Flow<List<CartEntity>>

    @Query("SELECT SUM(quantity) FROM `Order`")
    fun getCartItemsCount(): Flow<Int>

    @Query("SELECT * FROM `Order` WHERE id=:ID")
    suspend fun getById(ID: String): CartEntity?

}