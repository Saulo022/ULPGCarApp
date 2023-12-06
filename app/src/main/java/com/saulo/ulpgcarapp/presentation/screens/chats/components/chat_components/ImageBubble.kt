package com.saulo.ulpgcarapp.presentation.screens.chats.components.chat_components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.saulo.ulpgcarapp.R
import com.saulo.ulpgcarapp.presentation.ui.theme.Blue300
import com.saulo.ulpgcarapp.presentation.ui.theme.Orange400

@Composable
fun ImageBubble(
    ownMessage: Boolean = true,
    image: String,
    name: String
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Column(
            modifier = Modifier
                .align(if (ownMessage) Alignment.CenterStart else Alignment.CenterEnd)
        ) {
            Row() {

                if (!ownMessage){
                    Text(text = name, color = Color.Black, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(5.dp))
                }

                if (image != "") {
                    AsyncImage(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .wrapContentWidth(Alignment.End),
                        model = image,
                        contentDescription = "User image",
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = ""
                    )
                }

                if (ownMessage){
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = name, color = Color.Black, fontWeight = FontWeight.Bold)
                }

            }
        }
    }
}
