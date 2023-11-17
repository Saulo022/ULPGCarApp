package com.saulo.ulpgcarapp.presentation.screens.driver_route.components


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.saulo.ulpgcarapp.presentation.components.DefaultButton
import com.saulo.ulpgcarapp.presentation.screens.driver_route.DriveRouteViewModel

@Composable
fun DriverRouteContent(viewModel: DriveRouteViewModel = hiltViewModel()) {

    /*
    val startLocation = LatLng(
        viewModel.state.origin.latitude.toDouble(),
        viewModel.state.origin.longitude.toDouble()
    )

    val endLocation = LatLng(
        viewModel.state.destination.latitude.toDouble(),
        viewModel.state.destination.longitude.toDouble()
    )
         */

    val firstLocation = LatLng(
        27.99508,
        -15.61543
    )

    val secondLocation = LatLng(
        28.060615,
        -15.545412
    )
    val thirdLocation = LatLng(
        27.902829,
        -15.446121
    )



    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(firstLocation, 15f)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(firstLocation),
                title = "Inicio del trayecto",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
            )
            Polyline(points = viewModel.state.polyline, color = Color.Green)
            Marker(
                state = MarkerState(secondLocation),
                title = "Inicio del trayecto",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)
            )

            Marker(
                state = MarkerState(thirdLocation),
                title = "Inicio del trayecto",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
            )
        }

        Column() {
            DefaultButton(modifier = Modifier, text = "HOLA", onClick = { })
        }
    }
}
