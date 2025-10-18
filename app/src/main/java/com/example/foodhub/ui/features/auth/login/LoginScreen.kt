package com.example.foodhub.ui.features.auth.login

import android.widget.Toast
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
fun LoginScreen(
    onBackClick: () -> Unit,
    onSignUpClick: () -> Unit,
    navigateToHome: () -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val email = viewModel.email.collectAsStateWithLifecycle()
    val password = viewModel.password.collectAsStateWithLifecycle()
    var loading = remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(uiState.value) {

        when (val state = uiState.value) {

            is SignInViewModel.SignInEvents.Idle -> {
                // do nothing
            }

            is SignInViewModel.SignInEvents.Loading -> {
                // show loading indicator
                loading.value = true
            }

            is SignInViewModel.SignInEvents.Success -> {
                // here we will show a toast or navigate our screen to home
                Toast.makeText(context, "Navigate to Home", Toast.LENGTH_SHORT).show()
                loading.value = false
            }

            is SignInViewModel.SignInEvents.Failure -> {
                // show a toast Also a failure message
                Toast.makeText(context, state.error.message, Toast.LENGTH_SHORT).show()
                loading.value = false
            }

        }

    }

    LaunchedEffect(Unit) {
        viewModel.navState.collectLatest { state ->
            when (state) {
                SignInViewModel.SignInNavigationEvent.NavigateToHome -> {
                    navigateToHome()
                    loading.value = false
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

        IconButton(
            onClick = {
                onBackClick()
            },
            colors = IconButtonDefaults.iconButtonColors(containerColor = Color.White),
            modifier = Modifier
                .padding(top = 37.dp, start = 24.dp)
                .size(40.dp),
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Back Button",
                modifier = Modifier.padding(12.dp)
            )
        }



        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 185.dp, bottom = 20.dp, start = 24.dp, end = 24.dp)
        ) {

            Text(
                text = stringResource(R.string.login),
                modifier = Modifier
                    .padding(),
                fontWeight = FontWeight.SemiBold,
                fontSize = 40.sp
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

            Text(
                text = stringResource(R.string.forgot_password),
                fontSize = 14.sp,
                color = Orange,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(20.dp))

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

                Button(
                    onClick = viewModel::onSignInClick,
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
                                text = stringResource(R.string.login),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp
                            )
                        }
                    }
                }

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Text(
                    text = stringResource(R.string.dont_have_an_account),
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Light
                )

                TextButton(
                    onClick = {
                        onSignUpClick()
                    }
                ) {

                    Text(
                        text = stringResource(R.string.sign_up),
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
private fun LoginScreenPreview() {
    LoginScreen(onBackClick = {}, onSignUpClick = {}, navigateToHome = {})
}