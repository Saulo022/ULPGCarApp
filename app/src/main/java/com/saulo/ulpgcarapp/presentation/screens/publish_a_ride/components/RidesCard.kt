package com.saulo.ulpgcarapp.presentation.screens.publish_a_ride.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
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
import com.saulo.ulpgcarapp.R
import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.presentation.navigation.DetailsScreen
import com.saulo.ulpgcarapp.presentation.screens.publish_a_ride.PublishRideViewModel
import com.saulo.ulpgcarapp.presentation.ui.theme.Red200

@Composable
fun RidesCard(
    publishRide: Publish,
    navController: NavHostController,
    viewModel: PublishRideViewModel = hiltViewModel()
) {

    Card(
        elevation = CardDefaults.cardElevation(),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(bottom = 10.dp)
            .clickable { navController.navigate(route = DetailsScreen.UpdatePublishRide.passPublishRide(publishRide.toJson())) }
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
                    text = "Fecha y hora",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 10.dp)
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
                        modifier = Modifier.clickable { viewModel.delete(publishRide.id) })
                }
            }

            Row() {
                Text(text = publishRide.fecha)

                Spacer(modifier = Modifier.width(10.dp))

                Text(text = publishRide.hora)
            }

            Text(
                text = "Origen",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 10.dp)
            )

            Text(text = publishRide.origen)

            Text(
                text = "Destino",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 10.dp)
            )

            Text(text = publishRide.destino)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 5.dp), horizontalArrangement = Arrangement.End
            ) {
                Text(text = "Precio", fontWeight = FontWeight.Bold)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 5.dp), horizontalArrangement = Arrangement.End
            ) {
                Text(text = publishRide.precio + "€")
            }

        }
    }
}