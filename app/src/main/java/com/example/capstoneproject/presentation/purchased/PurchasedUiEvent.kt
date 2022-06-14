package com.example.capstoneproject.presentation.purchased

sealed class PurchasedUiEvent {
    data class GetPurchasedProducts(val userId: String) : PurchasedUiEvent()
}
