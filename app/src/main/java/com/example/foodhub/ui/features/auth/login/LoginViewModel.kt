package com.example.foodhub.ui.features.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodhub.data.FoodApi
import com.example.foodhub.data.models.SignInRequest
import com.example.foodhub.data.models.SignUpRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    val foodApi: FoodApi
) : ViewModel() {

    private var _uiState = MutableStateFlow<SignInEvents>(SignInEvents.Idle)
    val uiState = _uiState.asStateFlow()

    private var _navEvents = MutableSharedFlow<SignInNavigationEvent>()
    val navState = _navEvents.asSharedFlow()

    private var _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private var _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun onSignInClick() {
        _uiState.value = SignInEvents.Loading
        viewModelScope.launch {

            try {
                val response = foodApi.signIn(
                    SignInRequest(
                        email = _email.value,
                        password = _password.value
                    )
                )
                if (response.token.isNotEmpty()) {
                    // Perform SignUp
                    _uiState.value = SignInEvents.Success
                    _navEvents.emit(SignInNavigationEvent.NavigateToHome)
                }
            } catch (e: Exception) {
                _uiState.value = SignInEvents.Failure(e)
            }

        }
    }

    sealed class SignInNavigationEvent() {
        object NavigateToSignup : SignInNavigationEvent()
        object NavigateToHome : SignInNavigationEvent()
    }

    sealed class SignInEvents() {
        object Idle : SignInEvents()
        object Loading : SignInEvents()
        object Success : SignInEvents()
        data class Failure(val error: Throwable) : SignInEvents()
    }
}