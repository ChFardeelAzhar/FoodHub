package com.example.foodhub.ui.features.auth.signup

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodhub.data.FoodApi
import com.example.foodhub.data.models.SignInRequest
import com.example.foodhub.data.models.SignUpRequest
import com.example.foodhub.ui.features.auth.BaseAuthViewModel
import com.example.foodhub.ui.features.auth.login.SignInViewModel.SignInEvents
import com.example.foodhub.ui.features.auth.login.SignInViewModel.SignInNavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    override val foodApi: FoodApi
) : BaseAuthViewModel(foodApi) {

    private var _uiState = MutableStateFlow<SignupEvents>(SignupEvents.Idle)
    val uiState = _uiState.asStateFlow()

    private var _navEvents = MutableSharedFlow<SignUpNavigationEvent>()
    val navState = _navEvents.asSharedFlow()

    private var _fullName = MutableStateFlow("")
    val fullName = _fullName.asStateFlow()

    private var _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private var _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    fun onNameChange(fullName: String) {
        _fullName.value = fullName
    }

    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun onSignUpClick() {
        _uiState.value = SignupEvents.Loading
        viewModelScope.launch {

            try {
                val response = foodApi.signUp(
                    SignUpRequest(
                        name = _fullName.value,
                        email = _email.value,
                        password = _password.value
                    )
                )
                if (response.token.isNotEmpty()) {
                    // Perform SignUp
                    _uiState.value = SignupEvents.Success
                    _navEvents.emit(SignUpNavigationEvent.NavigateToHome)
                }
            } catch (e: Exception) {
                _uiState.value = SignupEvents.Failure(e)
            }

        }
    }


    fun onGoogleSignInClick(context: ComponentActivity) {
        _uiState.value = SignupEvents.Loading
        viewModelScope.launch {
            initiateGoogleLogin(context)
        }
    }

    fun onFacebookButtonClick(context: ComponentActivity) {
        viewModelScope.launch {
            initiateFacebook(context)
        }
    }

    override fun onloading() {
        _uiState.value = SignupEvents.Loading
    }

    override fun onFacebookError(error: String) {
        _uiState.value = SignupEvents.Failure(Throwable(error))
    }

    override fun onGoogleError(error: String) {
        _uiState.value = SignupEvents.Failure(Throwable(error))
    }

    override fun onSuccess(token: String) {
        viewModelScope.launch {
            _uiState.value = SignupEvents.Success
            _navEvents.emit(SignUpNavigationEvent.NavigateToHome)
        }
    }

    sealed class SignUpNavigationEvent() {
        object NavigateToLogin : SignUpNavigationEvent()
        object NavigateToHome : SignUpNavigationEvent()
    }

    sealed class SignupEvents() {
        object Idle : SignupEvents()
        object Loading : SignupEvents()
        object Success : SignupEvents()
        data class Failure(val error: Throwable) : SignupEvents()
    }
}