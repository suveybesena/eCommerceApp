package com.example.capstoneproject.presentation.detail

import com.example.capstoneproject.data.model.product.Product

data class DetailUiState(
    val product: Product? = null,
    val error: String? = null
)
