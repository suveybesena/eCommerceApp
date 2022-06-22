package com.example.capstoneproject.presentation.loginregister.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.common.Resource
import com.example.capstoneproject.data.model.AuthModel
import com.example.capstoneproject.domain.usecase.firebase.GetCurrentUserUseCase
import com.example.capstoneproject.domain.usecase.firebase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val uiState = MutableStateFlow(LoginUiState())
    val _uiState: StateFlow<LoginUiState> = uiState.asStateFlow()

    fun handleEvent(uiEvent: LoginUiEvent) {
        when (uiEvent) {
            is LoginUiEvent.Login -> {
                login(uiEvent.authModel)
            }
            is LoginUiEvent.GetCurrentUser -> {
                getCurrentUser()
            }
        }
    }

    private fun getCurrentUser() {
        viewModelScope.launch {
            getCurrentUserUseCase.invoke().collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(currentUser = resultState.data)
                        }
                    }
                }
            }
        }
    }

    private fun login(authModel: AuthModel) {
        viewModelScope.launch {
            loginUseCase.invoke(authModel).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(isLoggedIn = true)
                        }
                    }
                }
            }
        }
    }
}