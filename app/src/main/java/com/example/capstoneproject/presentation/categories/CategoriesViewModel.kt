package com.example.capstoneproject.presentation.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.common.Resource
import com.example.capstoneproject.domain.usecase.remote.GetProductsByCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(private val getProductsByCategoriesUseCase: GetProductsByCategoriesUseCase) :
    ViewModel() {

    private val uiState = MutableStateFlow(CategoriesUiState())
    val _uiState: StateFlow<CategoriesUiState> = uiState.asStateFlow()

    fun handleEvent(event: CategoriesUiEvent) {
        when (event) {
            is CategoriesUiEvent.GetProductsByCategoriesUseCase -> {
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
                            state.copy(products = resultState.data)
                        }
                    }
                }
            }
        }
    }
}
