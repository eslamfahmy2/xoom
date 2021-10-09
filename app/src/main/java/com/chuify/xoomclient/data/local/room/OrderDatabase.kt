package com.chuify.xoomclient.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chuify.xoomclient.data.local.dao.OrderDao
import com.chuify.xoomclient.data.local.entity.OrderEntity

@Database(entities = [OrderEntity::class], version = 1, exportSchema = false)
abstract class OrderDatabase : RoomDatabase() {

    abstract fun orderDao(): OrderDao

    companion object {
        const val DATABASE_NAME: String = "xoom.dp"
    }

}