package com.example.capstoneproject.presentation.categories

sealed class CategoriesUiEvent {
    data class GetProductsByCategoriesUseCase(val categoryName: String) : CategoriesUiEvent()
}
