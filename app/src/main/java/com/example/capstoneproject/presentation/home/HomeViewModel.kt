package com.example.capstoneproject.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.common.Resource
import com.example.capstoneproject.data.entities.product.Collection
import com.example.capstoneproject.data.entities.product.Favorites
import com.example.capstoneproject.domain.usecase.local.product.GetBasketItemsCountUseCase
import com.example.capstoneproject.domain.usecase.local.product.InsertProductToCollectionsUseCase
import com.example.capstoneproject.domain.usecase.local.product.InsertProductToFavoritesUseCase
import com.example.capstoneproject.domain.usecase.remote.product.GetAllCategoriesByUserUseCase
import com.example.capstoneproject.domain.usecase.remote.product.GetAllProductsByNameUseCase
import com.example.capstoneproject.domain.usecase.remote.product.GetProductsByCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val insertProductToFavoritesUseCase: InsertProductToFavoritesUseCase,
    private val insertProductToCollectionsUseCase: InsertProductToCollectionsUseCase,
    private val getAllProductsFromUseCase: GetAllProductsByNameUseCase,
    private val getProductsByCategoriesUseCase: GetProductsByCategoriesUseCase,
    private val getBasketItemsCountUseCase: GetBasketItemsCountUseCase,
    private val getCategoriesUseCase: GetAllCategoriesByUserUseCase

) :
    ViewModel() {

    private val uiState = MutableStateFlow(HomeUiState())
    val _uiState: StateFlow<HomeUiState> = uiState.asStateFlow()

    fun handleEvent(uiEvent: HomeUiEvent) {
        when (uiEvent) {
            is HomeUiEvent.GetAllProducts -> {
                getAllProductsByName()
            }
            is HomeUiEvent.InsertProductToFavorite -> {
                insertProductToFavorite(uiEvent.favorite)
            }
            is HomeUiEvent.InsertProductToCollections -> {
                insertProductToCollections(uiEvent.collection)
            }
            is HomeUiEvent.GetDiscountProducts -> {
                getProductsByCategories(uiEvent.categoryName)
            }
            is HomeUiEvent.GetBasketItemsCount -> {
                getBasketItemsCount(uiEvent.userId)
            }
            is HomeUiEvent.GetCategories -> {
                getCategories(uiEvent.user)
            }
        }
    }

    private fun getCategories(user: String) {
        viewModelScope.launch {
            getCategoriesUseCase.invoke(user).collect { resultState ->
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

    private fun getBasketItemsCount(userId: String) {
        viewModelScope.launch {
            getBasketItemsCountUseCase.invoke(userId).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(basketItemsCount = resultState.data)
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
                            state.copy(discountProducts = resultState.data)
                        }
                    }
                }
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

    private fun getAllProductsByName() {
        viewModelScope.launch {
            getAllProductsFromUseCase.invoke().collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(allProducts = resultState.data)
                        }
                    }
                }
            }
        }
    }
}