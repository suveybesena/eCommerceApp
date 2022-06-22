package com.example.capstoneproject.presentation.loginregister.login

import com.example.capstoneproject.data.entities.user.User
import com.example.capstoneproject.data.entities.user.UserItem

data class LoginUiState(
    val userLogin: User? = null,
    val isLoggedIn: Boolean? = false,
    val currentUser: String? = null
)
