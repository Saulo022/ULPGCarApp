package com.saulo.ulpgcarapp.presentation.screens.publish_a_ride.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import com.saulo.ulpgcarapp.R
import com.saulo.ulpgcarapp.presentation.components.DefaultButton
import com.saulo.ulpgcarapp.presentation.components.DefaultTextField
import com.saulo.ulpgcarapp.presentation.ui.theme.Blue400

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublishRideContent() {

    val calendarState = rememberSheetState()

    var dateChoose by remember { mutableStateOf("") }

    CalendarDialog(state = calendarState, selection = CalendarSelection.Date { date ->
        dateChoose = date.toString()
    })


    val clockState = rememberSheetState()

    var timeChoose by remember { mutableStateOf("") }

    ClockDialog(state = clockState, selection = ClockSelection.HoursMinutes { hours, minutes ->
        timeChoose = "$hours:" + "$minutes"
    })

    var pickerValue by remember { mutableStateOf(0) }


    Box(modifier = Modifier.fillMaxWidth()) {

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
                        .height(80.dp)
                        .padding(top = 30.dp),
                    painter = painterResource(id = R.drawable.configcar),
                    contentDescription = "Control de xbox 360"
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Publish a ride",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

            }
        }

        Card(
            modifier = Modifier
                .padding(start = 40.dp, end = 40.dp, top = 120.dp)
        ) {
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {

                Text(
                    modifier = Modifier.padding(top = 30.dp),
                    text = "Publish",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Rellene los siguientes datos",
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                DefaultTextField(
                    modifier = Modifier.padding(top = 5.dp),
                    value = "",
                    onValueChange = { },
                    label = "¿Desde dónde?",
                    icon = Icons.Default.LocationSearching,
                    errorMsg = "",
                    validateField = { }
                )

                DefaultTextField(
                    modifier = Modifier.padding(top = 0.dp),
                    value = "",
                    onValueChange = { },
                    label = "¿Hacia dónde?",
                    icon = Icons.Default.LocationOn,
                    errorMsg = "",
                    validateField = { }
                )

                Text(
                    modifier = Modifier.padding(top = 15.dp),
                    text = "Salida",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "Elige fecha, hora y numero de pasajeros",
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {

                    DefaultButton(
                        modifier = Modifier
                            .height(50.dp)
                            .width(70.dp),
                        text = "",
                        onClick = { calendarState.show() },
                        icon = Icons.Default.DateRange
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(text = dateChoose)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {

                    DefaultButton(
                        modifier = Modifier
                            .height(50.dp)
                            .width(70.dp),
                        text = "",
                        onClick = { clockState.show() },
                        icon = Icons.Default.Timer
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(text = timeChoose)
                }

                Spacer(modifier = Modifier.height(10.dp))

                DefaultTextField(
                    modifier = Modifier.padding(top = 0.dp),
                    value = "",
                    onValueChange = { },
                    label = "Numero de pasajeros",
                    icon = Icons.Default.Person,
                    keyboardType = KeyboardType.Number,
                    errorMsg = "",
                    validateField = { }
                )
            }
        }
    }
}