package com.saulo.ulpgcarapp.presentation.screens.driver_route.components



import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.saulo.ulpgcarapp.domain.model.Passenger
import com.saulo.ulpgcarapp.presentation.screens.driver_route.DriveRouteViewModel
import kotlin.math.roundToLong

@Composable
fun DriverRouteContent(viewModel: DriveRouteViewModel = hiltViewModel()) {

    val routeTimes = viewModel.state.routeTimes

    val latLngList: List<LatLng> = viewModel.state.optimalRoute.map {
        val cleanedString = it.replace("[", "").replace("]", "")
        val (lat, lng) = cleanedString.split(",").map { it.toDouble() }
        LatLng(lng, lat)
    }

    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(latLngList[0], 15f)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            cameraPositionState = cameraPositionState
        ) {

            if (routeTimes.isEmpty()){

            } else {


            var i = 0
            for (latLng in latLngList){
                val optimalRoute: MutableList<Passenger> = viewModel.getlocations()
                var numeroRedondeado = (routeTimes[i]/60).roundToLong()
                Marker(
                    state = MarkerState(latLng),
                    title = optimalRoute[i].placeName,
                    snippet = if (routeTimes[i] == 0.0) "Inicio del trayecto" else numeroRedondeado.toString() + " minutos aproximadamente",
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
                )
                i++
            }

            Polyline(points = viewModel.state.polyline, color = Color.Green)

        }
        }
    }

}
