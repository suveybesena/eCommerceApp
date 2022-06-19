package com.example.capstoneproject.presentation.loginregister.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.common.Resource
import com.example.capstoneproject.data.entities.user.User
import com.example.capstoneproject.domain.usecase.local.user.InsertUserToDatabaseUseCase
import com.example.capstoneproject.domain.usecase.remote.user.SignUpWithAPIUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val insertUserToDatabaseUseCase: InsertUserToDatabaseUseCase,
    private val signUpWithAPIUseCase: SignUpWithAPIUseCase
) :
    ViewModel() {

    private val uiState = MutableStateFlow(RegisterUiState())
    val _uiState: StateFlow<RegisterUiState> = uiState.asStateFlow()

    fun handleEvent(uiEvent: RegisterUiEvent) {
        when (uiEvent) {
            is RegisterUiEvent.InsertUserToDb -> {
                insertUserToDb(uiEvent.user)
            }
            is RegisterUiEvent.SignUp -> {
                signUp(
                    uiEvent.email,
                    uiEvent.password,
                    uiEvent.name,
                    uiEvent.phone,
                    uiEvent.address
                )
            }

        }
    }

    private fun signUp(
        email: String,
        password: String,
        name: String,
        phone: String,
        address: String
    ) {
        viewModelScope.launch {
            signUpWithAPIUseCase.invoke(email, password, name, phone, address)
                .collect { resultState ->
                    when (resultState) {
                        is Resource.Success -> {
                            uiState.update { state ->
                                state.copy(response = resultState.data)
                            }
                        }
                    }
                }
        }
    }

    private fun insertUserToDb(user: User) {
        viewModelScope.launch {
            insertUserToDatabaseUseCase.invoke(user).collect { resultState ->
                when (resultState) {
                    is Resource.Error -> {
                        uiState.update { state ->
                            state.copy(dbError = resultState.message)
                        }
                    }
                }
            }
        }
    }
}