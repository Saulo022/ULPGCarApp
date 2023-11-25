package com.saulo.ulpgcarapp.presentation.screens.request_a_ride.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.saulo.ulpgcarapp.R
import com.saulo.ulpgcarapp.presentation.components.DefaultButton
import com.saulo.ulpgcarapp.presentation.screens.request_a_ride.RequestRideViewModel
import com.saulo.ulpgcarapp.presentation.ui.theme.Blue400
import com.saulo.ulpgcarapp.presentation.ui.theme.Orange400


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestRideContent(viewModel: RequestRideViewModel = hiltViewModel()) {

    val state = viewModel.state

    var active by remember { mutableStateOf(false) }

    val ctx = LocalContext.current

    Box(modifier = Modifier.fillMaxWidth().padding(top = 50.dp)) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .background(Blue400)
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    modifier = Modifier
                        .height(100.dp)
                        .padding(top = 20.dp),
                    painter = painterResource(id = R.drawable.driverlesscar),
                    contentDescription = "Search Ride Image"
                )

            }
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {


            Card(
                modifier = Modifier
                    .height(600.dp)
                    .width(400.dp)
                    .padding(start = 10.dp, end = 10.dp, top = 110.dp)
            ) {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {

                    Text(
                        text = "Elija una parada",
                        modifier = Modifier.padding(all = 20.dp),
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Orange400,
                        textAlign = TextAlign.Justify
                    )

                    SearchBar(
                        query = state.stopLocation,
                        onQueryChange = {
                            viewModel.onSearchInput(it)
                            viewModel.onSearchSelected()
                        },
                        onSearch = {
                            active = false
                        },
                        active = active,
                        onActiveChange = { active = it },
                        modifier = Modifier.padding(0.dp),
                        placeholder = { Text(text = "Buscar Origen ") },
                        leadingIcon = {
                            IconButton(onClick = { viewModel.onSearchDelete() }) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = null)
                            }
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = ""
                            )
                        }
                    ) {

                        LazyColumn(modifier = Modifier.fillMaxSize()) {

                            val filteredSearchs = state.searchList.filter {
                                it.properties.label.contains(state.stopLocation, true)
                            }

                            items(filteredSearchs) { result ->
                                Text(
                                    text = result.properties.label,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .clickable {
                                            viewModel.onSearchInput(result.properties.label)
                                            viewModel.onCoordinatesInput(
                                                longitude = "${result.geometry.coordinates[0]}",
                                                latitude = "${result.geometry.coordinates[1]}"
                                            )
                                            active = false
                                        },
                                )
                            }
                        }

                    }

                }

            }
            Spacer(modifier = Modifier.height(5.dp))

            DefaultButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                text = "Continuar",
                onClick = { viewModel.onUpdateRide() },
                color = Orange400
            )
        }
    }
}

