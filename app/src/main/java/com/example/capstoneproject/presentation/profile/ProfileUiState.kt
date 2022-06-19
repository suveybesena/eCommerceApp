package com.example.capstoneproject.presentation.profile

import com.example.capstoneproject.data.entities.user.User

data class ProfileUiState(
    val user: User? = null,
    val collectionsCount: Int? = null,
    val favoritesCount: Int? = null,
    val purchasedCount: Int? = null
)
