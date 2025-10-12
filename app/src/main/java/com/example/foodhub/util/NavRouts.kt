package com.example.foodhub.util

sealed class NavRouts(val route: String) {
    object AuthScreen : NavRouts("auth_screen")
    object LoginScreen : NavRouts("login_screen")
    object SignUpScreen : NavRouts("signup_screen")
    object HomeScreen : NavRouts("home_screen")
}