package com.saulo.ulpgcarapp.screens.login.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saulo.ulpgcarapp.R
import com.saulo.ulpgcarapp.ui.theme.Blue200
import com.saulo.ulpgcarapp.ui.theme.Orange200

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
            .background(Blue200)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                modifier = Modifier.height(130.dp),
                painter = painterResource(id = R.drawable.user),
                contentDescription = "Control de xbox 360"
            )

            Text(text = "ULPGCar App")

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardForm() {
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

            OutlinedTextField(
                value = "",
                onValueChange = { },
                label = { Text(text = "Correo Electronico") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email Icon",
                        tint = Color.White
                    )
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = "",
                onValueChange = { },
                label = { Text(text = "Contraseña") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Password Icon",
                        tint = Color.White
                    )
                }
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 45.dp),
                onClick = { }) {
                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "")
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "INICIAR SESION")
            }
        }
    }
}