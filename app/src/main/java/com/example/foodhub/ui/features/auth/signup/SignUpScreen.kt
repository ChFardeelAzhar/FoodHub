package com.example.foodhub.ui.features.auth.signup

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.foodhub.R
import com.example.foodhub.ui.LabeledTextField
import com.example.foodhub.ui.SocialButtons
import com.example.foodhub.ui.theme.Orange
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignUpScreen(
    onLoginClick: () -> Unit,
    navigateToHome: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {

    val name = viewModel.fullName.collectAsStateWithLifecycle()
    val email = viewModel.email.collectAsStateWithLifecycle()
    val password = viewModel.password.collectAsStateWithLifecycle()

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val errorMessage = remember { mutableStateOf<String?>(null) }
    val loading = remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(uiState.value) {
        when (val state = uiState.value) {

            is SignUpViewModel.SignupEvents.Loading -> {
                // Loading Indicator
                loading.value = true
            }

            is SignUpViewModel.SignupEvents.Failure -> {
                loading.value = false
                // show a toast message
                errorMessage.value = state.error.message
                Toast.makeText(context, state.error.message, Toast.LENGTH_SHORT).show()

            }

            else -> {
                loading.value = false
                errorMessage.value = null
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.navState.collectLatest { state ->
            when (state) {
                SignUpViewModel.SignUpNavigationEvent.NavigateToHome -> {
                    // show a toast message
                    Toast.makeText(context, "Navigate to home", Toast.LENGTH_SHORT).show()
                    navigateToHome()
                }

                else -> {

                }
            }
        }

    }


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
                .padding(top = 130.dp, bottom = 20.dp, start = 24.dp, end = 24.dp)
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
                value = name.value,
                onValueChange = { viewModel.onNameChange(it) },
                isPassword = false,
                modifier = Modifier
            )

            Spacer(Modifier.height(20.dp))

            LabeledTextField(
                title = stringResource(R.string.email),
                value = email.value,
                onValueChange = { viewModel.onEmailChange(it) },
                isPassword = false,
                modifier = Modifier
            )

            Spacer(Modifier.height(20.dp))

            LabeledTextField(
                title = stringResource(R.string.password),
                value = password.value,
                onValueChange = { viewModel.onPasswordChange(it) },
                isPassword = true,
                modifier = Modifier
            )

            Spacer(Modifier.height(20.dp))

            Text(text = errorMessage.value ?: "", color = Color.Red)

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Button(
                    onClick = viewModel::onSignUpClick,
                    modifier = Modifier
                        .height(65.dp),
                    shape = RoundedCornerShape(32.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Orange),
                    border = BorderStroke(1.dp, color = Color.White)
                ) {

                    AnimatedContent(
                        targetState = loading.value,
                        transitionSpec = {
                            fadeIn(animationSpec = tween(300)) + scaleIn(initialScale = 0.8f) togetherWith fadeOut(
                                animationSpec = tween(300)
                            ) + scaleOut(targetScale = 0.8f)
                        }
                    ) { target ->
                        if (target) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.padding(horizontal = 60.dp)
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = Color.White,
                                    strokeCap = StrokeCap.Round,
                                    strokeWidth = 2.dp
                                )
                            }
                        } else {
                            Text(
                                text = stringResource(R.string.sign_up),
                                modifier = Modifier
                                    .padding(horizontal = 80.dp),
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))


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

            SocialButtons(viewModel)

        }

    }

}

@Preview(showBackground = true)
@Composable
private fun SignUpScreenPreview() {
    SignUpScreen(onLoginClick = {}, navigateToHome = {})
}