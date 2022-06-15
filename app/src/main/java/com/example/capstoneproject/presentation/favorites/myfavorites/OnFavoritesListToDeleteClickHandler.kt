package com.example.capstoneproject.presentation.favorites.myfavorites

import com.example.capstoneproject.data.entities.product.Favorites

interface OnFavoritesListToDeleteClickHandler {
    fun deleteProduct(favorites: Favorites)
}