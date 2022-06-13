package com.example.capstoneproject.presentation.favorites.myfavorites

sealed class MyFavoritesUiEvent {
    data class GetAllFavorites(val userId: String) : MyFavoritesUiEvent()
}
