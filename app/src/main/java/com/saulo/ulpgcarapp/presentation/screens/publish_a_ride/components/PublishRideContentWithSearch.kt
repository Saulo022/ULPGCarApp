package com.saulo.ulpgcarapp.presentation.screens.publish_a_ride.components

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.saulo.ulpgcarapp.presentation.components.DefaultSearchBar
import com.saulo.ulpgcarapp.presentation.screens.publish_a_ride.PublishRideViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublishRideContentWithSearch(viewModel: PublishRideViewModel = hiltViewModel()) {

    var active by remember { mutableStateOf(false) }

    var active2 by remember { mutableStateOf(false) }

    val ctx = LocalContext.current

    val state = viewModel.state
    val stateList = viewModel.stateList

    val stateReturn = viewModel.stateReturn
    val stateReturnList = viewModel.stateReturnList

    Scaffold {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Top
        ) {


            SearchBar(
                query = state.search,
                onQueryChange = {
                    viewModel.onSearchInput(it)
                    viewModel.onSearchSelected()
                },
                onSearch = {
                    Toast.makeText(ctx, state.search, Toast.LENGTH_LONG).show()
                    //searchViewModel.onSearchSelected()
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
                trailingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "") }
            ) {

                LazyColumn(modifier = Modifier.fillMaxSize()) {

                    val filteredSearchs = stateList.searchList.filter {
                        it.properties.label.contains(state.search, true)
                    }

                    items(filteredSearchs) { result ->
                        Text(
                            text = result.properties.label,
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable {
                                    Toast
                                        .makeText(ctx, result.properties.label, Toast.LENGTH_LONG)
                                        .show()
                                    Toast
                                        .makeText(
                                            ctx,
                                            result.geometry.coordinates.toString(),
                                            Toast.LENGTH_LONG
                                        )
                                        .show()
                                    viewModel.onSearchInput(result.properties.label)
                                    active = false
                                },
                        )
                    }
                }

            }

            Spacer(modifier = Modifier.height(30.dp))

            SearchBar(
                query = stateReturn.searchReturn,
                onQueryChange = {
                    viewModel.onSearchReturnInput(it)
                    viewModel.onSearchReturnSelected()
                },
                onSearch = {
                    Toast.makeText(ctx, stateReturn.searchReturn, Toast.LENGTH_LONG).show()
                    active2 = false
                },
                active = active2,
                onActiveChange = { active2 = it },
                placeholder = { Text(text = "Buscar Destino ") },
                leadingIcon = {
                    IconButton(onClick = { viewModel.onSearchReturnDelete() }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }
                },
                trailingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "") }
            ) {

                LazyColumn(modifier = Modifier.fillMaxSize()) {

                    val filteredReturnSearchs = stateReturnList.searchReturnList.filter {
                        it.properties.label.contains(stateReturn.searchReturn, true)
                    }

                    items(filteredReturnSearchs) { result ->
                        Text(
                            text = result.properties.label,
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable {
                                    Toast
                                        .makeText(ctx, result.properties.label, Toast.LENGTH_LONG)
                                        .show()
                                    Toast
                                        .makeText(
                                            ctx,
                                            result.geometry.coordinates.toString(),
                                            Toast.LENGTH_LONG
                                        )
                                        .show()
                                    viewModel.onSearchReturnInput(result.properties.label)
                                    active2 = false
                                },
                        )
                    }
                }

            }

            /*
            Box(modifier = Modifier
                .background(Color.Blue)
                .padding(10.dp)
                .align(Alignment.CenterHorizontally)) {

                val singapore = LatLng(1.35, 103.87)

                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(singapore, 10f)
                }
                //GoogleMap(modifier = Modifier.fillMaxSize(), cameraPositionState = cameraPositionState)

            }*/

        }
    }

}
