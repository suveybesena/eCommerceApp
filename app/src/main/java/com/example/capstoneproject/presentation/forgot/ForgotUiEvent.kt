package com.example.capstoneproject.presentation.forgot

sealed class ForgotUiEvent {
    data class ResetPassword(val email: String) : ForgotUiEvent()
}
