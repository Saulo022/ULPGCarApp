package com.saulo.ulpgcarapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.saulo.ulpgcarapp.presentation.ui.theme.Blue400


@Composable
fun DefaultButton(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
    color: Color = Blue400,
    icon: ImageVector = Icons.Default.ArrowForward,
    mainColor: Color = Color.White,
    enabled: Boolean = true
) {

    Button(
        modifier = modifier,
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(containerColor = color),
        enabled = enabled
    ) {
        Icon(imageVector = icon, contentDescription = "", tint = mainColor)
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = text, color = mainColor)
    }

}