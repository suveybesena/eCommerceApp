package com.example.capstoneproject.presentation.loginregister.login

import com.example.capstoneproject.data.model.AuthModel

sealed class LoginUiEvent {

    data class Login(var authModel: AuthModel) : LoginUiEvent()
    object GetCurrentUser : LoginUiEvent()
}
