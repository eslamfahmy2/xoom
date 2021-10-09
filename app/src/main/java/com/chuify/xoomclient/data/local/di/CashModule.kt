package com.chuify.xoomclient.data.local.di

import androidx.room.Room
import com.chuify.xoomclient.data.local.dao.OrderDao
import com.chuify.xoomclient.data.local.room.OrderDatabase
import com.chuify.xoomclient.data.local.room.OrderDatabase.Companion.DATABASE_NAME
import com.chuify.xoomclient.presentation.ui.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CashModule {

    @Singleton
    @Provides
    fun provideAppDb(context: BaseApplication): OrderDatabase {
        return Room
            .databaseBuilder(context, OrderDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideOrderDao(dp: OrderDatabase): OrderDao {
        return dp.orderDao()
    }

}