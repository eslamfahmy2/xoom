package com.chuify.xoomclient.data.local.dao

import androidx.room.*
import com.chuify.xoomclient.data.local.entity.OrderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(model: OrderEntity)

    @Update
    suspend fun update(model: OrderEntity)

    @Delete
    suspend fun delete(model: OrderEntity)

    @Query("DELETE FROM `Order`")
    suspend fun deleteAll()

    @Query("SELECT * FROM `Order` ORDER BY time")
    fun getAll(): Flow<List<OrderEntity>>

    @Query("SELECT SUM(quantity) FROM `Order`")
    fun getCartItemsCount(): Flow<Int>

}