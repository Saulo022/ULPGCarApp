package com.saulo.ulpgcarapp.presentation.screens.login.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
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
import com.saulo.ulpgcarapp.R
import com.saulo.ulpgcarapp.presentation.components.DefaultButton
import com.saulo.ulpgcarapp.presentation.components.DefaultTextField
import com.saulo.ulpgcarapp.presentation.ui.theme.Blue400

@Composable
fun LoginContent() {
    Box(modifier = Modifier.fillMaxWidth()) {

        BoxHeader()
        CardForm()
    }
}


@Composable
fun BoxHeader() {
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
                    .height(140.dp)
                    .padding(top = 30.dp),
                painter = painterResource(id = R.drawable.user),
                contentDescription = "Control de xbox 360"
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "ULPGCar App",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardForm() {

    var email by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .padding(start = 40.dp, end = 40.dp, top = 200.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {

            Text(
                modifier = Modifier.padding(top = 30.dp),
                text = "LOGIN",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Por favor inicia sesión para continuar",
                fontSize = 12.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(10.dp))

            DefaultTextField(
                modifier = Modifier.padding(top = 25.dp),
                value = email,
                onValueChange = { email = it},
                label = "Correo Electronico",
                icon = Icons.Default.Email,
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(10.dp))

            DefaultTextField(
                modifier = Modifier.padding(top = 5.dp),
                value = password,
                onValueChange = { password = it},
                label = "Contraseña",
                icon = Icons.Default.Lock,
                hideText = true
            )

            DefaultButton(text = "INICIAR SESION", onClick = { })

        }
    }
}