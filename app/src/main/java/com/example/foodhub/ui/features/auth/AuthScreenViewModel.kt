package com.example.foodhub.ui.features.auth

import androidx.activity.ComponentActivity
import androidx.lifecycle.viewModelScope
import com.example.foodhub.data.FoodApi
import com.example.foodhub.data.models.SignInRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthScreenViewModel @Inject constructor(
    override val foodApi: FoodApi
) : BaseAuthViewModel(foodApi) {


    private var _uiState = MutableStateFlow<AuthScreenEvents>(AuthScreenEvents.Idle)
    val uiState = _uiState.asStateFlow()

    private var _navEvents = MutableSharedFlow<AuthNavigationEvent>()
    val navState = _navEvents.asSharedFlow()


    override fun onloading() {
        _uiState.value = AuthScreenEvents.Loading
    }

    override fun onFacebookError(error: String) {
        _uiState.value = AuthScreenEvents.Failure(Throwable(error))
    }

    override fun onGoogleError(error: String) {
        _uiState.value = AuthScreenEvents.Failure(Throwable(error))
    }

    override fun onSuccess(token: String) {
        viewModelScope.launch {
            _uiState.value = AuthScreenEvents.Success
            _navEvents.emit(AuthNavigationEvent.NavigateToHome)
        }
    }


    sealed class AuthNavigationEvent() {
        object NavigateToSignup : AuthNavigationEvent()
        object NavigateToHome : AuthNavigationEvent()
    }

    sealed class AuthScreenEvents() {
        object Idle : AuthScreenEvents()
        object Loading : AuthScreenEvents()
        object Success : AuthScreenEvents()
        data class Failure(val error: Throwable) : AuthScreenEvents()
    }
}
