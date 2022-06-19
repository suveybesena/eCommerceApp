package com.example.capstoneproject.presentation.favorites.myfavorites

import com.example.capstoneproject.data.entities.product.Favorites

sealed class MyFavoritesUiEvent {
    data class GetAllFavorites(val userId: String) : MyFavoritesUiEvent()
    data class DeleteProduct(val favorites: Favorites) : MyFavoritesUiEvent()
}
