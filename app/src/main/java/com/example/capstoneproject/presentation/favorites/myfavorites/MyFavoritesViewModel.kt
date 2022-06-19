package com.example.capstoneproject.presentation.favorites.myfavorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.common.Resource
import com.example.capstoneproject.data.entities.product.Favorites
import com.example.capstoneproject.domain.usecase.local.product.DeleteFavoriteProductUseCase
import com.example.capstoneproject.domain.usecase.local.product.GetFavoritesProductsFromDatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyFavoritesViewModel @Inject constructor(
    private val getFavoritesProductsFromDatabaseUseCase: GetFavoritesProductsFromDatabaseUseCase,
    private val deleteFavoriteProductUseCase: DeleteFavoriteProductUseCase
) : ViewModel() {

    private val uiState = MutableStateFlow(MyFavoritesUiState())
    val _uiState: StateFlow<MyFavoritesUiState> = uiState.asStateFlow()

    fun handleEvent(event: MyFavoritesUiEvent) {
        when (event) {
            is MyFavoritesUiEvent.GetAllFavorites -> {
                getAllFavorites(event.userId)
            }
            is MyFavoritesUiEvent.DeleteProduct -> {
                deleteProduct(event.favorites)
            }
        }
    }

    private fun deleteProduct(favorites: Favorites) {
        viewModelScope.launch {
            deleteFavoriteProductUseCase.invoke(favorites).collect { resultState ->
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