package com.saulo.ulpgcarapp.presentation.screens.search

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Polyline
import java.net.URLEncoder

@Composable
fun SearchScreen(navController: NavHostController) {

    Scaffold(
        content = {
            Text(text = "SearchScreen")

            val ctx = LocalContext.current

            Box(modifier = Modifier.fillMaxSize()) {

                val encodedCoordinates = URLEncoder.encode("37.4222222, -122.0833333", "UTF-8")
                val queryString = "q=${encodedCoordinates}"

                 fun openGoogleMaps(latitude: String, longitude: String) {
                    val uri = "geo:$latitude,$longitude"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                    intent.setPackage("com.google.android.apps.maps")
                    startActivity(ctx,intent, null)
                }

                 val googleMapsApiKey = "AIzaSyALFu7egKJNykpKR6A6RJMdg10ckaEfuPs"

                 fun openGoogleMapsWithDirections(
                    startLatitude: String,
                    startLongitude: String,
                    endLatitude: String,
                    endLongitude: String
                ) {
                    val uri = "https://www.google.com/maps/dir/$startLatitude,$startLongitude/$endLatitude,$endLongitude"
                     //val uri = "https://www.google.com/maps/dir/?api=1" +
                     //                             "&origin=$startLatitude,$startLongitude" +
                     //                             "&destination=$endLatitude,$endLongitude" +
                     //                             "&dir_action=navigate"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                    startActivity(ctx,intent, null)
                }


            Button(onClick = {
                    // Abre Google Maps
                //val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/?${queryString}"))
                //  startActivity(ctx,mapIntent,null)
                //openGoogleMaps("28.0997300","-15.4134300")
                openGoogleMapsWithDirections("28.127455","-15.437416","28.094827","-15.456349")
                }) {
                    Text("Abrir Google Maps")
                }


            }

        }
    )

}