package com.example.capstoneproject.presentation.detail

import com.example.capstoneproject.data.model.product.Basket

sealed class DetailUiEvent {
    data class GetProductById(val productId: Int) : DetailUiEvent()
    data class InsertProductToBasket(val basketItem: Basket) : DetailUiEvent()
}
