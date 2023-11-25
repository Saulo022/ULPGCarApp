package com.saulo.ulpgcarapp.presentation.screens.search_result.components

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.saulo.ulpgcarapp.domain.model.Publish


@Composable
fun SearchResultContent(publishRides: List<Publish>, navController: NavHostController) {

    val ctx = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 70.dp, bottom = 15.dp)
    ) {
        items(items = publishRides) {
            RidesResultsCard(
                navController = navController,
                publishRide = it,
                modifier = Modifier.clickable { Toast.makeText(ctx, it.id, Toast.LENGTH_SHORT).show()})
        }
    }

}