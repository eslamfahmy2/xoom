package com.chuify.cleanxoomclient.data.local.dao

import androidx.room.*
import com.chuify.cleanxoomclient.data.local.entity.NotificationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
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