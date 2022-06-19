package com.example.capstoneproject.presentation.loginregister.register

import com.example.capstoneproject.data.entities.user.User

sealed class RegisterUiEvent {
    data class InsertUserToDb(val user: User) : RegisterUiEvent()
    data class SignUp(
        val email: String,
        val password: String,
        val name: String,
        val phone: String,
        val address: String
    ) : RegisterUiEvent()
}