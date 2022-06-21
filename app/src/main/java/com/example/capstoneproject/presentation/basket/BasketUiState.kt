package com.example.capstoneproject.presentation.basket

import com.example.capstoneproject.data.entities.product.Basket
import com.example.capstoneproject.data.model.CRUDResponse
import kotlinx.coroutines.flow.Flow

data class BasketUiState(
    val basketItems: Flow<List<Basket>>? = null,
    val error: String? = null,
    val basketItemsCount: Int? = null,
    val bagProducts: MutableList<Basket>? = null,
    val deleteBagResponse: CRUDResponse? = null
)
