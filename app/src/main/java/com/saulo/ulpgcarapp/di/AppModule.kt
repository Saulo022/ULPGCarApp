package com.saulo.ulpgcarapp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.saulo.ulpgcarapp.core.Constants.USERS
import com.saulo.ulpgcarapp.data.repository.AuthRepositoryImpl
import com.saulo.ulpgcarapp.data.repository.UsersRepositoryImpl
import com.saulo.ulpgcarapp.domain.repository.AuthRepository
import com.saulo.ulpgcarapp.domain.repository.UsersRepository
import com.saulo.ulpgcarapp.domain.use_cases.auth.*
import com.saulo.ulpgcarapp.domain.use_cases.users.Create
import com.saulo.ulpgcarapp.domain.use_cases.users.GetUserById
import com.saulo.ulpgcarapp.domain.use_cases.users.Update
import com.saulo.ulpgcarapp.domain.use_cases.users.UsersUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides
    fun provideUsersRef(db: FirebaseFirestore): CollectionReference = db.collection(USERS)

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    fun provideUsersRepository(impl: UsersRepositoryImpl): UsersRepository = impl

    @Provides
    fun provideAuthUseCases(repository: AuthRepository) = AuthUseCases(
        getCurrentUser = GetCurrentUser(repository),
        login = Login(repository),
        logout = Logout(repository),
        signup = Signup(repository)
    )

    @Provides
    fun provideUsersUseCases(repository: UsersRepository) = UsersUseCases(
        create = Create(repository),
        getUserById = GetUserById(repository),
        update = Update(repository)
    )

}