package com.example.foodhub.ui.features.auth

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.credentials.CredentialManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodhub.data.FoodApi
import com.example.foodhub.data.auth.GoogleAuthUiProvider
import com.example.foodhub.data.models.OAuthRequest
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kotlinx.coroutines.launch

abstract class BaseAuthViewModel(open val foodApi: FoodApi) : ViewModel() {

    abstract fun onloading()
    abstract fun onFacebookError(error: String)
    abstract fun onGoogleError(error: String)
    abstract fun onSuccess(token: String)


    fun onFacebookClick(context: ComponentActivity) {
        initiateFacebook(context = context)
    }

    fun onGoogleClick(context: ComponentActivity) {
        initiateGoogleLogin(context)
    }


    val googleAuthUiProvider = GoogleAuthUiProvider()
    lateinit var callbackManager: CallbackManager

    fun initiateFacebook(context: ComponentActivity) {
        onloading()
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    // App code
                    viewModelScope.launch {
                        if (loginResult != null) {
                            val request = OAuthRequest(
                                token = loginResult.accessToken.token,
                                provider = "facebook"
                            )
                            val res = foodApi.oAuthLogin(
                                request = request
                            )
                            if (res.token.isNotEmpty()) {
                                onSuccess(res.token)
                            }
                        }
                    }
                }

                override fun onCancel() {
                    onFacebookError(error = "Canceled")
                }

                override fun onError(exception: FacebookException) {
                    onFacebookError(error = "Failed: ${exception.message.toString()}")
                }
            })

        LoginManager.getInstance().logInWithReadPermissions(
            context,
            callbackManager,
            listOf("public_profile", "email")
        )
    }

    fun initiateGoogleLogin(context: ComponentActivity) {
        viewModelScope.launch {
            onloading()
            try {

                val response = googleAuthUiProvider.signIn(
                    credentialManager = CredentialManager.create(context),
                    activityContext = context
                )

                if (response==null){
                    onGoogleError("Failed")
                }

                if (response != null) {
                    val request = OAuthRequest(
                        token = response.token,
                        provider = "google"
                    )
                    val res = foodApi.oAuthLogin(
                        request = request
                    )
                    if (res.token.isNotEmpty()) {
                        onSuccess(res.token)
                    }
                }

            } catch (e: Exception) {
                Log.d("FRDL", "onGoogleSignInClick: ${e.message.toString()}")
                onGoogleError("Failed: ${e.message.toString()}")
            }

        }
    }
}