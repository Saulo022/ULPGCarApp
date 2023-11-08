package com.saulo.ulpgcarapp.presentation.screens.driver_route.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.maps.android.compose.GoogleMap

@Composable
fun DriverRouteContent() {

    Column() {
        GoogleMap(modifier = Modifier.fillMaxSize())

    }
}
