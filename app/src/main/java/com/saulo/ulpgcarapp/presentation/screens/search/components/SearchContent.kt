package com.saulo.ulpgcarapp.presentation.screens.search.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.navigation.NavHostController
import com.saulo.ulpgcarapp.R
import com.saulo.ulpgcarapp.presentation.components.DefaultButton
import com.saulo.ulpgcarapp.presentation.navigation.DetailsScreen
import com.saulo.ulpgcarapp.presentation.screens.search.SearchViewModel
import com.saulo.ulpgcarapp.presentation.ui.theme.Blue400
import com.saulo.ulpgcarapp.presentation.ui.theme.Orange400

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarContent(navController: NavHostController, viewmodel: SearchViewModel = hiltViewModel()) {

    var expanded by remember { mutableStateOf(false) }
    //var selectedOptionText by remember { mutableStateOf(options[0]) }
    val ctx = LocalContext.current

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
                    contentDescription = "Search Ride Image"
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Busca un viaje",
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
                        text = "Elija un municipio desde el que desee salir",
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
                            value = viewmodel.state.municipality,
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

                            viewmodel.listOfMunicipalities.forEach { selectionOption ->
                                DropdownMenuItem(
                                    text = { Text(selectionOption) },
                                    onClick = {
                                        viewmodel.onMenuInput(selectionOption)
                                        expanded = false
                                        Toast.makeText(ctx, viewmodel.state.municipality, Toast.LENGTH_SHORT).show()
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
                onClick = { navController.navigate(DetailsScreen.SearchResults.passMunicipality(viewmodel.state.municipality)) },
                color = Orange400
            )
        }
    }
}
