package com.saulo.ulpgcarapp.presentation.screens.driver_route

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.saulo.ulpgcarapp.core.Constants.API_KEY
import com.saulo.ulpgcarapp.data.network.response.Matrix
import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.domain.model.Response
import com.saulo.ulpgcarapp.domain.use_cases.routes.RoutesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DriveRouteViewModel @Inject constructor(
    private val routeUseCase: RoutesUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    //STATE PUBLISH SCREEN
    var state by mutableStateOf(DriveRouteState())
        private set

    //ARGUMENTS
    val data = savedStateHandle.get<String>("publish")
    val publish = Publish.fromJson(data!!)

    var driveRouteResponse by mutableStateOf<Response<Boolean>?>(null)
        private set

    init {
        state = state.copy(
            origin = publish.origin,
            destination = publish.destination,
            municipality = publish.municipio,
            timeChoose = publish.hora,
            dateChoose = publish.fecha,
            passengersList = publish.pasajeros,
            passengers = publish.numeroPasajeros,
            price = publish.precioViaje
        )
        getRoute()
        getMatrixCoordinates()
        getMatrix()
    }

    fun getMatrixCoordinates() {
        state.matrixCoordinates.add(
            listOf(
                state.origin.longitude.toDouble(),
                state.origin.latitude.toDouble()
            )
        )
        for (coordenadas in state.passengersList) {
            state.matrixCoordinates.add(
                listOf(
                    coordenadas.longitude.toDouble(),
                    coordenadas.latitude.toDouble()
                )
            )
        }
        Log.d("Saulo", "MatrixCoordinates + ${state.matrixCoordinates}")
    }

    fun getMatrix() {
        viewModelScope.launch {
            val result = routeUseCase.matrixUseCase(
                matrix = Matrix(
                    locations = listOf(
                        listOf(9.70093, 48.477473),
                        listOf(9.207916, 49.153868),
                        listOf(37.573242, 55.801281),
                        listOf(115.663757, 38.106467)
                    )
                )
            )
            state = state.copy(matrixTime = result[0])
        }
    }

    fun getRoute() {
        viewModelScope.launch {
            val result = routeUseCase.getrouteUseCase(
                apiKey = API_KEY,
                start = "${state.origin.longitude},${state.origin.latitude}",
                end = "${state.destination.longitude},${state.destination.latitude}"
            )
            state = state.copy(route = result)

            val poly: MutableList<com.google.android.gms.maps.model.LatLng> = mutableListOf()

            state.route.features.first().geometry.coordinates.forEach {
                poly.add(LatLng(it[1], it[0]))
            }
            state = state.copy(polyline = poly)
        }
    }

}