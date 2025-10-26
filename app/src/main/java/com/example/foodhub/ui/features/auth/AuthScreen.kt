package com.example.foodhub.ui.features.auth

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
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
import com.example.foodhub.ui.SocialButtons
import com.example.foodhub.ui.features.auth.login.SignInViewModel
import com.example.foodhub.ui.theme.Orange
import kotlinx.coroutines.flow.collectLatest


@Composable
fun AuthScreen(
    onSignInClick: () -> Unit,
    navigateToHome: () -> Unit,
    viewModel: AuthScreenViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    var loading = remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(uiState.value) {

        when (val state = uiState.value) {

            is AuthScreenViewModel.AuthScreenEvents.Failure -> {
                // show a toast Also a failure message
                Toast.makeText(context, state.error.message, Toast.LENGTH_SHORT).show()
                loading.value = false
            }

            AuthScreenViewModel.AuthScreenEvents.Idle -> {

            }

            AuthScreenViewModel.AuthScreenEvents.Loading -> {
                // show loading indicator
                loading.value = true
            }

            AuthScreenViewModel.AuthScreenEvents.Success -> {
                // here we will show a toast or navigate our screen to home
                Toast.makeText(context, "Navigate to Home", Toast.LENGTH_SHORT).show()
                loading.value = false
            }
        }

    }

    LaunchedEffect(Unit) {
        viewModel.navState.collectLatest { state ->
            when (state) {
                AuthScreenViewModel.AuthNavigationEvent.NavigateToHome -> {
                    navigateToHome()
                    loading.value = false
                }

                else -> {
                    loading.value = false
                }
            }
        }
    }


    val brush = Brush.verticalGradient(
        listOf(
            Color.Transparent,
            Color.Black
        )
    )
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.ic_food_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
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
                .statusBarsPadding()
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

            SocialButtons(
                viewModel = viewModel,
                color = Color.White
            )

            // Sign In Button
            Button(
                onClick = {
                    onSignInClick()
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
                    fontSize = 14.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Light
                )

                TextButton(
                    onClick = {
                        onSignInClick()
                    }
                ) {

                    Text(
                        text = stringResource(R.string.sign_in),
                        fontSize = 14.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Light,
                        textDecoration = TextDecoration.Underline
                    )

                }
            }
        }
    }

    if (loading.value) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Orange, strokeCap = StrokeCap.Round)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    AuthScreen(onSignInClick = {}, navigateToHome = {})
}