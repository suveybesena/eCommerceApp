package com.example.capstoneproject.domain.usecase.firebase

import com.example.capstoneproject.common.Resource
import com.example.capstoneproject.data.model.AuthModel
import com.example.capstoneproject.di.IoDispatcher
import com.example.capstoneproject.domain.repository.FirebaseAuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: FirebaseAuthRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun invoke(authModel: AuthModel) = flow {
        emit(Resource.Loading)
        try {
            repository.signIn(authModel)?.let {
                emit(Resource.Success(null))
            }
        } catch (e: Exception) {
        }
    }.flowOn(ioDispatcher)
}