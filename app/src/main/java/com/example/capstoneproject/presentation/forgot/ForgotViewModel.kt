package com.example.capstoneproject.presentation.forgot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.common.Resource
import com.example.capstoneproject.domain.usecase.firebase.ForgotUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotViewModel @Inject constructor(private val forgotUseCase: ForgotUseCase) : ViewModel() {

    private val uiState = MutableStateFlow(ForgotUiState())
    val _uiState: StateFlow<ForgotUiState> = uiState.asStateFlow()

    fun handleEvent(event: ForgotUiEvent) {
        when (event) {
            is ForgotUiEvent.ResetPassword -> {
                resetPassword(event.email)
            }
        }
    }

    private fun resetPassword(email: String) {
        viewModelScope.launch {
            forgotUseCase.invoke(email).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(success = true)
                        }
                    }
                }
            }
        }
    }
}