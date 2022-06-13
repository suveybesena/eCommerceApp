package com.example.capstoneproject.presentation.home

import com.example.capstoneproject.data.model.product.Favorites

sealed class HomeUiEvent {
    object GetAllCategories : HomeUiEvent()
    object GetAllProducts : HomeUiEvent()
    object GetLastUser : HomeUiEvent()
    data class InsertProductToFavorite(val favorite: Favorites) : HomeUiEvent()
}
