package com.example.capstoneproject.presentation.categories

import com.example.capstoneproject.data.entities.product.Product

data class CategoriesUiState(
    val productModels: MutableList<Product>? = null,
    val error: String? = null
)
