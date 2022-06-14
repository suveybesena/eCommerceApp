package com.example.capstoneproject.presentation.profile

sealed class ProfileUiEvent {
    data class GetCurrentUserId(val userId: Int) : ProfileUiEvent()
}