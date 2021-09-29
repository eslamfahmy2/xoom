package com.chuify.xoomclient.domain.di


import com.chuify.xoomclient.domain.mapper.UserDtoMapper
import com.chuify.xoomclient.domain.repository.AuthRepo
import com.chuify.xoomclient.domain.usecase.auth.AuthInteraction
import com.chuify.xoomclient.domain.usecase.auth.SignInUseCase
import com.chuify.xoomclient.domain.usecase.auth.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Singleton
    @Provides
    fun provideSingInUseCase(
        repository: AuthRepo,
        mapperModule: UserDtoMapper,
    ) = SignInUseCase(repo = repository, mapper = mapperModule)

    @Singleton
    @Provides
    fun provideSingUpUseCase(
        repository: AuthRepo,
        mapperModule: UserDtoMapper,
    ) = SignUpUseCase(repo = repository, mapper = mapperModule)

    @Singleton
    @Provides
    fun provideAuthInteractions(
        signUpUseCase: SignUpUseCase,
        signInUseCase: SignInUseCase,
    ) = AuthInteraction(signIn = signInUseCase, signUp = signUpUseCase)


}