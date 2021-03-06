package com.chuify.cleanxoomclient.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chuify.cleanxoomclient.data.local.dao.CartDao
import com.chuify.cleanxoomclient.data.local.dao.NotificationDao
import com.chuify.cleanxoomclient.data.local.entity.CartEntity
import com.chuify.cleanxoomclient.data.local.entity.NotificationEntity

@Database(entities = [CartEntity::class , NotificationEntity::class], version =3 , exportSchema = false)
abstract class CartDatabase : RoomDatabase() {

    abstract fun orderDao(): CartDao

    abstract fun notificationDao(): NotificationDao

    companion object {
        const val DATABASE_NAME: String = "xoom.dp"
    }

}