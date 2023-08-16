package com.saulo.ulpgcarapp.presentation.screens.login

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.saulo.ulpgcarapp.presentation.screens.login.components.Login
import com.saulo.ulpgcarapp.presentation.screens.login.components.LoginBottomBar
import com.saulo.ulpgcarapp.presentation.screens.login.components.LoginContent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(navController: NavHostController) {

    Scaffold(
        topBar = {},
        content = {
            LoginContent(navController)
        },
        bottomBar = {
            LoginBottomBar(navController)
        }
    )

    //MANEJAR EL ESTADO DE LA PETICION DE LOGIN
    Login(navController = navController)

}