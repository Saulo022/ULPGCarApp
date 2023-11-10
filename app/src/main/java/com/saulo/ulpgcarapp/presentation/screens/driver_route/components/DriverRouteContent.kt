package com.saulo.ulpgcarapp.presentation.screens.driver_route.components

import android.webkit.WebSettings.ZoomDensity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.saulo.ulpgcarapp.R
import com.saulo.ulpgcarapp.presentation.screens.driver_route.DriveRouteViewModel
import com.saulo.ulpgcarapp.presentation.screens.update_publish_ride.UpdatePublishRideViewModel

@Composable
fun DriverRouteContent(viewModel: DriveRouteViewModel = hiltViewModel()) {

    val startLocation = LatLng(
        viewModel.state.origin.latitude.toDouble(),
        viewModel.state.origin.longitude.toDouble()
    )

    val endLocation = LatLng(
        viewModel.state.destination.latitude.toDouble(),
        viewModel.state.destination.longitude.toDouble()
    )

    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(startLocation, 15f)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(startLocation),
                title = "Inicio del trayecto",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
            )
            Polyline(points = viewModel.state.polyline, color = Color.Green)
            Marker(
                state = MarkerState(endLocation),
                title = "Inicio del trayecto",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
            )

        }

    }
}
