package com.example.foodhub.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodhub.R
import com.example.foodhub.ui.features.auth.BaseAuthViewModel
import com.example.foodhub.ui.theme.Orange

@Composable
fun SocialButtons(
    viewModel: BaseAuthViewModel,
    color: Color = Color.Black
) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // -----sign in with-----
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 45.dp, end = 16.dp, top = 10.dp, bottom = 10.dp)
            )
            Text(
                text = stringResource(R.string.sign_in_with),
                modifier = Modifier
                    .padding(vertical = 8.dp),
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                color = color
            )
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp, end = 45.dp, top = 10.dp, bottom = 10.dp)
            )
        }

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
                        viewModel.onFacebookClick(context as ComponentActivity)
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
                        viewModel.onGoogleClick(context as ComponentActivity)
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
    }
}

@Composable
fun LabeledTextField(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    modifier: Modifier = Modifier
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(modifier = modifier.padding(vertical = 8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            color = Color.Gray,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                if (isPassword) {
                    val image = if (passwordVisible)
                        Icons.Default.Visibility
                    else Icons.Default.VisibilityOff

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = image,
                            contentDescription = "Toggle password visibility",
                            tint = Color.Gray.copy(alpha = 0.7f)
                        )
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedLabelColor = Orange,
                focusedBorderColor = Orange,
                cursorColor = Orange
            )
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SocialButtonsPreview() {
    SocialButtons(
        viewModel = TODO()
    )
}


@Preview(showBackground = true)
@Composable
fun LabeledTextFieldPreview() {
    LabeledTextField(
        title = "Name",
        value = "Fardeel",
        onValueChange = {},
        isPassword = false,
        modifier = Modifier.padding(10.dp)
    )
}