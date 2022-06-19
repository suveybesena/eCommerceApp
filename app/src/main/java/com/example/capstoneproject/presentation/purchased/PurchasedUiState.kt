package com.example.capstoneproject.presentation.purchased

import com.example.capstoneproject.data.entities.product.Purchased
import kotlinx.coroutines.flow.Flow

data class PurchasedUiState(
    val purchasedProducts: Flow<List<Purchased>>? = null
)
