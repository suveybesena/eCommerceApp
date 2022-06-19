package com.example.capstoneproject.presentation.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.common.Resource
import com.example.capstoneproject.data.entities.product.Collection
import com.example.capstoneproject.data.entities.product.Favorites
import com.example.capstoneproject.domain.usecase.local.product.InsertProductToCollectionsUseCase
import com.example.capstoneproject.domain.usecase.local.product.InsertProductToFavoritesUseCase
import com.example.capstoneproject.domain.usecase.remote.product.GetProductsByCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getProductsByCategoriesUseCase: GetProductsByCategoriesUseCase,
    private val insertProductToFavoritesUseCase: InsertProductToFavoritesUseCase,
    private val insertProductToCollectionsUseCase: InsertProductToCollectionsUseCase
) :
    ViewModel() {

    private val uiState = MutableStateFlow(CategoriesUiState())
    val _uiState: StateFlow<CategoriesUiState> = uiState.asStateFlow()

    fun handleEvent(event: CategoriesUiEvent) {
        when (event) {
            is CategoriesUiEvent.GetProductsByCategoriesUseCase -> {
                getProductsByCategories(event.categoryName)
            }
            is CategoriesUiEvent.InsertProductToFavorites -> {
                insertProductToFavorite(event.favorites)
            }
            is CategoriesUiEvent.InsertProductToCollections -> {
                insertProductToCollections(event.collection)
            }
        }
    }

    private fun insertProductToCollections(collection: Collection) {
        viewModelScope.launch {
            insertProductToCollectionsUseCase.invoke(collection).collect { resultState ->
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

    private fun insertProductToFavorite(favorites: Favorites) {
        viewModelScope.launch {
            insertProductToFavoritesUseCase.invoke(favorites).collect { resultState ->
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

    private fun getProductsByCategories(categoryName: String) {
        viewModelScope.launch {
            getProductsByCategoriesUseCase.invoke(categoryName).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(productModels = resultState.data)
                        }
                    }
                }
            }
        }
    }
}
