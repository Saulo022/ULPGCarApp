package com.saulo.ulpgcarapp.presentation.screens.driver_route

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.compose.Polyline
import com.saulo.ulpgcarapp.core.Constants.API_KEY
import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.domain.model.Response
import com.saulo.ulpgcarapp.domain.use_cases.routes.RoutesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DriveRouteViewModel @Inject constructor(
    private val routeUseCase: RoutesUseCases,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

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
            passengers = publish.numeroPasajeros,
            price = publish.precioViaje
        )
        Log.d("Saulo", "onDriveRouteViewModel: OK +  ${publish.id}")
        getRoute()
        //drawRoute()
        //createRoute()
    }


    fun getRoute() {
        viewModelScope.launch {
            val result = routeUseCase.getrouteUseCase(
                apiKey = API_KEY,
                start = "${state.origin.longitude},${state.origin.latitude}",
                end = "${state.destination.longitude},${state.destination.latitude}"
            )
            state = state.copy(route = result)
            Log.d("Saulo", "onDriveRouteViewModel: OK +  ${state.route}")
            Log.d("Saulo", "onDriveRouteViewModel: OK +  ${state.route.features.first().geometry.coordinates[0]}")

            val poly: MutableList<com.google.android.gms.maps.model.LatLng> = mutableListOf()

            state.route.features.first().geometry.coordinates.forEach {
                poly.add(LatLng(it[1], it[0]))
            }
            state = state.copy(polyline = poly)
            Log.d("Saulo", "onDriveRouteViewModel: OK +  ${state.polyline}")
        }
    }
    /*
   fun createRoute() {
       CoroutineScope(Dispatchers.IO).launch {
           val result = routeUseCase.getrouteUseCase(
               apiKey = API_KEY,
               start = "${state.origin.longitude},${state.origin.latitude}",
               end = "${state.destination.longitude},${state.destination.latitude}"
           )
           state = state.copy(route = result)
           drawRoute()
       }
   }


   fun drawRoute() {
       val polyLineOptions = PolylineOptions()
       state.route.features.first().geometry.coordinates.forEach {
           polyLineOptions.add(LatLng(it[1], it[0]))
       }
   }

    */
}