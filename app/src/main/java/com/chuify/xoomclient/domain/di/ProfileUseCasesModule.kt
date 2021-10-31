package com.chuify.xoomclient.domain.di


import com.chuify.xoomclient.data.prefrences.SharedPrefs
import com.chuify.xoomclient.domain.repository.ProfileRepo
import com.chuify.xoomclient.domain.usecase.profile.GetLoyaltyPointsUseCase
import com.chuify.xoomclient.domain.usecase.profile.GetProfileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileUseCasesModule {

    @Singleton
    @Provides
    fun provideGetProfileUseCase(
        sharedPrefs: SharedPrefs,
    ) = GetProfileUseCase(sharedPrefs = sharedPrefs)

    @Singleton
    @Provides
    fun provideGetLocalityPointsUseCase(
        profileRepo: ProfileRepo,
        sharedPrefs: SharedPrefs
    ) = GetLoyaltyPointsUseCase(profileRepo = profileRepo , sharedPrefs = sharedPrefs)


}