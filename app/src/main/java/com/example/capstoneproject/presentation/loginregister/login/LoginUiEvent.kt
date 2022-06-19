package com.example.capstoneproject.presentation.loginregister.login

sealed class LoginUiEvent {
    data class SignIn(val userName: String, val password: String) : LoginUiEvent()
}
