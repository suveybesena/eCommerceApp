package com.example.capstoneproject.presentation.home

import com.example.capstoneproject.data.model.product.Product
import com.example.capstoneproject.data.model.user.User
import kotlinx.coroutines.flow.Flow


data class HomeUiState(
    val error: String? = null,
    val categories: List<String>? = null,
    val products: List<Product>? = null,
    val currentUser: Flow<List<User>>? = null
)
