package com.saulo.ulpgcarapp.presentation.screens.your_rides.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.saulo.ulpgcarapp.R
import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.presentation.screens.publish_a_ride.components.RidesCard
import com.saulo.ulpgcarapp.presentation.screens.your_rides.YourRidesViewModel
import com.saulo.ulpgcarapp.presentation.ui.theme.Orange400

@Composable
fun YourRidesContent(
    publishRides: List<Publish>,
    navController: NavHostController,
    viewModel: YourRidesViewModel = hiltViewModel()
) {

    if (publishRides.size != 0) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, top = 70.dp, bottom = 75.dp)
        ) {
            items(items = publishRides) {
                YourRidesCard(
                    navController = navController,
                    publishRide = it,
                    modifier = Modifier.clickable {

                    })
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize().padding(top = 64.dp)) {

            Column() {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    painter = painterResource(id = R.drawable.notpublications),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    alpha = 0.6f
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "No hay viajes solicitados",
                    modifier = Modifier.padding(all = 20.dp),
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Orange400,
                    textAlign = TextAlign.Justify
                )
            }
        }
    }

}