package com.saulo.ulpgcarapp.presentation.screens.your_rides.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.presentation.components.InformationPill
import com.saulo.ulpgcarapp.presentation.navigation.DetailsScreen
import com.saulo.ulpgcarapp.presentation.screens.publish_a_ride.PublishRideViewModel

@Composable
fun YourRidesCard(
    publishRide: Publish,
    navController: NavHostController,
    viewModel: PublishRideViewModel = hiltViewModel(),
    modifier: Modifier
) {

    Card(
        elevation = CardDefaults.cardElevation(),
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
            .padding(bottom = 10.dp)
    ) {
        Column(modifier = Modifier.padding(all = 10.dp)) {
            /*
            Row(verticalAlignment = Alignment.CenterVertically) {

                if (publishRide.user?.image != "") {
                    AsyncImage(
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape),
                        model = publishRide.user?.image,
                        contentDescription = "User image",
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        modifier = Modifier.size(70.dp),
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = ""
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))

                Text(text = publishRide.user?.username ?: "", fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic, fontSize = 20.sp)
            }
                 */
            Row() {

                Text(
                    text = "Origen",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(all = 10.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 5.dp), horizontalArrangement = Arrangement.End
                ) {

                    Image(imageVector = Icons.Default.DirectionsCar,
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(
                            Color.Green
                        ),
                        modifier = Modifier.clickable {
                            if (publishRide.pasajeros.size != 0){
                                viewModel.getMatrixCoordinates(publishRide.origin,publishRide.pasajeros,publishRide.destination,publishRide)
                            }
                            navController.navigate(route = DetailsScreen.DriverRoute.passPublishRide(publishRide.toJson()))
                        })

                    Spacer(modifier = Modifier.width(10.dp))

                    Image(imageVector = Icons.Default.Delete,
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(
                            Color.Red
                        ),
                        modifier = Modifier.clickable { viewModel.delete(publishRide.id) })
                }
            }

            InformationPill(text = publishRide.origin.label)


            Text(
                text = "Destino",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(all = 10.dp)
            )

            InformationPill(text = publishRide.destination.label)

            Text(
                text = "Fecha y hora",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(all = 10.dp)
            )

            Row() {

                InformationPill(text = publishRide.fecha)

                Spacer(modifier = Modifier.width(10.dp))

                InformationPill(text = publishRide.hora)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 0.dp, end = 5.dp, top = 3.dp, bottom = 0.dp), horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Precio",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(all = 10.dp)
                )
                VerticalDivider(
                    modifier = Modifier
                        .height(height = 35.dp)
                        .padding(top = 10.dp, start = 10.dp, end = 10.dp),
                    thickness = 0.5.dp,
                    color = Color.Black
                )
                Text(
                    text = "Pasajeros",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(all = 10.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 5.dp), horizontalArrangement = Arrangement.Start
            ) {
                InformationPill(
                    text = "${publishRide.precioViaje}" + "€",
                    modifier = Modifier.padding(start = 0.dp)
                )
                Spacer(modifier = Modifier.width(40.dp))
                InformationPill(text = "${publishRide.numeroPasajeros}")
            }

        }
    }
}