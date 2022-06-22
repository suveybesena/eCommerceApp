package com.example.capstoneproject.presentation.loginregister.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.common.Resource
import com.example.capstoneproject.data.entities.user.User
import com.example.capstoneproject.data.model.AuthModel
import com.example.capstoneproject.domain.usecase.firebase.CreateUserUseCase
import com.example.capstoneproject.domain.usecase.firebase.GetCurrentUserUseCase
import com.example.capstoneproject.domain.usecase.local.user.InsertUserToDatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val insertUserToDatabaseUseCase: InsertUserToDatabaseUseCase,
    private val createUserUseCase: CreateUserUseCase,
) :
    ViewModel() {

    private val uiState = MutableStateFlow(RegisterUiState())
    val _uiState: StateFlow<RegisterUiState> = uiState.asStateFlow()

    fun handleEvent(uiEvent: RegisterUiEvent) {
        when (uiEvent) {
            is RegisterUiEvent.InsertUserToDb -> {
                insertUserToDb(uiEvent.user)
            }
            is RegisterUiEvent.CreateUser -> {
                createUser(uiEvent.authModel)
            }
        }
    }

    private fun createUser(authModel: AuthModel) {
        viewModelScope.launch {
            createUserUseCase.invoke(authModel).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(isRegister = true)
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