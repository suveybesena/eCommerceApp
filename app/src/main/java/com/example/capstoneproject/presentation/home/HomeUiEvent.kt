package com.example.capstoneproject.presentation.home

import com.example.capstoneproject.data.entities.product.Collection
import com.example.capstoneproject.data.entities.product.Favorites

sealed class HomeUiEvent {
    object GetAllProducts : HomeUiEvent()
    data class InsertProductToFavorite(val favorite: Favorites) : HomeUiEvent()
    data class InsertProductToCollections(val collection: Collection) : HomeUiEvent()
    data class GetDiscountProducts(val categoryName: String) : HomeUiEvent()
    data class GetBasketItemsCount(val userId: String) : HomeUiEvent()
    data class GetCategories(val user: String) : HomeUiEvent()
}
