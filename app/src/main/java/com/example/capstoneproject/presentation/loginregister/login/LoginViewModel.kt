package com.example.capstoneproject.presentation.loginregister.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.common.extensions.Resource
import com.example.capstoneproject.domain.usecase.remote.GetUsersUseCase
import com.example.capstoneproject.domain.usecase.remote.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val uiState = MutableStateFlow(LoginUiState())
    val _uiState: StateFlow<LoginUiState> = uiState.asStateFlow()

    fun handleEvent(uiEvent: LoginUiEvent) {
        when (uiEvent) {
            is LoginUiEvent.SignIn -> {
                signIn(uiEvent.userName, uiEvent.password)
            }
            is LoginUiEvent.GetAllUsers -> {
                getAllUser()
            }
        }
    }

    private fun getAllUser() {
        viewModelScope.launch {
            getUsersUseCase.invoke().collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(users = resultState.data)
                        }
                    }
                }

            }
        }
    }

    private fun signIn(userName: String, password: String) {
        viewModelScope.launch {
            signInUseCase.invoke(userName, password).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(token = resultState.data)
                        }
                    }
                }
            }
        }
    }
}