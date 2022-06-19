package com.example.capstoneproject.presentation.loginregister.register

import com.example.capstoneproject.data.entities.user.UserItem
import com.example.capstoneproject.data.model.CRUDResponse

data class RegisterUiState(
    val userItem: UserItem? = null,
    val apiError: String? = null,
    val dbError: String? = null,
    val isLoading: Boolean? = null,
    val response: CRUDResponse? = null
)
