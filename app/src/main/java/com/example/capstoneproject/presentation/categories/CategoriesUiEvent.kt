package com.example.capstoneproject.presentation.categories

import com.example.capstoneproject.data.entities.product.Collection
import com.example.capstoneproject.data.entities.product.Favorites

sealed class CategoriesUiEvent {
    data class GetProductsByCategoriesUseCase(val categoryName: String) : CategoriesUiEvent()
    data class InsertProductToFavorites(val favorites: Favorites) : CategoriesUiEvent()
    data class InsertProductToCollections(val collection: Collection) : CategoriesUiEvent()
}
