package com.example.capstoneproject.presentation.basket

import com.example.capstoneproject.data.entities.product.Basket
import kotlinx.coroutines.flow.Flow

data class BasketUiState(
    val basketItems: Flow<List<Basket>>? = null,
    val error: String? = null,
    val basketItemsCount: Int? = null
)
