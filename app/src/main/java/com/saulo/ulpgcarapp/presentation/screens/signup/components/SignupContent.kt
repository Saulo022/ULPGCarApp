package com.saulo.ulpgcarapp.presentation.screens.signup.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.saulo.ulpgcarapp.R
import com.saulo.ulpgcarapp.domain.model.Response
import com.saulo.ulpgcarapp.presentation.components.DefaultButton
import com.saulo.ulpgcarapp.presentation.components.DefaultTextField
import com.saulo.ulpgcarapp.presentation.navigation.AppScreen
import com.saulo.ulpgcarapp.presentation.screens.signup.SignupViewModel
import com.saulo.ulpgcarapp.presentation.ui.theme.Blue400


@Composable
fun SignupContent(
    navController: NavHostController,
    viewModel: SignupViewModel = hiltViewModel(),
    paddingValues: PaddingValues
) {

    val signupFlow = viewModel.signupFlow.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = paddingValues.calculateTopPadding())
            .height(260.dp)
            .background(Blue400)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(10.dp))

            Image(
                modifier = Modifier.height(110.dp),
                painter = painterResource(id = R.drawable.user),
                contentDescription = "Imagen de Usuario"
            )

        }
    }

    Card(
        modifier = Modifier
            .padding(start = 40.dp, end = 40.dp, top = 200.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {

            Text(
                modifier = Modifier.padding(top = 30.dp),
                text = "REGISTRO",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Por favor rellena estos datos para continuar",
                fontSize = 12.sp,
                color = Color.Gray
            )

            DefaultTextField(
                modifier = Modifier.padding(top = 5.dp),
                value = viewModel.username.value,
                onValueChange = { viewModel.username.value = it },
                label = "Nombre de usuario",
                icon = Icons.Default.Person,
                errorMsg = viewModel.usernameErrMsg.value,
                validateField = { viewModel.validateUsername() }
            )


            DefaultTextField(
                modifier = Modifier.padding(top = 0.dp),
                value = viewModel.email.value,
                onValueChange = { viewModel.email.value = it },
                label = "Correo electronico",
                icon = Icons.Default.Email,
                keyboardType = KeyboardType.Email,
                errorMsg = viewModel.emailErrMsg.value,
                validateField = { viewModel.validateEmail() }
            )

            DefaultTextField(
                modifier = Modifier.padding(top = 0.dp),
                value = viewModel.password.value,
                onValueChange = { viewModel.password.value = it },
                label = "Contraseña",
                icon = Icons.Default.Lock,
                hideText = true,
                errorMsg = viewModel.passwordErrMsg.value,
                validateField = { viewModel.validatePassword() }
            )

            DefaultTextField(
                modifier = Modifier.padding(top = 0.dp),
                value = viewModel.confirmPassword.value,
                onValueChange = { viewModel.confirmPassword.value = it },
                label = "Confirmar contraseña",
                icon = Icons.Outlined.Lock,
                hideText = true,
                errorMsg = viewModel.confirmPasswordErrMsg.value,
                validateField = { viewModel.validateConfirmPassword() }
            )

            DefaultButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 35.dp),
                text = "REGISTRARSE",
                onClick = { viewModel.onSignup() },
                enabled = viewModel.isEnabledLoginButton
            )
        }
    }

    signupFlow.value.let {
        when (it) {
            Response.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is Response.Success -> {
                LaunchedEffect(Unit) {
                    viewModel.createUser()
                    navController.popBackStack(AppScreen.Login.route, true)
                    navController.navigate(route = AppScreen.Profile.route)
                }
            }
            is Response.Failure -> {
                Toast.makeText(LocalContext.current, it.exception?.message ?: "Error desconocido", Toast.LENGTH_LONG).show()
            }

            else -> {}
        }
    }

}


