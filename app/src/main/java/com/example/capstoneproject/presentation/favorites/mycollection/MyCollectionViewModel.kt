package com.example.capstoneproject.presentation.favorites.mycollection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.common.Resource
import com.example.capstoneproject.data.entities.product.Collection
import com.example.capstoneproject.domain.usecase.local.product.DeleteCollectionProductUseCase
import com.example.capstoneproject.domain.usecase.local.product.GetCollectionProductsFromDatabaseUseCase
import com.example.capstoneproject.domain.usecase.remote.product.GetAllProductsByNameUseCase
import com.example.capstoneproject.domain.usecase.remote.product.GetProductsByCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyCollectionViewModel @Inject constructor(
    private val getCollectionProductsFromDatabaseUseCase: GetCollectionProductsFromDatabaseUseCase,
    private val getAllProductsByNameUseCase: GetAllProductsByNameUseCase,
    private val deleteCollectionProductUseCase: DeleteCollectionProductUseCase,
    private val getProductsByCategoriesUseCase: GetProductsByCategoriesUseCase
) :
    ViewModel() {
    private val uiState = MutableStateFlow(MyCollectionUiState())
    val _uiState: StateFlow<MyCollectionUiState> = uiState.asStateFlow()

    fun handleEvent(event: MyCollectionUiEvent) {
        when (event) {
            is MyCollectionUiEvent.GetAllCollections -> {
                getAllCollections(event.userId)
            }
            is MyCollectionUiEvent.GetAllProducts -> {
                getAllProducts()
            }
            is MyCollectionUiEvent.DeleteProduct -> {
                deleteProduct(event.collection)
            }
            is MyCollectionUiEvent.GetNewProducts -> {
                getProductsByCategories(event.categoryName)
            }
        }
    }

    private fun getProductsByCategories(categoryName: String) {
        viewModelScope.launch {
            getProductsByCategoriesUseCase.invoke(categoryName).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(newProducts = resultState.data)
                        }
                    }
                }
            }
        }
    }

    private fun deleteProduct(collection: Collection) {
        viewModelScope.launch {
            deleteCollectionProductUseCase.invoke(collection).collect { resultState ->
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

    private fun getAllProducts() {
        viewModelScope.launch {
            getAllProductsByNameUseCase.invoke().collect { resultState ->
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

    private fun getAllCollections(userId: String) {
        viewModelScope.launch {
            getCollectionProductsFromDatabaseUseCase.invoke(userId).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(collectionProduct = resultState.data)
                        }
                    }
                }
            }
        }
    }
}