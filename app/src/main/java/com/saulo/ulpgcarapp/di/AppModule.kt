package com.saulo.ulpgcarapp.di

import com.google.firebase.auth.FirebaseAuth
import com.saulo.ulpgcarapp.data.repository.AuthRepositoryImpl
import com.saulo.ulpgcarapp.domain.repository.AuthRepository
import com.saulo.ulpgcarapp.domain.use_cases.auth.AuthUseCases
import com.saulo.ulpgcarapp.domain.use_cases.auth.GetCurrentUser
import com.saulo.ulpgcarapp.domain.use_cases.auth.Login
import com.saulo.ulpgcarapp.domain.use_cases.auth.Logout
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    fun provideAuthUseCases(repository: AuthRepository) = AuthUseCases(
        getCurrentUSer = GetCurrentUser(repository),
        login = Login(repository),
        logout = Logout(repository)
    )

}