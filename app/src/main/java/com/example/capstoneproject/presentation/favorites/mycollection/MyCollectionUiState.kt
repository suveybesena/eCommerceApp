package com.example.capstoneproject.presentation.favorites.mycollection

import com.example.capstoneproject.data.entities.product.Collection
import com.example.capstoneproject.data.entities.product.Product
import kotlinx.coroutines.flow.Flow

data class MyCollectionUiState(
    val collectionProduct: Flow<List<Collection>>? = null,
    val products: MutableList<Product>? = null,
    val error: String? = null,
    val newProducts: MutableList<Product>? = null
)
