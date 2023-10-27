package com.saulo.ulpgcarapp.presentation.screens.search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saulo.ulpgcarapp.R
import com.saulo.ulpgcarapp.presentation.components.DefaultButton
import com.saulo.ulpgcarapp.presentation.ui.theme.Blue400
import com.saulo.ulpgcarapp.presentation.ui.theme.Orange400

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarContent() {
    val options = listOf(
        "Agaete",
        "Agüimes",
        "Artenara",
        "Arucas",
        "Firgas",
        "Gáldar",
        "Ingenio",
        "Las Palmas de Gran Canaria",
        "Mogán",
        "Moya",
        "San Bartolomé de Tirajana",
        "La Aldea de San Nicolás",
        "Santa Brígida",
        "Santa Lucía",
        "Santa María de Guía",
        "Vega de San Mateo",
        "Tejeda",
        "Telde",
        "Teror",
        "Valleseco",
        "Valsequillo"
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }

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
                        .height(120.dp)
                        .padding(top = 50.dp),
                    painter = painterResource(id = R.drawable.driverlesscar),
                    contentDescription = "Publish Ride Image"
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Search a ride",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

            }
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {


            Card(
                modifier = Modifier
                    .height(550.dp)
                    .width(400.dp)
                    .padding(start = 10.dp, end = 10.dp, top = 180.dp)
            ) {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {

                    Text(
                        text = "Elija un municipio desde el que desea salir o al que desea llegar",
                        modifier = Modifier.padding(all = 20.dp),
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Orange400,
                        textAlign = TextAlign.Justify
                    )

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded },
                        Modifier
                            .fillMaxSize()
                            .padding(top = 15.dp)
                            .wrapContentSize(Alignment.TopCenter)
                    ) {
                        TextField(
                            modifier = Modifier.menuAnchor(),
                            readOnly = true,
                            value = selectedOptionText,
                            onValueChange = {},
                            label = { Text("Municipio de") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.height(150.dp)
                        ) {

                            options.forEach { selectionOption ->
                                DropdownMenuItem(
                                    text = { Text(selectionOption) },
                                    onClick = {
                                        selectedOptionText = selectionOption
                                        expanded = false
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
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
                onClick = { },
                color = Orange400
            )
        }
    }
}
