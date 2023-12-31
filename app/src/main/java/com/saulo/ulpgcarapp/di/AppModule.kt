package com.saulo.ulpgcarapp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.saulo.ulpgcarapp.core.Constants.CHAT
import com.saulo.ulpgcarapp.core.Constants.PUBLISH
import com.saulo.ulpgcarapp.core.Constants.USERS
import com.saulo.ulpgcarapp.data.network.RouteApiRepository
import com.saulo.ulpgcarapp.data.repository.AuthRepositoryImpl
import com.saulo.ulpgcarapp.data.repository.ChatRepositoryImpl
import com.saulo.ulpgcarapp.data.repository.PublishRepositoryImp
import com.saulo.ulpgcarapp.data.repository.UsersRepositoryImpl
import com.saulo.ulpgcarapp.domain.repository.AuthRepository
import com.saulo.ulpgcarapp.domain.repository.ChatRepository
import com.saulo.ulpgcarapp.domain.repository.PublishRepository
import com.saulo.ulpgcarapp.domain.repository.UsersRepository
import com.saulo.ulpgcarapp.domain.use_cases.auth.*
import com.saulo.ulpgcarapp.domain.use_cases.chat.ChatUseCases
import com.saulo.ulpgcarapp.domain.use_cases.chat.GetChatMessages
import com.saulo.ulpgcarapp.domain.use_cases.chat.SendMessage
import com.saulo.ulpgcarapp.domain.use_cases.publish.*
import com.saulo.ulpgcarapp.domain.use_cases.routes.GetRouteUseCase
import com.saulo.ulpgcarapp.domain.use_cases.routes.MatrixUseCase
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
    @Named(CHAT)
    //fun provideChatRef(db: FirebaseFirestore): CollectionReference = db.collection(CHAT)
    fun provideChatRef(@Named(PUBLISH) publishCollection: CollectionReference): CollectionReference = publishCollection.document().collection(CHAT)

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    fun provideUsersRepository(impl: UsersRepositoryImpl): UsersRepository = impl

    @Provides
    fun providePublishRepository(impl: PublishRepositoryImp): PublishRepository = impl

    @Provides
    fun provideChatRepository(impl: ChatRepositoryImpl): ChatRepository = impl

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
        getPublishRidesByUserId = GetPublishRidesByUserId(repository),
        getPublishRidesByPassengerId = GetPublishRidesByPassengerId(repository),
        getPublishRidesByMunicipality = GetPublishRidesByMunicipality(repository),
        deletePublishRide = DeletePublishRide(repository),
        updatePublishRide = UpdatePublishRide(repository),
        updatePassengerRequest = UpdatePassengerRequest(repository),
        getPublishRideById = GetPublishRideById(repository)
    )

    @Provides
    fun provideRoutesUseCases(repository: RouteApiRepository) = RoutesUseCases(
        getrouteUseCase = GetRouteUseCase(repository),
        matrixUseCase = MatrixUseCase(repository)
    )

    @Provides
    fun provideChatUseCases(repository: ChatRepository) = ChatUseCases(
        sendMessage = SendMessage(repository),
        getChatMessages = GetChatMessages(repository)
    )


}