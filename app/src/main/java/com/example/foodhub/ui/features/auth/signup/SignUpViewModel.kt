package com.example.foodhub.ui.features.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodhub.data.FoodApi
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
class SignUpViewModel @Inject constructor(
    val foodApi: FoodApi
) : ViewModel() {

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