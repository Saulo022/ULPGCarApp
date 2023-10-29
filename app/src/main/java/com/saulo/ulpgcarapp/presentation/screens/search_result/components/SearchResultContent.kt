package com.saulo.ulpgcarapp.presentation.screens.search_result.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.presentation.screens.publish_a_ride.components.RidesCard

@Composable
fun SearchResultContent(publishRides: List<Publish>, navController: NavHostController) {

    LazyColumn(modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 75.dp)) {
        items(items = publishRides) {
            RidesCard(navController = navController,publishRide = it, modifier = Modifier)
        }
    }

}