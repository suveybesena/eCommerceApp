package com.example.capstoneproject.domain.repository

import com.example.capstoneproject.data.model.AuthModel
import com.google.firebase.auth.FirebaseUser

interface FirebaseAuthRepository {
    suspend fun signUpWithEmail(authModel: AuthModel): FirebaseUser?
    suspend fun getCurrentUserId(): String?
    suspend fun signIn(authModel: AuthModel): FirebaseUser?
    suspend fun forgotPassword(email: String)
}