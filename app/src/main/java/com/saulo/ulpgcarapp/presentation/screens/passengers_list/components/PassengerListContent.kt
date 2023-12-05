package com.saulo.ulpgcarapp.presentation.screens.passengers_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.presentation.screens.passengers_list.PassengerListViewModel
import com.saulo.ulpgcarapp.presentation.screens.update_publish_ride.UpdatePublishRideViewModel


@Composable
fun PassengerListContent(publishRides: Publish) {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 70.dp, bottom = 75.dp)
    ) {

            items(items = publishRides.pasajeros) {
                PassengerCard(
                    passenger = it,
                    modifier = Modifier.clickable {

                    })
        }
    }
}