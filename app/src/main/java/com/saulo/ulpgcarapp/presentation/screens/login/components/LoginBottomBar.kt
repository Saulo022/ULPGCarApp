package com.saulo.ulpgcarapp.presentation.screens.login.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.saulo.ulpgcarapp.presentation.navigation.AuthScreen
import com.saulo.ulpgcarapp.presentation.ui.theme.Orange400

@Composable
fun LoginBottomBar(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 30.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No tienes cuenta?",
            fontSize = 14.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            modifier = Modifier.clickable { navController.navigate(route = AuthScreen.Signup.route) },
            text = "REGISTRATE AQUI",
            color = Orange400,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}