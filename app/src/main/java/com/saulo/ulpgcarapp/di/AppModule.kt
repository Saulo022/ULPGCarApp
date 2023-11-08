package com.saulo.ulpgcarapp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.saulo.ulpgcarapp.core.Constants.PUBLISH
import com.saulo.ulpgcarapp.core.Constants.USERS
import com.saulo.ulpgcarapp.data.network.SearchApiRepository
import com.saulo.ulpgcarapp.data.repository.AuthRepositoryImpl
import com.saulo.ulpgcarapp.data.repository.PublishRepositoryImp
import com.saulo.ulpgcarapp.data.repository.UsersRepositoryImpl
import com.saulo.ulpgcarapp.domain.repository.AuthRepository
import com.saulo.ulpgcarapp.domain.repository.PublishRepository
import com.saulo.ulpgcarapp.domain.repository.UsersRepository
import com.saulo.ulpgcarapp.domain.use_cases.auth.*
import com.saulo.ulpgcarapp.domain.use_cases.publish.*
import com.saulo.ulpgcarapp.domain.use_cases.routes.GetRouteUseCase
import com.saulo.ulpgcarapp.domain.use_cases.routes.RoutesUseCases
import com.saulo.ulpgcarapp.domain.use_cases.users.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides
    fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()

    @Provides
    @Named(USERS)
    fun provideStorageUsersRed(storage: FirebaseStorage): StorageReference = storage.reference.child(USERS)

    @Provides
    @Named(USERS)
    fun provideUsersRef(db: FirebaseFirestore): CollectionReference = db.collection(USERS)

    @Provides
    @Named(PUBLISH)
    fun providePublishRef(db: FirebaseFirestore): CollectionReference = db.collection(PUBLISH)

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    fun provideUsersRepository(impl: UsersRepositoryImpl): UsersRepository = impl

    @Provides
    fun providePublishRepository(impl: PublishRepositoryImp): PublishRepository = impl

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
        update = Update(repository),
        saveImage = SaveImage(repository)
    )

    @Provides
    fun providePublishUseCases(repository: PublishRepository) = PublishUseCases(
        publish = PublishRide(repository),
        getPublishRides = GetPublishRides(repository),
        getPublishRidesById = GetPublishRidesById(repository),
        getPublishRidesByMunicipality = GetPublishRidesByMunicipality(repository),
        deletePublishRide = DeletePublishRide(repository),
        updatePublishRide = UpdatePublishRide(repository)
    )

    @Provides
    fun provideRoutesUseCases(repository: SearchApiRepository) = RoutesUseCases(
        getrouteUseCase = GetRouteUseCase(repository)
    )


}