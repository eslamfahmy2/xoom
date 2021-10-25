package com.chuify.xoomclient.data.local.dao

import androidx.room.*
import com.chuify.xoomclient.data.local.entity.NotificationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(model: NotificationEntity)

    @Update
    suspend fun update(model: NotificationEntity)

    @Query("DELETE FROM `Notification`")
    suspend fun deleteAll()

    @Query("SELECT * FROM `Notification` ORDER BY time")
    fun getAll(): Flow<List<NotificationEntity>>

    @Query("SELECT Count(*) FROM `Notification` where open = 0 ")
    fun getCountNotRead(): Flow<Int>


}