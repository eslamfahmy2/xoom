package com.chuify.xoomclient.domain.di


import com.chuify.xoomclient.data.prefrences.SharedPrefs
import com.chuify.xoomclient.data.prefrences.flow.FlowSharedPreferences
import com.chuify.xoomclient.domain.mapper.UserDtoMapper
import com.chuify.xoomclient.domain.repository.ProfileRepo
import com.chuify.xoomclient.domain.usecase.profile.GetLoyaltyPointsUseCase
import com.chuify.xoomclient.domain.usecase.profile.GetProfileUseCase
import com.chuify.xoomclient.domain.usecase.profile.ThemeUseCase
import com.chuify.xoomclient.domain.usecase.profile.UpdateUserUseCase
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