package com.chuify.xoomclient.domain.di


import com.chuify.xoomclient.domain.mapper.NotificationEntityMapper
import com.chuify.xoomclient.domain.repository.NotificationRepo
import com.chuify.xoomclient.domain.usecase.notification.GetNotReadCountUseCase
import com.chuify.xoomclient.domain.usecase.notification.GetNotificationListUseCase
import com.chuify.xoomclient.domain.usecase.notification.MarkAsReadUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationUseCasesModule {

    @Singleton
    @Provides
    fun provideListNotificationUseCase(
        notificationRepo: NotificationRepo,
        notificationEntityMapper: NotificationEntityMapper,
    ) = GetNotificationListUseCase(
        notificationRepo = notificationRepo,
        notificationEntityMapper = notificationEntityMapper)


    @Singleton
    @Provides
    fun provideMarkAsReadUseCase(
        notificationRepo: NotificationRepo,
        notificationEntityMapper: NotificationEntityMapper,
    ) = MarkAsReadUseCase(
        notificationRepo = notificationRepo,
        notificationEntityMapper = notificationEntityMapper)


    @Singleton
    @Provides
    fun provideGetNotReadCountUseCaseUseCase(
        notificationRepo: NotificationRepo,
    ) = GetNotReadCountUseCase(
        notificationRepo = notificationRepo,
    )


}