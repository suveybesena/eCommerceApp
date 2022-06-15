package com.example.capstoneproject.presentation.home

import com.example.capstoneproject.data.entities.product.Product

interface OnProductListToFavoritesClickHandler {
    fun addFavorites(productModel: Product)
}