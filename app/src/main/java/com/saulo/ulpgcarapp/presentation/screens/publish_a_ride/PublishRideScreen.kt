package com.saulo.ulpgcarapp.presentation.screens.publish_a_ride

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.AddLocationAlt
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.presentation.components.DefaultTopBar
import com.saulo.ulpgcarapp.presentation.navigation.DetailsScreen
import com.saulo.ulpgcarapp.presentation.screens.publish_a_ride.components.GetPublishRides
import com.saulo.ulpgcarapp.presentation.screens.publish_a_ride.components.UpdateRoutePublishRide


@Composable
fun PublishRideScreen(navController: NavHostController, viewModel: PublishRideViewModel = hiltViewModel()) {

    Scaffold(
        topBar = {
            DefaultTopBar(
                title = "Tus viajes publicados",
                upAvailable = false,
                navController = navController
            )
        },
        content = {
            GetPublishRides(navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(bottom = 80.dp),
                onClick = { navController.navigate(DetailsScreen.PublishNewRide.route) }
            ) {
                Icon(
                    imageVector = Icons.Default.AddLocationAlt,
                    contentDescription = "Button navigaiton to publish new ride"
                )
            }
        }
    )
    //UpdateRoutePublishRide()
}