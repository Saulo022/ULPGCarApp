package com.saulo.ulpgcarapp.data.repository

import com.google.firebase.firestore.CollectionReference
import com.saulo.ulpgcarapp.core.Constants
import com.saulo.ulpgcarapp.domain.model.Passenger
import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.domain.model.Response
import com.saulo.ulpgcarapp.domain.model.User
import com.saulo.ulpgcarapp.domain.repository.PublishRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

class PublishRepositoryImp @Inject constructor(
    @Named(Constants.PUBLISH) private val publishRef: CollectionReference,
    @Named(Constants.USERS) private val usersRef: CollectionReference
) : PublishRepository {

    override suspend fun create(publish: Publish): Response<Boolean> {
        return try {
            publishRef.add(publish).await()

            Response.Success(true)

        } catch (e: Exception) {
            e.printStackTrace()
            Response.Failure(e)
        }
    }

    override suspend fun delete(idPublishRide: String): Response<Boolean> {
        return try {
            publishRef.document(idPublishRide).delete().await()
            Response.Success(true)

        } catch (e: Exception) {
            e.printStackTrace()
            Response.Failure(e)
        }
    }

    override suspend fun update(publish: Publish): Response<Boolean> {
        return try {

            val map: MutableMap<String, Any> = HashMap()
            map["id"] = publish.id
            map["origin"] = publish.origin
            map["destination"] = publish.destination
            map["fecha"] = publish.fecha
            map["hora"] = publish.hora
            map["numeroPasajeros"] = publish.numeroPasajeros
            map["route"] = publish.route
            map["pasajeros"] = publish.pasajeros
            map["precioViaje"] = publish.precioViaje
            map["plazasDisponibles"] = publish.plazasDisponibles


            publishRef.document(publish.id).update(map).await()
            Response.Success(true)

        } catch (e: Exception) {
            e.printStackTrace()
            Response.Failure(e)
        }
    }

    override suspend fun updatePassengerRequest(publish: Publish): Response<Boolean> {
        return try {

            val map: MutableMap<String, Any> = HashMap()
            map["pasajeros"] = publish.pasajeros
            map["plazasDisponibles"] = publish.plazasDisponibles

            publishRef.document(publish.id).update(map).await()
            Response.Success(true)

        } catch (e: Exception) {
            e.printStackTrace()
            Response.Failure(e)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun getPublishRides(): Flow<Response<List<Publish>>> = callbackFlow {

        val snapshotListener = publishRef.addSnapshotListener { snapshot, e ->

            GlobalScope.launch(Dispatchers.IO) {

                val publishResponse = if (snapshot != null) {
                    val publications = snapshot.toObjects(Publish::class.java)
                    val idUserArray = ArrayList<String>()

                    publications.forEach {
                        idUserArray.add(it.idUser)
                    }

                    //IDs SIN REPETIR
                    val idUserList = idUserArray.toSet().toList()

                    idUserList.map { id ->
                        async {
                            val user =
                                usersRef.document(id).get().await().toObject(User::class.java)!!
                            publications.forEach { publish ->
                                if (publish.idUser == id) {
                                    publish.user = user
                                }
                            }
                        }
                    }.forEach {
                        it.await()
                    }

                    Response.Success(publications)
                } else {
                    Response.Failure(e)
                }
                trySend(publishResponse)
            }
        }
        awaitClose {
            snapshotListener.remove()
        }
    }
    @OptIn(DelicateCoroutinesApi::class)
    override fun getPublishRidesByUserId(idUser: String): Flow<Response<List<Publish>>> =
        callbackFlow {
            val snapshotListener =
                publishRef.whereEqualTo("idUser", idUser).addSnapshotListener { snapshot, e ->

                    GlobalScope.launch(Dispatchers.IO) {


                    val publishResponse = if (snapshot != null) {
                        val publications = snapshot.toObjects(Publish::class.java)
                        snapshot.documents.forEachIndexed { index, document ->
                            publications[index].id = document.id
                        }

                        val idUserArray = ArrayList<String>()

                        publications.forEach {
                            idUserArray.add(it.idUser)
                        }

                        //IDs SIN REPETIR
                        val idUserList = idUserArray.toSet().toList()

                        idUserList.map { id ->
                            async {
                                val user =
                                    usersRef.document(id).get().await()
                                        .toObject(User::class.java)!!
                                publications.forEach { publish ->
                                    if (publish.idUser == id) {
                                        publish.user = user
                                    }
                                }
                            }
                        }.forEach {
                            it.await()
                        }

                        Response.Success(publications)
                    } else {
                        Response.Failure(e)
                    }
                    trySend(publishResponse)
                    }
                }
            awaitClose {
                snapshotListener.remove()
            }
        }

    @OptIn(DelicateCoroutinesApi::class)
    override fun getPublishRidesByPassengerId(idUser: String): Flow<Response<List<Publish>>> =
        callbackFlow {
            val snapshotListener = publishRef.addSnapshotListener { snapshot, e ->

                GlobalScope.launch(Dispatchers.IO) {


                val publishResponse = if (snapshot != null) {
                    val publications = snapshot.toObjects(Publish::class.java)

                    val filteredPublish = publications.filter {
                        it.pasajeros.any {
                            it.idPassenger == idUser
                        }
                    }

                    filteredPublish.forEachIndexed { index, document ->
                        publications[index].id = document.id
                    }

                    val idUserArray = ArrayList<String>()

                    publications.forEach {
                        idUserArray.add(it.idUser)
                    }

                    //IDs SIN REPETIR
                    val idUserList = idUserArray.toSet().toList()

                    idUserList.map { id ->
                        async {
                            val user =
                                usersRef.document(id).get().await()
                                    .toObject(User::class.java)!!
                            publications.forEach { publish ->
                                if (publish.idUser == id) {
                                    publish.user = user
                                }
                            }
                        }
                    }.forEach {
                        it.await()
                    }

                    Response.Success(filteredPublish)
                } else {
                    Response.Failure(e)
                }
                trySend(publishResponse)
                }
            }
            awaitClose {
                snapshotListener.remove()
            }
        }


    @OptIn(DelicateCoroutinesApi::class)
    override fun getPublishRidesByMunicipality(municipality: String): Flow<Response<List<Publish>>> =
        callbackFlow {
            val snapshotListener = publishRef.whereEqualTo("municipio", municipality)
                .addSnapshotListener { snapshot, e ->

                    GlobalScope.launch(Dispatchers.IO) {

                        val publishResponse = if (snapshot != null) {
                            val publications = snapshot.toObjects(Publish::class.java)
                            snapshot.documents.forEachIndexed { index, document ->
                                publications[index].id = document.id
                            }
                            val idUserArray = ArrayList<String>()

                            publications.forEach {
                                idUserArray.add(it.idUser)
                            }

                            //IDs SIN REPETIR
                            val idUserList = idUserArray.toSet().toList()

                            idUserList.map { id ->
                                async {
                                    val user =
                                        usersRef.document(id).get().await()
                                            .toObject(User::class.java)!!
                                    publications.forEach { publish ->
                                        if (publish.idUser == id) {
                                            publish.user = user
                                        }
                                    }
                                }
                            }.forEach {
                                it.await()
                            }

                            Response.Success(publications)
                        } else {
                            Response.Failure(e)
                        }
                        trySend(publishResponse)
                    }
                }
            awaitClose {
                snapshotListener.remove()
            }
        }

    override fun getPublishRideById(publishRide: Publish): Flow<Response<Publish?>> =
        callbackFlow {
            val snapshotListener =
                publishRef.whereEqualTo("id", publishRide.id).addSnapshotListener { snapshot, e ->

                    val publishResponse = try {
                        if (snapshot != null && !snapshot.isEmpty) {
                            val document = snapshot.documents[0]
                            val publication = document.toObject(Publish::class.java)
                            publication?.id = document.id

                            Response.Success(publication)
                        } else {
                            Response.Success(null)
                        }
                    } catch (exception: Exception) {
                        Response.Failure(exception)
                    }
                    trySend(publishResponse)
                }
            awaitClose { snapshotListener.remove() }

        }
}



