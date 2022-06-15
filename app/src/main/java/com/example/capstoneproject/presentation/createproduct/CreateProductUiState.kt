package com.example.capstoneproject.presentation.createproduct

import com.example.capstoneproject.data.model.InsertedProduct

data class CreateProductUiState(
    val product: InsertedProduct? = null,
    val dbError: String? = null,
    val apiError: String? = null
)
