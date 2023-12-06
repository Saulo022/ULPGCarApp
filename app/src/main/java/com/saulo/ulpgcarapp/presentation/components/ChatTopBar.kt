package com.saulo.ulpgcarapp.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.saulo.ulpgcarapp.presentation.ui.theme.Blue300
import com.saulo.ulpgcarapp.presentation.ui.theme.Blue400


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopBar(
    origin: String,
    destination: String,
    date: String,
    time: String,
    upAvailable: Boolean = true,
    navController: NavHostController? = null,
) {
    TopAppBar(
        title = {
            Column() {
                Text(text = origin + " -> " + destination, fontSize = 10.sp)
                Text(date + " -> " + time, fontSize = 10.sp)
            }

        },
        navigationIcon = {
            if (upAvailable) {
                IconButton(onClick = { navController?.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Blue300,
            titleContentColor = Color.White
        )
    )
}

