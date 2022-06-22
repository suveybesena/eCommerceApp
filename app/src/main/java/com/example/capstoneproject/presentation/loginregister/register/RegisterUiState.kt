package com.example.capstoneproject.presentation.loginregister.register

import com.google.firebase.auth.FirebaseUser

data class RegisterUiState(
    var error: String? = null,
    val isRegister: Boolean? = false,
    val dbError: String? = null,
    val currentUser: String? = null,
    val firebaseUser: FirebaseUser? = null
)
