package com.example.capstoneproject.presentation.favorites.myfavorites

import com.example.capstoneproject.data.entities.product.Favorites
import kotlinx.coroutines.flow.Flow

data class MyFavoritesUiState(
    val favorites: Flow<List<Favorites>>? = null,
    val error: String? = null
)
