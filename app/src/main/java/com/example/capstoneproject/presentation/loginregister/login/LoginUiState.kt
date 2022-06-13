package com.example.capstoneproject.presentation.loginregister.login

import com.example.capstoneproject.data.model.user.UserItem

data class LoginUiState(
    val token: Map<String, String>? = null,
    val users: List<UserItem>? = null
)
