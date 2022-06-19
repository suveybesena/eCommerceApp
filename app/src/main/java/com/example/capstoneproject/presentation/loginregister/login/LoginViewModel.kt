package com.example.capstoneproject.presentation.loginregister.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.common.Resource
import com.example.capstoneproject.domain.usecase.local.user.SignInWithDatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInWithDatabaseUseCase: SignInWithDatabaseUseCase
) : ViewModel() {

    private val uiState = MutableStateFlow(LoginUiState())
    val _uiState: StateFlow<LoginUiState> = uiState.asStateFlow()

    fun handleEvent(uiEvent: LoginUiEvent) {
        when (uiEvent) {
            is LoginUiEvent.SignIn -> {
                signIn(uiEvent.userName, uiEvent.password)
            }
        }
    }

    private fun signIn(userName: String, password: String) {
        viewModelScope.launch {
            signInWithDatabaseUseCase.invoke(userName, password).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(userLogin = resultState.data)
                        }
                    }
                }
            }
        }
    }
}