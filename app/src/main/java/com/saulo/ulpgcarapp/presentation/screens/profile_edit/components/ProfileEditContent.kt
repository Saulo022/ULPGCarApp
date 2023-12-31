package com.saulo.ulpgcarapp.presentation.screens.profile_edit.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.saulo.ulpgcarapp.presentation.components.DefaultButton
import com.saulo.ulpgcarapp.presentation.components.DefaultTextField
import com.saulo.ulpgcarapp.presentation.screens.profile_edit.ProfileEditViewModel
import com.saulo.ulpgcarapp.R
import com.saulo.ulpgcarapp.presentation.components.DialogCapturePicture
import com.saulo.ulpgcarapp.presentation.ui.theme.Orange400


@Composable
fun ProfileEditContent(
    navController: NavHostController,
    viewModel: ProfileEditViewModel = hiltViewModel()
) {

    val state = viewModel.state

    viewModel.resultingActivityHandler.handle()
    val dialogState = remember { mutableStateOf(false) }

    DialogCapturePicture(
        status = dialogState,
        takePhoto = { viewModel.takePhoto() },
        pickImage = { viewModel.pickImage() }
    )

    Box(modifier = Modifier.fillMaxWidth()) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(Orange400)
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(80.dp))

                if (viewModel.state.image != "") {
                    AsyncImage(
                        modifier = Modifier
                            .height(150.dp)
                            .width(150.dp)
                            .clip(CircleShape)
                            .clickable { dialogState.value = true },
                        model = viewModel.state.image,
                        contentDescription = "Selected image",
                        contentScale = ContentScale.Crop
                    )
                } else {

                    Image(
                        modifier = Modifier
                            .height(120.dp)
                            .clickable {  dialogState.value = true },
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = "Imagen de Usuario"
                    )
                }

            }
        }



        Card(
            modifier = Modifier
                .padding(start = 40.dp, end = 40.dp, top = 250.dp)
        ) {
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {

                Text(
                    modifier = Modifier.padding(top = 30.dp),
                    text = "EDITAR DATOS",
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


                DefaultButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, bottom = 40.dp),
                    text = "ACTUALIZAR DATOS",
                    onClick = { viewModel.saveImage() },
                )


            }
        }
    }

}