package com.example.capstoneproject.presentation.loginregister.register

import com.example.capstoneproject.data.model.user.UserItem

data class RegisterUiState(
    val userItem: UserItem? = null,
    val error: String? = null,
    val isLoading: Boolean? = null
)
