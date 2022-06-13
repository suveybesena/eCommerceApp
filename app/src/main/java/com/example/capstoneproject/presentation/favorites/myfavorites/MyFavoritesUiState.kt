package com.example.capstoneproject.presentation.favorites.myfavorites

import com.example.capstoneproject.data.model.product.Favorites
import kotlinx.coroutines.flow.Flow

data class MyFavoritesUiState(
    val favorites: Flow<List<Favorites>>? = null
)
