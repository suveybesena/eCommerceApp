package com.example.capstoneproject.presentation.profile

sealed class ProfileUiEvent {
    data class GetCurrentUserId(val userId: String) : ProfileUiEvent()
    data class GetCollectionItems(val userId: String) : ProfileUiEvent()
    data class GetPurchasedItems(val userId: String) : ProfileUiEvent()
    data class GetFavoritesItems(val userId: String) : ProfileUiEvent()

}