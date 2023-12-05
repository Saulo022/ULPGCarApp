package com.saulo.ulpgcarapp.presentation.screens.publish_a_ride

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saulo.ulpgcarapp.data.network.response.Matrix
import com.saulo.ulpgcarapp.domain.model.Location
import com.saulo.ulpgcarapp.domain.model.Passenger
import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.domain.model.Response
import com.saulo.ulpgcarapp.domain.use_cases.auth.AuthUseCases
import com.saulo.ulpgcarapp.domain.use_cases.publish.PublishUseCases
import com.saulo.ulpgcarapp.domain.use_cases.routes.RoutesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PublishRideViewModel @Inject constructor(
    private val publishUseCases: PublishUseCases,
    private val routeUseCase: RoutesUseCases,
    private val authUseCases: AuthUseCases
) : ViewModel() {

    //STATE PUBLISH SCREEN
    var state by mutableStateOf(PublishRideState())
        private set

    var publishRidesResponse by mutableStateOf<Response<List<Publish>>?>(null)
    var deleteResponse by mutableStateOf<Response<Boolean>?>(null)
    var updatePublishRideResponse by mutableStateOf<Response<Boolean>?>(null)
        private set

    val currentUser = authUseCases.getCurrentUser()

    var matrixTime: List<Double> = emptyList()
    var orderedStopsList: MutableList<String> = mutableListOf()


    init {
        getPublisRides()
    }

    fun delete(idPost: String) {
        viewModelScope.launch {
            deleteResponse = Response.Loading
            val result = publishUseCases.deletePublishRide(idPost)
            deleteResponse = result
        }
    }

    fun getPublisRides() {
        viewModelScope.launch {
            publishRidesResponse = Response.Loading
            publishUseCases.getPublishRidesByUserId(currentUser?.uid ?: "").collect() {
                publishRidesResponse = it
            }
        }
    }

    fun getMatrixCoordinates(
        startLocation: Location,
        passengersList: MutableList<Passenger>,
        endLocation: Location,
        publish: Publish
    ) {
        val matrixCoordinates: MutableList<List<Double>> = mutableListOf()
        matrixCoordinates.add(
            listOf(
                startLocation.longitude.toDouble(),
                startLocation.latitude.toDouble()
            )
        )
        for (coordenadas in passengersList) {
            matrixCoordinates.add(
                listOf(
                    coordenadas.longitude.toDouble(),
                    coordenadas.latitude.toDouble()
                )
            )
        }
        Log.d("Saulo", "PublishRideViewModelMatrixCoordinates + ${matrixCoordinates}")

        viewModelScope.launch {
            val matrixJob = launch {
                getMatrix(matrixCoordinates, startLocation, passengersList, endLocation, publish)
                Log.d("Saulo", "PublishRideViewModelMatrixCoordinates222")
            }
            matrixJob.join()

            val orderJob = launch {
                orderStops(passengersList, startLocation, endLocation)
                Log.d("Saulo", "PublishRideViewModelMatrixCoordinates555")
            }
            orderJob.join()


            val updateJob = launch {
                onUpdateRide(publish)
                Log.d("Saulo", "PublishRideViewModelMatrixCoordinates888 + ${publish}")
            }
            updateJob.join()

        }
    }

    suspend fun getMatrix(
        matrixCoordinates: MutableList<List<Double>>,
        startLocation: Location,
        passengersList: MutableList<Passenger>,
        endLocation: Location,
        publish: Publish
    ) {

        val result = routeUseCase.matrixUseCase(
            matrix = Matrix(
                locations = matrixCoordinates
            )
        )
            matrixTime = result[0]
            Log.d("Saulo", "PublishRideViewModelMatrixCoordinates111 + ${result[0]}")
        }



    fun orderStops(
        passengersList: MutableList<Passenger>,
        startLocation: Location,
        endLocation: Location
    ) {
        if (passengersList.size >= 2) {
            val sortList = matrixTime
            var sortListIndex = 0
            val listOrderedStops: MutableList<List<Double>> = mutableListOf()
            var list: List<Double> = emptyList()


            for (duration in sortList.sorted()) {
                var notSortListIndex = 0
                for (coordinates in matrixTime) {
                    if (duration == coordinates && duration != 0.0) {
                        notSortListIndex = notSortListIndex - 1
                        list = listOf(
                            passengersList[notSortListIndex].longitude.toDouble(),
                            passengersList[notSortListIndex].latitude.toDouble()
                        )
                        listOrderedStops.add(list)
                    }
                    notSortListIndex = notSortListIndex + 1
                }
                sortListIndex = sortListIndex + 1
            }
            listOrderedStops.add(
                0,
                listOf(startLocation.longitude.toDouble(), startLocation.latitude.toDouble())
            )
            listOrderedStops.add(
                listOf(
                    endLocation.longitude.toDouble(),
                    endLocation.latitude.toDouble()
                )
            )
            Log.d("Saulo", "PublishRideViewModelMatrixCoordinates333 + ${listOrderedStops}")

            orderedStopsList = listOrderedStops.map { "${it[0]},${it[1]}" } as MutableList<String>

            Log.d("Saulo", "PublishRideViewModelMatrixCoordinates444 + ${orderedStopsList}")
        }

    }


    suspend fun onUpdateRide(publish: Publish) {
        val updatePublish = Publish(
            id = publish.id,
            origin = publish.origin,
            municipio = publish.municipio,
            destination = publish.destination,
            fecha = publish.fecha,
            hora = publish.hora,
            numeroPasajeros = publish.numeroPasajeros,
            pasajeros = publish.pasajeros,
            precioViaje = publish.precioViaje,
            idUser = currentUser?.uid ?: "",
            route = orderedStopsList
        )
        Log.d("Saulo", "PublishRideViewModelPublish666 + ${updatePublish.route}")

            updatePublishRideResponse = Response.Loading
            val result = publishUseCases.updatePublishRide(updatePublish)
            updatePublishRideResponse = result

        Log.d("Saulo", "PublishRideViewModelPublish777 + ${updatePublish.route}")
    }




}