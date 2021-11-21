package com.chuify.cleanxoomclient.data.local.di

import androidx.room.Room
import com.chuify.cleanxoomclient.data.local.dao.CartDao
import com.chuify.cleanxoomclient.data.local.dao.NotificationDao
import com.chuify.cleanxoomclient.data.local.room.CartDatabase
import com.chuify.cleanxoomclient.data.local.room.CartDatabase.Companion.DATABASE_NAME
import com.chuify.cleanxoomclient.presentation.application.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CashModule {

    @Singleton
    @Provides
    fun provideAppDb(context: BaseApplication): CartDatabase {
        return Room
            .databaseBuilder(context, CartDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideOrderDao(dp: CartDatabase): CartDao {
        return dp.orderDao()
    }

    @Singleton
    @Provides
    fun provideNotificationDao(dp: CartDatabase): NotificationDao {
        return dp.notificationDao()
    }

}