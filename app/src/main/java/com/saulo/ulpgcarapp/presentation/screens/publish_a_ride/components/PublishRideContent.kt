package com.saulo.ulpgcarapp.presentation.screens.publish_a_ride.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.saulo.ulpgcarapp.domain.model.Publish

@Composable
fun PublishRideContent(publishRides: List<Publish>) {
    
    LazyColumn(modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 75.dp)) {
        items(items = publishRides) {
            RidesCard(publishRide = it)
        }
    }
    
}