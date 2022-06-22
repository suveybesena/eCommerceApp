package com.example.capstoneproject.presentation.loginregister.register

import com.example.capstoneproject.data.entities.user.User
import com.example.capstoneproject.data.model.AuthModel

sealed class RegisterUiEvent {
    data class InsertUserToDb(val user: User) : RegisterUiEvent()
    data class CreateUser(val authModel: AuthModel) : RegisterUiEvent()
}