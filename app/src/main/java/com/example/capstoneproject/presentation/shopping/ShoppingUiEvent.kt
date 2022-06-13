package com.example.capstoneproject.presentation.shopping

sealed class ShoppingUiEvent {
    data class GetAllBasketItems(val userId: String) : ShoppingUiEvent()
}
