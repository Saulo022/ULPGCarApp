package com.saulo.ulpgcarapp.presentation.screens.profile_edit

import android.util.Log
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.saulo.ulpgcarapp.presentation.components.DefaultTopBar
import com.saulo.ulpgcarapp.presentation.screens.profile_edit.components.ProfileEditContent
import com.saulo.ulpgcarapp.presentation.screens.profile_edit.components.SaveImage
import com.saulo.ulpgcarapp.presentation.screens.profile_edit.components.Update


@Composable
fun ProfileEditScreen(navController: NavHostController, user: String) {

    Log.d("ProfileEditScreen", "Usuario: $user ")

    Scaffold(
        topBar = {
            DefaultTopBar(
                title = "Editar Usuario",
                upAvailable = true,
                navController = navController
            )
        },
        content = {
            ProfileEditContent(navController = navController)
        },
        bottomBar = {}
    )
    SaveImage()
    Update()
}