package com.saulo.ulpgcarapp.presentation.screens.your_rides.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.presentation.components.DefaultButton
import com.saulo.ulpgcarapp.presentation.components.InformationPill
import com.saulo.ulpgcarapp.presentation.navigation.DetailsScreen
import com.saulo.ulpgcarapp.presentation.screens.publish_a_ride.PublishRideViewModel
import com.saulo.ulpgcarapp.presentation.screens.your_rides.YourRidesViewModel
import com.saulo.ulpgcarapp.presentation.ui.theme.Blue400
import com.saulo.ulpgcarapp.presentation.ui.theme.Orange400
import com.saulo.ulpgcarapp.R

@Composable
fun YourRidesCard(
    publishRide: Publish,
    navController: NavHostController,
    modifier: Modifier,
    viewModel: YourRidesViewModel = hiltViewModel()
) {
    var pasajero = viewModel.findCurrentUser(publishRide)

    Card(
        elevation = CardDefaults.cardElevation(),
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
            .padding(bottom = 10.dp)
    ) {
        Column(modifier = Modifier.padding(all = 10.dp)) {

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

                Text(
                    text = publishRide.user?.username ?: "",
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    fontSize = 20.sp
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 5.dp), horizontalArrangement = Arrangement.Start
            ) {
                if (pasajero != null) {
                    Row() {
                        Text(text = "Solicitud", fontStyle = FontStyle.Italic, fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = pasajero.requestState,
                            fontStyle = FontStyle.Italic,
                            fontSize = 20.sp,
                            color = if (pasajero.requestState == "Pendiente") Color.Gray else Color.Green
                        )
                    }
                }


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 5.dp), horizontalArrangement = Arrangement.End
                ) {
                    if (pasajero != null) {

                        if (pasajero.requestState == "Pendiente") {

                        } else {

                            Image(imageVector = Icons.Default.DirectionsCar,
                                contentDescription = "",
                                colorFilter = ColorFilter.tint(
                                    Color.Green
                                ),
                                modifier = Modifier.clickable {
                                    navController.navigate(
                                        route = DetailsScreen.DriverRoute.passPublishRide(
                                            publishRide.toJson()
                                        )
                                    )
                                })
                        }
                    }
                }
            }

            Row() {

                Text(
                    text = "Origen",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(all = 10.dp)
                )


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

            Text(
                text = "Matrícula del vehículo",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(all = 10.dp)
            )

            Row() {
                InformationPill(text = publishRide.plate)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 0.dp, end = 5.dp, top = 3.dp, bottom = 0.dp),
                horizontalArrangement = Arrangement.Start
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 5.dp), horizontalArrangement = Arrangement.End
                ) {

                    if (pasajero != null) {

                        if (pasajero.requestState == "Pendiente") {
                        } else {
                            DefaultButton(modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .padding(vertical = 5.dp, horizontal = 5.dp),
                                text = "Chat",
                                icon = Icons.Default.Chat,
                                color = Orange400,
                                mainColor = Blue400,
                                onClick = {
                                    navController.navigate(
                                        route = DetailsScreen.PublicationChat.passPublishRide(
                                            publishRide.toJson()
                                        )
                                    )
                                })
                        }
                    }
                }
            }

        }
    }
}