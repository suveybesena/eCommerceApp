package com.example.capstoneproject.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.common.extensions.Resource
import com.example.capstoneproject.data.model.product.Favorites
import com.example.capstoneproject.domain.usecase.local.GetLastUserFromDatabaseUseCase
import com.example.capstoneproject.domain.usecase.local.InsertProductToFavoritesUseCase
import com.example.capstoneproject.domain.usecase.remote.GetAllCategoriesUseCase
import com.example.capstoneproject.domain.usecase.remote.GetAllProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val getLastUserFromDatabaseUseCase: GetLastUserFromDatabaseUseCase,
    private val insertProductToFavoritesUseCase: InsertProductToFavoritesUseCase
) :
    ViewModel() {

    private val uiState = MutableStateFlow(HomeUiState())
    val _uiState: StateFlow<HomeUiState> = uiState.asStateFlow()

    fun handleEvent(uiEvent: HomeUiEvent) {
        when (uiEvent) {
            is HomeUiEvent.GetAllCategories -> {
                getAllCategories()
            }
            is HomeUiEvent.GetAllProducts -> {
                getAllProducts()
            }
            is HomeUiEvent.GetLastUser -> {
                getLastUser()
            }
            is HomeUiEvent.InsertProductToFavorite -> {
                insertProductToFavorite(uiEvent.favorite)
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

    private fun getLastUser() {
        viewModelScope.launch {
            getLastUserFromDatabaseUseCase.invoke().collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(currentUser = resultState.data)
                        }
                    }
                }
            }
        }
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            getAllProductsUseCase.invoke().collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(products = resultState.data)
                        }
                    }
                }
            }
        }
    }

    private fun getAllCategories() {
        viewModelScope.launch {
            getAllCategoriesUseCase.invoke().collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(categories = resultState.data)
                        }
                    }
                }
            }
        }
    }
}