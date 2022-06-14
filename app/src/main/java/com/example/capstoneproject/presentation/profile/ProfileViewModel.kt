package com.example.capstoneproject.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.common.Resource
import com.example.capstoneproject.domain.usecase.local.GetCurrentUserFromDatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val getCurrentUserFromDatabaseUseCase: GetCurrentUserFromDatabaseUseCase) :
    ViewModel() {

    private val uiState = MutableStateFlow(ProfileUiState())
    val _uiState: StateFlow<ProfileUiState> = uiState.asStateFlow()

    fun handleEvent(event: ProfileUiEvent) {
        when (event) {
            is ProfileUiEvent.GetCurrentUserId -> {
                getPurchasedProducts(event.userId)
            }
        }
    }

    private fun getPurchasedProducts(userId: Int) {
        viewModelScope.launch {
            getCurrentUserFromDatabaseUseCase.invoke(userId).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(user = resultState.data)
                        }
                    }
                }
            }
        }
    }
}