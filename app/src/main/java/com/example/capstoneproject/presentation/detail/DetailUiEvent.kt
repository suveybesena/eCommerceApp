package com.example.capstoneproject.presentation.detail

import com.example.capstoneproject.data.entities.product.Basket

sealed class DetailUiEvent {
    data class InsertProductToBasket(val basketItem: Basket) : DetailUiEvent()
    data class GetBasketItemsCount(val userId: String) : DetailUiEvent()
    data class AddProductToBasket(
        val user: String?,
        val title: String?,
        val price: Double?,
        val description: String?,
        val category: String?,
        val image: String?,
        val rate: Double?,
        val count: Int?,
        val sale_state: Int?
    ) : DetailUiEvent()
}
