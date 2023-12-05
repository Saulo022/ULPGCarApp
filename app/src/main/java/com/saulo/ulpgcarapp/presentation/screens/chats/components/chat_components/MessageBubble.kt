package com.saulo.ulpgcarapp.presentation.screens.chats.components.chat_components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saulo.ulpgcarapp.presentation.ui.theme.Blue300
import com.saulo.ulpgcarapp.presentation.ui.theme.Orange400

@Composable
fun MessageBubble(
    ownMessage: Boolean = true,
    message: String,
    hora: String
) {

    Box(modifier = Modifier.fillMaxSize().padding(all = 10.dp)) {
        Column(
            modifier = Modifier
                .align(if (ownMessage) Alignment.CenterStart else Alignment.CenterEnd)
                .clip(
                    RoundedCornerShape(
                        topStart = 48f,
                        topEnd = 48f,
                        bottomStart = if (ownMessage) 0f else 48f,
                        bottomEnd = if (ownMessage) 48f else 0f
                    )
                )
                .background(if (ownMessage) Blue300 else Orange400)
                .padding(16.dp)
        ) {
            Text(text = message, color = if (ownMessage) Color.White else Color.Black)
            Text(text = hora, fontSize = 11.sp, fontFamily = FontFamily.Cursive, fontStyle = FontStyle.Italic, color = if (ownMessage) Color.White else Color.Black)
        }
    }
}


