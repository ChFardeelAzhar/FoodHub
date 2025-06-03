package com.example.foodhub.ui.features.auth.signup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodhub.R
import com.example.foodhub.ui.LabeledTextField
import com.example.foodhub.ui.SocialButtons
import com.example.foodhub.ui.theme.Orange

@Composable
fun SignUpScreen(onLoginClick : ()-> Unit) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        androidx.compose.foundation.Image(
            painter = painterResource(R.drawable.signup_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 100.dp, bottom = 20.dp, start = 24.dp, end = 24.dp)
        ) {

            Text(
                text = stringResource(R.string.sign_up),
                modifier = Modifier
                    .padding(),
                fontWeight = FontWeight.SemiBold,
                fontSize = 37.sp
            )

            Spacer(Modifier.height(20.dp))

            LabeledTextField(
                title = stringResource(R.string.full_name),
                value = name,
                onValueChange = { name = it },
                isPassword = false,
                modifier = Modifier
            )

            Spacer(Modifier.height(20.dp))

            LabeledTextField(
                title = stringResource(R.string.email),
                value = email,
                onValueChange = { email = it },
                isPassword = false,
                modifier = Modifier
            )

            Spacer(Modifier.height(20.dp))

            LabeledTextField(
                title = stringResource(R.string.password),
                value = password,
                onValueChange = { password = it },
                isPassword = true,
                modifier = Modifier
            )

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {

                },
                modifier = Modifier
                    .padding(horizontal = 36.dp, vertical = 16.dp)
                    .height(65.dp),
                shape = RoundedCornerShape(32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Orange),
                border = BorderStroke(1.dp, color = Color.White)
            ) {
                Text(
                    text = stringResource(R.string.sign_up),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Text(
                    text = stringResource(R.string.already_have_an_account),
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Light
                )

                TextButton(
                    onClick = {
                        onLoginClick()
                    }
                ) {

                    Text(
                        text = stringResource(R.string.login),
                        fontSize = 14.sp,
                        color = Orange,
                        fontWeight = FontWeight.Light,
                        textDecoration = TextDecoration.Underline
                    )

                }
            }

            Spacer(Modifier.height(25.dp))

            SocialButtons(
                onFacebookClick = {},
                onGoogleClick = {}
            )

        }

    }

}

@Preview(showBackground = true)
@Composable
private fun SignUpScreenPreview() {
    SignUpScreen(onLoginClick = {})
}