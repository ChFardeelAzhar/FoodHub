package com.example.foodhub.ui.features.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodhub.R
import com.example.foodhub.ui.theme.Orange


@Composable
fun AuthScreen(modifier: Modifier = Modifier) {

    val brush = Brush.verticalGradient(
        listOf(
            Color.Transparent,
            Color.Black
        )
    )
    Box(modifier = Modifier.fillMaxSize()) {
        Image(painter = painterResource(R.drawable.ic_food_bg), contentDescription = null, modifier = Modifier.fillMaxSize())
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush)
        ) {

        }

        Button(
            onClick = {

            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopEnd)
        ) {
            Text("Skip", color = Orange)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 170.dp, start = 30.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = stringResource(R.string.welcome_to),
                fontWeight = FontWeight.Bold,
                fontSize = 50.sp
            )
            Text(
                text = stringResource(R.string.foodHub),
                fontWeight = FontWeight.Bold,
                fontSize = 50.sp,
                color = Orange
            )

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(R.string.your_favourite_foods_delivered),
                fontSize = 19.sp,
                color = Color.Black,
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.size(7.dp))

            Text(
                text = stringResource(R.string.fast_at_your_door),
                fontSize = 19.sp,
                color = Color.Black,
                fontWeight = FontWeight.Light
            )
        }


        Column(
            modifier = Modifier
                .padding(bottom = 35.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(top = 170.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // -----sign in with-----

            Text(
                text = stringResource(R.string.sign_in_with),
                modifier = Modifier
                    .padding(vertical = 8.dp),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                color = Color.White
            )

            // Google,FaceBook sign in Buttons
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 16.dp),
            ) {

                Row(
                    modifier = Modifier
                        .clickable {

                        }
                        .clip(shape = RoundedCornerShape(32.dp))
                        .background(color = Color.White),
                ) {

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp)
                    ) {

                        Image(
                            painter = painterResource(R.drawable.ic_facebook),
                            contentDescription = "Google icon"
                        )

                        Spacer(Modifier.width(10.dp))

                        Text(
                            text = stringResource(R.string.facebook),
                            modifier = Modifier
                                .padding(vertical = 8.dp),
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    }
                }


                Row(
                    modifier = Modifier
                        .clickable {

                        }
                        .clip(shape = RoundedCornerShape(32.dp))
                        .background(color = Color.White),
                ) {

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp)
                    ) {

                        Image(
                            painter = painterResource(R.drawable.ic_google),
                            contentDescription = "Google icon"
                        )

                        Spacer(Modifier.width(10.dp))

                        Text(
                            text = stringResource(R.string.google),
                            modifier = Modifier
                                .padding(vertical = 8.dp),
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    }
                }
            }

            // Sign In Button
            Button(
                onClick = {

                },
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp),
                shape = RoundedCornerShape(32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray.copy(alpha = 0.3f)),
                border = BorderStroke(1.dp, color = Color.White)
            ) {
                Text(
                    text = stringResource(R.string.sign_in_with_email),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
            }

            // Already have account row
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Text(
                    text = stringResource(R.string.already_have_an_account),
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Light
                )

                TextButton(
                    onClick = {}
                ) {

                    Text(
                        text = stringResource(R.string.sign_in),
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Light,
                        textDecoration = TextDecoration.Underline
                    )

                }
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    AuthScreen()
}