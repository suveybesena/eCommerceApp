package com.example.capstoneproject.presentation.favorites.myfavorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.common.extensions.Resource
import com.example.capstoneproject.domain.usecase.local.GetFavoritesProductsFromDatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyFavoritesViewModel @Inject constructor(
    private val getFavoritesProductsFromDatabaseUseCase: GetFavoritesProductsFromDatabaseUseCase
) : ViewModel() {

    private val uiState = MutableStateFlow(MyFavoritesUiState())
    val _uiState: StateFlow<MyFavoritesUiState> = uiState.asStateFlow()

    fun handleEvent(event: MyFavoritesUiEvent) {
        when (event) {
            is MyFavoritesUiEvent.GetAllFavorites -> {
                getAllFavorites(event.userId)
            }
        }
    }

    private fun getAllFavorites(userId: String) {
        viewModelScope.launch {
            getFavoritesProductsFromDatabaseUseCase.invoke(userId).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(favorites = resultState.data)
                        }
                    }
                }
            }
        }
    }
}