package com.example.capstoneproject.presentation.loginregister.register

import com.example.capstoneproject.data.model.user.User
import com.example.capstoneproject.data.model.user.UserItem

sealed class RegisterUiEvent {
    data class SignUp(val userItem: UserItem) : RegisterUiEvent()
    data class InsertUserToDb(val user: User) : RegisterUiEvent()
}