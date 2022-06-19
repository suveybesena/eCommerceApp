package com.example.capstoneproject.presentation.detail

import com.example.capstoneproject.data.entities.product.Product
import com.example.capstoneproject.data.model.CRUDResponse

data class DetailUiState(
    val productModel: Product? = null,
    val error: String? = null,
    val basketItemsCount: Int? = null,
    val response: CRUDResponse? = null
)
