package com.example.fitnessapp.model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginScreenView : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState: StateFlow<LoginUIState> = _uiState.asStateFlow()

    fun onLoginButtonClick(username:String, password:String){
        _uiState.update { currentState ->
            currentState.copy(username = username, password = password)
        }
    }

    fun resetLogin() {
        _uiState.value = LoginUIState()
    }
}