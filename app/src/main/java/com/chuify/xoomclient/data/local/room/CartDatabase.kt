package com.chuify.xoomclient.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chuify.xoomclient.data.local.dao.CartDao
import com.chuify.xoomclient.data.local.entity.CartEntity

@Database(entities = [CartEntity::class], version = 2, exportSchema = false)
abstract class CartDatabase : RoomDatabase() {

    abstract fun orderDao(): CartDao

    companion object {
        const val DATABASE_NAME: String = "xoom.dp"
    }

}