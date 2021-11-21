package com.chuify.cleanxoomclient.domain.di


import com.chuify.cleanxoomclient.data.prefrences.SharedPrefs
import com.chuify.cleanxoomclient.data.prefrences.flow.FlowSharedPreferences
import com.chuify.cleanxoomclient.domain.mapper.UserDtoMapper
import com.chuify.cleanxoomclient.domain.repository.CartRepo
import com.chuify.cleanxoomclient.domain.repository.NotificationRepo
import com.chuify.cleanxoomclient.domain.repository.ProfileRepo
import com.chuify.cleanxoomclient.domain.usecase.profile.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileUseCasesModule {

    @ExperimentalCoroutinesApi
    @Singleton
    @Provides
    fun provideGetProfileUseCase(
        flowSharedPreferences: FlowSharedPreferences,
        userDtoMapper: UserDtoMapper
    ) = GetProfileUseCase(
        userDtoMapper = userDtoMapper,
        flowSharedPreferences = flowSharedPreferences
    )

    @Singleton
    @Provides
    fun provideGetLocalityPointsUseCase(
        profileRepo: ProfileRepo,
        sharedPrefs: SharedPrefs
    ) = GetLoyaltyPointsUseCase(profileRepo = profileRepo, sharedPrefs = sharedPrefs)


    @ExperimentalCoroutinesApi
    @Singleton
    @Provides
    fun provideLogOutUseCase(
        flowSharedPreferences: FlowSharedPreferences,
        sharedPrefs: SharedPrefs,
        notificationRepo: NotificationRepo,
        cartRepo: CartRepo
    ) = LogOutUseCase(
        flowSharedPreferences = flowSharedPreferences, sharedPrefs = sharedPrefs,
        notificationRepo = notificationRepo, cartRepo = cartRepo
    )


    @Singleton
    @Provides
    fun provideUpdateUserUseCaseUseCase(
        profileRepo: ProfileRepo,
        sharedPrefs: SharedPrefs,
        userDtoMapper: UserDtoMapper
    ) = UpdateUserUseCase(
        profileRepo = profileRepo,
        sharedPrefs = sharedPrefs,
        userDtoMapper = userDtoMapper
    )


    @Singleton
    @Provides
    fun provideThemeUseCaseUseCase(
        sharedPrefs: SharedPrefs
    ) = ThemeUseCase(sharedPrefs = sharedPrefs)


}