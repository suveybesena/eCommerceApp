package com.example.capstoneproject.presentation.basket

import com.example.capstoneproject.data.entities.product.Basket
import com.example.capstoneproject.data.entities.product.Purchased

sealed class BasketUiEvent {
    data class GetAllBasketItems(val userId: String) : BasketUiEvent()
    data class InsertPurchasedToDatabase(val purchased: Purchased) : BasketUiEvent()
    data class DeleteBasketItem(val basketItem: Basket) : BasketUiEvent()
    data class GetBasketItemCount(val userId: String) : BasketUiEvent()
}

