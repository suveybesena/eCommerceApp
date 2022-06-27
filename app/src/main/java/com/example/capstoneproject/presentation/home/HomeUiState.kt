package com.example.capstoneproject.presentation.home

import com.example.capstoneproject.data.entities.product.Product


data class HomeUiState(
    val error: String? = null,
    val products: List<Product>? = null,
    val allProducts: MutableList<Product>? = null,
    val discountProducts: MutableList<Product>? = null,
    val basketItemsCount: Int? = null,
    val categories: List<String>? = null
)
