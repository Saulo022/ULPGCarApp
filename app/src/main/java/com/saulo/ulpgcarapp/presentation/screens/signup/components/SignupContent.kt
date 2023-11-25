package com.saulo.ulpgcarapp.presentation.screens.signup.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.saulo.ulpgcarapp.R
import com.saulo.ulpgcarapp.presentation.components.DefaultButton
import com.saulo.ulpgcarapp.presentation.components.DefaultTextField
import com.saulo.ulpgcarapp.presentation.screens.signup.SignupViewModel
import com.saulo.ulpgcarapp.presentation.ui.theme.Blue400


@Composable
fun SignupContent(
    navController: NavHostController,
    viewModel: SignupViewModel = hiltViewModel(),
    paddingValues: PaddingValues
) {

    val state = viewModel.state

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = paddingValues.calculateTopPadding())
            .height(260.dp)
            .background(Blue400)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth(),
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
            .padding(start = 20.dp, end = 20.dp, top = 200.dp, bottom = 40.dp)
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
                value = state.username,
                onValueChange = { viewModel.onUsernameInput(it) },
                label = "Nombre de usuario",
                icon = Icons.Default.Person,
                errorMsg = viewModel.usernameErrMsg,
                validateField = { viewModel.validateUsername() }
            )


            DefaultTextField(
                modifier = Modifier.padding(top = 0.dp),
                value = state.email,
                onValueChange = { viewModel.onEmailInput(it) },
                label = "Correo electronico",
                icon = Icons.Default.Email,
                keyboardType = KeyboardType.Email,
                errorMsg = viewModel.emailErrMsg,
                validateField = { viewModel.validateEmail() }
            )

            DefaultTextField(
                modifier = Modifier.padding(top = 0.dp),
                value = state.password,
                onValueChange = { viewModel.onPasswordInput(it) },
                label = "Contraseña",
                icon = Icons.Default.Lock,
                hideText = true,
                errorMsg = viewModel.passwordErrMsg,
                validateField = { viewModel.validatePassword() }
            )

            DefaultTextField(
                modifier = Modifier.padding(top = 0.dp),
                value = state.confirmPassword,
                onValueChange = { viewModel.onConfirmPasswordInput(it) },
                label = "Confirmar contraseña",
                icon = Icons.Outlined.Lock,
                hideText = true,
                errorMsg = viewModel.confirmPasswordErrMsg,
                validateField = { viewModel.validateConfirmPassword() }
            )

            DefaultButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp,),
                text = "REGISTRARSE",
                onClick = { viewModel.onSignup() },
                enabled = viewModel.isEnabledLoginButton
            )
        }
    }

}


