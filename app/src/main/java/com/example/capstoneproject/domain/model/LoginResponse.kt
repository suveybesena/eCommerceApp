package com.example.capstoneproject.domain.model

import com.example.capstoneproject.data.entities.user.UserResult

data class LoginResponse(
    val error: Boolean,
    val success: String,
    val user: UserResult
)
