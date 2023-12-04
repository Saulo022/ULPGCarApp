package com.saulo.ulpgcarapp.presentation.screens.search_result.components

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.saulo.ulpgcarapp.R
import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.presentation.components.DefaultButton
import com.saulo.ulpgcarapp.presentation.components.InformationPill
import com.saulo.ulpgcarapp.presentation.navigation.DetailsScreen
import com.saulo.ulpgcarapp.presentation.screens.publish_a_ride.PublishRideViewModel
import com.saulo.ulpgcarapp.presentation.screens.search_result.SearchResultViewModel
import com.saulo.ulpgcarapp.presentation.ui.theme.Blue400
import com.saulo.ulpgcarapp.presentation.ui.theme.Orange400
import com.saulo.ulpgcarapp.presentation.ui.theme.Red200

@SuppressLint("SuspiciousIndentation")
@Composable
fun RidesResultsCard(
    publishRide: Publish,
    navController: NavHostController,
    viewModel: SearchResultViewModel = hiltViewModel(),
    modifier: Modifier
) {
    val ctx = LocalContext.current

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
                    Image(imageVector = Icons.Default.Delete,
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(
                            Color.Red
                        ),
                        modifier = Modifier.clickable { })
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
                    text = "Plazas disponibles",
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, top = 5.dp),
                horizontalArrangement = Arrangement.End
            ) {
                DefaultButton(
                    modifier = Modifier,
                    text = if (publishRide.numeroPasajeros == 0) "No hay plazas disponibles" else if (viewModel.checkRequestState(
                            publishRide
                        ) == "Pendiente"
                    ) "Solicitud pendiente" else if (viewModel.checkRequestState(publishRide) == "Denegada") "Solicitud denegada" else "Solicitar plaza",
                    onClick = {
                        if (publishRide.numeroPasajeros == 0) else

                        navController.navigate(
                            route = DetailsScreen.RequestRide.passPublishRide(
                                publishRide.toJson()
                            )
                        )
                    },
                    color = if (publishRide.numeroPasajeros == 0) Color.Red else if (viewModel.checkRequestState(publishRide) == "Denegada") Color.Magenta else Color.Green,
                    icon = null,
                    enabled = if (viewModel.checkRequestState(publishRide) == "Pendiente") false else true
                )
            }

        }
    }
}