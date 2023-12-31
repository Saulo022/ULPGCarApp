package com.saulo.ulpgcarapp.presentation.screens.update_publish_ride.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import com.saulo.ulpgcarapp.R
import com.saulo.ulpgcarapp.presentation.components.DefaultButton
import com.saulo.ulpgcarapp.presentation.components.DefaultTextField
import com.saulo.ulpgcarapp.presentation.screens.publish_new_ride.PublishNewRideViewModel
import com.saulo.ulpgcarapp.presentation.screens.update_publish_ride.UpdatePublishRideViewModel
import com.saulo.ulpgcarapp.presentation.ui.theme.Blue400
import com.saulo.ulpgcarapp.presentation.ui.theme.Orange400

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePublishRideContent(viewModel: UpdatePublishRideViewModel = hiltViewModel()) {

    var active by remember { mutableStateOf(false) }

    var active2 by remember { mutableStateOf(false) }

    val ctx = LocalContext.current

    val state = viewModel.state

    Box(modifier = Modifier.fillMaxWidth()) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .background(Blue400)
        ) {
        }

        Card(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 70.dp, bottom = 5.dp)
        ) {
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {

                Text(
                    modifier = Modifier.padding(top = 10.dp),
                    text = "Trayecto",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "Rellene los siguientes datos",
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                SearchBar(
                    query = state.search.label,
                    onQueryChange = {
                        viewModel.onSearchInput(it,"","")
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
                            it.properties.label.contains(state.search.label, true)
                        }

                        items(filteredSearchs) { result ->
                            Text(
                                text = result.properties.label,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clickable {
                                        viewModel.onSearchInput(result.properties.label,
                                            result.geometry.coordinates[0].toString(),
                                            result.geometry.coordinates[1].toString())
                                        viewModel.onMunicipalityInput(result.properties.localadmin)
                                        active = false
                                    },
                            )
                        }
                    }

                }

                Spacer(modifier = Modifier.height(10.dp))

                SearchBar(
                    query = state.searchReturn.label,
                    onQueryChange = {
                        viewModel.onSearchReturnInput(it,"","")
                        viewModel.onSearchReturnSelected()
                    },
                    onSearch = {
                        Toast.makeText(ctx, state.searchReturn.label, Toast.LENGTH_LONG).show()
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
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = ""
                        )
                    }
                ) {

                    LazyColumn(modifier = Modifier.fillMaxSize()) {

                        val filteredReturnSearchs = state.searchReturnList.filter {
                            it.properties.label.contains(state.searchReturn.label, true)
                        }

                        items(filteredReturnSearchs) { result ->
                            Text(
                                text = result.properties.label,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clickable {
                                        viewModel.onSearchReturnInput(
                                            result.properties.label,
                                            result.geometry.coordinates[0].toString(),
                                            result.geometry.coordinates[1].toString())
                                        viewModel.getMatrix(
                                            result.geometry.coordinates[0].toString(),
                                            result.geometry.coordinates[1].toString())
                                        active2 = false
                                    },
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "Elija fecha, hora y especifique la matrícula del vehículo",
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {

                    val calendarState = rememberSheetState()

                    CalendarDialog(
                        state = calendarState,
                        selection = CalendarSelection.Date { date ->
                            viewModel.dateChoose = date.toString()
                            viewModel.onDateSelected(viewModel.dateChoose)
                        })

                    DefaultButton(
                        modifier = Modifier
                            .height(50.dp)
                            .width(70.dp),
                        text = "",
                        onClick = { calendarState.show() },
                        icon = Icons.Default.DateRange
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Text(text = viewModel.state.dateChoose)

                    Spacer(modifier = Modifier.width(15.dp))

                    val clockState = rememberSheetState()

                    ClockDialog(
                        state = clockState,
                        selection = ClockSelection.HoursMinutes { hours, minutes ->
                            viewModel.timeChoose = "$hours:" + "$minutes"
                            viewModel.onClockSelected(viewModel.timeChoose)
                        })

                    DefaultButton(
                        modifier = Modifier
                            .height(50.dp)
                            .width(70.dp),
                        text = "",
                        onClick = { clockState.show() },
                        icon = Icons.Default.Timer
                    )


                    Spacer(modifier = Modifier.width(10.dp))

                    Text(text = viewModel.state.timeChoose)
                }

                Spacer(modifier = Modifier.height(10.dp))

                DefaultTextField(
                    modifier = Modifier.padding(top = 5.dp),
                    value = state.plate,
                    onValueChange = { viewModel.onPlateInput(it)},
                    label = "Matrícula del vehículo",
                    icon = Icons.Default.DirectionsCar,
                    errorMsg = viewModel.plateErrMsg,
                    validateField = { viewModel.validatePlate()  }
                )

                Text(
                    text = "Indique el número máximo de pasajeros en su viaje",
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    DefaultButton(
                        modifier = Modifier
                            .height(50.dp)
                            .width(70.dp),
                        text = "",
                        onClick = { viewModel.removePassenger() },
                        icon = Icons.Default.PersonRemove,
                        enabled = viewModel.isEnabledRemovePassengerButton
                    )

                    Spacer(modifier = Modifier.width(20.dp))

                    Text(text = viewModel.state.passengers.toString())

                    Spacer(modifier = Modifier.width(20.dp))

                    DefaultButton(
                        modifier = Modifier
                            .height(50.dp)
                            .width(70.dp),
                        text = "",
                        onClick = { viewModel.addPassenger() },
                        icon = Icons.Default.PersonAdd,
                        enabled = viewModel.isEnabledAddPassengerButton
                    )
                }

                Text(
                    text = "Elija un precio para tu viaje",
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    DefaultButton(
                        modifier = Modifier
                            .height(50.dp)
                            .width(70.dp),
                        text = "",
                        onClick = { viewModel.lowerPrice()
                                  },
                        icon = Icons.Default.Remove,
                        enabled = viewModel.isEnabledLowerPriceButton
                    )

                    Spacer(modifier = Modifier.width(20.dp))

                    Text(text = viewModel.state.price.toString() + "€")

                    Spacer(modifier = Modifier.width(20.dp))

                    DefaultButton(
                        modifier = Modifier
                            .height(50.dp)
                            .width(70.dp),
                        text = "",
                        onClick = { viewModel.upPrice()
                                  },
                        icon = Icons.Default.Add,
                        enabled = viewModel.isEnabledUpPriceButton
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DefaultButton(
                    modifier = Modifier.width(300.dp),
                    text = "Actualizar Viaje",
                    onClick = { viewModel.onUpdateRide()
                              },
                    color = Orange400
                )
            }

        }
    }
}