package com.saulo.ulpgcarapp.screens.login

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.saulo.ulpgcarapp.screens.login.components.LoginBottomBar
import com.saulo.ulpgcarapp.screens.login.components.LoginContent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {

    Scaffold(
        topBar = {},
        content = { LoginContent()},
        bottomBar = {
            LoginBottomBar()
        }
    )

}