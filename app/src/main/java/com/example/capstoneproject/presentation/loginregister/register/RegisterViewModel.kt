package com.example.capstoneproject.presentation.loginregister.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.common.extensions.Resource
import com.example.capstoneproject.data.model.user.User
import com.example.capstoneproject.data.model.user.UserItem
import com.example.capstoneproject.domain.usecase.local.InsertUserToDatabaseUseCase
import com.example.capstoneproject.domain.usecase.remote.SingUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val singUpUseCase: SingUpUseCase,
    private val insertUserToDatabaseUseCase: InsertUserToDatabaseUseCase
) :
    ViewModel() {

    private val uiState = MutableStateFlow(RegisterUiState())
    val _uiState: StateFlow<RegisterUiState> = uiState.asStateFlow()

    fun handleEvent(uiEvent: RegisterUiEvent) {
        when (uiEvent) {
            is RegisterUiEvent.SignUp -> {
                singUp(uiEvent.userItem)
            }
            is RegisterUiEvent.InsertUserToDb -> {
                insertUserToDb(uiEvent.user)
            }

        }
    }

    private fun insertUserToDb(user: User) {
        viewModelScope.launch {
            insertUserToDatabaseUseCase.invoke(user).collect { resultState ->
                when (resultState) {
                    is Resource.Error -> {
                        uiState.update { state ->
                            state.copy(error = resultState.message)
                        }
                    }
                }
            }
        }
    }

    private fun singUp(userItem: UserItem) {
        viewModelScope.launch {
            singUpUseCase.invoke(userItem).collect { resultState ->
                when (resultState) {
                    is Resource.Error -> {
                        uiState.update { state ->
                            state.copy(error = resultState.message)
                        }
                    }
                    is Resource.Loading -> {
                        uiState.update { state ->
                            state.copy(isLoading = true)
                        }
                    }
                }
            }
        }
    }
}