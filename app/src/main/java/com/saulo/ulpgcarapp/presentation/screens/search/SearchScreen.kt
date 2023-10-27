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
import com.saulo.ulpgcarapp.presentation.screens.search.components.SearchBarContent
import java.net.URLEncoder

@Composable
fun SearchScreen(navController: NavHostController) {

    Scaffold(
        content = {
            SearchBarContent()
        }
    )
}