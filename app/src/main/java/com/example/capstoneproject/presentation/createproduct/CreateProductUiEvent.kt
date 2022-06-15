package com.example.capstoneproject.presentation.createproduct

import com.example.capstoneproject.data.entities.product.Product
import com.example.capstoneproject.data.model.InsertedProduct

sealed class CreateProductUiEvent {
    data class CreateProduct(val product: InsertedProduct) : CreateProductUiEvent()
    data class InsertProductToDatabase(val product: Product) : CreateProductUiEvent()
}
