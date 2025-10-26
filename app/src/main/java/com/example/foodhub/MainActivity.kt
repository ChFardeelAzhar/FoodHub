package com.example.foodhub

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.runtime.Composable
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodhub.ui.features.auth.AuthScreen
import com.example.foodhub.ui.features.auth.login.LoginScreen
import com.example.foodhub.ui.features.auth.signup.SignUpScreen
import com.example.foodhub.ui.features.home.HomeScreen
import com.example.foodhub.ui.theme.FoodHubTheme
import com.example.foodhub.util.NavRouts
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        var showSplashScreen = true
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                showSplashScreen
            }
            setOnExitAnimationListener { screen ->
                val zoomX = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_X,
                    0.5f,
                    0f
                )

                val zoomY = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_Y,
                    0.5f,
                    0f
                )

                zoomX.duration = 500
                zoomY.duration = 500

                zoomX.interpolator = OvershootInterpolator()
                zoomY.interpolator = OvershootInterpolator()

                zoomX.doOnEnd {
                    screen.remove()
                }
                zoomY.doOnEnd {
                    screen.remove()
                }

                zoomX.start()
                zoomY.start()

            }
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()

            FoodHubTheme {
                AppNavigation()
            }

        }

        CoroutineScope(Dispatchers.IO).launch {
            delay(2000)
            showSplashScreen = false
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRouts.AuthScreen.route,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300)
            ) + fadeIn(tween(300))
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300)
            ) + fadeOut(tween(300))
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300)
            ) + fadeIn(tween(300))
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300)
            ) + fadeOut(tween(300))
        }
    ) {
        composable(NavRouts.AuthScreen.route) {
            AuthScreen(
                onSignInClick = {
                    navController.navigate(NavRouts.LoginScreen.route)
                },
                navigateToHome = {
                    navController.navigate(NavRouts.HomeScreen.route)
                }
            )
        }

        composable(NavRouts.LoginScreen.route) {
            LoginScreen(
                onBackClick = { navController.popBackStack() },
                onSignUpClick = {
                    navController.navigate(
                        NavRouts.SignUpScreen.route
                    ) {
                        popUpTo(NavRouts.AuthScreen.route) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                navigateToHome = {
                    navController.navigate(NavRouts.HomeScreen.route) {
                        popUpTo(0)
                    }
                })
        }

        composable(NavRouts.SignUpScreen.route) {
            SignUpScreen(
                onLoginClick = {
                    navController.navigate(
                        NavRouts.LoginScreen.route
                    ) {
                        popUpTo(NavRouts.LoginScreen.route) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                navigateToHome = {
                    navController.navigate(
                        NavRouts.HomeScreen.route
                    ) {
                        popUpTo(NavRouts.LoginScreen.route) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(NavRouts.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
    }
}
