package com.example.capstoneproject.presentation.shopping

import com.example.capstoneproject.data.model.product.Basket
import kotlinx.coroutines.flow.Flow

data class ShoppingUiState(
    val basketItems: Flow<List<Basket>>? = null
)
