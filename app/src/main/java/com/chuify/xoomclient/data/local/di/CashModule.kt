package com.chuify.xoomclient.data.local.di

import androidx.room.Room
import com.chuify.xoomclient.data.local.dao.CartDao
import com.chuify.xoomclient.data.local.dao.NotificationDao
import com.chuify.xoomclient.data.local.room.CartDatabase
import com.chuify.xoomclient.data.local.room.CartDatabase.Companion.DATABASE_NAME
import com.chuify.xoomclient.presentation.application.BaseApplication
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